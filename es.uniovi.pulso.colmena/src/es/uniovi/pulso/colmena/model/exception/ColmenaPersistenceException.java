package es.uniovi.pulso.colmena.model.exception;

/**
 *  Class that represents the custom Exception of the project for persistence problems
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 *
 */
public class ColmenaPersistenceException extends ColmenaAbstractException {

	private static final long serialVersionUID = -8104378233105166097L;

	/**
	 * A general constructor based in a String message
	 * 
	 * @param message
	 *            the message of the problem or error.
	 */
	public ColmenaPersistenceException(String message) {
		super(message);
	}
}
