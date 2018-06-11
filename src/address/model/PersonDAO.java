package address.model;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import address.services.BD;
import address.services.Dependences;

public class PersonDAO {
	
	private String sql, msg;
	private int decision;
	private BD bd;
	
	public PersonDAO() {
		bd = new BD();
	}
	
	/**
	 * Conta total de clientes cadastrados no banco.
	 * @return - Total de cliente.
	 */
	public int countNumPerson(String searchString, String tableName) {
		
		String idNameColumn = "idCliente";
		
		if(tableName == "funcionario") {
			idNameColumn = "idFuncionario";
		}else if (tableName == "fornecedor") {
			idNameColumn = "idFornecedor";
		}
		
		String sql2 = "SELECT COUNT(?) AS total FROM " + tableName + " WHERE ativo = 1 AND nome LIKE ?";
		int countRow = 0;
		
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql2);
			bd.st.setString(1, idNameColumn);
			bd.st.setString(2, "%" + searchString + "%");
			bd.rs = bd.st.executeQuery();
			
			if(bd.rs.next()){
				countRow = bd.rs.getInt("total");
			}
			
		} catch (SQLException e) {
			
			msg = "Falha ao recuperar lista! =[";
			System.out.println("ERRO - contar lista: " + e.toString());

		}
		finally {
			bd.close();
		}
		
		return countRow;
	}
	
	/**
	 * Lista clientes.
	 * @return - Objeto Cliente.
	 */
	public Person[] listPerson(String searchString, String tableName) {
		
		sql = "SELECT * FROM " + tableName + " WHERE ativo = 1 AND nome LIKE ?";
		
		String idNameColumn = "idCliente";
		String documentNameColumn 	= "cpf";
		
		if(tableName == "funcionario") {
			idNameColumn = "idFuncionario";
		}else if (tableName == "fornecedor") {
			idNameColumn = "idFornecedor";
			documentNameColumn = "cnpj";
		}
		
		int i = 0;
		int numRow = countNumPerson(searchString, tableName);
		Person[] cl = new Person[numRow];
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, "%" + searchString + "%");
			bd.rs = bd.st.executeQuery();
			
			while(bd.rs.next()) {
				
				//table consult columns -----
				//nome, cpf, telefone, email, cidade, endereco, ativo
				cl[i] = new Client();
				cl[i].setId(bd.rs.getInt(idNameColumn));
				cl[i].setNome(bd.rs.getString("nome"));
				cl[i].setDocumento(bd.rs.getString(documentNameColumn));
				cl[i].setTelefone(bd.rs.getString("telefone"));
				cl[i].setEmail(bd.rs.getString("email"));
				cl[i].setCidade(bd.rs.getString("cidade"));
				
				i++;
			}

			
		} catch (SQLException e) {
			
			msg = "Falha ao recuperar lista! =[";
			System.out.println("ERRO - recuperar a lista: " + e.toString());

		}
		finally {
			bd.close();
		}
		
		return cl;
		
	}
	
	/**
	 * Exclui pessoa.
	 * @param id - Id da pessoa que será excluida.
	 * @return - Retorna a decisão do usuário através de um JOptionPane.showConfirmDialog.
	 */
	public int excludePerson(int id, int type) {
		
		if(type == 1) {
			
			sql = "UPDATE cliente SET ativo = ? " + 
					"WHERE idCliente = ?";
			
		}else if(type == 2){
			sql = "UPDATE funcionario SET ativo = ? " + 
					"WHERE idFuncionario = ?";
		}else {
			sql = "UPDATE fornecedor SET ativo = ? " + 
					"WHERE idFornecedor = ?";
		}
		
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setBoolean(1, false);
			bd.st.setInt(2, id);
			
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog (
					null, "Essa ação é irreversível!\nDeseja mesmo prosseguir?","Alerta!", dialogButton
			);
			if(dialogResult == JOptionPane.YES_OPTION){
				decision = 1;
				bd.st.executeUpdate();
			}else {
				decision = 0;
			}
			
			
		} catch (SQLException  e) {
			
			msg = "Falha no cadastro! =[";
			System.out.println("Erro DAO: insersao de dados " + e.toString());

		}
		finally {
			bd.close();
		}
		
		return decision;
	}
	
}
