package com.codebricks.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashController {

    @FXML private Circle dot1;
    @FXML private Circle dot2;
    @FXML private Circle dot3;

    @FXML
    public void initialize() {
        animateDots();
        scheduleTransition();
    }

    // Cycles the 3 loading dots: one active (blue) at a time
    private void animateDots() {
        Timeline dotAnimation = new Timeline(
                new KeyFrame(Duration.millis(0), e -> setDots(0)),
                new KeyFrame(Duration.millis(400), e -> setDots(1)),
                new KeyFrame(Duration.millis(800), e -> setDots(2)),
                new KeyFrame(Duration.millis(1200)) // pause before loop
        );
        dotAnimation.setCycleCount(Timeline.INDEFINITE);
        dotAnimation.play();
    }

    // Sets which dot is "active" (blue) and the others inactive (grey)
    private void setDots(int activeDot) {
        dot1.getStyleClass().setAll(activeDot == 0 ? "loading-dot" : "loading-dot-inactive");
        dot2.getStyleClass().setAll(activeDot == 1 ? "loading-dot" : "loading-dot-inactive");
        dot3.getStyleClass().setAll(activeDot == 2 ? "loading-dot" : "loading-dot-inactive");
    }

    // After 3 seconds, fade out and navigate to Login
    private void scheduleTransition() {
        Timeline timer = new Timeline(
                new KeyFrame(Duration.seconds(3), e -> navigateToLogin())
        );
        timer.play();
    }

    private void navigateToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/login-view.fxml")
            );

            Scene currentScene = dot1.getScene();

            // Fade out current scene, then swap
            FadeTransition fade = new FadeTransition(Duration.millis(500), currentScene.getRoot());
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setOnFinished(e -> {
                try {
                    Scene loginScene = new Scene(loader.load(), 800, 600);
                    loginScene.getStylesheets().add(
                            getClass().getResource("/styles/styles.css").toExternalForm()
                    );
                    Stage stage = (Stage) currentScene.getWindow();
                    stage.setScene(loginScene);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            fade.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}