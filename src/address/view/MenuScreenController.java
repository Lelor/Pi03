package address.view;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;

import address.MainApp;
import address.model.Instrument;
import address.services.ObsLists;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
 
public class MenuScreenController implements Initializable{
	
	private MainApp mainApp;
	
	int id_slc_view;
	ArrayList<Integer> ids_selected = new ArrayList<Integer>();
	
	public MenuScreenController(int id_slc_view, ArrayList<Integer> ids_selected) {
		this.id_slc_view = id_slc_view;
		this.ids_selected = ids_selected;
	}
	
	@FXML private TableView<Instrument> tableView;
	@FXML private TableColumn<Instrument, SimpleBooleanProperty> selectRow;
	@FXML private TableColumn<Instrument, Integer> idInstrument;
	@FXML private TableColumn<Instrument, String> nameInstrument;
	@FXML private TableColumn<Instrument, String> brandInstrument;
	@FXML private TableColumn<Instrument, BigDecimal> priceInstrument;
	@FXML private TableColumn<Instrument, String> availableInstrument;
	@FXML private TableColumn<Instrument, String> availableInstrumentId;
	@FXML private ImageView logoImg;
	
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
    	// percorre table view
    	
    	ArrayList<Integer> ids_selecteds = new ArrayList<Integer>();
		
    	for (Instrument in : tableView.getItems()) {
    		if( in.getSelected().getValue()) { // se checkbox estiver checado/true entao insere valor no array 
    			ids_selecteds.add(in.getId());
    		}
		}
		
		if(ids_selecteds.isEmpty()){
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
		
		// set logo
		File file = new File("src/images/system/logo.png");
        Image image = new Image(file.toURI().toString());
        logoImg.setImage(image);
		
		ObsLists ol = new ObsLists();
		
		// configurando as colunas na tabela -----
		selectRow.setCellValueFactory(new PropertyValueFactory<Instrument, SimpleBooleanProperty>("selected"));
		idInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, Integer>("id"));
		nameInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, String>("nome"));
		brandInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, String>("marca"));
		priceInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, BigDecimal>("valorLocacao"));
		availableInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, String>("status"));
		availableInstrumentId.setCellValueFactory(new PropertyValueFactory<Instrument, String>("statusId"));

		// adiciona campo de checkbox à lista de instrumentos com callback para mudar o value de false para true
//		selectRow.setCellFactory(CheckBoxTableCell.forTableColumn(
//				
//			new Callback<Integer, ObservableValue<Boolean>>() {
//			    @Override
//			    public ObservableValue<Boolean> call(Integer param) {
//    				return tableView.getItems().get(param).getSelected();
//			    }
//			}
//		));
		
		// Seta checkbox nas cols. Desabilita se instrumentos estiver indisponivel
		selectRow.setCellFactory(column -> {
	        return new TableCell<Instrument, SimpleBooleanProperty>() {
	        	
	            @Override
	            public void updateItem(SimpleBooleanProperty item, boolean empty) {
	                super.updateItem(item, empty);

	                TableRow<Instrument> currentRow = getTableRow();
	                CheckBox checkBox = new CheckBox();
	                checkBox.setAlignment(Pos.CENTER);
	                setAlignment(Pos.CENTER);
	                
	                // veridica se existe dados, se nao é uma linha vazia
	                if (empty || item == null) { // se estiver vazio, seta a linha como nulla
	                    setGraphic(null);
	                } else { //se nao for vazio:
	                	
	                	//seta o checkbox na linha
	                    setGraphic(checkBox);
	                    
	                    //verifica se havia ids selecionados ao voltar da tela de visualizacao de produto
	                    if (ids_selected != null ) {
	                    	for(int i = 0; i < ids_selected.size(); i++) {
	                    		if(currentRow.getItem().getId() == ids_selected.get(i)) { 
	                    			//se tiver, verifica quais e seta como true novamente
	                    			checkBox.setSelected(true);
	    		        			item.setValue(true);
	                    		}
	                    	}
	                	}
	                    
	                    // verifica se veio um id valido da tela de visualizaçao de produto ao clicar no botao selecionar da mesma
		        		if(id_slc_view == currentRow.getItem().getId()) {
		        			checkBox.setSelected(true);
		        			item.setValue(true);
		        		}
	                }
	                
	                //add evento que muda o valor do tableCell para true ou false ao checar chekbox
	                checkBox.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
	                	if(checkBox.isSelected()) {
	                		item.setValue(false);
	                	}else{
	                		item.setValue(true);
	                	}
	                });
	                
	                if (currentRow.getItem() != null && !empty) { //verifica se o instrumento existe
	                    if (currentRow.getItem().getStatusId() != 1) { //verifica se o status é diferente de disponivel
	                    	//desabilita a tableView
	                        this.setDisable(true);
	                        setStyle(tableView.getStyle());
	                    }
	                } else {
	                    setStyle(tableView.getStyle());
	                }
	            }

	        };
		});
		
		tableView.setItems(ol.getListInstrument());
				
		// evento de double click na linha
		tableView.setRowFactory( tv -> {
		    TableRow<Instrument> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	
		        	Instrument rowData = row.getItem();
		        	int id = rowData.getId();
		        	
		        	ArrayList<Integer> ids_selecteds = new ArrayList<Integer>();
		    		
		        	for (Instrument in : tableView.getItems()) {
		        		if( in.getSelected().getValue()) { // se checkbox estiver checado/true entao insere valor no array 
		        			ids_selecteds.add(in.getId());
		        		}
		    		}
		        	
		        	try {
						mainApp.showProductDetailScreen(id, ids_selecteds);
					} catch (IOException e) {

						System.out.println(e.getMessage().toString());
					}
		        }
		    });
		    return row ;
		});
	}
	
}
