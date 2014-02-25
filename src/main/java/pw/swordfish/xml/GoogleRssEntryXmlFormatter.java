package pw.swordfish.xml;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;
import pw.swordfish.GoogleRssEntry;
import org.w3c.dom.Node;

public class GoogleRssEntryXmlFormatter extends XmlFormatter<GoogleRssEntry> {

	// date format
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GoogleRssEntry parse(Node node) throws ParseException, XPathException {
		GoogleRssEntry entry = new GoogleRssEntry();
		entry.setTitle(this.evaluate("title", node));
		entry.setLink(this.evaluate("link/@href", node));
		entry.setPublished(this.evaluateDate("published", node));
		entry.setUpdated(this.evaluateDate("updated", node));
		return entry;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPath() {
		return "feed/entry";
	}

	private Date evaluateDate(String updated, Node node) throws ParseException, XPathExpressionException {
		return this.dateFormat.parse(this.evaluate(updated, node).replaceAll("[^0-9^\\:^\\-]", ""));
	}
	
}
