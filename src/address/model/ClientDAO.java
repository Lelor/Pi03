package address.model;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import address.services.BD;
import address.services.Dependences;

public class ClientDAO extends PersonDAO{
	
	private String sql, msg;
	private int decision;
	private BD bd;
	
	public ClientDAO() {
		bd = new BD();
	}
	
	/**
	 * Cadastra Cliente.
	 * @param cl - Objeto Cliente.
	 * @return
	 */
	public String insertClient(Client cl) {
		
		sql = "INSERT INTO cliente(nome, cpf, telefone, email, cidade, endereco) " + 
				"VALUES(?, ?, ?, ?, ?, ?)";
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, cl.getNome());
			bd.st.setString(2, cl.getDocumento());
			bd.st.setString(3, cl.getTelefone());
			bd.st.setString(4, cl.getEmail());
			bd.st.setString(5, cl.getCidade());
			bd.st.setString(6, cl.getEndereco());
			
			bd.st.executeUpdate();
			
			msg = "Cliente cadastrado com sucesso! =]";
			
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
	 * Realiza atualização de clientes.
	 * @param cl - Objeto cliente.
	 * @return - Retorna mensagem.
	 */
	public String updateClient(Client cl) {
		
		sql = "UPDATE cliente SET nome = ?, cpf = ?, telefone = ?, email = ?, cidade = ?, endereco = ? " + 
				"WHERE idCliente = ?";
		
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, cl.getNome());
			bd.st.setString(2, cl.getDocumento());
			bd.st.setString(3, cl.getTelefone());
			bd.st.setString(4, cl.getEmail());
			bd.st.setString(5, cl.getCidade());
			bd.st.setString(6, cl.getEndereco());
			bd.st.setInt(7, cl.getId());
			
			bd.st.executeUpdate();
			msg = "Cliente atualizado! =]";
			
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
	 * Pega um cliente específico.
	 * @param id - Id do cliente.
	 * @return - Objeto Cliente.
	 */
	public Client getClient(int id) {
		
			sql = "SELECT * FROM cliente WHERE idCliente = ?";
		
		Client cl = null;

		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, id);
			
			bd.rs = bd.st.executeQuery();
			
			if(bd.rs.next()) {
				
				//idFuncionario, cpf, nome, email, telefone, cidade, endereco
				cl = new Client(
						bd.rs.getInt("idCliente"),
						bd.rs.getString("nome"),
						bd.rs.getString("cpf"),
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
		
		return cl;
	}
}
