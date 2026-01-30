package com.upb.agripos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main entry point for Agri-POS Application
 * Initializes JavaFX window and displays login screen
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Set up main window
            primaryStage.setTitle("Agri-POS - Agricultural Point of Sale System");
            primaryStage.setWidth(600);
            primaryStage.setHeight(500);
            
            // Create simple login screen
            VBox root = new VBox(20);
            root.setStyle("-fx-padding: 40; -fx-alignment: center;");
            
            Label titleLabel = new Label("Agri-POS Login");
            titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
            
            Label userLabel = new Label("Username:");
            TextField userField = new TextField();
            userField.setPromptText("Enter username");
            userField.setMaxWidth(300);
            
            Label passLabel = new Label("Password:");
            PasswordField passField = new PasswordField();
            passField.setPromptText("Enter password");
            passField.setMaxWidth(300);
            
            Button loginBtn = new Button("Login");
            loginBtn.setStyle("-fx-padding: 10; -fx-font-size: 14;");
            loginBtn.setOnAction(e -> {
                String username = userField.getText();
                String password = passField.getText();
                if (username.isEmpty() || password.isEmpty()) {
                    System.out.println("Please enter username and password");
                } else {
                    System.out.println("Login attempt: " + username);
                }
            });
            
            root.getChildren().addAll(
                titleLabel,
                userLabel, userField,
                passLabel, passField,
                loginBtn
            );
            
            Scene scene = new Scene(root, 600, 500);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}