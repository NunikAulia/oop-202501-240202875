package com.upb.agripos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.upb.agripos.model.Product;

public class JdbcProductDAO implements ProductDAO {

    public void save(Product product) {
        String sql = "INSERT INTO public.product " +
                     "(code, name, category, price, stock) " +
                     "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = JdbcConnection.getConnection();
            
            if (conn == null) {
                System.err.println("❌ ERROR: Koneksi database NULL!");
                return;
            }
            
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, product.getCode());
            ps.setString(2, product.getName());
            ps.setString(3, product.getCategory());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getStock());

            int result = ps.executeUpdate();
            System.out.println("✅ Row inserted = " + result);
            System.out.println("   Produk: " + product.getCode() + " - " + product.getName());
            
            ps.close();

        } catch (Exception e) {
            System.err.println("❌ ERROR saat insert produk: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insert(Product product) {
        save(product);
    }

    @Override
    public Product findById(String id) {
        String sql = "SELECT * FROM public.product WHERE code = ?";
        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Product product = new Product(
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                );
                try {
                    product.setProductId(rs.getInt("product_id"));
                } catch (Exception e) {
                    // product_id column might not exist, skip it
                }
                product.setCategory(rs.getString("category"));
                return product;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM public.product";
        List<Product> products = new ArrayList<>();

        try (Connection conn = JdbcConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product(
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                );
                try {
                    product.setProductId(rs.getInt("product_id"));
                } catch (Exception e) {
                    // product_id column might not exist, skip it
                }
                product.setCategory(rs.getString("category"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE public.product SET name = ?, category = ?, price = ?, stock = ? WHERE code = ?";

        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setString(5, product.getCode());

            int result = ps.executeUpdate();
            System.out.println("Row updated = " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM public.product WHERE code = ?";

        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            int result = ps.executeUpdate();
            System.out.println("Row deleted = " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}