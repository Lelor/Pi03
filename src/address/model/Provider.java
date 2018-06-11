package address.model;

public class Provider extends Person {
	
	public Provider() {
	}
	
	public Provider(int id, String nome, String cpf, String email, String telefone, String cidade, String endereco) {
		this.id = id;
		this.nome = nome;
		this.documento = cpf;
		this.email = email;
		this.telefone = telefone;
		this.cidade = cidade;
		this.endereco = endereco;
	}
	
	//contrutor para listar fornecedores
	public Provider(int id, String nome, String cpf, String email, String telefone) {
		this.id = id;
		this.nome = nome;
		this.documento = cpf;
		this.email = email;
		this.telefone = telefone;
	}
}
