package com.upb.agripos.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Transaction {

    private String id;
    private List<ItemTransaksi> items;
    private double subtotal;
    private double discount;
    private double total;
    private String paymentMethod;
    private LocalDate date;
    private LocalTime time;
    private String cashier;

    public Transaction(String id, List<ItemTransaksi> items, double subtotal, 
                      double discount, double total, String paymentMethod, 
                      LocalDate date, LocalTime time, String cashier) {
        this.id = id;
        this.items = items;
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.time = time;
        this.cashier = cashier;
    }

    public String getId() {
        return id;
    }

    public List<ItemTransaksi> getItems() {
        return items;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotal() {
        return total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getCashier() {
        return cashier;
    }
}