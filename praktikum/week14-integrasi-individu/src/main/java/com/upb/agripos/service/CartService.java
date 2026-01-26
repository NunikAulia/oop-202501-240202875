package com.upb.agripos.service;

import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.Cart;
import com.upb.agripos.model.Product;

/**
 * CartService provides business logic for shopping cart operations
 */
public class CartService {
    private final Cart cart;

    public CartService() {
        this.cart = new Cart();
    }

    public void addItem(Product product, int quantity) throws Exception {
        if (product == null) {
            throw new ValidationException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new ValidationException("Quantity must be greater than 0");
        }
        if (product.getStock() < quantity) {
            throw new ValidationException("Insufficient stock for product: " + product.getName());
        }

        cart.addItem(product, quantity);
    }

    public void removeItem(String productCode) throws Exception {
        if (productCode == null || productCode.trim().isEmpty()) {
            throw new ValidationException("Product code cannot be empty");
        }
        cart.removeItem(productCode);
    }

    public Cart getCart() {
        return cart;
    }

    public double getTotal() {
        return cart.getTotal();
    }

    public int getItemCount() {
        return cart.getItemCount();
    }

    public void clear() {
        cart.clear();
    }

    public boolean isEmpty() {
        return cart.isEmpty();
    }
}
