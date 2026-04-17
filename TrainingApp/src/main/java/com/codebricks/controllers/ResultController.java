package com.codebricks.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
//import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ResultController {

    @FXML private BorderPane rootPane;

    @FXML private Label subtitleLabel;
    @FXML private Label scoreLabel;
    @FXML private Label totalLabel;
    @FXML private Label correctLabel;
    @FXML private Label accuracyLabel;
    @FXML private Label resultMessageLabel;

    private String currentDiff;
    private int totalQuestions;

    //Called by quiz controller after quiz ends

    public void showResults(int score, int questions, String diff){
        this.currentDiff = diff;
        this.totalQuestions = questions;

        int acc = (int) Math.round((score / (double) questions) * 100);

        subtitleLabel.setText(
                "Here is how you did on your " + diff + " quiz. Keep practicing to improve!"
        );
        scoreLabel.setText(String.valueOf(score));
        totalLabel.setText("/ " + questions);
        correctLabel.setText(score + " of " + questions);
        accuracyLabel.setText(acc + "%");

        String message;
        String colour;

        if (acc == 100) {
            message = "🎉 Perfect score! Outstanding work!";
            colour  = "#16A34A";   // dark green
        } else if (acc >= 80) {
            message = "Great job! You passed this quiz.";
            colour  = "#16A34A";
        } else if (acc >= 60) {
            message = "Good effort! Review the missed topics and try again.";
            colour  = "#D97706";   // amber
        } else {
            message = "Keep going — every attempt makes you better!";
            colour  = "#DC2626";   // red
        }

        resultMessageLabel.setText(message);
        resultMessageLabel.setStyle(
                "-fx-text-fill: " + colour + "; -fx-font-size: 14px; -fx-font-weight: bold;"
        );

        // ── Colour the big score number to match ─────────────────────────────
        scoreLabel.setStyle(
                "-fx-text-fill: " + colour + ";"
        );
    }

    @FXML
    private void handlePlayAgain() {
        navigateToFxml("/views/quiz-view.fxml", true);
    }

    @FXML
    private void handleHome() {
        navigateToFxml("/views/login-view.fxml", false);
    }

    private void navigateToFxml(String fxmlPath, boolean isQuiz) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), 800, 600);
            scene.getStylesheets().add(
                    getClass().getResource("/styles/styles.css").toExternalForm()
            );

            if (isQuiz) {
                QuizController quizController = loader.getController();
                quizController.initQuiz(currentDiff);
            }

            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
