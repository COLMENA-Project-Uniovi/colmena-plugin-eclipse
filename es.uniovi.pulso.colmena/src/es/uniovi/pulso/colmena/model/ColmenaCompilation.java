package es.uniovi.pulso.colmena.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;

import com.google.gson.Gson;

import es.uniovi.pulso.colmena.model.exception.ColmenaGeneralException;

public class ColmenaCompilation {
	// type of the error
		public static final String OK = "OK";
		public static final String ERROR = "ERROR";

		// data about the compilation
		private String id;
		private ColmenaUser user;
		private String type;
		private String timestamp;
		private String projectName;
		private String className;		
		private String ipAdress;
		private int nMarkers;

		/**
		 * Constructor of the class
		 * 
		 * @param marker
		 *            the  object
		 * @param currentUser
		 *            the Owner of the error
		 * @param type
		 * 			  OK compilation or ERROR compilation		 
		 * @param resource 
		 */
		public ColmenaCompilation(ColmenaUser currentUser, String type, IResource resource, int nMarkers)
				throws ColmenaGeneralException {
			
				// OK or error compilation
				this.type = type;
				
				Date now = new Date();
				// the current time in a specified format
				this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(now);
				// owner
				this.user = currentUser;
				
				this.id = currentUser + new SimpleDateFormat("yyyyMMddHHmmss").format(now);
								
				this.className = resource.getName();				
				
				this.projectName = resource.getProject().getName();

				this.ipAdress = obtainIP();
				
				this.nMarkers = nMarkers;
			
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

		public String getId() {
			return id;
		}
		
		public String getType() {
			return type;
		}
		
		public String getProjectName() {
			return projectName;
		}		
		
		public String getClassName() {
			return className;
		}

		public String getTimestamp() {
			return timestamp;
		}
		
		public String getIpAdress() {
			return ipAdress;
		}
		
		public int getNMarkers() {
			return nMarkers;
		}

		@Override
		public String toString() {
			return user.toString() + "\t" + type + "\t" + timestamp + "\t" + projectName
					+ "\t" + className + "\t" + ipAdress;
		}
		
		public String toFile() {
			return type + "_COMPILATION\t" + timestamp + "\t" + nMarkers + "markers\t" +  projectName
					+ "\t" + className + "\t" + ipAdress;
		}
		
		/**
		 * Method which makes a JSON string from the marker
		 * 
		 * @return String
		 */
		public String toJson() {
			Gson gson = new Gson();
			
			Map<String, String> map = new HashMap<>();
			map.put("user_id", user.getId());
			map.put("type", type);
			map.put("timestamp", getTimestamp());
			map.put("num_markers", Integer.toString(getNMarkers()));
			map.put("project_name", getProjectName());
			map.put("class_name", getClassName());
			map.put("ip", getIpAdress());

			return gson.toJson(map);
		}

		public String getFormat() {
			return "User id\tType\tCreation Time\tProject Name\tClass Name\tIP";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result;			
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
			ColmenaCompilation other = (ColmenaCompilation) obj;
			
			if (user == null) {
				if (other.getUser() != null)
					return false;
			} else if (!user.equals(other.getUser()))
				return false;
			return true;
		}
}
