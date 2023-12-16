package SnakeGame;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.stage.Stage;

public class StageManager {
	private static Stage mainStage;
	private String title;

	private static boolean blackWhite;

	private Scene gameScene;

	private static Scene gameSettings;

	private Scene currentScene;

	public StageManager(Stage mainStage) {
		this(mainStage, "");
	}
	public StageManager(Stage mainStage, String title) {
		this.mainStage = mainStage;
		this.title = title;
		blackWhite = false;
	}
	public void loadGameScene(String urlPath) {
		URL url = getClass().getResource(urlPath);
		if (url != null) {
			gameScene = loadSceneToStage(mainStage, url);
			// Additional logic for handling a loaded game scene
		}
	}
	public void loadSettingsScene(String urlPath) {
		URL url = getClass().getResource(urlPath);
		if (url != null) {
			gameSettings = loadSceneToStage(mainStage, url);
			// Additional logic for handling a loaded game scene
		}
	}
	public void resumeSettings() {
		if (gameSettings != null) {
			mainStage.setScene(gameSettings);
			mainStage.show();
		}
	}
	public void resumeGame() {
		if (gameScene != null) {
			if (blackWhite){
				// Apply ColorAdjust filter to make the scene black and white
				ColorAdjust colorAdjust = new ColorAdjust();
				colorAdjust.setSaturation(-1); // Set saturation to -1 for grayscale
				gameScene.getRoot().setEffect(colorAdjust);
			}
			mainStage.setScene(gameScene);
			mainStage.show();
		}
	}
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
		if (blackWhite){
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
	public void loadNewScene(String urlPath) {
		loadNewScene(getClass().getResource(urlPath));
	}
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
		if (blackWhite){
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
	public static void setBlackWhite(boolean bool)
	{
		System.out.println(blackWhite);
		blackWhite = bool;
	}

	public static void applyBlackWhite()
	{
			ColorAdjust colorAdjust = new ColorAdjust();colorAdjust.setSaturation(-1); // Set saturation to -1 for grayscale
			gameSettings.getRoot().setEffect(colorAdjust);
			mainStage.setScene(gameSettings);
			mainStage.show();
	}
	public static void reverseBlackWhite()
	{
		gameSettings.getRoot().setEffect(null);
		mainStage.setScene(gameSettings);
		mainStage.show();
	}
	public static boolean getBlackWhite()
	{
		return blackWhite;
	}
}

