package pw.swordfish.xml;

import java.text.ParseException;
import javax.xml.xpath.XPathException;
import org.w3c.dom.Node;

/**
 * @author brandon
 */
public class QueryExceptionXmlFormatter extends XmlFormatter<QueryException> {

	@Override
	public QueryException parse(Node node) throws ParseException, XPathException {
		return new QueryException(this.evaluate("description", node));
	}

	@Override
	public String getPath() {
		return "query/error";
	}
		
}
