package com.upb.agripos;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.ProductTableView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Aplikasi Main untuk Agri-POS GUI Lanjutan
 * Week 13: TableView dan Lambda Expression
 * Implements event-driven programming dengan JavaFX
 * Mengintegrasikan MVC architecture dengan backend DAO/Service
 */
public class AppJavaFX extends Application {
    private Connection connection;
    private ProductController controller;
    private ProductTableView view;

    /**
     * Main method untuk menjalankan aplikasi
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start method dipanggil oleh JavaFX framework
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // 1. Inisialisasi database connection
            initializeDatabase();

            // 2. Setup MVC components
            setupMVC();

            // 3. Buat scene
            Scene scene = new Scene(view, 800, 750);

            // 4. Setup stage
            primaryStage.setTitle("Agri-POS - Kelola Produk (Advanced)");
            primaryStage.setScene(scene);
            primaryStage.show();

            // 5. Load initial data
            view.loadInitialData();

        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Inisialisasi koneksi ke database PostgreSQL
     * Fallback ke H2 in-memory jika PostgreSQL tidak tersedia
     */
    private void initializeDatabase() throws Exception {
        try {
            // Try PostgreSQL first
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/agripos";
            String user = "postgres";
            String password = "postgres";

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("✓ PostgreSQL database connected successfully");

        } catch (Exception postgresError) {
            System.out.println("PostgreSQL connection failed, trying H2 fallback...");
            try {
                // Fallback to H2 in-memory database
                Class.forName("org.h2.Driver");
                String url = "jdbc:h2:mem:agripos;MODE=PostgreSQL";
                
                connection = DriverManager.getConnection(url);
                System.out.println("✓ H2 in-memory database connected successfully");
                
                // Create tables for H2
                initializeH2Tables();
                
            } catch (Exception h2Error) {
                System.err.println("Both PostgreSQL and H2 connection failed");
                postgresError.printStackTrace();
                h2Error.printStackTrace();
                throw new Exception("Database connection failed - no database available", h2Error);
            }
        }
    }

    /**
     * Inisialisasi table struktur untuk H2 database
     */
    private void initializeH2Tables() throws Exception {
        try (java.sql.Statement stmt = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS products (" +
                    "code VARCHAR(20) PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "price DECIMAL(12, 2) NOT NULL, " +
                    "stock INT NOT NULL" +
                    ")";
            stmt.execute(createTableSQL);
            System.out.println("  ✓ Products table created");
            
            // Insert sample data
            String insertSQL = "INSERT INTO products (code, name, price, stock) VALUES (?, ?, ?, ?)";
            try (java.sql.PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                ps.setString(1, "PROD001");
                ps.setString(2, "Benih Padi Premium");
                ps.setDouble(3, 50000.0);
                ps.setInt(4, 100);
                ps.executeUpdate();
                
                ps.setString(1, "PROD002");
                ps.setString(2, "Pupuk NPK");
                ps.setDouble(3, 15000.0);
                ps.setInt(4, 50);
                ps.executeUpdate();
            }
            System.out.println("  ✓ Sample data loaded");
            
        } catch (Exception e) {
            System.err.println("Error initializing H2 tables: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Setup Model-View-Controller pattern
     * Mendemonstrasikan Dependency Injection Principle
     */
    private void setupMVC() throws Exception {
        // Model & Data Access Layer
        ProductDAOImpl dao = new ProductDAOImpl(connection);

        // Service Layer (Business Logic)
        ProductService service = new ProductService(dao);

        // Controller Layer
        controller = new ProductController(service);

        // View Layer (dengan TableView)
        view = new ProductTableView(controller);
    }

    /**
     * Cleanup resources ketika aplikasi ditutup
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Database connection closed");
        }
    }
}
