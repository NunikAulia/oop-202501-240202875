package com.upb.agripos.controller;

import com.upb.agripos.model.Cart;
import com.upb.agripos.model.Transaction;
import com.upb.agripos.service.TransactionService;

public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Transaction checkout(Cart cart, String cashier) {
        return transactionService.checkout(cart, cashier);
    }
}