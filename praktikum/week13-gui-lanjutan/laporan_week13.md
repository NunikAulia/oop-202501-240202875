# LAPORAN PRAKTIKUM WEEK 13
## GUI Lanjutan JavaFX - TableView dan Lambda Expression

**Program:** S1 Teknik Informatika  
**Mata Kuliah:** Pemrograman Berorientasi Objek  
**Minggu:** 13  
**Topik:** GUI Lanjutan dengan JavaFX - TableView dan Lambda Expression  
**Semester:** Ganjil 2024/2025  

---

## 1. PENDAHULUAN

### 1.1 Latar Belakang

Minggu 13 merupakan lanjutan langsung dari minggu 12. Pada minggu 12, telah dibuat aplikasi GUI dasar menggunakan JavaFX dengan komponen `ListView`. Pada minggu 13, aplikasi GUI ditingkatkan dengan:

1. **Mengganti ListView dengan TableView** - Untuk menampilkan data dalam bentuk tabel dengan kolom-kolom terstruktur
2. **Menggunakan Lambda Expression** - Untuk menyederhanakan event handling pada tombol
3. **Implementasi ObservableList** - Untuk data binding reaktif antara View dan Controller
4. **Operasi Delete dengan Konfirmasi** - Untuk menghapus produk dengan dialog konfirmasi

### 1.2 Tujuan Pembelajaran

Setelah menyelesaikan minggu 13, mahasiswa mampu:

1. ✅ Mengimplementasikan `TableView<T>` dengan kolom-kolom yang terstruktur
2. ✅ Mengggunakan `PropertyValueFactory` untuk data binding otomatis
3. ✅ Menerapkan **lambda expression** dalam event handler (`setOnAction()`)
4. ✅ Memanfaatkan `ObservableList<T>` untuk reactive data updates
5. ✅ Mengintegrasikan TableView dengan backend service layer via DAO pattern
6. ✅ Menerapkan SOLID principles khususnya **Dependency Inversion Principle (DIP)**
7. ✅ Membuat UML diagram untuk architecture TableView-based application
8. ✅ Menulis test case untuk semua operasi CRUD

### 1.3 Spesifikasi Teknis

| Aspek | Spesifikasi |
|-------|-------------|
| **Framework** | JavaFX 21.0.2 |
| **Database** | PostgreSQL dengan JDBC Driver 42.7.1 |
| **Build Tool** | Apache Maven 3.6+ |
| **Target Java** | Java 11 |
| **Architecture** | MVC (Model-View-Controller) |
| **Key Pattern** | Dependency Injection, DAO Pattern, Service Layer |
| **New Component** | TableView<Product>, ObservableList<Product>, Lambda Expression |

---

## 2. TEORI DASAR

### 2.1 TableView di JavaFX

**TableView** adalah komponen JavaFX yang menampilkan data dalam format tabel dengan baris dan kolom. Berbeda dengan ListView yang hanya menampilkan text, TableView memungkinkan:

- **Struktur data terorganisir** dalam kolom-kolom
- **Sorting dan filtering** secara otomatis
- **Selection model** untuk memilih satu atau beberapa baris
- **Data binding** dengan ObservableList

#### Contoh Deklarasi TableView:

```java
// Deklarasi TableView
TableView<Product> tableView = new TableView<>();

// Membuat kolom
TableColumn<Product, String> codeColumn = new TableColumn<>("Code");
codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

// Tambah kolom ke table
tableView.getColumns().addAll(codeColumn, nameColumn);

// Binding data
ObservableList<Product> data = FXCollections.observableArrayList();
tableView.setItems(data);
```

### 2.2 PropertyValueFactory

`PropertyValueFactory` adalah mechanism JavaFX untuk automatic data binding antara object property dan table column. Bekerja dengan JavaBeans property (getter/setter methods).

```java
// PropertyValueFactory menggunakan reflection untuk menemukan property
TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
// Akan mencari property "name" via getName()/setName() methods
```

### 2.3 Lambda Expression

Lambda expression dalam Java adalah fungsi anonymous yang dapat disimpan dalam variable. Syntax:

```java
(parameters) -> { statements }
```

#### Keuntungan Lambda:
1. **Kode lebih ringkas** dibanding anonymous class
2. **Readable** untuk functional programming
3. **Optimal** untuk functional interfaces (single abstract method)

#### Contoh Lambda untuk Event Handler:

```java
// Tanpa Lambda (Anonymous Class)
button.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent e) {
        System.out.println("Button clicked!");
    }
});

// Dengan Lambda (lebih ringkas)
button.setOnAction(e -> System.out.println("Button clicked!"));

// Lambda dengan multiple statements
button.setOnAction(e -> {
    System.out.println("Button clicked!");
    loadData();
});
```

### 2.4 ObservableList

`ObservableList` adalah list yang dapat di-observe oleh listener. Ketika data berubah, semua observer akan notified secara otomatis.

```java
// Create ObservableList
ObservableList<Product> productList = FXCollections.observableArrayList();

// Bind ke TableView
tableView.setItems(productList);

// Ketika data berubah, TableView otomatis terupdate
productList.addAll(products);  // TableView langsung menampilkan data baru
```

### 2.5 SOLID Principles di Week 13

Week 13 melanjutkan implementasi SOLID principles dari Week 12 dengan penekanan pada:

#### 1. **Dependency Inversion Principle (DIP)**

```
View Layer (ProductTableView)
    ↓ depends on abstraction
ProductController interface
    ↓ depends on abstraction  
ProductService interface
    ↓ depends on abstraction
ProductDAO interface
    ↓ implements
ProductDAOImpl (concrete)
```

Diagram DIP di Week 13:

```
ProductTableView (Concrete)
         ↓
    ProductController (Concrete)
         ↓
    ProductService (Concrete)
         ↓
    ProductDAO (Abstract)
         ↓
    ProductDAOImpl (Concrete implementation)
```

#### 2. **Single Responsibility Principle (SRP)**

- **ProductTableView**: Hanya bertanggung jawab untuk UI rendering dan event handling
- **ProductController**: Hanya koordinasi antara View dan Service
- **ProductService**: Hanya business logic dan validation
- **ProductDAOImpl**: Hanya database operations
- **Product Model**: Hanya data representation

#### 3. **Open/Closed Principle (OCP)**

Struktur membuka extensibility tanpa memodifikasi code yang existing:

```java
// Interface ProductDAO terbuka untuk extension
public interface ProductDAO {
    boolean insert(Product product) throws SQLException;
    Product findByCode(String code) throws SQLException;
    List<Product> findAll() throws SQLException;
    // bisa di-extend dengan method baru
}

// Bisa membuat implementasi baru tanpa mengubah ProductDAOImpl
class ProductDAOImpl implements ProductDAO { ... }
class ProductDAOMongo implements ProductDAO { ... } // extension baru
```

---

## 3. IMPLEMENTASI

### 3.1 Struktur Direktori

```
week13-gui-lanjutan/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/upb/agripos/
│   │           ├── AppJavaFX.java             (Main application)
│   │           ├── model/
│   │           │   └── Product.java            (Entity model)
│   │           ├── dao/
│   │           │   ├── ProductDAO.java         (Interface - DIP)
│   │           │   └── ProductDAOImpl.java      (JDBC implementation)
│   │           ├── service/
│   │           │   └── ProductService.java     (Business logic)
│   │           ├── controller/
│   │           │   └── ProductController.java  (Coordination layer)
│   │           └── view/
│   │               └── ProductTableView.java   (JavaFX GUI - NEW)
│   └── test/
│       └── java/
│           └── com/upb/agripos/
│               └── ProductControllerTest.java  (Test cases)
├── pom.xml
├── laporan_week13.md                          (This file)
├── DOKUMENTASI.md
├── README.md
├── CHECKLIST.md
├── screenshots/
│   ├── tableview_produk.png
│   ├── add_produk.png
│   ├── delete_produk.png
│   └── delete_konfirmasi.png
└── target/
    ├── classes/
    └── test-classes/
```

### 3.2 Class Diagram Week 13

```
┌─────────────────────────────────────────────────────────────────┐
│                      PRODUCT MODEL LAYER                        │
│                                                                  │
│  ┌──────────────┐                                               │
│  │   Product    │                                               │
│  ├──────────────┤                                               │
│  │ - code: String   (PK)                                        │
│  │ - name: String   (NOT NULL)                                  │
│  │ - price: Double  (NOT NULL)                                  │
│  │ - stock: Integer (NOT NULL)                                  │
│  ├──────────────┤                                               │
│  │ + getters()  │                                               │
│  │ + setters()  │                                               │
│  │ + toString() │                                               │
│  └──────────────┘                                               │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│               DATA ACCESS OBJECT (DAO) LAYER                    │
│                                                                  │
│  ┌──────────────────────────┐                                   │
│  │     «interface»          │                                   │
│  │     ProductDAO           │ ◄──── Dependency Inversion        │
│  ├──────────────────────────┤                                   │
│  │ + insert(Product): bool  │                                   │
│  │ + findByCode(String): P  │                                   │
│  │ + findAll(): List<P>     │                                   │
│  │ + update(Product): bool  │                                   │
│  │ + delete(String): bool   │                                   │
│  └──────────────────────────┘                                   │
│           △                                                     │
│           │ implements                                          │
│           │                                                     │
│  ┌──────────────────────────┐                                   │
│  │   ProductDAOImpl          │                                   │
│  ├──────────────────────────┤                                   │
│  │ - connection: Connection │                                   │
│  ├──────────────────────────┤                                   │
│  │ + insert(Product): bool  │ ──┐                              │
│  │ + findByCode(String): P  │   │ JDBC Operations              │
│  │ + findAll(): List<P>     │   │ to PostgreSQL DB             │
│  │ + update(Product): bool  │   │                              │
│  │ + delete(String): bool   │ ──┘                              │
│  └──────────────────────────┘                                   │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│              SERVICE LAYER (BUSINESS LOGIC)                     │
│                                                                  │
│  ┌──────────────────────────┐                                   │
│  │   ProductService         │                                   │
│  ├──────────────────────────┤                                   │
│  │ - dao: ProductDAO        │ ──┐                              │
│  ├──────────────────────────┤   │                              │
│  │ + insert(Product): bool  │   │ Validation:                 │
│  │ + findByCode(String): P  │   │ • code not empty            │
│  │ + findAll(): List<P>     │   │ • name not empty            │
│  │ + update(Product): bool  │   │ • price > 0                 │
│  │ + delete(String): bool   │   │ • stock >= 0                │
│  └──────────────────────────┘ ──┘                              │
│           △                                                     │
│           │ uses                                                │
│           │                                                     │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│           CONTROLLER LAYER (COORDINATION)                       │
│                                                                  │
│  ┌──────────────────────────┐                                   │
│  │  ProductController       │                                   │
│  ├──────────────────────────┤                                   │
│  │ - service: ProductSvc    │                                   │
│  ├──────────────────────────┤                                   │
│  │ + addProduct(P): bool    │                                   │
│  │ + deleteProduct(S): bool │ ──┐ Koordinasi                   │
│  │ + getAllProducts(): List │   │ View ◄─► Service            │
│  │ + updateProduct(P): bool │ ──┘                              │
│  └──────────────────────────┘                                   │
│           △                                                     │
│           │ uses                                                │
│           │                                                     │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│           VIEW LAYER (USER INTERFACE - WEEK 13 NEW)             │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │         ProductTableView (extends VBox)                  │   │
│  ├──────────────────────────────────────────────────────────┤   │
│  │ - controller: ProductController                          │   │
│  │ - tableView: TableView<Product>                          │   │
│  │ - productList: ObservableList<Product>                   │   │
│  │ - tfCode, tfName, tfPrice, tfStock: TextField           │   │
│  │ - btnAdd, btnDelete, btnRefresh: Button                 │   │
│  ├──────────────────────────────────────────────────────────┤   │
│  │ + buildUI(): void                                        │   │
│  │ + loadInitialData(): void                               │   │
│  │ + loadData(): void                                       │   │
│  │ + handleAddProduct(): void ──┐ Lambda                    │   │
│  │ + handleDeleteProduct(): void ┤ Expression               │   │
│  │ + clearInputFields(): void    │ Event Handlers           │   │
│  │ + showAlert(String): void   ──┘                          │   │
│  └──────────────────────────────────────────────────────────┘   │
│           △                                                     │
│           │ uses                                                │
│           │                                                     │
└─────────────────────────────────────────────────────────────────┘
```

### 3.3 Key Features Implementation

#### 3.3.1 ProductTableView dengan Lambda Expression

```java
// Deklarasi TableView dan kolom
private TableView<Product> tableView = new TableView<>();
private ObservableList<Product> productList = FXCollections.observableArrayList();

// Setup kolom dengan PropertyValueFactory (automatic data binding)
TableColumn<Product, String> codeColumn = new TableColumn<>("Code");
codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

// Lambda Expression untuk Add button
btnAdd.setOnAction(e -> handleAddProduct());

// Lambda Expression untuk Delete button
btnDelete.setOnAction(e -> handleDeleteProduct());

// Lambda Expression untuk Refresh button
btnRefresh.setOnAction(e -> loadData());
```

#### 3.3.2 loadData() Method dengan ObservableList

```java
private void loadData() {
    try {
        // Get data dari service layer
        java.util.List<Product> products = controller.getAllProducts();
        
        // Clear existing data
        productList.clear();
        
        // Add new data (TableView otomatis terupdate)
        productList.addAll(products);
        
        statusLabel.setText("Total produk: " + products.size());
    } catch (Exception e) {
        showAlert("Error", "Gagal load data: " + e.getMessage());
    }
}
```

#### 3.3.3 Delete dengan Konfirmasi Dialog

```java
private void handleDeleteProduct() {
    Product selected = tableView.getSelectionModel().getSelectedItem();
    
    if (selected == null) {
        showAlert("Warning", "Pilih produk untuk dihapus!");
        return;
    }
    
    // Dialog konfirmasi
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Konfirmasi Hapus");
    alert.setHeaderText("Apakah Anda yakin?");
    alert.setContentText("Produk '" + selected.getName() + "' akan dihapus.");
    
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        boolean success = controller.deleteProduct(selected.getCode());
        if (success) {
            showAlert("Success", "Produk berhasil dihapus");
            loadData();
            clearInputFields();
        } else {
            showAlert("Error", "Gagal menghapus produk");
        }
    }
}
```

---

## 4. TESTING

### 4.1 Test Cases untuk Functional Testing

#### Test Case 1: Menampilkan Data Produk di TableView

| Tahap | Deskripsi | Expected Result |
|-------|-----------|-----------------|
| **Setup** | Aplikasi dijalankan, database tersambung | ✅ Aplikasi terbuka |
| **Action** | Klik tombol "Refresh" atau tunggu auto-load | ✅ Data produk muncul di TableView |
| **Verify** | Lihat kolom Code, Name, Price, Stock terisi | ✅ Semua produk dari DB ditampilkan |

**Result:** ✅ **PASS**

```
TableView menampilkan:
Code    | Name           | Price    | Stock
--------|----------------|----------|-------
P001    | Pupuk Urea     | 25000.00 | 100
P002    | Benih Padi     | 35000.00 | 50
P003    | Pestisida      | 45000.00 | 25
```

#### Test Case 2: Menambah Produk Baru

| Tahap | Deskripsi | Expected Result |
|-------|-----------|-----------------|
| **Setup** | Form input ditampilkan dengan fields kosong | ✅ Semua TextField kosong |
| **Action 1** | Input Code: "P004", Name: "Pupuk Potash" | ✅ Data terisi di TextField |
| **Action 2** | Input Price: "55000", Stock: "30" | ✅ Data terisi di TextField |
| **Action 3** | Klik tombol "Tambah Produk" | ✅ Tombol ditekan |
| **Verify 1** | Pesan "Produk berhasil ditambahkan" muncul | ✅ Success message muncul |
| **Verify 2** | Form otomatis kosong (clearInputFields) | ✅ TextField kosong |
| **Verify 3** | Data baru muncul di TableView | ✅ P004 ada di table dengan data benar |
| **Verify 4** | Database terupdate (SELECT * produk baru) | ✅ Data persist di DB |

**Result:** ✅ **PASS**

#### Test Case 3: Menghapus Produk dengan Konfirmasi

| Tahap | Deskripsi | Expected Result |
|-------|-----------|-----------------|
| **Setup** | TableView menampilkan minimal 3 produk | ✅ Data terlihat |
| **Action 1** | Pilih baris produk (misal P002) | ✅ Baris highlight/selected |
| **Action 2** | Klik tombol "Hapus Produk" | ✅ Tombol ditekan |
| **Action 3** | Dialog konfirmasi muncul "Apakah Anda yakin?" | ✅ Dialog terbuka |
| **Action 4** | Klik "OK" untuk confirm | ✅ Button OK ditekan |
| **Verify 1** | Pesan "Produk berhasil dihapus" muncul | ✅ Success message |
| **Verify 2** | Baris produk hilang dari TableView | ✅ P002 tidak ada di table |
| **Verify 3** | Database terupdate (produk tidak ada di DB) | ✅ DELETE executed di DB |

**Result:** ✅ **PASS**

#### Test Case 4: Menghapus Produk tanpa Seleksi

| Tahap | Deskripsi | Expected Result |
|-------|-----------|-----------------|
| **Setup** | TableView menampilkan data | ✅ Data terlihat |
| **Action** | Klik tombol "Hapus Produk" tanpa pilih baris | ✅ Tombol ditekan |
| **Verify** | Dialog warning "Pilih produk untuk dihapus!" muncul | ✅ Warning message |

**Result:** ✅ **PASS**

#### Test Case 5: Refresh Data

| Tahap | Deskripsi | Expected Result |
|-------|-----------|-----------------|
| **Setup** | Aplikasi berjalan dengan data di TableView | ✅ Data terlihat |
| **Action** | Tambah produk baru dari aplikasi lain (insert ke DB) | ✅ Produk ada di DB |
| **Action** | Klik tombol "Refresh" | ✅ Tombol ditekan |
| **Verify** | Data baru dari DB muncul di TableView | ✅ Data sync dengan DB |

**Result:** ✅ **PASS**

### 4.2 Validation Testing

#### Test Case 6: Validasi Input Form

| Input | Kondisi | Expected Result |
|-------|---------|-----------------|
| Code: "" (kosong) | Nama kosong | ❌ Reject, pesan "Code tidak boleh kosong" |
| Code: "P005", Name: "" | Nama kosong | ❌ Reject, pesan "Nama tidak boleh kosong" |
| Code: "P005", Name: "Produk", Price: "-100" | Price negatif | ❌ Reject, pesan "Harga harus > 0" |
| Code: "P005", Name: "Produk", Price: "50000", Stock: "-5" | Stock negatif | ❌ Reject, pesan "Stok tidak boleh negatif" |
| Code: "P005", Name: "Produk", Price: "50000", Stock: "20" | Semua valid | ✅ Accept, insert ke DB |

**Result:** ✅ **PASS** - Semua validation berfungsi

---

## 5. KETERKAITAN DENGAN BAB 6 (UML & SOLID)

### 5.1 UML Traceability Table

| No | Use Case (Bab 6) | Class Diagram | Sequence Diagram | Implementation |
|----|--------------------|---------------|-----------------|-----------------|
| 1 | **UC-01: Tampil Form & Data** | ✅ Product, DAO, Service, Controller, View | ✅ Actor → View → Controller → Service → DAO → DB | AppJavaFX → ProductTableView.loadInitialData() |
| 2 | **UC-02: Lihat Daftar Produk** | ✅ ProductTableView (TableView), ObservableList, PropertyValueFactory | ✅ User → btnRefresh → loadData() → getAllProducts() → findAll() → ResultSet → TableView refresh | ProductTableView.loadData() menggunakan TableView dengan lambda: `btnRefresh.setOnAction(e -> loadData())` |
| 3 | **UC-03: Tambah Produk** | ✅ ProductTableView (input form), ProductController, ProductService | ✅ User → TextField input → btnAdd (lambda) → handleAddProduct() → insert() → validation → DAO.insert() → DB commit → ObservableList.add() → TableView refresh | `btnAdd.setOnAction(e -> handleAddProduct())` dengan lambda expression |
| 4 | **UC-04: Hapus Produk** | ✅ ProductTableView (table selection), Alert dialog | ✅ User → tableView.select() → btnDelete (lambda) → confirm dialog → handleDeleteProduct() → delete() → DAO.delete() → ObservableList.remove() → TableView refresh | `btnDelete.setOnAction(e -> handleDeleteProduct())` dengan Alert confirmation dialog |
| 5 | **SD-01: Menampilkan Data** | ✅ ProductTableView extends VBox, PropertyValueFactory<Product, String> | ✅ :Actor → :ProductTableView → :ProductController → :ProductService → :ProductDAO → :Database | propertyValueFactory = new PropertyValueFactory<>("code") untuk automatic binding |
| 6 | **SD-02: Menambah Produk** | ✅ TextFields, Button, validation logic | ✅ TextField input → parse → validate (SRP) → service.insert() → dao.insert() → DB update | Service layer melakukan validation sebelum DAO.insert() |
| 7 | **SD-03: Menghapus Produk** | ✅ TableView selection, Alert dialog, Button | ✅ Product selection → confirmation → service.delete() → dao.delete() → DB update → ObservableList notification → TableView refresh | handleDeleteProduct() menggunakan Alert(AlertType.CONFIRMATION) untuk user confirmation |
| 8 | **SOLID-1: SRP** | ✅ Each class punya 1 responsibility | ProductDAO hanya database ops, Service hanya validation+logic, View hanya UI | Product: model; ProductDAO: DB abstraction; ProductService: business rules; ProductController: coordination; ProductTableView: GUI only |
| 9 | **SOLID-2: OCP** | ✅ Classes open for extension, closed for modification | ProductDAO interface allows new implementations | ProductDAO interface, ProductDAOImpl, dapat di-extend dengan ProductDAOMongo tanpa ubah existing |
| 10 | **SOLID-3: LSP** | ✅ Subtypes can substitute base types | ProductDAOImpl implements ProductDAO fully | `ProductDAO dao = new ProductDAOImpl(connection);` - implementasi substitute interface |
| 11 | **SOLID-4: ISP** | ✅ Clients depend on specific interfaces | ProductDAO interface focused on CRUD only | 5 methods: insert, findByCode, findAll, update, delete - no extra methods |
| 12 | **SOLID-5: DIP** | ✅ High-level modules depend on abstractions | ProductTableView → ProductController → ProductService → ProductDAO (interface) | Dependency injection via constructor: `new ProductTableView(controller)` |

### 5.2 SOLID Principles Implementation Detail

#### A. Single Responsibility Principle (SRP)

```
┌─────────────────────────────────────────────────────────────┐
│  LAYERED ARCHITECTURE - Each layer has 1 responsibility     │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  View Layer (ProductTableView)                              │
│  Responsibility: Rendering UI dan handling user input       │
│  Methods: buildUI(), loadData(), handleAddProduct(), etc    │
│                                                              │
│  Controller Layer (ProductController)                       │
│  Responsibility: Koordinasi antara View dan Service         │
│  Methods: addProduct(), deleteProduct(), getAllProducts()   │
│                                                              │
│  Service Layer (ProductService)                             │
│  Responsibility: Business logic dan validation              │
│  Methods: insert() {validate + DAO.insert()}               │
│                                                              │
│  DAO Layer (ProductDAOImpl)                                  │
│  Responsibility: Database operations                        │
│  Methods: insert(), delete(), findAll(), update()           │
│                                                              │
│  Model Layer (Product)                                      │
│  Responsibility: Data representation                        │
│  Properties: code, name, price, stock                       │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

#### B. Dependency Inversion Principle (DIP)

```java
// DIP Implementation di AppJavaFX

// HIGH-LEVEL MODULES depend on ABSTRACTIONS
ProductDAO dao = new ProductDAOImpl(connection);  // ✅ Depend on interface
ProductService service = new ProductService(dao);  // ✅ Depend on interface
ProductController controller = new ProductController(service);
ProductTableView view = new ProductTableView(controller);

// Result: View tidak tahu implementasi DAO
// View hanya tahu interface ProductDAO
// Bisa ganti implementasi DAO tanpa ubah View
```

---

## 6. LAMBDA EXPRESSION ANALYSIS

### 6.1 Lambda Expression Digunakan di ProductTableView

#### Lambda 1: Add Product Button

```java
btnAdd.setOnAction(e -> handleAddProduct());

// Equivalente dengan:
btnAdd.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent e) {
        handleAddProduct();
    }
});

// Lambda lebih ringkas: (parameter) -> { body }
// e adalah ActionEvent parameter yang implicitly diketahui
// -> pointing ke method handleAddProduct()
```

#### Lambda 2: Delete Product Button

```java
btnDelete.setOnAction(e -> handleDeleteProduct());

// Sama seperti Lambda 1, tapi memanggil handleDeleteProduct()
// Method ini akan:
// 1. Get selected item dari TableView
// 2. Show confirmation dialog
// 3. If confirmed: call controller.deleteProduct()
// 4. Update ObservableList (TableView otomatis refresh)
```

#### Lambda 3: Refresh Button

```java
btnRefresh.setOnAction(e -> loadData());

// Memanggil loadData() yang:
// 1. controller.getAllProducts() → get List<Product>
// 2. productList.clear() → clear ObservableList
// 3. productList.addAll(products) → add fresh data
// 4. TableView automatically updated karena bound ke ObservableList
```

### 6.2 Benefits of Lambda Expression

| Aspek | Traditional Anonymous Class | Lambda Expression | Benefit |
|-------|---------------------------|-------------------|---------|
| **Lines of Code** | 5-10 baris | 1 baris | ✅ Lebih ringkas |
| **Readability** | Verbose | Concise | ✅ Lebih mudah dibaca |
| **Performance** | Sedikit overhead | Optimized | ✅ Lebih cepat |
| **Syntax** | new EventHandler { ... } | e -> { ... } | ✅ Lebih modern |

---

## 7. DATA BINDING & REACTIVE UPDATES

### 7.1 PropertyValueFactory Mechanism

```
AUTOMATIC DATA BINDING via PropertyValueFactory:

Object Model (Product)
    ├─ code: String
    ├─ name: String
    ├─ price: Double
    └─ stock: Integer
        │
        ├─ getCode() method
        ├─ getName() method
        ├─ getPrice() method
        └─ getStock() method
            │
            ▼
    TableColumn PropertyValueFactory
    
    ┌──────────────────────────────────────────┐
    │ TableColumn<Product, String> codeColumn  │
    │ codeColumn.setCellValueFactory(          │
    │   new PropertyValueFactory<>("code")     │ ◄── Uses reflection
    │ );                                       │     to find getCode()
    └──────────────────────────────────────────┘
            │
            ▼
    REFLECTION finds:
    Product.getCode() → "P001"
    
            │
            ▼
    Column Cell displays:
    ┌─────────────┐
    │   P001      │  ◄── Value automatically set
    └─────────────┘
```

### 7.2 ObservableList Reactive Updates

```
USER ACTION (Add Product):

1. User input: Code=P004, Name="Pupuk", Price=50000, Stock=30
                ▼
2. btnAdd clicked → lambda e -> handleAddProduct()
                ▼
3. handleAddProduct() validates dan call controller.addProduct()
                ▼
4. service.insert(product) → validation → dao.insert() → DB update
                ▼
5. If success: ObservableList.add(product)
                ▼
6. TableView listens to ObservableList changes
                ▼
7. TableView automatically refreshes and displays new row
                ▼
    ┌────────────────────────────┐
    │ Code | Name      | P | Stk │
    ├────────────────────────────┤
    │ P001 | Pupuk Urea| 25| 100 │
    │ P002 | Benih     | 35|  50 │
    │ P003 | Pestisida | 45|  25 │
    │ P004 | Pupuk     | 50|  30 │ ◄── NEW ROW
    └────────────────────────────┘

UI otomatis updated tanpa explicit repaint!
```

---

## 8. COMPARISON: Week 12 vs Week 13

| Aspek | Week 12 (ListView) | Week 13 (TableView) | Improvement |
|-------|-------------------|-------------------|-------------|
| **Display Component** | ListView<String> | TableView<Product> | ✅ Structured columns |
| **Data Binding** | Manual string concatenation | PropertyValueFactory | ✅ Automatic binding |
| **Event Handlers** | Anonymous class | Lambda expression | ✅ Concise syntax |
| **Data Updates** | Direct ObservableList | ObservableList + TableView sync | ✅ Reactive |
| **Delete Operation** | Simple delete | Delete + confirmation dialog | ✅ User safety |
| **Data Structure** | Only string representation | Full object properties accessible | ✅ Better design |

---

## 9. COMPILATION & EXECUTION RESULTS

### 9.1 Maven Compilation

```
$ mvn clean compile

[INFO] --- compiler:3.11.0:compile (default-compile) @ agripos-gui-lanjutan ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 8 source files with javac [debug target 11] to target\classes
[INFO] 
[INFO] BUILD SUCCESS [Total time: 3.010 s]
```

✅ **Status:** COMPILE SUCCESS - No errors, No warnings

### 9.2 Application Execution

```bash
$ mvn javafx:run
```

✅ **Status:** APPLICATION RUNS SUCCESSFULLY
- Database connection: ✅ Connected
- MVC initialization: ✅ Complete
- UI rendering: ✅ TableView displayed with 4 columns
- Initial data load: ✅ Products loaded from database

---

## 10. KESIMPULAN

### 10.1 Pembelajaran Kunci

1. ✅ **TableView** - Komponen JavaFX powerful untuk menampilkan data terstruktur
2. ✅ **PropertyValueFactory** - Automatic data binding via reflection
3. ✅ **Lambda Expression** - Syntax concise untuk functional programming
4. ✅ **ObservableList** - Reactive data structure yang auto-update UI
5. ✅ **MVC Architecture** - Clean separation of concerns
6. ✅ **SOLID Principles** - Extensible dan maintainable code
7. ✅ **Event-Driven Programming** - Responsive user interface

### 10.2 Kontribusi Week 13

Week 13 berhasil:

- ✅ Mengganti ListView dengan TableView untuk display lebih terstruktur
- ✅ Mengimplementasikan lambda expression untuk 3 event handlers
- ✅ Menambahkan confirmation dialog untuk delete operation
- ✅ Mempertahankan semua SOLID principles dari Week 12
- ✅ Maintaining clean MVC architecture dengan dependency injection
- ✅ Full traceability dengan Bab 6 UML & SOLID specifications

### 10.3 Saran Pengembangan Lebih Lanjut

Untuk Week 14+, bisa dikembangkan dengan:

1. **Search/Filter** - Filter data di TableView berdasarkan criteria
2. **Sorting** - Automatic sorting ketika klik column header
3. **Pagination** - Handle big data dengan pagination
4. **Styling** - CSS styling untuk TableView appearance
5. **Export** - Export data ke CSV/PDF format
6. **Real-time Sync** - WebSocket untuk real-time multi-user updates

---

## 11. REFERENSI

### Official Documentation
- JavaFX Documentation: https://openjfx.io/openjfx-docs/
- Oracle JavaFX Tutorial: https://docs.oracle.com/javase/tutorial/uiswing/
- PostgreSQL JDBC Driver: https://jdbc.postgresql.org/

### Learning Resources
- Lambda Expression: https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
- TableView Guide: https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/TableView.html
- SOLID Principles: https://www.baeldung.com/solid-principles

---

**Laporan Praktikum Week 13 - Selesai**

*Disusun untuk memenuhi tugas Mata Kuliah Pemrograman Berorientasi Objek*  
*Semester Ganjil 2024/2025*

---
