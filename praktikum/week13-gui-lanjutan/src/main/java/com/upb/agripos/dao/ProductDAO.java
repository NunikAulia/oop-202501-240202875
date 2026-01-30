package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    void insert(Product p) throws SQLException;
    Product findByCode(String code) throws SQLException;
    List<Product> findAll() throws SQLException;
    void update(Product p) throws SQLException;
    void delete(String code) throws SQLException;
}
