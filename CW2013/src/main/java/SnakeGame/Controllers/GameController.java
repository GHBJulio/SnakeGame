package SnakeGame.Controllers;

import SnakeGame.Models.*;
import SnakeGame.RunnableSceneController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends RunnableSceneController implements Initializable {
    @FXML
    public AnchorPane rootPane;
    @FXML
    public javafx.scene.control.TextField addNameField;
    @FXML
    public javafx.scene.control.Button addNameButton;
    public Text endText;
    public Text scoreLabel;
    private SnakeModel snake;

    private boolean gameRunning;
    private ImageView head;

    private ImageView foodImage;
    private Thread gameThread;
    private Food food;

    @FXML
    private ImageView endImage;

    private MenuController controller;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameRunning = true;
        snake = new SnakeModel(100, 100);
        snake.setW(30);
        snake.setH(30);
        head = snake.getImgSnakeHead();
        food = new Food();

        gameThread = new Thread(() -> {
            while (gameRunning) {
                drawGame();
                try {
                    outofBounds();
                    Thread.sleep(30);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                move();
                actualize();
            }
            gameEnded();
        });
    }

    @Override
    public void run() {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                switch (e.getCode())
                {
                    case UP:
                        if (!snake.isDown())
                        {
                            snake.setUp(true);
                            snake.setDown(false);
                            snake.setLeft(false);
                            snake.setRight(false);
                        }
                        break;

                    case DOWN:
                        if (!snake.isUp())
                        {
                            snake.setUp(false);
                            snake.setDown(true);
                            snake.setLeft(false);
                            snake.setRight(false);
                        }
                        break;

                    case LEFT:
                        if (!snake.isRight())
                        {
                            snake.setUp(false);
                            snake.setDown(false);
                            snake.setLeft(true);
                            snake.setRight(false);
                        }
                        break;

                    case RIGHT:
                        if (!snake.isLeft())
                        {
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
        });
        gameThread.start();
    }

    public void drawGame() {
        if (snake.isAlive()) {
            createSnake();
            if (food.isAlive) {
                drawFood();
                food.eaten(snake);
            } else {
                food = new Food();
            }
        } else {
           gameEnded();
        }
       drawScore();
    }
    public void createSnake(){
        outofBounds();
        eatBody();
        snake.bodyPoints.add(new Point2D(snake.getHeadX(), snake.getHeadY()));

        if (snake.bodyPoints.size() == (snake.snakeLength - 1) * snake.getNum())
        {
            snake.bodyPoints.remove(0);
        }
        rootPane.getChildren().add(head);
        drawBody();
        move();
    }
    public void drawFood()
    {
        // Create an ImageView with the image
        foodImage = new ImageView(food.getImage());

        // Set the position of the ImageView
        foodImage.setLayoutX(food.getHeadX());
        foodImage.setLayoutY(food.getHeadY());

        // Add the ImageView to the rootPane
        rootPane.getChildren().add(foodImage);
    }

    public void move()
    {
        // make the snake move
        if (snake.isUp())
        {
            snake.setHeadY(snake.getHeadY() - snake.getSpeed_XY());
        } else if (snake.isDown())
        {
            snake.setHeadY(snake.getHeadY() + snake.getSpeed_XY());
        } else if (snake.isLeft())
        {
            snake.setHeadX(snake.getHeadX() - snake.getSpeed_XY());
        } else if (snake.isRight())
        {
            snake.setHeadX(snake.getHeadX() + snake.getSpeed_XY());
        }
    }

    public void drawScore()
    {
        scoreLabel.setText("Score: " + snake.getScore());
    }
    public void drawBody()
    {
        int length = snake.bodyPoints.size() - 1 - snake.getNum();
        for (int i = length; i >= snake.getNum(); i -= snake.getNum())
        {
            Point2D point = snake.bodyPoints.get(i);
            ImageView snakeBody = new ImageView(snake.getBody());
            snakeBody.setLayoutX(point.getX());
            snakeBody.setLayoutY(point.getY());
            rootPane.getChildren().add(snakeBody);
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
        boolean xOut = (snake.getHeadX() <= 0 ||snake.getHeadX() >= (810 - snake.getW()));
        boolean yOut = (snake.getHeadY() <= 0 || snake.getHeadY() >= (510 - snake.getH())); // updated version = boolean yOut = (y <= 0 || y >= (560 - h));
        // old code yOut wasn't working properly as snake would not be able to eat food on top of the screen
        if (xOut || yOut)
        {
            gameEnded();
        }
    }

    public void eatBody()
    {
        for (Point2D point : snake.bodyPoints)
        {
            for (Point2D point2 : snake.bodyPoints)
            {
                if (point.equals(point2) && point != point2)
                {
                    snake.setAlive(false);
                }
            }
        }
    }


    private void gameEnded()
    {
        snake.setAlive(false);
        gameRunning = false;
        endImage.setVisible(true);
        addNameField.setVisible(true);
        addNameButton.setVisible(true);
        endText.setVisible(true);
        head.setVisible(false);
        foodImage.setVisible(false);

        MusicPlayer.stopMusic("src/main/resources/frogger.mp3");
    }


    public void submitName(ActionEvent actionEvent) {
        System.out.println(addNameField.getText());
    }

}
