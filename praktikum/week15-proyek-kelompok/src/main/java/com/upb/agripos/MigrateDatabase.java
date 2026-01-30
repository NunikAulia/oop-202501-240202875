package com.upb.agripos;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.upb.agripos.dao.JdbcConnection;
import com.upb.agripos.dao.JdbcProductDAO;
import com.upb.agripos.model.Product;

public class MigrateDatabase {
    public static void main(String[] args) {
        System.out.println("=== DATABASE MIGRATION & SEEDING ===\n");
        
        try {
            Connection conn = JdbcConnection.getConnection();
            if (conn == null) {
                System.err.println("❌ Tidak bisa connect ke database!");
                return;
            }
            
            System.out.println("✅ Connected to database\n");
            
            // Check if category column exists
            String checkSql = "SELECT column_name FROM information_schema.columns WHERE table_name='product' AND column_name='category'";
            try (var stmt = conn.createStatement();
                 var rs = stmt.executeQuery(checkSql)) {
                
                if (!rs.next()) {
                    System.out.println("❌ Column 'category' tidak ditemukan!");
                    System.out.println("➕ Menambahkan column 'category'...\n");
                    
                    String alterSql = "ALTER TABLE product ADD COLUMN category VARCHAR(50)";
                    try (PreparedStatement ps = conn.prepareStatement(alterSql)) {
                        ps.execute();
                        System.out.println("✅ Column 'category' berhasil ditambahkan!\n");
                    }
                } else {
                    System.out.println("✅ Column 'category' sudah ada di database\n");
                }
            }
            
            conn.close();
            
            // Insert seed data
            System.out.println("=== INSERTING PRODUCT DATA ===\n");
            JdbcProductDAO dao = new JdbcProductDAO();
            
            Product[] products = {
                createProduct("P001", "Beras 10kg", "Pangan", 12000, 50),
                createProduct("P002", "Gula 1kg", "Pangan", 15000, 30),
                createProduct("P003", "Minyak Goreng 2L", "Pangan", 25000, 25),
                createProduct("P004", "Telur 1kg", "Pangan", 18000, 40),
                createProduct("P005", "Tepung Terigu 1kg", "Pangan", 8000, 20)
            };
            
            for (Product p : products) {
                try {
                    dao.save(p);
                    System.out.println("✅ " + p.getCode() + ": " + p.getName());
                } catch (Exception e) {
                    if (e.getMessage().contains("duplicate")) {
                        System.out.println("⚠️  " + p.getCode() + ": Sudah ada");
                    } else {
                        System.err.println("❌ " + p.getCode() + ": " + e.getMessage());
                    }
                }
            }
            
            System.out.println("\n✅ Migration & Seeding completed!");
            
        } catch (Exception e) {
            System.err.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static Product createProduct(String code, String name, String category, double price, int stock) {
        Product p = new Product(code, name, price, stock);
        p.setCategory(category);
        return p;
    }
}