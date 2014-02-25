package pw.swordfish;

/**
 * @author brandon
 */
public class FormatException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Thrown when the object cannot be formatted properly
	 * @param message the message to send
	 */
	public FormatException(String message) {
		super(message);
	}
	
}
