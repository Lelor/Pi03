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
	 * Cadastra Funcinoário.
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
			
			msg = "Funcionário cadastrado com sucesso! =]";
			
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
	 * Realiza atualização de Funcionário.
	 * @param pr - Objeto funcionário.
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
	 * Pega um funcionario específico.
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
	
	/**
	 * Realiza login do funcionário.
	 * @param login - Usuário de acesso do funcionário.
	 * @param senha - Senha de acesso do funcionário
	 * @return - retorna um objeto Employee com informações do funcionário se os dados estiverem corretos ou vazio se não.
	 */
	public Employee doLogin(String login, String senha) {
		
		sql = "SELECT * FROM funcionario " + 
				"WHERE login = ? AND senha = ? AND ativo = 1";
				
		Employee ep = new Employee();
		
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, login);
			bd.st.setString(2, senha);
			
			bd.rs = bd.st.executeQuery();
			
			if(bd.rs.next()) {
				
				// idFuncionario, nome, nivel
				ep.setId(bd.rs.getInt("idFuncionario"));
				ep.setNome(bd.rs.getString("nome"));
				ep.setNivel(bd.rs.getInt("nivel"));
				ep.setLogado(true);
				
			}else {
				ep.setLogado(false);
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
