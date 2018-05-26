package address;

import java.io.IOException;

import address.view.AddProductScreenController;
import address.view.LoginController;
import address.view.MenuScreenController;
import address.view.ProductDetailScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application{
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Application");
		
		initRootLayout();
		
		showMainMenu();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initRootLayout() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view\\RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();

        // Shows the scene which contains the root layout.
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
	}

	/**
	 * Initializes the main menu.
	 */
	public void showMainMenu() throws IOException {
		// loads the main menu screen.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view\\MenuScreen.fxml"));
        AnchorPane menuScreen = (AnchorPane) loader.load();

        // puts the main menu inside the root layout.
        rootLayout.setCenter(menuScreen);
        MenuScreenController menuScreenController = loader.getController();
        menuScreenController.setMainApp(this);
	}
	
	public void showNewProductScreen() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view\\ProductDetailScreen.fxml"));
        AnchorPane newProductScreen = (AnchorPane) loader.load();

        // puts the new product screen inside the root layout.
        rootLayout.setCenter(newProductScreen);
        
        ProductDetailScreenController controller = loader.getController();
        controller.setMainApp(this);
	}
	
	public void showLoginScreen() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view\\LoginScreen.fxml"));
        AnchorPane loginScreen = (AnchorPane) loader.load();

        // puts the new product screen inside the root layout.
        rootLayout.setCenter(loginScreen);
        
        LoginController controller = loader.getController();
        controller.setMainApp(this);
	}
	public void showAddProductScreen() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view\\AddProductScreen.fxml"));
        AnchorPane loginScreen = (AnchorPane) loader.load();

        // puts the new product screen inside the root layout.
        rootLayout.setCenter(loginScreen);
        
        AddProductScreenController controller = loader.getController();
        controller.setMainApp(this);
	}
}