package address.services;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObsLists {
	
	private String sql, msg;
	private int id;
	private BD bd;
	
	public ObsLists() {
		bd = new BD();
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

}
