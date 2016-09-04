package cz.olaf.supersimplestocks;

import java.util.Date;

/**
 *
 * @author Martin Konir
 */
public class Trade {
	private String stockSymbol;
	private int quantity;
	private int tickerPrice;
	private Date timestamp;
	private TradeType type;

	public Trade(String stockSymbol, int quantity, int tickerPrice, Date timestamp, TradeType type) {
		this.stockSymbol = stockSymbol;
		this.quantity = quantity;
		this.tickerPrice = tickerPrice;
		this.timestamp = timestamp;
		this.type = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTickerPrice() {
		return tickerPrice;
	}

	public void setTickerPrice(int tickerPrice) {
		this.tickerPrice = tickerPrice;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public TradeType getType() {
		return type;
	}

	public void setType(TradeType type) {
		this.type = type;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}	

	@Override
	public String toString() {
		return "Trade{" + "stockSymbol=" + stockSymbol + ", quantity=" + quantity + ", tickerPrice=" + tickerPrice + ", timestamp=" + timestamp + ", type=" + type + '}';
	}
}
