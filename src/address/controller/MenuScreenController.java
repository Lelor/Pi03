package address.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import address.MainApp;
import address.model.Instrument;
import address.model.InstrumentDAO;
import address.model.MaintenanceDAO;
import address.services.ObsLists;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
 
public class MenuScreenController implements Initializable{
	
	private MainApp mainApp;
    
    public void setMainApp(MainApp mainApp){
    	this.mainApp = mainApp;
    }
	
	int id_slc_view;
	ArrayList<Integer> ids_selected = new ArrayList<Integer>();
	ArrayList<Integer> ids_selected_search = new ArrayList<Integer>();
	
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
	
	@FXML private TextField txtSearch;
	@FXML private Button btSearch;
	
    @FXML
    protected void showAddProductSceen(ActionEvent event) throws IOException {
    	mainApp.showAddProductScreen();
    }
    
    @FXML
    protected void showReturnScreen(ActionEvent event) throws IOException {
    	mainApp.showReturnScreen();
    }
    
    @FXML
    protected void showMaintenanceScreen(ActionEvent event) throws IOException {
    	mainApp.showMaintenanceScreen();
    }
    
    @FXML
    protected void showManagesPeapleScreen(ActionEvent event) throws IOException {
    	mainApp.showManagesPeapleScreen();
    }
    
    @FXML
    protected void lbExitHandler() throws IOException {
    	mainApp.showLoginScreen();
    }
    
    @FXML
    protected void searchAction() throws IOException {
    	seach();
    }
    
    @FXML
    public void onEnter(ActionEvent ae){
    	seach();
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
			JOptionPane.showMessageDialog(null, "Escolha ao menos um instrumento para locar", "Alerta!", 2);
		}else{
			mainApp.showRentScreen(ids_selecteds);
		}
    	
    }
    
    @FXML
    protected void maintenenceInstrument(ActionEvent event) throws IOException {
    	// percorre table view
    	
    	ArrayList<Integer> ids_selecteds = new ArrayList<Integer>();
		
    	for (Instrument in : tableView.getItems()) {
    		if( in.getSelected().getValue()) { // se checkbox estiver checado/true entao insere valor no array 
    			ids_selecteds.add(in.getId());
    		}
		}
		
		if(ids_selecteds.isEmpty()){
			JOptionPane.showMessageDialog(null, "Escolha ao menos um instrumento para mover para manuten��o", "Alerta!", 2);
		}else{
			
			MaintenanceDAO mnDAO = new MaintenanceDAO();
			
			if(mnDAO.insertMaintenance(ids_selecteds)) {
				mainApp.showMaintenanceScreen();
			}else {
				JOptionPane.showMessageDialog(null, "Ops! Algo deu errado. =[", "Alerta!", 2);
			}
		}
    	
    }
    
    /**
     * Atualiza tableView com a busca.
     */
    protected void seach() {
    	String search = txtSearch.getText();
    	
    	updateListSearch(search);
    }
    
    /**
     * Atualiza��o da lista de instrumentos.
     * @param search - String de busca.
     */
    protected void updateListSearch(String search) {

    	ids_selected = null;
    			
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
	                
	                // veridica se existe dados, se nao � uma linha vazia
	                if (empty || item == null) { // se estiver vazio, seta a linha como nulla
	                    setGraphic(null);
	                } else { //se nao for vazio:
	                	
	                	//seta o checkbox na linha
	                    setGraphic(checkBox);
	                    
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
	                    if (currentRow.getItem().getStatusId() != 1) { //verifica se o status � diferente de disponivel
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
		
		ObsLists ol = new ObsLists();
		
		try {
			tableView.setItems(ol.getListInstrument(search));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao recuperar lista de instrumentos", "Alerta!", 2);
		}
    }
    
    protected void updateListLoadScreen(String search) {
    	
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
	                
	                // veridica se existe dados, se nao � uma linha vazia
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
	                    
	                    // verifica se veio um id valido da tela de visualiza�ao de produto ao clicar no botao selecionar da mesma
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
	                    if (currentRow.getItem().getStatusId() != 1) { //verifica se o status � diferente de disponivel
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
		
		ObsLists ol = new ObsLists();
		
		try {
			tableView.setItems(ol.getListInstrument(search));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao recuperar lista de instrumentos", "Alerta!", 2);
		}
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
		
		// configurando as colunas na tabela -----
		selectRow.setCellValueFactory(new PropertyValueFactory<Instrument, SimpleBooleanProperty>("selected"));
		idInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, Integer>("id"));
		nameInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, String>("nome"));
		brandInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, String>("marca"));
		priceInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, BigDecimal>("valorLocacao"));
		availableInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, String>("status"));
		availableInstrumentId.setCellValueFactory(new PropertyValueFactory<Instrument, String>("statusId"));

		updateListLoadScreen("");
	}
	
}
