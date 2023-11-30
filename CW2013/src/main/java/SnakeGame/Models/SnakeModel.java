package SnakeGame.Models;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.LinkedList;
import java.util.List;

public class SnakeModel extends Entity {
    // The game changer.
    private int speed_XY;
    public int snakeLength;
    private int num; // not sure why it's here
    public int score = 0;

    private static Image IMG_SNAKE_HEAD = ImageUtil.images.get("snake-head-right");

    public List<Point2D> bodyPoints = new LinkedList<>();
    private boolean isAlive;
    boolean up, down, left, right = true;

    public SnakeModel (int x, int y){

        this.isAlive = true;
        this.headX = x;
        this.headY = y;
        this.image = ImageUtil.images.get("snake-body");
        this.w = (int) image.getWidth();
        this.h = (int) image.getHeight();

        this.speed_XY = 7;
        this.snakeLength = 1;

        this.num = w / speed_XY;
    }

    public Image getBody()
    {
        return image;
    }

    public ImageView getImgSnakeHead() {
        ImageView imageView = new ImageView(IMG_SNAKE_HEAD);
        return imageView;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public int getSpeed_XY() {
        return speed_XY;
    }

    public int getNum() {
        return num;
    }

    public int getScore() {
        return score;
    }

    public List<Point2D> getBodyPoints() {
        return bodyPoints;
    }

    public int getSnakeLength()
    {
        return snakeLength;
    }

    public void changeLength(int length)
    {
        this.snakeLength = length;
    }


}

