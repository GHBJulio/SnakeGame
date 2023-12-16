package SnakeGame;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class for the Snake Game application.
 * Extends JavaFX Application for JavaFX lifecycle management.
 * Initializes the StageManager and loads the initial scene (Menu.fxml) upon application launch.
 * Contains the main method for launching the JavaFX application.
 *
 * @author Guilherme Julio
 */
public class SnakeGameApp extends Application {

    /**
     * Default constructor for the SnakeGameApp class.
     */
    public SnakeGameApp() {}
    /**
     * The entry point for the Snake Game application.
     * Launches the JavaFX application.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The StageManager instance for managing scene transitions.
     * It is static to allow access from various controllers and scenes.
     */
    public static StageManager stageManager;

    /**
     * The primary stage for the JavaFX application.
     * It is static to allow access from various controllers and scenes.
     */
    /**
     * The start method of the JavaFX application.
     * Initializes the StageManager and loads the initial scene (Menu.fxml).
     *
     * @param primaryStage The primary stage for the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {
        // Initialize the StageManager with the primary stage
        stageManager = new StageManager(primaryStage);

        // Load the initial scene (Menu.fxml)
        stageManager.loadNewScene("/fxml/Menu.fxml");
    }
}
