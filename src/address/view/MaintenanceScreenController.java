package address.view;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import address.MainApp;
import address.model.Client;
import address.model.Instrument;
import address.model.Maintenance;
import address.model.MaintenanceDAO;
import address.services.ObsLists;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MaintenanceScreenController implements Initializable {
	
	private MainApp mainApp;
	
    public void setMainApp(MainApp mainApp){
    	this.mainApp = mainApp;
    }
    
    // tabela de manutenção ativas
 	@FXML private TableView<Maintenance> tableViewActive;
 	@FXML private TableColumn<Maintenance, Integer> idManutencaoA;
 	@FXML private TableColumn<Maintenance, Integer> idInstrumentoA;
 	@FXML private TableColumn<Maintenance, String> nomeInstrumentA;
 	@FXML private TableColumn<Maintenance, String> dataEntradaA;
 	@FXML private TableColumn<Maintenance, String> dataSaidaA;
 	
     // tabela de manutenção inativas
 	@FXML private TableView<Maintenance> tableViewClosed;
 	@FXML private TableColumn<Maintenance, Integer> idManutencaoC;
 	@FXML private TableColumn<Maintenance, Integer> idInstrumentoC;
 	@FXML private TableColumn<Maintenance, String> nomeInstrumentC;
 	@FXML private TableColumn<Maintenance, String> dataEntradaC;
	@FXML private TableColumn<Maintenance, String> dataSaidaC;
	
	//campos
	@FXML private TextField txtTipo;
	@FXML private TextField txtValor;
	@FXML private TextArea txaDesc;
	
	//botões
	@FXML private Button btnUpdate;
	@FXML private Button btnClose;
	
	@FXML
    protected void goBackHandler(ActionEvent event) throws IOException {
    	mainApp.showMainMenu(0, null);
    }
	
	@FXML
    protected void updateData(ActionEvent event) throws IOException {
		
		Maintenance mn = new Maintenance();
		MaintenanceDAO mnDAO = new MaintenanceDAO();
    	
		try {
			
			Maintenance mRow = tableViewActive.getSelectionModel().getSelectedItem();
			
			mn.setIdManutencao(mRow.getIdManutencao());
			mn.setAtivo(true);
			mn.setTipo(txtTipo.getText());
			mn.setDescricao(txaDesc.getText());
			
			try {
				mn.setValor(new BigDecimal(txtValor.getText()));
			} catch (Exception e) {
				mn.setValor(new BigDecimal(0));
			}
			
			JOptionPane.showMessageDialog(null, mnDAO.updateMaintenance(mn), "Alerta!", 2);
			
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Selecione uma manutenção", "Alerta!", 2);
		}
    }
	
	@FXML
    protected void closeMaintenance(ActionEvent event) throws IOException {
		
		Maintenance mn = new Maintenance();
		MaintenanceDAO mnDAO = new MaintenanceDAO();
		
		try {
			
			Maintenance mRow = tableViewActive.getSelectionModel().getSelectedItem();
			
			mn.setIdManutencao(mRow.getIdManutencao());
			mn.setIdInstrumento(mRow.getIdInstrumento());
			
			try {
				mn.setValor(new BigDecimal(txtValor.getText()));
				
				if(!txtTipo.getText().isEmpty()) {
					
					mn.setTipo(txtTipo.getText());
					mn.setAtivo(false);
					mn.setDescricao(txaDesc.getText());
					
					JOptionPane.showMessageDialog(null, mnDAO.updateMaintenance(mn), "Alerta!", 2);
					
					updateListActive(true);
					updateListClosed(false);
					
				}else {
					JOptionPane.showMessageDialog(null, "Preencha o campo TIPO", "Alerta!", 2);
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Preencha um valor válido no campo VALOR", "Alerta!", 2);
			}
			
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Selecione uma manutenção", "Alerta!", 2);
		}

	}
    
    /**
     * Atualiza lista de manutenções ativas
     * @param active 
     */
    protected void updateListActive(boolean active) {
		
    	ObsLists ol = new ObsLists();
    	
    	try {
    		tableViewActive.setItems(ol.getListMaintenance(true));
		} catch (Exception e) {
			System.out.println("Erro listagem ATIVOS: " + e.toString());
		}
		
    }
    
    /**
     * Atualiza lista de manutenções inativas
     * @param active
     */
    protected void updateListClosed(boolean active) {
		
    	ObsLists ol = new ObsLists();
    	
    	try {
    		tableViewClosed.setItems(ol.getListMaintenance(false));
		} catch (Exception e) {
			System.out.println("Erro listagem FECHADOS: " + e.toString());
		}
		
    }
    
    /**
     * Apaga valores dos campos.
     */
    protected void eraseFields() {
    	txtTipo.setText("");
    	txtValor.setText("0.0");
    	txaDesc.setText("");
    }
    
    /**
     * Preenche campos com valores do banco.
     * @param idMn
     */
    protected void fillFields(int idMn) {
    	
    	MaintenanceDAO mnDAO = new MaintenanceDAO();
    	Maintenance mn = mnDAO.getMaintenance(idMn);
    	
    	txtTipo.setText(mn.getTipo());
    	txtValor.setText("0.0");
    	txaDesc.setText(mn.getDescricao());
    	
    	if(mn.getValor() != null) {
    		txtValor.setText(String.valueOf(mn.getValor()));
    	}
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// configurando as colunas na tabela Manutençao Ativa -----
		idManutencaoA.setCellValueFactory(new PropertyValueFactory<Maintenance, Integer>("idManutencao"));
		idInstrumentoA.setCellValueFactory(new PropertyValueFactory<Maintenance, Integer>("idInstrumento"));
		nomeInstrumentA.setCellValueFactory(new PropertyValueFactory<Maintenance, String>("nomeinstrumento"));
		dataEntradaA.setCellValueFactory(new PropertyValueFactory<Maintenance, String>("dataEntrada"));
		dataSaidaA.setCellValueFactory(new PropertyValueFactory<Maintenance, String>("dataSaida"));
		
		// configurando as colunas na tabela Manutençao fechada -----
		idManutencaoC.setCellValueFactory(new PropertyValueFactory<Maintenance, Integer>("idManutencao"));
		idInstrumentoC.setCellValueFactory(new PropertyValueFactory<Maintenance, Integer>("idInstrumento"));
		nomeInstrumentC.setCellValueFactory(new PropertyValueFactory<Maintenance, String>("nomeinstrumento"));
		dataEntradaC.setCellValueFactory(new PropertyValueFactory<Maintenance, String>("dataEntrada"));
		dataSaidaC.setCellValueFactory(new PropertyValueFactory<Maintenance, String>("dataSaida"));
		
		updateListActive(true);
		updateListClosed(false);
		
		//desabilita campos e botoes
		btnUpdate.setDisable(true);
		btnClose.setDisable(true);
    	txtTipo.setEditable(false);
    	txtValor.setEditable(false);
    	txaDesc.setEditable(false);
		
		tableViewActive.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	        	
	    		btnUpdate.setDisable(false);
	    		btnClose.setDisable(false);
	    		
	        	txtTipo.setEditable(true);
	        	txtValor.setEditable(true);
	        	txaDesc.setEditable(true);
	    		
	        	tableViewClosed.getSelectionModel().clearSelection();
	        	
	        	int idMn = newSelection.getIdManutencao();
	        	
	        	eraseFields();
	        	fillFields(idMn);
	        }
		});
		
		tableViewClosed.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	        	
	    		btnUpdate.setDisable(true);
	    		btnClose.setDisable(true);
	    		
	        	txtTipo.setEditable(false);
	        	txtValor.setEditable(false);
	        	txaDesc.setEditable(false);
	        	
	        	tableViewActive.getSelectionModel().clearSelection();
	        	
	        	int idMn = newSelection.getIdManutencao();
	        	
	        	eraseFields();
	        	fillFields(idMn);
	        }
		});
	}

}
