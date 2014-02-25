package pw.swordfish.query;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import pw.swordfish.FormatException;
import pw.swordfish.predicate.Predicate;

public class LoggingQueryable<T> implements Queryable<T> {
	private final Queryable<T> queryable;

	public LoggingQueryable(Queryable<T> queryable) throws UnsupportedEncodingException {
		this.queryable = queryable;
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public Iterable<T> where(Predicate specification) throws IOException, FormatException {
		return this.where(this.getQueryString(specification));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getQueryString(Predicate predicate) throws UnsupportedEncodingException {
		return this.queryable.getQueryString(predicate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterable<T> where(String query) throws IOException, FormatException {
		System.out.println(query);

		return this.queryable.where(query);
	}
	
}
