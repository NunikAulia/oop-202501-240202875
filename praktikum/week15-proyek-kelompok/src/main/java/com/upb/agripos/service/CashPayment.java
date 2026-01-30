package com.upb.agripos.service;

public class CashPayment implements PaymentMethod {

    private double cash;

    public CashPayment(double cash) {
        this.cash = cash;
    }

    @Override
    public boolean pay(double amount) {
        return cash >= amount;
    }

    public double getChange(double amount) {
        return cash - amount;
    }
}