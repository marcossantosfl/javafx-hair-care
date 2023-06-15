package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import model.User;
import model.UserLogged;


/*
 * UserDaoService class
 */
public class UserDaoService {
	
	/*
	 * New account
	 */
	public int newAccount(User account, boolean toggle, int location) throws SQLException 
	{
		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "INSERT INTO Account(name, email, number, password) VALUES(?, ?, ?, ?);";
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, account.getName());
			statement.setString(2, account.getEmail());
			statement.setString(3, account.getNumber());
			statement.setString(4, account.getPassword());
			statement.executeUpdate();
			connection.commit();
			resultSet = statement.getGeneratedKeys();
			
			if (resultSet.next()) {
				if(toggle)
				{
					String query2 = "INSERT INTO AccountWaiting(id, location, accepted) VALUES(?, ?, ?)";
					statement2 = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
					statement2.setInt(1, resultSet.getInt(1));
					statement2.setInt(2, location);
					statement2.setInt(3, 0);
					statement2.executeUpdate();
					connection.commit();
				}
				return resultSet.getInt(1);
			}
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
			
			if (null != statement2) {
				statement.close();
			}

			if (null != connection) {
				connection.close();
			}
		}

		return 0;
	}
	
	/*
	 * Save role
	 */
	public int saveAccountRole(User account, int role) throws SQLException 
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "INSERT INTO AccountRole(`idAccount`,`IdRole`) VALUES(?, ?);";
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, account.getId());
			statement.setInt(2, role);
			statement.executeUpdate();
			connection.commit();
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

		return 0;
	}
	
	/*
	 * Check if userExists
	 */
	public boolean AccountExists(String email, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;

		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "SELECT Account.id, AccountRole.idRole, Account.password FROM AccountRole INNER JOIN Account ON AccountRole.idAccount = Account.id where Account.email = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				UserLogged.userId = resultSet.getInt(1);
				UserLogged.userRole = resultSet.getInt(2);
				
				if(resultSet.getInt(2) == 1)
				{
					String query1 = "SELECT AccountWaiting.accepted FROM AccountWaiting INNER JOIN Account ON AccountWaiting.id = Account.id WHERE Account.id = ?";
					statement2 = connection.prepareStatement(query1);
					statement2.setInt(1, resultSet.getInt(1));
					resultSet = statement2.executeQuery();
					
					if(resultSet.next()) {
					    UserLogged.userAccepted = resultSet.getInt(1);
					}
				}
				
				/*
				 * Check password encrypted
				 */
				if(new BCryptPasswordEncoder().matches(password, resultSet.getString(3)))
				{
					return true;
				}
			}

			return false;
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

		return false;
	}
}
