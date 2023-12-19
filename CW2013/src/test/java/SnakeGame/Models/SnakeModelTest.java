package SnakeGame.Models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SnakeModelTest {

    @Test
    void testInitialization() {
        SnakeModel snake = new SnakeModel(0, 0);

        assertTrue(snake.isAlive());
        assertEquals(0, snake.getScore());
        assertEquals(25, snake.getSpeed_XY());
        assertTrue(snake.isRight()); // Assuming the default direction is right
        assertEquals(1, snake.getSnakeLength());
        assertEquals(0, snake.getBodyPoints().size());
    }

}
