package com.upb.agripos.service;

public class AuthService {

    // Login yang mengembalikan pesan error detail
    public String login(String username, String password, String role) {
        
        if ("KASIR".equals(role)) {
            if (!username.equals("kasir")) {
                return "❌ Username salah";
            }
            if (!password.equals("kasir123")) {
                return "❌ Password salah";
            }
            return "SUCCESS";
        }

        if ("ADMIN".equals(role)) {
            if (!username.equals("admin")) {
                return "❌ Username salah";
            }
            if (!password.equals("admin123")) {
                return "❌ Password salah";
            }
            return "SUCCESS";
        }

        return "❌ Login gagal";
    }
}