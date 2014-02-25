package pw.swordfish.yql;

import java.io.InputStream;
import pw.swordfish.FormatException;

/**
 * @author brandon
 */
public interface YqlFormatter<T> {
	/**
	 * Gets the format string to be appended to the query
	 * @return the format string
	 */
	public String getFormat();

	/**
	 * Gets an entity from the stream
	 * @param result the result to process by the yql formatter
	 * @return the entity
	 */
	public T getEntity(InputStream result) throws FormatException;

	/**
	 * Gets entities from the stream
	 * @param result the result to process with the yql formatter
	 * @return the entities
	 */
	public Iterable<T> getEntities(InputStream result) throws FormatException;
}
