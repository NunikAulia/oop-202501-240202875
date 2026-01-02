# Laporan Praktikum Minggu 6 
Topik: [Bab 6 – Desain Arsitektur Sistem dengan UML dan Prinsip SOLID]

## Identitas
- Nama  : [NUNIIK AULIA PRIMADANI]
- NIM   : [240202875]
- Kelas : [3IKRB]

---

## Tujuan
1. *Mahasiswa mampu mengidentifikasi kebutuhan sistem ke dalam diagram UML.*
2. *Mahasiswa mampu menggambar UML Class Diagram dengan relasi antar class yang tepat.*
3. *Mahasiswa mampu menjelaskan prinsip desain OOP (SOLID).*
4. *Mahasiswa mampu menerapkan minimal dua prinsip SOLID dalam kode program.*

---

## 1. Deskripsi Sistem Agri-POS
Agri-POS (Agricultural Point of Sale) adalah sistem penjualan yang digunakan pada toko pertanian untuk mengelola produk dan memproses transaksi penjualan. Sistem ini mendukung manajemen produk, proses checkout, serta pembayaran menggunakan metode tunai dan e-wallet. Pada pembayaran non-tunai, Agri-POS terintegrasi dengan Payment Gateway untuk validasi transaksi. Sistem dirancang menggunakan prinsip Object Oriented Programming (OOP) dan SOLID, sehingga bersifat modular, mudah dikembangkan, dan mudah dipelihara, dengan pemisahan peran antara Kasir dan Admin.

---

## Desain Arsitektur UML
Desain arsitektur sistem Agri-POS dimodelkan menggunakan Unified Modeling Language (UML) untuk menggambarkan struktur dan perilaku sistem secara jelas dan terstruktur.
### 1. Use Case Diagram

- Admin : login, kelola produk, dan melihat laporan
- Kasir :  login, membuat transaksi, checkout, dan mencetak struk
- Payment Gateway : terlibat khusus pada pembayaran e-wallet
- Checkout mencakup pemilihan metode pembayaran (tunai / e-wallet)

![USECASEDIAGRAM](/praktikum/week6-uml-solid/docs/USE%20CASE%20DIAGRAM.drawio.png)

### 2. ACTIVITY DIAGRAM
Activity Diagram menggambarkan alur lengkap proses checkout dengan tiga swimlane:
- *Kasir*: Melakukan input dan interaksi dengan sistem
- *Sistem*: Memproses logika bisnis dan validasi
- *Payment Gateway*: Menangani pembayaran e-wallet

Proses diawali ketika kasir melakukan checkout dan memilih metode pembayaran. Sistem kemudian menghitung total belanja. Jika metode pembayaran yang dipilih adalah tunai, sistem akan memvalidasi pembayaran dan menyimpan transaksi. Jika metode pembayaran yang dipilih adalah e-wallet, sistem akan mengirim permintaan pembayaran ke Payment Gateway untuk melakukan validasi saldo. Apabila saldo mencukupi, transaksi akan disimpan dan struk ditampilkan, sedangkan jika saldo tidak mencukupi, sistem akan menampilkan pesan pembayaran gagal. Activity Diagram ini membantu memahami alur proses bisnis serta kemungkinan kondisi sukses dan gagal dalam proses pembayaran.

![ACTIVITYDIAGRAM](/praktikum/week6-uml-solid/docs/ACTIVITY%20DIAGRAM.drawio%20(2).png)

### CLASS DIAGRAM
Class Diagram digunakan untuk memodelkan struktur kelas, atribut, metode, serta hubungan antar kelas dalam sistem. Diagram ini menunjukkan pemisahan tanggung jawab antar kelas, seperti Kasir, SistemPOS, PaymentService, dan PaymentGateway.

Pada bagian pembayaran, digunakan interface PaymentMethod yang diimplementasikan oleh kelas CashPayment dan EWalletPayment. Desain ini menerapkan prinsip SOLID, khususnya Single Responsibility Principle, di mana setiap kelas memiliki tanggung jawab yang jelas, serta Open/Closed Principle dan Dependency Inversion Principle, di mana sistem bergantung pada interface, bukan pada implementasi konkret. Dengan desain ini, sistem dapat dikembangkan dengan menambahkan metode pembayaran baru tanpa mengubah kode yang sudah ada.

![CLASSDIAGRAM](/praktikum/week6-uml-solid/docs/CLASS%20DIAGRAM.drawio%20(1).png)

### 4. SQUENCE DIAGRAM
Sequence Diagram menggambarkan urutan interaksi antar objek selama proses checkout dan pembayaran. Diagram ini melibatkan aktor Kasir, objek Sistem, PaymentService, dan Payment Gateway.

Urutan proses dimulai ketika kasir melakukan checkout, kemudian sistem menghitung total belanja dan memproses pembayaran sesuai metode yang dipilih. Untuk pembayaran tunai, sistem langsung memvalidasi pembayaran dan menampilkan struk. Untuk pembayaran e-wallet, sistem meneruskan permintaan ke Payment Gateway. Diagram ini juga menampilkan skenario alternatif menggunakan blok alt, yaitu kondisi saldo mencukupi dan saldo tidak mencukupi. Sequence Diagram memberikan gambaran yang jelas mengenai alur komunikasi dan pertukaran pesan antar komponen sistem.

![SQUENCEDIAGRAM](/praktikum/week6-uml-solid/docs/SQUENCE%20DIAGRAM.drawio%20(1).png)

---

## 2. Penerapan Prinsip SOLID

- SRP : PaymentService → logika pembayaran
PaymentGateway → komunikasi pihak ketiga

- OCP : Tambah metode baru (QRIS, BankTransfer) tanpa ubah class lama

- LSP : CashPayment & EWalletPayment saling menggantikan PaymentMethod

- ISP : Interface kecil & fokus (PaymentMethod)

- DIP : PaymentService bergantung pada interface, bukan implementasi

---

## 3. Traceability Matrix (FR → Desain)

| **Kode FR** | **Functional Requirement**                           | **Use Case Diagram**    | **Activity Diagram**      | **Sequence Diagram**           | **Class / Interface**                               |
| ----------- | ---------------------------------------------------- | ----------------------- | ------------------------- | ------------------------------ | --------------------------------------------------- |
| FR-01       | Login dan hak akses pengguna (Kasir & Admin)         | Login                   | –                         | –                              | `User`, `Kasir`, `Admin`                            |
| FR-02       | Manajemen produk (tambah, ubah, hapus, lihat produk) | Kelola Produk           | Activity Manajemen Produk | –                              | `Product`, `ProductService`, `ProductRepository`    |
| FR-03       | Membuat transaksi penjualan                          | Kelola Transaksi        | Checkout                  | Checkout & Pembayaran          | `Transaction`, `TransactionItem`, `SistemPOS`       |
| FR-04       | Menghitung total belanja                             | Checkout                | Hitung Total              | hitungTotal()                  | `SistemPOS`, `Transaction`                          |
| FR-05       | Pembayaran tunai                                     | Pilih Metode Pembayaran | Pembayaran Tunai          | alt Pembayaran Tunai           | `PaymentMethod`, `CashPayment`                      |
| FR-06       | Pembayaran e-wallet                                  | Pilih Metode Pembayaran | Pembayaran E-Wallet       | alt Pembayaran E-Wallet        | `PaymentMethod`, `EWalletPayment`, `PaymentGateway` |
| FR-07       | Validasi pembayaran                                  | Checkout                | Validasi Pembayaran       | paymentSuccess / paymentFailed | `PaymentService`, `PaymentGateway`                  |
| FR-08       | Pencetakan / penampilan struk                        | Cetak Struk             | Cetak Struk               | tampilkanStruk()               | `SistemPOS`                                         |
| FR-09       | Penanganan pembayaran gagal                          | Checkout                | Pembayaran Gagal          | alt Saldo Tidak Cukup          | `PaymentService`                                    |
| FR-10       | Ekstensibilitas metode pembayaran                    | –                       | –                         | –                              | `PaymentMethod` (interface)                         |


---

## QUIZ
1. [Jelaskan perbedaan aggregation dan composition serta berikan contoh penerapannya pada desain Anda.]  
   **Jawaban:** Aggregation adalah hubungan whole–part yang lemah, di mana objek bagian masih dapat berdiri sendiri.
Contoh: SistemPOS dengan PaymentGateway. Payment Gateway tetap ada meskipun sistem POS tidak digunakan.

Composition adalah hubungan whole–part yang kuat, di mana objek bagian tidak dapat hidup tanpa induknya.
Contoh: Transaction dengan TransactionItem, karena item transaksi hanya ada selama transaksi berlangsung. 

2. [Mengapa Dependency Inversion Principle (DIP) meningkatkan testability? Berikan contoh penerapannya.]  
   **Jawaban:** DIP meningkatkan testability karena sistem bergantung pada interface, bukan implementasi konkret.
Contoh: PaymentService bergantung pada PaymentMethod, sehingga saat pengujian dapat menggunakan mock payment tanpa melibatkan Payment Gateway asli.

3. [Bagaimana Prinsip Open/Closed Memastikan Sistem Mudah Dikembangkan]  
   **Jawaban:** Prinsip Open/Closed memastikan sistem mudah dikembangkan dengan cara memungkinkan penambahan fitur baru tanpa mengubah kode lama.
Contoh: Penambahan metode pembayaran baru cukup membuat class baru yang mengimplementasikan PaymentMethod, tanpa mengubah PaymentService.

---

## Kesimpulan
*Perancangan sistem Agri-POS menggunakan UML berhasil memodelkan kebutuhan fungsional sistem secara terstruktur melalui Activity, Sequence, dan Class Diagram. Penerapan prinsip SOLID membuat desain sistem bersifat modular, mudah dikembangkan, dan mudah dipelihara, khususnya pada modul pembayaran. Dengan desain ini, Agri-POS mampu mendukung proses checkout dan pembayaran secara efektif serta siap untuk pengembangan fitur di masa depan..*

---
