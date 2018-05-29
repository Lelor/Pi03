package address.view;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import com.sun.glass.events.MouseEvent;
import com.sun.glass.ui.Accessible.EventHandler;

import address.MainApp;
import address.model.Instrument;
import address.services.ObsLists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
 
public class MenuScreenController implements Initializable{
	
	private MainApp mainApp;
	
	@FXML private TableView<Instrument> tableView;
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
		idInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, Integer>("id"));
		nameInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, String>("nome"));
		brandInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, String>("marca"));
		priceInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, BigDecimal>("valorLocacao"));
		availableInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, String>("status"));
		
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
	
	public ObservableList<Instrument> getInstrument(){
		
		ObservableList<Instrument> instrument = FXCollections.observableArrayList();

		instrument.add(new Instrument(1, "Guitarra Stratocaster Fender", "Fender", new BigDecimal(1000.00), "1"));
		instrument.add(new Instrument(1, "Guitarra Stratocaster Fender", "Fender", new BigDecimal(1000.00), "Disponível"));

		return instrument;
		
	}
}
