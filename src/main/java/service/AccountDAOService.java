package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Account;
import model.AccountLogged;

public class AccountDAOService {
	
	public int saveAccount(Account account, boolean toggle, int location) throws SQLException 
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DatabaseService.getDBConnection();
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
					statement = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
					statement.setInt(1, resultSet.getInt(1));
					statement.setInt(2, location);
					statement.setInt(3, 0);
					statement.executeUpdate();
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

			if (null != connection) {
				connection.close();
			}
		}

		return 0;
	}
	
	public int saveAccountRole(Account account, int role) throws SQLException 
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DatabaseService.getDBConnection();
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
	
	public boolean AccountExists(String email, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = DatabaseService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "SELECT Account.id, AccountRole.idRole FROM AccountRole INNER JOIN Account ON AccountRole.idAccount = Account.id where Account.email = ? and Account.password = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				AccountLogged.accountId = resultSet.getInt(1);
				AccountLogged.accountRole = resultSet.getInt(2);
				
				if(resultSet.getInt(2) == 1)
				{
					String query1 = "SELECT AccountWaiting.accepted FROM AccountWaiting INNER JOIN Account ON AccountWaiting.id = Account.id WHERE Account.id = ?";
					statement = connection.prepareStatement(query1);
					statement.setInt(1, resultSet.getInt(1));
					resultSet = statement.executeQuery();
					
					if(resultSet.next()) {
						System.out.print(resultSet.getInt(1));
					    AccountLogged.accountAccepted = resultSet.getInt(1);
					}
				}
				return true;
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
