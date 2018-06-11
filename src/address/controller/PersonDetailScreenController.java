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
import address.model.Instrument;
import address.model.InstrumentDAO;
import address.model.Person;
import address.model.PersonDAO;
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

public class PersonDetailScreenController implements Initializable{
	
	private MainApp mainApp;
	private int id;
	private int type;
	
    public void setMainApp(MainApp mainApp){
    	this.mainApp = mainApp;
    }
    
    public PersonDetailScreenController() {
	}
    
    public PersonDetailScreenController(int id, int type) {
    	
    	this.id = id;
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
    protected void excludePerson(ActionEvent event) throws IOException {
    	
    	switch (type) {
			case 1:
				
				ClientDAO clDAO = new ClientDAO();
				
				int decision = clDAO.excludePerson(id, type);
				
				if(decision == 1) {
					JOptionPane.showMessageDialog(null, "Cliente excluído!", "Alerta!", 3);
					mainApp.showManagesPeapleScreen();
				}else {
					JOptionPane.showMessageDialog(null, "Operação cancelada!", "Alerta!", 3);
				}
				
				break;
			case 2:

				EmployeeDAO epDAO = new EmployeeDAO();
				
				int decision2 = epDAO.excludePerson(id, type);
				
				if(decision2 == 1) {
					JOptionPane.showMessageDialog(null, "Funcionário excluído!", "Alerta!", 3);
					mainApp.showManagesPeapleScreen();
				}else {
					JOptionPane.showMessageDialog(null, "Operação cancelada!", "Alerta!", 3);
				}
				
				break;
			case 3:

				ProviderDAO prDAO = new ProviderDAO();
				
				int decision3 = prDAO.excludePerson(id, type);
				
				if(decision3 == 1) {
					JOptionPane.showMessageDialog(null, "Fornecedor excluído!", "Alerta!", 3);
					mainApp.showManagesPeapleScreen();
				}else {
					JOptionPane.showMessageDialog(null, "Operação cancelada!", "Alerta!", 3);
				}
				
				break;
			default:
				break;
		}
		
		
    }
    
    
    @FXML
    protected void btnUpdate(ActionEvent event) throws IOException {
    	
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
							cl.setId(id);
							cl.setNome(txtNome.getText());
							cl.setDocumento(txtDocument.getText());
							cl.setTelefone(txtTel.getText());
							cl.setEmail(txtEmail.getText());
							cl.setCidade(txtCidade.getText());
							cl.setEndereco(txaEndereco.getText());
							
							ClientDAO clDAO = new ClientDAO();
							
							JOptionPane.showMessageDialog(null, clDAO.updateClient(cl), "Alerta!", 2);
							

							break;
						case 2:
							
							Employee ep = new Employee();
							ep.setId(id);
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
							
							JOptionPane.showMessageDialog(null, epDAO.updateEmployee(ep), "Alerta!", 2);

							break;
						case 3:
							
							Provider pr = new Provider();
							pr.setId(id);
							pr.setNome(txtNome.getText());
							pr.setDocumento(txtDocument.getText());
							pr.setTelefone(txtTel.getText());
							pr.setEmail(txtEmail.getText());
							pr.setCidade(txtCidade.getText());
							pr.setEndereco(txaEndereco.getText());
							
							ProviderDAO prDAO = new ProviderDAO();
							
							JOptionPane.showMessageDialog(null, prDAO.updateProvider(pr), "Alerta!", 2);

							break;
				
						default:
							break;
					}
					
					
				} catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, "Ops! Não foi possível realiza o cadastro. Cheque os dados e tente novamente.", 
							"Alerta!", 2);
					System.out.println("Erro ao cadastrar: " + e.toString());
					
				}
				
			}
    	}
    	
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
				lblTitulo.setText("Detalhes Cliente");
				lblDocument.setText("CPF:");
				
				ClientDAO clDAO = new ClientDAO();
				Client cl = clDAO.getClient(id);
				
		        txtNome.setText(cl.getNome());
		        txtDocument.setText(cl.getDocumento());
		        txtTel.setText(cl.getTelefone());
		        txtEmail.setText(cl.getEmail());
		        txtCidade.setText(cl.getCidade());
		        txaEndereco.setText(cl.getEndereco());
				
				break;
			case 2:
				paneAccess.setDisable(false);
				lblTitulo.setText("Detalhes Funcionário");
				lblDocument.setText("CPF:");
				
				EmployeeDAO epDAO = new EmployeeDAO();
				Employee ep = epDAO.getEmployee(id);
				
		        txtNome.setText(ep.getNome());
		        txtDocument.setText(ep.getDocumento());
		        txtTel.setText(ep.getTelefone());
		        txtEmail.setText(ep.getEmail());
		        txtCidade.setText(ep.getCidade());
		        txaEndereco.setText(ep.getEndereco());
		        txtLogin.setText(ep.getLogin());
		        pswSenha.setText(ep.getSenha());
		        if(ep.getNivel() == 0) {
		        	cbxNivel.setValue("Vendedor");
		        }else {
		        	cbxNivel.setValue("Master");
		        }
		        
				
				break;
			case 3:
				paneAccess.setDisable(true);
				lblTitulo.setText("Detalhes Fornecedor");
				lblDocument.setText("CNPJ:");
				
				ProviderDAO prDAO = new ProviderDAO();
				Provider pr = prDAO.getProvider(id);
				
		        txtNome.setText(pr.getNome());
		        txtDocument.setText(pr.getDocumento());
		        txtTel.setText(pr.getTelefone());
		        txtEmail.setText(pr.getEmail());
		        txtCidade.setText(pr.getCidade());
		        txaEndereco.setText(pr.getEndereco());
		        
				break;
			default:
				break;
		}
		
	}
}
