package com.upb.agripos.controller;

import com.upb.agripos.service.ProductService;
import com.upb.agripos.service.CartService;
import com.upb.agripos.model.Product;
import com.upb.agripos.exception.ValidationException;
import java.util.List;

/**
 * PosController acts as a controller between View and Services
 * Implements Dependency Inversion Principle (DIP)
 */
public class PosController {
    private final ProductService productService;
    private final CartService cartService;

    public PosController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    // Product Operations
    public void addProduct(String code, String name, double price, int stock) throws Exception {
        Product product = new Product(code, name, price, stock);
        productService.insert(product);
    }

    public List<Product> getAllProducts() throws Exception {
        return productService.findAll();
    }

    public Product getProduct(String code) throws Exception {
        return productService.findByCode(code);
    }

    public void updateProduct(String code, String name, double price, int stock) throws Exception {
        Product product = new Product(code, name, price, stock);
        productService.update(product);
    }

    public void deleteProduct(String code) throws Exception {
        productService.delete(code);
    }

    // Cart Operations
    public void addToCart(String productCode, int quantity) throws Exception {
        Product product = productService.findByCode(productCode);
        if (product == null) {
            throw new ValidationException("Product not found: " + productCode);
        }
        cartService.addItem(product, quantity);
    }

    public void removeFromCart(String productCode) throws Exception {
        cartService.removeItem(productCode);
    }

    public double getCartTotal() {
        return cartService.getTotal();
    }

    public int getCartItemCount() {
        return cartService.getItemCount();
    }

    public void clearCart() {
        cartService.clear();
    }

    public boolean isCartEmpty() {
        return cartService.isEmpty();
    }

    public String getCartSummary() {
        return cartService.getCart().toString();
    }
}
