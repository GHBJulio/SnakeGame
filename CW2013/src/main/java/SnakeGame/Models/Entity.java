package SnakeGame.Models;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * The abstract class representing entities in the Snake Game.
 * Entities are objects with a position (headX, headY), dimensions (w, h), and an associated image.
 * Provides methods to access and modify the properties of entities, such as position and dimensions.
 * Also includes a method to retrieve the rectangular bounds of the entity for collision detection.
 */
public abstract class Entity {

    /**
     * Default constructor for the Entity class.
     */
    public Entity() {
    }

    /** Indicates whether the entity is alive. */
    public boolean isAlive;

    /** The x-coordinate of the head of the entity. */
    private int headX;

    /** The y-coordinate of the head of the entity. */
    private int headY;

    /** The image associated with the entity. */
    private Image image;

    /** The width of the entity. */
    private int w;

    public void setImage(Image image) {
        this.image = image;
    }

    /** The height of the entity. */
    private int h;

    /**
     * Sets the x-coordinate of the head of the entity.
     *
     * @param headX The new x-coordinate.
     */
    public void setHeadX(int headX) {
        this.headX = headX;
    }

    /**
     * Sets the y-coordinate of the head of the entity.
     *
     * @param headY The new y-coordinate.
     */
    public void setHeadY(int headY) {
        this.headY = headY;
    }

    /**
     * Sets the width of the entity.
     *
     * @param w The new width.
     */
    public void setW(int w) {
        this.w = w;
    }

    /**
     * Sets the height of the entity.
     *
     * @param h The new height.
     */
    public void setH(int h) {
        this.h = h;
    }

    /**
     * Gets the x-coordinate of the head of the entity.
     *
     * @return The x-coordinate.
     */
    public int getHeadX() {
        return headX;
    }

    /**
     * Gets the y-coordinate of the head of the entity.
     *
     * @return The y-coordinate.
     */
    public int getHeadY() {
        return headY;
    }

    /**
     * Gets the image associated with the entity.
     *
     * @return The image.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Gets the width of the entity.
     *
     * @return The width.
     */
    public int getW() {
        return w;
    }

    /**
     * Gets the height of the entity.
     *
     * @return The height.
     */
    public int getH() {
        return h;
    }

    /**
     * Gets a Rectangle representing the bounds of the entity for collision detection.
     *
     * @return The Rectangle bounds.
     */
    public Rectangle getRectangle() {
        return new Rectangle(headX, headY, w, h);
    }
}
