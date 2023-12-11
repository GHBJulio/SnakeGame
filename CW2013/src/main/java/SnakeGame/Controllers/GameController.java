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

public class GameController extends RunnableSceneController implements Initializable {
    @FXML
    public AnchorPane rootPane;
    @FXML
    public ImageView backgroundImageView;
    private MenuController menu;
    @FXML
    public javafx.scene.control.TextField addNameField;
    @FXML
    public javafx.scene.control.Button addNameButton;
    public Text endText;
    public Text scoreLabel;
    public int scoreMin;
    private SnakeModel snake;
    private ItemsController items;
    private boolean gameRunning;
    public boolean gamePaused;
    private ImageView head;
    private ImageView foodImage;
    private ImageView bodyImage;
    private ImageView obstacleImage;
    private Thread gameThread;
    @FXML
    private ImageView endImage;
    private MusicPlayer musicPlayer1;

    // default speed 1.0
    public double speedFactor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scoreMin = 5;
        speedFactor = 1.0;
        items = new ItemsController();
        MenuController.controller = this;
        items.controller = this;
        ImageView image = new ImageView(ImageUtil.images.get("19"));
        backgroundImageView.setImage(image.getImage());
        gameRunning = true;
        gamePaused = false;
        snake = new SnakeModel(100, 100);
        head = snake.getImgSnakeHead();
        head.setX(snake.getHeadX());
        head.setY(snake.getHeadY());
        //musicPlayer1 = new MusicPlayer("src/main/resources/frogger.mp3");
        //musicPlayer1.play();
        startGame();
    }

    private void startGame()
    {
        //animation timer
        gameThread = new Thread(() -> {
            while (gameRunning) {
                if (!gamePaused) {
                    drawGame();
                    outofBounds();
                }
                    try {
                        Thread.sleep((long) (1000 / (30 * speedFactor)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
            if (!gameRunning){ gameEnded();}
        });
    }

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

    @FXML
    public void pauseGame(MouseEvent mouseEvent) {
            togglePause();
    }
    private void togglePause()  {
        gamePaused = !gamePaused;
        if (gamePaused) {
            SnakeGameApp.stageManager.loadNewScene("/fxml/Menu.fxml");
            System.out.println("Paused");
        } else {
            System.out.println("Resumed");
        }
    }

    public void drawGame() {
        Platform.runLater(() -> {
            if (snake.isAlive()) {
                createSnake();
                actualize();
                if (items.food.isAlive()) {
                    if(items.food.isAlive() && !items.obstacle.isAlive())
                    {
                        items.drawObstacle();
                        items.obstacleHit(snake);
                        items.deleteObstacle();
                        items.obstacle = new ItemsModel();
                    }
                    items.drawFood();
                    items.drawObstacle();
                    items.obstacleHit(snake);
                    ImageView newSnakeBody = items.eaten(snake);
                    if (newSnakeBody != null) {
                        rootPane.getChildren().add(newSnakeBody);
                    }
                } else {
                        items.deleteObstacle();
                        items.deleteFood();
                        items.obstacle = new ItemsModel();
                        items.food = new ItemsModel();
                    }
                if (items.slowMo.isAlive() && snake.score == scoreMin)
                {
                    items.drawSloMo();
                    items.slowMoHit(snake);
                }
                else{
                    items.deleteSlow();
                    items.slowMo = new ItemsModel();
                }
                    actualizeBody();
                    move();

            } else {
                gameEnded();
            }
            drawScore();
        });
    }


    public void createSnake(){
        outofBounds();
        eatBody();
        if (!rootPane.getChildren().contains(head))
        {
            rootPane.getChildren().add(head);
        }
        //move();
    }



    public void move()
    {
        lastX = snake.getHeadX();
        lastY = snake.getHeadY();
        // make the snake move
        if (snake.isUp())
        {
            lastY = snake.getHeadY() + snake.getNum();
            snake.setHeadY(snake.getHeadY() - snake.getNum());
        } else if (snake.isDown())
        {
            lastY = snake.getHeadY() - snake.getNum();
            snake.setHeadY(snake.getHeadY() + snake.getNum());
        } else if (snake.isLeft())
        {
            lastX = snake.getHeadX() + snake.getNum();
            snake.setHeadX(snake.getHeadX() - snake.getNum());
        } else if (snake.isRight())
        {
            lastX = snake.getHeadX() - snake.getNum();
            snake.setHeadX(snake.getHeadX() + snake.getNum());
        }
    }
    private int lastX = 0;
    private int lastY = 0;



    public void drawScore()
    {
        scoreLabel.setText("Score: " + snake.getScore());
    }

    public void actualizeBody()
    {
        for (int i = snake.bodyPoints.size() - 1; i >= 0; i--)
        {
            Point bodyPoint = snake.bodyPoints.get(i);
            bodyImage = snake.bodyPointImages.get(i);
            if (i != 0)
            {
                Point nextBodyPoint = snake.bodyPoints.get(i - 1);
                bodyPoint.setLocation(nextBodyPoint.getX(), nextBodyPoint.getY());
            }
            else
            {
                bodyPoint.setLocation(lastX, lastY);
            }
            bodyImage.setLayoutX(bodyPoint.getX());
            bodyImage.setLayoutY(bodyPoint.getY());
        }
    }

    private void actualize()
    {
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
        // Set the image of the head
        head.setImage(snake.getImgSnakeHead().getImage());
    }

    private void outofBounds()
    {
        boolean xOut = (snake.getHeadX() <= 0 ||snake.getHeadX() >= (800 - snake.getW()));
        boolean yOut = (snake.getHeadY() <= 0 || snake.getHeadY() >= (500 - snake.getH())); // updated version = boolean yOut = (y <= 0 || y >= (560 - h));
        // old code yOut wasn't working properly as snake would not be able to eat food on top of the screen
        if (xOut || yOut)
        {
            gameEnded();
        }
    }

    public void eatBody()
    {
        if (snake.getSnakeLength() > 1) {
            for (Point point : snake.bodyPoints) {
                for (Point point2 : snake.bodyPoints) {
                    if (point.equals(point2) && point != point2) {
                        snake.setAlive(false);
                    }
                }
            }
        }
    }

    public void gameEnded()
    {
        hideAll(rootPane);
        showEndGame();
        gamePaused = true;
        MenuController.controller = null;
        MusicPlayer.stopAllMusic();
    }

    public void submitName(ActionEvent actionEvent) throws IOException {
        DatabaseConnection.createLeaderboard();
        if (!addNameField.getText().matches("[a-zA-Z0-9 ]+") || addNameField.getText() == null || addNameField.getText().isEmpty() || addNameField.getText().length() > 15 || addNameField.getText().trim().isEmpty()) {
            showInvalidInputAlert();
        } else {
            DatabaseConnection.updateLeaderboard(addNameField.getText(), snake.getScore());
            SnakeGameApp.stageManager.loadNewScene("/fxml/Menu.fxml");
        }
    }

    public static void showInvalidInputAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText("Invalid name! Please 10 characters maximum and use only alphanumeric characters and spaces.");

        alert.showAndWait();
    }


    private void showEndGame() {
        endImage.setVisible(true);
        addNameField.setVisible(true);
        addNameButton.setVisible(true);
        endText.setVisible(true);
    }

    public static void hideAll(AnchorPane anchorPane) {
        for (Node node : anchorPane.getChildren()) {
            // Set visibility to false for all nodes
            node.setVisible(false);
        }
    }

}
