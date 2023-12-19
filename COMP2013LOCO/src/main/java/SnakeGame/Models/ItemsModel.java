package SnakeGame.Models;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * The {@code ItemsModel} class represents items in the SnakeGame, such as food and obstacles.
 * It provides methods to generate random items, get their images, and manage their positions and status.
 */
public class ItemsModel extends Entity {

    private boolean isAlive;
    private Image imageObstacle;
    private int newRandom;
    private int previousRandom;

    /**
     * Constructs a new instance of the {@code ItemsModel} class.
     * Initializes item properties such as image, position, and status.
     */
    public ItemsModel() {
        newRandom = 0;
        previousRandom = 0;
        isAlive = true;
        imageObstacle = getRandomObstacle();
        this.setImage(getRandomFoodImage());
        this.setW((int) getImage().getWidth());
        this.setH((int) getImage().getHeight());
        this.setHeadX(getRandomXPosition());
        this.setHeadY(getRandomYPosition());
    }

    /**
     * Gets the image representing the slow motion item.
     *
     * @return The slow motion item image.
     */
    public Image getSlowMoImage() {
        return ImageUtil.getImages().get("25");
    }

    /**
     * Gets the image representing the fast speed item.
     *
     * @return The fast speed item image.
     */
    public Image getFastSpeedImage() {
        return ImageUtil.getImages().get("26");
    }

    /**
     * Gets the image representing the double points item.
     *
     * @return The double points item image.
     */
    public Image getDoublePointsImage() {
        return ImageUtil.getImages().get("27");
    }

    /**
     * Gets a random image representing the obstacle item.
     *
     * @return The random obstacle item image.
     */
    public Image getRandomObstacle() {
        Random random = new Random();
        while (newRandom == previousRandom) {
            newRandom = random.nextInt(3) + 22;
        }
        previousRandom = newRandom;
        return ImageUtil.getImages().get(String.valueOf(newRandom));
    }

    /**
     * Gets a random image representing the food item.
     *
     * @return The random food item image.
     */
    public Image getRandomFoodImage() {
        Random random = new Random();
        return ImageUtil.getImages().get(String.valueOf(random.nextInt(16)));
    }

    /**
     * Gets the image representing the obstacle item.
     *
     * @return The obstacle item image.
     */
    public Image getObstacleImage() {
        return imageObstacle;
    }

    /**
     * Gets the rectangle representing the item.
     *
     * @return The item rectangle.
     */
    public Rectangle ItemRectangle() {
        return getRectangle();
    }

    /**
     * Gets a random X position
     *
     * @return The random X position
     */
    public int getRandomXPosition() {
        return (int) (Math.random() * (800 - getW() + 10));
    }

    /**
     * Gets a random Y position
     *
     * @return The random Y position
     */
    public int getRandomYPosition() {
        return (int) (Math.random() * (500 - getH() - 40));
    }

    /**
     * Checks if the item is alive.
     *
     * @return {@code true} if the item is alive, {@code false} otherwise.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the status of the item.
     *
     * @param setAlive The status to set for the item.
     * @return The updated status of the item.
     */
    public boolean setAlive(boolean setAlive) {
        return isAlive = setAlive;
    }
}
