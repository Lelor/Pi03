package address.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import address.MainApp;
import address.model.Employee;
import address.model.EmployeeDAO;
import address.model.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginController implements Initializable {
	
	private MainApp mainApp;
	
    public void setMainApp(MainApp mainApp){
    	this.mainApp = mainApp;
    }

    @FXML ImageView imgLogo;
    @FXML TextField txtLogin;
    @FXML TextField txtSenha;
   
    @FXML
    public void onEnter(ActionEvent ae) throws IOException{
    	
    	if(login()) {
    		mainApp.showMainMenu(0, null);
    	}else {
    		JOptionPane.showMessageDialog(null, "Login ou senha incorretos!", "Alerta!", 2);
    	}
    }

    @FXML
    protected void doLogin(ActionEvent event) throws IOException {
    	
    	if(login()) {
    		mainApp.showMainMenu(0, null);
    	}else {
    		JOptionPane.showMessageDialog(null, "Login ou senha incorretos!", "Alerta!", 2);
    	}
    	
    }
    
    /**
     * Realiza login no sistema.
     * @return - Verdadeiro ou falso para login.
     */
    public boolean login() {
    	
    	if(txtLogin.getText().isEmpty() || txtSenha.getText().isEmpty()) {
    		
    		JOptionPane.showMessageDialog(null, "Login ou senha incorretos!", "Alerta!", 2);
    		
    		return false;
    		
    	}else {
    		
    		EmployeeDAO epDAO = new EmployeeDAO();
    		Employee ep = new Employee();
    		
    		ep = epDAO.doLogin(txtLogin.getText(), txtSenha.getText());
    		
    		if(ep.isLogado()) {
    			
    			Login.idUser = ep.getId();
    			Login.nivel = ep.getNivel();
    			Login.nameUser = ep.getNome();
    			
    			return true;
    		
    		}else {
    			
    			return false;
    			
    		}
    	}

    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// seta imagem default ------
		File file = new File("src/images/system/logo_login.png");
        Image image = new Image(file.toURI().toString());
        imgLogo.setImage(image);
		
	}

}
