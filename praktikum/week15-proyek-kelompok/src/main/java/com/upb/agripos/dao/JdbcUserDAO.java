package com.upb.agripos.dao;

import com.upb.agripos.model.User;
import java.sql.*;

public class JdbcUserDAO implements UserDAO {

    @Override
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new RuntimeException("Username yang anda masukkan salah");
            }

            if (!rs.getString("password").equals(password)) {
                throw new RuntimeException("Password yang anda masukkan salah");
            }

            return new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
            );

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}