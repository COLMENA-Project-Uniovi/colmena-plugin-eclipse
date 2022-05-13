package es.uniovi.pulso.colmena.model.exception;

public class ColmenaAbstractException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A general constructor based in a String message
	 * 
	 * @param message
	 *            the message of the problem or error.
	 */
	public ColmenaAbstractException(String message) {
		super(message);
	}
}
