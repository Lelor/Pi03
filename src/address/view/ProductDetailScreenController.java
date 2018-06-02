package address.view;

import java.awt.PageAttributes.OriginType;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import address.MainApp;
import address.model.Instrument;
import address.model.InstrumentDAO;
import address.services.ObsLists;
import address.services.Utilities;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
 
public class ProductDetailScreenController implements Initializable {
	
	private MainApp mainApp;
	
	//constante ID do instruemnto -----
	final int ID;
	ArrayList<Integer> ids_selecteds = new ArrayList<Integer>(); // ids selecionados para manter o estado
	
	// construtor recebe id do instrumento ----
	public ProductDetailScreenController(int idInstrument, ArrayList<Integer> ids_selecteds) {
		this.ID = idInstrument;
		this.ids_selecteds = ids_selecteds;
    }

	// create fields -----------
	@FXML private TextField txtNumSerie;
	@FXML private TextField txtNome;
	@FXML private TextField txtAno;
	@FXML private TextField txtValorLocacao;
	@FXML private TextField txtValorCompra;
	@FXML private ComboBox<String> cbTipo;
	@FXML private ComboBox<String> cbMarca;
	@FXML private ComboBox<String> cbCor;
	@FXML private ComboBox<String> cbFornecedor;
	@FXML private ImageView imgInstrument;
	@FXML private Label lblStatus;
	@FXML private Button btSelect;
	
	// variaveis  --------
	int numSerie;
	private String nome, cor, fornecedor, ano, tipo, marca;
	private BigDecimal valorLocacao, valorCompra;
	private File fileImage;
	private Path from, to;
	
	String rootPathImage = "src/images/instruments/";
	
	private String originalImgName = null, imgName = null; // pega o valor no metodo de inicialização
	
    @FXML
    protected void goBackHandler(ActionEvent event) throws IOException {
    	mainApp.showMainMenu(0, ids_selecteds);
    }
    
    @FXML
    protected void selectProduct(ActionEvent event) throws IOException {
    	mainApp.showMainMenu(ID, ids_selecteds);
    }
    
    @FXML
    protected void excludeProduct(ActionEvent event) throws IOException {
    	
		// exclui instrumento
		Instrument in = new Instrument();
		in.setId(ID);
		
		InstrumentDAO inDAO = new InstrumentDAO();
		
		int decision = inDAO.excludeInstrument(ID);
		
		if(decision == 1) {
			JOptionPane.showMessageDialog(null, "Instrumento excluído!", "Alerta!", 3);
			mainApp.showMainMenu(0, ids_selecteds);
		}else {
			JOptionPane.showMessageDialog(null, "Operação cancelada!", "Alerta!", 3);
		}
    }
    
    @FXML
    protected void getImage(ActionEvent event) throws IOException {
    	
    	FileChooser fileChooser = new FileChooser();
    	
    	//Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
    	
    	//Show open file dialog
        fileImage = fileChooser.showOpenDialog(null);

    	try {
    	
    		BufferedImage bufferedImage = ImageIO.read(fileImage);
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			imgInstrument.setImage(image);
			
			// pega o nome do arquivo carregado
			String bufferedImageName = fileImage.getName();
			
			// pega extensao do arquivo carregado
			String extension = "";

			int i = bufferedImageName.lastIndexOf('.');
			if (i > 0) {
			    extension = bufferedImageName.substring(i+1);
			}
			
			//cria um nome randomico
			Utilities ut = new Utilities();
			String rdName = ut.randomName();
			
			//gera novo nome
			imgName = rdName + "." + extension;
			
	    	from = Paths.get(fileImage.toURI());
	    	to 	= Paths.get(rootPathImage + imgName);
			
    	} catch (IllegalArgumentException ex) {
    		
    		// seta imagem default ------
    		File fileDefault = new File(rootPathImage + "shape.png");
            Image imageDefault = new Image(fileDefault.toURI().toString());
            imgInstrument.setImage(imageDefault);
            
            imgName = "shape.png";

		}

    }
    
    @FXML
    protected void updateProduct(ActionEvent event) throws IOException {

    	if (txtNumSerie.getText() == null || txtNome.getText() == null || cbCor.getValue() == null || cbFornecedor.getValue() == null || 
        		txtAno.getText() == null || cbTipo.getValue() == null || cbMarca.getValue() == null || txtValorLocacao.getText() == null || 
        		txtValorCompra.getText() == null)
        	{
        		JOptionPane.showMessageDialog(null, "Preencha todos os campos, por favor", "Erro", 0);
        		
        	} else {
        		
        		// recupera valores --------
            	numSerie	= Integer.parseInt( txtNumSerie.getText() );
            	nome 		= txtNome.getText();
            	cor 		= cbCor.getValue();
            	fornecedor 	= cbFornecedor.getValue();
            	ano 		= txtAno.getText();
            	tipo 		= cbTipo.getValue();
            	marca 		= cbMarca.getValue();
            	valorLocacao= new BigDecimal( txtValorLocacao.getText() );
            	valorCompra = new BigDecimal( txtValorCompra.getText() );
            	
            	try {
            		
            		//se a imagem da imgView for diferente da original e
            		//diferente di guitar_shape, deleta arquivo original e sobe outro
            		if(imgName != originalImgName && imgName != "shape.png") {
            			try {
            				
            				//apaga arquivo
            				Path pathOrImg = Paths.get(rootPathImage + originalImgName);
            			    Files.delete(pathOrImg);
            			    
            			    // move imagem
                    		Files.copy(from, to);
            			    
            			} catch (Exception e) {
            			    System.out.println("Nao apagaou: " + e.toString());
            			}
            		}
            		
            		// atualiza instrumento
            		Instrument in = new Instrument();
            		in.setNome(nome);
            		in.setvalorCompra(valorCompra);
            		in.setValorLocacao(valorLocacao);
            		in.setAno(ano);
            		in.setFoto(imgName);
            		in.setNomeFornecedor(fornecedor);
            		in.setCor(cor);
            		in.setTipo(tipo);
            		in.setMarca(marca);
            		in.setId(ID);
            		
            		InstrumentDAO inDAO = new InstrumentDAO();
            		
            		JOptionPane.showMessageDialog(null, inDAO.updateInstrument(in), "Alerta", 1);
            		
    			} catch (Exception e) {
    				
    				JOptionPane.showMessageDialog(null, "Houve algum erro! =[", "Erro", 2);
    				System.out.println(e.toString());
    			}
        		
        	}
    	
    }
    
    public void setMainApp(MainApp mainApp){
    	this.mainApp = mainApp;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Instrument in = new Instrument();
		InstrumentDAO inDAO = new InstrumentDAO();

		in = inDAO.getInstrument(ID);
		
		txtNumSerie.setText(String.valueOf(in.getNumSerie()));
		txtNome.setText(in.getNome());
		txtAno.setText(in.getAno());
		txtValorLocacao.setText(String.valueOf(in.getValorLocacao()));
		txtValorCompra.setText(String.valueOf(in.getvalorCompra()));
		cbTipo.setValue(in.getTipo());
		cbMarca.setValue(in.getMarca());
		cbCor.setValue(in.getCor());
		cbFornecedor.setValue(in.getNomeFornecedor());
		lblStatus.setText(in.getStatus());
		
		originalImgName = in.getFoto();
		imgName = in.getFoto();
		
		if(in.getStatusId() == 2) {
			lblStatus.setStyle("-fx-background-color: orange");
			btSelect.setDisable(true);
		}else if(in.getStatusId() == 3) {
			btSelect.setDisable(true);
			lblStatus.setStyle("-fx-background-color: red");
		}
		
		//set image ----
		File file = new File("src/images/instruments/" + in.getFoto());
        Image image = new Image(file.toURI().toString());
        imgInstrument.setImage(image);
        
        ObsLists obs = new ObsLists();
        // preecnhe Combobox de fornecedores ----
        cbFornecedor.setItems(obs.getMarcaTipoCorFornecInstrument("fornecedor"));
        // preecnhe Combobox de Cor ----
        cbCor.setItems(obs.getMarcaTipoCorFornecInstrument("cor"));
        // preecnhe Combobox de Marca ----
        cbMarca.setItems(obs.getMarcaTipoCorFornecInstrument("marca"));
        // preecnhe Combobox de Tipo ----
        cbTipo.setItems(obs.getMarcaTipoCorFornecInstrument("tipo"));
	}
}
