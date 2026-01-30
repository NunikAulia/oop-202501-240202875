package com.upb.agripos.service;

import java.util.List;

import com.upb.agripos.model.CartItem;

public class ReceiptService {
    public String render(List<CartItem> cart, double total) {
        StringBuilder sb = new StringBuilder("STRUK\n");
        cart.forEach(c ->
                sb.append(c.getProduct().getName())
                  .append(" x").append(c.getQty())
                  .append(" = ").append(c.getSubtotal()).append("\n")
        );
        sb.append("TOTAL: ").append(total);
        return sb.toString();
    }
}