package SnakeGame.Controllers;

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

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController extends RunnableSceneController implements Initializable {

    public Button playButton;
    public Button leaderBoardButton;

    public static GameController controller;

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
        } else {
            playButton.setText("Play");
            leaderBoardButton.setVisible(true);
        }
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
}
