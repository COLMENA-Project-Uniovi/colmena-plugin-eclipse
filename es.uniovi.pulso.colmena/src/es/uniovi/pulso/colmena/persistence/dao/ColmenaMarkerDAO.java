package es.uniovi.pulso.colmena.persistence.dao;

import es.uniovi.pulso.colmena.model.ColmenaMarker;

/**
 * DAO that represents the Concrete Marker DAO in db.
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public interface ColmenaMarkerDAO {
	/**
	 * Insert a ColmenaMarker in a db
	 * 
	 * @param cmarker
	 *            the marker
	 */
	public void insertColmenaMarker(ColmenaMarker cmarker, String ccompilation_id);

}
