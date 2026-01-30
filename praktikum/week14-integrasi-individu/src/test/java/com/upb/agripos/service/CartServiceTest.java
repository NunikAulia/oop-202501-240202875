package com.upb.agripos.service;

import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.Product;

import java.util.HashMap;
import java.util.Map;

public class CartServiceTest {

    private Map<String, CartItem> items = new HashMap<>();

    // ===== INNER CLASS =====
    private static class CartItem {
        Product product;
        int quantity;

        CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
    }

    // ===== ADD ITEM =====
    public void addItem(Product product, int quantity) throws ValidationException {
        if (product == null) {
            throw new ValidationException("Product cannot be null");
        }

        if (quantity <= 0) {
            throw new ValidationException("Quantity must be greater than zero");
        }

        if (quantity > product.getStock()) {
            throw new ValidationException("Insufficient stock");
        }

        String code = product.getCode();

        if (items.containsKey(code)) {
            CartItem existing = items.get(code);
            int newQty = existing.quantity + quantity;

            if (newQty > product.getStock()) {
                throw new ValidationException("Insufficient stock");
            }

            existing.quantity = newQty;
        } else {
            items.put(code, new CartItem(product, quantity));
        }
    }

    // ===== REMOVE ITEM =====
    public void removeItem(String productCode) {
        if (productCode == null) return;
        items.remove(productCode);
    }

    // ===== CLEAR CART =====
    public void clear() {
        items.clear();
    }

    // ===== GET ITEM COUNT =====
    public int getItemCount() {
        return items.size();
    }

    // ===== GET TOTAL =====
    public double getTotal() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.product.getPrice() * item.quantity;
        }
        return total;
    }

    // ===== IS EMPTY =====
    public boolean isEmpty() {
        return items.isEmpty();
    }
}
