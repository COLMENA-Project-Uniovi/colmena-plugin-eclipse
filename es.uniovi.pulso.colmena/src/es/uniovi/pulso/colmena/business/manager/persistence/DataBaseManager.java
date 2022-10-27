package es.uniovi.pulso.colmena.business.manager.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import es.uniovi.pulso.colmena.business.manager.preferences.PreferenceManager;
import es.uniovi.pulso.colmena.model.ColmenaCompilation;
import es.uniovi.pulso.colmena.model.ColmenaMarker;
import es.uniovi.pulso.colmena.model.ColmenaUser;
import es.uniovi.pulso.colmena.model.exception.ColmenaPersistenceException;
import es.uniovi.pulso.colmena.persistence.dao.DAOFactory;
import es.uniovi.pulso.colmena.persistence.jdbc.JdbcDAOFactory;

/**
 * Class that manages the persistence to data base
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class DataBaseManager {

	// the instance
	private static DataBaseManager instance;

	// manager
	private PreferenceManager pm;

	// values of connection
	private String databaseHostName;
	// private String databaseHostPort;
	private String databaseName;
	private String databaseUserName;
	private String databaseUserPass;

	// values of the tables
	private String compilationTableName;
	private String markerTableName;
	private String userTableName;
	private String errorTableName;

	// the connection
	private Connection connection;

	// the DAO factory
	private DAOFactory factory;

	private DataBaseManager() {
		try {
			// start the manager and factory of DAOS
			this.pm = new PreferenceManager();
			this.factory = new JdbcDAOFactory();

			// obtain the values of properties
			obtainValuesOfConnection();

			// obtain the values of properties
			obtainValuesOfTables();

			// open the connection
			openConnection();
		} catch (ColmenaPersistenceException cpe) {
			cpe.printStackTrace();
		}
	}


	/**
	 * Persist the markers
	 * 
	 * @param cmarkerList
	 *            the list of markers
	 */
	public void saveMarkers(List<ColmenaMarker> cmarkerList, String ccompilation_id) {
		for (ColmenaMarker c : cmarkerList) {
			factory.getColmenaMarkerDAO().insertColmenaMarker(c, ccompilation_id);

		}
	}

	/**
	 * Store the user in the data base
	 * 
	 * @param currentUser
	 */
	public void saveUser(ColmenaUser currentUser) {
		if (factory.getColmenaUserDAO().findUserbyId(currentUser.getId()) == null){			
			factory.getColmenaUserDAO().insertUser(currentUser);
		}
	}
	

	/**
	 * Store the type of compilation in the data base
	 * @param currentUser 
	 * 
	 * @param currentUser
	 */
	public void saveCompilation(ColmenaCompilation cc) {
		factory.getColmenaCompilationDAO().insertColmenaCompilation(cc);
	}

	/**
	 * Open a connection in jdbc for the database specified in the preferences
	 * page.
	 * 
	 * @throws ColmenaPersistenceException
	 */
	public void openConnection() throws ColmenaPersistenceException {
		try {
			
			// load the driver
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + databaseHostName + "/"
					+ databaseName;
			
			// create the connection
			connection = DriverManager.getConnection(url, databaseUserName,
					databaseUserPass);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Get the current connection
	 * 
	 * @return the current connection
	 */
	public Connection getConnection() {
		try {
			openConnection();
		} catch (ColmenaPersistenceException e) {
			System.out.println("Error during oppening connection");
		}
		return connection;
	}

	/**
	 * Close a connection
	 */
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the values specified in the preferences page for the connection
	 */
	private void obtainValuesOfConnection() {
		databaseHostName = pm.obtainCurrentDatabaseHostName();
		databaseName = pm.obtainCurrentDatabaseName();
		databaseUserName = pm.obtainCurrentDatabaseUserName();
		databaseUserPass = pm.obtainCurrentDatabaseUserPass();
	}

	/**
	 * Obtain the values in the preferences.
	 */
	private void obtainValuesOfTables() {
		compilationTableName = pm.getColmenaCompilationTableName();
		markerTableName = pm.getColmenaMakerTableName();		
		userTableName = pm.getColmenaUserTableName();
		errorTableName = pm.getColmenaErrorTableName();
	}

	/**
	 * Singleton
	 * 
	 * @return the instance of the object
	 */
	public static DataBaseManager getInstance() {
		if (instance == null) {
			instance = new DataBaseManager();
		}
		return instance;
	}

	public PreferenceManager getPm() {
		return pm;
	}

	public String getDatabaseHostName() {
		return databaseHostName;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public String getDatabaseUserName() {
		return databaseUserName;
	}

	public String getDatabaseUserPass() {
		return databaseUserPass;
	}

	public String getMarkerTableName() {
		return markerTableName;
	}

	public String getUserTableName() {
		return userTableName;
	}

	public String getErrorTableName() {
		return errorTableName;
	}

	public DAOFactory getFactory() {
		return factory;
	}

	public String getCompilationTableName() {
		return compilationTableName;
	}

}
