package address.model;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import address.services.BD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProviderDAO extends PersonDAO{

	private String sql, msg;
	private int decision;
	private BD bd;
	
	public ProviderDAO() {
		bd = new BD();
	}
	
	/**
	 * Cadastra Fornecedor.
	 * @param pr - Objeto Fornecedor.
	 * @return
	 */
	public String insertProvider(Provider pr) {
		
		sql = "INSERT INTO fornecedor(nome, cnpj, telefone, email, cidade, endereco) " + 
				"VALUES(?, ?, ?, ?, ?, ?)";
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, pr.getNome());
			bd.st.setString(2, pr.getDocumento());
			bd.st.setString(3, pr.getTelefone());
			bd.st.setString(4, pr.getEmail());
			bd.st.setString(5, pr.getCidade());
			bd.st.setString(6, pr.getEndereco());
			
			bd.st.executeUpdate();
			
			msg = "Fornecedor cadastrado com sucesso! =]";
			
		} catch (SQLException  e) {
			
			msg = "Falha no cadastro! =[";
			System.out.println(e.toString());

		}
		finally {
			bd.close();
		}
		
		return msg;
	}
	
	/**
	 * Realiza atualização de fornecedor.
	 * @param pr - Objeto fornecedor.
	 * @return - Retorna mensagem.
	 */
	public String updateProvider(Provider pr) {
		
		sql = "UPDATE fornecedor SET nome = ?, cnpj = ?, telefone = ?, email = ?, cidade = ?, endereco = ? " + 
				"WHERE idFornecedor = ?";
		
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, pr.getNome());
			bd.st.setString(2, pr.getDocumento());
			bd.st.setString(3, pr.getTelefone());
			bd.st.setString(4, pr.getEmail());
			bd.st.setString(5, pr.getCidade());
			bd.st.setString(6, pr.getEndereco());
			bd.st.setInt(7, pr.getId());
			
			bd.st.executeUpdate();
			msg = "Fornecedor atualizado! =]";
			
		} catch (SQLException  e) {
			
			msg = "Falha no cadastro! =[";
			System.out.println("Erro DAO: insersao de dados " + e.toString());

		}
		finally {
			bd.close();
		}
		
		return msg;
	}
	
	/**
	 * Pega um fornecedor específico.
	 * @param id - Id do fornecedor.
	 * @return - Objeto fornecedor.
	 */
	public Provider getProvider(int id) {
		
		sql = "SELECT * FROM fornecedor WHERE idFornecedor = ?";
		
		Provider pr = null;

		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, id);
			
			bd.rs = bd.st.executeQuery();
			
			if(bd.rs.next()) {
				
				//idFuncionario, cpf, nome, email, telefone, cidade, endereco
				pr = new Provider(
					bd.rs.getInt("idFornecedor"),
					bd.rs.getString("nome"),
					bd.rs.getString("cnpj"),
					bd.rs.getString("email"),
					bd.rs.getString("telefone"),
					bd.rs.getString("cidade"),
					bd.rs.getString("endereco")
				);
			}
			
			
		} catch (SQLException  e) {
			
			System.out.println("Erro DAO: pegar dados " + e.toString());

		}
		finally {
			bd.close();
		}
		
		return pr;
	}
}
