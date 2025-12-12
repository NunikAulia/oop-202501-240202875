package main.java.com.upb.agripos.util;

import main.java.com.upb.agripos.modul.Product;
import main.java.com.upb.agripos.modul.AlatPertanian;
import main.java.com.upb.agripos.modul.Benih;
import main.java.com.upb.agripos.modul.ObatHama;
import main.java.com.upb.agripos.modul.Pupuk;
import main.java.com.upb.agripos.util.CreditBy;

public class MainPolymorphism {
    public static void main(String[] args) {

        Product[] daftarProduk = {
            new Benih("BNH-001", "Benih Padi IR64", 25000, 100, "IR64"),
            new Pupuk("PPK-101", "Pupuk Urea", 350000, 40, "Urea"),
            new AlatPertanian("ALT-501", "Cangkul Baja", 90000, 15, "Baja"),
            new ObatHama("OBT-220", "Wastak", 25000, 4, "Basmi Wareng")
        };

        System.out.println("=== Info Produk Pertanian (Polymorphism) ===");
        for (Product p : daftarProduk) {
            System.out.println(p.getInfo()); // Polymorphism (Dynamic Binding)
        }

        CreditBy.print("240202875", "Nunik Aulia Primadani");
    }
}
