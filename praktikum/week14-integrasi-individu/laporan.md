# Laporan Praktikum Minggu 14 - Integrasi Individu (OOP + Database + GUI)

Topik: Integrasi Complete AgriPOS System menggabungkan OOP, Database, dan GUI

## Identitas
- Nama  : [Nama Mahasiswa]
- NIM   : [NIM Mahasiswa]
- Kelas : [Kelas]
- Dosen : [Nama Dosen]

---

## Tujuan

Mahasiswa mampu:
1. Mengintegrasikan konsep OOP (Bab 1–5) ke dalam satu aplikasi utuh
2. Mengimplementasikan rancangan UML + SOLID (Bab 6) menjadi kode nyata
3. Mengintegrasikan Collections + Keranjang (Bab 7) ke alur aplikasi
4. Menerapkan exception handling (Bab 9) untuk validasi dan error flow
5. Menerapkan pattern + unit testing (Bab 10) pada bagian yang relevan
6. Menghubungkan aplikasi dengan database via DAO + JDBC (Bab 11)
7. Menyajikan aplikasi berbasis Swing (Bab 12-13) yang terhubung ke backend

---

## Dasar Teori

### 1. **Dependency Inversion Principle (DIP)**
   - View tidak boleh akses DAO langsung
   - Alur: View → Controller → Service → DAO → Database
   - Abstraksi melalui interface dan dependency injection

### 2. **MVC Architecture**
   - **Model**: Product, Cart, CartItem
   - **View**: PosView (Swing GUI)
   - **Controller**: PosController (business logic coordination)

### 3. **Service Layer Pattern**
   - ProductService: Mengelola operasi produk dengan validasi
   - CartService: Mengelola operasi keranjang
   - Encapsulate business rules dan validasi

### 4. **DAO Pattern**
   - ProductDAO: Interface kontrak data access
   - JdbcProductDAO: Implementasi dengan JDBC dan PreparedStatement
   - Separasi data access dari business logic

### 5. **Collections & Generics (Bab 7)**
   - ArrayList untuk menyimpan produk di database
   - Cart menggunakan ArrayList<CartItem> untuk item
   - Stream API untuk perhitungan total

### 6. **Exception Handling (Bab 9)**
   - ValidationException: Custom exception untuk error bisnis
   - Try-catch untuk error handling di semua layer
   - User-friendly error messages

### 7. **Unit Testing (Bab 10)**
   - JUnit 5 untuk test CartService
   - Test CRUD tanpa GUI/database
   - Test validasi dan edge cases

---

## Ringkasan Integrasi Bab 1-13

| Bab | Konsep | Implementasi di Week 14 |
|-----|--------|------------------------|
| **1** | Paradigma OOP | Struktur class dengan inheritance & polymorphism |
| **2** | Class & Object | Product, Cart, CartItem model classes |
| **3** | Inheritance | - |
| **4** | Polymorphism | Interface ProductDAO dengan multiple implementations |
| **5** | Abstraction | ProductDAO interface, abstract service layer |
| **6** | UML + SOLID | Implementasi use case, activity, sequence, class diagram |
| **7** | Collections | ArrayList untuk Cart items, Stream untuk total |
| **8** | UTS | - |
| **9** | Exception | ValidationException untuk error handling |
| **10** | Pattern | DAO pattern, Service layer, JUnit testing |
| **11** | DAO + JDBC | JdbcProductDAO dengan PreparedStatement CRUD |
| **12** | GUI Dasar | Swing form input untuk products |
| **13** | GUI Lanjutan | JTable untuk display, menu, dialog, search |

---

## Struktur Direktori

```
week14-integrasi-individu/
├── src/main/java/com/upb/agripos/
│   ├── AppMain.java                    (Main entry point)
│   ├── model/
│   │   ├── Product.java               (Product model)
│   │   ├── Cart.java                  (Shopping cart)
│   │   └── CartItem.java              (Cart item)
│   ├── dao/
│   │   ├── ProductDAO.java            (Interface)
│   │   └── JdbcProductDAO.java        (JDBC implementation)
│   ├── service/
│   │   ├── ProductService.java        (Product business logic)
│   │   └── CartService.java           (Cart business logic)
│   ├── controller/
│   │   └── PosController.java         (Controller layer)
│   ├── view/
│   │   └── PosView.java               (Swing GUI)
│   └── exception/
│       └── ValidationException.java   (Custom exception)
├── src/test/java/com/upb/agripos/
│   └── service/
│       └── CartServiceTest.java       (JUnit tests)
├── screenshots/
│   ├── app_main.png
│   └── junit_result.png
└── laporan.md
```

---

## File yang Dibuat

### 1. Model Layer (3 files)
- **Product.java**: Produk dengan code, name, price, stock
- **Cart.java**: Keranjang mengelola CartItem collection
- **CartItem.java**: Item dalam keranjang dengan product & quantity

### 2. DAO Layer (2 files)
- **ProductDAO.java**: Interface CRUD operations
- **JdbcProductDAO.java**: JDBC implementation dengan PreparedStatement

### 3. Service Layer (2 files)
- **ProductService.java**: Business logic + validation untuk produk
- **CartService.java**: Business logic untuk keranjang

### 4. Controller & View
- **PosController.java**: Controller untuk koordinasi View-Service
- **PosView.java**: Swing GUI dengan 20+ komponen

### 5. Exception & Entry Point
- **ValidationException.java**: Custom exception untuk validasi
- **AppMain.java**: Entry point dengan dependency initialization

### 6. Testing
- **CartServiceTest.java**: 10 unit tests untuk CartService

---

## Langkah Praktikum

### Langkah 1: Setup Database
```sql
CREATE TABLE products (
    code VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100),
    price DOUBLE PRECISION,
    stock INTEGER
);
```

### Langkah 2: Implementasi Model
- Buat Product, Cart, CartItem dengan proper encapsulation
- Override equals, hashCode, toString

### Langkah 3: Implementasi DAO
- Buat ProductDAO interface
- Implementasi JdbcProductDAO dengan JDBC operations
- Gunakan PreparedStatement untuk SQL injection prevention

### Langkah 4: Implementasi Service
- Buat ProductService dengan validasi
- Buat CartService dengan inventory checking
- Throw ValidationException untuk error cases

### Langkah 5: Implementasi Controller
- Buat PosController sebagai coordinator
- Delegasi ke services
- Catch exceptions dan propagate

### Langkah 6: Implementasi View
- Buat PosView dengan Swing components
- Setup menu bar, table, dialogs
- Call controller methods (NEVER call DAO directly)

### Langkah 7: Testing
- Buat CartServiceTest dengan JUnit 5
- Test add, remove, total, validation
- Run tests tanpa GUI

### Langkah 8: Integration Testing
- Compile semua classes
- Run AppMain
- Test CRUD products dan cart operations

---

## Kode Program Ringkas

### Product.java - Model
```java
public class Product {
    private String code;
    private String name;
    private double price;
    private int stock;
    // Constructor, getters, setters, equals, hashCode, toString
}
```

### ProductDAO Interface
```java
public interface ProductDAO {
    void insert(Product product) throws Exception;
    Product findByCode(String code) throws Exception;
    List<Product> findAll() throws Exception;
    void update(Product product) throws Exception;
    void delete(String code) throws Exception;
}
```

### JdbcProductDAO - JDBC Implementation
```java
public class JdbcProductDAO implements ProductDAO {
    private final Connection connection;
    
    @Override
    public void insert(Product product) throws Exception {
        String sql = "INSERT INTO products (code, name, price, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getCode());
            // ... set other parameters
            ps.executeUpdate();
        }
    }
    // ... other CRUD methods
}
```

### ProductService - Business Logic
```java
public class ProductService {
    private final ProductDAO productDAO;
    
    public void insert(Product product) throws Exception {
        if (product.getCode() == null || product.getCode().isEmpty()) {
            throw new ValidationException("Code cannot be empty");
        }
        // ... other validations
        productDAO.insert(product);
    }
    // ... other service methods
}
```

### PosController - Coordinator
```java
public class PosController {
    private final ProductService productService;
    private final CartService cartService;
    
    public void addProduct(String code, String name, double price, int stock) throws Exception {
        Product product = new Product(code, name, price, stock);
        productService.insert(product);
    }
    
    public void addToCart(String productCode, int quantity) throws Exception {
        Product product = productService.findByCode(productCode);
        cartService.addItem(product, quantity);
    }
    // ... other methods
}
```

### AppMain - Entry Point
```java
public class AppMain {
    public static void main(String[] args) {
        System.out.println("Hello World, I am [Nama]-[NIM]");
        
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        ProductDAO dao = new JdbcProductDAO(conn);
        ProductService service = new ProductService(dao);
        CartService cartService = new CartService();
        PosController controller = new PosController(service, cartService);
        
        SwingUtilities.invokeLater(() -> {
            PosView view = new PosView(controller);
            view.setVisible(true);
        });
    }
}
```

---

## Tabel Traceability Bab 6 → Implementasi

| Artefak | Referensi | Handler/Trigger | Controller | Service | DAO | Dampak |
|---------|-----------|-----------------|-----------|---------|-----|--------|
| Use Case | UC-01 Tambah Produk | Button Add | `PosController.addProduct()` | `ProductService.insert()` | `ProductDAO.insert()` | DB insert + TableView reload |
| Use Case | UC-02 Lihat Produk | Inisialisasi/Refresh | `PosController.getAllProducts()` | `ProductService.findAll()` | `ProductDAO.findAll()` | TableView terisi dari DB |
| Use Case | UC-03 Edit Produk | Button Edit | `PosController.updateProduct()` | `ProductService.update()` | `ProductDAO.update()` | DB update + TableView refresh |
| Use Case | UC-04 Hapus Produk | Button Delete | `PosController.deleteProduct()` | `ProductService.delete()` | `ProductDAO.delete()` | DB delete + TableView reload |
| Use Case | UC-05 Tambah ke Keranjang | Button Add to Cart | `PosController.addToCart()` | `CartService.addItem()` | - | Cart display + total update |
| Use Case | UC-06 Lihat Keranjang | Update otomatis | `PosController.getCartSummary()` | `CartService.getCart()` | - | CartArea refresh dengan items |
| Activity | AD-Produk-01 Tambah | Form submit | Controller → Service | Validasi input | Insert | Item di DB |
| Activity | AD-Keranjang-01 Tambah | Quantity input | Controller → Service | Check stock | - | Item di cart |
| Sequence | SD-01 Tambah Produk | View → Controller → Service → DAO → DB | addProduct() | insert() | insert() | INSERT query |
| Sequence | SD-02 Hapus Produk | View → Controller → Service → DAO → DB | deleteProduct() | delete() | delete() | DELETE query |
| Class | Product | Model | - | - | - | Entity di DB |
| Class | ProductDAO | Interface | - | Dependency | Implementation | JDBC operations |
| Class | ProductService | Service | Dependency | Business logic | Dependency injection | Validasi & workflow |
| Class | PosController | Controller | Business logic | Dependency | - | Koordinasi layer |
| Class | PosView | View | Delegasi | - | - | GUI Swing |

---

## Fitur yang Diimplementasikan

### A. Produk Management
✅ Tambah produk dengan validasi (code, name, price, stock)
✅ Lihat daftar produk di JTable dari database
✅ Edit produk (update ke database)
✅ Hapus produk dengan konfirmasi
✅ Custom exception ValidationException untuk error handling

### B. Keranjang Belanja
✅ Tambah item ke keranjang dengan quantity check
✅ Validasi stock availability
✅ Lihat ringkasan keranjang (items + total)
✅ Hapus item dari keranjang
✅ Clear cart
✅ Perhitungan total otomatis

### C. Architecture & Pattern
✅ Dependency Inversion Principle (DIP)
✅ MVC Architecture (View → Controller → Service → DAO)
✅ DAO Pattern dengan interface & JDBC implementation
✅ Service Layer dengan business logic & validation
✅ Collections (ArrayList) untuk keranjang

### D. Database Integration
✅ JDBC dengan PreparedStatement (SQL injection prevention)
✅ Connection pooling ready
✅ CRUD operations lengkap
✅ Error handling dengan exception

### E. GUI Features
✅ Menu bar (File, Product, Cart)
✅ Product JTable dengan CRUD buttons
✅ Dialog boxes untuk input
✅ Cart display area dengan total
✅ Error/warning dialogs
✅ Split pane layout

### F. Testing & Quality
✅ JUnit 5 tests untuk CartService
✅ Test coverage untuk CRUD, validation, edge cases
✅ No GUI dependency untuk testing
✅ Proper exception testing

---

## Analisis

### Konsistensi UML Bab 6 → Implementasi
1. **Use Cases**: Semua use case (Tambah, Lihat, Edit, Hapus, Cart) diimplementasikan
2. **Activity Diagram**: Alur input → validasi → service → DAO → DB diikuti
3. **Sequence Diagram**: Pemanggilan layer terurut (View → Controller → Service → DAO)
4. **Class Diagram**: Struktur class, interface, inheritance sesuai desain

### SOLID Principles
- **S** (Single Responsibility): Setiap class punya satu tanggung jawab
- **O** (Open/Closed): Extensible via interface ProductDAO
- **L** (Liskov): JdbcProductDAO implements ProductDAO correctly
- **I** (Interface Segregation): ProductDAO interface fokus pada CRUD
- **D** (Dependency Inversion): View bergantung pada Controller interface, bukan implementasi langsung

### Perbedaan dengan Week 13
| Aspek | Week 13 | Week 14 |
|-------|---------|---------|
| **Data Storage** | In-memory ArrayList | PostgreSQL Database ✅ |
| **Architecture** | View only | Full MVC with Service/DAO ✅ |
| **Validation** | Basic if-else | Exception-based ✅ |
| **Testing** | Manual GUI test | JUnit unit tests ✅ |
| **Keranjang** | Tidak ada | Complete shopping cart ✅ |
| **Design Pattern** | Tidak ada | DAO + Service Pattern ✅ |

### Kendala & Solusi

| Kendala | Solusi |
|---------|--------|
| Database connection fail | Check PostgreSQL running, credentials, database exists |
| View akses DAO langsung | Implement Controller layer & remove DAO references dari View |
| Validation tersebar | Centralize di Service dengan custom exception |
| Testing GUI sulit | Test CartService non-UI dengan JUnit 5 |
| Cart data lost | Implement persistence jika diperlukan di future |

---

## Kesimpulan

Praktikum Week 14 berhasil mengintegrasikan seluruh konsep OOP (Bab 1-13) menjadi **Agri-POS System** yang complete:

### Pencapaian:
1. ✅ Full application dengan OOP principles
2. ✅ Database integration via JDBC & DAO pattern
3. ✅ Professional GUI dengan Swing
4. ✅ Business logic layer dengan validation
5. ✅ Unit testing untuk non-UI components
6. ✅ Proper exception handling & error flow
7. ✅ Architecture consistency dengan Bab 6 UML

### Teknologi:
- **Language**: Java
- **GUI**: Swing (JFrame, JTable, JDialog)
- **Database**: PostgreSQL
- **Persistence**: JDBC + PreparedStatement
- **Testing**: JUnit 5
- **Architecture**: MVC + Service + DAO

Aplikasi siap untuk dikembangkan lebih lanjut dengan fitur tambahan seperti:
- Report generation
- Multi-user dengan authentication
- Discount/promotion system
- Payment gateway integration
- Advanced analytics

---

## Quiz

### 1. Jelaskan Dependency Inversion Principle dan bagaimana diimplementasikan di Week 14!
**Jawaban:**
DIP menyatakan high-level module harus bergantung pada abstraksi, bukan concrete implementation.
Implementasi di Week 14:
- View bergantung pada PosController (abstraksi interface)
- PosController bergantung pada ProductService & CartService
- ProductService bergantung pada ProductDAO interface
- JdbcProductDAO adalah concrete implementation
- Database layer bisa diganti tanpa mengubah View/Controller

### 2. Bagaimana alur data dari User Input di View sampai ke Database?
**Jawaban:**
1. User klik button di PosView (View)
2. Event handler panggil PosController.addProduct()
3. Controller create Product object & panggil ProductService.insert()
4. Service validasi dengan exception jika invalid
5. Service panggil ProductDAO.insert() interface
6. JdbcProductDAO execute PreparedStatement ke PostgreSQL
7. Database INSERT product
8. View reload product table via controller.getAllProducts()

### 3. Apa keuntungan menggunakan custom exception ValidationException?
**Jawaban:**
- Domain-specific error handling (bukan generic Exception)
- Caller tahu error type tanpa parse message
- Stack trace lebih informatif
- Catch specific exception type dengan try-catch
- Extensible untuk exception hierarchy
- Better error reporting ke user

### 4. Jelaskan mengapa View tidak boleh akses DAO langsung!
**Jawaban:**
- Violates DIP dan separation of concerns
- View adalah presentation layer, DAO adalah data layer
- Business logic (validasi, inventory check) harus di Service
- Testing View menjadi sulit jika tergantung Database
- Maintenance sulit karena coupling tinggi
- Refactoring DAO mempengaruhi View
- Proper: View → Controller → Service → DAO

### 5. Bagaimana Cart menggunakan Collections dan perhitungan total?
**Jawaban:**
```java
private List<CartItem> items = new ArrayList<>();

public void addItem(Product product, int quantity) {
    // Check apakah product sudah ada
    for (CartItem item : items) {
        if (item.getProduct().getCode().equals(product.getCode())) {
            item.setQuantity(item.getQuantity() + quantity);
            return;
        }
    }
    items.add(new CartItem(product, quantity));
}

public double getTotal() {
    return items.stream()
        .mapToDouble(CartItem::getSubtotal)
        .sum(); // Stream API untuk total
}
```

### 6. Apa perbedaan ProductService dan JdbcProductDAO?
**Jawaban:**
| Aspek | ProductService | JdbcProductDAO |
|-------|--------|---------|
| Tanggung Jawab | Business logic & validation | Data access only |
| Layer | Service layer | Data layer |
| Validasi | YES - throw exception | NO - just execute |
| Database | NO - delegasi ke DAO | YES - JDBC operations |
| Testing | Easy - mock DAO | Need database |
| Contoh | Check duplicate code | INSERT/SELECT/UPDATE/DELETE |

### 7. Bagaimana PreparedStatement mencegah SQL Injection?
**Jawaban:**
```java
// SQL Injection vulnerable (WRONG):
String sql = "SELECT * FROM users WHERE name = '" + userInput + "'";

// PreparedStatement safe (CORRECT):
String sql = "SELECT * FROM products WHERE code = ?";
try (PreparedStatement ps = conn.prepareStatement(sql)) {
    ps.setString(1, productCode); // Parameter binding
    ps.executeQuery();
}
```
PreparedStatement:
- Separates SQL structure dari data
- Parameter binding automatic escaping
- Compiled once, reused multiple times
- Type-safe parameter setting

### 8. Bagaimana Unit Testing CartService tanpa Database atau GUI?
**Jawaban:**
```java
@Test
public void testAddItem() throws Exception {
    CartService service = new CartService(); // In-memory
    Product product = new Product("P1", "Test", 100, 10);
    
    service.addItem(product, 5);
    
    assertEquals(1, service.getItemCount());
    assertEquals(500, service.getTotal(), 0.01);
    assertFalse(service.isEmpty());
}
```
Keuntungan:
- Fast execution (no DB)
- No external dependencies
- Reproducible results
- Easy to add/remove tests
- Test business logic only

### 9. Apa yang dimaksud dengan "identify praktikum"?
**Jawaban:**
Setiap aplikasi wajib menampilkan identitas pembuat saat startup:
```java
System.out.println("Hello World, I am [Nama]-[NIM]");
```
Ini:
- Menunjukkan source application (tidak plagiat)
- Requirement dari RPS Week 1 (Paradigma OOP & Hello World)
- Diintegrasikan di AppMain entry point

### 10. Bagaimana struktur aplikasi memenuhi Week 12 dan Week 13?
**Jawaban:**
- **Week 12 (GUI Dasar)**: Swing form dengan input fields → PosView
- **Week 13 (GUI Lanjutan)**: JTable, menu, dialog → PosView advanced
- **Week 14 (Integrasi)**: GUI connected ke backend (DAO + Service)
  - View tidak standalone, punya Controller
  - Data dari database, bukan hardcoded
  - Full CRUD operations berfungsi
  - Cart feature terintegrasi
  - Exception handling proper

---

## Referensi
- JDBC Documentation: https://docs.oracle.com/javase/tutorial/jdbc/
- Swing API: https://docs.oracle.com/javase/tutorial/uiswing/
- Design Patterns: https://refactoring.guru/design-patterns/java
- SOLID Principles: https://en.wikipedia.org/wiki/SOLID
- JUnit 5: https://junit.org/junit5/docs/current/user-guide/

---

## Checklist Keberhasilan

- ✅ Aplikasi JavaFX/Swing berjalan tanpa error
- ✅ CRUD Produk menggunakan DAO (JDBC) & PostgreSQL
- ✅ Keranjang menggunakan Collections dan terintegrasi
- ✅ Custom exception ValidationException untuk validasi
- ✅ DAO Pattern dengan interface & JDBC implementation
- ✅ Service Layer dengan business logic
- ✅ PosController sebagai MVC coordinator
- ✅ Struktur View → Controller → Service → DAO konsisten
- ✅ Unit testing CartService dengan JUnit 5
- ✅ Laporan dengan UML traceability
- ✅ "Hello World" identity display
- ✅ Dependency Injection & DIP diterapkan
- ✅ PreparedStatement untuk SQL safety
- ✅ Exception handling di semua layer
- ✅ Documentation lengkap

---

## Catatan Implementasi

### Database Setup
```sql
CREATE DATABASE agripos;
\c agripos

CREATE TABLE products (
    code VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    stock INTEGER NOT NULL
);

INSERT INTO products VALUES 
('BNH-001', 'Benih Padi Premium', 25000, 100),
('BNH-002', 'Benih Jagung Hibrida', 15000, 150),
('PUP-001', 'Pupuk Urea 50kg', 250000, 50),
('PUP-002', 'Pupuk NPK 25kg', 180000, 75),
('ALT-001', 'Cangkul Baja', 75000, 25);
```

### Compilation
```bash
cd src/main/java
javac -d . com/upb/agripos/*.java
javac -d . com/upb/agripos/model/*.java
javac -d . com/upb/agripos/dao/*.java
javac -d . com/upb/agripos/service/*.java
javac -d . com/upb/agripos/controller/*.java
javac -d . com/upb/agripos/view/*.java
javac -d . com/upb/agripos/exception/*.java

# Test compilation
cd ../../../
javac -cp .:junit-jupiter-api-5.9.0.jar -d test/java com/upb/agripos/service/CartServiceTest.java
```

### Execution
```bash
java com.upb.agripos.AppMain
```

---
