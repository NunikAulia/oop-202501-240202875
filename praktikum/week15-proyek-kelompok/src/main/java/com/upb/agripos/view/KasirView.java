package com.upb.agripos.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class KasirView {

    private final Stage stage;
    private final String username;

    public KasirView(Stage stage, String username) {
        this.stage = stage;
        this.username = username;
    }

    public void show() {
        Label label = new Label("Selamat datang, Kasir " + username);
        VBox root = new VBox(20, label);
        root.setStyle("-fx-padding: 20px;");

        stage.setTitle("Dashboard Kasir");
        stage.setScene(new Scene(root, 500, 400));
        stage.show();
    }
}
