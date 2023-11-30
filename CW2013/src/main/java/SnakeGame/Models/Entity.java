package SnakeGame.Models;

import javafx.scene.image.Image;

import javafx.scene.shape.Rectangle;

public abstract class Entity
{
    public boolean isAlive;
    int headX;
    int headY;
    Image image;
    int w;
    int h;

    public void setHeadX(int headX) {
        this.headX = headX;
    }

    public void setHeadY(int headY) {
        this.headY = headY;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getHeadX() {
        return headX;
    }

    public int getHeadY() {
        return headY;
    }

    public Image getImage() {
        return image;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

        public Rectangle getRectangle()
       {
            return new Rectangle(headX, headY, w, h);
        }
}
