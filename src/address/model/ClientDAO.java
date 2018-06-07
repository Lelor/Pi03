package address.model;

import java.sql.SQLException;

import address.services.BD;

public class ClientDAO {
	
	private String sql, msg;
	private int decision;
	private BD bd;
	
	public ClientDAO() {
		bd = new BD();
	}
	
	/**
	 * Conta total de clientes cadastrados no banco.
	 * @return - Total de cliente.
	 */
	public int countNumCliente() {
		
		String sql2 = "SELECT COUNT(idCliente) AS total FROM cliente WHERE ativo = 1";
		int countRow = 0;
		
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql2);
			bd.rs = bd.st.executeQuery();
			
			if(bd.rs.next()){
				countRow = bd.rs.getInt("total");
			}
			
		} catch (SQLException e) {
			
			msg = "Falha ao recuperar lista! =[";
			System.out.println(e.toString());

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
	public Client[] listCliente() {
		
		sql = "SELECT * from cliente WHERE ativo = 1";
		
		int i = 0;
		int numRow = countNumCliente();
		Client[] cl = new Client[numRow];
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.rs = bd.st.executeQuery();
			
			while(bd.rs.next()) {
				
				//table consult columns -----
				//idCliente	nome	cpf	telefone	email	cidade	endereco	ativo
				cl[i] = new Client();
				cl[i].setId(bd.rs.getInt("idCliente"));
				cl[i].setNome(bd.rs.getString("nome"));
				cl[i].setCpf(bd.rs.getString("cpf"));
				cl[i].setTelefone(bd.rs.getString("telefone"));
				cl[i].setEmail(bd.rs.getString("email"));
				cl[i].setCidade(bd.rs.getString("cidade"));
				
				i++;
			}

			
		} catch (SQLException e) {
			
			msg = "Falha ao recuperar lista! =[";
			System.out.println(e.toString());

		}
		finally {
			bd.close();
		}
		
		return cl;
		
	}
}
