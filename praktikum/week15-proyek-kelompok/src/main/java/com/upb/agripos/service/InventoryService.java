package com.upb.agripos.service;

import java.util.List;

import com.upb.agripos.model.CartItem;

public class InventoryService {
    public void updateStock(List<CartItem> cart) {
        cart.forEach(c -> c.getProduct().reduceStock(c.getQty()));
    }
}