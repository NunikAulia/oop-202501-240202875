package com.upb.agripos.dao;

import com.upb.agripos.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private final Connection connection;

    public ProductDAOImpl(Connection connection) {
        if (connection == null) throw new IllegalArgumentException("Connection tidak boleh null");
        this.connection = connection;
    }

    @Override
    public void insert(Product p) throws SQLException {
        if (p == null) throw new IllegalArgumentException("Product tidak boleh null");

        String sql = "INSERT INTO products (code, name, price, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getCode());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.executeUpdate();
        }
    }

    @Override
    public Product findByCode(String code) throws SQLException {
        if (code == null) throw new IllegalArgumentException("Code tidak boleh null");

        String sql = "SELECT code, name, price, stock FROM products WHERE code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT code, name, price, stock FROM products";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product(
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                );
                list.add(p);
            }
        }
        return list;
    }

    @Override
    public void update(Product p) throws SQLException {
        if (p == null) throw new IllegalArgumentException("Product tidak boleh null");

        String sql = "UPDATE products SET name = ?, price = ?, stock = ? WHERE code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getCode());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Update gagal, produk dengan code " + p.getCode() + " tidak ditemukan.");
            }
        }
    }

    @Override
    public void delete(String code) throws SQLException {
        if (code == null) throw new IllegalArgumentException("Code tidak boleh null");

        String sql = "DELETE FROM products WHERE code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Delete gagal, produk dengan code " + code + " tidak ditemukan.");
            }
        }
    }
}
