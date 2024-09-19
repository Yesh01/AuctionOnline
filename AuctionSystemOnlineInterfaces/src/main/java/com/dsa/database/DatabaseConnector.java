package com.dsa.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/auction_db";  // Ensure this matches your DB setup
    private static final String USER = "root";  // Use your actual DB username
    private static final String PASS = "password";  // Use your actual DB password

    public Connection getConnection() {
        Connection connection = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");  // This line ensures the driver is loaded
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection to the database successful.");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

