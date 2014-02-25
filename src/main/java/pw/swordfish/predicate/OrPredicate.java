package pw.swordfish.predicate;

/**
 * @author brandon
 */
public class OrPredicate extends Predicate {
	// the first specification
	private Predicate first;

	// the second specification
	private Predicate second;

	/**
	 * Creates a new OR specification based on two other specifications
	 * @param first the first specification
	 * @param second the second specification
	 */
	public OrPredicate(final Predicate first, final Predicate second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StringBuilder getQuery() {
		StringBuilder query = new StringBuilder();
		// query.append('(');
		query.append(first.getQuery());
		query.append(" or ");
		query.append(second.getQuery());
		// query.append(')');
		return query;
	}
		
}
