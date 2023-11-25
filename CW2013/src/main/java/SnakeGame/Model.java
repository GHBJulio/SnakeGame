package SnakeGame;

import java.awt.*;

public class Model {
    private MySnake snake;

    private Food food;

    public Model()
    {
        // initialize snake with x y directions
        snake = new MySnake(100,100);
        food = new Food();
    }

    public MySnake getSnake()
    {
        return snake;
    }

    public boolean drawGame(Graphics g) {
        // Determine the state of the game.
        if (snake.isAlive) {
            snake.draw(g);
            if (food.isAlive) {
                food.draw(g);
                food.eaten(snake);
            } else {
                food = new Food();
            }
        } else {
            MusicPlayer.stopMusic("src/main/resources/frogger.mp3");
            return false;
        }
        return true;
    }

}
