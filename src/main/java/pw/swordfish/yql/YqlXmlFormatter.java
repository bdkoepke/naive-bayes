package pw.swordfish.yql;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathFactory;
import pw.swordfish.FormatException;
import pw.swordfish.xml.XmlFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class YqlXmlFormatter<T> implements YqlFormatter<T> {
	private final XmlFormatter<T> formatter;

	private static final Logger logger = Logger.getLogger(YqlXmlFormatter.class.getName());

	private static final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	private final DocumentBuilder documentBuilder;

	private static final XPath path = XPathFactory.newInstance().newXPath();

	public YqlXmlFormatter(XmlFormatter<T> formatter) throws ParserConfigurationException {
		this.formatter = formatter;
		this.documentBuilder = documentBuilderFactory.newDocumentBuilder();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFormat() {
		return "&format=xml";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getEntity(InputStream result) throws FormatException {
		List<T> entities = this.getEntitiesAsList(result);
		int size = entities.size();

		if (size > 1) {
			logger.log(Level.WARNING, "getEntity returned {0} entities", size);
		}

		if (size < 1) {
			logger.log(Level.SEVERE, "getEntity returned 0 entities");
			return null;
		}

		return entities.get(0);
	}

	/**
	 * Gets the entities as a list
	 * @param result the stream to read the entities from
	 * @return a list of the entities
	 */
	private List<T> getEntitiesAsList(InputStream result) throws FormatException {
		
		NodeList nodes;

		List<T> entities = new ArrayList<>();
			
		try {
			Document response = documentBuilder.parse(result);
			nodes = (NodeList)path.evaluate(this.formatter.getPath(), response, XPathConstants.NODESET);

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				T obj = this.formatter.parse(node);
				
				entities.add(obj);	
			}
			
		} catch (XPathException | ParseException | SAXException | IOException ex) {
			logger.log(Level.SEVERE, null, ex);
			throw new FormatException(ex.getMessage());
		}

		return entities;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterable<T> getEntities(InputStream result) throws FormatException {
		return this.getEntitiesAsList(result);
	}
}
