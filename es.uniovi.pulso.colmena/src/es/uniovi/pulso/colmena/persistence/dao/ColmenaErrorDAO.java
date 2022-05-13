package es.uniovi.pulso.colmena.persistence.dao;

import java.util.List;

import es.uniovi.pulso.colmena.model.ColmenaError;

/**
 * DAO that represents the Concrete Error DAO in db.
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public interface ColmenaErrorDAO {

	public void insertError(ColmenaError e);

	public ColmenaError findErrorById(String id);

	public List<ColmenaError> findErrorByType(String string);

	public String obtainTemplateName(String errorCode);

	public ColmenaError getUserMostCommonMarker(String userId, String type);

}
