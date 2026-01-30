package com.upb.agripos;

import com.upb.agripos.dao.JdbcProductDAO;
import com.upb.agripos.model.Product;

public class TestInsertProduct {
    public static void main(String[] args) {
        System.out.println("=== INSERT P004 & P005 ===\n");
        
        try {
            JdbcProductDAO productDAO = new JdbcProductDAO();

            // Insert P004
            try {
                Product p4 = new Product("P004", "Telur 1kg", 18000, 40);
                p4.setCategory("Pangan");
                productDAO.insert(p4);
                System.out.println("✅ P004 berhasil ditambahkan");
            } catch (Exception e) {
                System.out.println("⚠️  P004: " + e.getMessage());
            }

            // Insert P005
            try {
                Product p5 = new Product("P005", "Tepung Terigu 1kg", 8000, 20);
                p5.setCategory("Pangan");
                productDAO.insert(p5);
                System.out.println("✅ P005 berhasil ditambahkan");
            } catch (Exception e) {
                System.out.println("⚠️  P005: " + e.getMessage());
            }

            System.out.println("\n=== ALL PRODUCTS IN DATABASE ===");
            productDAO.findAll().forEach(p -> 
                System.out.println(p.getCode() + ": " + p.getName() + " | Rp " + p.getPrice() + " | Stok: " + p.getStock())
            );
            
        } catch (Exception e) {
            System.err.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}