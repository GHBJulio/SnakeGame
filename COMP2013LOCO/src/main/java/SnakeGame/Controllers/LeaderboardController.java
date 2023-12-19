package SnakeGame.Controllers;

import SnakeGame.Models.PlayerModel;
import SnakeGame.RunnableSceneController;
import SnakeGame.SnakeGameApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for managing the leaderboard scene.
 * Extends RunnableSceneController for handling scene transitions.
 * Implements Initializable to initialize the scene.
 * Displays the top players and allows the user to navigate back to the main menu.
 *
 * @author Guilherme Julio
 */
public class LeaderboardController extends RunnableSceneController implements Initializable {

    /**
     * Default constructor for the GameController class.
     */
    public LeaderboardController() {}

    /**
     * The ImageView for the back button in the leaderboard scene.
     */
    @FXML
    public ImageView backButton;
    @FXML
    private VBox leaderboardVBox;

    /**
     * List containing the top players retrieved from the database.
     */
    public static List<PlayerModel> topPlayers;

    /**
     * Initializes the leaderboard scene by retrieving top players and displaying them.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources specific to this controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            topPlayers = getTopPlayers();
            if (topPlayers == null || topPlayers.isEmpty()) {
                return;
            }
            displayTopPlayers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays the top players on the leaderboard scene.
     */
    private void displayTopPlayers() {
        // Create a GridPane to organize player information
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);

        for (int i = 0; i < topPlayers.size(); i++) {
            PlayerModel player = topPlayers.get(i);

            // Create a label for each player
            Label playerLabel = new Label(playerToString(player));
            playerLabel.setStyle(
                    "-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #FFFFFF; " +
                            "-fx-background-color: " + (i % 2 == 0 ? "#336633" : "#447c4d") + ";"
            ); // White text color, alternate background color

            // Set background styling for each row
            BackgroundFill backgroundFill = new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
            Background background = new Background(backgroundFill);
            playerLabel.setBackground(background);

            // Add the label to the GridPane
            gridPane.add(playerLabel, 0, i);
        }

        // Add the GridPane to the VBox
        leaderboardVBox.getChildren().add(gridPane);
    }

    /**
     * Converts PlayerModel object to a string representation.
     *
     * @param player The PlayerModel object.
     * @return A string representation of the player's name and score.
     */
    private String playerToString(PlayerModel player) {
        return "Player Name: " + player.getPlayerName() + ", Score: " + player.getScore();
    }

    /**
     * Retrieves the top players from the database and sorts them based on their scores.
     * Returns the top 10 players or less if there are fewer than 10.
     *
     * @return A list of top players.
     * @throws IOException If an I/O error occurs while reading players from the database.
     */
    public static List<PlayerModel> getTopPlayers() throws IOException {
        List<PlayerModel> players = DatabaseConnection.readPlayersFromLeaderboard();
        if (players == null || players.isEmpty()) {
            showAlert("Leaderboard not available yet.");
            return null;
        }

        // Sort players based on score in descending order
        Collections.sort(players, Comparator.comparingInt(PlayerModel::getScore).reversed());

        // Return the top 10 players (or less if there are fewer than 10)
        return players.subList(0, Math.min(players.size(), 10));
    }

    /**
     * Displays an alert with the given message.
     *
     * @param message The message to be displayed in the alert.
     */
    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Navigates back to the main menu when the back button is clicked.
     *
     * @param mouseEvent The mouse event triggered by clicking the back button.
     */
    public void goBack(MouseEvent mouseEvent) {
        SnakeGameApp.stageManager.loadNewScene("/fxml/Menu.fxml");
    }
}
