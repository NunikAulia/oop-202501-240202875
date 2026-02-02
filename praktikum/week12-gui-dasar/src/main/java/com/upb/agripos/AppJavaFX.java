package com.upb.agripos;

import com.upb.agripos.view.ProductFormView;
import com.upb.agripos.controller.ProductController;
import com.upb.agripos.dao.*;
import com.upb.agripos.service.ProductService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage stage) {
        // 1. Tampilkan Jendela Dulu
        ProductFormView view = new ProductFormView();
        Scene scene = new Scene(view, 400, 550);
        stage.setTitle("Agri-POS - Week 12");
        stage.setScene(scene);
        stage.show();

        // 2. Coba Hubungkan Database (Gunakan try-catch agar jika gagal, app tidak mati)
        try {
            // GANTI PASSWORD SESUAI LAPTOP KAMU
            Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/agripos", "postgres", "1234"
            );

            // Inisialisasi MVC
            ProductDAO dao = new ProductDAOImpl(conn);
            ProductService service = new ProductService(dao);
            new ProductController(service, view);

            System.out.println("Koneksi Database Berhasil!");
        } catch (Exception e) {
            System.err.println("Database Error (Aplikasi tetap jalan): " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}