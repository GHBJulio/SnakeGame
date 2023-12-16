package SnakeGame.Controllers;

import SnakeGame.Models.ImageUtil;
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

public class MenuController extends RunnableSceneController implements Initializable {

    public Button playButton;
    public Button leaderBoardButton;
    public static GameController controller;
    public Button backgroundButton;
    private int previousRandom;
    private int newRandom;
    public static SettingsController settingsController;
    @FXML
    public void goToGame(ActionEvent actionEvent) {
        if (controller != null && controller.gamePaused) {
            // If the game is paused, resume it
            SnakeGameApp.stageManager.resumeGame();
            controller.gamePaused = false;
        } else {
            // If the game is not paused or no controller is set, start a new game
            SnakeGameApp.stageManager.loadGameScene("/fxml/Game.fxml");
        }

    }
    @FXML
    public void goToLeaderboard(ActionEvent actionEvent) {
        SnakeGameApp.stageManager.loadNewScene("/fxml/Leaderboard.fxml");

    }

    @FXML
    public void goToSettings(ActionEvent actionEvent) {
        if (settingsController != null) {
            SnakeGameApp.stageManager.resumeSettings();
        } else {
            SnakeGameApp.stageManager.loadSettingsScene("/fxml/Settings.fxml");
        }
    }

    @FXML
    public void exitApplication(ActionEvent actionEvent) {
        if (controller != null && controller.gamePaused) {
            alertProgress();
        }
        else {
            Platform.exit();
        }
    }

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

        public void alertProgress()
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Warning: Leaving now will result in lost progress");
            alert.setContentText("Are you sure you want to leave?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.YES) {
                    // User clicked "Yes," perform any necessary action before closing the application
                    System.out.println("User clicked Yes");
                    Platform.exit();
                } else {
                    // User clicked "Cancel," do nothing
                    System.out.println("User clicked Cancel");
                }
            });
        }

    public void randomBackground(ActionEvent actionEvent) {
        if (controller != null && controller.gamePaused) {
            Random random = new Random();
            while(previousRandom == newRandom)
            {
                newRandom = random.nextInt(5) + 17;
            }
            System.out.println("New" + newRandom);
            System.out.println("Previous" + previousRandom);
            previousRandom = newRandom;
            ImageView image = new ImageView(ImageUtil.images.get(String.valueOf(newRandom)));
            controller.backgroundImageView.setImage(image.getImage());
        }
    }
}
