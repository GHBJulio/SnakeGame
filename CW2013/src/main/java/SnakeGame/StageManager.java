package SnakeGame;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageManager {
	private Stage mainStage;
	private String title;

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

	public void loadScene(String urlPath) {
		loadScene(getClass().getResource(urlPath));
	}

	public Scene loadScene(URL url) {
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

		return scene;
	}
}
