package SnakeGame;

import javafx.scene.Scene;

/**
 * The RunnableSceneController class is an abstract class that serves as a base for
 * controllers associated with JavaFX scenes. It provides methods to set and retrieve
 * the associated scene, as well as a run method that can be overridden by subclasses
 * to define custom behavior.
 * @author Guilherme Julio
 */
public abstract class RunnableSceneController {

	/**
	 * Default constructor for the RunnableSceneController class.
	 */
	public RunnableSceneController() {}

	/** The associated JavaFX scene. */
	protected Scene scene;

	/**
	 * Sets the associated JavaFX scene for the controller.
	 *
	 * @param scene The JavaFX scene to associate with the controller.
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Gets the associated JavaFX scene.
	 *
	 * @return The associated JavaFX scene.
	 */
	public Scene getScene() {
		return this.scene;
	}

	/**
	 * The run method to be overridden by subclasses to define custom behavior.
	 * This method can be used to perform actions when the scene is executed or
	 * when specific events occur.
	 */
	public void run() {
		// Default implementation does nothing; intended to be overridden.
	}
}
