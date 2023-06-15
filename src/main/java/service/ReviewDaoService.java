package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Review;
import model.UserLogged;

/*
 * ReviewDaoService
 */
public class ReviewDaoService {

	public boolean insertReview(Review review) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "INSERT INTO Review(idDate, idProviderReview, idAccountReview, StarGiven) VALUES (?,?,?,0);";
			statement = connection.prepareStatement(query);
			statement.setInt(1, review.getIdDate());
			statement.setInt(2, review.getIdProviderReview());
			statement.setInt(3, review.getIdAccountReview());
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
	 * Incompleted Reviews
	 */
	public void selectAllReviewIncompleted(int id) throws SQLException {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet;

		UserLogged.reviews.clear();

		try {
			connection = ConnectSQLService.getDBConnection();
			String query = "SELECT Review.id, Review.idDate, Review.idProviderReview, Account.name FROM Review INNER JOIN Account ON Review.idProviderReview = Account.id WHERE Review.idAccountReview = "
					+ id + " AND Review.StarGiven = 0";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Review review = new Review();
				review.setId(resultSet.getInt(1));
				review.setIdDate(resultSet.getInt(2));
				review.setIdProviderReview(resultSet.getInt(3));
				review.setProviderName(resultSet.getString(4));
				UserLogged.reviews.add(review);
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
	 * Submit review
	 */
	public boolean submitReview(int id, int given) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectSQLService.getDBConnection();
			connection.setAutoCommit(false);
			String query = "UPDATE Review SET StarGiven = ? WHERE id = ?;";
			statement = connection.prepareStatement(query);
			statement.setInt(1, given);
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
	 * Get all stars
	 */
	public int selectAllStars(int id) throws SQLException {

		int totalStar = 0;
		int count = 0;

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet;

		UserLogged.dates.clear();

		try {
			connection = ConnectSQLService.getDBConnection();
			String query = "SELECT StarGiven FROM Review WHERE idProviderReview = " + id + " AND StarGiven > 0";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				totalStar += resultSet.getInt(1);
				count++;
			}

			if (totalStar < 1) {
				totalStar = 0;
			} else {
				totalStar = totalStar / count;
			}

			return totalStar;

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

		return (totalStar / count);
	}

}
