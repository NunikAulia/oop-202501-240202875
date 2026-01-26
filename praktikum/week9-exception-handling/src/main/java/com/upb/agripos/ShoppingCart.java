package main.java.com.upb.agripos;
import java.util.ArrayList;

import main.java.com.upb.agripos.InvalidQuantityException;

public class ShoppingCart {
    private final ArrayList<Produk> items = new ArrayList<>();

    public void addProduct(Produk p, int qty) throws InvalidQuantityException {
    if (qty <= 0) {
        throw new InvalidQuantityException("Quantity harus lebih dari 0.");
    }
    for (int i = 0; i < qty; i++) {
        items.add(p);
    }
    }

    public void removeProduct(Produk p) throws ProductNotFoundException {
        // Pesan disesuaikan dengan gambar Anda
        if (!items.contains(p)) throw new ProductNotFoundException("Produk tidak ada dalam keranjang.");
        items.remove(p);
    }

    public void checkout() throws InsufficientStockException {
        for (Produk p : items) {
            // Jika jumlah di keranjang melebihi stok yang ada
            if (p.getStock() < 1) { 
                throw new InsufficientStockException("Stok tidak cukup untuk: " + p.getName());
            }
        }
        System.out.println("Checkout Berhasil!");
        items.clear();
    }

    public void printCart() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printCart'");
    }
}