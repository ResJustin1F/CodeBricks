package com.codebricks.controllers;

import com.codebricks.services.AuthService;
import com.codebricks.services.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showError("Please enter your email and password.");
            return;
        }

        AuthService auth = new AuthService();
        boolean success = auth.login(email, password);

        if (success) {
            SessionManager.set(email);
            navigateTo("/views/home-dashboard-view.fxml");
        } else {
            showError("Invalid email or password.");
        }
    }

    @FXML
    private void handleRegisterLink() {
        navigateTo("/views/registration-view.fxml");
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