package ru.kpfu.itis.service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection conn;

    private static final String url = "jdbc:postgresql:itisevents";

    public static Connection getConnection(){
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(url);
                System.out.println("Connection to psql has been established.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection(){
        try {
            if (conn != null) {
                conn.close();
                conn = null;
                System.out.println("Connection to SQLite has been closed.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}


