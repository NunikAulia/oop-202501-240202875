package com.upb.agripos.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ProductFormView extends VBox {
    // Deklarasi komponen agar bisa diakses oleh Controller
    public TextField txtKode = new TextField();
    public TextField txtNama = new TextField();
    public TextField txtHarga = new TextField();
    public TextField txtStok = new TextField();
    public Button btnTambah = new Button("Tambah Produk");
    public ListView<String> listProduk = new ListView<>();

    public ProductFormView() {
        this.setPadding(new Insets(20));
        this.setSpacing(10);

        // Menambahkan komponen ke dalam VBox (Urutan sesuai gambar)
        this.getChildren().addAll(
            new Label("Form Input Produk Agri-POS"),
            new Label("Kode Produk:"), txtKode,
            new Label("Nama Produk:"), txtNama,
            new Label("Harga:"), txtHarga,
            new Label("Stok:"), txtStok,
            btnTambah,
            new Label("Daftar Produk:"), listProduk
        );
    }
}