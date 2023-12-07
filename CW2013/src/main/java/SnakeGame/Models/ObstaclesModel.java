package SnakeGame.Models;


import java.awt.*;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
public class ObstaclesModel extends Entity {

    private boolean isAlive;

    public ObstaclesModel() {
        isAlive = true;
        this.image = getRandomObstacleImage();
        this.w = (int) image.getWidth();
        this.h = (int) image.getHeight();
        this.headX = getRandomXPosition();
        this.headY = getRandomYPosition();
    }

    private Image getRandomObstacleImage() {
        Random random = new Random();
        return ImageUtil.images.get("22");
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
