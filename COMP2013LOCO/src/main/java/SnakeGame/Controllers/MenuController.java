package SnakeGame.Controllers;

import SnakeGame.Models.ImageUtil;
import SnakeGame.Models.MusicPlayer;
import SnakeGame.Models.SnakeModel;
import SnakeGame.RunnableSceneController;
import SnakeGame.SnakeGameApp;
import SnakeGame.StageManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import static SnakeGame.Controllers.GameController.musicPlayer1;

/**
 * Controller class for managing the main menu scene.
 * Extends RunnableSceneController for handling scene transitions.
 * Implements Initializable to initialize the scene.
 * Handles actions such as starting a new game, navigating to the leaderboard, going to settings, and exiting the application.
 *
 * @author Guilherme Julio
 */
public class    MenuController extends RunnableSceneController implements Initializable {

    /**
     * Default constructor for the MenuController class.
     */
    public MenuController() {}

    /**
     * The Button for changing the game background in the main menu.
     */
    public Button backgroundButton;

    /**
     * The GameController associated with the main menu.
     */
    public static GameController controller;

    /**
     * The Button for accessing the leaderboard in the main menu.
     */
    public Button leaderBoardButton;

    /**
     * The Button for starting a new game in the main menu.
     */
    @FXML
    public Button playButton;

    /**
     * The SettingsController associated with the main menu.
     */
    public static SettingsController settingsController;
    private int previousRandom;
    private int newRandom;


    /**
     * The ItemsController associated with the Items from the game.
     */
    public static ItemsController items;

    /**
     * Handles the action event when the "Play" button is clicked.
     * If the game is paused, resumes it. Otherwise, starts a new game.
     *
     * @param actionEvent The action event triggered by clicking the "Play" button.
     */
    @FXML
    public void goToGame(ActionEvent actionEvent) {
        if (controller != null && controller.gamePaused) {
            // If the game is paused, resume it
            SnakeGameApp.stageManager.resumeGame();
            items.resumeGame();
            MusicPlayer.resumeAllMusic();
            controller.gamePaused = false;
        } else {
            // If the game is not paused or no controller is set, start a new game
            musicPlayer1 = new MusicPlayer("src/main/resources/frogger.mp3");
            musicPlayer1.play();
            SnakeGameApp.stageManager.loadGameScene("/fxml/Game.fxml");
        }
    }

    /**
     * Navigates to the leaderboard scene when the "Leaderboard" button is clicked.
     *
     * @param actionEvent The action event triggered by clicking the "Leaderboard" button.
     */
    @FXML
    public void goToLeaderboard(ActionEvent actionEvent) {
        SnakeGameApp.stageManager.loadNewScene("/fxml/Leaderboard.fxml");
    }

    /**
     * Navigates to the settings scene or resumes the existing settings scene when the "Settings" button is clicked.
     *
     * @param actionEvent The action event triggered by clicking the "Settings" button.
     */
    @FXML
    public void goToSettings(ActionEvent actionEvent) {
        if (settingsController != null) {
            SnakeGameApp.stageManager.resumeSettings();
        } else {
            SnakeGameApp.stageManager.loadSettingsScene("/fxml/Settings.fxml");
        }
    }

    /**
     * Exits the application. Displays a confirmation dialog if the game is paused.
     *
     * @param actionEvent The action event triggered by clicking the "Exit" button.
     */
    @FXML
    public void exitApplication(ActionEvent actionEvent) {
        if (controller != null && controller.gamePaused) {
            alertProgress();
        } else {
            Platform.exit();
        }
    }

    /**
     * Initializes the main menu scene.
     * Adjusts button labels and visibility based on the game's state (paused or not).
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources specific to this controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (controller != null && controller.gamePaused) {
            playButton.setText("Resume");
            leaderBoardButton.setVisible(false);
            backgroundButton.setVisible(true);
        } else {
            playButton.setText("Play");
            leaderBoardButton.setVisible(true);
        }
        newRandom = 0;
        previousRandom = 0;
    }
    /**
     * Displays a confirmation dialog before exiting the application.
     * Warns the user about potential progress loss if leaving while the game is paused.
     */
    public void alertProgress() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Warning: Leaving now will result in lost progress");
        alert.setContentText("Are you sure you want to leave?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
                Platform.exit();
            } else {
                // User clicked "Cancel," do nothing
            }
        });
    }

    /**
     * Changes the background image randomly when the "Change Background" button is clicked.
     * Prevents selecting the same background consecutively.
     *
     * @param actionEvent The action event triggered by clicking the "Change Background" button.
     */
    public void randomBackground(ActionEvent actionEvent) {
        if (controller != null && controller.gamePaused) {
            Random random = new Random();
            while (previousRandom == newRandom) {
                newRandom = random.nextInt(5) + 17;
            }
            previousRandom = newRandom;
            ImageView image = new ImageView(ImageUtil.getImages().get(String.valueOf(newRandom)));
            controller.backgroundImageView.setImage(image.getImage());
        }
    }
}
