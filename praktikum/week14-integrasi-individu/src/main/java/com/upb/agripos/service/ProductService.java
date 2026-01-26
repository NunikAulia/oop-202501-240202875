package com.upb.agripos.service;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.Product;
import java.util.List;

/**
 * ProductService provides business logic for product operations
 */
public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void insert(Product product) throws Exception {
        if (product == null || product.getCode() == null || product.getCode().trim().isEmpty()) {
            throw new ValidationException("Product code cannot be empty");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new ValidationException("Product name cannot be empty");
        }
        if (product.getPrice() < 0) {
            throw new ValidationException("Product price cannot be negative");
        }
        if (product.getStock() < 0) {
            throw new ValidationException("Product stock cannot be negative");
        }

        // Check for duplicate code
        if (productDAO.findByCode(product.getCode()) != null) {
            throw new ValidationException("Product code already exists");
        }

        productDAO.insert(product);
    }

    public Product findByCode(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) {
            throw new ValidationException("Product code cannot be empty");
        }
        return productDAO.findByCode(code);
    }

    public List<Product> findAll() throws Exception {
        return productDAO.findAll();
    }

    public void update(Product product) throws Exception {
        if (product == null || product.getCode() == null || product.getCode().trim().isEmpty()) {
            throw new ValidationException("Product code cannot be empty");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new ValidationException("Product name cannot be empty");
        }
        if (product.getPrice() < 0) {
            throw new ValidationException("Product price cannot be negative");
        }
        if (product.getStock() < 0) {
            throw new ValidationException("Product stock cannot be negative");
        }

        productDAO.update(product);
    }

    public void delete(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) {
            throw new ValidationException("Product code cannot be empty");
        }
        productDAO.delete(code);
    }
}
