package es.uniovi.pulso.colmena.business.manager.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import es.uniovi.pulso.colmena.activator.Activator;

/**
 * Class used to initialize default preference values.
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		// persistence types settings
		store.setDefault(PreferenceConstants.COLMENA_STORE_IN_DB, false);
		store.setDefault(PreferenceConstants.COLMENA_STORE_IN_FILE, false);
		store.setDefault(PreferenceConstants.COLMENA_STORE_IN_FTP, false);
		store.setDefault(PreferenceConstants.COLMENA_POST, true);

		// request settings
		store.setDefault(PreferenceConstants.COLMENA_MARKERS_ENDPOINT, "https://beta.colmenaproject.es/admin/api/1.0/errors/markers/add.json");
		store.setDefault(PreferenceConstants.COLMENA_COMPILATIONS_ENDPOINT, "https://beta.colmenaproject.es/admin/api/1.0/errors/compilations/add.json");
		
		// database settings
		store.setDefault(PreferenceConstants.DATABASE_HOST_NAME, "94.127.187.103");
		store.setDefault(PreferenceConstants.DATABASE_NAME, "admin_colmena");
		store.setDefault(PreferenceConstants.DATABASE_USER_NAME, "colmena_admin");
		store.setDefault(PreferenceConstants.DATABASE_USER_PASS, "9W$ecd709");
		store.setDefault(PreferenceConstants.DATABASE_HOST_PORT, "3306");

		// user settings
		store.setDefault(PreferenceConstants.USER_NAME, System.getProperty("user.name"));
		
		//table settings
		store.setDefault(PreferenceConstants.COLMENA_ERROR_TABLE, "colmena_error");
		store.setDefault(PreferenceConstants.COLMENA_MARKER_TABLE, "colmena_marker_temp");
		store.setDefault(PreferenceConstants.COLMENA_COMPILATIONS_TABLE, "colmena_compilations_temp");
		store.setDefault(PreferenceConstants.COLMENA_USER_TABLE, "colmena_user");
		
		// folder & file name
		store.setDefault(PreferenceConstants.FOLDER_NAME, "Colmena Results");
		store.setDefault(PreferenceConstants.FILE_NAME, "Colmena");
		
		// FTP settings
		store.setDefault(PreferenceConstants.FTP_HOST_NAME, "sandbox.neozink.com");
		store.setDefault(PreferenceConstants.FTP_HOST_PORT, "21");
		store.setDefault(PreferenceConstants.FTP_NAME, "/httpdocs/colmena/2015/");
		store.setDefault(PreferenceConstants.FTP_USER_NAME, "neobox");
		store.setDefault(PreferenceConstants.FTP_USER_PASS, "N30z1nk");
		store.setDefault(PreferenceConstants.FTP_N_SAVING, "10");
	}

}
