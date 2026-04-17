package com.codebricks.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class PerformanceDashboardController {

    @FXML private Label totalQuizzesValueLabel;
    @FXML private Label bestScoreValueLabel;
    @FXML private TableView<QuizSessionRow> quizHistoryTable;
    @FXML private TableColumn<QuizSessionRow, String> dateColumn;
    @FXML private TableColumn<QuizSessionRow, String> difficultyColumn;
    @FXML private TableColumn<QuizSessionRow, String> scoreColumn;

    private final ObservableList<QuizSessionRow> quizHistory = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        quizHistoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        difficultyColumn.setCellValueFactory(cellData -> cellData.getValue().difficultyProperty());
        scoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty());

        difficultyColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String difficulty, boolean empty) {
                super.updateItem(difficulty, empty);

                if (empty || difficulty == null || difficulty.isBlank()) {
                    setText(null);
                    setGraphic(null);
                    getStyleClass().removeAll(
                            "difficulty-pill-easy",
                            "difficulty-pill-medium",
                            "difficulty-pill-hard"
                    );
                    return;
                }

                setText(difficulty);
                getStyleClass().removeAll(
                        "difficulty-pill-easy",
                        "difficulty-pill-medium",
                        "difficulty-pill-hard"
                );

                switch (difficulty.trim().toLowerCase()) {
                    case "easy" -> getStyleClass().add("difficulty-pill-easy");
                    case "medium" -> getStyleClass().add("difficulty-pill-medium");
                    case "hard" -> getStyleClass().add("difficulty-pill-hard");
                    default -> { }
                }
            }
        });

        quizHistoryTable.setItems(quizHistory);
        showEmptyState();
    }

    public void showEmptyState() {
        totalQuizzesValueLabel.setText("");
        bestScoreValueLabel.setText("");
        quizHistory.clear();
    }

    public void setSummary(String totalQuizzesCompleted, String bestScore) {
        totalQuizzesValueLabel.setText(totalQuizzesCompleted == null ? "" : totalQuizzesCompleted);
        bestScoreValueLabel.setText(bestScore == null ? "" : bestScore);
    }

    public void setQuizHistory(ObservableList<QuizSessionRow> rows) {
        quizHistory.setAll(rows == null ? FXCollections.observableArrayList() : rows);
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login-view.fxml"));
            // TODO: fix asap: update back button to navigate to home dashboard instead of login-view
            Scene scene = new Scene(loader.load(), 800, 600);
            scene.getStylesheets().add(
                    getClass().getResource("/styles/styles.css").toExternalForm()
            );

            Stage stage = (Stage) quizHistoryTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class QuizSessionRow {
        private final StringProperty date = new SimpleStringProperty();
        private final StringProperty difficulty = new SimpleStringProperty();
        private final StringProperty score = new SimpleStringProperty();

        public QuizSessionRow(String date, String difficulty, String score) {
            this.date.set(date);
            this.difficulty.set(difficulty);
            this.score.set(score);
        }

        public StringProperty dateProperty() {
            return date;
        }

        public StringProperty difficultyProperty() {
            return difficulty;
        }

        public StringProperty scoreProperty() {
            return score;
        }
    }
}
