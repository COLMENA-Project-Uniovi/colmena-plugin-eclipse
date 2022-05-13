package es.uniovi.pulso.colmena.business.manager;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import es.uniovi.pulso.colmena.model.ColmenaMarker;
import es.uniovi.pulso.colmena.model.ColmenaUser;
import es.uniovi.pulso.colmena.model.exception.ColmenaGeneralException;

/**
 * Class that manages the extraction marker operation and converts the IMarker
 * objects from Eclipse SDK into ColmenaMarkers (customised)
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class MarkerManager {

	/**
	 * Method that obtain the problem markers (errors and warnings) in the
	 * resource specified with the user owner specified.
	 * 
	 * @param file
	 *            The file where the markers will be extracted
	 * @param currentUser
	 *            The user that will be the owner of the errors
	 * @return A list of ColmenaMarkers
	 * @throws ColmenaGeneralException
	 *             If exists problems in the operation.
	 */
	public List<ColmenaMarker> obtainMarkers(IResource file, ColmenaUser currentUser)
			throws ColmenaGeneralException {
		// a list of IMarker.
		IMarker[] markerSet = null;
		try {
			// we obtain the PROBLEM markers
			markerSet = file.findMarkers(IMarker.PROBLEM, true,
					IResource.DEPTH_INFINITE);

		} catch (CoreException ce) {
			throw new ColmenaGeneralException("Problem obtaining markers form IDE");
		}
		// convert the IMarkers into ColmenaMarker
		return convertMarkers(markerSet, currentUser);
	}

	/**
	 * Print all ColmenaMarkers
	 * 
	 * @param cmarkerList
	 *            the list of markers
	 * @throws ColmenaGeneralException
	 *             If exist any problem with the print method.
	 */
	public void printMarkers(List<ColmenaMarker> cmarkerList)
			throws ColmenaGeneralException {
		for (ColmenaMarker c : cmarkerList) {
			// print the markers
			System.out.println(c);
		}
	}

	/**
	 * Converts the IMarker into ColmenaMarker objects.
	 * 
	 * @param markerSet
	 *            The list of IMarker
	 * @param currentUser
	 *            The owner of the problems in the resource
	 * @return An ArrayList of ColmenaMarker type object
	 * @throws ColmenaGeneralException
	 *             If exist problem in the operation
	 */
	public List<ColmenaMarker> convertMarkers(IMarker[] markerSet,
			ColmenaUser currentUser) throws ColmenaGeneralException {
		// the list of ColmenaMarker that will be managed
		List<ColmenaMarker> cmarkerList = new ArrayList<ColmenaMarker>();
		for (IMarker m : markerSet) {
			// conversion process and save in the List
			cmarkerList.add(new ColmenaMarker(m, currentUser));
		}
		return cmarkerList;
	}

}
