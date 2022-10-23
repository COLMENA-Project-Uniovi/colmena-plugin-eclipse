package es.uniovi.pulso.colmena.business.manager.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import es.uniovi.pulso.colmena.activator.Activator;

/**
 * Class that manages the acces to preferences values and constants
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class PreferenceManager {
	// Preferences access object
	private IPreferenceStore pref;

	public PreferenceManager() {
		pref = Activator.getDefault().getPreferenceStore();

	}

	/**
	 * Changes the stored preferences with a new name value
	 * 
	 * @param name
	 *            the new name to change in preferences
	 */
	public void changeUserName(String name) {		
		pref.setValue(PreferenceConstants.USER_NAME, name);		
	}

	/**
	 * Obtain the current Name from the stored preferences.
	 * 
	 * @return the current user name
	 */
	public String obtainCurrentUserName() {
		return pref.getString(PreferenceConstants.USER_NAME);
	}

	public String obtainCurrentFtpHostName() {
		return pref.getString(PreferenceConstants.FTP_HOST_NAME);
	}

	public String obtainCurrentFtpHostPort() {
		return pref.getString(PreferenceConstants.FTP_HOST_PORT);
	}

	public String obtainCurrentFtpName() {
		return pref.getString(PreferenceConstants.FTP_NAME);
	}

	public String obtainCurrentFtpUserName() {
		return pref.getString(PreferenceConstants.FTP_USER_NAME);
	}

	public String obtainCurrentFtpUserPass() {
		return pref.getString(PreferenceConstants.FTP_USER_PASS);
	}
	
	public String obtainMakersEndpoint() {
		return pref.getString(PreferenceConstants.COLMENA_MARKERS_ENDPOINT);
	}
	
	public String obtainCompilationsEndpoint() {
		return pref.getString(PreferenceConstants.COLMENA_COMPILATIONS_ENDPOINT);
	}
	
	public String obtainCurrentDatabaseHostName() {
		return pref.getString(PreferenceConstants.DATABASE_HOST_NAME);
	}

	public String obtainCurrentDatabaseHostPort() {
		return pref.getString(PreferenceConstants.DATABASE_HOST_PORT);
	}

	public String obtainCurrentDatabaseName() {
		return pref.getString(PreferenceConstants.DATABASE_NAME);
	}

	public String obtainCurrentDatabaseUserName() {
		return pref.getString(PreferenceConstants.DATABASE_USER_NAME);
	}

	public String obtainCurrentDatabaseUserPass() {
		return pref.getString(PreferenceConstants.DATABASE_USER_PASS);
	}

	public boolean isActiveFilePersistence() {
		return pref.getBoolean(PreferenceConstants.COLMENA_STORE_IN_FILE);
	}

	public boolean isActiveDatabasePersistence() {
		return pref.getBoolean(PreferenceConstants.COLMENA_STORE_IN_DB);
	}
	
	public boolean isActiveFtpPersistence() {
		return pref.getBoolean(PreferenceConstants.COLMENA_STORE_IN_FTP);
	}
	
	public boolean isActivePetitions() {
		return pref.getBoolean(PreferenceConstants.COLMENA_POST);
	}

	public String getColmenaCompilationTableName() {
		return pref.getString(PreferenceConstants.COLMENA_COMPILATIONS_TABLE);
	}
	
	public String getColmenaMakerTableName() {
		return pref.getString(PreferenceConstants.COLMENA_MARKER_TABLE);
	}

	public String getColmenaUserTableName() {
		return pref.getString(PreferenceConstants.COLMENA_USER_TABLE);
	}

	public String getColmenaErrorTableName() {
		return pref.getString(PreferenceConstants.COLMENA_ERROR_TABLE);
	}

	public String getFileName() {
		return pref.getString(PreferenceConstants.FILE_NAME);
	}

	public String obtainCurrentFtpSaving() {
		return pref.getString(PreferenceConstants.FTP_SAVING);
	}

	public int obtainCurrentFtpNumSaving() {		
		return pref.getInt(PreferenceConstants.FTP_N_SAVING);
	}

	public String getFolderName() {
		return pref.getString(PreferenceConstants.FOLDER_NAME);
	}

}
