# Laporan Praktikum Minggu 12 - GUI Dasar (Basic GUI)
Topik: Implementasi Graphical User Interface (GUI) Dasar menggunakan Java Swing

## Identitas
- Nama  : [Nama Mahasiswa]
- NIM   : [NIM Mahasiswa]
- Kelas : [Kelas]
- Dosen : [Nama Dosen]

---

## Tujuan
Mahasiswa memahami konsep Graphical User Interface (GUI) dan dapat membuat aplikasi desktop sederhana menggunakan Java Swing dengan komponen-komponen dasar seperti JFrame, JPanel, JLabel, JTextField, JButton, dan JTextArea.

---

## Dasar Teori

### 1. **Graphical User Interface (GUI)**
   - GUI adalah antarmuka pengguna berbasis grafis yang memungkinkan pengguna berinteraksi dengan aplikasi melalui elemen visual seperti tombol, teks, menu, dan lainnya.
   - Berbeda dengan CLI (Command Line Interface), GUI lebih user-friendly dan intuitif.

### 2. **Java Swing**
   - Swing adalah library Java untuk membuat GUI yang bekerja di atas AWT (Abstract Window Toolkit).
   - Swing menyediakan komponen UI yang ringan dan fleksibel untuk pengembangan aplikasi desktop.

### 3. **Komponen Utama Swing**
   - **JFrame**: Container utama untuk window aplikasi
   - **JPanel**: Panel untuk mengorganisir komponen
   - **JLabel**: Label untuk menampilkan teks
   - **JTextField**: Field untuk input teks
   - **JButton**: Tombol yang dapat diklik
   - **JTextArea**: Area untuk menampilkan/edit teks panjang

### 4. **Layout Manager**
   - **FlowLayout**: Mengatur komponen secara berurutan dari kiri ke kanan
   - **GridLayout**: Mengatur komponen dalam bentuk grid
   - **BorderLayout**: Mengatur komponen di 5 lokasi (NORTH, SOUTH, EAST, WEST, CENTER)
   - **BoxLayout**: Mengatur komponen secara vertikal atau horizontal

### 5. **Event Handling**
   - Listener digunakan untuk menangani aksi pengguna seperti klik tombol
   - ActionListener adalah interface untuk menangani aksi dari tombol

---

## Langkah Praktikum

### Langkah 1: Setup Struktur Project
```
week12-gui-dasar/
├── src/main/java/com/upb/agripos/
│   ├── Product.java (Model class)
│   └── ProductFrame.java (GUI Form)
└── laporan.md
```

### Langkah 2: Membuat Class Model (Product.java)
- Membuat class Product dengan atribut: id, name, price, stock
- Menambahkan getter dan setter untuk setiap atribut
- Override method toString() untuk menampilkan data

### Langkah 3: Membuat GUI Form (ProductFrame.java)
- Membuat class ProductFrame yang extends JFrame
- Inisialisasi komponen GUI (JLabel, JTextField, JButton, JTextArea)
- Mengatur layout menggunakan BoxLayout dan GridLayout
- Menambahkan action listeners untuk tombol-tombol

### Langkah 4: Testing
- Compile dan jalankan aplikasi
- Input data produk melalui form
- Verifikasi output dan validasi input

### File yang Dibuat
1. `Product.java` - Model class
2. `ProductFrame.java` - GUI Form dengan main method

### Commit Message
```
feat: add week 12 basic GUI implementation with Product form
- Create Product model class with getters/setters
- Implement ProductFrame GUI with Swing components
- Add event handling for Save, Reset, Cancel buttons
- Include input validation and output display
```

---

## Kode Program

### Product.java (Model Class)
```java
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

### ProductFrame.java (GUI Form)
Komponen utama:
- **Title Panel**: Menampilkan judul form
- **Input Panel**: Berisi 4 label dan 4 text field untuk input data produk
- **Button Panel**: Tombol Simpan, Reset, dan Batal
- **Output Panel**: Text area untuk menampilkan hasil input

Method penting:
- `saveProduct()`: Validasi input dan membuat object Product baru
- `resetForm()`: Membersihkan semua input field
- Action listeners: Menangani event dari tombol

---

## Hasil Eksekusi

### Screenshot Aplikasi

#### 1. Form Kosong (Initial State)
![Screenshot awal](screenshots/01-form-kosong.png)

#### 2. Input Data Produk
![Screenshot input](screenshots/02-input-data.png)

#### 3. Hasil Penyimpanan
![Screenshot hasil](screenshots/03-hasil-simpan.png)

#### 4. Pesan Validasi
![Screenshot validasi](screenshots/04-validasi-error.png)

---

## Analisis

### Bagaimana Kode Berjalan
1. **Inisialisasi JFrame**: `ProductFrame` extends `JFrame` dan mengatur properti window
2. **Pembuatan Panel**: Menggunakan `BoxLayout` untuk menyusun panel vertikal
3. **Penambahan Komponen**: Setiap panel berisi komponen GUI yang diatur dengan layout manager
4. **Event Handling**: 
   - Klik tombol "Simpan" → validasi input → buat object Product → tampilkan di TextArea
   - Klik tombol "Reset" → bersihkan semua field
   - Klik tombol "Batal" → keluar aplikasi

### Perbedaan dengan Minggu Sebelumnya (Week 11 - DAO Database)
- **Week 11**: Fokus pada persistensi data ke database menggunakan DAO pattern
- **Week 12**: Fokus pada antarmuka user melalui GUI Swing, input/output interaktif
- Week 12 dapat dikombinasikan dengan Week 11 untuk aplikasi yang lebih lengkap

### Kendala dan Solusi
| Kendala | Solusi |
|---------|--------|
| Komponen tidak teratir dengan baik | Gunakan layout manager yang tepat (GridLayout, BoxLayout) |
| Event tidak terpicu | Pastikan ActionListener terdaftar dengan benar di button |
| Validasi input kurang | Tambahkan try-catch untuk NumberFormatException dan pengecekan field kosong |
| GUI terlihat sempit/lebar | Atur size window dan gunakan BoxLayout dengan strut untuk spacing |

---

## Kesimpulan

Dengan menggunakan Java Swing, kami dapat membuat aplikasi desktop yang user-friendly dengan antarmuka grafis yang intuitif. Komponen-komponen Swing seperti JFrame, JPanel, JTextField, dan JButton dapat dikombinasikan untuk membuat form input yang fungsional. Event handling memungkinkan aplikasi untuk merespons aksi pengguna secara real-time. Integrasi antara GUI (Week 12) dan database (Week 11) dapat menghasilkan aplikasi desktop yang komprehensif.

---

## Quiz

### 1. Jelaskan perbedaan antara JPanel dan JFrame!
**Jawaban:**  
- **JFrame**: Container utama (window) yang dapat dijalankan sendiri, memiliki title bar, tombol minimize/close/maximize
- **JPanel**: Komponen yang berfungsi sebagai container untuk menyimpan komponen lain, tidak dapat berdiri sendiri tanpa JFrame

### 2. Apa kegunaan Layout Manager dalam GUI Java?
**Jawaban:**  
Layout Manager mengatur posisi dan ukuran komponen secara otomatis sesuai dengan preferensi layout yang dipilih. Contoh: GridLayout mengatur komponen dalam grid, BoxLayout mengatur secara linear (vertikal/horizontal).

### 3. Bagaimana cara menangani event klik tombol di Swing?
**Jawaban:**  
Gunakan interface `ActionListener` dan implementasikan method `actionPerformed(ActionEvent e)`. Daftarkan listener ke tombol menggunakan method `addActionListener()`.

### 4. Jelaskan konsep validasi input dalam form!
**Jawaban:**  
Validasi input adalah pengecekan data sebelum diproses untuk memastikan data valid:
- Pengecekan field kosong dengan `isEmpty()`
- Pengecekan tipe data dengan `try-catch` untuk `NumberFormatException`
- Tampilkan pesan error menggunakan `JOptionPane.showMessageDialog()` jika validasi gagal

### 5. Apa yang akan terjadi jika tidak menggunakan `SwingUtilities.invokeLater()`?
**Jawaban:**  
Tanpa `SwingUtilities.invokeLater()`, GUI komponen mungkin tidak dirender dengan sempurna karena Thread Safety. `invokeLater()` memastikan semua operasi GUI berjalan di Event Dispatch Thread yang aman.

---

## Referensi
- Oracle Java Swing Tutorial: https://docs.oracle.com/javase/tutorial/uiswing/
- Java AWT dan Swing: https://www.geeksforgeeks.org/java-swing/
- Event Handling dalam Swing: https://www.tutorialspoint.com/swing/swing_event_handling.htm
