package SnakeGame.Models;


import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
public class ItemsModel extends Entity {

    private boolean isAlive;
    private Image imageObstacle;
    private Image imageSlowMo;
    private int newRandom;
    private int previousRandom;

    public ItemsModel() {
        newRandom = 0;
        previousRandom = 0;
        isAlive = true;
        imageObstacle = getRandomObstacle();
        imageSlowMo = getSlowMoImage();
        this.image = getRandomFoodImage();
        this.w = (int) image.getWidth();
        this.h = (int) image.getHeight();
        this.headX = getRandomXPosition();
        this.headY = getRandomYPosition();
    }

    public Image getSlowMoImage() {
        return ImageUtil.images.get("25");
    }

    private Image getRandomObstacle() {
        Random random = new Random();
        while(newRandom == previousRandom) {
            newRandom = random.nextInt(3) + 22;
        }
        previousRandom = newRandom;
        return ImageUtil.images.get(String.valueOf(newRandom));
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
