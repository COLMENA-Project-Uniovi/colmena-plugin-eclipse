//package es.uniovi.pulso.colmena.persistence.jdbc;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//import es.uniovi.pulso.colmena.business.manager.persistence.DataBaseManager;
//import es.uniovi.pulso.colmena.model.ColmenaError;
//import es.uniovi.pulso.colmena.model.ColmenaMarker;
//import es.uniovi.pulso.colmena.persistence.dao.ColmenaErrorDAO;
//
///**
// * Implementation for the ColmenaErrorDAO in JDBC
// * 
// * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
// * 
// */
//public class ColmenaErrorJdbcDAO implements ColmenaErrorDAO {
//
//	private String tableName = DataBaseManager.getInstance()
//			.getErrorTableName();
//	private String colmenaMarkerTable = DataBaseManager.getInstance()
//			.getMarkerTableName();
//
//	@Override
//	public void insertError(ColmenaError e) {
//		// connection
//		Connection connection = DataBaseManager.getInstance().getConnection();
//		// query
//		String url = "insert into " + tableName
//				+ " (id, type, template_message ) values(?,?,?)";
//		try {
//			// query elaboration in jdbc
//			PreparedStatement ps = connection.prepareStatement(url);
//			ps.setString(1, e.getId());
//			ps.setString(2, e.getType());
//			ps.setString(3, e.getTemplateName());
//			ps.executeUpdate();
//			ps.close();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//
//	@Override
//	public ColmenaError findErrorById(String id) {
//		// connection and query
//		Connection connection = DataBaseManager.getInstance().getConnection();
//		String url = "Select * from " + tableName + " where id=?";
//		try {
//			// query elaboration in jdbc
//			PreparedStatement ps = connection.prepareStatement(url);
//			ps.setString(1, id);
//			ResultSet rs = ps.executeQuery();
//			if (rs.next()) {
//				String type = rs.getString("type");
//				String templateName = rs.getString("template_message");
//				return new ColmenaError(id, type, templateName);
//			}
//			ps.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	@Override
//	public List<ColmenaError> findErrorByType(String type) {
//		List<ColmenaError> listErrors = new ArrayList<ColmenaError>();
//		// connection and query
//		Connection connection = DataBaseManager.getInstance().getConnection();
//		String url = "Select * from " + tableName + " where type=?";
//		try {
//			// query elaboration in jdbc
//			PreparedStatement ps = connection.prepareStatement(url);
//			ps.setString(1, type);
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				String id = rs.getString("id");
//				String templateName = rs.getString("template_message");
//				listErrors.add(new ColmenaError(id, type, templateName));
//			}
//			ps.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return listErrors;
//	}
//
//	@Override
//	public ColmenaError getUserMostCommonMarker(String userId, String type) {
//		ColmenaError mostCommon = new ColmenaError("0",type,"Not yet obtained");
//		// connection and query
//		Connection connection = DataBaseManager.getInstance().getConnection();
//		String url = "select error_id, count(*) as total from "
//				+ colmenaMarkerTable
//				+ " where type=? and user_id=? group by error_id order by total desc limit 1";
//		try {
//			// query elaboration in jdbc
//			PreparedStatement ps = connection.prepareStatement(url);
//			ps.setString(1, type);
//			ps.setString(2, userId);
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				String errorCode = rs.getString("error_id");
//				int times = rs.getInt("total");
//				String templateName = obtainTemplateName(errorCode);
//				templateName += " (" + times + " times )";
//				mostCommon = new ColmenaError(errorCode, ColmenaMarker.ERROR,
//						templateName);
//			}
//			ps.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mostCommon;
//	}
//
//	public String obtainTemplateName(String errorCode) {
//		// connection and query
//		Connection connection = DataBaseManager.getInstance().getConnection();
//		String url = "select template_message from " + tableName
//				+ " where id = ? ";
//		try {
//			// query elaboration in jdbc
//			PreparedStatement ps = connection.prepareStatement(url);
//			ps.setString(1, errorCode);
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				return rs.getString("template_message");
//
//			}
//			ps.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
// }
