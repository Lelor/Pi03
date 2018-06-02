package address.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
 
public class AddProductScreenController implements Initializable {
	
	private MainApp mainApp;
	
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
	
	// variaveis  --------
	int numSerie;
	private String nome, cor, fornecedor, ano, tipo, marca, imgName = "shape.png";
	BigDecimal valorLocacao, valorCompra;
	File fileImage;
	Path from, to;

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
	    	to 	= Paths.get("src/images/instruments/" + imgName);
			
    	} catch (IllegalArgumentException ex) {
    		
    		// seta imagem default ------
    		File fileDefault = new File("src/images/instruments/shape.png");
            Image imageDefault = new Image(fileDefault.toURI().toString());
            imgInstrument.setImage(imageDefault);
            
            imgName = "shape.png";

		}

    }

    @FXML
    protected void registerProduct(ActionEvent event) throws IOException {
    
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
        		
        		if(imgName != "shape.png") {
        			
        			// move imagem
            		Files.copy(from, to);
        		}
        		
        		// cadastra instrumento
        		Instrument in = new Instrument();
        		in.setNumSerie(numSerie);
        		in.setNome(nome);
        		in.setvalorCompra(valorCompra);
        		in.setValorLocacao(valorLocacao);
        		in.setAno(ano);
        		in.setFoto(imgName);
        		in.setNomeFornecedor(fornecedor);
        		in.setCor(cor);
        		in.setTipo(tipo);
        		in.setMarca(marca);
        		
        		InstrumentDAO inDAO = new InstrumentDAO();
        		
        		JOptionPane.showMessageDialog(null, inDAO.insertInstrument(in), "Sucesso", 1);
        		
        		//resta campos
        		restFields();

        		
			} catch (Exception e) {
				
				JOptionPane.showMessageDialog(null, "Houve algum erro! =[");
				System.out.println(e.toString());
			}
    		
    	}
    }
    
    @FXML
    protected void goBackHandler(ActionEvent event) throws IOException {
    	mainApp.showMainMenu(0, null);
    }
    
    @FXML
    protected void btRestFields(ActionEvent event) throws IOException {
    	restFields();
    }
    
    public void restFields() {
    	// reseta campos
		txtNome.clear();
		txtNumSerie.clear();
		txtValorCompra.clear();
		txtValorLocacao.clear();
		txtAno.clear();
		cbCor.setValue("");
		cbTipo.setValue("");
		cbMarca.setValue("");
		cbFornecedor.setValue("");
		
		// seta imagem default ------
		File fileDefault = new File("src/images/instruments/shape.png");
        Image imageDefault = new Image(fileDefault.toURI().toString());
        imgInstrument.setImage(imageDefault);
    }
    
    public void setMainApp(MainApp mainApp){
    	this.mainApp = mainApp;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// seta imagem default ------
		File file = new File("src/images/instruments/shape.png");
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
