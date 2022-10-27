package es.uniovi.pulso.colmena.persistence.dao;

/**
 * Interface used to provide the DAO object to system
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 *
 */
public interface DAOFactory {
	/**
	 * Returns a User DAO
	 * @return the ColmenaUserDAO object
	 */
	public ColmenaUserDAO getColmenaUserDAO();

	/**
	 * Returns a ColmenaMarkerDAO 
	 * @return the ColmenaMarkerDAO object
	 */
	public ColmenaMarkerDAO getColmenaMarkerDAO();
	
	/**
	 * Returns a ColmenCompilationDAO
	 * @return the ColmenaCompilationDAO object
	 */
	public ColmenaCompilationDAO getColmenaCompilationDAO();

}
