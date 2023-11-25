package SnakeGame;

import java.awt.*;

public abstract class SnakeObject
{
    int headX;
    int headY;
    Image image;
    int w;
    int h;

    public boolean isAlive;
    public abstract void draw(Graphics g);

    public Rectangle getRectangle()
    {
        return new Rectangle(headX, headY, w, h);
    }
}
