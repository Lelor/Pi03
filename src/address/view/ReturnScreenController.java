package address.view;

import java.io.File;
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
import address.model.Rent;
import address.model.RentDAO;
import address.services.ObsLists;
import address.services.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ReturnScreenController implements Initializable{
	
	private MainApp mainApp;
	
    public void setMainApp(MainApp mainApp){
    	this.mainApp = mainApp;
    }
    
	// inicializa elementos ---
    
    // tabela de locações ativas
	@FXML private TableView<Rent> rentViewActive;
	@FXML private TableColumn<Rent, Integer> idCientActive;
	@FXML private TableColumn<Rent, String> nameClientActive;
	@FXML private TableColumn<Rent, String> dateDoItActive;
	
    // tabela de locações inativas
	@FXML private TableView<Rent> rentViewDesactive;
	@FXML private TableColumn<Rent, Integer> idCientDesactive;
	@FXML private TableColumn<Rent, String> nameClientDesactive;
	@FXML private TableColumn<Rent, String> dateDoItDesactive;
	
    // tabela de instrumentos
	@FXML private TableView<Rent> rentViewInstrument;
	@FXML private TableColumn<Rent, Integer> idInstrument;
	@FXML private TableColumn<Rent, String> nameInstrument;
	@FXML private TableColumn<Rent, String> dateReturnInstrument;
	@FXML private TableColumn<Rent, BigDecimal> valLocacaoInstrument;
	@FXML private TableColumn<Rent, BigDecimal> multaLocacaoInstrument;
	
	//campos --
	@FXML private Label lblIdRent;
	@FXML private Label lbLNameFunc;
	@FXML private Label lblEfetiva;
	@FXML private Label lblDataRealizado;
	@FXML private TextField txtDesconto;
	@FXML private TextField txtMulta;
	@FXML private TextField txtTotalMulta;
	@FXML private TextField txtValorLocao;
	@FXML private TextField txtTotal;
	@FXML private TextArea txaDescricao;
	@FXML private TextArea txaObservacao;
	@FXML private CheckBox ckbPago;
	@FXML private CheckBox ckbManutencao;
	@FXML private Button btnFechar;
	@FXML private Button btnDevolucao;

	@FXML private ImageView imgArrow;
	
    @FXML
    protected void goBackHandler(ActionEvent event) throws IOException {
    	mainApp.showMainMenu(0, null);
    }
    
    @FXML
    public void onEnter(ActionEvent ae){
    	totalUpdate(lblDataRealizado.getText());
    }
    
    @FXML
    protected void closeRent(ActionEvent event) throws IOException {
    	
    	RentDAO rtDAO = new RentDAO();
    	
    	try {
    		Rent rtRow = rentViewActive.getSelectionModel().getSelectedItem();
    		
    		JOptionPane.showMessageDialog(null, rtDAO.updateRent(rtRow.getIdLocacao()), "Alerta!", 2);
    		
     		ObsLists obList = new ObsLists();
     		
     		// preenche table view de locações ativas se houver
     		try {
     			rentViewActive.setItems(obList.getListRent(true));
    		} catch (Exception e) {
    			System.out.println(e.toString());
    		}
     		// preenche table view de locações inativas se houver
     		try {
     	 		rentViewDesactive.setItems(obList.getListRent(false));
    		} catch (Exception e) {
    		}
     		
    		
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Selecione uma locação!", "Alerta!", 2);
			
		}
    		
    }
    
    @FXML
    protected void doReturn(ActionEvent event) throws IOException {
    	
    	Rent rt = new Rent();
    	RentDAO rtDAO = new RentDAO();
    	
    	try {
    		
    		Rent rtRow = rentViewActive.getSelectionModel().getSelectedItem();
    		
    		rt.setIdLocacao(rtRow.getIdLocacao());
    		rt.setObservacao(txaObservacao.getText());
    		
    		try {
    			rt.setMulta(new BigDecimal(txtMulta.getText()));
			} catch (Exception e) {
				rt.setMulta(new BigDecimal(0));
			}
    		
    		
    		try {
    			
        		rtRow = rentViewInstrument.getSelectionModel().getSelectedItem();
    			rt.setIdInstrument(rtRow.getIdInstrument());
    			
    			// devolve o instrumento
    			JOptionPane.showMessageDialog(null, rtDAO.updateInstrumentsRent(rt, ckbManutencao.isSelected()), "Alerta!", 2);
    			
    			//atualiza tela -----
    			rentUpdate(rt.getIdLocacao());
    			totalUpdate(lblDataRealizado.getText());
    			
 	    		lblEfetiva.setText("____/____/____");
 	    		btnDevolucao.setDisable(true);
 	    		txtValorLocao.setText("R$ 0.0");
 	    		txtMulta.setEditable(false);
 	    		txtMulta.setText("0");
 	    		txaObservacao.setEditable(false);
 	    		txaObservacao.setText("");
 	    		ckbManutencao.setDisable(true);
 	    		ckbManutencao.setSelected(false);
    			
 	        	//verifica se ainda há instrumentos para serem devolvidos
 	        	if(rtDAO.verifyInstrumentsReturn(rt.getIdLocacao())) {
 	        		 btnFechar.setDisable(true);
 	        	}else {
 	        		btnFechar.setDisable(false);
 	        	}
    			
			} catch (Exception e) {

				JOptionPane.showMessageDialog(null, "Selecione um instrumento", "Alerta!", 2);
			
			}
    		
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Selecione uma locação!", "Alerta!", 2);
		}
    	
    }
    
    /**
     * Atualiza dados da locação e lista de instrumentos locados
     * @param idRent - Id da locação
     */
    protected void rentUpdate(int idRent) {
    	
    	RentDAO rtDAO = new RentDAO();
    	Rent rt = rtDAO.getRent(idRent);
    	
    	// campos da locação
    	lbLNameFunc.setText(rt.getNomeFuncionario());
    	txtDesconto.setText(rt.getDesconto().toString());
    	ckbPago.setSelected(rt.isPago());
    	txaDescricao.setText(rt.getDescricao());
    	
    	ObsLists obList = new ObsLists();
    	
    	rentViewInstrument.setItems(obList.getListInstrumentRent(rt.getIdLocacao()));
    }
    
    /**
     * Atualiza informações do instrumento locado.
     * @param idRent - Id da locação.
     * @param idInstrument - Id do instrumento.
     */
    protected void rentInstrumentUpdate(int idRent, int idInstrument) {
    	
    	Utilities ul = new Utilities();
    	RentDAO rtDAO = new RentDAO();
    	Rent rt = rtDAO.getInstrumentRent(idRent, idInstrument);
    	
    	// se nao foi devolvido ainda 
    	if(rt.getDataDevolucaoEfetiva() == null) {
    		lblEfetiva.setText("Não efetiva");
    		btnDevolucao.setDisable(false);
    		txtMulta.setEditable(true);
    		txtMulta.setText("0.0");
    		txaObservacao.setEditable(true);
    		txaObservacao.setText("");
    		ckbManutencao.setVisible(true);
    		ckbManutencao.setDisable(false);
    	} else {
    		lblEfetiva.setText(ul.dateFormatView(rt.getDataDevolucaoEfetiva()));
    		btnDevolucao.setDisable(true);
    		txtMulta.setEditable(false);
    		txtMulta.setText(String.valueOf(rt.getMulta()));
    		txaObservacao.setEditable(false);
    		txaObservacao.setText(rt.getObservacao());
    		ckbManutencao.setVisible(false);
    		ckbManutencao.setDisable(true);
    	}
    	
    }
    
    /**
     * Atualiza os valores totais.
     * @param dataRealizado - Data que foi realizado a locação.
     */
    protected void totalUpdate(String dataRealizado) {
    	
    	BigDecimal valorTotal = new BigDecimal(0);
    	BigDecimal valSubTotal = new BigDecimal(0);
    	BigDecimal valInstrumento = new BigDecimal(0);
    	BigDecimal valXdias = new BigDecimal(0);
    	BigDecimal valMulta = new BigDecimal(0);
    	BigDecimal valDesconto = new BigDecimal(txtDesconto.getText());
    	
    	Utilities ut = new Utilities();
    	LocalDate dateRealizado = ut.dateFormatLocalDate(dataRealizado);

		//preenche total
		for (Rent rt : rentViewInstrument.getItems()) {
					
			LocalDate dateDevolucao = ut.dateFormatLocalDate(rt.getDataDevolucao());
			
			// pega numero de dias locado
			long numDaysLong = Duration.between(dateRealizado.atStartOfDay(), dateDevolucao.atStartOfDay()).toDays();
			BigDecimal numDays = new BigDecimal(numDaysLong);
			BigDecimal valorLocacao = rt.getValorLocacao();
			
			//soma os valores de multa
			valMulta = valMulta.add(rt.getMulta());
			
			//soma os valores X o numero de dias locado
			valXdias = valorLocacao.multiply(numDays);
			valInstrumento = valInstrumento.add(valXdias);
				
		}
		
		valSubTotal = valInstrumento.subtract(valDesconto);
		valorTotal = valSubTotal.add(valMulta);
		
		txtTotal.setText("R$ " + String.valueOf(valorTotal));
		txtTotalMulta.setText("R$ " + String.valueOf(valMulta));
    }
    
    /**
     * Atualiza o valor do instrumento
     * @param dataRealizado - Data em que foi realizado a lozação.
     * @param idInstrument - Id do instrumento que será atualizado.
     */
    protected void valInstrumentUpdate(String dataRealizado, int idInstrument) {
    	
    	BigDecimal valInstrumento = new BigDecimal(0);
    	BigDecimal valXdias = new BigDecimal(0);
    	
    	Utilities ut = new Utilities();
    	LocalDate dateRealizado = ut.dateFormatLocalDate(dataRealizado);

		//preenche total
		for (Rent rt : rentViewInstrument.getItems()) {
			if(rt.getIdInstrument() == idInstrument) {
				LocalDate dateDevolucao = ut.dateFormatLocalDate(rt.getDataDevolucao());
				
				// pega numero de dias locado
				long numDaysLong = Duration.between(dateRealizado.atStartOfDay(), dateDevolucao.atStartOfDay()).toDays();
				BigDecimal numDays = new BigDecimal(numDaysLong);
				BigDecimal valorLocacao = rt.getValorLocacao();
				
				//soma os valores X o numero de dias locado
				valXdias = valorLocacao.multiply(numDays);
				valInstrumento = valInstrumento.add(valXdias);
			}
		}
		
		txtValorLocao.setText("R$ " + valInstrumento.toString());
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// seta imagem de seta  ------
		File fileDefault = new File("src/images/system/arrow.png");
        Image imageDefault = new Image(fileDefault.toURI().toString());
        imgArrow.setImage(imageDefault);
        
        // configurando as colunas na tabela de locações ativas -----
        idCientActive.setCellValueFactory(new PropertyValueFactory<Rent, Integer>("idCliente"));
        nameClientActive.setCellValueFactory(new PropertyValueFactory<Rent, String>("nomeCliente"));
        dateDoItActive.setCellValueFactory(new PropertyValueFactory<Rent, String>("dataRealizacao"));
 		
        // configurando as colunas na tabela de locações inativas -----
        idCientDesactive.setCellValueFactory(new PropertyValueFactory<Rent, Integer>("idCliente"));
        nameClientDesactive.setCellValueFactory(new PropertyValueFactory<Rent, String>("nomeCliente"));
        dateDoItDesactive.setCellValueFactory(new PropertyValueFactory<Rent, String>("dataRealizacao"));
        
    	//configura table de instrumentos
    	idInstrument.setCellValueFactory(new PropertyValueFactory<Rent, Integer>("idInstrument"));
    	nameInstrument.setCellValueFactory(new PropertyValueFactory<Rent, String>("nomeInstrumento"));
    	dateReturnInstrument.setCellValueFactory(new PropertyValueFactory<Rent, String>("dataDevolucao"));
    	valLocacaoInstrument.setCellValueFactory(new PropertyValueFactory<Rent, BigDecimal>("valorLocacao"));
    	multaLocacaoInstrument.setCellValueFactory(new PropertyValueFactory<Rent, BigDecimal>("multa"));
 		
 		ObsLists obList = new ObsLists();
 		
 		// preenche table view de locações ativas se houver
 		try {
 			rentViewActive.setItems(obList.getListRent(true));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
 		// preenche table view de locações inativas se houver
 		try {
 	 		rentViewDesactive.setItems(obList.getListRent(false));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
 		
 		//ao selecionar uma linha das locações, atualiza dados da locação e lista de instrumentos locados
 	    rentViewActive.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
 	        if (newSelection != null) {
 	        	
 	        	RentDAO rtDAO = new RentDAO();
 	        	
 	        	int idRent = newSelection.getIdLocacao();
 	        	lblIdRent.setText(String.valueOf(idRent));
 	        	lblDataRealizado.setText(newSelection.getDataRealizacao());
 	        	
 	        	//verifica se ainda há instrumentos para serem devolvidos
 	        	if(rtDAO.verifyInstrumentsReturn(idRent)) {
 	        		 btnFechar.setDisable(true);
 	        	}else {
 	        		btnFechar.setDisable(false);
 	        	}
 	        	
 	            rentUpdate(idRent);
 	            
 	    		lblEfetiva.setText("____/____/____");
 	    		btnDevolucao.setDisable(true);
 	    		txtValorLocao.setText("R$ 0.0");
 	    		txtMulta.setEditable(false);
 	    		txtMulta.setText("0");
 	    		txaObservacao.setEditable(false);
 	    		txaObservacao.setText("");
 	    		ckbManutencao.setDisable(true);
 	    		ckbManutencao.setSelected(false);
 	    		
 	    		totalUpdate(lblDataRealizado.getText());
 	        }
 	   });
 	    
 	   rentViewDesactive.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	        	
	        	int idRent = newSelection.getIdLocacao();
	        	lblIdRent.setText(String.valueOf(idRent));
	        	lblDataRealizado.setText(newSelection.getDataRealizacao());
	        	btnFechar.setDisable(true);
	        	
	            rentUpdate(idRent);
	            
	    		lblEfetiva.setText("____/____/____");
	    		btnDevolucao.setDisable(true);
	    		txtValorLocao.setText("R$ 0.0");
	    		txtMulta.setEditable(false);
	    		txtMulta.setText("0");
	    		txaObservacao.setEditable(false);
	    		txaObservacao.setText("");
	    		ckbManutencao.setDisable(true);
	    		ckbManutencao.setSelected(false);
	    		
	    		totalUpdate(lblDataRealizado.getText());
	        }
	   });
 	   rentViewInstrument.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	        	
	        	int idInstrument = newSelection.getIdInstrument();
	        	int idRent = Integer.parseInt(lblIdRent.getText());
	            rentInstrumentUpdate(idRent, idInstrument);
	            
	            valInstrumentUpdate(lblDataRealizado.getText(), idInstrument);
	            totalUpdate(lblDataRealizado.getText());
	        }
	    });
 	   
 	   	// quando o campo perde focus atuliza valor total
		txtMulta.focusedProperty().addListener((ov, oldV, newV) -> {
           if (!newV) { // focus lost
        	   totalUpdate(lblDataRealizado.getText());
           }
        });
		
	}

}
