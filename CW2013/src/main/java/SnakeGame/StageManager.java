package SnakeGame;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager {
	private Stage mainStage;
	private String title;

	private Scene gameScene;

	private Scene currentScene;

	public StageManager(Stage mainStage) {
		this(mainStage, "");
	}

	public StageManager(Stage mainStage, String title) {
		this.mainStage = mainStage;
		this.title = title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void loadGameScene(String urlPath) {
		URL url = getClass().getResource(urlPath);
		if (url != null) {
			gameScene = loadSceneToStage(mainStage, url);
			// Additional logic for handling a loaded game scene
		}
	}

	public void resumeGame() {
		if (gameScene != null) {
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
		mainStage.show();

		RunnableSceneController controller = (RunnableSceneController) loader.getController();
		controller.setScene(scene);
		controller.run();

		currentScene = scene;

		return scene;
	}

	public Scene getCurrentScene() {
		return currentScene;
	}
}
