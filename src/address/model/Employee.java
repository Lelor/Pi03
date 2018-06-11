package address.model;

public class Employee extends Person{
	
	private String login, senha;
	private int nivel;
	
	/*
	 * Nível 0: Vendedor
	 * Nível 1: Master
	 */
	
	public Employee() {
	}
	
	public Employee(String login, String senha, int nivel, int id, String nome, String cpf, String email, String telefone,
			String cidade, String endereco) {
		this.id = id;
		this.nome = nome;
		this.documento = cpf;
		this.email = email;
		this.telefone = telefone;
		this.cidade = cidade;
		this.endereco = endereco;
		this.login = login;
		this.senha = senha;
		this.nivel = nivel;
	}


	//contrutor para listar funcionarios
	public Employee(int id, String nome, String cpf, String email, String telefone) {
		this.id = id;
		this.nome = nome;
		this.documento = cpf;
		this.email = email;
		this.telefone = telefone;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
}
