package com.upb.agripos.model;

/**
 * Model class untuk Product (Produk)
 * Digunakan untuk merepresentasikan data produk dalam sistem Agri-POS
 */
public class Product {
    private String code;
    private String name;
    private double price;
    private int stock;

    /**
     * Constructor dengan semua parameter
     */
    public Product(String code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getter methods
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (Rp %.0f) Stok: %d", code, name, price, stock);
    }
}
