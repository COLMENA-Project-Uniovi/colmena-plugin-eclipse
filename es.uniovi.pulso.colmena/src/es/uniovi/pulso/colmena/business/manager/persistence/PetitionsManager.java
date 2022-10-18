package es.uniovi.pulso.colmena.business.manager.persistence;

import java.util.List;

import es.uniovi.pulso.colmena.business.manager.preferences.PreferenceManager;
import es.uniovi.pulso.colmena.model.ColmenaCompilation;
import es.uniovi.pulso.colmena.model.ColmenaMarker;

/**
 * Class that manages the persistence to data base
 * 
 * @author Borja Rodríguez Lorenzo, PULSO Research Team, University of Oviedo
 * 
 */
public class PetitionsManager {

	// the instance
	private static PetitionsManager instance;

	// manager
	private PreferenceManager pm;

	// values of the tables
	private String compilationTableName;
	private String markerTableName;
	private String userTableName;
	private String errorTableName;

	public PetitionsManager() {
		// start the manager and factory of DAOS
		this.pm = new PreferenceManager();
	}


	/**
	 * Persist the markers
	 * 
	 * @param cmarkerList
	 *            the list of markers
	 */
	public void saveMarkers(List<ColmenaMarker> cmarkerList, String ccompilation_id) {
		for (ColmenaMarker c : cmarkerList) {
			System.out.println("Insertamos esto -> : " + c.toString());
//			factory.getColmenaMarkerDAO().insertColmenaMarker(c, ccompilation_id);

		}
	}
	
	/**
	 * Store the type of compilation in the data base
	 * @param currentUser 
	 * 
	 * @param currentUser
	 */
	public void saveCompilation(ColmenaCompilation cc) {
//		factory.getColmenaCompilationDAO().insertColmenaCompilation(cc);
	}

	/**
	 * Singleton
	 * 
	 * @return the instance of the object
	 */
	public static PetitionsManager getInstance() {
		if (instance == null) {
			instance = new PetitionsManager();
		}
		return instance;
	}

	public PreferenceManager getPm() {
		return pm;
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

	public String getCompilationTableName() {
		return compilationTableName;
	}

}
