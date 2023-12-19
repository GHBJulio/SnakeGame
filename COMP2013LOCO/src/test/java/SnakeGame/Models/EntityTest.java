package SnakeGame.Models;

import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    private ConcreteEntity entity;

    @BeforeEach
    void setUp() {
        entity = new ConcreteEntity();
    }

    @Test
    void testInitialValues() {
        assertTrue(entity.isAlive);
        assertEquals(0, entity.getHeadX());
        assertEquals(0, entity.getHeadY());
        assertNull(entity.getImage());
        assertEquals(0, entity.getW());
        assertEquals(0, entity.getH());
    }

    @Test
    void testSettersAndGetters() {
        entity.setHeadX(10);
        entity.setHeadY(20);
        entity.setImage(ImageUtil.getImages().get("22"));
        entity.setW(30);
        entity.setH(40);

        assertEquals(10, entity.getHeadX());
        assertEquals(20, entity.getHeadY());
        assertNotNull(entity.getImage());
        assertEquals(30, entity.getW());
        assertEquals(40, entity.getH());
    }

    @Test
    void testGetRectangle() {
        entity.setHeadX(10);
        entity.setHeadY(20);
        entity.setW(30);
        entity.setH(40);

        Rectangle rectangle = entity.getRectangle();

        assertEquals(10, rectangle.getX());
        assertEquals(20, rectangle.getY());
        assertEquals(30, rectangle.getWidth());
        assertEquals(40, rectangle.getHeight());
    }

    private static class ConcreteEntity extends Entity {
        ConcreteEntity()
        {
            this.isAlive = true;
        }
    }
}
