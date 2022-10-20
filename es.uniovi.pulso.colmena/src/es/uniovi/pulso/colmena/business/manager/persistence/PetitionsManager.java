package es.uniovi.pulso.colmena.business.manager.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import es.uniovi.pulso.colmena.business.manager.preferences.PreferenceManager;
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
	 * @param cmarkerList the list of markers
	 */
	public void saveMarkers(List<ColmenaMarker> cmarkerList, String ccompilation_id) {
		for (ColmenaMarker c : cmarkerList) {
			System.out.println("Insertamos esto -> : " + c.toString());
			this.makePetition(c);
		}
	}

	/**
	 * Method which sends the post petition to the API endpoint
	 * 
	 * @param maker
	 * @return
	 */
	private void makePetition(ColmenaMarker marker) {
		URL url;

		try {
			url = new URL("https://beta.colmenaproject.es/admin/api/1.0/errors/markers/add.json");

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);

			String jsonInputString = marker.toJson();

			try (OutputStream os = con.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;

				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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
