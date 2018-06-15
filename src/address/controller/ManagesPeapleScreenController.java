package address.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import address.MainApp;
import address.model.Client;
import address.model.Employee;
import address.model.Instrument;
import address.model.Login;
import address.model.Provider;
import address.services.ObsLists;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManagesPeapleScreenController implements Initializable {
	
	private MainApp mainApp;
	
    public void setMainApp(MainApp mainApp){
    	this.mainApp = mainApp;
    }
    
	// inicializa elementos ---
	
	//elementos cliente
	@FXML private TableView<Client> tableViewClient;
	@FXML private TableColumn<Client, Integer> idClient;
	@FXML private TableColumn<Client, String> nameClient;
	@FXML private TableColumn<Client, String> cpfClient;
	@FXML private TableColumn<Client, String> emailClient;
	@FXML private TableColumn<Client, String> phoneClient;
	
	//elementos funcionario
	@FXML private TableView<Employee> tableViewEmployee;
	@FXML private TableColumn<Employee, Integer> idEmployee;
	@FXML private TableColumn<Employee, String> nameEmployee;
	@FXML private TableColumn<Employee, String> cpfEmployee;
	@FXML private TableColumn<Employee, String> emailEmployee;
	@FXML private TableColumn<Employee, String> phoneEmployee;
	
	//elementos funcionario
	@FXML private TableView<Provider> tableViewProvider;
	@FXML private TableColumn<Provider, Integer> idProvider;
	@FXML private TableColumn<Provider, String> nameProvider;
	@FXML private TableColumn<Provider, String> cnpjProvider;
	@FXML private TableColumn<Provider, String> emailProvider;
	@FXML private TableColumn<Provider, String> phoneProvider;
	
	// campos ----
	@FXML private TextField txtSearch;
	@FXML private Tab tabEmployee;
	
    @FXML
    protected void goBackHandler(ActionEvent event) throws IOException {
    	mainApp.showMainMenu(0, null);
    }
    
    @FXML
    protected void searchAction() throws IOException {
    	search();
    }
    
    @FXML
    public void onEnter(ActionEvent ae){
    	search();
    }
    
    @FXML
    public void showAddClientScreen(ActionEvent ae) throws IOException{
    	mainApp.showAddPersonScreen(1);
    }
    
    @FXML
    public void showAddEmployeeScreen(ActionEvent ae) throws IOException{
    	mainApp.showAddPersonScreen(2);
    }
    
    @FXML
    public void showAddProviderScreen(ActionEvent ae) throws IOException{
    	mainApp.showAddPersonScreen(3);
    }
    
    /**
     * Realiza busca.
     */
    protected void search() {
    	String search = txtSearch.getText();
    	
    	updateLists(search);
    }
    
    /**
     * Atualiza lista de Clientes, Funcionários e Fornecedores.
     * @param search - String de busca.
     */
    protected void updateLists(String search) {
    	
		ObsLists ol = new ObsLists();
		
		try {
			tableViewClient.setItems(ol.getListClient(search));
			tableViewEmployee.setItems(ol.getListEmployee(search));
			tableViewProvider.setItems(ol.getListProvider(search));
		} catch (Exception e) {
			System.out.println("Erro ao recuperar lista");
		}
		
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		if(Login.nivel == 0) {
			tabEmployee.setDisable(true);
		}
		
		System.out.println(Login.nameUser);
		
		// configura tabela de clientes
		idClient.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));
		nameClient.setCellValueFactory(new PropertyValueFactory<Client, String>("nome"));
		cpfClient.setCellValueFactory(new PropertyValueFactory<Client, String>("documento"));
		emailClient.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
		phoneClient.setCellValueFactory(new PropertyValueFactory<Client, String>("telefone"));
		
		// configura tabela de funcionário
		idEmployee.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
		nameEmployee.setCellValueFactory(new PropertyValueFactory<Employee, String>("nome"));
		cpfEmployee.setCellValueFactory(new PropertyValueFactory<Employee, String>("documento"));
		emailEmployee.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
		phoneEmployee.setCellValueFactory(new PropertyValueFactory<Employee, String>("telefone"));
		
		// configura tabela de fornecedor
		idProvider.setCellValueFactory(new PropertyValueFactory<Provider, Integer>("id"));
		nameProvider.setCellValueFactory(new PropertyValueFactory<Provider, String>("nome"));
		cnpjProvider.setCellValueFactory(new PropertyValueFactory<Provider, String>("documento"));
		emailProvider.setCellValueFactory(new PropertyValueFactory<Provider, String>("email"));
		phoneProvider.setCellValueFactory(new PropertyValueFactory<Provider, String>("telefone"));
		
		updateLists("");
		
		// evento de double click na linha
		tableViewClient.setRowFactory( tv -> {
		    TableRow<Client> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	
		        	Client rowData = row.getItem();
		        	int id = rowData.getId();
		        	
		        	try {
						mainApp.showPersonDetailScreen(id, 1);
					} catch (IOException e) {

						System.out.println(e.toString());
					}
		        }
		    });
		    return row ;
		});
		
		// evento de double click na linha
		tableViewEmployee.setRowFactory( tv -> {
		    TableRow<Employee> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	
		        	Employee rowData = row.getItem();
		        	int id = rowData.getId();
		        	
		        	try {
						mainApp.showPersonDetailScreen(id, 2);
					} catch (IOException e) {

						System.out.println(e.getMessage().toString());
					}
		        }
		    });
		    return row ;
		});
		
		// evento de double click na linha
		tableViewProvider.setRowFactory( tv -> {
		    TableRow<Provider> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	
		        	Provider rowData = row.getItem();
		        	int id = rowData.getId();
		        	
		        	try {
						mainApp.showPersonDetailScreen(id, 3);
					} catch (IOException e) {

						System.out.println(e.getMessage().toString());
					}
		        }
		    });
		    return row ;
		});
		
	}
	
	
}
