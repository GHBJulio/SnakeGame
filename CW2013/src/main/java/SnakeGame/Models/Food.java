package SnakeGame.Models;

import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;


public class Food extends Entity {
	private static final long serialVersionUID = -3641221053272056036L;

	public Food() {
		this.isAlive = true;
		this.image = getRandomFoodImage();
		this.w = (int) image.getWidth();
		this.h = (int) image.getHeight();
		this.headX = getRandomXPosition();
		this.headY = getRandomYPosition();
	}


	private Image getRandomFoodImage() {
		Random random = new Random();
		return ImageUtil.images.get(String.valueOf(random.nextInt(10)));
	}

	private int getRandomXPosition() {
		return (int) (Math.random() * (800 - w + 10));
	}

	private int getRandomYPosition() {
		return (int) (Math.random() * (500 - h - 40));
	}

	public void eaten(SnakeModel mySnake)	{
		Rectangle snakeRect = mySnake.getRectangle();
		Rectangle foodRect = getRectangle();

		if (foodRect.getBoundsInParent().intersects(snakeRect.getBoundsInParent()) && isAlive && mySnake.isAlive()) {
			this.isAlive = false;
			mySnake.changeLength(mySnake.getSnakeLength() + 1);
			mySnake.setScore(mySnake.getScore() + 1);
		}
	}
}


