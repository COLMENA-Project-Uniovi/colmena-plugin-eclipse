package es.uniovi.pulso.colmena.business.manager.persistence;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import es.uniovi.pulso.colmena.business.manager.preferences.PreferenceManager;

public class FTPManager {
	
	
	// manager
	private PreferenceManager pm;	
	
	// the instance
	private static FTPManager instance;
	
	

	// values of connection
	private String ftpHostName;
	private String ftpHostPort;
	private String ftpName;
	private String ftpUserName;
	private String ftpUserPass;			
	
	/**
	 * Singleton
	 * 
	 * @return the instance of the object
	 */
	public static FTPManager getInstance() {
		if (instance == null) {
			instance = new FTPManager();
		}
		return instance;
	}
	
	public void connectToServer() {

	}


	public void sendFile(File f) {
		try {
			
			this.pm = new PreferenceManager();
			// obtain the values of properties
			obtainValuesOfConnection();
			
			StringBuffer sb = new StringBuffer("ftp://");

			sb.append(ftpUserName);
			sb.append(':');
			sb.append(ftpUserPass);
			sb.append('@');

			sb.append(ftpHostName);
			sb.append(':');
			sb.append(ftpHostPort);
			sb.append(ftpName);
			sb.append(f.getName());
			sb.append(";type=i");

			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				
				URL url = new URL(sb.toString());
				URLConnection urlc = url.openConnection();
				System.out.println("Conectando a : " + url);
				bos = new BufferedOutputStream(urlc.getOutputStream());
				bis = new BufferedInputStream(new FileInputStream(f));

				int i;
				while ((i = bis.read()) != -1) {
					bos.write(i);
				}
			} finally {
				if (bis != null)
					try {
						bis.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				if (bos != null)
					try {
						bos.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
			}
		} catch (Exception e) {
			System.out.println("Error con el ftp" + e.getMessage());
		}

	}
	
	/**
	 * Load the values specified in the preferences page for the connection
	 */
	private void obtainValuesOfConnection() {
		ftpHostName = pm.obtainCurrentFtpHostName();
		ftpHostPort = pm.obtainCurrentFtpHostPort();
		ftpName = pm.obtainCurrentFtpName();
		ftpUserName = pm.obtainCurrentFtpUserName();
		ftpUserPass = pm.obtainCurrentFtpUserPass();
	}

	/**
	 * Create the files and send them via ftp
	 */
	/*
	public void createAndSendFile() {
		if(fm == null)
			this.fm = new FileManager();
		
		List<ColmenaMarker> colmenaMarkers = mm.obtainMarkers(resource,
				um.getCurrentUser());
		// Store the markers in a file
		File dinamic = fm.saveMarkersToCVS(colmenaMarkers, pm.getFileName()
				+ "-Dinamic-All-"+ resource.getProject().getName());
		// for the markers real, noncached
		File noncached = fm.saveMarkersToCVS(cm.getFinalErrorList(),
				pm.getFileName() + "-Dinamic-NonCached-"+ resource.getProject().getName());
		
		sendFile(dinamic);
		sendFile(noncached);
	}
	*/
}
