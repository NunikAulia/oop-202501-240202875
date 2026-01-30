package com.upb.agripos.model;

import java.time.LocalDate;

public class Promo {

    private String code;
    private String name;
    private double discount;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    public Promo(String code, String name, double discount, LocalDate startDate, LocalDate endDate) {
        this.code = code;
        this.name = name;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = true;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDiscountPercentage() {
        return (int)(discount * 100) + "%";
    }
}