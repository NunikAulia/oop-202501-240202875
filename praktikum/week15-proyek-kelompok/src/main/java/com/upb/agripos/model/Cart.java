package com.upb.agripos.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final List<ItemTransaksi> items = new ArrayList<>();

    public void addItem(Product product, int qty) {
        items.add(new ItemTransaksi(product, qty));
    }

    public List<ItemTransaksi> getItems() {
        return items;
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(ItemTransaksi::getSubtotal)
                .sum();
    }

    public void clear() {
        items.clear();
    }
}