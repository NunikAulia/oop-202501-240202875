package com.upb.agripos.controller;

import java.util.List;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public void addProduct(String id, String name, double price, int stock) {
        Product product = new Product(id, name, price, stock);
        productService.addProduct(product);
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    public Product getProductById(String id) {
        return productService.getProductById(id);
    }

    public void updateStock(String productId, int newStock) {
        productService.updateStock(productId, newStock);
    }

    public void updateProduct(String id, String name, double price, int stock) {
        Product product = new Product(id, name, price, stock);
        productService.updateProduct(product);
    }

    public void deleteProduct(String id) {
        productService.deleteProduct(id);
    }
}