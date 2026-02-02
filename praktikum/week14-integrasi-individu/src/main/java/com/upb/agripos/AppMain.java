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
import java.sql.Statement;

/**
 * Main application entry point for Agri-POS system
 * Demonstrates integration of OOP, Database, and GUI (Week 14)
 */
public class AppMain {
    // PostgreSQL Database configuration
    private static final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/agripos";
    private static final String POSTGRES_USER = "postgres";
    private static final String POSTGRES_PASSWORD = "1234";
    
    // H2 In-Memory Database configuration (fallback)
    private static final String H2_URL = "jdbc:h2:mem:agripos;DB_CLOSE_DELAY=-1";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "";

    public static void main(String[] args) {
        // Display identity
        System.out.println("Hello World, I am [Nunik Aulia Primadani]-[240202875]");
        System.out.println("=== Agri-POS Application Started ===");
        System.out.println("Week 14 - Individual Integration (OOP + Database + GUI)");
        System.out.println("=====================================\n");

        try {
            // Initialize database connection
            Connection connection = initializeDatabase();
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

    /**
     * Initialize database connection
     * Tries PostgreSQL first, falls back to H2 in-memory if PostgreSQL fails
     */
    private static Connection initializeDatabase() throws Exception {
        // Try PostgreSQL first
        try {
            System.out.println("Attempting to connect to PostgreSQL...");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(POSTGRES_URL, POSTGRES_USER, POSTGRES_PASSWORD);
            System.out.println("✓ Connected to PostgreSQL database");
            return conn;
        } catch (Exception postgresError) {
            System.out.println("⚠ PostgreSQL connection failed: " + postgresError.getMessage());
            System.out.println("Falling back to H2 in-memory database...");
            
            // Fallback to H2
            try {
                Class.forName("org.h2.Driver");
                Connection conn = DriverManager.getConnection(H2_URL, H2_USER, H2_PASSWORD);
                System.out.println("✓ Connected to H2 in-memory database");
                
                // Initialize schema if needed
                initializeH2Schema(conn);
                
                return conn;
            } catch (Exception h2Error) {
                System.err.println("✗ Both PostgreSQL and H2 connection failed");
                throw new Exception("Database initialization failed", h2Error);
            }
        }
    }

    /**
     * Initialize H2 database schema
     */
    private static void initializeH2Schema(Connection conn) throws Exception {
        try (Statement stmt = conn.createStatement()) {
            // Create products table if it doesn't exist
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS products (" +
                "  code VARCHAR(50) PRIMARY KEY," +
                "  name VARCHAR(255) NOT NULL," +
                "  price DOUBLE NOT NULL," +
                "  stock INT NOT NULL" +
                ")"
            );
            
            // Insert sample data
            stmt.execute("INSERT INTO products VALUES ('P001', 'Beras 5kg', 75000.0, 100)");
            stmt.execute("INSERT INTO products VALUES ('P002', 'Gula 1kg', 12000.0, 50)");
            stmt.execute("INSERT INTO products VALUES ('P003', 'Minyak 2L', 28000.0, 30)");
            
            conn.commit();
            System.out.println("✓ H2 schema initialized with sample data");
        }
    }
}
