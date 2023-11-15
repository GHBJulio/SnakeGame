package SnakeGame;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

// well implemented class (add more images) (keep)
// error handling needed.
public class  ImageUtil
{
	public static Map<String, Image> images = new HashMap<>();

	static
	{
		// snake
		images.put("snake-head-right", GameUtil.getImage("SnakeGame/snake-head-right.png"));
		images.put("snake-body", GameUtil.getImage("SnakeGame/snake-body.png"));
		// obstacles
		images.put("0", GameUtil.getImage("SnakeGame/food-kiwi.png"));
		images.put("1", GameUtil.getImage("SnakeGame/food-lemon.png"));
		images.put("2", GameUtil.getImage("SnakeGame/food-litchi.png"));
		images.put("3", GameUtil.getImage("SnakeGame/food-mango.png"));
		images.put("4", GameUtil.getImage("SnakeGame/food-apple.png"));
		images.put("5", GameUtil.getImage("SnakeGame/food-banana.png"));
		images.put("6", GameUtil.getImage("SnakeGame/food-blueberry.png"));
		images.put("7", GameUtil.getImage("SnakeGame/food-cherry.png"));
		images.put("8", GameUtil.getImage("SnakeGame/food-durian.png"));
		images.put("9", GameUtil.getImage("SnakeGame/food-grape.png"));
		images.put("10", GameUtil.getImage("SnakeGame/food-grapefruit.png"));
		images.put("11", GameUtil.getImage("SnakeGame/food-peach.png"));
		images.put("12", GameUtil.getImage("SnakeGame/food-pear.png"));
		images.put("13", GameUtil.getImage("SnakeGame/food-orange.png"));
		images.put("14", GameUtil.getImage("SnakeGame/food-pineapple.png"));
		images.put("15", GameUtil.getImage("SnakeGame/food-strawberry.png"));
		images.put("16", GameUtil.getImage("SnakeGame/food-watermelon.png"));
		images.put("UI-background", GameUtil.getImage("SnakeGame/UI-background.png"));
		images.put("game-scene-01", GameUtil.getImage("SnakeGame/game-scene-01.jpg"));
	}
}