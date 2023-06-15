package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.AccountLogged;
import model.DateTimeProvider;

public class DateTimeProviderDAO {

	public void selectAllProviders(int available, int idProvider) throws SQLException {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet;
		
		try {
			connection = DatabaseService.getDBConnection();
			String query = "SELECT id, idCustumer, idProvider, Year, Month, Day, Hour, Minute, Available FROM DateTimeProvider WHERE Available = "
					+ available + " and idProvider = " + idProvider;
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				DateTimeProvider provider = new DateTimeProvider();
				provider.setId(resultSet.getInt(1));
				provider.setIdCustumer(resultSet.getInt(2));
				provider.setIdProvider(resultSet.getInt(3));
				provider.setYear(resultSet.getInt(4));
				provider.setMonth(resultSet.getInt(5));
				provider.setDay(resultSet.getInt(6));
				provider.setHour(resultSet.getInt(7));
				provider.setMinute(resultSet.getInt(8));
				provider.setAvailable(resultSet.getInt(9));
				AccountLogged.datetimeproviders.add(provider);
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
	}
	
	public boolean saveProvider(int idCustumer, int id) throws SQLException 
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DatabaseService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "UPDATE DateTimeProvider SET idCustumer = ?, Available = 0 WHERE id = ?;";
			statement = connection.prepareStatement(query);
			statement.setInt(1, idCustumer);
			statement.setInt(2, id);
			statement.executeUpdate();
			connection.commit();
			resultSet = statement.getResultSet();
			
			return true;
			
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

		return false;
	}

}
