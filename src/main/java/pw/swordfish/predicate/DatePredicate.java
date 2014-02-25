package pw.swordfish.predicate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import pw.swordfish.DateType;


public class DatePredicate extends Predicate {
	// the actual date value
	private final Date date;

	// the type of date
	private final DateType dateType;

	// date formatter
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Create a new date specification
	 * @param date the date value
	 * @param dateType the date type
	 */
	public DatePredicate(Date date, DateType dateType) {
		this.date = date;
		this.dateType = dateType;
	}
	
	/**
	 * Create a new start date specification
	 * @param date the date value
	 */
	public DatePredicate(Date date) {
		this(date, DateType.Start);
	}

	/**
	 * Creates a new date specification
	 * @param date the date value
	 * @param dateType the date type
	 */
	public DatePredicate(String date, DateType dateType) throws ParseException {
		this(dateFormat.parse(date), dateType);
	}

	/**
	 * Creates a new date specification
	 * @param date the date value
	 * @throws ParseException thrown when the date cannot be parsed
	 */
	public DatePredicate(String date) throws ParseException {
		this(dateFormat.parse(date));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StringBuilder getQuery() {
		StringBuilder query = new StringBuilder();
		query.append(this.dateType.name().toLowerCase());
		query.append("Date=\"");
		query.append(dateFormat.format(this.date));
		query.append("\"");

		return query;
	}
}
