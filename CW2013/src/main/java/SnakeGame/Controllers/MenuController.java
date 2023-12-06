package SnakeGame.Controllers;

import SnakeGame.Models.SnakeModel;
import SnakeGame.RunnableSceneController;
import SnakeGame.SnakeGameApp;
import SnakeGame.StageManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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
            System.out.println("helo");
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
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (controller != null && controller.gamePaused) {
            playButton.setText("Resume");
            leaderBoardButton.setVisible(false);
        }
        else{
            playButton.setText("Play");
            leaderBoardButton.setVisible(true);
        }

    }
}
