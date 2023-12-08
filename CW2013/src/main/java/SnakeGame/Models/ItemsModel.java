package SnakeGame.Models;


import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
public class ItemsModel extends Entity {

    private boolean isAlive;

    private Image imageObstacle;

    public ItemsModel() {
        isAlive = true;
        imageObstacle = getRandomObstacle();
        this.image = getRandomFoodImage();
        this.w = (int) image.getWidth();
        this.h = (int) image.getHeight();
        this.headX = getRandomXPosition();
        this.headY = getRandomYPosition();
    }

    private Image getRandomObstacle() {
        Random random = new Random();
        return ImageUtil.images.get("22");
    }

    private Image getRandomFoodImage() {
        Random random = new Random();
        return ImageUtil.images.get(String.valueOf(random.nextInt(16)));
    }

    public Image getObstacleImage()
    {
        return imageObstacle;
    }
    public Rectangle ItemRectangle()
    {
       return getRectangle();
    }

    private int getRandomXPosition() {
        return (int) (Math.random() * (800 - w + 10));
    }

    private int getRandomYPosition() {
        return (int) (Math.random() * (500 - h - 40));
    }

    public boolean isAlive()
    {
        return isAlive;
    }

    public boolean setAlive(boolean setAlive)
    {
       return isAlive = setAlive;
    }
}
