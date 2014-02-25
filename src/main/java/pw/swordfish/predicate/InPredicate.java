package pw.swordfish.predicate;

/**
 * @author brandon
 */
public class InPredicate extends Predicate {
	// the items we are selecting in
	private final String[] items;

	// where we are selecing the items from
	private final String predicate;

	/**
	 * Creates a new IN specification
	 * @param predicate the predicate for the specification
	 * @param items the items to select
	 */
	public InPredicate(String predicate, String... items) {
		if (items.length < 1) {
			throw new IllegalArgumentException();
		}
		this.items = items;
		this.predicate = predicate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StringBuilder getQuery() {
		StringBuilder query = new StringBuilder();

		query.append(this.predicate);
		query.append(" in (\"");

		int size = this.items.length;

		query.append(this.items[0]).append('"');

		if (size > 1) {
			for (int index = 1; index < size; index++) {
				query.append(",\"").append(this.items[index]).append('"');
			}
		}

		query.append(')');

		return query;
	}
	
}
