package SnakeGame.Controllers;

import SnakeGame.Models.ItemsModel;
import SnakeGame.Models.MusicPlayer;
import SnakeGame.Models.SnakeModel;
import SnakeGame.RunnableSceneController;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;

/**
 * Controller class for managing game items such as food, obstacles, and power-ups.
 * Extends RunnableSceneController for handling scene transitions.
 * Handles item interactions with the snake and updates the UI accordingly.
 *
 * @author Guilherme Julio
 */
public class ItemsController extends RunnableSceneController {
    /**
     * The ItemsModel representing the power-up item for additional food.
     */
    public ItemsModel food;
    /**
     * The ItemsModel representing an obstacle in the game.
     */
    public ItemsModel obstacle;
    /**
     * The ItemsModel representing the power-up item for slow motion.
     */
    public ItemsModel slowMo;
    /**
     * The ItemsModel representing the power-up item for fast speed.
     */
    public ItemsModel fastSpeed;
    /**
     * The ItemsModel representing the power-up item for double points.
     */
    public ItemsModel doublePoints;
    /**
     * The GameController associated with this ItemsController.
     * It is used to perform certain actions or obtain information from the game controller.
     */
    public GameController controller;
    private ImageView obstacleImage;
    private ImageView slowImage;
    private ImageView fastImage;
    private ImageView foodImage;
    private ImageView doublePointsImage;

    private Timeline timeline;
    /**
     * Flag indicating whether the double points power-up is active.
     */
    public boolean isDoublePoints;


    /**
     * Constructs an ItemsController, initializing item models.
     */
    public ItemsController() {
        isDoublePoints = false;
        food = new ItemsModel();
        obstacle = new ItemsModel();
        slowMo = new ItemsModel();
        fastSpeed = new ItemsModel();
        doublePoints = new ItemsModel();
        MenuController.items = this;
    }

    /**
     * Handles the event when the snake eats the food item.
     * Increases the snake's length and updates the score.
     *
     * @param mySnake The snake model.
     */
    public void eaten(SnakeModel mySnake) {
        Rectangle snakeRect = mySnake.getRectangle();
        Rectangle foodRect = food.ItemRectangle();

        if (foodRect.getBoundsInParent().intersects(snakeRect.getBoundsInParent()) && food.isAlive() && mySnake.isAlive()) {
            MusicPlayer.getMusicPlay("src/main/resources/snakeEat.mp3", false);
            food.setAlive(false);
            ImageView bodyImage = new ImageView(mySnake.getBody());
            ImageView bodyImage2 = new ImageView(mySnake.getBody());
            if (!isDoublePoints) {
                mySnake.getBodyPoints().add(new Point(mySnake.getHeadX(), mySnake.getHeadY()));
                mySnake.getBodyPointImages().add(bodyImage);
                mySnake.changeLength(mySnake.getSnakeLength() + 1);
                mySnake.setScore(mySnake.getScore() + 1);
                controller.rootPane.getChildren().add(bodyImage);
            } else {
                mySnake.getBodyPoints().add(new Point(mySnake.getHeadX(), mySnake.getHeadY()));
                mySnake.getBodyPointImages().add(bodyImage);
                controller.rootPane.getChildren().add(bodyImage);
                mySnake.getBodyPoints().add(new Point(mySnake.getHeadX(), mySnake.getHeadY()));
                mySnake.getBodyPointImages().add(bodyImage2);
                controller.rootPane.getChildren().add(bodyImage2);
                mySnake.changeLength(mySnake.getSnakeLength() + 2);
                mySnake.setScore(mySnake.getScore() + 2);
            }
        }
    }

    /**
     * Handles the event when the snake collides with the slow motion power-up.
     * Slows down the game for a duration.
     *
     * @param mySnake The snake model.
     */
    public void slowMoHit(SnakeModel mySnake) {
        Rectangle snakeRect = mySnake.getRectangle();
        Rectangle slowRect = slowMo.ItemRectangle();

        if (slowRect.getBoundsInParent().intersects(snakeRect.getBoundsInParent()) && slowMo.isAlive() && mySnake.isAlive()) {
            MusicPlayer.getMusicPlay("src/main/resources/slowMotion.mp3", false);
            slowMo.setAlive(false);
            System.out.println("SLOW MOTION");
            controller.scoreMin += 7;
            controller.speedFactor = 0.5;

            // Adjust the duration as needed
            long doublePointsDuration = 5400;  // 5 seconds in milliseconds

            // Callback function when the timer expires
            EventHandler<ActionEvent> callback = event -> controller.speedFactor = 1.0;

            // Start the timer
            startTimer(doublePointsDuration, callback);
        }
    }

    /**
     * Handles the event when the snake collides with the double points power-up.
     * Doubles the points earned for a duration.
     *
     * @param mySnake The snake model.
     */
    public void doublePointsHit(SnakeModel mySnake) {
        Rectangle snakeRect = mySnake.getRectangle();
        Rectangle doubleRect = doublePoints.ItemRectangle();

        if (doubleRect.getBoundsInParent().intersects(snakeRect.getBoundsInParent()) && doublePoints.isAlive() && mySnake.isAlive()) {
            MusicPlayer.getMusicPlay("src/main/resources/doublePoints.mp3", false);
            doublePoints.setAlive(false);
            isDoublePoints = true;
            System.out.println("DOUBLE POINTS");
            controller.scoreMin += 5;

            long doublePointsDuration = 6200;  // 6.2 seconds in milliseconds

            // Callback function when the timer expires
            EventHandler<ActionEvent> callback = event -> isDoublePoints = false;

            // Start the timer
            startTimer(doublePointsDuration, callback);
        }
    }

    /**
     * Handles the event when the snake collides with the fast speed power-up.
     * Increases the game speed for a duration.
     *
     * @param mySnake The snake model.
     */
    public void fastSpeedHit(SnakeModel mySnake) {
        Rectangle snakeRect = mySnake.getRectangle();
        Rectangle fastRect = fastSpeed.ItemRectangle();

        if (fastRect.getBoundsInParent().intersects(snakeRect.getBoundsInParent()) && fastSpeed.isAlive() && mySnake.isAlive()) {
            MusicPlayer.getMusicPlay("src/main/resources/boost.mp3", false);
            System.out.println("GO FAST");
            controller.scoreMin += 5;
            controller.speedFactor = 2.0;
            long doublePointsDuration = 6000;  // 5 seconds in milliseconds

            // Callback function when the timer expires
            EventHandler<ActionEvent> callback = event -> controller.speedFactor = 1.0;

            // Start the timer
            startTimer(doublePointsDuration, callback);
        }
    }

    /**
     * Handles the event when the snake collides with the obstacle.
     * Reduces the snake's length upon collision.
     *
     * @param mySnake The snake model.
     */
    public void obstacleHit(SnakeModel mySnake) {
        Rectangle snakeRect = mySnake.getRectangle();
        Rectangle obstacleRect = obstacle.ItemRectangle();

        if (obstacleRect.getBoundsInParent().intersects(snakeRect.getBoundsInParent()) && obstacle.isAlive() && mySnake.isAlive()) {
            MusicPlayer.getMusicPlay("src/main/resources/snakeHit.mp3", false);
            obstacle.setAlive(false);
            int lastIndexImage = mySnake.getBodyPointImages().size() - 1;
            int lastIndex = mySnake.getBodyPoints().size() - 1;
            System.out.println(mySnake.getSnakeLength());

            // Check if the snake's length is greater than 1
            if (mySnake.getSnakeLength() - 1 >= 1) {
                mySnake.setSpeed_XY(7);
                mySnake.changeLength(mySnake.getSnakeLength() - 1);
                mySnake.getBodyPoints().remove(lastIndex);
                mySnake.setScore(mySnake.getScore() - 1);

                // Remove the oldest bodyImage
                ImageView removedBodyImage = mySnake.getBodyPointImages().remove(lastIndexImage);

                // Remove it from the rootPane (or your container)
                controller.rootPane.getChildren().remove(removedBodyImage);
            } else if (mySnake.getSnakeLength() - 1 < 1) {
                controller.gameEnded();
            }
        }
    }

    /**
     * Draws the obstacle on the game UI.
     */
    public void drawObstacle() {
        if (!controller.rootPane.getChildren().contains(obstacleImage)) {
            obstacleImage = new ImageView(obstacle.getObstacleImage());
            obstacleImage.setLayoutX(obstacle.getHeadX());
            obstacleImage.setLayoutY(obstacle.getHeadY());
            controller.rootPane.getChildren().add(obstacleImage);
        }
    }

    /**
     * Draws the double points power-up on the game UI.
     */
    public void drawDoublePoints() {
        if (!controller.rootPane.getChildren().contains(doublePointsImage)) {
            doublePointsImage = new ImageView(doublePoints.getDoublePointsImage());
            doublePointsImage.setLayoutX(doublePoints.getHeadX());
            doublePointsImage.setLayoutY(doublePoints.getHeadY());
            controller.rootPane.getChildren().add(doublePointsImage);
        }
    }

    /**
     * Draws the slow motion power-up on the game UI.
     */
    public void drawSloMo() {
        if (!controller.rootPane.getChildren().contains(slowImage)) {
            slowImage = new ImageView(slowMo.getSlowMoImage());
            slowImage.setLayoutX(slowMo.getHeadX());
            slowImage.setLayoutY(slowMo.getHeadY());
            controller.rootPane.getChildren().add(slowImage);
        }
    }

    /**
     * Draws the fast speed power-up on the game UI.
     */
    public void drawFastSpeed() {
        if (!controller.rootPane.getChildren().contains(fastImage)) {
            fastImage = new ImageView(fastSpeed.getFastSpeedImage());
            fastImage.setLayoutX(fastSpeed.getHeadX());
            fastImage.setLayoutY(fastSpeed.getHeadY());
            controller.rootPane.getChildren().add(fastImage);
        }
    }

    /**
     * Draws the food item on the game UI.
     */
    public void drawFood() {
        if (!controller.rootPane.getChildren().contains(foodImage)) {
            foodImage = new ImageView(food.getImage());
            foodImage.setLayoutX(food.getHeadX());
            foodImage.setLayoutY(food.getHeadY());
            controller.rootPane.getChildren().add(foodImage);
        }
    }

    /**
     * Deletes the food item from the game UI.
     */
    public void deleteFood() {
        controller.rootPane.getChildren().remove(foodImage);
    }

    /**
     * Deletes the slow motion power-up from the game UI.
     */
    public void deleteSlow() {
        controller.rootPane.getChildren().remove(slowImage);
    }

    /**
     * Deletes the fast speed power-up from the game UI.
     */
    public void deleteFastSpeed() {
        controller.rootPane.getChildren().remove(fastImage);
    }

    /**
     * Deletes the obstacle from the game UI.
     */
    public void deleteObstacle() {
        controller.rootPane.getChildren().remove(obstacleImage);
    }

    /**
     * Deletes the double points power-up from the game UI.
     */
    public void deleteDoublePoints() {
        controller.rootPane.getChildren().remove(doublePointsImage);
    }

    /**
     * Starts a general-purpose timer.
     *
     * @param duration The duration of the timer in milliseconds.
     * @param callback The callback function to be executed when the timer expires.
     */
    private void startTimer(long duration, EventHandler<ActionEvent> callback) {
        // Create a timeline with the specified duration
        timeline = new Timeline(new KeyFrame(Duration.millis(duration), callback));

        // Set cycle count to indefinite to keep it running
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Play the timeline
        timeline.play();
    }

    // Call this method when the game is paused
    void pauseGame() {
        if (timeline != null) {
            // Pause the timeline
            System.out.println("I'VE PAUSED");
            timeline.pause();
        }
    }

    // Call this method when the game is resumed
    void resumeGame() {
        if (timeline != null) {
            // Resume the timeline
            System.out.println("I'VE RESUMED");
            timeline.play();
        }
    }
}
