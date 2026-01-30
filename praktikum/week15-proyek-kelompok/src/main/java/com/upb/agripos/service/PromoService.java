package com.upb.agripos.service;

public class PromoService {
    public double apply(String code, double total) {
        if (code.equalsIgnoreCase("DISKON10"))
            return total * 0.9;
        return total;
    }
}