package cz.olaf.supersimplestocks.test;

import cz.olaf.supersimplestocks.SSSModel;
import cz.olaf.supersimplestocks.Stock;
import cz.olaf.supersimplestocks.StockType;
import cz.olaf.supersimplestocks.SuperSimpleStock;
import cz.olaf.supersimplestocks.Trade;
import cz.olaf.supersimplestocks.TradeType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Martin Konir
 */
public class Test1 {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			Set<Stock> stocks = createSampleStocks();
			List<Trade> trades = createOldTrades(stocks);
			SuperSimpleStock sss = new SuperSimpleStock(new SSSModel(stocks, trades));
			List<Trade> newTrades = createCurrentTrades(stocks);
			for(Trade t : newTrades) {
				sss.addTrade(t);
			}
			
			System.out.println("GBCEAllShareIndex: "+sss.getGBCEAllShareIndex());
			for(Stock stock : stocks) {
				System.out.println("Divident Yield for "
						+stock.getStockSymbol()
						+": "
						+sss.getDividentYield(stock.getStockSymbol()));
				System.out.println("PERatio for "
						+stock.getStockSymbol()
						+": "
						+sss.getPERatio(stock.getStockSymbol()));
				System.out.println("StockPrice for "
						+stock.getStockSymbol()
						+": "
						+sss.getStockPrice(stock.getStockSymbol()));
			}
			
		} catch (Exception e) {
			System.out.println("something unexpected happened:");
			e.printStackTrace();	
		}
	}
	
	private static Set<Stock> createSampleStocks() {
		HashSet<Stock> stocks = new HashSet<>();
		stocks.add(new Stock("TEA", StockType.COMMON, 0, 0, 100));
		stocks.add(new Stock("POP", StockType.COMMON, 8, 0, 100));
		stocks.add(new Stock("ALE", StockType.COMMON, 23, 0, 60));
		stocks.add(new Stock("GIN", StockType.PREFFERED, 8, 2, 100));
		stocks.add(new Stock("JOE", StockType.COMMON, 13, 0, 250));
		return stocks;
	}
	
	private static List<Trade> createOldTrades(Set<Stock> stocks) {
		List<Trade> trades = new ArrayList<Trade>();
		int modifier = 0;
		for(Stock stock : stocks) {
			for(int i=0; i<10; i++) {
				trades.add(new Trade(stock.getStockSymbol(),
						(i*15)%20,
						500+((i*15)%20)+modifier,
						new Date(System.currentTimeMillis()-20*60*1000),
						TradeType.BUY));
			}
			modifier+=100;
		}
		return trades;
	}
	
	private static List<Trade> createCurrentTrades(Set<Stock> stocks) {
		List<Trade> trades = new ArrayList<Trade>();
		int modifier = 0;
		for(Stock stock : stocks) {
			for(int i=0; i<10; i++) {
				trades.add(new Trade(stock.getStockSymbol(),
						(i*15)%20,
						500+((i*15)%20+modifier),
						new Date(System.currentTimeMillis()),
						TradeType.BUY));
			}
			modifier+=100;
		}
		return trades;
	}
	
}
