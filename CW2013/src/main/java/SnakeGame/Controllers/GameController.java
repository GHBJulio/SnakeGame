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
import javafx.scene.shape.Rectangle;
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

    private ItemsModel food;

    private ItemsModel obstacle;
    public Text endText;
    public Text scoreLabel;
    private SnakeModel snake;

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
    private MenuController controller;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MenuController.controller = this;
        ImageView image = new ImageView(ImageUtil.images.get("19"));
        backgroundImageView.setImage(image.getImage());
        gameRunning = true;
        gamePaused = false;
        snake = new SnakeModel(100, 100);
        head = snake.getImgSnakeHead();
        head.setX(snake.getHeadX());
        head.setY(snake.getHeadY());
        food = new ItemsModel();
        obstacle = new ItemsModel();
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
                        Thread.sleep(1000 / 25);
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
                if (food.isAlive()) {
                    if(food.isAlive() && !obstacle.isAlive())
                    {
                        drawObstacle();
                        obstacleHit(snake);
                        deleteObstacle();
                        obstacle = new ItemsModel();
                    }
                    drawFood();
                    drawObstacle();
                    obstacleHit(snake);
                    ImageView newSnakeBody = eaten(snake);
                    if (newSnakeBody != null) {
                        rootPane.getChildren().add(newSnakeBody);
                    }
                } else {
                        deleteObstacle();
                        deleteFood();
                        obstacle = new ItemsModel();
                        food = new ItemsModel();
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

    public ImageView eaten(SnakeModel mySnake)	{
        javafx.scene.shape.Rectangle snakeRect = mySnake.getRectangle();
        Rectangle foodRect = food.ItemRectangle();

        if (foodRect.getBoundsInParent().intersects(snakeRect.getBoundsInParent()) && food.isAlive() && mySnake.isAlive()) {
            MusicPlayer.getMusicPlay("src/main/resources/snakeEat.mp3", false);
            food.setAlive(false);
            ImageView bodyImage = new ImageView(mySnake.getBody());
            mySnake.bodyPoints.add(new Point(mySnake.getHeadX(), mySnake.getHeadY()));
            mySnake.bodyPointImages.add(bodyImage);
            mySnake.changeLength(mySnake.getSnakeLength() + 1);
            mySnake.setScore(mySnake.getScore() + 1);
            return bodyImage;
        }

        return null;
    }


    public void obstacleHit(SnakeModel mySnake)	{
        javafx.scene.shape.Rectangle snakeRect = mySnake.getRectangle();
        Rectangle obstacleRect = obstacle.ItemRectangle();

        if (obstacleRect.getBoundsInParent().intersects(snakeRect.getBoundsInParent()) && obstacle.isAlive() && mySnake.isAlive()) {
            MusicPlayer.getMusicPlay("src/main/resources/snakeEat.mp3", false);
            obstacle.setAlive(false);
            int lastIndexImage = mySnake.bodyPointImages.size() - 1;
            int lastIndex = mySnake.bodyPoints.size() - 1;
            System.out.println(mySnake.getSnakeLength());


            // Check if the snake's length is greater than 1
            if (mySnake.getSnakeLength() - 1 >= 1) {
                mySnake.changeLength(mySnake.getSnakeLength() - 1);
                mySnake.bodyPoints.remove(lastIndex);
                mySnake.setScore(mySnake.getScore() - 1);
                // Remove the oldest bodyImage
                ImageView removedBodyImage = mySnake.bodyPointImages.remove(lastIndexImage);
                // Remove it from the rootPane (or your container)
                rootPane.getChildren().remove(removedBodyImage);
            }
            else if (mySnake.getSnakeLength() - 1 < 1)
            {
                gameEnded();
            }
        }

    }

    public void deleteFood()
    {
        rootPane.getChildren().remove(foodImage);
    }

    public void deleteObstacle()
    {
        rootPane.getChildren().remove(obstacleImage);
    }

    public void drawFood() {
        if (!rootPane.getChildren().contains(foodImage)) {
            foodImage = new ImageView(food.getImage());
            foodImage.setLayoutX(food.getHeadX());
            foodImage.setLayoutY(food.getHeadY());
            rootPane.getChildren().add(foodImage);
        }
    }

    public void drawObstacle() {
        if (!rootPane.getChildren().contains(obstacleImage)) {
            obstacleImage = new ImageView(obstacle.getObstacleImage());
            obstacleImage.setLayoutX(obstacle.getHeadX());
            obstacleImage.setLayoutY(obstacle.getHeadY());
            rootPane.getChildren().add(obstacleImage);
        }
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


    private void gameEnded()
    {
        hideAll(rootPane);
        showEndGame();
        gamePaused = true;
        MenuController.controller = null;
        MusicPlayer.stopAllMusic();
    }

    public void submitName(ActionEvent actionEvent) throws IOException {
        DatabaseConnection.createLeaderboard();
        if (!addNameField.getText().matches("[a-zA-Z0-9 ]+") || addNameField.getText() == null || addNameField.getText().isEmpty() || addNameField.getText().length() > 15 || addNameField.getText().trim().isEmpty())
        {
            showInvalidInputAlert();
        }
        else{
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
