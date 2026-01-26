package com.upb.agripos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    private DatabaseConnection() {} 

    public static Connection getInstance() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // Pastikan port 5432 dan nama database db_agripos sudah sesuai
            String url = "jdbc:postgresql://localhost:5432/agripos";
            String user = "postgres"; 
            
            // MASUKKAN PASSWORD ANDA DI SINI
            // Jika saat install PostgreSQL anda memasukkan 'admin123', maka tulis 'admin123'
            String password = "242405"; 

            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver PostgreSQL tidak ditemukan! Periksa pom.xml anda.", e);
            }
        }
        return connection;
    }
}