package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.UserLogged;
import model.Admin;
import model.Provider;

/*
 * ProviderDaoService class
 */
public class ProviderDaoService {

	/*
	 * Get all providers by location
	 */
	public void selectAllProviders(int location) throws SQLException {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet;

		UserLogged.providers.clear();

		try {
			connection = ConnectSQLService.getDBConnection();
			String query = "SELECT Account.id, Account.name FROM Provider INNER JOIN Account ON Provider.idProvider = Account.id where Provider.location = "
					+ location;
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Provider provider = new Provider();

				provider.setIdProvider(resultSet.getInt(1));
				provider.setLocation(location);
				provider.setName(resultSet.getString(2));
				UserLogged.providers.add(provider);
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

	/*
	 * Get all providers
	 */
	public void selectAllProviders() throws SQLException {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet;

		UserLogged.adminAcceptProviders.clear();

		try {
			connection = ConnectSQLService.getDBConnection();
			String query = "SELECT AccountWaiting.id, Account.name, AccountWaiting.location FROM AccountWaiting INNER JOIN Account ON AccountWaiting.id = Account.id where AccountWaiting.accepted = 0";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Admin provider = new Admin();
				provider.setId(resultSet.getInt(1));
				provider.setName(resultSet.getString(2));
				provider.setLocation(resultSet.getInt(3));
				UserLogged.adminAcceptProviders.add(provider);
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

	/*
	 * Update provider
	 */
	public boolean updateProvider(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "UPDATE AccountWaiting SET accepted = 1 WHERE id = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
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

	/*
	 * Remove provider
	 */
	public boolean removeProvider(int id) throws SQLException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "DELETE FROM AccountWaiting WHERE id = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
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
