package SnakeGame;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;


// well designed class to just have key features needed (keep)
public class MusicPlayer extends Thread
{
	private String musicFilename;
	private static Player player;


	public MusicPlayer(String filename)
	{
		this.musicFilename = filename;
	}

	public void play()
	{
		new Thread() {
				@Override
				public void run() {
					super.run();
					try {
						while(true) { // small modification, keeps playing the same music as it ends.
							player = new Player(new BufferedInputStream(new FileInputStream(musicFilename)));
							player.play();
						}
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}.start();
	}
	public static void getMusicPlay(String filename)
	{
		MusicPlayer musicPlayer = new MusicPlayer(filename);
		musicPlayer.play();
	}

	public static void stopMusic(String filename) { // quick implementation function to stop music.
	if (player != null){
		player.close();
	}
	}





}
