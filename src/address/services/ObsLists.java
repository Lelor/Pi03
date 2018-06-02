package address.services;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import address.model.Client;
import address.model.ClientDAO;
import address.model.Instrument;
import address.model.InstrumentDAO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObsLists {
	
	private String sql, msg;
	private int id;
	private BD bd;
	
	public ObsLists() {
	}
	
	/**
	 * Observable list para listar nome de fornecedores, cores, marcar e tipos de instrumentos.
	 * @param table - tabela do banco para pegar nomes (fornecedor, cor, tipo, marca).
	 * @return - ObservableList com nome do respectivo parametro.
	 */
	public ObservableList<String> getMarcaTipoCorFornecInstrument(String table){
		
		ObservableList<String> item = FXCollections.observableArrayList();
		
		String sql = "SELECT nome FROM " + table;
		
		try {
			bd = new BD();
			bd.getConnection();
			bd.stmt = bd.con.createStatement();
			
			bd.rs = bd.stmt.executeQuery(sql);
			
			while (bd.rs.next()) {
				item.add(bd.rs.getString("nome"));
	        }
			
		} catch (SQLException  e) {
			
			System.out.println("Falha ao carregar! =[");

		}
		finally {
			bd.close();
		}

		return item;
	}

	/**
	 * Lista instrumentos na tela principal.
	 * @return - Observable list com lista de instrumentos.
	 */
	public ObservableList<Instrument> getListInstrument(){
		
		ObservableList<Instrument> obsList = FXCollections.observableArrayList();
		
		InstrumentDAO objDAO = new InstrumentDAO();
		
		Instrument[] obj = objDAO.listInstrument();
		
		for(int i = 0; i < obj.length;i++) {
			obsList.add(new Instrument(obj[i].getId(), obj[i].getNome(), obj[i].getMarca(), obj[i].getValorLocacao(), obj[i].getStatus(), obj[i].getStatusId(), new SimpleBooleanProperty(false)));
		}
	
		return obsList;
		
	}
	
	/**
	 * Observable list para listar clientes na tela de locação.
	 * @return - lista de clientes.
	 */
	public ObservableList<Client> getListClientRent(){
		
		ObservableList<Client> obsList = FXCollections.observableArrayList();
		
		ClientDAO objDAO = new ClientDAO();
		
		Client[] obj = objDAO.listCliente();
		
		for(int i = 0; i < obj.length;i++) {
			obsList.add(new Client(obj[i].getId(), obj[i].getNome(), obj[i].getCpf()));
		}
	
		return obsList;
	}
	
	public ObservableList<Instrument> getListInstrumentRent(ArrayList<Integer> idsInstrumentsRent){
		
		ObservableList<Instrument> obsList = FXCollections.observableArrayList();
		
		InstrumentDAO objDAO = new InstrumentDAO();
		
		for(int i = 0; i < idsInstrumentsRent.size(); i++) {
			
			Instrument obj = objDAO.getInstrument(idsInstrumentsRent.get(i));
			
			obsList.add(new Instrument(obj.getId(), obj.getNome(), new SimpleStringProperty("0"), obj.getValorLocacao()));
    	}
	
		return obsList;
	}
	
}
