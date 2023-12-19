package SnakeGame.Controllers;

import SnakeGame.Models.*;
import SnakeGame.RunnableSceneController;
import SnakeGame.SnakeGameApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * The GameController class is responsible for managing the game logic, user input, and scene transitions
 * in the Snake Game application. It extends the RunnableSceneController and implements Initializable
 * to handle JavaFX initialization.
 *
 * @author Guilherme Julio
 */
public class GameController extends RunnableSceneController implements Initializable {

    /**
     * Default constructor for the GameController class.
     */
    public GameController() {}

    /**
     * The AnchorPane used as a background pane
     */
    @FXML
    public AnchorPane rootPane;
    /**
     * ImageView used to add an image for our Background.
     */
    @FXML
    public ImageView backgroundImageView;
    private MenuController menu;
    /**
     * The field used to store a name.
     */
    @FXML
    public javafx.scene.control.TextField addNameField;

    /**
     * The button used to add a name.
     */
    @FXML
    public javafx.scene.control.Button addNameButton;
    /**
     * The Text displayed as the game reaches the end.
     */
    public Text endText;

    /**
     * The Text used to display the score.
     */
    public Text scoreLabel;

    /**
     * The int used to store minimum score for a condition.
     */
    public int scoreMin;
    private SnakeModel snake;
    private ItemsController items;
    private boolean gameRunning;

    /**
     * The boolean used to check if the game is paused.
     */
    public boolean gamePaused;
    private ImageView head;
    private ImageView bodyImage;
    private Thread gameThread;
    @FXML
    private ImageView endImage;
    private int lastX = 0;
    private int lastY = 0;

    /**
     * The instance used to play music simultaneously.
     */
    public static MusicPlayer musicPlayer1;

    /**
     * The double used to have a default speed for the snake.
     */
    public double speedFactor;


    /**
     * Initializes the GameController with default settings when the associated JavaFX scene is loaded.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources specific to this controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization code here
        scoreMin = 5;
        speedFactor = 1.0;
        items = new ItemsController();
        MenuController.controller = this;
        items.controller = this;
        ImageView image = new ImageView(ImageUtil.getImages().get("19"));
        backgroundImageView.setImage(image.getImage());
        gameRunning = true;
        gamePaused = false;
        snake = new SnakeModel(0, 0);
        snake.setHeadX(3 * snake.getW());
        snake.setHeadY(3 * snake.getH());
        head = snake.getImgSnakeHead();
        head.setX(snake.getHeadX());
        head.setY(snake.getHeadY());
        musicPlayer1 = new MusicPlayer("src/main/resources/frogger.mp3");
        musicPlayer1.play();
        startGame();
    }

    /**
     * Starts the game loop in a separate thread, continuously updating the game state and rendering.
     * The thread sleeps based on the speedFactor to control the game's frame rate.
     */
    private void startGame() {
        // Animation timer
        gameThread = new Thread(() -> {
            while (gameRunning) {
                if (!gamePaused) {
                    drawGame();
                    outofBounds();
                }
                try {
                    Thread.sleep((long) (1000 / (15 * speedFactor)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!gameRunning) {
                gameEnded();
            }
        });
    }

    /**
     * Overrides the run method of the RunnableSceneController to handle keyboard input for the game.
     * Listens for key presses, such as arrow keys for controlling the snake and ESC for pausing the game.
     */
    @Override
    public void run() {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                if (!gamePaused) {
                    switch (e.getCode()) {
                        case ESCAPE:
                            togglePause();
                            break;
                        case UP:
                            if (!snake.isDown()) {
                                snake.setUp(true);
                                snake.setDown(false);
                                snake.setLeft(false);
                                snake.setRight(false);
                            }
                            break;
                        case DOWN:
                            if (!snake.isUp()) {
                                snake.setUp(false);
                                snake.setDown(true);
                                snake.setLeft(false);
                                snake.setRight(false);
                            }
                            break;
                        case LEFT:
                            if (!snake.isRight()) {
                                snake.setUp(false);
                                snake.setDown(false);
                                snake.setLeft(true);
                                snake.setRight(false);
                            }
                            break;
                        case RIGHT:
                            if (!snake.isLeft()) {
                                snake.setUp(false);
                                snake.setDown(false);
                                snake.setLeft(false);
                                snake.setRight(true);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        gameThread.start();
    }
    /**
     * Pauses or resumes the game when the pause button is clicked.
     *
     * @param mouseEvent The MouseEvent triggered by clicking the pause button.
     */
    @FXML
    public void pauseGame(MouseEvent mouseEvent) {
        togglePause();
    }

    /**
     * Toggles between pausing and resuming the game. Changes the gamePaused state accordingly.
     * If the game is paused, loads the Menu scene; otherwise, prints "Resumed" to the console.
     */
    private void togglePause()  {
        gamePaused = !gamePaused;
        if (gamePaused) {
            items.pauseGame();
            MusicPlayer.pauseAllMusic();
            SnakeGameApp.stageManager.loadNewScene("/fxml/Menu.fxml");
        } else {
            items.resumeGame();
        }
    }

    /**
     * Draws the game on the UI. Updates the game state, handles item interactions, and renders the snake.
     * Invoked periodically to maintain real-time updates.
     */
    public void drawGame() {
        Platform.runLater(() -> {
            if (snake.isAlive()) {
                createSnake();
                actualize();
                if (items.food.isAlive()) {
                    if (items.food.isAlive() && !items.obstacle.isAlive()) {
                        items.drawObstacle();
                        items.obstacleHit(snake);
                        items.deleteObstacle();
                        items.obstacle = new ItemsModel();
                    }
                    items.drawFood();
                    items.drawObstacle();
                    items.obstacleHit(snake);
                    items.eaten(snake);
                } else {
                    items.deleteObstacle();
                    items.deleteFood();
                    items.obstacle = new ItemsModel();
                    items.food = new ItemsModel();
                }
                if (items.slowMo.isAlive() && snake.getScore() == scoreMin && speedFactor == 1.0) {
                    items.drawSloMo();
                    items.slowMoHit(snake);
                } else {
                    items.deleteSlow();
                    items.slowMo = new ItemsModel();
                }
                if (items.fastSpeed.isAlive() && snake.getScore() == scoreMin + 4 && speedFactor == 1.0) {
                    items.drawFastSpeed();
                    items.fastSpeedHit(snake);
                } else {
                    items.deleteFastSpeed();
                    items.fastSpeed = new ItemsModel();
                }
                if (items.doublePoints.isAlive() && snake.getScore() == scoreMin + 3) {
                    items.drawDoublePoints();
                    items.doublePointsHit(snake);
                } else {
                    items.deleteDoublePoints();
                    items.doublePoints = new ItemsModel();
                }
                actualizeBody();
                move();

            } else {
                gameEnded();
            }
            drawScore();
        });
    }

    /**
     * Creates and renders the snake on the game screen. Checks for boundary violations and body collisions.
     */
    public void createSnake(){
        outofBounds();
        eatBody();
        if (!rootPane.getChildren().contains(head))
        {
            rootPane.getChildren().add(head);
        }
    }


    /**
     * Moves the snake based on its current direction.
     * Updates the last known coordinates for future reference.
     */
    public void move() {
        lastX = snake.getHeadX();
        lastY = snake.getHeadY();

        // Make the snake move
        if (snake.isUp()) {
            snake.setHeadY(snake.getHeadY() - snake.getH());
        } else if (snake.isDown()) {
            snake.setHeadY(snake.getHeadY() + snake.getH());
        } else if (snake.isLeft()) {
            snake.setHeadX(snake.getHeadX() - snake.getW());
        } else if (snake.isRight()) {
            snake.setHeadX(snake.getHeadX() + snake.getW());
        }
    }

    /**
     * Draws and updates the score on the UI.
     */
    public void drawScore() {
        scoreLabel.setText("Score: " + snake.getScore());
    }

    /**
     * Updates the positions of the snake's body segments on the UI.
     */
    public void actualizeBody() {
        for (int i = snake.getBodyPoints().size() - 1; i >= 0; i--) {
            Point bodyPoint = snake.getBodyPoints().get(i);
            bodyImage = snake.getBodyPointImages().get(i);

            if (i != 0) {
                Point nextBodyPoint = snake.getBodyPoints().get(i - 1);
                bodyPoint.setLocation(nextBodyPoint.getX(), nextBodyPoint.getY());
            } else {
                bodyPoint.setLocation(lastX, lastY);
            }

            bodyImage.setLayoutX(bodyPoint.getX());
            bodyImage.setLayoutY(bodyPoint.getY());
        }
    }

    /**
     * Updates the position and rotation of the snake's head on the UI.
     */
    private void actualize() {
        head.setX(snake.getHeadX());
        head.setY(snake.getHeadY());

        // Rotate the image based on the snake's direction
        double rotation = 0.0;

        if (snake.isUp()) {
            rotation = -90.0;
        } else if (snake.isDown()) {
            rotation = 90.0;
        } else if (snake.isLeft()) {
            rotation = -180.0;
        } else if (snake.isRight()) {
            rotation = 0.0;
        }

        head.setRotate(rotation);
        head.setImage(snake.getImgSnakeHead().getImage());
    }

    /**
     * Checks if the snake is out of bounds and ends the game if true.
     */
    private void outofBounds() {
        boolean xOut = (snake.getHeadX() < 0 || snake.getHeadX() > (800 - snake.getW()));
        boolean yOut = (snake.getHeadY() < 0 || snake.getHeadY() > (500 - snake.getH()));

        if (xOut || yOut) {
            gameEnded();
        }
    }

    /**
     * Checks for collisions between segments of the snake's body, marking the snake as not alive if detected.
     */
    public void eatBody() {
        Point headPoint = new Point(snake.getHeadX(), snake.getHeadY());
        if (snake.getSnakeLength() > 1) {
            for (Point point : snake.getBodyPoints()) {
                if (point.equals(headPoint)) {
                    snake.setAlive(false);
                }
            }
        }
    }

    /**
     * Ends the game, hides UI elements, and shows the end game screen.
     */
    public void gameEnded() {
        hideAll(rootPane);
        showEndGame();
        gamePaused = true;
        MenuController.controller = null;
        MusicPlayer.stopAllMusic();
    }

    /**
     * Submits the player's name to the leaderboard if it meets valid criteria; otherwise, shows an input alert.
     *
     * @param actionEvent The ActionEvent triggered by the submit name button.
     * @throws IOException If an IO exception occurs.
     */
    public void submitName(ActionEvent actionEvent) throws IOException {
        DatabaseConnection.createLeaderboard();
        if (!addNameField.getText().matches("[a-zA-Z0-9 ]+") || addNameField.getText() == null ||
                addNameField.getText().isEmpty() || addNameField.getText().length() > 15 ||
                addNameField.getText().trim().isEmpty()) {
            showInvalidInputAlert();
        } else {
            DatabaseConnection.updateLeaderboard(addNameField.getText(), snake.getScore());
            SnakeGameApp.stageManager.loadNewScene("/fxml/Menu.fxml");
        }
    }

    /**
     * Shows an alert for invalid input when submitting the player's name.
     */
    public static void showInvalidInputAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText("Invalid name! Please use 10 characters maximum and only alphanumeric characters and spaces.");

        alert.showAndWait();
    }

    /**
     * Shows the end game screen with relevant UI elements.
     */
    private void showEndGame() {
        endImage.setVisible(true);
        addNameField.setVisible(true);
        addNameButton.setVisible(true);
        endText.setVisible(true);
    }

    /**
     * Hides all UI elements within the specified AnchorPane.
     *
     * @param anchorPane The AnchorPane containing UI elements to hide.
     */
    public static void hideAll(AnchorPane anchorPane) {
        for (Node node : anchorPane.getChildren()) {
            // Set visibility to false for all nodes
            node.setVisible(false);
        }
    }


}
