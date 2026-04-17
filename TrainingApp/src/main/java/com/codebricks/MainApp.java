package com.codebricks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                MainApp.class.getResource("/views/home-dashboard-view.fxml")
                //TODO change back to splash-view.fxml when group is done this week
        );

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        //Load global stylesheet
        scene.getStylesheets().add(
                MainApp.class.getResource("/styles/styles.css").toExternalForm()
        );

        stage.setTitle("CodeBricks");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}