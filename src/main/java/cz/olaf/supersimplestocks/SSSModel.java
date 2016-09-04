package cz.olaf.supersimplestocks;

import java.util.List;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Martin Konir
 */
@XmlRootElement
public class SSSModel {
	private Set<Stock> stocks;
	private List<Trade> trades;

	public SSSModel(Set<Stock> stocks, List<Trade> trades) {
		this.stocks = stocks;
		this.trades = trades;
	}

	public Set<Stock> getStocks() {
		return stocks;
	}

	public List<Trade> getTrades() {
		return trades;
	}
	
}
