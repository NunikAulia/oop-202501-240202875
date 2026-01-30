package com.upb.agripos.controller;

import com.upb.agripos.service.AuthService;
import com.upb.agripos.view.LoginView;
import com.upb.agripos.view.KasirView;

import javafx.stage.Stage;

public class LoginController {

    private final Stage stage;
    private final AuthService authService = new AuthService();
    private Runnable onLoginFailure = () -> {};

    public LoginController(Stage stage) {
        this.stage = stage;
    }

    public void setOnLoginFailure(Runnable callback) {
        this.onLoginFailure = callback;
    }

    public String login(String username, String password, String role) {

        String result = authService.login(username, password, role);

        if ("SUCCESS".equals(result)) {
            if ("KASIR".equals(role)) {
                KasirView kasirView = new KasirView(stage, username);
                kasirView.show();
            } else if ("LOGIN".equals(role)) {
                LoginView loginView = new LoginView(stage, username);
                loginView.show();
            }
            return null;
        } else {
            return result;
        }
    }

    public Stage getStage() {
        return stage;
    }
}