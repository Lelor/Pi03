package address.services;

import java.sql.SQLException;
import java.util.ArrayList;

import address.model.Client;
import address.model.ClientDAO;
import address.model.Instrument;
import address.model.InstrumentDAO;
import address.model.Maintenance;
import address.model.MaintenanceDAO;
import address.model.Rent;
import address.model.RentDAO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObsLists {
	
	private String sql, msg;
	private int id;
	private BD bd;
	String dateView;
	Utilities ut;
	
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
	
	/**
	 * Observable list para listar instrumentos na tela de locação.
	 * @param idsInstrumentsRent - Ids dos Instrumentos que seram listados.
	 * @return - Retorna uma Observable list dos instrumentos.
	 */
	public ObservableList<Instrument> getListInstrumentRent(ArrayList<Integer> idsInstrumentsRent){
		
		ObservableList<Instrument> obsList = FXCollections.observableArrayList();
		
		InstrumentDAO objDAO = new InstrumentDAO();
		
		for(int i = 0; i < idsInstrumentsRent.size(); i++) {
			
			Instrument obj = objDAO.getInstrument(idsInstrumentsRent.get(i));
			
			obsList.add(new Instrument(obj.getId(), obj.getNome(), new SimpleStringProperty("0"), obj.getValorLocacao()));
    	}
	
		return obsList;
	}
	
	/**
	 * Lista locações ativas e inativas.
	 * @param ativo - variavel que determina se a locação esta ativa ou inativa.
	 * @return - retorna lista de locações.
	 */
	public ObservableList<Rent> getListRent(boolean ativo){
		
		ObservableList<Rent> obsList = FXCollections.observableArrayList();
		
		RentDAO objDAO = new RentDAO();
		
		Rent[] obj = objDAO.listRent(ativo);
		
		ut = new Utilities();
		
		for(int i = 0; i < obj.length;i++) {
			
			// formata data para exibição
			dateView = ut.dateFormatView(obj[i].getDataRealizacao());
			obsList.add(new Rent(obj[i].getIdLocacao(), obj[i].getNomeCliente(), dateView));
		}
	
		return obsList;
	}
	
	/**
	 * Pega lista de instrumentos locados referente a uma locação.
	 * @param idRent - Id da locação.
	 * @return - Retora uma observable list dos instrumentos.
	 */
	public ObservableList<Rent> getListInstrumentRent(int idRent){
		
		ObservableList<Rent> obsList = FXCollections.observableArrayList();
		
		RentDAO objDAO = new RentDAO();
		
		Rent[] obj = objDAO.listInstrumentsRent(idRent);
		
		ut = new Utilities();
		
		for(int i = 0; i < obj.length;i++) {
			
			// formata data para exibição
			dateView = ut.dateFormatView(obj[i].getDataDevolucao());
			obsList.add(new Rent(obj[i].getIdInstrument(), obj[i].getNomeInstrumento(), dateView, obj[i].getValorLocacao(), obj[i].getMulta()));
		}
	
		return obsList;
	}
	
	public ObservableList<Maintenance> getListMaintenance(boolean ativo){
		
		ObservableList<Maintenance> obsList = FXCollections.observableArrayList();
		
		MaintenanceDAO objDAO = new MaintenanceDAO();
		
		Maintenance[] obj = objDAO.listMaintenance(ativo);
		
		ut = new Utilities();
		
		String dateEntrada, dateSaida;
		
		for(int i = 0; i < obj.length;i++) {
			
			dateEntrada = ut.dateFormatView(obj[i].getDataEntrada());
			
			try {
				dateSaida  = ut.dateFormatView(obj[i].getDataSaida());
			}catch (Exception e) {
				dateSaida  = "--------------";
			} 
			
			obsList.add(new Maintenance(obj[i].getIdManutencao(), obj[i].getIdInstrumento(), obj[i].getNomeinstrumento(), dateEntrada, dateSaida));
		}
	
		return obsList;
		
	}
}
