package pw.swordfish.yql;

import pw.swordfish.predicate.Predicate;

/**
 * @author brandon
 */
class AdhocPredicate extends Predicate {
	private final String predicate;

	public AdhocPredicate(String predicate) {
		this.predicate = predicate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StringBuilder getQuery() {
		return new StringBuilder(this.predicate);
	}
	
}
