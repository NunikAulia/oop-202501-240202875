package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;

/**
 * Controller untuk menangani logic aplikasi
 * Menghubungkan View dan Service layer
 * Menerapkan prinsip Single Responsibility Principle (SRP)
 */
public class ProductController {
    private final ProductService productService;

    /**
     * Constructor dengan dependency injection
     * @param productService Service untuk Product
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Menambah produk baru
     * Dipanggil dari View ketika tombol "Tambah" diklik
     * 
     * @param code Kode produk
     * @param name Nama produk
     * @param price Harga produk
     * @param stock Stok produk
     * @return true jika berhasil, false jika gagal
     */
    public boolean addProduct(String code, String name, String price, String stock) {
        try {
            // Validasi tidak boleh kosong
            if (code == null || code.trim().isEmpty() ||
                name == null || name.trim().isEmpty() ||
                price == null || price.trim().isEmpty() ||
                stock == null || stock.trim().isEmpty()) {
                return false;
            }

            // Parse numeric fields
            double priceValue = Double.parseDouble(price);
            int stockValue = Integer.parseInt(stock);

            // Buat object Product
            Product product = new Product(code, name, priceValue, stockValue);

            // Panggil service untuk insert
            productService.insert(product);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mengambil semua data produk
     * Dipanggil saat aplikasi dimulai atau refresh
     * 
     * @return Array berisi semua produk
     */
    public Product[] getAllProducts() {
        try {
            return productService.findAll().toArray(new Product[0]);
        } catch (Exception e) {
            System.err.println("Error fetching products: " + e.getMessage());
            return new Product[0];
        }
    }

    /**
     * Mencari produk berdasarkan kode
     * @param code Kode produk
     * @return Product jika ditemukan, null jika tidak
     */
    public Product findByCode(String code) {
        try {
            return productService.findByCode(code);
        } catch (Exception e) {
            System.err.println("Error finding product: " + e.getMessage());
            return null;
        }
    }

    /**
     * Update data produk
     * @param code Kode produk
     * @param name Nama produk baru
     * @param price Harga baru
     * @param stock Stok baru
     * @return true jika berhasil, false jika gagal
     */
    public boolean updateProduct(String code, String name, String price, String stock) {
        try {
            if (code == null || code.trim().isEmpty() ||
                name == null || name.trim().isEmpty() ||
                price == null || price.trim().isEmpty() ||
                stock == null || stock.trim().isEmpty()) {
                return false;
            }

            double priceValue = Double.parseDouble(price);
            int stockValue = Integer.parseInt(stock);

            Product product = new Product(code, name, priceValue, stockValue);
            productService.update(product);
            return true;
        } catch (Exception e) {
            System.err.println("Error updating product: " + e.getMessage());
            return false;
        }
    }

    /**
     * Menghapus produk
     * @param code Kode produk yang akan dihapus
     * @return true jika berhasil, false jika gagal
     */
    public boolean deleteProduct(String code) {
        try {
            if (code == null || code.trim().isEmpty()) {
                return false;
            }
            productService.delete(code);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting product: " + e.getMessage());
            return false;
        }
    }
}
