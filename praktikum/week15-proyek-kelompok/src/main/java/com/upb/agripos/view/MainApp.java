package com.upb.agripos.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Alternative main application class - can be used as entry point
 * Currently redirects to App.java as main entry point
 * 
 * NOTE: This class is kept for reference, use App.java for main entry point
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        root.setCenter(new Label("Agri-POS Application"));
        
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Agri-POS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}