package com.codebricks.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DifficultyController {

    @FXML private BorderPane rootPane;

    //Difficulty buttons
    @FXML
    private void handleEasy() {
        navigateToQuiz("easy");
    }

    @FXML
    private void handleMedium() {
        navigateToQuiz("medium");
    }

    @FXML
    private void handleHard() {
        navigateToQuiz("hard");
    }

    //Back button → return to Login
    @FXML
    private void handleBack() {
        navigateToFxml("/views/login-view.fxml");
        // TODO: change to home-dashboard-view.fxml (@matt)
    }

    //Navigation helpers
    private void navigateToQuiz(String difficulty) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/quiz-view.fxml")
            );
            Scene scene = new Scene(loader.load(), 800, 600);
            scene.getStylesheets().add(
                    getClass().getResource("/styles/styles.css").toExternalForm()
            );

            // Hand off the chosen difficulty to the QuizController
            QuizController quizController = loader.getController();
            quizController.initQuiz(difficulty);

            getStage().setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToFxml(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), 800, 600);
            scene.getStylesheets().add(
                    getClass().getResource("/styles/styles.css").toExternalForm()
            );
            getStage().setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Stage getStage() {
        return (Stage) rootPane.getScene().getWindow();
    }
}