package com.upb.agripos;

import com.upb.agripos.model.Product;
import com.upb.agripos.view.ConsoleView;
import com.upb.agripos.controller.ProductController;
import com.upb.agripos.config.DatabaseConnection;
import java.sql.Connection;

public class AppMVC {
    public static void main(String[] args) {
        System.out.println("Hello, I am [Nunik Aulia Primadani]-[240202875] (Week10)");

        try {
            // Test Singleton Connection
            Connection conn = DatabaseConnection.getInstance();
            if (conn != null) {
                System.out.println("Koneksi Database Berhasil!");
            }

            // Implementasi MVC
            Product product = new Product("P01", "Pupuk Organik");
            ConsoleView view = new ConsoleView();
            ProductController controller = new ProductController(product, view);
            
            controller.showProduct();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}