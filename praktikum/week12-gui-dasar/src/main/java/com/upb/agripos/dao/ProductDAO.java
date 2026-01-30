package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import java.util.List;

/**
 * Interface ProductDAO - Data Access Object
 * Mendefinisikan kontrak untuk operasi CRUD pada tabel products
 * Sesuai dengan prinsip Dependency Inversion Principle (DIP) dari SOLID
 */
public interface ProductDAO {
    /**
     * Insert produk baru ke database
     */
    void insert(Product product) throws Exception;

    /**
     * Mencari produk berdasarkan kode
     */
    Product findByCode(String code) throws Exception;

    /**
     * Mengambil semua produk dari database
     */
    List<Product> findAll() throws Exception;

    /**
     * Update data produk yang sudah ada
     */
    void update(Product product) throws Exception;

    /**
     * Menghapus produk berdasarkan kode
     */
    void delete(String code) throws Exception;
}
