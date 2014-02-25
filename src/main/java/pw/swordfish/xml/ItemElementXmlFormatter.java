package pw.swordfish.xml;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;
import pw.swordfish.ItemElement;
import org.w3c.dom.Node;

/**
 * Formatter for item elements
 * @author brandon
 */
public class ItemElementXmlFormatter extends XmlFormatter<ItemElement> {
	private final String path;
	public ItemElementXmlFormatter() {
		this("query/results/item");
	}

	public ItemElementXmlFormatter(String path) {
		this.path = path;
	}
	
	private DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss zzz");

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ItemElement parse(Node node) throws ParseException, XPathException {
		ItemElement itemElement = new ItemElement();

		itemElement.setTitle(this.evaluate("title", node));
		itemElement.setDescription(this.evaluate("description", node));
		itemElement.setGuid(this.evaluate("guid", node));
		// itemElement.setPubDate(this.evaluateDate("pubDate", node));
		itemElement.setLink(this.evaluate("link", node));

		return itemElement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPath() {
		return this.path;
	}

	private Date evaluateDate(String pubDate, Node node) throws ParseException, XPathExpressionException {
		return dateFormat.parse(this.evaluate(pubDate, node));
	}
	
}
