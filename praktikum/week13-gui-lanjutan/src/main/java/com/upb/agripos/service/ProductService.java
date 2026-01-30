package com.upb.agripos.service;

import com.upb.agripos.model.Product;
import com.upb.agripos.dao.ProductDAOImpl;
import java.util.List;

/**
 * Service Layer untuk Product
 * Menangani business logic dan validasi
 * Berfungsi sebagai intermediary antara View/Controller dengan DAO
 * Implementasi dari Dependency Inversion Principle (DIP)
 */
public class ProductService {
    private final ProductDAOImpl productDAO;

    /**
     * Constructor dengan dependency injection
     * @param productDAO Data Access Object untuk Product
     */
    public ProductService(ProductDAOImpl productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * Menambah produk baru dengan validasi
     * @param product Produk yang akan ditambahkan
     * @throws Exception jika ada error di database atau validasi
     */
    public void insert(Product product) throws Exception {
        // Validasi input
        if (product.getCode() == null || product.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Kode produk tidak boleh kosong");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nama produk tidak boleh kosong");
        }
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Harga harus lebih dari 0");
        }
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif");
        }

        // Panggil DAO untuk insert
        productDAO.insert(product);
    }

    /**
     * Mencari produk berdasarkan kode
     * @param code Kode produk
     * @return Product jika ditemukan, null jika tidak
     * @throws Exception jika ada error di database
     */
    public Product findByCode(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode produk tidak boleh kosong");
        }
        return productDAO.findByCode(code);
    }

    /**
     * Mengambil semua data produk
     * @return List berisi semua produk
     * @throws Exception jika ada error di database
     */
    public List<Product> findAll() throws Exception {
        return productDAO.findAll();
    }

    /**
     * Update data produk yang sudah ada
     * @param product Produk dengan data terbaru
     * @throws Exception jika ada error di database atau validasi
     */
    public void update(Product product) throws Exception {
        // Validasi input
        if (product.getCode() == null || product.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Kode produk tidak boleh kosong");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nama produk tidak boleh kosong");
        }
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Harga harus lebih dari 0");
        }
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif");
        }

        productDAO.update(product);
    }

    /**
     * Menghapus produk berdasarkan kode
     * @param code Kode produk yang akan dihapus
     * @throws Exception jika ada error di database
     */
    public void delete(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode produk tidak boleh kosong");
        }
        productDAO.delete(code);
    }
}
