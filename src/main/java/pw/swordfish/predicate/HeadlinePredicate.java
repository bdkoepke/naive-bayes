package pw.swordfish.predicate;

import org.apache.commons.lang.StringUtils;


public class HeadlinePredicate extends UrlPredicate {

	/**
	 * Gets a properly formatted headline url
	 * @param symbols the symbols we want
	 * @return the properly formatted url
	 */
	private static String getUrl(String... symbols) {
		return "http://finance.yahoo.com/rss/headline?s=" + StringUtils.join(symbols, ',');

	}

	/**
	 * Creates a new headline specification
	 * @param symbols the symbols to fetch the headline for
	 */
	public HeadlinePredicate(String... symbols) {
		super(getUrl(symbols));
	}
	
}
