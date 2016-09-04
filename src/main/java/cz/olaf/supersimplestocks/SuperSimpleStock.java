package cz.olaf.supersimplestocks;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Martin Konir
 */
public class SuperSimpleStock {
	
	public static int DIVISIONSCALE = 3;

	private HashMap<String, Stock> stockMap;
	private HashMap<String, List<Trade>> tradeMap; //map of sorted list by stock

	public SuperSimpleStock(SSSModel model) throws Exception {
		stockMap = new HashMap<>();
		tradeMap = new HashMap<>();
		for (Stock stock : model.getStocks()) {
			stockMap.put(stock.getStockSymbol(), stock);
			tradeMap.put(stock.getStockSymbol(), new ArrayList<>());
		}

		for (Trade trade : model.getTrades()) { //dividing trades to list by it's stock
			if (stockMap.containsKey(trade.getStockSymbol())) { //a bit of error handling
				tradeMap.get(trade.getStockSymbol()).add(trade);
			} else {
				throw new Exception("Trade uses stockSymbol which is not in stocks.");
			}
		} 

		for (List<Trade> trades : tradeMap.values()) { //sorting trade lists by price
			Collections.sort(trades, new Comparator<Trade>() {
				@Override
				public int compare(Trade o1, Trade o2) {
					return -o1.getTimestamp().compareTo(o2.getTimestamp()); //descenting - newest will be first
				}
			});
		}
	}
	
	public BigDecimal getStockPrice(String stockSymbol) throws Exception {
		if(this.stockMap.containsKey(stockSymbol)) {
			List<Trade> tradeList = tradeMap.get(stockSymbol);
			Date fifteenMinsBack = new Date(System.currentTimeMillis()-(15*60*1000));
			int index = 0;
			int priceSum = 0;
			int quantitySum = 0;
			Trade trade = tradeList.get(0);
			while(trade.getTimestamp().compareTo(fifteenMinsBack)>0) {
				priceSum+=trade.getTickerPrice()*trade.getQuantity();
				quantitySum+=trade.getQuantity();
				index++;
				trade = tradeList.get(index);
			}
			if (quantitySum == 0) {
				throw new Exception("No trade in last 15 minutes for stock: "+stockSymbol);
			} else {
				return BigDecimal.valueOf(priceSum).divide(BigDecimal.valueOf(quantitySum), DIVISIONSCALE, RoundingMode.HALF_UP);
			}
		} else {
			throw new Exception("Unknown stock: "+stockSymbol);
		}
	}
	
	public double getGBCEAllShareIndex() throws Exception {
		BigDecimal tmp = BigDecimal.valueOf(1);
		for(String stockSymbol : stockMap.keySet()) {
			tmp=tmp.multiply(this.getStockPrice(stockSymbol));
		}
		return Math.pow(tmp.doubleValue(),1d/stockMap.keySet().size());
	}

	public BigDecimal getDividentYield(String stockSymbol) throws Exception{
		if(!this.stockMap.containsKey(stockSymbol)) {
			throw new Exception("No such Stock: "+stockSymbol);
		}
		if(this.tradeMap.get(stockSymbol).isEmpty()) {
			throw new Exception("No trade for stock "+stockSymbol+". Can't get tickerPrice.");
		}
		Stock stock = this.stockMap.get(stockSymbol);
		int tickerPrice = this.tradeMap.get(stockSymbol).get(0).getTickerPrice();
		switch (stock.getType()) {
			case PREFFERED:
				return BigDecimal.valueOf(stock.getFixedDividentPercentage())
						.multiply(BigDecimal.valueOf(stock.getParValue())
						.divide(BigDecimal.valueOf(100)))
						.divide(BigDecimal.valueOf(tickerPrice), DIVISIONSCALE, RoundingMode.HALF_UP);
			case COMMON:
			default:
				return BigDecimal.valueOf(stock.getLastDivident()).divide(BigDecimal.valueOf(tickerPrice), DIVISIONSCALE, RoundingMode.HALF_UP);
		}
	}

	public BigDecimal getPERatio(String stockSymbol) throws Exception{
		if(!this.stockMap.containsKey(stockSymbol)) {
			throw new Exception("No such Stock: "+stockSymbol);
		}
		if(this.tradeMap.get(stockSymbol).isEmpty()) {
			throw new Exception("No trade for stock "+stockSymbol+". Can't get tickerPrice.");
		}
		Stock stock = this.stockMap.get(stockSymbol);
		if(stock.getLastDivident()==0) {
			return BigDecimal.valueOf(-1);
		}
		int tickerPrice = this.tradeMap.get(stockSymbol).get(0).getTickerPrice();
		return BigDecimal.valueOf(tickerPrice).divide(BigDecimal.valueOf(stock.getLastDivident()), DIVISIONSCALE, RoundingMode.HALF_UP);
	}
	
	public void addTrade(Trade trade) throws Exception{
		if(!this.stockMap.containsKey(trade.getStockSymbol())) {
			throw new Exception("No such Stock: "+trade.getStockSymbol());
		}
		List<Trade> list = this.tradeMap.get(trade.getStockSymbol()) ;
		if(list.isEmpty()) {
			list.add(trade);
		} else {
			int index = 0;
			while(list.get(index).getTimestamp().compareTo(trade.getTimestamp())>0) {
				index++;
			}
			list.add(index, trade);
		}
	}
}
