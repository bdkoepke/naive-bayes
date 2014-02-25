package pw.swordfish;

/**
 * @author brandon
 */
public class StockQuote {

	// backing fields
	private double ask;
	private double bid;
	private long volume;
	private String symbol;

	/**
	 * Get the value of ask
	 *
	 * @return the value of ask
	 */
	public double getAsk() {
		return ask;
	}

	/**
	 * Set the value of ask
	 *
	 * @param string new value of ask
	 */
	public void setAsk(double ask) {
		this.ask = ask;
	}

	/**
	 * Get the value of bid
	 *
	 * @return the value of bid
	 */
	public double getBid() {
		return bid;
	}

	/**
	 * Set the value of bid
	 *
	 * @param string new value of bid
	 */
	public void setBid(double bid) {
		this.bid = bid;
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
	 * @param string new value of volume
	 */
	public void setVolume(long volume) {
		this.volume = volume;
	}

	/**
	 * Get the value of symbol
	 * @return the value of symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Set the value of symbol
	 * @param symbol the new value of symbol
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
