package com.upb.agripos.service;

import java.util.List;

import com.upb.agripos.dao.JdbcProductDAO;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;

public class ProductService {

    private final ProductDAO productDAO = new JdbcProductDAO();

    public void addProduct(Product product) {
        productDAO.save(product);
    }

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    public Product getProductById(String id) {
        return productDAO.findById(id);
    }

    public void updateStock(String id, int qty) {
        Product p = productDAO.findById(id);
        if (p != null) {
            p.reduceStock(qty);
        }
    }

    public void updateProduct(Product product) {
        productDAO.update(product);
    }

    public void deleteProduct(String id) {
        productDAO.delete(id);
    }
}