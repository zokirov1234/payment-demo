package com.company.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface Base {

    default Connection getConnection() {
        // Provide the driver class name
        String driver = "org.postgresql.Driver";

        try {
            // Load the driver class
            Class.forName(driver);

            // Provide the connection URL, username, and password
            String url = "jdbc:postgresql://localhost:5432/payment";
            String username = "postgres";
            String password = "74809903";

            // Establish the connection
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
