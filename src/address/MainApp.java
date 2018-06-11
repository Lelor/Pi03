package address;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import address.controller.AddPersonScreenController;
import address.controller.AddProductScreenController;
import address.controller.LoginController;
import address.controller.MaintenanceScreenController;
import address.controller.ManagesPeapleScreenController;
import address.controller.MenuScreenController;
import address.controller.PersonDetailScreenController;
import address.controller.ProductDetailScreenController;
import address.controller.RentScreenController;
import address.controller.ReturnScreenController;
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
		
		showLoginScreen();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Inicializa o root layout.
	 * @throws IOException
	 */
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
	 * Carrega a tela principal.
	 * @param id_slc_view - Id de instrumento selecionado na tela de visualização do mesmo.
	 * @param state_selected - Ids de instrumentos selecionados durante a navegação.
	 * @throws IOException
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
	
	/**
	 * Carrega a tela de login.
	 * @throws IOException
	 */
	public void showLoginScreen() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view\\LoginScreen.fxml"));
        AnchorPane loginScreen = (AnchorPane) loader.load();

        // puts the new product screen inside the root layout.
        rootLayout.setCenter(loginScreen);
        
        LoginController controller = loader.getController();
        controller.setMainApp(this);
	}
	
	/**
	 * Carrega a tela de gerenciamento de pessoas.
	 * @throws IOException
	 */
	public void showManagesPeapleScreen() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view\\ManagesPeopleScreen.fxml"));
        AnchorPane screen = (AnchorPane) loader.load();

        // puts the new product screen inside the root layout.
        rootLayout.setCenter(screen);
        
        ManagesPeapleScreenController controller = loader.getController();
        controller.setMainApp(this);
	}
	
	/**
	 * Carrega a tela de adicionar pessoas.
	 * @param type - Tipo de pessoa.
	 * @throws IOException
	 */
	public void showAddPersonScreen(int type) throws IOException{
		
		AddPersonScreenController controller = new AddPersonScreenController(type);
		
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view\\AddPersonScreen.fxml"));
        
        controller.setMainApp(this);
        loader.setController(controller);
        
        AnchorPane anchor = (AnchorPane) loader.load();

        rootLayout.setCenter(anchor);
	}
	
	/**
	 * Carrega a tela de detalhes e edição de pessoas.
	 * @param type - tipo de pessoa.
	 * @throws IOException
	 */
	public void showPersonDetailScreen(int id, int type) throws IOException{
		
		PersonDetailScreenController controller = new PersonDetailScreenController(id, type);
		
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view\\PersonDetailScreen.fxml"));
        
        controller.setMainApp(this);
        loader.setController(controller);
        
        AnchorPane anchor = (AnchorPane) loader.load();

        rootLayout.setCenter(anchor);
	}
	
	/**
	 * Carrega a tela de adicionar novos intrumentos.
	 * @throws IOException
	 */
	public void showAddProductScreen() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view\\AddProductScreen.fxml"));
        AnchorPane loginScreen = (AnchorPane) loader.load();

        // puts the new product screen inside the root layout.
        rootLayout.setCenter(loginScreen);
        
        AddProductScreenController controller = loader.getController();
        controller.setMainApp(this);
	}
	
	/**
	 * Carrega a tela de devolução de intrumentos.
	 * @throws IOException
	 */
	public void showReturnScreen() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view\\ReturnScreen.fxml"));
        AnchorPane screen = (AnchorPane) loader.load();

        // puts the new product screen inside the root layout.
        rootLayout.setCenter(screen);
        
        ReturnScreenController controller = loader.getController();
        controller.setMainApp(this);
	}
	
	/**
	 * Carrega a tela de informações do instrumento.
	 * @param id - Id do instrumento.
	 * @param state_selected - Ids dos intrumentos selecionados com a navegação.
	 * @throws IOException
	 */
	public void showProductDetailScreen(int id, ArrayList<Integer> state_selected) throws IOException{
		
		ProductDetailScreenController controller = new ProductDetailScreenController(id, state_selected);
		
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view\\ProductDetailScreen.fxml"));
        
        controller.setMainApp(this);
        loader.setController(controller);
        
        AnchorPane anchor = (AnchorPane) loader.load();

        rootLayout.setCenter(anchor);
	}
	
	/**
	 * Carrega a tela de locação.
	 * @param idsInstruments - Ids dos instrumentos que serão locados.
	 * @throws IOException
	 */
	public void showRentScreen(ArrayList<Integer> idsInstruments) throws IOException{
		
		RentScreenController controller = new RentScreenController(idsInstruments);
		
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view\\RentScreen.fxml"));
        
        controller.setMainApp(this);
        loader.setController(controller);
        
        AnchorPane anchor = (AnchorPane) loader.load();

        rootLayout.setCenter(anchor);
	}
	
	/**
	 * Carrega a tela de manutenção.
	 * @throws IOException
	 */
	public void showMaintenanceScreen() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view\\MaintenanceScreen.fxml"));
        AnchorPane screen = (AnchorPane) loader.load();

        // puts the new product screen inside the root layout.
        rootLayout.setCenter(screen);
        
        MaintenanceScreenController controller = loader.getController();
        controller.setMainApp(this);
	}
}