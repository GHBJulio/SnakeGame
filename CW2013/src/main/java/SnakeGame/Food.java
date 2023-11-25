package SnakeGame;

import java.awt.Graphics;
import java.util.Random;
import java.awt.Image;


// added new methods to handle generation of random values and image selection
// improving readability & maintainability (Keep)

public class Food extends SnakeObject
{

	private static final long serialVersionUID = -3641221053272056036L;

	public Food()
	{
		this.isAlive = true;
		this.image = getRandomFoodImage();
		this.w = image.getWidth(null);
		this.h = image.getHeight(null);
		this.headX = getRandomXPosition();
		this.headY = getRandomYPosition();
	}

	private Image getRandomFoodImage() {
		Random random = new Random();
		return ImageUtil.images.get(String.valueOf(random.nextInt(10)));
	}

	private int getRandomXPosition() {
		return (int) (Math.random() * (870 - w + 10));
	}

	private int getRandomYPosition() {
		return (int) (Math.random() * (560 - h - 40));
	}


	public void eaten(MySnake mySnake)	{
		if (mySnake.getRectangle().intersects(this.getRectangle()) && isAlive && mySnake.isAlive)		{
			this.isAlive = false;
			mySnake.changeLength(mySnake.getSnakeLength() + 1); // add a variable for the amount it grows
			mySnake.score += 1;  // add a variable for score++
		}
	}
	@Override
	public void draw(Graphics g)
	{
		g.drawImage(image, headX, headY, null);
	}
}
