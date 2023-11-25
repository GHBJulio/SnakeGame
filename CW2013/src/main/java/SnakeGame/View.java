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

public class View extends JPanel implements KeyListener
{
	private static final long serialVersionUID = -3149926831770554380L;
	public JFrame jFrame = new JFrame();
	private Controller controller;
	public Image backgroundImage = ImageUtil.images.get("UI-background");
	public Image failImage = ImageUtil.images.get("game-scene-01");

	MySnake snake;
	Model model;
	public View()
	{
		jFrame.setIconImage(GameUtil.getImage("snake-logo.png"));
	}

	public void initialize(Model model, Controller controller) {
		this.model = model;
		this.snake = model.getSnake();
		this.controller = controller;
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
		jFrame.addWindowListener(new WindowAdapter()
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
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(backgroundImage, 0, 0, null);

		if(!model.drawGame(g))
		{
			g.drawImage(failImage, 0, 0, null);
		}

		drawScore(g);
	}

	public void drawScore(Graphics g)
	{
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		g.setColor(Color.MAGENTA);
		g.drawString("Score : " + snake.score, 20, 40); // edit this as well to maybe add something nicer as the score.
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		controller.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}
}
