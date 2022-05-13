package es.uniovi.pulso.colmena.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;

import es.uniovi.pulso.colmena.business.manager.persistence.DataBaseManager;
import es.uniovi.pulso.colmena.model.ColmenaMarker;
import es.uniovi.pulso.colmena.persistence.dao.ColmenaMarkerDAO;

/**
 * Class that implements the ColmenaMarkerDAO in JDBC
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class ColmenaMarkerJdbcDAO implements ColmenaMarkerDAO {
	private String tableName = DataBaseManager.getInstance()
			.getMarkerTableName();

	@Override
	public void insertColmenaMarker(ColmenaMarker cmarker, String ccompilation_id) {
		// get the connection
		Connection connection = DataBaseManager.getInstance().getConnection();		
		
		// query
		String query = "insert into "
				+ tableName
				+ " (user_id, error_id, gender, timestamp, path, class_name, ip, project, project_name, line_number, custom_message, colmena_compilation_id) values (?,?,?,?,?,?,?,?,?,?,?,?)";

		// prepare the query in jdbc
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(query);

			ps.setString(1, cmarker.getUser().getId());
			ps.setString(2, cmarker.getErrorId());
			ps.setString(3, cmarker.getGender());
			ps.setString(4, cmarker.getTimestamp());
			ps.setString(5, cmarker.getPath());
			ps.setString(6, cmarker.getClassName());
			ps.setString(7, cmarker.getIpAdress());
			ps.setString(8, cmarker.getProjectPath());
			ps.setString(9, cmarker.getProjectName());
			ps.setString(10, cmarker.getLineNumber());
			ps.setString(11, cmarker.getMessage());
			ps.setString(12, ccompilation_id);

			ps.executeUpdate();

			ps.close();
			DataBaseManager.getInstance().closeConnection();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
