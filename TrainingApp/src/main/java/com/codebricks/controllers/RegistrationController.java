package com.codebricks.controllers;

import com.codebricks.services.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleCreateAccount() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        // Validation checks in order
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("All fields are required.");
            return;
        }

        if (!email.contains("@")) {
            showError("Please enter a valid email address.");
            return;
        }

        if (password.length() < 6) {
            showError("Password must be at least 6 characters.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match.");
            return;
        }

        AuthService auth = new AuthService();
        boolean success = auth.register(email, password);

        if (success) {
            showError("Registration successful. Please log in.");
            errorLabel.setStyle("-fx-text-fill: #22C55E; -fx-font-size: 13px;");
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                javafx.application.Platform.runLater(() -> navigateTo("/views/login-view.fxml"));
            }).start();
        }else{
            showError("An account with that email address already exists.");
        }
    }

    @FXML
    private void handleLoginLink() {
        navigateTo("/views/login-view.fxml");
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle(
                message.isEmpty() ? "" : "-fx-text-fill: #EF4444; -fx-font-size: 13px;"
        );
    }

    private void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(fxmlPath)
            );
            Scene scene = new Scene(loader.load(), 800, 600);
            scene.getStylesheets().add(
                    getClass().getResource("/styles/styles.css").toExternalForm()
            );
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}