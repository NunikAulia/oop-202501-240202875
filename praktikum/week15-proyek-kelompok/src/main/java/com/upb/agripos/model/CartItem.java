package com.upb.agripos.model;

public class CartItem {

    private final Product product;
    private final int qty;

    public CartItem(Product product, int qty) {
        this.product = product;
        this.qty = qty;
    }

    public Product getProduct() {
        return product;
    }

    public int getQty() {
        return qty;
    }

    public double getSubtotal() {
        return product.getPrice() * qty;
    }
}