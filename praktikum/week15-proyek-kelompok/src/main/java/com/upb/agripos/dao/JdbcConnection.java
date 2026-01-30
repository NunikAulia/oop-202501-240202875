package com.upb.agripos.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnection {

    private static final String URL =
        "jdbc:postgresql://localhost:5432/agripos";
    private static final String USER = "postgres";
    private static final String PASSWORD = "339521";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Koneksi database berhasil!");
            return conn;
        } catch (Exception e) {
            System.err.println("❌ ERROR koneksi database: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}