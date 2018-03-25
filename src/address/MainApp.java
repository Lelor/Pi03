package address;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		initMainMenu(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Initializes the main menu.
	 */
	public void initMainMenu(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("view\\MenuScreen.fxml"));

		Scene scene = new Scene(root);

		stage.setTitle("FXML Welcome");
		stage.setScene(scene);
		stage.show();
	}
}