package pw.swordfish.yql;

import javax.xml.parsers.ParserConfigurationException;
import pw.swordfish.HistoricStockQuote;
import pw.swordfish.ItemElement;
import pw.swordfish.StockQuote;
import pw.swordfish.query.Queryable;
import pw.swordfish.xml.HistoricStockQuoteXmlFormatter;
import pw.swordfish.xml.ItemElementXmlFormatter;
import pw.swordfish.xml.StockQuoteXmlFormatter;

/**
 * @author brandon
 */
public class QueryFactory {
	/**
	 * Creates a new historic stock quote queryable
	 * @return the stock quote queryable
	 */
	public Queryable<HistoricStockQuote> createHistoricStockQuoteQueryable() throws ParserConfigurationException {
		return new YqlQueryable<>(
				"yahoo.finance.historicaldata",
				"&env=http://datatables.org/alltables.env",
				new YqlXmlFormatter<>(
				new HistoricStockQuoteXmlFormatter()));
	}

	/**
	 * Creates a new stock quote queryable
	 * @return the stock quote queryable
	 * @throws ParserConfigurationException 
	 */
	public Queryable<StockQuote> createStockQuoteQueryable() throws ParserConfigurationException {
		return new YqlQueryable<>(
				"yahoo.finance.quotes",
				new YqlXmlFormatter<>(
				new StockQuoteXmlFormatter()))		;
	}

	/**
	 * Creates a new query for item elements
	 * @return the item element queryable
	 * @throws ParserConfigurationException 
	 */
	public Queryable<ItemElement> createItemElementQueryable() throws ParserConfigurationException {
		return new YqlQueryable<>(
				"rss",
				new YqlXmlFormatter<>(
				new ItemElementXmlFormatter()));
	}
}
