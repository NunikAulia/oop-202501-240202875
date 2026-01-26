package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import java.util.List;

/**
 * ProductDAO interface defining contract for product data access
 */
public interface ProductDAO {
    void insert(Product product) throws Exception;
    Product findByCode(String code) throws Exception;
    List<Product> findAll() throws Exception;
    void update(Product product) throws Exception;
    void delete(String code) throws Exception;
}
