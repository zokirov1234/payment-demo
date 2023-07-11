package com.company.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface Base {

    default Connection getConnection() {

        String driver = "org.postgresql.Driver";

        try {

            Class.forName(driver);


            String url = "jdbc:postgresql://localhost:5432/payment";
            String username = "postgres";
            String password = "74809903";


            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
