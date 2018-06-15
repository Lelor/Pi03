package address.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import address.MainApp;
import address.model.Client;
import address.model.Instrument;
import address.model.Login;
import address.model.Rent;
import address.model.RentDAO;
import address.services.ObsLists;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class RentScreenController implements Initializable{
	
	private MainApp mainApp;
	
	//variaveis ----
	private boolean pago;
	private String descricao;
	
	private ArrayList<Integer> idsInstrumentsRent = new ArrayList<Integer>();
	
    public void setMainApp(MainApp mainApp){
    	this.mainApp = mainApp;
    }
	
	public RentScreenController(ArrayList<Integer> idsInstrumentsRent) {
		this.idsInstrumentsRent = idsInstrumentsRent;
	}
	
	// inicializa elementos ---
	
	//elementos cliente
	@FXML private TableView<Client> tableViewClient;
	@FXML private TableColumn<Client, Integer> idClient;
	@FXML private TableColumn<Client, String> nameClient;
	@FXML private TableColumn<Client, String> cpfClient;
	
	//elementos instrumento
	@FXML private TableView<Instrument> tableViewInstrument;
	@FXML private TableColumn<Instrument, Integer> idInstrument;
	@FXML private TableColumn<Instrument, String> nomeInstrument;
	@FXML private TableColumn<Instrument, SimpleStringProperty> dateInstrumentDevo;
	@FXML private TableColumn<Instrument, BigDecimal> valorInstrumentoLoca;
	
	//campos
	@FXML private TextField txtDesconto;
	@FXML private TextField txtTotal;
	@FXML private TextField txtSearch;
	@FXML private TextArea txtDescricao;
	@FXML private CheckBox cbPago;
	
    @FXML
    protected void goBackHandler(ActionEvent event) throws IOException {
    	mainApp.showMainMenu(0, idsInstrumentsRent);
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
    protected void doRent(ActionEvent event) throws IOException {
    	rent();
    }
    
    public void rent() {
    	
    	//variables ----
    	int idClient = 0;
    	ArrayList<Integer> idsInstruments = new ArrayList<Integer>();
		ArrayList<String> datesDev = new ArrayList<String>();
		BigDecimal desconto;
		String descricao;
		boolean pago;
    	
    	try {
    		
    		//id cliente
    		Client clRow = tableViewClient.getSelectionModel().getSelectedItem();
    		idClient = clRow.getId();
    		
    		try {
    			
    			for (Instrument in : tableViewInstrument.getItems()) {
    				datesDev.add(in.getDataDevolucao().getValue());
    				idsInstruments.add(in.getId());
    			}
    			
    			try {
    				desconto = new BigDecimal(txtDesconto.getText());
    			} catch (Exception e) {
    				desconto = new BigDecimal(0);
    			}
    			
    			descricao = txtDescricao.getText();
    			pago = false;
    			
    			if(cbPago.isSelected()) {
    				pago = true;
    			}
    			
    			Rent rt = new Rent();
    			RentDAO rtDAO = new RentDAO();
    			
    			rt.setIdFuncionario(Login.idUser);
    			rt.setIdCliente(idClient);
    			rt.setIdInstrumentList(idsInstruments);
    			rt.setDataDevolucaoList(datesDev);
    			rt.setDesconto(desconto);
    			rt.setDescricao(descricao);
    			rt.setPago(pago);
    			
    			// grava tudo sa merda
    			JOptionPane.showMessageDialog(null, rtDAO.insertRent(rt), "Alert!", 2);
    			mainApp.showMainMenu(0, null);
    			
    		} catch (Exception e) {
    			JOptionPane.showMessageDialog(null, "Informe a(s) data(s) de devolução", "Alerta!", 2);
    		}
    		
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Selecione um cliente", "Alerta!", 2);
		}
    }
    
    /**
     * Atualiza os campos referentes ao valor total da locação.
     */
    public void updateTotal() {

    	BigDecimal valorTotal = new BigDecimal(0);
    	BigDecimal valTotalMult = new BigDecimal(0);
    	BigDecimal valMultDias = new BigDecimal(0);
    	BigDecimal desconto = new BigDecimal(0);
    	
    	if(txtDesconto.getText() != null) {
    		desconto = new BigDecimal(0);
    		try {
    			desconto = new BigDecimal(txtDesconto.getText());
			} catch (Exception e) {
				desconto = new BigDecimal(0);
			}
    	}

    	LocalDate now = LocalDate.now();
    	
		//preenche total
		for (Instrument in : tableViewInstrument.getItems()) {
			
			if(in.getDataDevolucao().getValue() != "0") {
					
				//pega a data de devolucao da tabela
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate dateDevolucao = LocalDate.parse(in.getDataDevolucao().getValue(), dtf);
				
				// pega numero de dias locado
				long numDaysLong = Duration.between(now.atStartOfDay(), dateDevolucao.atStartOfDay()).toDays();
				
				BigDecimal numDays = new BigDecimal(numDaysLong);
				BigDecimal valorLocacao = in.getValorLocacao();
				
				valMultDias = valorLocacao.multiply(numDays);
				
				valTotalMult = valTotalMult.add(valMultDias);
				
			}
		}
		
		valorTotal = valTotalMult.subtract(desconto);
		
		txtTotal.setText("R$ " + String.valueOf(valorTotal));
		
    }
    
    /**
     * Realiza busca.
     */
    protected void search() {
    	String search = txtSearch.getText();
    	
    	updateListSearch(search);
    }
    
    /**
     * Atualiza lista de Clientes.
     * @param search - String de busca.
     */
    protected void updateListSearch(String search) {
    	
		ObsLists ol = new ObsLists();
		
		try {
			tableViewClient.setItems(ol.getListClientRent(search, "cliente"));
		} catch (Exception e) {
			System.out.println("Erro ao recuperar lista");
		}
		
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// configurando as colunas na tabela Cliente -----
		idClient.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));
		nameClient.setCellValueFactory(new PropertyValueFactory<Client, String>("nome"));
		cpfClient.setCellValueFactory(new PropertyValueFactory<Client, String>("documento"));
		
		// configura tabela de intrumentos
		idInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, Integer>("id"));
		nomeInstrument.setCellValueFactory(new PropertyValueFactory<Instrument, String>("nome"));
		dateInstrumentDevo.setCellValueFactory(new PropertyValueFactory<Instrument, SimpleStringProperty>("dataDevolucao"));
		valorInstrumentoLoca.setCellValueFactory(new PropertyValueFactory<Instrument, BigDecimal>("valorLocacao"));
		
		ObsLists ol = new ObsLists();
		
		try {
			tableViewInstrument.setItems(ol.getListInstrumentRent(idsInstrumentsRent));
		} catch (Exception e) {
			System.out.println("Erro ao recuperar lista");
		}
		
		updateListSearch("");
		
		dateInstrumentDevo.setCellFactory(column -> {
	        return new TableCell<Instrument, SimpleStringProperty>() {
	        	
				@Override
	            public void updateItem(SimpleStringProperty item, boolean empty) {
	                super.updateItem(item, empty);
	                
	                TableRow<Instrument> currentRow = getTableRow();
	                DatePicker dtp = new DatePicker();
	                
	                // veridica se existe dados, se nao é uma linha vazia
	                if (empty || item == null) { // se estiver vazio, seta a linha como nulla
	                    setGraphic(null);
	                } else { //se nao for vazio:
	                	//seta o checkbox na linha
	                    setGraphic(dtp);
	                }
	                
	                dtp.valueProperty().addListener((ov, oldValue, newValue) -> {
	                	
	                	LocalDate now = LocalDate.now();
	                	LocalDate dateDev = dtp.getValue();
	                	
	                	if(dateDev.isBefore(now.plusDays(1))) {
	                		JOptionPane.showMessageDialog(null, "Escolha uma data válida!", "Alerta!", 3);
	                		dtp.setValue(now.plusDays(1));
	                	}else {	
	                		
	                		item.setValue(String.valueOf(dateDev));
	                		
	                		updateTotal();
	                		
	                	}
	                });
	                
	            }

	        };
		});
		
		// quando o campo perde focus atuliza valor total
		txtDesconto.focusedProperty().addListener((ov, oldV, newV) -> {
           if (!newV) { // focus lost
              updateTotal();
           }
        });
		
	}

}
