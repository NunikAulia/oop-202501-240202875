# Laporan Praktikum Minggu 13 - GUI Lanjutan ((TableView dan Lambda Expression))

Topik: Implementasi GUI Lanjutan dengan JTable, Menu Bar, dan Dialog Boxes

## Identitas
- Nama  : [Nunik Aulia Primadani]
- NIM   : [240202875]
- Kelas : [3IKRB]

---

## Tujuan

1. Menampilkan data menggunakan TableView JavaFX.
2. Mengintegrasikan koleksi objek dengan GUI.
3. Menggunakan lambda expression untuk event handling.
4. Menghubungkan GUI dengan DAO secara penuh.
5. Membangun antarmuka GUI Agri-POS yang lebih interaktif.

---

## Dasar Teori

1. TableView JavaFX digunakan untuk menampilkan data dalam bentuk tabel yang terstruktur dan dinamis.
2. Lambda Expression menyederhanakan penulisan event handler pada JavaFX.
3. DAO (Data Access Object) memisahkan logika akses data dari logika aplikasi.
4. Service Layer menjadi penghubung antara controller dan DAO.
5. Prinsip SOLID (DIP) memastikan View tidak berinteraksi langsung dengan database.

---

## Langkah Praktikum

1. Persiapan Project Menyusun dan mengonfigurasi file pom.xml untuk kebutuhan JavaFX dan koneksi database PostgreSQL.

2. Pengembangan Backend Mengimplementasikan kelas ProductDAO, ProductDAOImpl, dan ProductService sebagai pengelola akses data.

3. Pembuatan Antarmuka (View) Merancang ProductTableView yang berisi tabel daftar produk serta tombol aksi.

4. Implementasi Controller Mengembangkan ProductController sebagai penghubung antara View dan Service, serta menangani aksi pengguna menggunakan lambda expression.

5. Integrasi Aplikasi Menggabungkan seluruh komponen aplikasi melalui kelas AppJavaFX.

6. Melakukan commit dengan pesan

## Kode Program

### Product.java
```java
package com.upb.agripos;

/**
 * Model class untuk Product
 */
public class Product {
    private String id;
    private String name;
    private int price;
    private int stock;

    public Product(String id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
```

### ProductTableFrame.java - Komponen Utama
- **JTable** dengan DefaultTableModel untuk menampilkan data
- **JMenuBar** dengan menu File, Edit, Help
- **Toolbar** dengan search field untuk filter data
- **Button panel** dengan Add, Edit, Delete, Refresh buttons
- **Dialog boxes** untuk Add dan Edit product
- **Search functionality** untuk filter by name/ID

---

## Hasil Eksekusi

### Screenshot Aplikasi

#### 1. JTable View (Initial State)
![Screenshot tabel produk](screenshots/01-table-view.png)

#### 2. Add Product Dialog
![Screenshot add dialog](screenshots/02-add-dialog.png)

#### 3. Edit Product Dialog
![Screenshot edit dialog](screenshots/03-edit-dialog.png)

#### 4. Menu Bar Navigation
![Screenshot menu bar](screenshots/04-menu-bar.png)

---

## Analisis

### Bagaimana Kode Berjalan

1. **Inisialisasi Application**
   - ProductTableFrame extends JFrame
   - Constructor menjalankan createMenuBar(), createToolbar(), createTablePanel(), createButtonPanel()
   - initSampleData() mengisi ArrayList dengan 5 product sample

2. **JTable Display**
   - DefaultTableModel mengelola struktur tabel (kolom dan baris)
   - loadTableData() mengiterasi ArrayList dan menambahkan setiap product ke tabel
   - JTable menampilkan data dalam format grid dengan kolom: ID, Name, Price, Stock

3. **Menu Bar Navigation**
   - JMenuBar menampilkan File, Edit, Help menu
   - Setiap JMenuItem memiliki ActionListener yang menjalankan fungsi tertentu
   - File → Exit: System.exit(0)
   - Edit → Add: showAddDialog()

4. **Add Product Dialog**
   - JDialog modal menampilkan form dengan 4 JTextField
   - User input di-validasi sebelum save
   - Product baru ditambahkan ke ArrayList
   - Tabel di-refresh untuk menampilkan product baru

5. **Edit Product Dialog**
   - Memerlukan product yang dipilih (getSelectedRow())
   - Dialog pre-fill dengan data product terpilih
   - ID field tidak editable
   - Update ArrayList dan refresh table

6. **Delete dengan Confirmation**
   - JOptionPane.showConfirmDialog() menampilkan dialog konfirmasi
   - User pilih YES untuk delete, NO untuk batal
   - Product dihapus dari ArrayList
   - Tabel di-refresh

### Perbedaan dengan Week 12 (GUI Dasar)

| Aspek | Week 12 | Week 13 |
|-------|---------|---------|
| **Display** | JTextArea | JTable (structured columns) |
| **Navigation** | Tidak ada menu | JMenuBar dengan menu structure |
| **Input** | Single form | Dialog boxes per action |
| **Data View** | Text format | Table format |
| **Functionality** | Add only | Add/Edit/Delete/Search |

### Kendala dan Solusi

| Kendala | Solusi |
|---------|--------|
| Table tidak update setelah action | Panggil loadTableData() setelah setiap operasi |
| Modal dialog tidak muncul | Pastikan dialog.setVisible(true) di akhir method |
| Search tidak bekerja | Gunakan toLowerCase() untuk case-insensitive |
| Validation gagal | Gunakan try-catch untuk NumberFormatException |
| ID duplikat tidak dicegah | Tambah check loop di showAddDialog() |

---

## Kesimpulan

Praktikum Week 13 telah berhasil mendemonstrasikan pembuatan aplikasi GUI tingkat lanjut menggunakan Java Swing dengan focus pada JTable, Menu Bar Navigation, Dialog Boxes, dan complete CRUD operations dalam in-memory ArrayList.

Aplikasi AgriPOS - Product Management menunjukkan evolusi dari GUI dasar menjadi aplikasi desktop yang lebih profesional dan user-friendly.

---