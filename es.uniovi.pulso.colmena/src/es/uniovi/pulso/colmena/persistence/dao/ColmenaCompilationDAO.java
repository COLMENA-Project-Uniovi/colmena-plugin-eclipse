package es.uniovi.pulso.colmena.persistence.dao;

import es.uniovi.pulso.colmena.model.ColmenaCompilation;

/**
 * DAO that represents the type of Compilation DAO in db.
 * 
 * @author Julia Vallina García, University of Oviedo
 * 
 */
public interface ColmenaCompilationDAO {
	/**
	 * Insert a ColmenaCompilation in a db
	 * 
	 * @param compilation
	 *            the compilation
	 */
	public void insertColmenaCompilation(ColmenaCompilation ccompilation);

}
