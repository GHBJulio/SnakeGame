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

/**
 * Controller class for managing the settings scene.
 * Extends RunnableSceneController for handling scene transitions.
 * Implements Initializable to initialize the scene.
 * Handles actions such as adjusting volume, changing font size, toggling black and white mode, and navigating back to the main menu.
 *
 * @author Guilherme Julio
 */
public class SettingsController extends RunnableSceneController implements Initializable {

    /**
     * Default constructor for the SettingsController class.
     */
    public SettingsController() {}

    /**
     * The Button for toggling between black and white game mode.
     */
    @FXML
    public Button blackWhiteGameBtn;

    /**
     * The ImageView for muting and unmuting audio.
     */
    @FXML
    public ImageView muteButton;

    /**
     * The ImageView for controlling the volume.
     */
    @FXML
    public ImageView volumeImage;

    /**
     * Handles the action event when the volume icon is clicked.
     * Mutes the game's music and updates the visibility of volume/mute icons.
     *
     * @param mouseEvent The mouse event triggered by clicking the volume icon.
     */
    public void volumeClicked(MouseEvent mouseEvent) {
        volumeImage.setVisible(false);
        muteButton.setVisible(true);
        MusicPlayer.setMuted(true);
        System.out.println("Muted");
    }

    /**
     * Handles the action event when the mute icon is clicked.
     * Unmutes the game's music and updates the visibility of volume/mute icons.
     *
     * @param mouseEvent The mouse event triggered by clicking the mute icon.
     */
    public void muteClicked(MouseEvent mouseEvent) {
        volumeImage.setVisible(true);
        muteButton.setVisible(false);
        MusicPlayer.setMuted(false);
        System.out.println("Volume on");
    }

    /**
     * Navigates back to the main menu when the "Back" button is clicked.
     *
     * @param mouseEvent The mouse event triggered by clicking the "Back" button.
     */
    public void goBack(MouseEvent mouseEvent) {
        SnakeGameApp.stageManager.loadNewScene("/fxml/Menu.fxml");
    }

    /**
     * Initializes the settings scene.
     * Associates the current instance of the settings controller with the MenuController.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources specific to this controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MenuController.settingsController = this;
    }

    /**
     * Toggles between black and white mode and non-black and white mode when the corresponding button is clicked.
     * Updates the button text and applies or reverses the black and white mode.
     *
     * @param actionEvent The action event triggered by clicking the black and white mode button.
     */
    public void blackWhiteMode(ActionEvent actionEvent) {
        if (StageManager.getBlackWhite()) {
            StageManager.setBlackWhite(false);
            blackWhiteGameBtn.setText("Non-Black & White");
            StageManager.reverseBlackWhite();
        } else {
            StageManager.setBlackWhite(true);
            blackWhiteGameBtn.setText("Black & White");
            StageManager.applyBlackWhite();
        }
    }
}
