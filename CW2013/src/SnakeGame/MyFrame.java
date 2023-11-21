package SnakeGame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;


// modify class (make this our view) | it has both functionalities (view and model)
// only keep one of the MVC's in one class for good practice. (KEEP)

public class MyFrame extends JPanel implements KeyListener
{
	private static final long serialVersionUID = -3149926831770554380L;

	public JFrame jFrame = new JFrame();

	public MyFrame()
	{
		jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(MyFrame.class.getResource("snake-logo.png")));
	}
	public void loadFrame()
	{
		/*
		 * Prevent the image from flashing.
		 */
		this.setDoubleBuffered(true);
		jFrame.add(this);
		jFrame.addKeyListener(this);

		jFrame.setTitle("Original Snake Game");
		jFrame.setSize(870, 560);
		jFrame.setLocationRelativeTo(null);
		jFrame.addWindowListener(new WindowAdapter()// loka
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				super.windowClosing(e);
				System.exit(0);
			}
		});
		jFrame.setVisible(true);

		new MyThread().start();
	}
	class MyThread extends Thread
	{
		@Override
		public void run()
		{
			super.run();
			while (true)
			{
				repaint();
				try
				{
					sleep(30);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	public static class MySnake extends SnakeObject implements movable
	{
		// The game changer.
		private int speed_XY;
		private int snakeLength;
		private int num; // ?
		public int score = 0;

		private static final BufferedImage IMG_SNAKE_HEAD = (BufferedImage) ImageUtil.images.get("snake-head-right");

		public static List<Point> bodyPoints = new LinkedList<>();

		private static BufferedImage newImgSnakeHead;
		boolean up, down, left, right = true;

		public MySnake(int x, int y)
		{
			this.isAlive = true;
			this.headX = x;
			this.headY = y;
			this.image = ImageUtil.images.get("snake-body");
			this.w = image.getWidth(null);
			this.h = image.getHeight(null);

			this.speed_XY = 5;
			this.snakeLength = 1;

			/*
			 * Attention : ?
			 */
			this.num = w / speed_XY;
			newImgSnakeHead = IMG_SNAKE_HEAD;

		}

		public int getSnakeLength()
		{
			return snakeLength;
		}

		public void changeLength(int length)
		{
			this.snakeLength = length;
		}

		public void keyPressed(KeyEvent e)
		{
			// check the key
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_UP:
				if (!down)
				{
					up = true;
					down = false;
					left = false;
					right = false;

					newImgSnakeHead = (BufferedImage) GameUtil.rotateImage(IMG_SNAKE_HEAD, -90);
				}
				break;

			case KeyEvent.VK_DOWN:
				if (!up)
				{
					up = false;
					down = true;
					left = false;
					right = false;

					newImgSnakeHead = (BufferedImage) GameUtil.rotateImage(IMG_SNAKE_HEAD, 90);
				}
				break;

			case KeyEvent.VK_LEFT:
				if (!right)
				{
					up = false;
					down = false;
					left = true;
					right = false;

					newImgSnakeHead = (BufferedImage) GameUtil.rotateImage(IMG_SNAKE_HEAD, -180);

				}
				break;

			case KeyEvent.VK_RIGHT:
				if (!left)
				{
					up = false;
					down = false;
					left = false;
					right = true;

					newImgSnakeHead = IMG_SNAKE_HEAD;
				}

			default:
				break;
			}
		}


		public void move()
		{
			// make the snake move
			if (up)
			{
				headY -= speed_XY;
			} else if (down)
			{
				headY += speed_XY;
			} else if (left)
			{
				headX -= speed_XY;
			} else if (right)
			{
				headX += speed_XY;
			}

		}

		@Override
		public void draw(Graphics g)
		{
			outofBounds();
			eatBody();

			bodyPoints.add(new Point(headX, headY));

			if (bodyPoints.size() == (this.snakeLength + 1) * num)
			{
				bodyPoints.remove(0);
			}
			g.drawImage(newImgSnakeHead, headX, headY, null);
			drawBody(g);

			move();
		}

		public void eatBody()
		{
			for (Point point : bodyPoints)
			{
				for (Point point2 : bodyPoints)
				{
					if (point.equals(point2) && point != point2)
					{
						this.isAlive = false;
					}
				}
			}
		}

		public void drawBody(Graphics g)
		{
			int length = bodyPoints.size() - 1 - num;

			for (int i = length; i >= num; i -= num)
			{
				Point point = bodyPoints.get(i);
				g.drawImage(this.image, point.x, point.y, null);
			}
		}

		private void outofBounds()
		{
			boolean xOut = (headX <= 0 || headX >= (870 - w));
			boolean yOut = (headY <= 40 || headY >= (560 - h)); // updated version = boolean yOut = (y <= 0 || y >= (560 - h));
			// old code yOut wasn't working properly as snake would not be able to eat food on top of the screen
			if (xOut || yOut)
			{
				isAlive = false;
			}
		}
	}

	public abstract static class SnakeObject
	{
		int headX;
		int headY;
		Image image;
		int w;
		int h;

		public boolean isAlive;


		public abstract void draw(Graphics g);

		public Rectangle getRectangle()
		{
			return new Rectangle(headX, headY, w, h);
		}
	}
}
