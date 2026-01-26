# Dokumentasi Week 12 - GUI Dasar

## Status: COMPLETED ✅

---

## File yang Telah Dibuat

### 1. **Product.java** - Model Class
   - Lokasi: `src/main/java/com/upb/agripos/Product.java`
   - Fitur:
     - Atribut: id, name, price, stock
     - Getter & Setter lengkap
     - Method toString() untuk debug

### 2. **ProductFrame.java** - GUI Form
   - Lokasi: `src/main/java/com/upb/agripos/ProductFrame.java`
   - Fitur:
     - JFrame dengan title "AgriPOS - Product Input Form"
     - Input Panel dengan GridLayout untuk 4 field (ID, Name, Price, Stock)
     - Button Panel dengan 3 tombol (Simpan, Reset, Batal)
     - Output Panel dengan JTextArea untuk menampilkan hasil
     - Event Handling untuk semua tombol
     - Validasi Input (pengecekan field kosong dan tipe data)
     - Dialog box untuk feedback (success/error)

### 3. **laporan_lengkap.md** - Laporan Lengkap
   - Lokasi: `laporan_lengkap.md`
   - Isi:
     - Tujuan praktikum
     - Dasar teori GUI dan Swing
     - Langkah-langkah praktikum
     - Kode program (Product.java dan ProductFrame.java)
     - Analisis dan kendala
     - Quiz dengan 5 pertanyaan + jawaban
     - Referensi

---

## Features Implementasi

✅ **GUI Components**
- JFrame (Main window)
- JPanel (Layout organization)
- JLabel (Text labels)
- JTextField (Input fields)
- JButton (Action buttons)
- JTextArea (Output display)
- JScrollPane (Scrollable area)

✅ **Layout Managers**
- BoxLayout (Vertical arrangement)
- GridLayout (4x2 grid for input)
- FlowLayout (Button arrangement)
- BorderLayout (Panel arrangement)

✅ **Event Handling**
- ActionListener for buttons
- Save: Validasi + create Product object + display
- Reset: Clear all fields
- Cancel: Exit application

✅ **Input Validation**
- Check empty fields
- NumberFormatException handling
- JOptionPane dialogs

---

## Cara Menjalankan Aplikasi

```bash
# Navigasi ke folder project
cd d:\oop-202501-240202875\praktikum\week12-gui-dasar\src\main\java

# Compile
javac -d . com/upb/agripos/Product.java com/upb/agripos/ProductFrame.java

# Run
java com.upb.agripos.ProductFrame
```

---

## Testing Instructions

1. **Jalankan aplikasi** - form akan muncul
2. **Test Input Valid:**
   - ID: BNH-001
   - Nama: Benih Padi Premium
   - Harga: 25000
   - Stok: 100
   - Klik "Simpan" → Lihat output di text area
   
3. **Test Validasi Error:**
   - Kosongkan ID atau Nama
   - Klik "Simpan" → Dialog warning
   - Masukkan text di Harga/Stok
   - Klik "Simpan" → Dialog error NumberFormat

4. **Test Reset:**
   - Klik "Reset" → Semua field kosong

5. **Test Cancel:**
   - Klik "Batal" → Aplikasi tutup

---

## Kode Ringkas - ProductFrame

```java
// Main Components
private JLabel, JTextField untuk ID, Name, Price, Stock
private JButton buttonSave, buttonReset, buttonCancel
private JTextArea textAreaOutput

// Constructor Setup
- Inisialisasi JFrame properties
- Create 4 panels (Title, Input, Button, Output)
- Add components ke panels
- Register action listeners

// Event Handlers
- saveProduct(): Validasi + create Product + display
- resetForm(): Clear semua input
- buttonCancel: System.exit(0)
```

---

## Kesimpulan

Week 12 telah mengenalkan konsep **GUI Dasar** menggunakan Java Swing dengan membuat aplikasi form input produk yang user-friendly. Aplikasi ini mendemonstrasikan:

1. **Component Hierarchy**: JFrame → JPanel → Components
2. **Layout Management**: Kombinasi berbagai layout manager
3. **Event-Driven Programming**: Listener dan handling
4. **Input Validation**: Pengecekan data sebelum processing
5. **User Feedback**: Dialog dan text area untuk feedback

Materi ini siap untuk dilanjutkan ke **Week 13 - GUI Lanjutan** dengan menu bars, dialogs, dan komponen lebih kompleks.

---

## Next Steps (Week 13)

- Menu bars dan toolbars
- JDialog dan modal dialogs
- Table components untuk menampilkan data
- File chooser untuk open/save
- Kombinasi GUI dengan database (DAO pattern)

