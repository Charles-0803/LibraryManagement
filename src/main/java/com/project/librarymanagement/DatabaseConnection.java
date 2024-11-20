package com.project.librarymanagement;

import io.github.cdimascio.dotenv.Dotenv; // Importing the Dotenv library to load environment variables

import java.sql.Connection; // Importing the SQL Connection class
import java.sql.DriverManager; // Importing DriverManager to establish a connection
import java.sql.SQLException; // Importing SQLException to handle SQL exceptions

public class DatabaseConnection {
    private static Dotenv dotenv = Dotenv.load(); // Loading environment variables from the .env file
    private static final String url = dotenv.get("DB_URL"); // Getting the database URL from the environment variables
    private static final String user = dotenv.get("DB_USER");  // Getting the database username from the environment variables
    private static final String password = dotenv.get("DB_PASSWORD");  // Getting the database password from the environment variables

    // Method to establish and return a connection to the database
    public static Connection getConnection() throws SQLException {
        try {
            // Establishing a connection to the database using the loaded credentials
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn; // Returning the established connection
        } catch (SQLException e) {
            // Printing an error message if the connection fails
            System.err.println("Database connection failed: " + e.getMessage());
            throw e; // Rethrowing the exception to be handled by the caller
        }
    }
}
