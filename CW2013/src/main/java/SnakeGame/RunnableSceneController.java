package SnakeGame;

import javafx.scene.Scene;

public abstract class RunnableSceneController {
	protected Scene scene;
	public void setScene(Scene scene) {
		this.scene = scene;
	};
	public Scene getScene() {
		return this.scene;
	}
	
	public void run() {};
}
