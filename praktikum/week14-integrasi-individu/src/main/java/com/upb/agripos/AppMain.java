package com.upb.agripos;

import com.upb.agripos.controller.PosController;
import com.upb.agripos.dao.JdbcProductDAO;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.service.CartService;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.PosView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Main application entry point for Agri-POS system
 * Demonstrates integration of OOP, Database, and GUI (Week 14)
 */
public class AppMain {
    // Database configuration
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/agripos";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";

    public static void main(String[] args) {
        // Display identity
        System.out.println("Hello World, I am [Nama]-[NIM]");
        System.out.println("=== Agri-POS Application Started ===");
        System.out.println("Week 14 - Individual Integration (OOP + Database + GUI)");
        System.out.println("=====================================\n");

        try {
            // Initialize database connection
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("✓ Database connection successful");

            // Create DAO
            ProductDAO productDAO = new JdbcProductDAO(connection);
            System.out.println("✓ ProductDAO initialized");

            // Create Services
            ProductService productService = new ProductService(productDAO);
            CartService cartService = new CartService();
            System.out.println("✓ Services initialized");

            // Create Controller
            PosController controller = new PosController(productService, cartService);
            System.out.println("✓ Controller initialized");

            // Launch GUI
            SwingUtilities.invokeLater(() -> {
                PosView view = new PosView(controller);
                view.setVisible(true);
                System.out.println("✓ GUI launched successfully\n");
            });

        } catch (Exception e) {
            System.err.println("✗ Error initializing application:");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                    "Failed to initialize application: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
