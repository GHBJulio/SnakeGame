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

public class LeaderboardController extends RunnableSceneController implements Initializable {

    @FXML
    public ImageView backButton;
    @FXML
    private VBox leaderboardVBox; // Assuming you have this VBox in your FXML file

    public static List<PlayerModel> topPlayers;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //List<Player> topPlayers = null;
        try {
            topPlayers = getTopPlayers();
            if (topPlayers == null || topPlayers.isEmpty()) {
                System.out.println("Null TopPlayers");
                return;
            }
            displayTopPlayers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private void displayTopPlayers() {
//        for (Player player : topPlayers) {
//            Label playerLabel = new Label(playerToString(player));
//            playerLabel.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;"); // White text color
//            playerLabel.setAlignment(Pos.CENTER);
//            leaderboardVBox.getChildren().add(playerLabel);
//        }
//    }

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

    private String playerToString(PlayerModel player) {
        return "Player Name: " + player.getPlayerName() + ", Score: " + player.getScore();
    }

    public static List<PlayerModel> getTopPlayers() throws IOException {
        List<PlayerModel> players = DatabaseConnection.readPlayersFromLeaderboard();
        if (players == null || players.isEmpty())
        {
            showAlert("Leaderboard not available yet.");
            return null;
        }

        // Sort players based on score in descending order
        Collections.sort(players, Comparator.comparingInt(PlayerModel::getScore).reversed());

        // Return the top 10 players (or less if there are fewer than 10)
        return players.subList(0, Math.min(players.size(), 10));
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void goBack(MouseEvent mouseEvent) {
        SnakeGameApp.stageManager.loadScene("/fxml/Menu.fxml");
    }
}





