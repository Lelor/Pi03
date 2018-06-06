package address.model;

public class Client extends Person {
	
	private String cpf;
	
	public Client() {
	}
	
	//contrutor para listar clientes na tela de locação
	public Client(int id, String nome, String cpf) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}
