package pw.swordfish.xml;

import java.text.ParseException;
import javax.xml.xpath.XPathException;
import pw.swordfish.HistoricStockQuote;
import org.w3c.dom.Node;

/**
 * @author brandon
 */
public class HistoricStockQuoteXmlFormatter extends XmlFormatter<HistoricStockQuote> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoricStockQuote parse(Node node) throws ParseException, XPathException {
		HistoricStockQuote stockQuote = new HistoricStockQuote();
		stockQuote.setDate(this.evaluate("Date", node));
		stockQuote.setOpen(this.evaluateDouble("Open", node));
		stockQuote.setHigh(this.evaluateDouble("High", node));
		stockQuote.setLow(this.evaluateDouble("Low", node));
		stockQuote.setClose(this.evaluateDouble("Close", node));
		stockQuote.setVolume(this.evaluateLong("Volume", node));
		stockQuote.setAdjustedClose(this.evaluateDouble("Adj_Close", node));
		return stockQuote;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPath() {
		return "query/results/quote";
	}
	
}
