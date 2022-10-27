package es.uniovi.pulso.colmena.persistence.jdbc;

import es.uniovi.pulso.colmena.persistence.dao.ColmenaCompilationDAO;
import es.uniovi.pulso.colmena.persistence.dao.ColmenaMarkerDAO;
import es.uniovi.pulso.colmena.persistence.dao.ColmenaUserDAO;
import es.uniovi.pulso.colmena.persistence.dao.DAOFactory;

/**
 * Implementation for DAOFactory in JDBC
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class JdbcDAOFactory implements DAOFactory {

	@Override
	public ColmenaUserDAO getColmenaUserDAO() {
		return new ColmenaUserJdbcDAO();
	}

	@Override
	public ColmenaMarkerDAO getColmenaMarkerDAO() {
		return new ColmenaMarkerJdbcDAO();
	}

	@Override
	public ColmenaCompilationDAO getColmenaCompilationDAO() {
		return new ColmenaCompilationJdbcDAO();
	}


}
