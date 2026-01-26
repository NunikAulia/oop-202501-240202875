package com.upb.agripos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.upb.agripos.model.Product;

public class ProductTest {
    @Test
    public void testProductAttributes() {
        Product p = new Product("P01", "Benih Jagung");
        assertEquals("P01", p.getCode());
        assertEquals("Benih Jagung", p.getName());
    }
}