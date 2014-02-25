package pw.swordfish.xml;

import java.text.ParseException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;

/**
 * @author brandon
 */
public abstract class XmlFormatter<T> {
	// the xpath for formatting xml
	private static final XPath path = XPathFactory.newInstance().newXPath();

	/**
	 * Parses the specified node
	 * @param node the node to parse
	 * @return the object
	 */
	public abstract T parse(Node node) throws ParseException, XPathException;

	/**
	 * The path we expect to obtain the object from
	 * @return the path root
	 */
	public abstract String getPath();

	/**
	 * Evaluates the node as a string
	 * @param string the path in the node
	 * @param node the node to parse
	 * @return the string value of the node
	 * @throws XPathExpressionException 
	 */
	protected String evaluate(String string, Node node) throws XPathExpressionException {
		String result = (String)path.evaluate(string, node, XPathConstants.STRING);
		return result;
	}		

	/**
	 * Evaluates the node as a double
	 * @param string the path in the node
	 * @param node the node to parse
	 * @return the double value of the node
	 * @throws XPathExpressionException 
	 */
	protected double evaluateDouble(String string, Node node) throws XPathExpressionException {
		return (double)path.evaluate(string, node, XPathConstants.NUMBER);
	}	

	/**
	 * Evaluates the node as an int
	 * @param string the path in the node
	 * @param node the node to parse
	 * @return the int value of the node
	 * @throws XPathExpressionException 
	 */
	protected int evaluateInt(String string, Node node) throws XPathExpressionException {
		return Integer.valueOf(this.evaluate(string, node));
	}

	/**
	 * Evaluates the node as a long 
	 * @param string the path in the node
	 * @param node the node to parse
	 * @return the long value of the node
	 * @throws XPathExpressionException 
	 */
	protected long evaluateLong(String string, Node node) throws XPathExpressionException {
		return Long.valueOf(this.evaluate(string, node));
	}
}
