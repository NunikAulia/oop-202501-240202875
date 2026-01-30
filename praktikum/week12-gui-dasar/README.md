# Agri-POS Week 12 - GUI Dasar JavaFX

![Status](https://img.shields.io/badge/Status-Complete-brightgreen)
![Java](https://img.shields.io/badge/Java-11%2B-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-21.0.2-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-42.7.1-336791)

## 📋 Deskripsi

Aplikasi desktop Agri-POS dengan antarmuka grafis berbasis JavaFX untuk mengelola data produk. Aplikasi ini mengimplementasikan:

- **Event-Driven Programming**: Responsif terhadap aksi pengguna
- **MVC Architecture**: Pemisahan Model-View-Controller
- **SOLID Principles**: Terutama Dependency Inversion Principle
- **Database Integration**: Terhubung PostgreSQL via JDBC

## 🎯 Tujuan Pembelajaran

Mahasiswa mampu:
1. Menjelaskan konsep event-driven programming
2. Membangun GUI sederhana dengan JavaFX
3. Membuat form input dengan validasi
4. Mengintegrasikan GUI dengan backend (DAO & Service)
5. Menerapkan SOLID Principles dalam praktik

## 🏗️ Arsitektur Aplikasi

```
┌─────────────────┐
│   AppJavaFX     │  ← Entry Point
│   (Main)        │
└────────┬────────┘
         │
    ┌────▼─────┐
    │   Stage  │  ← JavaFX Window
    │  (Scene) │
    └────┬─────┘
         │
    ┌────▼──────────────┐
    │ ProductFormView   │  ← View Layer (GUI)
    │ (JavaFX)          │
    └────┬──────────────┘
         │
    ┌────▼─────────────────┐
    │ ProductController    │  ← Controller Layer (Events)
    └────┬─────────────────┘
         │
    ┌────▼──────────────────┐
    │ ProductService       │  ← Service Layer (Logic & Validation)
    └────┬──────────────────┘
         │
    ┌────▼──────────────────┐
    │ ProductDAO (Interface)│  ← DAO Layer (Data Access)
    │ ProductDAOImpl        │
    └────┬──────────────────┘
         │
    ┌────▼──────────────────┐
    │ PostgreSQL Database  │  ← Database
    └──────────────────────┘
```

## 📦 Struktur Proyek

```
praktikum/week12-gui-dasar/
├── src/main/java/com/upb/agripos/
│   ├── AppJavaFX.java                  (99 lines)
│   ├── model/
│   │   └── Product.java                (55 lines)
│   ├── dao/
│   │   ├── ProductDAO.java             (24 lines)
│   │   └── ProductDAOImpl.java          (72 lines)
│   ├── service/
│   │   └── ProductService.java         (96 lines)
│   ├── controller/
│   │   └── ProductController.java      (109 lines)
│   └── view/
│       └── ProductFormView.java        (246 lines)
├── pom.xml                              (Maven Config)
├── laporan_week12.md                    (Laporan Lengkap)
├── DOKUMENTASI.md                       (Dokumentasi Teknis)
├── README.md                            (File ini)
└── screenshots/
    └── [screenshots akan ditambahkan]
```

## 🚀 Quick Start

### Prerequisites

- Java 11+
- Maven 3.6+
- PostgreSQL dengan database `agripos`
- PostgreSQL JDBC Driver (auto-download via Maven)

### Setup Database

```sql
-- Buat database (jika belum ada)
CREATE DATABASE agripos;

-- Connect ke database
\c agripos;

-- Buat tabel products
CREATE TABLE products (
    code VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    stock INTEGER NOT NULL
);

-- (Optional) Insert sample data
INSERT INTO products VALUES ('P001', 'Benih Padi', 15000.00, 100);
INSERT INTO products VALUES ('P002', 'Pupuk Urea', 25000.00, 50);
```

### Compile & Run

```bash
# Clone/Navigate ke folder
cd praktikum/week12-gui-dasar

# Compile
mvn clean compile

# Run Application
mvn javafx:run
```

### Build JAR

```bash
mvn clean package
java -jar target/agripos-gui-dasar-1.0-SNAPSHOT-shaded.jar
```

## 💡 Fitur Utama

### 1. Tambah Produk
- Input form untuk: Kode, Nama, Harga, Stok
- Validasi input (tidak boleh kosong, harga > 0)
- Insert ke database via Service → DAO
- Auto-refresh ListView setelah sukses
- Status bar menampilkan pesan sukses/error

### 2. Lihat Daftar Produk
- Load otomatis saat aplikasi start
- Display dalam ListView dengan format: `[CODE] - [NAME] (Rp [PRICE]) Stok: [STOCK]`
- Refresh button untuk reload data dari database

### 3. Validasi Multi-Layer
- **View Layer**: Cek field tidak kosong
- **Controller Layer**: Parse type (String → Double/Integer)
- **Service Layer**: Validasi bisnis (harga > 0, stok >= 0)
- **Database Layer**: SQL constraint & error handling

## 🎨 GUI Components

| Komponen | Tipe | Fungsi |
|----------|------|--------|
| Kode Produk | TextField | Input kode unik (max 20 char) |
| Nama Produk | TextField | Input nama (max 100 char) |
| Harga (Rp) | TextField | Input harga (numeric, > 0) |
| Stok | TextField | Input stok (numeric, >= 0) |
| Tombol "Tambah Produk" | Button | Trigger insert event |
| Tombol "Refresh" | Button | Trigger load data event |
| Daftar Produk | ListView | Display semua produk |
| Status Bar | Label | Show operation status |

## 🔄 Event Flow

```
[User Click "Tambah Produk"]
          ↓
[handleAddProduct()] - View
          ↓
[controller.addProduct()] - Controller
          ↓
[service.insert()] - Service (Validation)
          ↓
[dao.insert()] - DAO (Database)
          ↓
[PostgreSQL INSERT]
          ↓
[clearFields() + loadProducts()] - View
          ↓
[Status: "Produk berhasil ditambahkan"]
```

## ✅ SOLID Principles

### Single Responsibility Principle
- `Product`: Entity/Model only
- `ProductDAO`: Database access only
- `ProductService`: Business logic & validation
- `ProductController`: Event handling only
- `ProductFormView`: UI rendering & display

### Dependency Inversion Principle
```
GOOD:
View → Controller → Service → ProductDAO (Interface) → ProductDAOImpl

BAD (Tidak diimplementasikan):
View → DAO → Database (Direct coupling)
```

### Interface Segregation Principle
```java
// ProductDAO interface: hanya 5 method CRUD yang diperlukan
interface ProductDAO {
    void insert(Product) throws Exception;
    Product findByCode(String) throws Exception;
    List<Product> findAll() throws Exception;
    void update(Product) throws Exception;
    void delete(String) throws Exception;
}
```

## 📊 Traceability ke Bab 6 (UML + SOLID)

| Artefak Bab 6 | Implementasi Week 12 | Status |
|---|---|---|
| Use Case: Kelola Produk | ProductController + ProductFormView | ✅ |
| Activity Diagram: Tambah Produk | handleAddProduct() flow | ✅ |
| Sequence Diagram: Tambah Produk | View → Controller → Service → DAO → DB | ✅ |
| Class Diagram | ProductDAO, ProductDAOImpl, ProductService, ProductController | ✅ |
| DIP (SOLID) | Interface ProductDAO + Dependency Injection | ✅ |
| SRP (SOLID) | Masing-masing class 1 tanggung jawab | ✅ |

Lihat [laporan_week12.md](laporan_week12.md) untuk tabel traceability lengkap.

## 🧪 Testing

### Test Case 1: Tambah Produk Valid
```
Input: P001, Benih Padi, 15000, 100
Expected: INSERT success, ListView update
Result: ✅ Pass
```

### Test Case 2: Validasi Field Kosong
```
Input: "", Benih, 20000, 50
Expected: Controller returns false
Result: ✅ Pass
```

### Test Case 3: Harga Negatif
```
Input: P002, Pupuk, -5000, 200
Expected: Service throws IllegalArgumentException
Result: ✅ Pass
```

### Test Case 4: Database Persistence
```
Action: Insert P001, tutup app, buka kembali
Expected: P001 masih ada
Result: ✅ Pass
```

## 🔧 Technologies & Dependencies

| Teknologi | Versi | Fungsi |
|-----------|-------|--------|
| Java | 11+ | Language |
| JavaFX | 21.0.2 | GUI Framework |
| PostgreSQL | 42.7.1 | Database Driver |
| Maven | 3.6+ | Build Tool |
| JUnit | 4.13.2 | Testing Framework |

### Maven Dependencies

```xml
<!-- JavaFX -->
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>21.0.2</version>
</dependency>

<!-- PostgreSQL JDBC -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.1</version>
</dependency>

<!-- JUnit -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>
```

## 📚 Dokumentasi Lengkap

- **[laporan_week12.md](laporan_week12.md)** - Laporan praktikum lengkap dengan:
  - Dasar teori Event-Driven Programming & JavaFX
  - Implementasi detail setiap layer
  - Traceability ke Bab 6 (UML + SOLID)
  - Database schema & operations

- **[DOKUMENTASI.md](DOKUMENTASI.md)** - Dokumentasi teknis dengan:
  - Class responsibility mapping
  - Event flow diagrams
  - Error handling & validation layers
  - Build & execution instructions
  - Troubleshooting guide

## 🐛 Troubleshooting

| Problem | Solution |
|---------|----------|
| **ClassNotFoundException: org.postgresql.Driver** | Pastikan postgresql dependency di pom.xml; run `mvn dependency:resolve` |
| **Database connection refused** | Cek PostgreSQL running, database `agripos` exists, credentials benar |
| **JavaFX module not found** | JavaFX harus di pom.xml, bukan di JDK; gunakan OpenJDK 11+ |
| **ListView kosong setelah insert** | Pastikan `loadProducts()` dipanggil setelah insert sukses |
| **Button tidak responsive** | `setOnAction()` harus di `setupEventHandlers()`, bukan di `initializeUI()` |

## 🚀 Future Enhancements (Week 13+)

- [ ] Replace ListView dengan TableView (terstruktur)
- [ ] Edit & Delete functionality
- [ ] Search/Filter produk
- [ ] Export ke CSV/Excel
- [ ] Undo/Redo functionality
- [ ] Input validation dengan visual feedback
- [ ] Loading indicator
- [ ] Multi-threading untuk DB operations

## 📝 Commit Message

```bash
git add .
git commit -m "week12-gui-dasar: [fitur] JavaFX GUI dengan event handling dan MVC integration"
```

## 👨‍💼 Author

**Praktikum OOP - Minggu 12**
- Dosen Pembimbing: [Nama Dosen]
- Mata Kuliah: [Nama Mata Kuliah]
- Universitas: [Universitas]

## 📄 License

Proprietary - Universitas

---

**Last Updated**: January 26, 2026
**Status**: ✅ Complete & Tested
