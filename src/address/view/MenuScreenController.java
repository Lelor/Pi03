package address.view;

import java.io.IOException;

import address.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
 
public class MenuScreenController {
	
	private MainApp mainApp;
	
    @FXML
    protected void addProductHandler(ActionEvent event) throws IOException {
    	mainApp.showNewProductScreen();
    }
    
    @FXML
    protected void lbExitHandler() throws IOException {
    	mainApp.showLoginScreen();;
    }
    
    public void setMainApp(MainApp mainApp){
    	this.mainApp = mainApp;
    }
}
