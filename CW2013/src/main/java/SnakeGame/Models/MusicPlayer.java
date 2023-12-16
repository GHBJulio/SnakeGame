package SnakeGame.Models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MusicPlayer extends Thread {
	private final String musicFilename;
	private MediaPlayer mediaPlayer;
	private static List<MusicPlayer> activeMusicPlayers = new ArrayList<>();
	private static boolean isMuted = false;

	public MusicPlayer(String filename) {
		this.musicFilename = filename;
	}

	public void play() {
		new Thread(() -> {
			try {
				if (!isMuted) {  // Check if not muted
					Media media = new Media(new File(musicFilename).toURI().toString());
					mediaPlayer = new MediaPlayer(media);
					activeMusicPlayers.add(this);

					// Restart the music when it reaches the end
					mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(javafx.util.Duration.ZERO));

					mediaPlayer.play();
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}).start();
	}

	public void playOnce() {
		new Thread(() -> {
			try {
				if (!isMuted) {  // Check if not muted
					Media media = new Media(new File(musicFilename).toURI().toString());
					mediaPlayer = new MediaPlayer(media);
					activeMusicPlayers.add(this);

					mediaPlayer.play();
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}).start();
	}
	public static void getMusicPlay(String filename, boolean loop) {
		MusicPlayer musicPlayer = new MusicPlayer(filename);
		if (loop) {
			musicPlayer.play();
		} else {
			musicPlayer.playOnce();
		}
	}

	public static void stopMusic(MusicPlayer musicPlayer) {
		try {
			System.out.println("Stopping music for: " + musicPlayer);

			if (musicPlayer != null && musicPlayer.mediaPlayer != null) {
				if (musicPlayer.mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
					musicPlayer.mediaPlayer.stop();
					activeMusicPlayers.remove(musicPlayer);
					System.out.println("Music stopped successfully.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void stopAllMusic() {
		synchronized (activeMusicPlayers) {
			Iterator<MusicPlayer> iterator = activeMusicPlayers.iterator();
			while (iterator.hasNext()) {
				MusicPlayer musicPlayer = iterator.next();
				if (musicPlayer != null && musicPlayer.mediaPlayer != null) {
					musicPlayer.mediaPlayer.stop();
					iterator.remove();  // Use iterator to safely remove from the list
				}
			}
		}
	}

	public void mute() {
		if (mediaPlayer != null) {
			mediaPlayer.setMute(true);
			isMuted = true;
		}
	}

	public void unmute() {
		if (mediaPlayer != null) {
			mediaPlayer.setMute(false);
			isMuted = false;
		}
	}

	public static void setMuted(boolean bool) {
		isMuted = bool;
	}

	// Other methods or modifications as needed
}
