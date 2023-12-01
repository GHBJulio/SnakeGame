package SnakeGame.Models;


import java.awt.*;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
public class FoodModel extends Entity {

    private boolean isAlive;

    public FoodModel() {
        isAlive = true;
        this.image = getRandomFoodImage();
        this.w = (int) image.getWidth();
        this.h = (int) image.getHeight();
        this.headX = getRandomXPosition();
        this.headY = getRandomYPosition();
    }

    private Image getRandomFoodImage() {
        Random random = new Random();
        return ImageUtil.images.get(String.valueOf(random.nextInt(10)));
    }

    public Rectangle foodRect()
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
