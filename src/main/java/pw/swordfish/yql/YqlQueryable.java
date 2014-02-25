package pw.swordfish.yql;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import pw.swordfish.FormatException;
import pw.swordfish.predicate.Predicate;
import pw.swordfish.query.Queryable;

/**
 * @author brandon
 * @param <T>
 */
public class YqlQueryable<T> implements Queryable<T> {
	// the request string
	private final String request = "http://query.yahooapis.com/v1/public/yql?q=";

	// the select query
	private final String select = "select *";

	// TODO: remove this...
	private final String options;

	// the from string
	private final String from;

	// the yql formatter for this type
	private final YqlFormatter<T> format;

	// the http client for running queries
	private final HttpClient httpClient;

	// the logger for the yql repository
	private static final Logger logger = Logger.getLogger(YqlQueryable.class.getName());

	/**
	 * Creates a new Yahoo query repository
	 * @param httpClient the client to use for querying Yahoo
	 */
	public YqlQueryable(String from, String options, YqlFormatter<T> format, HttpClient httpClient) {
		this.format = format;
		this.httpClient = httpClient;
		this.from = from;
		this.options = options;
	}

	/**
	 * Creates a new Yahoo query repository
	 */
	public YqlQueryable(String from, String options, YqlFormatter<T> format) {
		this(from, options, format, new HttpClient());
	}

	/**
	 * Creates a new yql queryable
	 * @param from
	 * @param format 
	 */
	public YqlQueryable(String from, YqlFormatter<T> format) {
		this(from, "", format);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getQueryString(Predicate predicate) throws UnsupportedEncodingException {
		StringBuilder queryBuilder = new StringBuilder();

		queryBuilder.append(this.select)
				.append(" from ").append(this.from)
				.append(" where ").append(predicate.getQuery().toString());

		StringBuilder urlBuilder = new StringBuilder();
		
		urlBuilder.append(this.request)
				.append(URLEncoder.encode(queryBuilder.toString(), "UTF-8").replace("+", "%20"))
				.append(this.format.getFormat())
				.append(this.options);

		return urlBuilder.toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterable<T> where(Predicate predicate) throws IOException, FormatException {
		return this.where(this.getQueryString(predicate));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterable<T> where(String query) throws IOException, FormatException {
		GetMethod method = new GetMethod(query);
		if (this.httpClient.executeMethod(method) != HttpStatus.SC_OK) {
			String message = String.format("Method failed: {0}", method.getStatusLine());
			logger.log(Level.SEVERE, message);
			throw new IOException(message);
		}

		byte[] array = method.getResponseBody();

		InputStream response = new ByteArrayInputStream(array);

		return this.format.getEntities(response);		
	}
}
