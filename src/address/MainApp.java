package address;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import address.view.AddProductScreenController;
import address.view.LoginController;
import address.view.MenuScreenController;
import address.view.ProductDetailScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
		
		File file = new File("src/images/system/icon.png");
        Image image = new Image(file.toURI().toString());
		primaryStage.getIcons().add(image);
		primaryStage.setTitle("Rent MI");
		
		initRootLayout();
		
		showMainMenu(0, null);
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
//        scene.getStylesheets().add("address/style/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
	}

	/**
	 * Initializes the main menu.
	 */
	public void showMainMenu(int id_slc_view, ArrayList<Integer> state_selected) throws IOException {
		
		MenuScreenController controller = new MenuScreenController(id_slc_view, state_selected);
		
		// loads the main menu screen.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view\\MenuScreen.fxml"));
        
        controller.setMainApp(this);
        loader.setController(controller);
        
        AnchorPane menuScreen = (AnchorPane) loader.load();

        // puts the main menu inside the root layout.
        rootLayout.setCenter(menuScreen);
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
	
	public void showProductDetailScreen(int id, ArrayList<Integer> state_selected) throws IOException{
		
//		FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("view\\\\ProductDetailScreen.fxml"));
//        
//        ProductDetailScreenController controller = new ProductDetailScreenController(id);
//        loader.setController(controller);
//        /* 
//         * if "fx:controller" is not set in fxml
//         * fxmlLoader.setController(NewWindowController);
//         */
//        Scene scene = new Scene(loader.load(), 800, 600);
//        Stage stage = new Stage();
//        stage.setTitle("Rent MI");
//        
//		File file = new File("src/images/system/icon.png");
//        Image image = new Image(file.toURI().toString());
//        stage.getIcons().add(image);
//        stage.setTitle("Rent MI");
//        
//        stage.setScene(scene);
//        stage.show();
		
		
		ProductDetailScreenController controller = new ProductDetailScreenController(id, state_selected);
		
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view\\ProductDetailScreen.fxml"));
        
        controller.setMainApp(this);
        loader.setController(controller);
        
        AnchorPane anchor = (AnchorPane) loader.load();

        rootLayout.setCenter(anchor);
	}
}