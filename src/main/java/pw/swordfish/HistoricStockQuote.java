package pw.swordfish;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author brandon
 */
public class HistoricStockQuote {

	// the format of the date returned by yql
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	// private backing fields
	private Date date;
	private double open;
	private double high;
	private double low;
	private double close;
	private long volume;
	private double adjustedClose;

	/**
	 * Get the value of date
	 *
	 * @return the value of date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * Set the value of date
	 *
	 * @param date new value of date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public void setDate(String date) throws ParseException {
		this.date = dateFormat.parse(date);
	}

	/**
	 * Get the value of open
	 *
	 * @return the value of open
	 */
	public double getOpen() {
		return open;
	}

	/**
	 * Set the value of open
	 *
	 * @param open new value of open
	 */
	public void setOpen(double open) {
		this.open = open;
	}

	/**
	 * Get the value of high
	 *
	 * @return the value of high
	 */
	public double getHigh() {
		return high;
	}

	/**
	 * Get the value of low
	 *
	 * @return the value of low
	 */
	public double getLow() {
		return low;
	}

	/**
	 * Set the value of low
	 *
	 * @param low new value of low
	 */
	public void setLow(double low) {
		this.low = low;
	}

	/**
	 * Set the value of high
	 *
	 * @param high new value of high
	 */
	public void setHigh(double high) {
		this.high = high;
	}

	/**
	 * Get the value of close
	 *
	 * @return the value of close
	 */
	public double getClose() {
		return close;
	}

	/**
	 * Set the value of close
	 *
	 * @param close new value of close
	 */
	public void setClose(double close) {
		this.close = close;
	}

	/**
	 * Get the value of volume
	 *
	 * @return the value of volume
	 */
	public long getVolume() {
		return volume;
	}

	/**
	 * Set the value of volume
	 *
	 * @param volume new value of volume
	 */
	public void setVolume(long volume) {
		this.volume = volume;
	}

	/**
	 * Get the value of adjustedClose
	 *
	 * @return the value of adjustedClose
	 */
	public double getAdjustedClose() {
		return adjustedClose;
	}

	/**
	 * Set the value of adjustedClose
	 *
	 * @param adjustedClose new value of adjustedClose
	 */
	public void setAdjustedClose(double adjustedClose) {
		this.adjustedClose = adjustedClose;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
