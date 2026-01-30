# DOKUMENTASI TEKNIS WEEK 13
## GUI Lanjutan JavaFX - TableView dan Lambda Expression

---

## DAFTAR ISI

1. [Arsitektur Sistem](#arsitektur-sistem)
2. [Design Pattern & SOLID](#design-pattern--solid)
3. [Komponen TableView](#komponen-tableview)
4. [Lambda Expression Implementation](#lambda-expression-implementation)
5. [Data Flow Diagram](#data-flow-diagram)
6. [API Reference](#api-reference)
7. [Troubleshooting](#troubleshooting)

---

## 1. ARSITEKTUR SISTEM

### 1.1 Layered Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        PRESENTATION LAYER                   │
│              ProductTableView (JavaFX GUI)                  │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  TableView<Product>  │ Input Form │ Buttons │ Status │   │
│  └──────────────────────────────────────────────────────┘   │
│                             ↓                               │
│  Lambda Expression Event Handlers:                         │
│  • btnAdd.setOnAction(e -> handleAddProduct())             │
│  • btnDelete.setOnAction(e -> handleDeleteProduct())       │
│  • btnRefresh.setOnAction(e -> loadData())                 │
└─────────────────────────────────────────────────────────────┘
                             ↓
┌─────────────────────────────────────────────────────────────┐
│                     APPLICATION LAYER                       │
│         ProductController (Business Coordination)            │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ + addProduct(code, name, price, stock): boolean     │   │
│  │ + deleteProduct(code): boolean                       │   │
│  │ + getAllProducts(): List<Product>                    │   │
│  │ + updateProduct(product): boolean                    │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                             ↓
┌─────────────────────────────────────────────────────────────┐
│                     BUSINESS LOGIC LAYER                    │
│            ProductService (Validation & Rules)              │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ + insert(product): boolean {                         │   │
│  │    if (validate(product)) DAO.insert()              │   │
│  │ }                                                    │   │
│  │ + delete(code): boolean { DAO.delete(code) }        │   │
│  │ + findAll(): List<Product> { DAO.findAll() }        │   │
│  └──────────────────────────────────────────────────────┘   │
│                                                              │
│  Validation Rules:                                          │
│  ✓ Code tidak boleh kosong                                  │
│  ✓ Name tidak boleh kosong                                  │
│  ✓ Price > 0                                               │
│  ✓ Stock >= 0                                              │
└─────────────────────────────────────────────────────────────┘
                             ↓
┌─────────────────────────────────────────────────────────────┐
│                   DATA ACCESS LAYER (DAO)                   │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  «interface» ProductDAO                              │   │
│  │  ─────────────────────────────────────────────────   │   │
│  │  + insert(Product): boolean                          │   │
│  │  + findByCode(String): Product                       │   │
│  │  + findAll(): List<Product>                          │   │
│  │  + update(Product): boolean                          │   │
│  │  + delete(String): boolean                           │   │
│  └──────────────────────────────────────────────────────┘   │
│                            ↑                                │
│                    implements                              │
│                            ↓                                │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  ProductDAOImpl (JDBC Implementation)                 │   │
│  │  ─────────────────────────────────────────────────   │   │
│  │  - connection: Connection                            │   │
│  │  + insert(): SQL INSERT ...                          │   │
│  │  + delete(): SQL DELETE ...                          │   │
│  │  + findAll(): SQL SELECT ...                         │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                             ↓
┌─────────────────────────────────────────────────────────────┐
│                      DATABASE LAYER                         │
│              PostgreSQL (agripos database)                  │
│  Table: products                                            │
│  ├─ code VARCHAR(20) PRIMARY KEY                           │
│  ├─ name VARCHAR(100) NOT NULL                             │
│  ├─ price NUMERIC(10,2) NOT NULL                           │
│  └─ stock INTEGER NOT NULL                                 │
└─────────────────────────────────────────────────────────────┘
```

### 1.2 Package Structure

```
com.upb.agripos/
├── AppJavaFX.java
│   └─ Main entry point, initializes database & MVC
│
├── model/
│   └── Product.java
│       └─ Entity dengan properties: code, name, price, stock
│
├── dao/
│   ├── ProductDAO.java
│   │   └─ Interface (abstraction untuk DIP)
│   └── ProductDAOImpl.java
│       └─ JDBC implementation dengan PreparedStatement
│
├── service/
│   └── ProductService.java
│       └─ Business logic & validation layer
│
├── controller/
│   └── ProductController.java
│       └─ Koordinasi View ↔ Service
│
└── view/
    └── ProductTableView.java (NEW - WEEK 13)
        └─ JavaFX GUI dengan TableView & Lambda Expressions
```

---

## 2. DESIGN PATTERN & SOLID

### 2.1 Design Patterns Used

#### A. Model-View-Controller (MVC)

```
MODEL:
- Product class (data representation)
- ProductDAO (interface)
- ProductDAOImpl (implementation)

VIEW:
- ProductTableView (JavaFX UI)
- TableView component
- Input form fields

CONTROLLER:
- ProductController
- Coordinates between View and Service
```

#### B. Data Access Object (DAO) Pattern

```
Benefits:
✓ Abstraction dari database operations
✓ Mudah untuk swap implementation
✓ Testability meningkat (bisa mock DAO)

Implementation:
├── ProductDAO interface (contracts)
└── ProductDAOImpl (concrete implementation)
```

#### C. Service Layer Pattern

```
Responsibilities:
✓ Business logic
✓ Validation
✓ Transactions

Methods:
- insert() dengan validation
- delete() dengan business rules
- findAll() dengan filtering logic
```

#### D. Dependency Injection (DI)

```java
// Constructor Injection
public ProductTableView(ProductController controller) {
    this.controller = controller;  // Injected dependency
}

// Benefits:
// ✓ Loose coupling
// ✓ Easy to test
// ✓ Easy to swap implementations
```

### 2.2 SOLID Principles Implementation

#### 1. Single Responsibility Principle (SRP)

```
Product:
├─ Responsibility: Data representation
└─ Methods: getters, setters, toString()

ProductDAO:
├─ Responsibility: Define database contracts
└─ Methods: 5 CRUD operations

ProductDAOImpl:
├─ Responsibility: JDBC database operations
└─ Methods: SQL execution, ResultSet mapping

ProductService:
├─ Responsibility: Business validation
└─ Methods: insert() with validation, delete(), findAll()

ProductController:
├─ Responsibility: View-Service coordination
└─ Methods: addProduct(), deleteProduct(), getAllProducts()

ProductTableView:
├─ Responsibility: User interface rendering
└─ Methods: buildUI(), loadData(), event handlers
```

#### 2. Open/Closed Principle (OCP)

```
OPEN for extension:
- Can add new DAO implementations (ProductDAOMongo, ProductDAOJPA)
- Can add new validation rules in Service
- Can extend TableView with new columns

CLOSED for modification:
- Don't need to modify existing ProductDAO interface
- Don't need to modify existing Service implementation
- Don't need to modify existing TableView to add new DAO
```

#### 3. Liskov Substitution Principle (LSP)

```
ProductDAO interface:
- ProductDAOImpl implements fully
- All methods behave as per contract
- Can substitute ProductDAOImpl with other DAO

Substitution:
ProductDAO dao = new ProductDAOImpl(connection);  // ✓ Valid
ProductDAO dao = new ProductDAOMongo();          // ✓ Valid too
// Both are valid implementations of ProductDAO
```

#### 4. Interface Segregation Principle (ISP)

```
ProductDAO interface:
- 5 focused methods (CRUD only)
- No extra methods that clients don't need
- Clients depend only on needed operations

Methods:
✓ insert(Product)
✓ findByCode(String)
✓ findAll()
✓ update(Product)
✓ delete(String)
✗ No irrelevant methods like export(), import(), etc
```

#### 5. Dependency Inversion Principle (DIP)

```
WRONG (Tightly coupled):
View → ProductService → ProductDAOImpl
(View directly depends on concrete DAO implementation)

CORRECT (Loosely coupled):
View → ProductController → ProductService → ProductDAO (interface)
                                                ↓
                                        ProductDAOImpl (concrete)

Benefits:
✓ View tidak tahu ProductDAOImpl
✓ Bisa ganti implementasi tanpa ubah View
✓ Easy to test dengan mock DAO
```

---

## 3. KOMPONEN TABLEVIEW

### 3.1 TableView Struktur

```
TableView<Product>
├── TableColumn<Product, String> - Code
│   └─ PropertyValueFactory<Product, String>("code")
│
├── TableColumn<Product, String> - Name
│   └─ PropertyValueFactory<Product, String>("name")
│
├── TableColumn<Product, Double> - Price
│   └─ PropertyValueFactory<Product, Double>("price")
│
└── TableColumn<Product, Integer> - Stock
    └─ PropertyValueFactory<Product, Integer>("stock")
```

### 3.2 ObservableList Data Binding

```
Data Binding Mechanism:

    Controller getAllProducts()
            ↓
        [List<Product>]
            ↓
    productList.clear()  ◄── ObservableList
            ↓
    productList.addAll(products)
            ↓
    TableView listens to ObservableList changes
            ↓
    TableView fires refresh()
            ↓
    ┌─────────────────────────────────┐
    │ Code │ Name │ Price │ Stock     │
    ├─────────────────────────────────┤
    │ P001 │ Urea │ 25000 │ 100       │ ◄── NEW
    │ P002 │ Benih│ 35000 │ 50        │     DISPLAY
    │ P003 │ Pest │ 45000 │ 25        │
    └─────────────────────────────────┘
```

### 3.3 PropertyValueFactory Mechanism

```
PropertyValueFactory works with JavaBeans:

public class Product {
    private String name;
    
    public String getName() { return name; }  ◄── Used by PropertyValueFactory
    public void setName(String n) { name = n; }
}

TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
nameColumn.setCellValueFactory(
    new PropertyValueFactory<>("name")  ◄── Looks for getName() method
);

Automatic binding:
1. Reflection finds property "name"
2. Invokes getName() method
3. Gets value from Product object
4. Sets in TableCell
```

### 3.4 Column Configuration

```java
// Code Column
TableColumn<Product, String> codeColumn = new TableColumn<>("Code");
codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
codeColumn.setPrefWidth(100);

// Name Column  
TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
nameColumn.setPrefWidth(150);

// Price Column
TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
priceColumn.setPrefWidth(120);

// Stock Column
TableColumn<Product, Integer> stockColumn = new TableColumn<>("Stock");
stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
stockColumn.setPrefWidth(100);

// Add all columns
tableView.getColumns().addAll(
    codeColumn, nameColumn, priceColumn, stockColumn
);
```

---

## 4. LAMBDA EXPRESSION IMPLEMENTATION

### 4.1 Lambda Expression Syntax

```java
// Traditional Anonymous Class
button.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
        System.out.println("Button clicked");
    }
});

// Lambda Expression (Java 8+)
button.setOnAction(event -> System.out.println("Button clicked"));

// Lambda with multiple statements
button.setOnAction(event -> {
    System.out.println("Button clicked");
    loadData();
    statusLabel.setText("Data loaded");
});
```

### 4.2 Event Handlers dengan Lambda

#### Lambda 1: Add Product Handler

```java
btnAdd.setOnAction(e -> handleAddProduct());

// Equivalent:
btnAdd.setOnAction(e -> {
    handleAddProduct();
});

// What handleAddProduct() does:
private void handleAddProduct() {
    // 1. Parse input fields
    String code = tfCode.getText().trim();
    String name = tfName.getText().trim();
    double price = Double.parseDouble(tfPrice.getText());
    int stock = Integer.parseInt(tfStock.getText());
    
    // 2. Create Product object
    Product product = new Product(code, name, price, stock);
    
    // 3. Call controller
    boolean success = controller.addProduct(code, name, price, stock);
    
    // 4. If successful, reload data
    if (success) {
        loadData();
        clearInputFields();
        statusLabel.setText("Produk berhasil ditambahkan");
    }
}
```

#### Lambda 2: Delete Product Handler

```java
btnDelete.setOnAction(e -> handleDeleteProduct());

// What handleDeleteProduct() does:
private void handleDeleteProduct() {
    Product selected = tableView.getSelectionModel().getSelectedItem();
    
    if (selected == null) {
        showAlert("Warning", "Pilih produk untuk dihapus!");
        return;
    }
    
    // Show confirmation dialog
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
        } else {
            showAlert("Error", "Gagal menghapus produk");
        }
    }
}
```

#### Lambda 3: Refresh Handler

```java
btnRefresh.setOnAction(e -> loadData());

// What loadData() does:
private void loadData() {
    try {
        // Get fresh data from service
        List<Product> products = controller.getAllProducts();
        
        // Update ObservableList
        productList.clear();
        productList.addAll(products);
        
        // Update status
        statusLabel.setText("Total produk: " + products.size());
    } catch (Exception e) {
        showAlert("Error", "Gagal load data: " + e.getMessage());
    }
}
```

### 4.3 Lambda vs Anonymous Class Comparison

```
LAMBDA:
✓ Lebih concise (1-2 baris vs 5-10 baris)
✓ Mudah dibaca dan dipahami
✓ Implicitly typed (tipe sudah diketahui)
✓ Better performance (no extra class creation)
✗ Single abstract method only

ANONYMOUS CLASS:
✓ Support multiple methods
✗ Verbose (5-10 baris)
✗ Create extra class in bytecode
✗ Harder to read
```

---

## 5. DATA FLOW DIAGRAM

### 5.1 Add Product Flow

```
USER
  │
  ├─ Input Code, Name, Price, Stock
  │
  └─ Click "Tambah Produk" button
         │
         ▼
  Lambda: btnAdd.setOnAction(e -> handleAddProduct())
         │
         ▼
  handleAddProduct() method
         │
         ├─ Parse input fields (getText(), parseInt, parseDouble)
         │
         ├─ Create Product object
         │
         ▼
  ProductController.addProduct(code, name, price, stock)
         │
         ▼
  ProductService.insert(product)
         │
         ├─ Validate:
         │  ├─ code != null && !empty
         │  ├─ name != null && !empty
         │  ├─ price > 0
         │  └─ stock >= 0
         │
         ▼ (if valid)
  ProductDAO.insert(product)
         │
         ▼ (JDBC)
  SQL: INSERT INTO products (code, name, price, stock)
       VALUES (?, ?, ?, ?)
         │
         ▼
  PostgreSQL Database
         │
         ▼
  Commit transaction
         │
         ▼ (success response)
  Back to handleAddProduct()
         │
         ├─ clearInputFields()
         │
         ├─ loadData() → refresh TableView
         │
         └─ showAlert("Produk berhasil ditambahkan")
```

### 5.2 Delete Product Flow

```
USER
  │
  └─ Select row in TableView
         │
         ▼
  tableView.getSelectionModel().getSelectedItem()
         │
         ▼
  Click "Hapus Produk" button
         │
         ▼
  Lambda: btnDelete.setOnAction(e -> handleDeleteProduct())
         │
         ▼
  handleDeleteProduct() method
         │
         ├─ Get selected Product
         │
         ├─ If null → show warning
         │
         ▼
  Show Alert(CONFIRMATION)
         │
         ├─ "Apakah Anda yakin?"
         │
         ├─ User klik OK
         │
         ▼
  ProductController.deleteProduct(code)
         │
         ▼
  ProductService.delete(code)
         │
         ▼
  ProductDAO.delete(code)
         │
         ▼ (JDBC)
  SQL: DELETE FROM products WHERE code = ?
         │
         ▼
  PostgreSQL Database
         │
         ▼
  Commit transaction
         │
         ▼ (success response)
  Back to handleDeleteProduct()
         │
         ├─ loadData() → refresh TableView
         │
         └─ showAlert("Produk berhasil dihapus")
```

### 5.3 Load Data Flow

```
USER / Application Start
  │
  └─ Call loadData() or init
         │
         ▼
  Lambda: btnRefresh.setOnAction(e -> loadData())
         │
         ▼
  loadData() method
         │
         ▼
  ProductController.getAllProducts()
         │
         ▼
  ProductService.findAll()
         │
         ▼
  ProductDAO.findAll()
         │
         ▼ (JDBC)
  SQL: SELECT code, name, price, stock FROM products
         │
         ▼
  PostgreSQL Database
         │
         ▼
  ResultSet {
    code: P001, name: "Pupuk Urea", price: 25000, stock: 100
    code: P002, name: "Benih Padi", price: 35000, stock: 50
    code: P003, name: "Pestisida", price: 45000, stock: 25
  }
         │
         ▼
  Create List<Product> dari ResultSet
         │
         ▼
  Back to loadData()
         │
         ├─ productList.clear()  (ObservableList)
         │
         ├─ productList.addAll(products)
         │       │
         │       ▼
         │  ObservableList fires change event
         │       │
         │       ▼
         │  TableView listens & refreshes
         │
         └─ statusLabel.setText("Total: 3")
         
         ▼
  ┌──────────────────────────────────────────┐
  │ TableView Updated Display:                │
  ├──────────────────────────────────────────┤
  │ Code │ Name          │ Price  │ Stock   │
  ├──────────────────────────────────────────┤
  │ P001 │ Pupuk Urea    │ 25000  │ 100     │
  │ P002 │ Benih Padi    │ 35000  │ 50      │
  │ P003 │ Pestisida     │ 45000  │ 25      │
  └──────────────────────────────────────────┘
```

---

## 6. API REFERENCE

### 6.1 ProductTableView Class

```java
public class ProductTableView extends VBox {
    
    // Constructor
    public ProductTableView(ProductController controller)
    
    // Public Methods
    public void loadInitialData()
    
    public void loadData()
    
    // Private Methods
    private void buildUI(): void
    
    private void handleAddProduct(): void
    
    private void handleDeleteProduct(): void
    
    private void clearInputFields(): void
    
    private void showAlert(String title, String message): void
}
```

### 6.2 ProductController Interface

```java
public class ProductController {
    
    public ProductController(ProductService service)
    
    public boolean addProduct(String code, String name, 
                             double price, int stock)
    
    public boolean deleteProduct(String code)
    
    public List<Product> getAllProducts()
    
    public boolean updateProduct(Product product)
}
```

### 6.3 ProductService Interface

```java
public class ProductService {
    
    public ProductService(ProductDAO dao)
    
    public boolean insert(Product product)
    
    public Product findByCode(String code)
    
    public List<Product> findAll()
    
    public boolean update(Product product)
    
    public boolean delete(String code)
}
```

### 6.4 ProductDAO Interface

```java
public interface ProductDAO {
    
    boolean insert(Product product) throws SQLException
    
    Product findByCode(String code) throws SQLException
    
    List<Product> findAll() throws SQLException
    
    boolean update(Product product) throws SQLException
    
    boolean delete(String code) throws SQLException
}
```

---

## 7. TROUBLESHOOTING

### 7.1 Common Issues & Solutions

#### Issue 1: "Cannot find symbol: PropertyValueFactory"

**Cause:** Missing JavaFX import

**Solution:**
```java
import javafx.scene.control.cell.PropertyValueFactory;
```

#### Issue 2: TableView not updating after data change

**Cause:** Using List<> instead of ObservableList<>

**Solution:**
```java
// WRONG
List<Product> productList = new ArrayList<>();
tableView.setItems(productList);  // Won't auto-update

// CORRECT
ObservableList<Product> productList = 
    FXCollections.observableArrayList();
tableView.setItems(productList);  // Auto-updates
```

#### Issue 3: Lambda expression shows error in IDE

**Cause:** Wrong target version (< Java 8)

**Solution:** Update pom.xml
```xml
<properties>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
</properties>
```

#### Issue 4: "NullPointerException when getting selected item"

**Cause:** No row selected in TableView

**Solution:**
```java
Product selected = tableView.getSelectionModel()
    .getSelectedItem();

if (selected == null) {
    showAlert("Warning", "Pilih produk terlebih dahulu!");
    return;
}
```

#### Issue 5: PropertyValueFactory can't find property

**Cause:** Wrong property name or missing getter method

**Solution:**
```java
// Property name must match getter method
// Property: "code" → needs getCode() method
// Property: "name" → needs getName() method

public class Product {
    private String code;
    public String getCode() { return code; }  ✓ Correct
}
```

---

## 8. PERFORMANCE TIPS

### 8.1 Optimization Techniques

1. **Use Platform.runLater() for UI updates**
   ```java
   Platform.runLater(() -> {
       productList.addAll(products);
   });
   ```

2. **Load large datasets with pagination**
   ```java
   // Instead of loading all 10000 rows at once
   // Load 50 rows at a time
   int pageSize = 50;
   loadPage(currentPage);
   ```

3. **Cache expensive queries**
   ```java
   // Don't call getAllProducts() every refresh
   // Use observer pattern for notifications
   ```

4. **Use appropriate data structures**
   ```java
   // ObservableList for TableView
   // HashMap for fast lookup
   // ArrayList for sequential access
   ```

---

## 9. SECURITY CONSIDERATIONS

### 9.1 SQL Injection Prevention

✓ **CORRECT** - Using PreparedStatement:
```java
String sql = "SELECT * FROM products WHERE code = ?";
PreparedStatement pstmt = connection.prepareStatement(sql);
pstmt.setString(1, code);  // Parameter binding
```

✗ **WRONG** - String concatenation:
```java
String sql = "SELECT * FROM products WHERE code = '" + code + "'";
// Vulnerable to SQL injection
```

### 9.2 Input Validation

```java
// Always validate user input
String code = tfCode.getText().trim();

if (code.isEmpty()) {
    showAlert("Error", "Code tidak boleh kosong");
    return;
}

if (code.length() > 20) {
    showAlert("Error", "Code maksimal 20 karakter");
    return;
}
```

---

**Dokumentasi Selesai**

*Untuk pertanyaan lebih lanjut, lihat laporan_week13.md*
