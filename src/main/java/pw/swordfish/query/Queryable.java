package pw.swordfish.query;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import pw.swordfish.FormatException;
import pw.swordfish.predicate.Predicate;

/**
 * @author brandon
 */
public interface Queryable<T> {
	public String getQueryString(Predicate predicate) throws UnsupportedEncodingException;

	public Iterable<T> where(String query) throws IOException, FormatException;
	
	/**
	 * Finds all items that match the specification
	 * @param specification the specification to find all items for
	 * @return an {@code Iterable} list of the matching items
	 */
	public Iterable<T> where(Predicate specification) throws IOException, FormatException;
}
