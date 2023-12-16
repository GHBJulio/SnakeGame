package SnakeGame.Controllers;

import SnakeGame.Models.MusicPlayer;
import SnakeGame.RunnableSceneController;
import SnakeGame.SnakeGameApp;
import SnakeGame.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController extends RunnableSceneController implements  Initializable{
    @FXML
    public ImageView muteButton;
    @FXML
    public ImageView volumeImage;
    public Slider fontSizeSlider;
    public Text fontSizeLabel;
    public Button blackWhiteGameBtn;
    public Slider objectSizeSlider;
    public Text inGameSizeText;

    private MenuController controller;

    public void volumeClicked(MouseEvent mouseEvent) {
        volumeImage.setVisible(false);
        muteButton.setVisible(true);
        MusicPlayer.setMuted(true);
        System.out.println("Muted");
    }

    public void muteClicked(MouseEvent mouseEvent) {
        volumeImage.setVisible(true);
        muteButton.setVisible(false);
        MusicPlayer.setMuted(false);
        System.out.println("Volume on");
    }

    public void goBack(MouseEvent mouseEvent) {
        SnakeGameApp.stageManager.loadNewScene("/fxml/Menu.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MenuController.settingsController = this;
    }

    public void blackWhiteMode(ActionEvent actionEvent) {
        if (StageManager.getBlackWhite()) {
            StageManager.setBlackWhite(false);
            blackWhiteGameBtn.setText("Non-Black & White");
            StageManager.reverseBlackWhite();
        }
        else {
            StageManager.setBlackWhite(true);
            blackWhiteGameBtn.setText("Black & White");
            StageManager.applyBlackWhite();

        }
    }
}
