package SnakeGame.Models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The MusicPlayer class is responsible for playing and controlling background music in the Snake Game.
 * It extends Thread to allow asynchronous music playback.
 * The class supports playing music continuously or only once based on user preferences.
 * It also provides methods to stop specific music instances or all active music.
 * Additionally, it supports muting and unmuting the music.
 *
 * @author Guilherme Julio
 */
public class MusicPlayer extends Thread {

	/** The filename of the music file to be played. */
	private final String musicFilename;

	/** The MediaPlayer responsible for playing the music. */
	private MediaPlayer mediaPlayer;

	/** List to keep track of active MusicPlayer instances. */
	private static List<MusicPlayer> activeMusicPlayers = new ArrayList<>();

	/** Flag to determine if the music is muted. */
	private static boolean isMuted = false;

	/**
	 * Constructs a MusicPlayer with the specified music filename.
	 *
	 * @param filename The filename of the music file.
	 */
	public MusicPlayer(String filename) {
		this.musicFilename = filename;
	}
	/**
	 * Retrieves the current muted status.
	 *
	 * @return {@code true} if the audio is muted, {@code false} otherwise.
	 */
	public static boolean isMuted() {
		return isMuted;
	}

	/**
	 * Starts playing the music continuously in a separate thread.
	 */
	public void play() {
		new Thread(() -> {
			try {
				Media media = new Media(new File(musicFilename).toURI().toString());
				mediaPlayer = new MediaPlayer(media);
				activeMusicPlayers.add(this);

				// Restart the music when it reaches the end
				mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(javafx.util.Duration.ZERO));

				if (!isMuted) {
					mediaPlayer.play();
				} else {
					mediaPlayer.setMute(true);
				}
			} catch (Exception e) {
			}
		}).start();
	}

	/**
	 * Starts playing the music once in a separate thread.
	 */
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
			}
		}).start();
	}

	/**
	 * Static method to play music with specified filename and loop option.
	 *
	 * @param filename The filename of the music file.
	 * @param loop     True if the music should loop, false for a one-time play.
	 */
	public static void getMusicPlay(String filename, boolean loop) {
		MusicPlayer musicPlayer = new MusicPlayer(filename);
		if (loop) {
			musicPlayer.play();
		} else {
			musicPlayer.playOnce();
		}
	}

	/**
	 * Stops the music for a specific MusicPlayer instance.
	 *
	 * @param musicPlayer The MusicPlayer instance to stop.
	 */
	public static void pauseMusic(MusicPlayer musicPlayer) {
		try {

			if (musicPlayer != null && musicPlayer.mediaPlayer != null) {
				if (musicPlayer.mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
					musicPlayer.mediaPlayer.pause();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Resumes the music for a specific MusicPlayer instance.
	 *
	 * @param musicPlayer The MusicPlayer instance to resume.
	 */
	public static void resumeMusic(MusicPlayer musicPlayer) {
		try {

			if (musicPlayer != null && musicPlayer.mediaPlayer != null && !isMuted) {
				if (musicPlayer.mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
					musicPlayer.mediaPlayer.play();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Resumes all active music instances.
	 */
	public static void resumeAllMusic() {
		synchronized (activeMusicPlayers) {
			for (MusicPlayer musicPlayer : activeMusicPlayers) {
				resumeMusic(musicPlayer);
			}
		}
	}

	/**
	 * Pauses all active music instances.
	 */
	public static void pauseAllMusic() {
		synchronized (activeMusicPlayers) {
			for (MusicPlayer musicPlayer : activeMusicPlayers) {
				pauseMusic(musicPlayer);
			}
		}
	}

	/**
	 * Stops all active music instances.
	 */
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

	/**
	 * Sets the mute status for all music instances.
	 *
	 * @param bool True to mute, false to unmute.
	 */
	public static void setMuted(boolean bool) {
		isMuted = bool;
	}
}
