package pw.swordfish.xml;

import java.text.ParseException;
import javax.xml.xpath.XPathException;
import pw.swordfish.StockQuote;
import org.w3c.dom.Node;

/**
 * @author brandon
 */
public class StockQuoteXmlFormatter extends XmlFormatter<StockQuote> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public StockQuote parse(Node node) throws ParseException, XPathException {
		StockQuote stockQuote = new StockQuote();
		stockQuote.setAsk(this.evaluateDouble("Ask", node));
		stockQuote.setBid(this.evaluateDouble("Bid", node));
		stockQuote.setSymbol(this.evaluate("Symbol", node));
		stockQuote.setVolume(this.evaluateInt("Volume", node));
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
