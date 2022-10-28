package es.uniovi.pulso.colmena.business.manager.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import es.uniovi.pulso.colmena.business.manager.preferences.PreferenceManager;
import es.uniovi.pulso.colmena.model.ColmenaCompilation;
import es.uniovi.pulso.colmena.model.ColmenaMarker;

/**
 * Class that manages the persistence to data base
 * 
 * @author Borja Rodríguez Lorenzo, PULSO Research Team, University of Oviedo
 * 
 */
public class RequestManager {

	// the instance
	private static RequestManager instance;

	// manager
	private PreferenceManager pm;

	public RequestManager() {
		this.pm = new PreferenceManager();
	}

	/**
	 * Persist the markers
	 * 
	 * @param cmarkerList the list of markers
	 */
	public void saveMarkers(List<ColmenaMarker> cmarkerList, String compilationID) {
		URL url;

		try {
			url = new URL(pm.obtainMakersEndpoint());

			for (ColmenaMarker c : cmarkerList) {
				makeRequest(url, c.toJson(compilationID), "marker");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method which prepares the params for the request method
	 * 
	 * @param compilation
	 */
	public String saveCompilation(ColmenaCompilation compilation) {
		String json = compilation.toJson();
		String response = "";

		try {
			URL url = new URL(pm.obtainCompilationsEndpoint());

			response = makeRequest(url, json, "compilation");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * Method which sends the post petition to the API endpoint
	 * 
	 * @param requestURL
	 * @param json
	 */
	private String makeRequest(URL requestURL, String json, String type) {
		String compilationID = "";
		Gson gson = new Gson();

		try {
			HttpURLConnection con = (HttpURLConnection) requestURL.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);

			String jsonInputString = json;

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

				if (type == "compilation") {
					JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
					compilationID = jsonObject.get("data").toString();
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return compilationID;
	}

	/**
	 * Singleton
	 * 
	 * @return the instance of the object
	 */
	public static RequestManager getInstance() {
		if (instance == null) {
			instance = new RequestManager();
		}
		return instance;
	}

	public PreferenceManager getPm() {
		return pm;
	}
}
