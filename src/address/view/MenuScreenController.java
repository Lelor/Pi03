package address.view;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import address.MainApp;
import address.model.Instrument;
import address.services.ObsLists;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
 
public class MenuScreenController implements Initializable{
	
	private MainApp mainApp;
	
	@FXML private TableView<Instrument> tableView;
	@FXML private TableColumn<Instrument, Boolean> selectRow;
	@FXML private TableColumn<Instrument, Integer> idInstrument;
	@FXML private TableColumn<Instrument, String> nameInstrument;
	@FXML private TableColumn<Instrument, String> brandInstrument;
	@FXML private TableColumn<Instrument, BigDecimal> priceInstrument;
	@FXML private TableColumn<Instrument, String> availableInstrument;
	
    @FXML
    protected void showAddProductSceen(ActionEvent event) throws IOException {
    	mainApp.showAddProductScreen();
    }
    
    @FXML
    protected void lbExitHandler() throws IOException {
    	mainApp.showLoginScreen();
    }
    
    
    @FXML
    protected void rentInstrument(ActionEvent event) throws IOException {
    	
		ArrayList<Integer> ids = new ArrayList<Integer>();
		
    	// percorre table view
    	for (Instrument in : tableView.getItems()) {
    	
    		if( in.getSelected().getValue()) { // se checkbox estiver checado/true entao insere valor no array 
    			ids.add(in.getId());
    		}

		}
		
		if(ids.isEmpty()){
			JOptionPane.showMessageDialog(null, "Escolha ao menos um produto", "Alerta!", 2);
		}else{
			//TODO Implements new screen
			JOptionPane.showMessageDialog(null, "Abre tela de locação");
			
		}
    	
    }
    
    public void setMainApp(MainApp mainApp){
    	this.mainApp = mainApp;
    }
    
    /**
     * Inicializa classe de controle
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ObsLists ol = new ObsLists();
		
		// configurando as colunas na tabela -----
		selectRow.setCellValueFactory(new PropertyValueFactory<Instrument, Boolean>("selected"));
		idInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, Integer>("id"));
		nameInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, String>("nome"));
		brandInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, String>("marca"));
		priceInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, BigDecimal>("valorLocacao"));
		availableInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, String>("status"));
		
		// adiciona campo de checkbox à lista de instrumentos
		selectRow.setCellFactory(CheckBoxTableCell.forTableColumn(
			
			new Callback<Integer, ObservableValue<Boolean>>() {
			    @Override
			    public ObservableValue<Boolean> call(Integer param) {
			        return tableView.getItems().get(param).getSelected();
			    }
			})
			
		);
		
		tableView.setItems(ol.getListInstrument());
		
		// evento de double click na linha
		tableView.setRowFactory( tv -> {
		    TableRow<Instrument> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	
		        	Instrument rowData = row.getItem();
		        	int id = rowData.getId();
		        	
		        	try {
						mainApp.showProductDetailScreen(id);
					} catch (IOException e) {

						System.out.println(e.getMessage().toString());
					}
		        }
		    });
		    return row ;
		});
	}
	
}
