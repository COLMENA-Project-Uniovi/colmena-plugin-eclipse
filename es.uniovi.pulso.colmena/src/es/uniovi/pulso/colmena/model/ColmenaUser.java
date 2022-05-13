package es.uniovi.pulso.colmena.model;

/**
 * Class that represents a user owner of errors and warnings.
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class ColmenaUser {
	// id
	private String id;
	/**
	 * Constructor of the class
	 * 
	 * @param name
	 *            The name of the user
	 */
	public ColmenaUser(String id) {
		this.id = id;
	}

	public ColmenaUser() {
	}

	// Getters + Setters + ToString

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		return id;
	}	

}
