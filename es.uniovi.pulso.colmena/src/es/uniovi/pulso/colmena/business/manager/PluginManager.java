package es.uniovi.pulso.colmena.business.manager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;

import es.uniovi.pulso.colmena.business.manager.persistence.DataBaseManager;
import es.uniovi.pulso.colmena.business.manager.persistence.FTPManager;
import es.uniovi.pulso.colmena.business.manager.persistence.FileManager;
import es.uniovi.pulso.colmena.business.manager.persistence.RequestManager;
import es.uniovi.pulso.colmena.business.manager.preferences.PreferenceManager;
import es.uniovi.pulso.colmena.model.ColmenaCompilation;
import es.uniovi.pulso.colmena.model.ColmenaMarker;
import es.uniovi.pulso.colmena.model.exception.ColmenaAbstractException;
import es.uniovi.pulso.colmena.model.exception.ColmenaGeneralException;
import es.uniovi.pulso.colmena.model.exception.ColmenaPersistenceException;

/**
 * Class that manages the life-cycle of the plugin.
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class PluginManager {

	// singleton
	private static PluginManager manager;
	// a MarkerManager object
	private MarkerManager mm;

	// the file manager object
	private FileManager fm;

	// the FTP manager object
	private FTPManager ftpm;

	// the preferences manager
	private PreferenceManager pm;

	// the data base manager
	private DataBaseManager dbm;

	// the requests manager
	private RequestManager rm;

	// enabled or disabled plugin
	private boolean enabled;

	// user Manager
	private UserManager um;

	// cache Manager
	private CacheManager cm;

	// count how many executions has been run
	private int counter;

	// moment since last execution
	private long start;

	// last resource that has been analize by colmena
	private IResource lastresource;

	/**
	 * private constructor: singleton
	 */
	private PluginManager() {
		// create managers
		this.mm = new MarkerManager();
		this.fm = new FileManager();
		this.um = new UserManager();
		this.pm = new PreferenceManager();

		if (pm.isActiveDatabasePersistence())
			this.dbm = DataBaseManager.getInstance();

		if (pm.isActiveFtpPersistence())
			this.ftpm = new FTPManager();

		if (pm.isActivePetitions())
			this.rm = new RequestManager();

		this.cm = CacheManager.getInstance();
		// enable the plugin
		this.enabled = false;
		startCounters();
	}

	/**
	 * Singleton
	 * 
	 * @return the instance
	 */
	public static PluginManager getInstance() {
		if (manager == null) {
			manager = new PluginManager();
		}
		return manager;
	}

	/**
	 * Method that runs the plugin and starts all the operations
	 * 
	 * @param resource The file saved
	 */
	public void runPlugin(IResource resource) {
		try {
			// if the plugin is enabled
			if (enabled) {
				counter++;
				long now = ((System.currentTimeMillis() - start) / 1000) / 60;

				lastresource = resource;

				List<ColmenaMarker> colmenaMarkers = mm.obtainMarkers(resource, um.getCurrentUser());

				String compilationID;

				File dinamic = null;
				File nonCached = null;

				// if NO ERRORS O WARNINGS
				if (colmenaMarkers.isEmpty()) {
					ColmenaCompilation cc = new ColmenaCompilation(um.getCurrentUser(), ColmenaCompilation.OK, resource, 0);

					if (pm.isActiveFilePersistence()) {
						dinamic = fm.saveCompilationType(cc, get_directory(), get_all_filename());
						nonCached = fm.saveCompilationType(cc, get_directory(), get_non_cached_filename());
					}

					if (pm.isActiveDatabasePersistence()) {
						if (dbm == null)
							this.dbm = DataBaseManager.getInstance();
						
						// Store the user
						dbm.saveUser(um.getCurrentUser());
						
						// Store the type of compilation in database
						dbm.saveCompilation(cc);
					}
					
					if(pm.isActivePetitions()) {
						compilationID = rm.saveCompilation(cc);
					}

				} else {
					ColmenaCompilation cc = new ColmenaCompilation(um.getCurrentUser(), ColmenaCompilation.ERROR, resource, colmenaMarkers.size());
					
					// Cache
					cm.addToCache(resource, colmenaMarkers);

					if(pm.isActivePetitions()) {
						compilationID = rm.saveCompilation(cc);
						rm.saveMarkers(resource, colmenaMarkers, compilationID);
					}
					
					if (pm.isActiveFilePersistence()) {
						// Store the markers in a file
						dinamic = fm.saveCompilationType(cc, get_directory(), get_all_filename());
						dinamic = fm.saveMarkersToCVS(colmenaMarkers, dinamic);
						
						// for the markers real, noncached
						nonCached = fm.saveCompilationType(cc, get_directory(), get_non_cached_filename());
						nonCached = fm.saveMarkersToCVS(cm.getFinalErrorList(), nonCached);

					}
					if (pm.isActiveDatabasePersistence()) {
						if (dbm == null)
							this.dbm = DataBaseManager.getInstance();
						// Store the user
						dbm.saveUser(um.getCurrentUser());
						// Store the type of compilation in db
						dbm.saveCompilation(cc);
						// Store the markers in a db
						dbm.saveMarkers(cm.getFinalErrorList(), cc.getId());
					}
					if (pm.isActiveFtpPersistence()) {
						if ((pm.obtainCurrentFtpSaving().equals("times")
								&& this.counter >= pm.obtainCurrentFtpNumSaving())
								|| (pm.obtainCurrentFtpSaving().equals("minutes")
										&& now >= pm.obtainCurrentFtpNumSaving())) {

							if (ftpm == null)
								this.ftpm = FTPManager.getInstance();

							ftpm.sendFile(dinamic);
							ftpm.sendFile(nonCached);

							if (!pm.isActiveFilePersistence()) {
								fm.deleteFile(dinamic);
								fm.deleteFile(nonCached);
							}
							startCounters();
						}

					}
				}
			}
		} catch (ColmenaAbstractException ce) {
			ce.printStackTrace();
		} catch (JavaModelException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private String get_directory() {
		return lastresource.getProject().getLocation().toString() + File.separator + pm.getFolderName()
				+ File.separator;
	}

	/**
	 * 
	 * @return All markers file name
	 */
	private String get_all_filename() {
		return pm.getFileName() + "-a-" + lastresource.getProject().getName() + "-" + um.getCurrentUser() + ".txt";
	}

	/**
	 * 
	 * @return Non cached markers filename
	 */
	private String get_non_cached_filename() {
		return pm.getFileName() + "-" + lastresource.getProject().getName() + "-" + um.getCurrentUser() + ".txt";
	}

	/**
	 * 
	 * @return Static plugin file name
	 */
	private String get_static_filename() {
		return pm.getFileName() + "-s-" + lastresource.getProject().getName() + "-" + um.getCurrentUser() + ".txt";
	}

	/**
	 * Method that runs the plugin statically and do not affect the normal life
	 * cycle of the plugin. Recover the markers of all the project.
	 * 
	 * @param resource the complete project.
	 * @throws IOException
	 */
	public void runPluginStatically(IResource resource) {
		try {
			lastresource = resource;
			// obtain the markers
			List<ColmenaMarker> colmenaMarkers = mm.obtainMarkers(resource, um.getCurrentUser());
			ColmenaCompilation cc = null;
			if (colmenaMarkers.isEmpty()) {
				cc = new ColmenaCompilation(um.getCurrentUser(), ColmenaCompilation.OK, resource, 0);
			} else {
				cc = new ColmenaCompilation(um.getCurrentUser(), ColmenaCompilation.ERROR, resource,
						colmenaMarkers.size());
			}
			if (pm.isActiveFilePersistence()) {
				File static_file = fm.saveCompilationType(cc, get_directory(), get_static_filename());
				// Store the markers in a file
				fm.saveMarkersToCVS(colmenaMarkers, static_file);
			}
			if (pm.isActiveDatabasePersistence()) {
				// Store the user
				dbm.saveUser(um.getCurrentUser());
				// Store the type of compilation in db
				dbm.saveCompilation(cc);
				// Store the markers in a db
				dbm.saveMarkers(colmenaMarkers, cc.getId());
			}
		} catch (ColmenaAbstractException ce) {
			ce.printStackTrace();
		}
	}

	/**
	 * Set the enabled
	 * 
	 * @param enabled the value of enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Returns if the plugin is enabled or not
	 * 
	 * @return if the plugin is enabled or not
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Returns the current user name
	 * 
	 * @return the current user name
	 */
	public String getCurrentUserId() {
		return um.getCurrentUser().getId();
	}

	/**
	 * Changes the current user name/ID
	 * 
	 * @param userId the new name/ID
	 */
	public void changeCurrentUserName(String userId) {
		um.setCurrentUser(userId);
	}

	/**
	 * Save to ftp. Called when the plugin is stopped
	 * 
	 * @param resource
	 * @throws IOException
	 */
	public void saveFtp() throws IOException {
		IResource resource = lastresource;
		if (resource != null) {
			if (ftpm == null)
				this.ftpm = FTPManager.getInstance();
			// obtain the markers
			List<ColmenaMarker> colmenaMarkers;
			try {
				colmenaMarkers = mm.obtainMarkers(resource, um.getCurrentUser());

				File dinamic = null;
				File nonCached = null;

				// if NO ERRORS O WARNINGS
				if (colmenaMarkers.isEmpty()) {
					ColmenaCompilation cc = new ColmenaCompilation(um.getCurrentUser(), ColmenaCompilation.OK, resource,
							0);

					if (pm.isActiveFilePersistence()) {
						dinamic = fm.saveCompilationType(cc, get_directory(), get_all_filename());
						nonCached = fm.saveCompilationType(cc, get_directory(), get_non_cached_filename());
					}
				} else {
					ColmenaCompilation cc = new ColmenaCompilation(um.getCurrentUser(), ColmenaCompilation.ERROR,
							resource, colmenaMarkers.size());
					// Store the markers in a file
					dinamic = fm.saveCompilationType(cc, get_directory(), get_all_filename());
					dinamic = fm.saveMarkersToCVS(colmenaMarkers, dinamic);
					// for the markers real, noncached
					nonCached = fm.saveCompilationType(cc, get_directory(), get_non_cached_filename());
					nonCached = fm.saveMarkersToCVS(cm.getFinalErrorList(), nonCached);
				}

				ftpm.sendFile(dinamic);
				ftpm.sendFile(nonCached);

				if (!pm.isActiveFilePersistence()) {
					fm.deleteFile(dinamic);
					fm.deleteFile(nonCached);
				}

				counter = 0;
			} catch (ColmenaGeneralException e) {
				e.printStackTrace();
			} catch (ColmenaPersistenceException e) {
				e.printStackTrace();
			}
		}
	}

	public void startCounters() {
		counter = 0;
		start = System.currentTimeMillis();
	}

}
