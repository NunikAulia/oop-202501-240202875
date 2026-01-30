package com.upb.agripos.dao;

import com.upb.agripos.model.Promo;

public interface TransactionDAO {
    void save(Promo transaction);
}