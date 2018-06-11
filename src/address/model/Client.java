package address.model;

public class Client extends Person {
	
	public Client() {
	}
	
	public Client(int id, String nome, String cpf, String email, String telefone, String cidade, String endereco) {
		this.id = id;
		this.nome = nome;
		this.documento = cpf;
		this.email = email;
		this.telefone = telefone;
		this.cidade = cidade;
		this.endereco = endereco;
	}
	
	//contrutor para listar clientes na tela de locação
	public Client(int id, String nome, String cpf) {
		this.id = id;
		this.nome = nome;
		this.documento = cpf;
	}
	
	//contrutor para listar clientes
	public Client(int id, String nome, String cpf, String email, String telefone) {
		this.id = id;
		this.nome = nome;
		this.documento = cpf;
		this.email = email;
		this.telefone = telefone;
	}
	
}
