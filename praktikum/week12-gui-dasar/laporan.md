# Laporan Praktikum Minggu 12 - "GUI Dasar JavaFX (Event-Driven Programming)"

Topik: "GUI Dasar JavaFX (Event-Driven Programming)"

## Identitas
- Nama  : [Nunik Aulia Primadani]
- NIM   : [240202875]
- Kelas : [3IKRB]

---

## Tujuan

Tujuan dari praktikum ini adalah agar mahasiswa mampu memahami konsep event-driven programming serta membangun antarmuka grafis sederhana menggunakan JavaFX. Selain itu, mahasiswa diharapkan dapat mengintegrasikan GUI dengan backend aplikasi menggunakan konsep MVC, Service, dan DAO tanpa menuliskan ulang logika CRUD pada layer tampilan.

---

## Dasar Teori

1. JavaFX JavaFX adalah platform untuk membuat aplikasi desktop dengan GUI modern. JavaFX menyediakan komponen UI yang kaya seperti Button, TextField, TableView, dan mendukung styling dengan CSS. Komponen Utama:

Stage: Window utama aplikasi Scene: Container untuk elemen UI Node: Elemen UI individual (Button, Label, TextField) Layout Panes: Container untuk mengatur tata letak (VBox, HBox, GridPane)

2. Event-Driven Programming Event-Driven Programming adalah paradigma pemrograman di mana alur program ditentukan oleh event (kejadian) seperti klik mouse, input keyboard, atau aksi pengguna lainnya. Program tidak berjalan secara linear, melainkan menunggu event terjadi dan merespons dengan menjalankan event handler yang telah didefinisikan.
Karakteristik: Alur program bersifat reaktif, bukan sequential Menggunakan callback atau handler untuk merespons event Cocok untuk aplikasi yang membutuhkan interaksi user tinggi

3. Pola desain MVC (Model-View-Controller) digunakan untuk memisahkan logika bisnis, tampilan, dan pengendali aplikasi.
4. Data Access Object (DAO) berfungsi untuk memisahkan akses database dari logika bisnis aplikasi.
5. Service layer berperan sebagai penghubung antara controller dan DAO sesuai prinsip SOLID (Dependency Inversion Principle).

---

## Langkah Praktikum

1. Menyiapkan project Maven dengan dependency JavaFX dan PostgreSQL.
2. Menyesuaikan versi Java compiler ke Java 17 agar kompatibel dengan runtime.
3. Membuat class Product sebagai model data produk.
4. Mengimplementasikan ProductDAO untuk operasi database.
5. Membuat ProductService sebagai layer bisnis.
6. Membuat GUI JavaFX berupa form input produk dan area tampilan data.
7. Menambahkan event handler pada tombol “Tambah Produk”.
8. Menghubungkan event GUI dengan ProductController dan ProductService.
9. Menjalankan aplikasi menggunakan perintah mvn javafx:run.
10. Melakukan commit

---

## Kode Program

### ProductController.java

```java
package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;

public class ProductController {
    private final ProductService productService;

    /**
     * Constructor dengan dependency injection
     * @param productService Service untuk Product
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Menambah produk baru
     * Dipanggil dari View ketika tombol "Tambah" diklik
     * 
     * @param code Kode produk
     * @param name Nama produk
     * @param price Harga produk
     * @param stock Stok produk
     * @return true jika berhasil, false jika gagal
     */
    public boolean addProduct(String code, String name, String price, String stock) {
        try {
            // Validasi tidak boleh kosong
            if (code == null || code.trim().isEmpty() ||
                name == null || name.trim().isEmpty() ||
                price == null || price.trim().isEmpty() ||
                stock == null || stock.trim().isEmpty()) {
                return false;
            }

            // Parse numeric fields
            double priceValue = Double.parseDouble(price);
            int stockValue = Integer.parseInt(stock);

            // Buat object Product
            Product product = new Product(code, name, priceValue, stockValue);

            // Panggil service untuk insert
            productService.insert(product);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mengambil semua data produk
     * Dipanggil saat aplikasi dimulai atau refresh
     * 
     * @return Array berisi semua produk
     */
    public Product[] getAllProducts() {
        try {
            return productService.findAll().toArray(new Product[0]);
        } catch (Exception e) {
            System.err.println("Error fetching products: " + e.getMessage());
            return new Product[0];
        }
    }

    /**
     * Mencari produk berdasarkan kode
     * @param code Kode produk
     * @return Product jika ditemukan, null jika tidak
     */
    public Product findByCode(String code) {
        try {
            return productService.findByCode(code);
        } catch (Exception e) {
            System.err.println("Error finding product: " + e.getMessage());
            return null;
        }
    }

    /**
     * Update data produk
     * @param code Kode produk
     * @param name Nama produk baru
     * @param price Harga baru
     * @param stock Stok baru
     * @return true jika berhasil, false jika gagal
     */
    public boolean updateProduct(String code, String name, String price, String stock) {
        try {
            if (code == null || code.trim().isEmpty() ||
                name == null || name.trim().isEmpty() ||
                price == null || price.trim().isEmpty() ||
                stock == null || stock.trim().isEmpty()) {
                return false;
            }

            double priceValue = Double.parseDouble(price);
            int stockValue = Integer.parseInt(stock);

            Product product = new Product(code, name, priceValue, stockValue);
            productService.update(product);
            return true;
        } catch (Exception e) {
            System.err.println("Error updating product: " + e.getMessage());
            return false;
        }
    }

    /**
     * Menghapus produk
     * @param code Kode produk yang akan dihapus
     * @return true jika berhasil, false jika gagal
     */
    public boolean deleteProduct(String code) {
        try {
            if (code == null || code.trim().isEmpty()) {
                return false;
            }
            productService.delete(code);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting product: " + e.getMessage());
            return false;
        }
    }
}

```

### ProductDAO.java

```java
package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import java.util.List;

public interface ProductDAO {
    
    void insert(Product product) throws Exception;
    Product findByCode(String code) throws Exception;
    List<Product> findAll() throws Exception;
    void update(Product product) throws Exception;
    void delete(String code) throws Exception;
}

```

### ProductDAOImpl.java

```java
package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementasi ProductDAO dengan menggunakan JDBC
 * Menangani semua operasi database untuk tabel products
 */
public class ProductDAOImpl implements ProductDAO {
    private final Connection connection;

    public ProductDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Product p) throws Exception {
        String sql = "INSERT INTO products(code, name, price, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getCode());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.executeUpdate();
        }
    }

    @Override
    public Product findByCode(String code) throws Exception {
        String sql = "SELECT * FROM products WHERE code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Product> findAll() throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Product(
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                ));
            }
        }
        return list;
    }

    @Override
    public void update(Product p) throws Exception {
        String sql = "UPDATE products SET name=?, price=?, stock=? WHERE code=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getCode());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String code) throws Exception {
        String sql = "DELETE FROM products WHERE code=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.executeUpdate();
        }
    }
}
```
### Product.java
```java
package com.upb.agripos.model;

/**
 * Model class untuk Product (Produk)
 * Digunakan untuk merepresentasikan data produk dalam sistem Agri-POS
 */
public class Product {
    private String code;
    private String name;
    private double price;
    private int stock;

    /**
     * Constructor dengan semua parameter
     */
    public Product(String code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getter methods
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (Rp %.0f) Stok: %d", code, name, price, stock);
    }
}
```
### ProductService.java
```java
package com.upb.agripos.model;

/**
 * Model class untuk Product (Produk)
 * Digunakan untuk merepresentasikan data produk dalam sistem Agri-POS
 */
public class Product {
    private String code;
    private String name;
    private double price;
    private int stock;

    /**
     * Constructor dengan semua parameter
     */
    public Product(String code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getter methods
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (Rp %.0f) Stok: %d", code, name, price, stock);
    }
}
```

---

## Hasil Eksekusi

#### 1.Input Data Produk
![Screenshot awal](screenshots/HasilInputProduk.png)

#### 2.Hasil Suksess
![Screenshot input](screenshots/HasilSuksess.png)

---

## Analisis

Pada praktikum ini, aplikasi berjalan menggunakan konsep event-driven programming, di mana aksi pengguna pada tombol akan memicu proses penambahan data produk. Dibandingkan praktikum sebelumnya yang berbasis console, praktikum ini menampilkan output secara visual melalui GUI. Kendala yang dihadapi adalah ketidaksesuaian versi Java compiler dengan runtime, yang menyebabkan error UnsupportedClassVersionError. Masalah tersebut diatasi dengan menyamakan versi compiler ke Java 17 pada file pom.xml.

---

## Kesimpulan

Berdasarkan praktikum ini, dapat disimpulkan bahwa penggunaan JavaFX memungkinkan pembuatan antarmuka grafis yang interaktif dan mudah digunakan. Dengan menerapkan konsep MVC, Service, dan DAO, struktur aplikasi menjadi lebih terorganisir serta sesuai dengan prinsip desain perangkat lunak yang baik.

---
