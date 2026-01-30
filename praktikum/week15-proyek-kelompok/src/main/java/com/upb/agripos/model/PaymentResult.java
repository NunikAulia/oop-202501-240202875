package com.upb.agripos.model;

public class PaymentResult {
    public boolean success;
    public String message;

    public PaymentResult(boolean s, String m) {
        success = s;
        message = m;
    }
}