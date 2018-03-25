package address.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class MenuScreenController {
	
    @FXML
    protected void addProductHandler(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("NewProduct.fxml"));
    	System.out.println("Button clicked.");
        Stage stage = new Stage();
        stage.setTitle("New Product");
        stage.setScene(new Scene(root));
        stage.show();
    }
    
}
