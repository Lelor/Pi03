package address.services;

import java.math.BigDecimal;
import java.sql.SQLException;

import address.model.Instrument;
import address.model.InstrumentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObsLists {
	
	private String sql, msg;
	private int id;
	private BD bd;
	
	public ObsLists() {
	}
	
	/**
	 * Cria uma ObservableList generica para cadastro de instrumeto.
	 * @param table - tabela do banco para pegar nomes (fornecedor, cor, tipo, marca).
	 * @return - ObservableList para cadastro de instrumentos.
	 */
	public ObservableList<String> getObsListInstrument(String table){
		
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
			
			System.out.println("Falha ao carregar Fornecedores! =[");

		}
		finally {
			bd.close();
		}

		return item;
	}

	public ObservableList<Instrument> getListInstrument(){
		
		ObservableList<Instrument> inOBS = FXCollections.observableArrayList();
		
		InstrumentDAO inDAO = new InstrumentDAO();
		
		Instrument[] in = inDAO.listInstrument();
		
		for(int i = 0; i < in.length;i++) {
			inOBS.add(new Instrument(in[i].getId(), in[i].getNome(), in[i].getMarca(), in[i].getValorLocacao(), in[i].getStatus()));
		}
	
		return inOBS;
		
	}
}
