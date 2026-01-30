package com.upb.agripos;

import java.sql.Connection;

import com.upb.agripos.dao.JdbcConnection;

public class TestConnection {
    public static void main(String[] args) {
        Connection conn = JdbcConnection.getConnection();
        if (conn != null) {
            System.out.println("KONEKSI DATABASE BERHASIL ✅");
        }
    }
}