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
     */
    private void initializeDatabase() throws Exception {
        try {
            // Load driver JDBC
            Class.forName("org.postgresql.Driver");

            // Setup koneksi
            String url = "jdbc:postgresql://localhost:5432/agripos";
            String user = "postgres";
            String password = "postgres";

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully");

        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL driver not found");
            throw new Exception("Database driver not available", e);
        } catch (Exception e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            throw new Exception("Database connection failed", e);
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
