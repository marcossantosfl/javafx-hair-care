package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * NotificationService class
 */
public class NotificationService {
	
	/*
	 * Get notification
	 */
	public int getNotification(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "SELECT Notify FROM Notification WHERE idAccountNotify = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				
				return resultSet.getInt(1);
			}
			
		} catch (SQLException exception) {
			exception.printStackTrace();
			
		} finally {
			if (null != statement) {
				statement.close();
			}

			if (null != connection) {
				connection.close();
			}
		}

		return 0;
	}
	
	/*
	 * Delete notification
	 */
	public void deleteNotification(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "DELETE FROM Notification WHERE idAccountNotify = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			statement.executeUpdate();
			connection.commit();
			resultSet = statement.getResultSet();

		} catch (SQLException exception) {
			exception.getMessage();
			if (null != connection) {
				connection.rollback();
			}
		} finally {
			if (null != resultSet) {
				resultSet.close();
			}

			if (null != statement) {
				statement.close();
			}

			if (null != connection) {
				connection.close();
			}
		}
	}

}
