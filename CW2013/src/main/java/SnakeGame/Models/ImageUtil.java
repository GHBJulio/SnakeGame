package SnakeGame.Models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for managing and retrieving images used in the Snake Game.
 * Provides a collection of images identified by string keys.
 * The images are loaded using the class's ClassLoader from resource files.
 * Handles errors by printing an error message and stack trace when an image fails to load.
 * @author Guilherme Julio
 */
public class  ImageUtil {

	/** Private constructor to prevent the creation of instances */
	private ImageUtil() {}
	/** A map to store images with corresponding string keys. */
	public static Map<String, Image> images = new HashMap<>();



	static {
		// snake
		images.put("snake-head-right", getImage("snake-head-right.png"));
		images.put("snake-body", getImage("snake-body.png"));
		// food
		images.put("0", getImage("food-kiwi.png"));
		images.put("1", getImage("food-lemon.png"));
		images.put("2", getImage("food-litchi.png"));
		images.put("3", getImage("food-mango.png"));
		images.put("4", getImage("food-apple.png"));
		images.put("5", getImage("food-banana.png"));
		images.put("6", getImage("food-blueberry.png"));
		images.put("7", getImage("food-cherry.png"));
		images.put("8", getImage("food-durian.png"));
		images.put("9", getImage("food-grape.png"));
		images.put("10", getImage("food-grapefruit.png"));
		images.put("11", getImage("food-peach.png"));
		images.put("12", getImage("food-pear.png"));
		images.put("13", getImage("food-orange.png"));
		images.put("14", getImage("food-pineapple.png"));
		images.put("15", getImage("food-strawberry.png"));
		images.put("16", getImage("food-watermelon.png"));
		images.put("17", getImage("UI-background.png"));
		images.put("18", getImage("UI-background12.jpg"));
		images.put("19", getImage("UI-background2.png"));
		images.put("20", getImage("UI-background3.jpg"));
		images.put("21", getImage("UI-background4.jpg"));
		images.put("22", getImage("bomb.png"));
		images.put("23", getImage("bomb1.png"));
		images.put("24", getImage("bonfire.png"));
		images.put("25", getImage("turtle.png"));
		images.put("26", getImage("fast-time.png"));
		images.put("27", getImage("double-point.png"));
		images.put("28", getImage("double-point.png"));
		images.put("29", getImage("double-point.png"));
		images.put("game-scene-01", getImage("game-scene-01.jpg"));
	}


	/**
	 * Retrieves the Image associated with the specified imagePath.
	 * Loads the image from the resource files using the ClassLoader.
	 * Handles errors by printing an error message and stack trace when the image fails to load.
	 *
	 * @param imagePath The path to the image resource.
	 * @return The Image object, or null if the image fails to load.
	 */

	public static Image getImage(String imagePath) {
		try {
			return new Image(ImageUtil.class.getClassLoader().getResourceAsStream(imagePath));
		} catch (Exception e) {
			System.err.println("ERROR: Failed to load image: " + imagePath);
			e.printStackTrace();
			return null;
		}
	}

	}
