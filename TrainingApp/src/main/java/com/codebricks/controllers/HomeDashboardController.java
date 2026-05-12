
package com.codebricks.controllers;

import com.codebricks.services.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.codebricks.services.DatabaseManager;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class HomeDashboardController {

    @FXML private BorderPane rootPane;
    @FXML private Label welcomeLabel;
    @FXML private Label quizCountLabel;

    @FXML
    public void initialize() {
        //USERNAME
        welcomeLabel.setText("Welcome, " + SessionManager.getEmail());

        //QUIZ COUNT
        MongoCollection<Document> results = DatabaseManager.getDatabase().getCollection("QuizResults");
        long count = results.countDocuments(Filters.eq("userId", SessionManager.getEmail()));
        quizCountLabel.setText(String.valueOf(count));
    }

    @FXML
    private void handleSelectDifficulty() {
        navigateTo("/views/difficulty-view.fxml");
    }

    @FXML
    private void handleViewDashboard() {
        navigateTo("/views/performance-dashboard-view.fxml");
    }

    @FXML
    private void handleLogout() {
        SessionManager.clear();
        navigateTo("/views/login-view.fxml");
    }

    public void setUsername(String username) {
        welcomeLabel.setText("Welcome, " + username);
    }

    public void setQuizCount(int count) {
        quizCountLabel.setText(String.valueOf(count));
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
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}