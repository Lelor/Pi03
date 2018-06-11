package address.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import address.MainApp;
import address.model.Client;
import address.model.ClientDAO;
import address.model.Employee;
import address.model.EmployeeDAO;
import address.model.Person;
import address.model.Provider;
import address.model.ProviderDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class AddPersonScreenController implements Initializable{
	
	private MainApp mainApp;
	private int type;
	
    public void setMainApp(MainApp mainApp){
    	this.mainApp = mainApp;
    }
    
    public AddPersonScreenController() {
	}
    
    public AddPersonScreenController(int type) {
    	
    	this.type = type;
    	/*
    	 * Type 1: Cliente
    	 * Type 2: Funcionário
    	 * Type 3: Fornecedor
    	 */
   	}
    
    // pega elementos fxml
    @FXML Pane paneAccess;
    @FXML Label lblTitulo;
    @FXML Label lblDocument;
    
    @FXML TextField txtNome;
    @FXML TextField txtDocument;
    @FXML TextField txtTel;
    @FXML TextField txtEmail;
    @FXML TextField txtCidade;
    @FXML TextField txtLogin;
    @FXML TextArea txaEndereco;
    @FXML PasswordField pswSenha;
    @FXML ComboBox<String> cbxNivel;
    
    @FXML
    protected void goBackHandler(ActionEvent event) throws IOException {
    	mainApp.showManagesPeapleScreen();
    }
    
    @FXML
    protected void btnClearFields(ActionEvent event) throws IOException {
    	ClearFieds();
    }
    
    @FXML
    protected void btnRegister(ActionEvent event) throws IOException {
    	
    	if( txtNome.getText().isEmpty() || txtDocument.getText().isEmpty() || txtTel.getText().isEmpty() 
    			|| txtEmail.getText().isEmpty() || txtCidade.getText().isEmpty() || txaEndereco.getText().isEmpty() ) {
    		
    		JOptionPane.showMessageDialog(null, "Preencha todos os campos para continuar!", "Alerta!", 2);
    		
    	}else {
    		
    		if (type == 2 && (txtLogin.getText().isEmpty() || pswSenha.getText().isEmpty() || cbxNivel.getValue().isEmpty())) {
    			
    			JOptionPane.showMessageDialog(null, "Preencha os dados de accesso para continuar!", "Alerta!", 2);
    			
			}else {
				
				try {
					
					switch (type) {
						case 1:
							
							Client cl = new Client();
							cl.setNome(txtNome.getText());
							cl.setDocumento(txtDocument.getText());
							cl.setTelefone(txtTel.getText());
							cl.setEmail(txtEmail.getText());
							cl.setCidade(txtCidade.getText());
							cl.setEndereco(txaEndereco.getText());
							
							ClientDAO clDAO = new ClientDAO();
							
							JOptionPane.showMessageDialog(null, clDAO.insertClient(cl), "Alerta!", 2);
							

							break;
						case 2:
							
							Employee ep = new Employee();
							ep.setNome(txtNome.getText());
							ep.setDocumento(txtDocument.getText());
							ep.setTelefone(txtTel.getText());
							ep.setEmail(txtEmail.getText());
							ep.setCidade(txtCidade.getText());
							ep.setEndereco(txaEndereco.getText());
							ep.setLogin(txtLogin.getText());
							ep.setSenha(pswSenha.getText());
							if(cbxNivel.getValue().toString() == "Vendedor") {
								ep.setNivel(0);
							}else {
								ep.setNivel(1);
							}
							
							
							EmployeeDAO epDAO = new EmployeeDAO();
							
							JOptionPane.showMessageDialog(null, epDAO.insertEmployee(ep), "Alerta!", 2);

							break;
						case 3:
							
							Provider pr = new Provider();
							pr.setNome(txtNome.getText());
							pr.setDocumento(txtDocument.getText());
							pr.setTelefone(txtTel.getText());
							pr.setEmail(txtEmail.getText());
							pr.setCidade(txtCidade.getText());
							pr.setEndereco(txaEndereco.getText());
							
							ProviderDAO prDAO = new ProviderDAO();
							
							JOptionPane.showMessageDialog(null, prDAO.insertProvider(pr), "Alerta!", 2);

							break;
				
						default:
							break;
					}
					
					ClearFieds();
					
					
				} catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, "Ops! Não foi possível realiza o cadastro. Cheque os dados e tente novamente.", 
							"Alerta!", 2);
					System.out.println("Erro ao cadastrar: " + e.toString());
					
				}
				
			}
    	}
    	
    }
    
    /**
     * Limpa todos os campos do formulário.
     */
    public void ClearFieds() {
        txtNome.setText("");
        txtDocument.setText("");
        txtTel.setText("");
        txtEmail.setText("");
        txtCidade.setText("");
        txaEndereco.setText("");
        txtLogin.setText("");
        pswSenha.setText("");
        cbxNivel.setValue("");
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// preenche lista de níveis
		cbxNivel.getItems().addAll(
        	    "Vendedor",
        	    "Master"
        	);

		switch (type) {
			case 1:
				paneAccess.setDisable(true);
				lblTitulo.setText("Cadastrar Cliente");
				lblDocument.setText("CPF:");
				break;
			case 2:
				paneAccess.setDisable(false);
				lblTitulo.setText("Cadastrar Funcionário");
				lblDocument.setText("CPF:");
				break;
			case 3:
				paneAccess.setDisable(true);
				lblTitulo.setText("Cadastrar Fornecedor");
				lblDocument.setText("CNPJ:");
				break;
	
			default:
				break;
		}
		
	}
}
