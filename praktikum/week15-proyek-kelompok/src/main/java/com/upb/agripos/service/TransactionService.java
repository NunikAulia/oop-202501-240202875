package com.upb.agripos.service;

import java.time.LocalDate;
import java.time.LocalTime;

import com.upb.agripos.model.Cart;
import com.upb.agripos.model.Transaction;

public class TransactionService {

    public Transaction checkout(Cart cart, String cashier) {
        double total = cart.getTotal();
        String transactionId = "TRX" + System.currentTimeMillis();
        
        Transaction trx = new Transaction(
            transactionId,
            cart.getItems(),
            total,
            0, // no discount in service
            total,
            "TUNAI", // default payment method
            LocalDate.now(),
            LocalTime.now(),
            cashier
        );
        cart.clear();
        return trx;
    }
}