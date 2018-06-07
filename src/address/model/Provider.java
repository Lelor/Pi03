package address.model;

public class Provider extends Person {
	
	private String cnpj;
	
	public Provider() {
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
}
