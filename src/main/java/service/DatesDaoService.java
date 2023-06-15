package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.UserLogged;
import model.Dates;

/*
 * DateDaoService class
 */
public class DatesDaoService {

	/*
	 * Get all the providers from database
	 */
	public void selectAllProviders(int available, int idProvider) throws SQLException {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet;

		UserLogged.dates.clear();
		try {
			connection = ConnectSQLService.getDBConnection();
			String query = "SELECT id, idCustumer, idProvider, Year, Month, Day, Hour, Minute, Available FROM DateTimeProvider WHERE Available = "
					+ available + " and idProvider = " + idProvider;
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Dates provider = new Dates();
				provider.setId(resultSet.getInt(1));
				provider.setIdCustumer(resultSet.getInt(2));
				provider.setIdProvider(resultSet.getInt(3));
				provider.setYear(resultSet.getInt(4));
				provider.setMonth(resultSet.getInt(5));
				provider.setDay(resultSet.getInt(6));
				provider.setHour(resultSet.getInt(7));
				provider.setMinute(resultSet.getInt(8));
				provider.setAvailable(resultSet.getInt(9));
				UserLogged.dates.add(provider);
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
	 * Get all the dates
	 */
	public void selectAllProvidersDates(int idProvider) throws SQLException {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet;

		UserLogged.dates.clear();

		try {
			connection = ConnectSQLService.getDBConnection();
			String query = "SELECT id, idCustumer, idProvider, Year, Month, Day, Hour, Minute, Available, Accept FROM DateTimeProvider WHERE Available = 1 AND idProvider = "
					+ idProvider + " AND Accept = 0";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Dates provider = new Dates();
				provider.setId(resultSet.getInt(1));
				provider.setIdCustumer(resultSet.getInt(2));
				provider.setIdProvider(resultSet.getInt(3));
				provider.setYear(resultSet.getInt(4));
				provider.setMonth(resultSet.getInt(5));
				provider.setDay(resultSet.getInt(6));
				provider.setHour(resultSet.getInt(7));
				provider.setMinute(resultSet.getInt(8));
				provider.setAvailable(resultSet.getInt(9));
				provider.setAccept(resultSet.getInt(10));
				UserLogged.dates.add(provider);
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
	 * Insert hours
	 */
	public boolean addProviderHours(int id, Dates dateTimeProvider) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "INSERT INTO DateTimeProvider(idCustumer,idProvider,Year,Month,Day,Hour,Minute,Available,Accept) VALUES (null,?,?,?,?,?,?,1,0);";
			statement = connection.prepareStatement(query);
			statement.setInt(1, UserLogged.userId);
			statement.setInt(2, dateTimeProvider.getYear());
			statement.setInt(3, dateTimeProvider.getMonth());
			statement.setInt(4, dateTimeProvider.getDay());
			statement.setInt(5, dateTimeProvider.getHour());
			statement.setInt(6, 0);
			statement.executeUpdate();
			connection.commit();
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				return true;
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

		return false;
	}

	/*
	 * Booking by customer
	 */
	public boolean addBookingByCustumer(int idCustumer, int id) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "UPDATE DateTimeProvider SET idCustumer = ?, Available = 0, Accept = 0 WHERE id = ?;";
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

	/*
	 * Get all bookings
	 */
	public void selectAllBookings(int id) throws SQLException {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet;

		UserLogged.dates.clear();

		try {
			connection = ConnectSQLService.getDBConnection();
			String query = "SELECT Account.name, DateTimeProvider.id, DateTimeProvider.idProvider, DateTimeProvider.Year, DateTimeProvider.Month, DateTimeProvider.Day, DateTimeProvider.Hour, DateTimeProvider.Minute, DateTimeProvider.Accept FROM DateTimeProvider INNER JOIN Account ON DateTimeProvider.idProvider = Account.id WHERE Available = 0 and idCustumer = "
					+ id;
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Dates provider = new Dates();
				provider.setProviderName(resultSet.getString(1));
				provider.setId(resultSet.getInt(2));
				provider.setIdCustumer(id);
				provider.setIdProvider(resultSet.getInt(3));
				provider.setYear(resultSet.getInt(4));
				provider.setMonth(resultSet.getInt(5));
				provider.setDay(resultSet.getInt(6));
				provider.setHour(resultSet.getInt(7));
				provider.setMinute(resultSet.getInt(8));
				provider.setAccept(resultSet.getInt(9));
				provider.setAvailable(0);
				UserLogged.dates.add(provider);
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
	 * Get all dates from provider
	 */
	public void selectAllDatesFromProvider(int id) throws SQLException {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet;

		UserLogged.dates.clear();

		try {
			connection = ConnectSQLService.getDBConnection();
			String query = "SELECT Account.name, DateTimeProvider.id, DateTimeProvider.idCustumer, DateTimeProvider.Year, DateTimeProvider.Month, DateTimeProvider.Day, DateTimeProvider.Hour, DateTimeProvider.Minute FROM DateTimeProvider INNER JOIN Account ON DateTimeProvider.idProvider = Account.id WHERE idProvider = "
					+ id;
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Dates provider = new Dates();
				provider.setProviderName(resultSet.getString(1));
				provider.setId(resultSet.getInt(2));
				provider.setIdCustumer(resultSet.getInt(3));
				provider.setYear(resultSet.getInt(4));
				provider.setMonth(resultSet.getInt(5));
				provider.setDay(resultSet.getInt(6));
				provider.setHour(resultSet.getInt(7));
				provider.setMinute(resultSet.getInt(8));
				UserLogged.dates.add(provider);
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
	 * Cancel booking
	 */
	public boolean cancelBooking(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "UPDATE DateTimeProvider SET Available = 1, Accept = 0, idCustumer = null WHERE id = ?";
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
	 * Delete date
	 */
	public boolean deleteDate(int year, int month, int day, int hour) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "DELETE FROM DateTimeProvider WHERE idProvider = ? AND Year = ? AND Month = ? AND Day = ? AND Hour = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, UserLogged.userId);
			statement.setInt(2, year);
			statement.setInt(3, month);
			statement.setInt(4, day);
			statement.setInt(5, hour);
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
	 * Person did not show up (update)
	 */
	public boolean updateNoShowUpProvider(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "UPDATE DateTimeProvider SET Available = 0, Accept = -3 WHERE id = ?;";
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
	 * Showed up (update)
	 */
	public boolean updateShowedUpProvider(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "UPDATE DateTimeProvider SET Available = 0, Accept = -2 WHERE id = ?";
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
	 * Set finished and close to review by customer
	 */
	public boolean finishedAndCloseReview(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "UPDATE DateTimeProvider SET Available = 0, Accept = -4 WHERE id = ?";
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
	 * Update accept provider
	 */
	public boolean updateAcceptProvider(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "UPDATE DateTimeProvider SET Available = 0, Accept = 1 WHERE id = ?";
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
	 * Deny booking
	 */
	public boolean updateDenyProvider(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "UPDATE DateTimeProvider SET Available = 1, Accept = -1, idCustumer = null WHERE id = ?;";
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
	 * Get all bookings from provider
	 */
	public void selectAllProviderBookings(int id) throws SQLException {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet;

		UserLogged.dates.clear();

		try {
			connection = ConnectSQLService.getDBConnection();
			String query = "SELECT DateTimeProvider.id, DateTimeProvider.Year, DateTimeProvider.Month, DateTimeProvider.Day, DateTimeProvider.Hour, DateTimeProvider.Minute, DateTimeProvider.idCustumer, Account.name  FROM DateTimeProvider INNER JOIN Account ON DateTimeProvider.idCustumer = Account.id WHERE Available = 0 AND Accept = 0 AND DateTimeProvider.idProvider = "
					+ id;
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Dates provider = new Dates();
				provider.setId(resultSet.getInt(1));
				provider.setYear(resultSet.getInt(2));
				provider.setMonth(resultSet.getInt(3));
				provider.setDay(resultSet.getInt(4));
				provider.setHour(resultSet.getInt(5));
				provider.setMinute(resultSet.getInt(6));
				provider.setIdCustumer(resultSet.getInt(7));
				provider.setProviderName(resultSet.getString(8));
				UserLogged.dates.add(provider);
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
}
