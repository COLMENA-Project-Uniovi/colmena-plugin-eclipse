package es.uniovi.pulso.colmena.business.manager;

import es.uniovi.pulso.colmena.business.manager.persistence.DataBaseManager;
import es.uniovi.pulso.colmena.business.manager.preferences.PreferenceManager;
import es.uniovi.pulso.colmena.model.ColmenaUser;
import es.uniovi.pulso.colmena.persistence.dao.DAOFactory;
import es.uniovi.pulso.colmena.persistence.jdbc.JdbcDAOFactory;

/**
 * Class that manages the user in the system
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class UserManager {

	// the current user
	private ColmenaUser currentUser;

	// preferences Manager
	private PreferenceManager pm;

	// persistence Manager
	private DataBaseManager dbm;

	// DAO Factory
	private DAOFactory factory;

	public UserManager() {
		// creates the manager
		this.pm = new PreferenceManager();

		if (pm.isActiveDatabasePersistence()) {
			this.dbm = DataBaseManager.getInstance();
		}
		// prepares the user
		this.currentUser = new ColmenaUser(pm.obtainCurrentUserName());

		// prepares the factory
		this.factory = new JdbcDAOFactory();
	}

	/**
	 * Get the current user
	 * 
	 * @return the current user
	 */
	public ColmenaUser getCurrentUser() {
		// obtain the preferencesName
		String preferencesName = pm.obtainCurrentUserName();
		// compare if the currentUser is the same as in the preferences page.
		if (!preferencesName.equals(currentUser.getId())) {
			setCurrentUser(preferencesName);
		}
		return currentUser;
	}

	/**
	 * Changes the current user by name/ID
	 * 
	 * @param userID
	 *            the userID of new user
	 */
	public void setCurrentUser(String userID) {
		if (pm.isActiveDatabasePersistence()) {
			currentUser = factory.getColmenaUserDAO().findUserbyId(userID);
			if (currentUser == null) {
				currentUser = new ColmenaUser(userID);
				dbm.saveUser(currentUser);
			}
		} else {
			currentUser.setId(userID);
		}
		// changes the preferences value;
		pm.changeUserName(userID);
	}

}
