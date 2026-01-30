package com.upb.agripos.model;

public class ItemTransaksi {

    private Product product;
    private int qty;

    public ItemTransaksi(Product product, int qty) {
        this.product = product;
        this.qty = qty;
    }

    public double getSubtotal() {
        return product.getPrice() * qty;
    }

    public Product getProduct() {
        return product;
    }

    public int getQty() {
        return qty;
    }
}