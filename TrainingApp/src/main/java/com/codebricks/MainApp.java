package com.codebricks;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Label label = new Label("You are on the dev branch. Test passed.");
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: #3B82F6; -fx-font-family: 'Courier New';");

        StackPane root = new StackPane(label);
        root.setStyle("-fx-background-color: #1E1E2E;");

        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("CodeBricks - DEV");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}