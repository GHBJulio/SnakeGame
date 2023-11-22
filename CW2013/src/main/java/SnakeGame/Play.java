package SnakeGame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;


public class Play extends MyFrame
{
	private static final long serialVersionUID = -3641221053272056036L;

	public MySnake snake = new MySnake(100, 100);// x , y
	public Food food = new Food();
	public Image backgroundImage = ImageUtil.images.get("UI-background");
	public Image failImage = ImageUtil.images.get("game-scene-01");

	@Override
	public void keyPressed(KeyEvent e)
	{
		super.keyPressed(e);
		snake.keyPressed(e);
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(backgroundImage, 0, 0, null);

		// Determine the state of the game.
		if (snake.isAlive)
		{
			snake.draw(g);
			if (food.isAlive)
			{
				food.draw(g);
				food.eaten(snake);
			} else
			{
				food = new Food();
			}
		} else
		{
			// MusicPlayer.stopMusic("src/SnakeGame/frogger.mp3"); - stops music as game ends.
			g.drawImage(failImage, 0, 0, null); // edit this to be the options menu (addition)
		}
		drawScore(g);
	}

	public void drawScore(Graphics g)
	{
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		g.setColor(Color.MAGENTA);
		g.drawString("Score : " + snake.score, 20, 40); // edit this as well to maybe add something nicer as the score.
	}

	public static void main(String[] args)
	{
		new Play().loadFrame();
		MusicPlayer.getMusicPlay("src/main/resources/frogger.mp3");

	}
/*	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		// frame.setSize(400,600);
		frame.setBounds(450, 200, 920, 600);
		// frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SnakePanel panel = new SnakePanel();
		frame.add(panel);

		frame.setVisible(true);

		// Play the background music.
		MusicPlayer.getMusicPlay("resource\\music\\background.mp3");
	} 
*/
}
