package com.upb.agripos.view;

import com.upb.agripos.model.Product;
import com.upb.agripos.controller.ProductController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View untuk Form Input Produk menggunakan JavaFX
 * Menangani semua elemen UI dan event handling
 * Menerapkan prinsip Single Responsibility Principle (SRP)
 * View hanya bertanggung jawab untuk UI, bukan business logic
 */
public class ProductFormView extends VBox {
    private final ProductController controller;
    
    // UI Components
    private TextField txtCode;
    private TextField txtName;
    private TextField txtPrice;
    private TextField txtStock;
    private Button btnAdd;
    private Button btnRefresh;
    private ListView<String> listView;
    private Label lblStatus;

    /**
     * Constructor
     * @param controller ProductController untuk handle event
     */
    public ProductFormView(ProductController controller) {
        this.controller = controller;
        initializeUI();
        setupEventHandlers();
    }

    /**
     * Inisialisasi semua komponen UI
     */
    private void initializeUI() {
        this.setPadding(new Insets(15));
        this.setSpacing(10);
        this.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1;");

        // Title
        Label lblTitle = new Label("Form Input Produk - Agri-POS");
        lblTitle.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        // Input Section
        VBox inputSection = createInputSection();

        // Button Section
        HBox buttonSection = createButtonSection();

        // List Section
        VBox listSection = createListSection();

        // Status Label
        lblStatus = new Label("Siap");
        lblStatus.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");

        // Add all to VBox
        this.getChildren().addAll(
            lblTitle,
            new Separator(),
            inputSection,
            buttonSection,
            listSection,
            lblStatus
        );
    }

    /**
     * Membuat section untuk input fields
     */
    private VBox createInputSection() {
        VBox section = new VBox(8);
        section.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-padding: 10;");

        // Kode Produk
        HBox codeBox = new HBox(5);
        Label lblCode = new Label("Kode Produk:");
        lblCode.setPrefWidth(120);
        txtCode = new TextField();
        txtCode.setPrefWidth(200);
        codeBox.getChildren().addAll(lblCode, txtCode);

        // Nama Produk
        HBox nameBox = new HBox(5);
        Label lblName = new Label("Nama Produk:");
        lblName.setPrefWidth(120);
        txtName = new TextField();
        txtName.setPrefWidth(200);
        nameBox.getChildren().addAll(lblName, txtName);

        // Harga
        HBox priceBox = new HBox(5);
        Label lblPrice = new Label("Harga (Rp):");
        lblPrice.setPrefWidth(120);
        txtPrice = new TextField();
        txtPrice.setPrefWidth(200);
        priceBox.getChildren().addAll(lblPrice, txtPrice);

        // Stok
        HBox stockBox = new HBox(5);
        Label lblStock = new Label("Stok:");
        lblStock.setPrefWidth(120);
        txtStock = new TextField();
        txtStock.setPrefWidth(200);
        stockBox.getChildren().addAll(lblStock, txtStock);

        section.getChildren().addAll(codeBox, nameBox, priceBox, stockBox);
        return section;
    }

    /**
     * Membuat section untuk tombol
     */
    private HBox createButtonSection() {
        HBox section = new HBox(10);
        section.setPadding(new Insets(10));

        btnAdd = new Button("Tambah Produk");
        btnAdd.setPrefWidth(150);
        btnAdd.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        btnAdd.setStyle(btnAdd.getStyle() + "; -fx-background-color: #3498db; -fx-text-fill: white;");

        btnRefresh = new Button("Refresh");
        btnRefresh.setPrefWidth(100);
        btnRefresh.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        btnRefresh.setStyle(btnRefresh.getStyle() + "; -fx-background-color: #95a5a6; -fx-text-fill: white;");

        section.getChildren().addAll(btnAdd, btnRefresh);
        return section;
    }

    /**
     * Membuat section untuk menampilkan list produk
     */
    private VBox createListSection() {
        VBox section = new VBox(8);
        section.setPadding(new Insets(10));
        section.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1;");

        Label lblList = new Label("Daftar Produk:");
        lblList.setStyle("-fx-font-weight: bold;");

        listView = new ListView<>();
        listView.setPrefHeight(200);
        listView.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1;");

        section.getChildren().addAll(lblList, listView);
        return section;
    }

    /**
     * Setup event handlers untuk tombol
     */
    private void setupEventHandlers() {
        // Handler untuk tombol "Tambah Produk"
        btnAdd.setOnAction(event -> handleAddProduct());

        // Handler untuk tombol "Refresh"
        btnRefresh.setOnAction(event -> handleRefresh());
    }

    /**
     * Handle event ketika tombol "Tambah Produk" diklik
     * Activity Diagram: Validasi → Simpan → Tampil
     */
    private void handleAddProduct() {
        try {
            // Ambil nilai dari text field
            String code = txtCode.getText();
            String name = txtName.getText();
            String price = txtPrice.getText();
            String stock = txtStock.getText();

            // Panggil controller untuk add product
            boolean success = controller.addProduct(code, name, price, stock);

            if (success) {
                // Update status
                lblStatus.setText("Produk berhasil ditambahkan");
                lblStatus.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");

                // Clear input fields
                clearInputFields();

                // Refresh list
                loadProducts();
            } else {
                lblStatus.setText("Error: Input tidak valid atau tidak lengkap");
                lblStatus.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
            }
        } catch (Exception e) {
            lblStatus.setText("Error: " + e.getMessage());
            lblStatus.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        }
    }

    /**
     * Handle event ketika tombol "Refresh" diklik
     */
    private void handleRefresh() {
        loadProducts();
        lblStatus.setText("Data di-refresh");
        lblStatus.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
    }

    /**
     * Load semua produk dari database ke ListView
     */
    private void loadProducts() {
        try {
            listView.getItems().clear();
            Product[] products = controller.getAllProducts();
            for (Product p : products) {
                listView.getItems().add(p.toString());
            }
        } catch (Exception e) {
            lblStatus.setText("Error loading products: " + e.getMessage());
            lblStatus.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        }
    }

    /**
     * Clear semua input field
     */
    private void clearInputFields() {
        txtCode.clear();
        txtName.clear();
        txtPrice.clear();
        txtStock.clear();
        txtCode.requestFocus();
    }

    /**
     * Public method untuk load initial data
     */
    public void loadInitialData() {
        loadProducts();
    }
}
