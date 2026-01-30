package com.upb.agripos.service;

import com.upb.agripos.model.Cart;
import com.upb.agripos.model.Product;

public class CartService {
    private Cart cart = new Cart();

    public void add(Product product, int qty) {
        if (qty <= 0) throw new RuntimeException("Qty tidak valid");
        cart.addItem(product, qty);
    }

    public double total() {
        return cart.getTotal();
    }

    public void clear() {
        cart.clear();
    }

    public Cart getCart() {
        return cart;
    }
}