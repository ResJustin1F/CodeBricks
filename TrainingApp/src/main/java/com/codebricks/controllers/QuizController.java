package com.codebricks.controllers;

import com.codebricks.services.QuizService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.List;

public class QuizController {

    @FXML private BorderPane rootPane;

    // Navbar
    @FXML private Label difficultyBadgeLabel;

    // Progress
    @FXML private Label questionCountLabel;
    @FXML private Rectangle progressFill;

    // Question card
    @FXML private Label questionLabel;
    @FXML private Label topicLabel;

    // Answer buttons
    @FXML private Button answerA;
    @FXML private Button answerB;
    @FXML private Button answerC;
    @FXML private Button answerD;

    // Bottom bar
    @FXML private Label questionIndexLabel;
    @FXML private Button nextButton;

    // State
    private List<QuizService.Question> questions;
    private int currentIndex = 0;
    private int score = 0;
    private String selectedAnswer = null;
    private String currentDifficulty;

    private static final double PROGRESS_MAX_WIDTH = 720.0;

    /**
     * Called by DifficultyController after navigation
     */
    public void initQuiz(String difficulty) {
        this.currentDifficulty = difficulty;

        // Set difficulty badge
        difficultyBadgeLabel.setText(difficulty);
        difficultyBadgeLabel.getStyleClass().removeAll(
                "quiz-badge-easy", "quiz-badge-medium", "quiz-badge-hard"
        );
        switch (difficulty.toLowerCase()) {
            case "easy"   -> difficultyBadgeLabel.getStyleClass().add("quiz-badge-easy");
            case "medium" -> difficultyBadgeLabel.getStyleClass().add("quiz-badge-medium");
            case "hard"   -> difficultyBadgeLabel.getStyleClass().add("quiz-badge-hard");
        }

        // Load questions
        QuizService service = new QuizService();
        questions = service.getQuestions(difficulty);

        if (questions.isEmpty()) {
            System.err.println("QuizController: no questions loaded for difficulty: " + difficulty);
            return;
        }

        currentIndex = 0;
        score = 0;
        loadQuestion();
    }

    private void loadQuestion() {
        QuizService.Question q = questions.get(currentIndex);

        // Update counter labels
        questionCountLabel.setText("Question " + (currentIndex + 1) + " of " + questions.size());
        questionIndexLabel.setText((currentIndex + 1) + " / " + questions.size());

        // Update progress bar width
        double progress = (double)(currentIndex + 1) / questions.size();
        progressFill.setWidth(PROGRESS_MAX_WIDTH * progress);

        // Update question card
        questionLabel.setText(q.questionText);
        topicLabel.setText(q.topic);

        // Set answer button text
        List<String> opts = q.options;
        answerA.setText(opts.size() > 0 ? opts.get(0) : "");
        answerB.setText(opts.size() > 1 ? opts.get(1) : "");
        answerC.setText(opts.size() > 2 ? opts.get(2) : "");
        answerD.setText(opts.size() > 3 ? opts.get(3) : "");

        // Reset selection state
        selectedAnswer = null;
        resetAnswerStyles();

        // Update next button text
        nextButton.setText(currentIndex == questions.size() - 1 ? "Finish" : "Next →");
    }

    @FXML private void handleAnswerA() { selectAnswer(answerA, questions.get(currentIndex).options.get(0)); }
    @FXML private void handleAnswerB() { selectAnswer(answerB, questions.get(currentIndex).options.get(1)); }
    @FXML private void handleAnswerC() { selectAnswer(answerC, questions.get(currentIndex).options.get(2)); }
    @FXML private void handleAnswerD() { selectAnswer(answerD, questions.get(currentIndex).options.get(3)); }

    private void selectAnswer(Button selected, String answer) {
        selectedAnswer = answer;
        resetAnswerStyles();
        selected.getStyleClass().add("quiz-answer-selected");
    }

    private void resetAnswerStyles() {
        for (Button btn : List.of(answerA, answerB, answerC, answerD)) {
            btn.getStyleClass().removeAll("quiz-answer-selected");
        }
    }

    @FXML
    private void handleNext() {
        if (selectedAnswer == null) return; // force selection before advancing

        // Check if correct
        if (selectedAnswer.equals(questions.get(currentIndex).correctAnswer)) {
            score++;
        }

        if (currentIndex < questions.size() - 1) {
            currentIndex++;
            loadQuestion();
        } else {
            navigateToResults();
        }
    }

    @FXML
    private void handleBack() {
        navigateTo("/views/difficulty-view.fxml");
    }

    //Bottom bar back button -> goes back one question
    @FXML
    private void handlePreviousQuestion() {
        if (currentIndex > 0) {
            currentIndex--;
            selectedAnswer = null;
            loadQuestion();
        }
    }

    private void navigateToResults() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/result-view.fxml")
            );
            Scene scene = new Scene(loader.load(), 800, 600);
            scene.getStylesheets().add(
                    getClass().getResource("/styles/styles.css").toExternalForm()
            );
            ResultController resultController = loader.getController();
            resultController.showResults(score, questions.size(), currentDifficulty);

            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
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