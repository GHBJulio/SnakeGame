package SnakeGame.Controllers;

import SnakeGame.Models.ItemsModel;
import SnakeGame.Models.MusicPlayer;
import SnakeGame.Models.SnakeModel;
import SnakeGame.RunnableSceneController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;

public class ItemsController extends RunnableSceneController{

    public ItemsModel food;
    public ItemsModel obstacle;
    public ItemsModel slowMo;
    public ItemsModel fastSpeed;
    public ItemsModel doublePoints;
    public GameController controller;
    private ImageView obstacleImage;
    private ImageView slowImage;
    private ImageView foodImage;
    public ItemsController()
    {
        food = new ItemsModel();
        obstacle = new ItemsModel();
        slowMo = new ItemsModel();
        fastSpeed = new ItemsModel();
        doublePoints = new ItemsModel();
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

    public void slowMoHit(SnakeModel mySnake)	{
        javafx.scene.shape.Rectangle snakeRect = mySnake.getRectangle();
        Rectangle slowRect = slowMo.ItemRectangle();

        if (slowRect.getBoundsInParent().intersects(snakeRect.getBoundsInParent()) && slowMo.isAlive() && mySnake.isAlive()) {
            MusicPlayer.getMusicPlay("src/main/resources/slowMotion.mp3", false);
            slowMo.setAlive(false);
            System.out.println("SLOW MOTION");
            controller.scoreMin += 10;
            controller.speedFactor = 0.5;
            // Adjust the duration as needed
            long doublePointsDuration = 5400;  // 5 seconds in milliseconds

            // Callback function when the timer expires
            EventHandler<ActionEvent> callback = event -> controller.speedFactor = 1.0;

            // Start the timer
            startTimer(doublePointsDuration, callback);

        }
    }

    public void fastSpeedHit(SnakeModel mySnake)	{
        javafx.scene.shape.Rectangle snakeRect = mySnake.getRectangle();
        Rectangle fastRect = fastSpeed.ItemRectangle();

        if (fastRect.getBoundsInParent().intersects(snakeRect.getBoundsInParent()) && fastSpeed.isAlive() && mySnake.isAlive()) {
            //  MusicPlayer.getMusicPlay("src/main/resources/snakeEat.mp3", false); // another code for slowmo
            fastSpeed.setAlive(false);
            System.out.println("GO FAST");
            controller.scoreMin += 10;
            controller.speedFactor = 2.0;
            long doublePointsDuration = 3000;  // 5 seconds in milliseconds

            // Callback function when the timer expires
            EventHandler<ActionEvent> callback = event -> controller.speedFactor = 1.0;

            // Start the timer
            startTimer(doublePointsDuration, callback);

        }
    }

    public void obstacleHit(SnakeModel mySnake)	{
        javafx.scene.shape.Rectangle snakeRect = mySnake.getRectangle();
        Rectangle obstacleRect = obstacle.ItemRectangle();

        if (obstacleRect.getBoundsInParent().intersects(snakeRect.getBoundsInParent()) && obstacle.isAlive() && mySnake.isAlive()) {
            MusicPlayer.getMusicPlay("src/main/resources/snakeHit.mp3", false);
            obstacle.setAlive(false);
            int lastIndexImage = mySnake.bodyPointImages.size() - 1;
            int lastIndex = mySnake.bodyPoints.size() - 1;
            System.out.println(mySnake.getSnakeLength());


            // Check if the snake's length is greater than 1
            if (mySnake.getSnakeLength() - 1 >= 1) {
                mySnake.setSpeed_XY(7);
                mySnake.changeLength(mySnake.getSnakeLength() - 1);
                mySnake.bodyPoints.remove(lastIndex);
                mySnake.setScore(mySnake.getScore() - 1);
                // Remove the oldest bodyImage
                ImageView removedBodyImage = mySnake.bodyPointImages.remove(lastIndexImage);
                // Remove it from the rootPane (or your container)
                controller.rootPane.getChildren().remove(removedBodyImage);
            }
            else if (mySnake.getSnakeLength() - 1 < 1)
            {
                controller.gameEnded();
            }
        }
    }

    public void drawObstacle() {
        if (!controller.rootPane.getChildren().contains(obstacleImage)) {
            obstacleImage = new ImageView(obstacle.getObstacleImage());
            obstacleImage.setLayoutX(obstacle.getHeadX());
            obstacleImage.setLayoutY(obstacle.getHeadY());
            controller.rootPane.getChildren().add(obstacleImage);
        }
    }

    public void drawSloMo() {
        if (!controller.rootPane.getChildren().contains(slowImage)) {
            slowImage = new ImageView(slowMo.getSlowMoImage());
            slowImage.setLayoutX(slowMo.getHeadX());
            slowImage.setLayoutY(slowMo.getHeadY());
            controller.rootPane.getChildren().add(slowImage);
        }
    }

    public void drawFood() {
        if (!controller.rootPane.getChildren().contains(foodImage)) {
            foodImage = new ImageView(food.getImage());
            foodImage.setLayoutX(food.getHeadX());
            foodImage.setLayoutY(food.getHeadY());
            controller.rootPane.getChildren().add(foodImage);
        }
    }

    public void deleteFood()
    {
        controller.rootPane.getChildren().remove(foodImage);
    }

    public void deleteSlow()
    {
        controller.rootPane.getChildren().remove(slowImage);
    }

    public void deleteObstacle()
    {
        controller.rootPane.getChildren().remove(obstacleImage);
    }



    // General-purpose timer function
    private void startTimer(long duration, EventHandler<ActionEvent> callback) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(duration), callback));
        timeline.play();
    }
}
