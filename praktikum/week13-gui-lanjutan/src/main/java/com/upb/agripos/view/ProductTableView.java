package com.upb.agripos.view;

import com.upb.agripos.model.Product;
import com.upb.agripos.controller.ProductController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View untuk Form Input dan TableView Produk menggunakan JavaFX
 * Menampilkan daftar produk dalam bentuk tabel terstruktur
 * Menggunakan Lambda Expression untuk event handling
 * Menerapkan prinsip Single Responsibility Principle (SRP)
 */
public class ProductTableView extends VBox {
    private final ProductController controller;
    
    // UI Components
    private TextField txtCode;
    private TextField txtName;
    private TextField txtPrice;
    private TextField txtStock;
    private Button btnAdd;
    private Button btnDelete;
    private Button btnRefresh;
    private TableView<Product> tableView;
    private Label lblStatus;
    private ObservableList<Product> productList;

    /**
     * Constructor
     * @param controller ProductController untuk handle event
     */
    public ProductTableView(ProductController controller) {
        this.controller = controller;
        this.productList = FXCollections.observableArrayList();
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
        Label lblTitle = new Label("Kelola Produk Agri-POS - Advanced GUI");
        lblTitle.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        // Input Section
        VBox inputSection = createInputSection();

        // Button Section
        HBox buttonSection = createButtonSection();

        // TableView Section
        VBox tableSection = createTableSection();

        // Status Label
        lblStatus = new Label("Siap");
        lblStatus.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");

        // Add all to VBox
        this.getChildren().addAll(
            lblTitle,
            new Separator(),
            inputSection,
            buttonSection,
            tableSection,
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
        btnAdd.setStyle("-fx-font-size: 12; -fx-padding: 8; -fx-background-color: #3498db; -fx-text-fill: white;");

        btnDelete = new Button("Hapus Produk");
        btnDelete.setPrefWidth(150);
        btnDelete.setStyle("-fx-font-size: 12; -fx-padding: 8; -fx-background-color: #e74c3c; -fx-text-fill: white;");

        btnRefresh = new Button("Refresh");
        btnRefresh.setPrefWidth(100);
        btnRefresh.setStyle("-fx-font-size: 12; -fx-padding: 8; -fx-background-color: #95a5a6; -fx-text-fill: white;");

        section.getChildren().addAll(btnAdd, btnDelete, btnRefresh);
        return section;
    }

    /**
     * Membuat section untuk TableView
     */
    private VBox createTableSection() {
        VBox section = new VBox(8);
        section.setPadding(new Insets(10));
        section.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1;");

        Label lblTable = new Label("Daftar Produk:");
        lblTable.setStyle("-fx-font-weight: bold;");

        tableView = new TableView<>();
        tableView.setPrefHeight(250);
        tableView.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1;");

        // Kolom Kode
        TableColumn<Product, String> colCode = new TableColumn<>("Kode");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCode.setPrefWidth(100);

        // Kolom Nama
        TableColumn<Product, String> colName = new TableColumn<>("Nama");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setPrefWidth(200);

        // Kolom Harga
        TableColumn<Product, Double> colPrice = new TableColumn<>("Harga (Rp)");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPrice.setPrefWidth(120);

        // Kolom Stok
        TableColumn<Product, Integer> colStock = new TableColumn<>("Stok");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colStock.setPrefWidth(100);

        // Add columns to table
        tableView.getColumns().addAll(colCode, colName, colPrice, colStock);
        tableView.setItems(productList);

        section.getChildren().addAll(lblTable, tableView);
        return section;
    }

    /**
     * Setup event handlers menggunakan Lambda Expression
     * Mendemonstrasikan event-driven programming dengan lambda
     */
    private void setupEventHandlers() {
        // Lambda Expression untuk tombol "Tambah Produk"
        btnAdd.setOnAction(e -> handleAddProduct());

        // Lambda Expression untuk tombol "Hapus Produk"
        btnDelete.setOnAction(e -> handleDeleteProduct());

        // Lambda Expression untuk tombol "Refresh"
        btnRefresh.setOnAction(e -> loadData());
    }

    /**
     * Handle event ketika tombol "Tambah Produk" diklik
     * Lambda: btnAdd.setOnAction(e -> handleAddProduct())
     */
    private void handleAddProduct() {
        try {
            String code = txtCode.getText();
            String name = txtName.getText();
            String price = txtPrice.getText();
            String stock = txtStock.getText();

            boolean success = controller.addProduct(code, name, price, stock);

            if (success) {
                lblStatus.setText("✓ Produk berhasil ditambahkan");
                lblStatus.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
                clearInputFields();
                loadData();
            } else {
                lblStatus.setText("✗ Error: Input tidak valid atau tidak lengkap");
                lblStatus.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
            }
        } catch (Exception e) {
            lblStatus.setText("✗ Error: " + e.getMessage());
            lblStatus.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        }
    }

    /**
     * Handle event ketika tombol "Hapus Produk" diklik
     * Lambda: btnDelete.setOnAction(e -> handleDeleteProduct())
     */
    private void handleDeleteProduct() {
        try {
            // Ambil item yang dipilih dari TableView
            Product selected = tableView.getSelectionModel().getSelectedItem();
            
            if (selected == null) {
                lblStatus.setText("✗ Silakan pilih produk untuk dihapus");
                lblStatus.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                return;
            }

            // Konfirmasi delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Hapus");
            alert.setHeaderText("Hapus Produk: " + selected.getCode());
            alert.setContentText("Apakah Anda yakin ingin menghapus produk ini?");

            if (alert.showAndWait().isPresent() && alert.getResult() == ButtonType.OK) {
                boolean success = controller.deleteProduct(selected.getCode());
                
                if (success) {
                    lblStatus.setText("✓ Produk " + selected.getCode() + " berhasil dihapus");
                    lblStatus.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
                    loadData();
                } else {
                    lblStatus.setText("✗ Error saat menghapus produk");
                    lblStatus.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                }
            }
        } catch (Exception e) {
            lblStatus.setText("✗ Error: " + e.getMessage());
            lblStatus.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        }
    }

    /**
     * Load semua produk dari database ke TableView
     * Data diambil dari DAO melalui Service, bukan direct query
     * Mengimplementasikan Dependency Inversion Principle (DIP)
     */
    private void loadData() {
        try {
            java.util.List<Product> products = controller.getAllProducts();
            productList.clear();
            productList.addAll(products);
            
            lblStatus.setText("✓ Data di-refresh (" + products.size() + " produk)");
            lblStatus.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
        } catch (Exception e) {
            lblStatus.setText("✗ Error loading products: " + e.getMessage());
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
     * Public method untuk load initial data saat aplikasi start
     */
    public void loadInitialData() {
        loadData();
    }
}
