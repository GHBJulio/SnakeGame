package SnakeGame.Controllers;

import SnakeGame.RunnableSceneController;
import SnakeGame.SnakeGameApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;

public class MenuController extends RunnableSceneController {

    @FXML
    public void goToGame(ActionEvent actionEvent) {
        SnakeGameApp.stageManager.loadScene("/fxml/Game.fxml");

    }
    @FXML
    public void goToLeaderboard(ActionEvent actionEvent) {
        // SnakeGameApp.stageManager.loadScene("/fxml/Leaderboard.fxml");
    }

    @FXML
    public void exitApplication(ActionEvent actionEvent) {
        Platform.exit();
    }

    public Scene getScenes()
    {
        return scene;
    }
}
