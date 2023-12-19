package SnakeGame;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.stage.Stage;

/**
 * The StageManager class manages the loading and transitioning of scenes in a JavaFX application.
 * It provides methods to load new scenes, set scenes to the main stage, and apply visual effects
 * such as black and white filtering. This class works in conjunction with controllers that extend
 * the RunnableSceneController class.
 *
 * @author Guilherme Julio
 */
public final class StageManager {
	private static Stage mainStage;
	private String title;
	private static boolean blackWhite;
	private Scene gameScene;
	private static Scene gameSettings;
	private Scene currentScene;

	/**
	 * Constructs a StageManager with the main stage and an optional title.
	 *
	 * @param mainStage The main JavaFX stage.
	 */
	private StageManager(Stage mainStage) {
		this.mainStage = mainStage;
		this.title = title;
		blackWhite = false;
	}

	private static StageManager instance;


	/**
	 * Gets the instance of the StageManager class, creating a new instance if it does not exist.
	 *
	 * @param primaryStage The main JavaFX stage to associate with the StageManager.
	 * @return The singleton instance of the StageManager.
	 */
	public static StageManager getInstance(Stage primaryStage) {
		if (instance == null) {
			instance = new StageManager(primaryStage);
		}
		return instance;
	}


	/**
	 * Loads the game scene from the specified FXML file path.
	 *
	 * @param urlPath The path to the FXML file.
	 */
	public void loadGameScene(String urlPath) {
		URL url = getClass().getResource(urlPath);
		if (url != null) {
			gameScene = loadSceneToStage(mainStage, url);
			// Additional logic for handling a loaded game scene
		}
	}

	/**
	 * Loads the game settings scene from the specified FXML file path.
	 *
	 * @param urlPath The path to the FXML file.
	 */
	public void loadSettingsScene(String urlPath) {
		URL url = getClass().getResource(urlPath);
		if (url != null) {
			gameSettings = loadSceneToStage(mainStage, url);
			// Additional logic for handling a loaded game settings scene
		}
	}

	/**
	 * Resumes the settings scene on the main stage.
	 */
	public void resumeSettings() {
		if (gameSettings != null) {
			mainStage.setScene(gameSettings);
			mainStage.show();
		}
	}

	/**
	 * Resumes the game scene on the main stage.
	 */
	public void resumeGame() {
		if (gameScene != null) {
			if (blackWhite) {
				// Apply ColorAdjust filter to make the scene black and white
				ColorAdjust colorAdjust = new ColorAdjust();
				colorAdjust.setSaturation(-1); // Set saturation to -1 for grayscale
				gameScene.getRoot().setEffect(colorAdjust);
			}
			else{
				ColorAdjust colorAdjust = new ColorAdjust();
				colorAdjust.setSaturation(0);
				gameScene.getRoot().setEffect(colorAdjust);

			}
			mainStage.setScene(gameScene);
			mainStage.show();
		}
	}

	/**
	 * Loads a scene to the main stage using the provided FXML file URL.
	 *
	 * @param stage The main stage.
	 * @param url   The URL of the FXML file.
	 * @return The loaded scene.
	 */
	public Scene loadSceneToStage(Stage stage, URL url) {
		if (url == null) {
			return null;
		}

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);

		Scene scene;
		try {
			scene = new Scene(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("An error occurred", e);
		}

		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle(title);
		if (blackWhite) {
			// Apply ColorAdjust filter to make the scene black and white
			ColorAdjust colorAdjust = new ColorAdjust();
			colorAdjust.setSaturation(-1); // Set saturation to -1 for grayscale
			scene.getRoot().setEffect(colorAdjust);
		}
		stage.show();

		RunnableSceneController controller = (RunnableSceneController) loader.getController();
		controller.setScene(scene);
		controller.run();

		currentScene = scene;

		return scene;
	}

	/**
	 * Loads a new scene to the main stage using the provided FXML file path.
	 *
	 * @param urlPath The path to the FXML file.
	 */
	public void loadNewScene(String urlPath) {
		loadNewScene(getClass().getResource(urlPath));
	}

	/**
	 * Loads a new scene to the main stage using the provided FXML file URL.
	 *
	 * @param url The URL of the FXML file.
	 * @return The loaded scene.
	 */
	public Scene loadNewScene(URL url) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);

		Scene scene;
		try {
			scene = new Scene(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("An error occurred", e);
		}
		mainStage.setResizable(false);
		mainStage.setScene(scene);
		mainStage.setTitle(title);
		if (blackWhite) {
			// Apply ColorAdjust filter to make the scene black and white
			ColorAdjust colorAdjust = new ColorAdjust();
			colorAdjust.setSaturation(-1); // Set saturation to -1 for grayscale
			scene.getRoot().setEffect(colorAdjust);
		}
		mainStage.show();

		RunnableSceneController controller = (RunnableSceneController) loader.getController();
		controller.setScene(scene);
		controller.run();

		currentScene = scene;

		return scene;
	}

	/**
	 * Sets the black and white mode for scenes.
	 *
	 * @param bool True to enable black and white mode, false otherwise.
	 */
	public static void setBlackWhite(boolean bool) {
		blackWhite = bool;
	}

	/**
	 * Applies the black and white filter to the game settings scene.
	 */
	public static void applyBlackWhite() {
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setSaturation(-1); // Set saturation to -1 for grayscale
		gameSettings.getRoot().setEffect(colorAdjust);
		mainStage.setScene(gameSettings);
		mainStage.show();
	}

	/**
	 * Reverts the black and white filter on the game settings scene.
	 */
	public static void reverseBlackWhite() {
		gameSettings.getRoot().setEffect(null);
		mainStage.setScene(gameSettings);
		mainStage.show();
	}

	/**
	 * Retrieves the current black and white mode status.
	 *
	 * @return True if black and white mode is enabled, false otherwise.
	 */
	public static boolean getBlackWhite() {
		return blackWhite;
	}
}
