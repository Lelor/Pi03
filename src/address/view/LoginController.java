package address.view;

import java.io.IOException;

import address.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LoginController {
private MainApp mainApp;
	
    @FXML
    protected void doLogin(ActionEvent event) throws IOException {
    	/**
    	 * Implement the login handler properly.
    	 */
    	mainApp.showMainMenu(0, null);
    }
    
    public void setMainApp(MainApp mainApp){
    	this.mainApp = mainApp;
    }

}
