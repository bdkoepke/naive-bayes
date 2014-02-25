package pw.swordfish.predicate;

/**
 * @author brandon
 */
public abstract class Predicate {
	/**
	 * Gets the query for the specification
	 * @return The string representing this specification
	 */
	public abstract StringBuilder getQuery();

	/**
	 * Creates a new specification that is the AND operation of
	 * {@code this} specification and another
	 * @param specification Specification to AND.
	 * @return A new specification
	 */
	public Predicate and(Predicate specification) {
		return new AndPredicate(this, specification);
	}

	/**
	 * Creates a new specification that is the OR operation of
	 * {@code this} specification and another
	 * @param specification Specification to OR.
	 * @return A new specification
	 */
	public Predicate or(Predicate specification) {
		return new OrPredicate(this, specification);
	}
}
