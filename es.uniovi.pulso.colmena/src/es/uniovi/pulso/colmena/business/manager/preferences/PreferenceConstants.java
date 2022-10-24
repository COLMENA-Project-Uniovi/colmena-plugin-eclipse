package es.uniovi.pulso.colmena.business.manager.preferences;

/**
 * Constant definitions for plug-in preferences
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 */
public class PreferenceConstants {
	//types of persistence
	public static final String COLMENA_STORE_IN_DB = "database";
	public static final String COLMENA_STORE_IN_FILE = "file";
	public static final String COLMENA_STORE_IN_FTP = "ftp";
	public static final String COLMENA_POST = "post";
	
	// user settings
	public static final String USER_NAME = System.getProperty("user.name");
	
	// requests settings
	public static final String COLMENA_ENDPOINT = "colmena_endpoint";

	// database settings
	public static final String DATABASE_HOST_NAME = "database_host_name";
	public static final String DATABASE_HOST_PORT = "database_host_port";
	public static final String DATABASE_NAME = "database_name";
	public static final String DATABASE_USER_NAME = "database_user_name";
	public static final String DATABASE_USER_PASS = "database_user_password";
	
	// constants for table name. 
	public static final String COLMENA_USER_TABLE = "colmena_user";
	public static final String COLMENA_ERROR_TABLE = "colmena_error";
	public static final String COLMENA_MARKER_TABLE = "colmena_marker";
	public static final String COLMENA_COMPILATIONS_TABLE = "colmena_compilations";
	
	// folder & file name
	public static final String FOLDER_NAME = "Colmena Results";
	public static final String FILE_NAME = "Colmena";
	
	// Ftp settings
	public static final String FTP_HOST_NAME = "ftp_host_name";
	public static final String FTP_HOST_PORT = "ftp_host_port";
	public static final String FTP_NAME = "ftp_name";
	public static final String FTP_USER_NAME = "ftp_user_name";
	public static final String FTP_USER_PASS = "ftp_user_password";
	public static final String FTP_SAVING = "minutes";
	public static final String FTP_N_SAVING = "10";
}
