package address.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dependences {
	
	private String sql, msg;
	private int id;
	private BD bd;
	
	public Dependences() {
		bd = new BD();
	}
	
	/**
	 * Verifica se as propriediads cor, tipo e marca do instrumento que serão salvas, ja não estão para evitar duplicidade
	 * de dados.
	 * @param value - Valor a ser salvo.
	 * @param table - Tabela a ser salva (cor, tipo ou marca).
	 * @return - Retorna o id da propriedade salva.
	 */
	public int propInstrument(String value, String table) {
		
		sql = "SELECT * FROM " + table +  " WHERE nome = ?";
		
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, value);
			bd.rs = bd.st.executeQuery();
			
			if (bd.rs.next()) { // se achar o valor na tabela desejada, atribui seu id
				
				id = bd.rs.getInt("id");
				
	        }else { // se nao achar o valor, insere o valir no banco e pega o id
	        	
	        	sql = "INSERT INTO " + table + " VALUES(?)";
	        	
	        	try {
					
		        	bd.st = bd.con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS );
					bd.st.setString(1, value);
					bd.st.executeUpdate();
					
					bd.rs = bd.st.getGeneratedKeys();
					bd.rs.next();
					id = bd.rs.getInt(1);
					
				} catch (SQLException  e) {
					System.out.println("EX2 - Não foi possível cadastrar: " + e.toString());
				}
	        	
	        }
			
		} catch (SQLException  e) {
			
			System.out.println("EX1 - Não foi possível cadastrar: " + e.toString());
		}
		finally {
			bd.close();
		}
		
		return id;
	}
	
	/**
	 * Retorna id do fornecedor a partir do nome
	 * @param name - nome do fornecedor
	 * @return - id do fornecedor
	 */
	public int idFornecFromName(String name) {
		
		sql = "SELECT idFornecedor FROM fornecedor WHERE nome = ?";
		
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, name);
			bd.rs = bd.st.executeQuery();
			
			if (bd.rs.next()) {
				id = bd.rs.getInt("idFornecedor");
	        }else {
	        	id = 0;
	        }
			
		} catch (SQLException  e) {
			System.out.println("EX1 - Não foi possível cadastrar: " + e.toString());
		}
		finally {
			bd.close();
		}
		
		return id;
	}
}
