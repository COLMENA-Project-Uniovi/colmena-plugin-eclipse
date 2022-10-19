package es.uniovi.pulso.colmena.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;

import es.uniovi.pulso.colmena.model.exception.ColmenaGeneralException;

/**
 * Class that represents a Marker customised.
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class ColmenaMarker {
	// type of the error
	public static final String WARNING = "WARNING";
	public static final String ERROR = "ERROR";
	public static final String INFO = "INFO";

	// data about the marker
	private ColmenaUser user;
	private Object error_id;
	private Object message;
	private String gender;
	private Object lineNumber;
	private String timestamp;
	private String projectName;
	private String projectPath;
	private String path;
	private String ipAdress;
	private int session_id = -1; // A�adido el id de la sesi�n a la que pertenece

	/**
	 * Constructor of the class
	 * 
	 * @param marker
	 *            the IMarker object
	 * @param currentUser
	 *            the Owner of the error
	 * @throws ColmenaGeneralException
	 *             If any problem exists in the creating object operation.
	 */
	public ColmenaMarker(IMarker marker, ColmenaUser currentUser)
			throws ColmenaGeneralException {
		try {
			// assign values extracted from IMarker
			this.error_id = marker.getAttribute("id");
			this.message = marker.getAttribute(IMarker.MESSAGE);
			this.lineNumber = marker.getAttribute(IMarker.LINE_NUMBER);

			switch ((Integer) marker.getAttribute(IMarker.SEVERITY)) {
			case 0:
				gender = INFO;
				break;
			case 1:
				gender = WARNING;
				break;
			case 2:
				gender = ERROR;
				break;
			}

			// the current time in a specified format
			this.timestamp = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss")
					.format(new Date());
			// owner
			this.user = currentUser;

			this.projectPath = marker.getResource().getProject().getLocation()
					.toString();
			
			this.projectName = marker.getResource().getProject().getName();

			this.path = marker.getResource().getLocation().toString();

			this.ipAdress = obtainIP();					
						
		} catch (CoreException ce) {
			throw new ColmenaGeneralException("Error creating Colmena Markers");
		}
	}

	private String obtainIP() {
		try {
			java.net.InetAddress i = java.net.InetAddress.getLocalHost();
			String ip = i.getHostAddress();
			return ip;
		} catch (Exception e) {
			System.out.println("Error recovering IP");
		}
		return "";
	}

	// Getters and ToString (including format of toString)

	public ColmenaUser getUser() {
		return user;
	}

	public String getErrorId() {
		return error_id.toString();
	}

	public String getPath() {
		return path;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public String getProjectPath() {
		return projectPath;
	}
	
	public String getClassName() {
		String[] className = projectPath.split("/");
		return className[className.length-1];
	}

	public String getMessage() {
		return message.toString();
	}

	public String getGender() {
		return gender;
	}

	public String getLineNumber() {
		return lineNumber.toString();
	}

	public String getTimestamp() {
		return timestamp;
	}
	
	public String getIpAdress() {
		return ipAdress;
	}
	
	public int getSessionId(){
		return session_id;
	}
	
	public void setSessionId(int session_id){
		this.session_id = session_id;
	}

	@Override
	public String toString() {
		return user.toString() + "\t" + error_id + "\t" + message + "\t"
				+ gender + "\t" + lineNumber + "\t" + timestamp + "\t" + path
				+ "\t" + projectPath + "\t" + ipAdress + "\t" + session_id;
	}
	
	public String toFile() {
		return  "\t" + error_id + "\t" + user.toString() + "\t" + message + "\t"
				+ gender + "\t" + lineNumber + "\t" + timestamp + "\t" + path
				+ "\t" + projectPath + "\t" + ipAdress + "\t" + session_id;
	}
	
	public String toJson() {				
		return "{user_id: " + user.getId() + ", error_id: " + error_id + ", message: " + message + ", gender: " + gender + ", line_number: " + lineNumber + ", creation_time: " + timestamp + ", class_path: " + path + ", project_path: " + projectPath + ", ip: " + getIpAdress() + "}";
	}

	public String getFormat() {
		return "User id\tError id\tMessage\tType\tLine Number\tCreation Time\tPath\tIP\tSession	";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((error_id == null) ? 0 : error_id.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColmenaMarker other = (ColmenaMarker) obj;
		if (error_id == null) {
			if (other.error_id != null)
				return false;
		} else if (!error_id.equals(other.error_id))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}


}
