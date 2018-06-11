package address.model;

import java.sql.SQLException;

import address.services.BD;

public class EmployeeDAO extends PersonDAO{
	
	private String sql, msg;
	private int decision;
	private BD bd;
	
	public EmployeeDAO() {
		bd = new BD();
	}
	
	/**
	 * Cadastra Funcino�rio.
	 * @param ep - Objeto Funcionario.
	 * @return
	 */
	public String insertEmployee(Employee ep) {
		
		sql = "INSERT INTO funcionario(nome, cpf, telefone, email, cidade, endereco, login, senha, nivel) " + 
				"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, ep.getNome());
			bd.st.setString(2, ep.getDocumento());
			bd.st.setString(3, ep.getTelefone());
			bd.st.setString(4, ep.getEmail());
			bd.st.setString(5, ep.getCidade());
			bd.st.setString(6, ep.getEndereco());
			bd.st.setString(7, ep.getLogin());
			bd.st.setString(8, ep.getSenha());
			bd.st.setInt(9, ep.getNivel());
			
			bd.st.executeUpdate();
			
			msg = "Funcion�rio cadastrado com sucesso! =]";
			
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
	 * Realiza atualiza��o de Funcion�rio.
	 * @param pr - Objeto funcion�rio.
	 * @return - Retorna mensagem.
	 */
	public String updateEmployee(Employee ep) {
		
		sql = "UPDATE funcionario SET nome = ?, cpf = ?, telefone = ?, email = ?, cidade = ?, endereco = ?, " +
				"login = ?, senha = ?, nivel = ? " + 
				"WHERE idFuncionario = ?";
		
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, ep.getNome());
			bd.st.setString(2, ep.getDocumento());
			bd.st.setString(3, ep.getTelefone());
			bd.st.setString(4, ep.getEmail());
			bd.st.setString(5, ep.getCidade());
			bd.st.setString(6, ep.getEndereco());
			bd.st.setString(7, ep.getLogin());
			bd.st.setString(8, ep.getSenha());
			bd.st.setInt(9, ep.getNivel());
			bd.st.setInt(10, ep.getId());
			
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
	 * Pega um funcionario espec�fico.
	 * @param id - Id do funcionario.
	 * @return - Objeto funcionario.
	 */
	public Employee getEmployee(int id) {
		
		sql = "SELECT * FROM funcionario WHERE idFuncionario = ?";
		
		Employee ep = null;

		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, id);
			
			bd.rs = bd.st.executeQuery();
			
			if(bd.rs.next()) {
				
				//idFuncionario, cpf, nome, email, telefone, cidade, endereco, login, senha, nivel
				ep = new Employee(
						bd.rs.getString("login"),
						bd.rs.getString("senha"),
						bd.rs.getInt("nivel"),
						bd.rs.getInt("idFuncionario"),
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
		
		return ep;
	}
	
}
