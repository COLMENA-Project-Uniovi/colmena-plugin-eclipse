package es.uniovi.pulso.colmena.business.manager.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import es.uniovi.pulso.colmena.model.ColmenaCompilation;
import es.uniovi.pulso.colmena.model.ColmenaMarker;
import es.uniovi.pulso.colmena.model.exception.ColmenaPersistenceException;

/**
 * Class that manages the external files. Controls the writting of information.
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 * 
 */
public class FileManager {

	/**
	 * Save the markers into a file (txt or cvs)
	 * 
	 * @param obtainedMarkers
	 *            the list of ColmenaMarkers obtained
	 * @throws ColmenaPersistenceException
	 */
	public File saveMarkersToCVS(List<ColmenaMarker> obtainedMarkers,
			File fileName) throws ColmenaPersistenceException {
		
		File file = null;
		for (ColmenaMarker c : obtainedMarkers) {
			try {
				// write the marker
				file = writeMarker(c, fileName);
			} catch (IOException e) {
				e.printStackTrace();
//				throw new ColmenaPersistenceException(
//						"Problem saving markers to CVS");
			}
		}
		
		return file;
	}

	/**
	 * Writes a marker into a file named "Colmena-Username.txt". It writes in
	 * the project root directory a new folder called
	 * "Colmena Results"
	 * 
	 * @param c
	 *            The marker to write
	 * @throws IOException
	 *             If happens any error related with the input or output
	 */
	private File writeMarker(ColmenaMarker c, File file)
			throws IOException {
		

		// control if the file is new
		boolean isFileNew = false;
		if (!file.exists()) {
			isFileNew = true;
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));

		// if the file no exits, we add some headers to info
		if (isFileNew) {
			bw.write(c.getFormat());
			bw.newLine();
		}
		// write the rest of marker
		bw.write(c.toFile());
		bw.newLine();
		bw.close();
		
		return file;		
	}

	public void deleteFile(File file) throws ColmenaPersistenceException { 
	    boolean success = file.delete();

	    if (!success)
	      throw new ColmenaPersistenceException("Error when deleting file");		
	}
	
	
	public File saveCompilationType(ColmenaCompilation cc, String dir, String filename){
		
		File colmenaDir = new File(dir);

		// create the directory
		colmenaDir.mkdir();

		File file = new File(colmenaDir.getPath() + File.separator + filename);

		// control if the file is new
		boolean isFileNew = false;
		if (!file.exists()) {
			isFileNew = true;
		}
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(file, true));
			if (!isFileNew) 
				bw.newLine();
			
			// write compilation type		
			bw.write(cc.toFile());
		
			bw.newLine();
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return file;
				
	}
	

}
