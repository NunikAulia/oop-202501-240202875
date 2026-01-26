package com.upb.agripos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.model.Product;

public class MainDAOTest {
    public static void main(String[] args) {
        // Menampilkan Identitas sesuai permintaan Modul
        System.out.println("====================================================");
        System.out.println("Praktikum Week 11 - Data Access Object (DAO)");
        System.out.println("Nama : Nunik Aulia Primadani");
        System.out.println("NIM  : 240202875");
        System.out.println("====================================================");
        
        // Sesuaikan nama database 'agripos' dan password pgAdmin Anda
        String url = "jdbc:postgresql://localhost:5432/db_agripos";
        String user = "postgres";
        String pass = "MASUKKAN_PASSWORD_PGADMIN_DISINI"; 

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            // Inisialisasi DAO
            ProductDAO dao = new ProductDAOImpl(conn);

            // --- 1. Operasi CREATE (Insert) ---
            System.out.println("\n[1] Menambah data produk baru...");
            Product newProduct = new Product("P01", "Pupuk Organik", 25000, 10);
            dao.insert(newProduct);
            System.out.println("Berhasil menambah: " + newProduct.getName());

            // --- 2. Operasi UPDATE ---
            System.out.println("\n[2] Mengupdate data produk P01...");
            Product updatedProduct = new Product("P01", "Pupuk Organik Premium", 30000, 8);
            dao.update(updatedProduct);
            System.out.println("Berhasil update ke: " + updatedProduct.getName());

            // --- 3. Operasi READ (Find by Code) ---
            System.out.println("\n[3] Mencari produk dengan kode P01...");
            Product p = dao.findByCode("P01");
            if (p != null) {
                System.out.println("Hasil Pencarian -> Nama: " + p.getName() + " | Harga: " + p.getPrice());
            }

            // --- 4. Operasi READ (Find All) ---
            System.out.println("\n[4] Daftar Semua Produk di Database:");
            List<Product> list = dao.findAll();
            for (Product item : list) {
                System.out.println(">> " + item.getCode() + " | " + item.getName() + " | Stok: " + item.getStock());
            }

            // --- 5. Operasi DELETE ---
            // Silakan uncomment baris di bawah ini jika ingin menguji fitur hapus
            // System.out.println("\n[5] Menghapus produk P01...");
            // dao.delete("P01");
            // System.out.println("Data berhasil dihapus.");

            System.out.println("\n====================================================");
            System.out.println("Status: Semua Operasi CRUD Berhasil Dijalankan.");

        } catch (Exception e) {
            System.err.println("\nTerjadi Kesalahan: " + e.getMessage());
            System.err.println("Pastikan database 'agripos' sudah dibuat dan password benar.");
        }
    }
}