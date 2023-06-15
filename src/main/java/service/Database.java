package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    /*
	 * Database connection
     */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String NAME = "EasyHairCare";
    private static final String CONNECTION = "jdbc:mysql://localhost/";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    private Database() {

    }

    /*
	 * Create the database if it does not exist
     */
    public static void CreatedDatabase() {
        try {
            Connection connection = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String s = "CREATE DATABASE " + NAME;
            statement.executeUpdate(s);

        } catch (SQLException e) {
        }
    }

    /*
	 * Get connection
     */
    public static Connection getDBConnection() {
        Connection connection = null;

        CreatedDatabase();

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException exception) {
            System.out.println("Driver not found");
            return connection;
        }

        try {
            connection = DriverManager.getConnection(CONNECTION + NAME, USER, PASSWORD);
            return connection;
        } catch (SQLException exception) {
            System.out.println("Invalid connection");
        }

        return connection;
    }
}
