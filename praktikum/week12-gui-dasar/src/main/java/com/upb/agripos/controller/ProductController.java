package com.upb.agripos.controller;

import com.upb.agripos.model.Product; 
import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.ProductFormView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductController {
    private ProductService service;
    private ProductFormView view;

    public ProductController(ProductService service, ProductFormView view) {
        this.service = service;
        this.view = view;
        initController();
    }

    private void initController() {
        loadData(); // Memasukkan data ke list saat aplikasi dibuka

        // Memberi aksi pada tombol Tambah Produk
        view.btnTambah.setOnAction(e -> {
            try {
                // 1. Ambil data dari kotak input (TextField)
                String code = view.txtKode.getText();
                String name = view.txtNama.getText();
                double price = Double.parseDouble(view.txtHarga.getText());
                int stock = Integer.parseInt(view.txtStok.getText());

                // 2. Buat objek Product (Sesuai dengan constructor Product.java kamu)
                Product newProduct = new Product(code, name, price, stock);
                
                // 3. Simpan lewat Service
                service.addProduct(newProduct);
                
                // 4. Refresh tampilan dan kosongkan inputan
                loadData();
                clearFields();
                System.out.println("Data berhasil disimpan!");

            } catch (NumberFormatException ex) {
                System.err.println("Harga dan Stok harus angka!");
            } catch (Exception ex) {
                System.err.println("Gagal simpan: " + ex.getMessage());
            }
        });
    }

    private void loadData() {
        try {
            ObservableList<String> displayList = FXCollections.observableArrayList();
            // Gunakan getName() dan getStock() sesuai file Product.java kamu
            for (Product p : service.getAllProducts()) {
                displayList.add(p.getCode() + " - " + p.getName() + " (Stok: " + p.getStock() + ")");
            }
            view.listProduk.setItems(displayList);
        } catch (Exception e) {
            System.err.println("Gagal load data: " + e.getMessage());
        }
    }

    private void clearFields() {
        view.txtKode.clear();
        view.txtNama.clear();
        view.txtHarga.clear();
        view.txtStok.clear();
    }
}