package com.upb.agripos.dao;

import com.upb.agripos.model.User;

public interface UserDAO {
    User login(String username, String password);
}