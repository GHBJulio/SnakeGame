package SnakeGame.Models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayer extends Thread {
	private final String musicFilename;
	private static MediaPlayer mediaPlayer;

	public MusicPlayer(String filename) {
		this.musicFilename = filename;
	}

	public void play() {
		new Thread(() -> {
			try {
				Media media = new Media(new File(musicFilename).toURI().toString());
				mediaPlayer = new MediaPlayer(media);

				// Restart the music when it reaches the end
				mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(javafx.util.Duration.ZERO));

				mediaPlayer.play();
			} catch (Exception e) {
				System.out.println(e);
			}
		}).start();
	}

	public static void getMusicPlay(String filename) {
		MusicPlayer musicPlayer = new MusicPlayer(filename);
		musicPlayer.play();
	}

	public static void stopMusic(String filename) {
		if (mediaPlayer != null)
		{
			mediaPlayer.stop();
		}
	}
}
