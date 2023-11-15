package SnakeGame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

// Well designed class, for what it's needed (Keep)
// error handling
public class  GameUtil
{
	public static Image getImage(String imagePath) {
		URL u = GameUtil.class.getClassLoader().getResource(imagePath);
		//System.out.println("Image Path: " + imagePath);
		//System.out.println("URL: " + u);
//		if (u == null){
//			System.err.println("ERROR: Image not found for path: " + imagePath);
//			return null;
//		}
// debug code above no longer needed.
		BufferedImage i = null;
		try {
			i = ImageIO.read(u);
		} catch (Exception e) {
			System.err.println("ERROR: Failed to load image: " + imagePath);
			e.printStackTrace();
		}

		return i;
	}
	public static Image rotateImage(final BufferedImage bufferedImage, final int degree)
	{
	int w = bufferedImage.getWidth();
	int h = bufferedImage.getHeight();
	int t = bufferedImage.getColorModel().getTransparency();

	BufferedImage i;
	Graphics2D graphics2d;

	(graphics2d = (i = new BufferedImage(w, h, t)).createGraphics()).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

	graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
	graphics2d.drawImage(bufferedImage, 0, 0, null);
	graphics2d.dispose();

	return i;

	}
}
