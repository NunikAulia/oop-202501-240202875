package com.upb.agripos.dao;

import java.util.List;

import com.upb.agripos.model.Product;

public interface ProductDAO {
    void save(Product product);
    Product findById(String id);
    List<Product> findAll();
    void update(Product product);
    void delete(String id);
}