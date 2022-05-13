package es.uniovi.pulso.colmena.persistence.dao;

import es.uniovi.pulso.colmena.model.ColmenaUser;

/**
 * 
 * Interface that represents the DAO for the users in database
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public interface ColmenaUserDAO {
	/**
	 * Find a user using the unique id
	 * 
	 * @param id
	 *            the identifier
	 * @return the User with this id
	 */
	public ColmenaUser findUserbyId(String id);

	/**
	 * Check if exists one user
	 * 
	 * @param user
	 *            the user to check
	 * @return true if the user exists and false if not.
	 */
	public boolean existsUser(ColmenaUser user);

	/**
	 * Insert a user in the db
	 * 
	 * @param user
	 *            the user wich will be inserted
	 */
	public void insertUser(ColmenaUser user);

}
