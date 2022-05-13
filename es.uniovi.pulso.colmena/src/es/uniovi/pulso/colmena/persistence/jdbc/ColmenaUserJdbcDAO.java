package es.uniovi.pulso.colmena.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import es.uniovi.pulso.colmena.business.manager.persistence.DataBaseManager;
import es.uniovi.pulso.colmena.model.ColmenaUser;
import es.uniovi.pulso.colmena.persistence.dao.ColmenaUserDAO;

/**
 * Implementation for the ColmenaUserDAO in JDBC
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class ColmenaUserJdbcDAO implements ColmenaUserDAO {
	private String tableName = DataBaseManager.getInstance().getUserTableName();

	@Override
	public boolean existsUser(ColmenaUser user) {
		// obtain the connection
		Connection connection = DataBaseManager.getInstance().getConnection();
		// prepare the query
		String url = "Select * from " + tableName + " where id=?";
		boolean exists = false;
		try {
			// query elaboration in jdbc
			PreparedStatement ps = connection.prepareStatement(url);
			ps.setString(1, user.getId());
			ResultSet rs = ps.executeQuery();
			// check if something is returned
			if (rs.next()) {
				exists = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DataBaseManager.getInstance().closeConnection();
		return exists;
	}

	@Override
	public void insertUser(ColmenaUser user) {
		// connection
		Connection connection = DataBaseManager.getInstance().getConnection();
		// query
		String url = "insert into " + tableName + " (id, password, subject, academic_year) values(?, ?, 0, 2014)";
		try {
			// query elaboration in jdbc
			PreparedStatement ps = connection.prepareStatement(url);
			ps.setString(1, user.getId());
			ps.setString(2, user.getId());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DataBaseManager.getInstance().closeConnection();
	}

	@Override
	public ColmenaUser findUserbyId(String id) {
		ColmenaUser cu = null;
		// connection and query
		Connection connection = DataBaseManager.getInstance().getConnection();
		String url = "Select * from " + tableName + " where id=?";
		try {
			// query elaboration in jdbc
			PreparedStatement ps = connection.prepareStatement(url);
			ps.setString(1, id);			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				cu =  new ColmenaUser(id);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DataBaseManager.getInstance().closeConnection();
		return cu;
	}
	
}
