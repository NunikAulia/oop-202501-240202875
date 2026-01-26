# Laporan Praktikum Minggu 13 - GUI Lanjutan (Advanced GUI with JTable)

Topik: Implementasi GUI Lanjutan dengan JTable, Menu Bar, dan Dialog Boxes

## Identitas
- Nama  : [Nama Mahasiswa]
- NIM   : [NIM Mahasiswa]
- Kelas : [Kelas]
- Dosen : [Nama Dosen]

---

## Tujuan

Mahasiswa memahami dan dapat mengimplementasikan antarmuka grafis tingkat lanjut menggunakan Java Swing dengan:
1. **JTable** untuk menampilkan data dalam format tabel yang terstruktur
2. **JMenuBar** dan **JMenu** untuk navigasi aplikasi
3. **JDialog** untuk interaksi dialog (Add, Edit, Delete)
4. **Event Handling** dengan lambda expressions
5. Integrasi antara berbagai komponen Swing untuk aplikasi desktop yang profesional

---

## Dasar Teori

### 1. **JTable (Table Component)**
   - JTable adalah komponen Swing untuk menampilkan data dalam format baris dan kolom
   - Menggunakan `DefaultTableModel` untuk mengelola data tabel
   - Mendukung multi-selection modes dan custom rendering
   - Model-View architecture memisahkan data dari tampilan

### 2. **Menu Bar dan Menu**
   - **JMenuBar**: Container untuk menu yang ditampilkan di bagian atas jendela
   - **JMenu**: Submenu yang berisi item-item menu
   - **JMenuItem**: Item individual dalam menu yang dapat dipicu dengan action listener
   - Struktur hierarki: JFrame → JMenuBar → JMenu → JMenuItem

### 3. **Dialog Boxes (JDialog)**
   - JDialog digunakan untuk membuat window dialog modal dan non-modal
   - Modal dialog: menghentikan akses ke window parent sampai dialog ditutup
   - Berguna untuk input data, konfirmasi, dan pesan error/warning
   - Dapat di-customize dengan berbagai komponen

### 4. **Event Handling dengan Lambda Expression**
   - Lambda expression menyederhanakan anonymous inner class untuk listener
   - Syntax: `(parameters) -> {body}`
   - Membuat kode lebih ringkas dan readable
   - Digunakan untuk ActionListener, ChangeListener, dll

### 5. **Data Management dalam JTable**
   - **DefaultTableModel**: Model default untuk JTable dengan ArrayList sebagai backing store
   - **setRowCount(0)**: Menghapus semua baris dari tabel
   - **addRow(Object[])**: Menambah baris baru ke tabel
   - **getSelectedRow()**: Mendapatkan index baris yang dipilih

---

## Langkah Praktikum

### Langkah 1: Setup Struktur Project
```
week13-gui-lanjutan/
├── src/main/java/com/upb/agripos/
│   ├── Product.java (Model class)
│   └── ProductTableFrame.java (GUI dengan JTable)
├── laporan.md
└── screenshots/
    ├── 01-table-view.png
    ├── 02-add-dialog.png
    ├── 03-edit-dialog.png
    └── 04-menu-bar.png
```

### Langkah 2: Membuat Class Model (Product.java)
- Copy dari week 12 atau buat class Product baru
- Atribut: id (String), name (String), price (int), stock (int)
- Constructor dan getter/setter lengkap
- Method toString() untuk debug

### Langkah 3: Membuat GUI dengan JTable (ProductTableFrame.java)
- Extends JFrame
- Inisialisasi ArrayList<Product> dengan sample data
- Buat JTable dengan DefaultTableModel
- Implementasi menu bar (File, Edit, Help)
- Implementasi toolbar dengan search field
- Implementasi button panel (Add, Edit, Delete, Refresh)
- Implementasi dialog untuk Add dan Edit product
- Implementasi metode search products

### Langkah 4: Implementasi Event Handling
- **loadTableData()**: Load data dari ArrayList ke JTable
- **showAddDialog()**: Display dialog untuk add product baru
- **showEditDialog()**: Display dialog untuk edit product terpilih
- **deleteProduct()**: Delete product dengan konfirmasi
- **searchProducts()**: Filter products berdasarkan keyword

### Langkah 5: Testing
- Compile: `javac -d . com/upb/agripos/Product.java com/upb/agripos/ProductTableFrame.java`
- Run: `java com.upb.agripos.ProductTableFrame`
- Test add product
- Test edit product
- Test delete product dengan konfirmasi
- Test search functionality
- Test menu items (File → Exit, Edit → Add/Delete, Help → About)

### File yang Dibuat
1. `Product.java` - Model class
2. `ProductTableFrame.java` - Main GUI dengan JTable dan menu bar

### Commit Message
```
feat: add week 13 advanced GUI with JTable and dialogs
- Create ProductTableFrame with JTable for product list
- Implement JMenuBar with File, Edit, Help menus
- Add Add/Edit/Delete product dialogs with validation
- Implement search functionality and table refresh
- Use lambda expressions for event handling
```

---

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

## Quiz

### 1. Jelaskan perbedaan antara JTable dan JTextArea!
**Jawaban:**
- **JTextArea**: Menampilkan teks dalam format plain text
- **JTable**: Menampilkan data dalam struktur baris-kolom terstruktur
- JTable lebih mudah untuk searching, sorting, dan manipulasi data individual

### 2. Apa kegunaan DefaultTableModel dalam JTable?
**Jawaban:**
DefaultTableModel adalah model yang mengelola data tabel. Menyimpan data dalam struktur 2D (rows × columns), menyediakan method untuk add/remove rows, dan memisahkan data dari tampilan (Model-View pattern).

### 3. Bagaimana cara membuat dialog modal di Java Swing?
**Jawaban:**
```java
JDialog dialog = new JDialog(parentFrame, "Title", true); // true = modal
dialog.setSize(400, 300);
dialog.setLocationRelativeTo(parentFrame);
dialog.setVisible(true); // Blocking call
```

### 4. Bagaimana cara mendapatkan baris yang dipilih di JTable?
**Jawaban:**
```java
int selectedRow = productTable.getSelectedRow();
if (selectedRow < 0) {
    JOptionPane.showMessageDialog(frame, "Please select a row!");
    return;
}
Product selected = productList.get(selectedRow);
```

### 5. Jelaskan konsep lambda expression dalam event handling!
**Jawaban:**
Lambda expression menyederhanakan anonymous inner class. Syntax: `(parameters) -> {body}`. Membuat kode lebih singkat dan readable untuk listener seperti ActionListener.

### 6. Bagaimana cara melakukan refresh data di JTable?
**Jawaban:**
```java
tableModel.setRowCount(0); // Clear all rows
for (Product product : productList) {
    tableModel.addRow(new Object[]{...});
}
```
Panggil method ini setelah setiap operasi (add, edit, delete).

### 7. Bagaimana cara membuat search/filter functionality?
**Jawaban:**
Iterate ArrayList dengan keyword filter dan tampilkan hanya product yang cocok di table menggunakan case-insensitive comparison dengan toLowerCase().

### 8. Apa perbedaan dialog.setVisible(true) dan dialog.setVisible(false)?
**Jawaban:**
- setVisible(true): Menampilkan dialog, blocking call jika modal
- setVisible(false): Menyembunyikan dialog tanpa menutupnya
- Untuk menutup: gunakan dialog.dispose() untuk lepas resources

---

## Referensi
- Oracle Java Swing Tutorial: https://docs.oracle.com/javase/tutorial/uiswing/
- JTable Documentation: https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
- Lambda Expression: https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
- Event Handling: https://www.geeksforgeeks.org/java-swing-event-handling/
