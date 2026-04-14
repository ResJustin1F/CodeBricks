package com.codebricks.controllers;

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

        // TODO: Replace with AuthService.register(email, password) when MongoDB is ready
        showError("");
        System.out.println("Registration attempted: " + email);
        showError("Registration backend not connected yet.");
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