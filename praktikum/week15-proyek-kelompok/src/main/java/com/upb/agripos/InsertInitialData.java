package com.upb.agripos;

import com.upb.agripos.dao.JdbcProductDAO;
import com.upb.agripos.model.Product;

public class InsertInitialData {
    public static void main(String[] args) {
        System.out.println("=== INSERT INITIAL DATA ===\n");
        
        try {
            JdbcProductDAO dao = new JdbcProductDAO();
            
            // Data yang akan ditambahkan
            Product[] products = {
                new Product("P004", "Telur 1kg", 18000, 40),
                new Product("P005", "Tepung Terigu 1kg", 8000, 20)
            };
            
            for (Product p : products) {
                p.setCategory("Pangan");
            }
            
            // Insert semua produk
            for (Product p : products) {
                try {
                    System.out.println("Inserting: " + p.getCode() + " - " + p.getName());
                    dao.save(p);
                    System.out.println("✅ " + p.getCode() + " berhasil ditambahkan!\n");
                } catch (Exception e) {
                    if (e.getMessage().contains("duplicate")) {
                        System.out.println("⚠️  " + p.getCode() + " sudah ada di database\n");
                    } else {
                        System.err.println("❌ Error: " + e.getMessage());
                    }
                }
            }
            
            // Verify
            System.out.println("=== ALL PRODUCTS IN DATABASE ===\n");
            var allProducts = dao.findAll();
            allProducts.forEach(prod -> 
                System.out.println(prod.getCode() + ": " + prod.getName() + 
                                 " | Rp " + prod.getPrice() + 
                                 " | Stok: " + prod.getStock())
            );
            
            System.out.println("\n✅ Data insertion completed!");
            
        } catch (Exception e) {
            System.err.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}