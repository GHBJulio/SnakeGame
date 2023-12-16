package SnakeGame;

import javafx.application.Application;
import javafx.stage.Stage;

public class SnakeGameApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public static StageManager stageManager;
    public static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        stageManager = new StageManager(primaryStage);
        stageManager.loadNewScene("/fxml/Menu.fxml");
    };

}