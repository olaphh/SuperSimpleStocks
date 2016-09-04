package cz.olaf.supersimplestocks;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Martin Konir
 */

public class Stock {
	private String stockSymbol;
	private StockType type;
	private int lastDivident;
	private int fixedDividentPercentage;
	private int parValue;

	public Stock(String stockSymbol, StockType type, int lastDivident, int fixedDividentPercentage, int parValue) {
		this.stockSymbol = stockSymbol;
		this.type = type;
		this.lastDivident = lastDivident;
		this.fixedDividentPercentage = fixedDividentPercentage;
		this.parValue = parValue;
	}
	
	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public StockType getType() {
		return type;
	}

	public void setType(StockType type) {
		this.type = type;
	}

	public int getLastDivident() {
		return lastDivident;
	}

	public void setLastDivident(int lastDivident) {
		this.lastDivident = lastDivident;
	}

	public int getFixedDividentPercentage() {
		return fixedDividentPercentage;
	}

	public void setFixedDividentPercentage(int fixedDividentPercentage) {
		this.fixedDividentPercentage = fixedDividentPercentage;
	}

	public int getParValue() {
		return parValue;
	}

	public void setParValue(int parValue) {
		this.parValue = parValue;
	}

	@Override
	public String toString() {
		return "Stock{" + "stockSymbol=" + stockSymbol + ", type=" + type + ", lastDivident=" + lastDivident + ", fixedDividentPercentage=" + fixedDividentPercentage + ", parValue=" + parValue + '}';
	}
}
