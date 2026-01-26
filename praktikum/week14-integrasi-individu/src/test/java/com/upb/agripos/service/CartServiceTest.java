package com.upb.agripos.service;

import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CartService
 * Tests business logic without GUI or database
 */
public class CartServiceTest {
    private CartService cartService;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        cartService = new CartService();
        product1 = new Product("P001", "Benih Padi", 25000, 100);
        product2 = new Product("P002", "Pupuk Urea", 150000, 50);
    }

    @Test
    public void testAddItem() throws Exception {
        cartService.addItem(product1, 5);
        assertEquals(1, cartService.getItemCount());
        assertEquals(125000, cartService.getTotal(), 0.01);
    }

    @Test
    public void testAddMultipleItems() throws Exception {
        cartService.addItem(product1, 2);
        cartService.addItem(product2, 1);
        assertEquals(2, cartService.getItemCount());
        assertEquals(200000, cartService.getTotal(), 0.01);
    }

    @Test
    public void testAddSameItemTwice() throws Exception {
        cartService.addItem(product1, 3);
        cartService.addItem(product1, 2);
        assertEquals(1, cartService.getItemCount());
        assertEquals(125000, cartService.getTotal(), 0.01);
    }

    @Test
    public void testRemoveItem() throws Exception {
        cartService.addItem(product1, 5);
        cartService.addItem(product2, 2);
        assertEquals(2, cartService.getItemCount());

        cartService.removeItem("P001");
        assertEquals(1, cartService.getItemCount());
        assertEquals(300000, cartService.getTotal(), 0.01);
    }

    @Test
    public void testInvalidQuantity() {
        assertThrows(ValidationException.class, () -> {
            cartService.addItem(product1, 0);
        });

        assertThrows(ValidationException.class, () -> {
            cartService.addItem(product1, -5);
        });
    }

    @Test
    public void testInsufficientStock() {
        assertThrows(ValidationException.class, () -> {
            cartService.addItem(product1, 200);
        });
    }

    @Test
    public void testNullProduct() {
        assertThrows(ValidationException.class, () -> {
            cartService.addItem(null, 5);
        });
    }

    @Test
    public void testClearCart() throws Exception {
        cartService.addItem(product1, 3);
        cartService.addItem(product2, 2);
        assertEquals(2, cartService.getItemCount());

        cartService.clear();
        assertEquals(0, cartService.getItemCount());
        assertEquals(0, cartService.getTotal(), 0.01);
        assertTrue(cartService.isEmpty());
    }

    @Test
    public void testGetTotal() throws Exception {
        cartService.addItem(product1, 2); // 50000
        cartService.addItem(product2, 1); // 150000
        assertEquals(200000, cartService.getTotal(), 0.01);
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(cartService.isEmpty());

        cartService.addItem(product1, 1);
        assertFalse(cartService.isEmpty());

        cartService.clear();
        assertTrue(cartService.isEmpty());
    }

    @Test
    public void testRemoveNonExistentItem() throws Exception {
        cartService.addItem(product1, 1);
        cartService.removeItem("P999"); // Non-existent code
        assertEquals(1, cartService.getItemCount());
    }
}
