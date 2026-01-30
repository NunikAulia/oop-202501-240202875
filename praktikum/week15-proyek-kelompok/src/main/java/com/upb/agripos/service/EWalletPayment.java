package com.upb.agripos.service;

public class EWalletPayment implements PaymentMethod {

    private double balance;

    public EWalletPayment(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean pay(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
}