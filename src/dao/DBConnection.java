package dao;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3307/quiz_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static volatile boolean driverAvailable = true;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            driverAvailable = false;
            System.err.println("MySQL JDBC driver not found. Falling back to built-in quiz data.");
        }
    }

    public static Connection getConnection() {
        if (!driverAvailable) {
            return null;
        }

        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.err.println("Database connection unavailable: " + e.getMessage());
            return null;
        }
    }
}
