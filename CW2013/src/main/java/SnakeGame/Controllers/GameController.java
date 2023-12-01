package SnakeGame.Controllers;

import SnakeGame.Models.FoodModel;
import SnakeGame.Models.MusicPlayer;
import SnakeGame.Models.SnakeModel;
import SnakeGame.RunnableSceneController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
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

    private FoodModel food;
    public Text endText;
    public Text scoreLabel;
    private SnakeModel snake;

    private boolean gameRunning;
    private ImageView head;

    private ImageView foodImage;
    private Thread gameThread;

    @FXML
    private ImageView endImage;

    private MusicPlayer musicPlayer1;
    private MenuController controller;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameRunning = true;
        snake = new SnakeModel(100, 100);
        head = snake.getImgSnakeHead();
        head.setX(snake.getHeadX());
        head.setY(snake.getHeadY());
        food = new FoodModel();
        System.out.println("playmusic1");
        musicPlayer1 = new MusicPlayer("src/main/resources/frogger.mp3");
        musicPlayer1.play();

        gameThread = new Thread(() -> {
            while (gameRunning) {
                try {
                    drawGame();
                    outofBounds();
                    Thread.sleep(1000/30);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        Platform.runLater(() -> {
            if (snake.isAlive()) {
                createSnake();
                actualize();
                if (food.isAlive()) {
                    drawFood();
                   ImageView newSnakeBody = eaten(snake);
                   if (newSnakeBody != null)
                   {
                       rootPane.getChildren().add(newSnakeBody);
                   }
                } else {
                    deleteFood();
                    food = new FoodModel();
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
        Rectangle foodRect = food.foodRect();

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

    public void deleteFood()
    {
        rootPane.getChildren().remove(foodImage);
    }
    public void drawFood() {
        if (!rootPane.getChildren().contains(foodImage)) {
            foodImage = new ImageView(food.getImage());
            foodImage.setLayoutX(food.getHeadX());
            foodImage.setLayoutY(food.getHeadY());
            rootPane.getChildren().add(foodImage);
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
            ImageView bodyImage = snake.bodyPointImages.get(i);
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
        snake.setAlive(false);
        gameRunning = false;
        endImage.setVisible(true);
        addNameField.setVisible(true);
        addNameButton.setVisible(true);
        endText.setVisible(true);
        head.setVisible(false);
        foodImage.setVisible(false);

        MusicPlayer.stopAllMusic();
    }




    public void submitName(ActionEvent actionEvent) {
        System.out.println(addNameField.getText());
    }

}
