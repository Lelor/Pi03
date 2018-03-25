package address.view;

import java.io.IOException;

import address.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
 
public class NewProductScreenController {
	
	private MainApp mainApp;
	
    @FXML
    protected void goBackHandler(ActionEvent event) throws IOException {
    	mainApp.showMainMenu();;
    }
    
    public void setMainApp(MainApp mainApp){
    	this.mainApp = mainApp;
    }
}
