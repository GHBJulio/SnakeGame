package SnakeGame;

import java.awt.Graphics;
import java.util.Random;
import java.awt.Image;


// added new methods to handle generation of random values and image selection
// improving readability & maintainability (Keep)

public class Food extends MyFrame.SnakeObject
{

	private static final long serialVersionUID = -3641221053272056036L;

	public Food()
	{
		this.l = true;
		this.i = getRandomFoodImage();
		this.w = i.getWidth(null);
		this.h = i.getHeight(null);
		this.x = getRandomXPosition();
		this.y = getRandomYPosition();
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


	public void eaten(MyFrame.MySnake mySnake)	{

		if (mySnake.getRectangle().intersects(this.getRectangle()) && l && mySnake.l)		{
			this.l = false;
			mySnake.changeLength(mySnake.getLength() + 1); // add a variable for the amount it grows
			mySnake.score += 1;  // add a variable for score++
		}
	}
	@Override
	public void draw(Graphics g)
	{
		g.drawImage(i, x, y, null);
	}
}
