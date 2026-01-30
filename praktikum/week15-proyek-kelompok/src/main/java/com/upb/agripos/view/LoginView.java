package com.upb.agripos.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {

    private final Stage stage;
    private final String username;

    public LoginView(Stage stage, String username) {
        this.stage = stage;
        this.username = username;
    }

    public void show() {
        Label label = new Label("Selamat datang, Login " + username);
        VBox root = new VBox(20, label);
        root.setStyle("-fx-padding: 20px;");

        stage.setTitle("Dashboard Login");
        stage.setScene(new Scene(root, 500, 400));
        stage.show();
    }
}
