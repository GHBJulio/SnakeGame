package SnakeGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {
    private Model model;
    private View view;

    private final MySnake snake;

    public Controller(Model model) {
        this.model = model;
        this.snake = model.getSnake();
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e)
    {
        //super.keyPressed(e);
        snake.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
