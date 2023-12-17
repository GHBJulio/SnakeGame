package SnakeGame.Models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * The SnakeModel class represents the snake entity in the Snake Game, storing information
 * such as its position, length, speed, and other attributes. It also provides methods to
 * manipulate and retrieve information about the snake.
 *
 * @author Guilherme Julio
 */
public class SnakeModel extends Entity {
    /** The speed of the snake in both X and Y directions. */
    private int speed_XY;

    /** The length of the snake. */
    private int snakeLength;

    private int num;

    /** The score achieved by the snake. */
    private int score = 0;

    /** The image representing the snake's head. */
    private static Image IMG_SNAKE_HEAD = ImageUtil.getImages().get("snake-head-right");

    /** The list of points representing the body segments of the snake. */
    private List<Point> bodyPoints = new LinkedList<>();

    /** The list of ImageViews representing the graphical body segments of the snake. */
    private List<ImageView> bodyPointImages = new LinkedList<>();
    /** Flag indicating whether the snake is alive. */
    private boolean isAlive;

    /** Flags indicating the direction of the snake. */
    private boolean up, down, left, right = true;

    /**
     * Constructs a SnakeModel with the specified initial position.
     *
     * @param x The initial X-coordinate of the snake.
     * @param y The initial Y-coordinate of the snake.
     */
    public SnakeModel(int x, int y) {
        this.isAlive = true;
        this.setHeadX(x);
        this.setHeadY(y);
        this.setImage(ImageUtil.getImages().get("snake-body"));
        this.setW((int) getImage().getWidth());
        this.setH((int) getImage().getHeight());
        this.speed_XY = 2;
        this.snakeLength = 1;
        this.num = getW() / speed_XY;
    }

    /**
     * Gets the image representing the body of the snake.
     *
     * @return The image of the snake's body.
     */
    public Image getBody() {
        return getImage();
    }

    /**
     * Gets an ImageView representing the snake's head.
     *
     * @return The ImageView of the snake's head.
     */
    public ImageView getImgSnakeHead() {
        ImageView imageView = new ImageView(IMG_SNAKE_HEAD);
        return imageView;
    }

    /**
     * Gets an ImageView representing the snake's body.
     * <p>
     * set The points of the snake's body.
     */
    public void setBodyPointImages(List<ImageView> bodyPointImages) {
        this.bodyPointImages = bodyPointImages;
    }
    /**
     * Gets an ImageView representing the snake's body.
     *
     * @return The bodyPoint ImageView of the snake's body.
     */
    public List<ImageView> getBodyPointImages() {
        return bodyPointImages;
    }

    /**
     * Sets the score of the snake.
     *
     * @param score The score to set.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Sets the speed of the snake in both X and Y directions.
     *
     * @param speed_XY The speed to set.
     */
    public void setSpeed_XY(int speed_XY) {
        this.speed_XY = speed_XY;
    }

    /**
     * Sets the alive status of the snake.
     *
     * @param alive The alive status to set.
     */
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    /**
     * Sets the upward movement direction of the snake.
     *
     * @param up The upward direction flag.
     */
    public void setUp(boolean up) {
        this.up = up;
    }

    /**
     * Sets the downward movement direction of the snake.
     *
     * @param down The downward direction flag.
     */
    public void setDown(boolean down) {
        this.down = down;
    }

    /**
     * Sets the leftward movement direction of the snake.
     *
     * @param left The leftward direction flag.
     */
    public void setLeft(boolean left) {
        this.left = left;
    }

    /**
     * Sets the rightward movement direction of the snake.
     *
     * @param right The rightward direction flag.
     */
    public void setRight(boolean right) {
        this.right = right;
    }

    /**
     * Checks if the snake is alive.
     *
     * @return True if the snake is alive, false otherwise.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Checks if the snake is moving upwards.
     *
     * @return True if the snake is moving upwards, false otherwise.
     */
    public boolean isUp() {
        return up;
    }

    /**
     * Checks if the snake is moving downwards.
     *
     * @return True if the snake is moving downwards, false otherwise.
     */
    public boolean isDown() {
        return down;
    }

    /**
     * Checks if the snake is moving leftwards.
     *
     * @return True if the snake is moving leftwards, false otherwise.
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * Checks if the snake is moving rightwards.
     *
     * @return True if the snake is moving rightwards, false otherwise.
     */
    public boolean isRight() {
        return right;
    }

    /**
     * Gets the speed of the snake in both X and Y directions.
     *
     * @return The speed of the snake.
     */
    public int getSpeed_XY() {
        return speed_XY;
    }

    /**
     * Gets the number attribute.
     *
     * @return The value of the number attribute.
     */
    public int getNum() {
        return num;
    }

    /**
     * Gets the score of the snake.
     *
     * @return The score of the snake.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the list of body points representing the snake's body.
     *
     * @return The list of body points.
     */
    public List<Point> getBodyPoints() {
        return bodyPoints;
    }

    /**
     * Gets the length of the snake.
     *
     * @return The length of the snake.
     */
    public int getSnakeLength() {
        return snakeLength;
    }

    /**
     * Changes the length of the snake.
     *
     * @param length The new length of the snake.
     */
    public void changeLength(int length) {
        this.snakeLength = length;
    }
}
