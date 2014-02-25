package pw.swordfish.predicate;

/**
 * @author brandon
 */
public class UrlPredicate extends Predicate {
	// the url to specify
	private final String url;

	/**
	 * Specification for url's
	 * @param url the url to select
	 */
	public UrlPredicate(String url) {
		this.url = url;
	}	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StringBuilder getQuery() {
		StringBuilder query = new StringBuilder();

		query.append("url=\"").append(this.url).append('"');

		return query;
	}
	
}
