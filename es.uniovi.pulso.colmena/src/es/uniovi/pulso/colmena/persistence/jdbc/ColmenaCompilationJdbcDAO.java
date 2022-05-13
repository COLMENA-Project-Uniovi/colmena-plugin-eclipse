package es.uniovi.pulso.colmena.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;

import es.uniovi.pulso.colmena.business.manager.persistence.DataBaseManager;
import es.uniovi.pulso.colmena.model.ColmenaCompilation;
import es.uniovi.pulso.colmena.persistence.dao.ColmenaCompilationDAO;

public class ColmenaCompilationJdbcDAO implements ColmenaCompilationDAO {
	private String tableName = DataBaseManager.getInstance()
			.getCompilationTableName();

	@Override
	public void insertColmenaCompilation(ColmenaCompilation ccompilation) {
		// get the connection
		Connection connection = DataBaseManager.getInstance().getConnection();		
		
		// query
		String query = "insert into "
				+ tableName
				+ " (id, user_id, type, timestamp, num_markers, project_name, class_name, ip) "
				+ "values (?,?,?,?,?,?,?,?)";

		// prepare the query in jdbc
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(query);
			
			ps.setString(1, ccompilation.getId());
			ps.setString(2, ccompilation.getUser().getId());			
			ps.setString(3, ccompilation.getType());
			ps.setString(4, ccompilation.getTimestamp());
			ps.setInt(5, ccompilation.getNMarkers());
			ps.setString(6, ccompilation.getProjectName());
			ps.setString(7, ccompilation.getClassName());
			ps.setString(8, ccompilation.getIpAdress());			

			ps.executeUpdate();

			ps.close();
			DataBaseManager.getInstance().closeConnection();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}
