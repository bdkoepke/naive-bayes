package pw.swordfish;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import pw.swordfish.xml.ItemElementXmlFormatter;
import pw.swordfish.yql.YqlFormatter;
import pw.swordfish.yql.YqlXmlFormatter;

/**
 * @author brandon
 */
public class FiveFilters {
	private final HttpClient httpClient;

	private final String queryString = "http://ftr.fivefilters.org/makefulltextfeed.php?url=";
	private final YqlFormatter<ItemElement> yqlFormatter;

	public FiveFilters() throws ParserConfigurationException {
		this(new YqlXmlFormatter<>(new ItemElementXmlFormatter("rss/channel/item")));
	}
	
	public FiveFilters(YqlFormatter<ItemElement> yqlFormatter) {
		this(yqlFormatter,new HttpClient());
	}

	public FiveFilters(YqlFormatter<ItemElement> yqlFormatter, HttpClient httpClient) {
		this.httpClient = httpClient;
		this.yqlFormatter = yqlFormatter;
	}

	public String parse(String url) throws IOException, FormatException {
		GetMethod method = new GetMethod(queryString + url);
		this.httpClient.executeMethod(method);

		return this.yqlFormatter.getEntity(method.getResponseBodyAsStream())
				.getDescription().replaceAll("\\<.*?\\>", "");
	}
	
}
