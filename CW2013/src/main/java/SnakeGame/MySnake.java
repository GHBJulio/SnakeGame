package SnakeGame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class MySnake extends SnakeObject {

    // The game changer.
    private int speed_XY;
    private int snakeLength;
    private int num; // not sure why it's here
    public int score = 0;
    private static final BufferedImage IMG_SNAKE_HEAD = (BufferedImage) ImageUtil.images.get("snake-head-right");

    public static List<Point> bodyPoints = new LinkedList<>();

    private static BufferedImage newImgSnakeHead;
    boolean up, down, left, right = true;

    public MySnake(int x, int y)
    {
        this.isAlive = true;
        this.headX = x;
        this.headY = y;
        this.image = ImageUtil.images.get("snake-body");
        this.w = image.getWidth(null);
        this.h = image.getHeight(null);

        this.speed_XY = 5;
        this.snakeLength = 1;

        /*
         * Attention : ?
        */
        this.num = w / speed_XY;
        newImgSnakeHead = IMG_SNAKE_HEAD;

    }

    public int getSnakeLength()
    {
        return snakeLength;
    }

    public void changeLength(int length)
    {
        this.snakeLength = length;
    }

    public void keyPressed(KeyEvent e)
    {
        // check the key
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_UP:
                if (!down)
                {
                    up = true;
                    down = false;
                    left = false;
                    right = false;

                    newImgSnakeHead = (BufferedImage) GameUtil.rotateImage(IMG_SNAKE_HEAD, -90);
                }
                break;

            case KeyEvent.VK_DOWN:
                if (!up)
                {
                    up = false;
                    down = true;
                    left = false;
                    right = false;

                    newImgSnakeHead = (BufferedImage) GameUtil.rotateImage(IMG_SNAKE_HEAD, 90);
                }
                break;

            case KeyEvent.VK_LEFT:
                if (!right)
                {
                    up = false;
                    down = false;
                    left = true;
                    right = false;

                    newImgSnakeHead = (BufferedImage) GameUtil.rotateImage(IMG_SNAKE_HEAD, -180);

                }
                break;

            case KeyEvent.VK_RIGHT:
                if (!left)
                {
                    up = false;
                    down = false;
                    left = false;
                    right = true;

                    newImgSnakeHead = IMG_SNAKE_HEAD;
                }

            default:
                break;
        }
    }


    public void move()
    {
        // make the snake move
        if (up)
        {
            headY -= speed_XY;
        } else if (down)
        {
            headY += speed_XY;
        } else if (left)
        {
            headX -= speed_XY;
        } else if (right)
        {
            headX += speed_XY;
        }
    }


    @Override
    public void draw(Graphics g)
    {
        outofBounds();
        eatBody();

        bodyPoints.add(new Point(headX, headY));

        if (bodyPoints.size() == (this.snakeLength + 1) * num)
        {
            bodyPoints.remove(0);
        }
        g.drawImage(newImgSnakeHead, headX, headY, null);
        drawBody(g);

        move();
    }

    public void eatBody()
    {
        if (snakeLength > 1){ // can't eat itself if it only has a head or length == 1.
            for (Point point : bodyPoints) {
                for (Point point2 : bodyPoints) {
                    if (point.equals(point2) && point != point2) {
                        this.isAlive = false;
                    }
                }
            }
        }
    }

    public void drawBody(Graphics g)
    {
        int length = bodyPoints.size() - 1 - num;

        for (int i = length; i >= num; i -= num)
        {
            Point point = bodyPoints.get(i);
            g.drawImage(this.image, point.x, point.y, null);
        }
    }

    private void outofBounds()
    {
        boolean xOut = (headX <= 0 || headX >= (870 - w));
        boolean yOut = (headY <= 0 || headY >= (560 - h)); // updated version = boolean yOut = (y <= 0 || y >= (560 - h));
        // old code yOut wasn't working properly as snake would not be able to eat food on top of the screen
        if (xOut || yOut)
        {
            isAlive = false;
        }
    }
}

