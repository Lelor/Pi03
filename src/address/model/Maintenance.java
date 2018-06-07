package address.model;

import java.math.BigDecimal;

public class Maintenance {
	
	int idManutencao, idInstrumento;
	String nomeinstrumento, dataEntrada, dataSaida, ultimoCliente, tipo, descricao;
	BigDecimal valor;
	boolean ativo;
	
	public Maintenance() {
	}

	public Maintenance(int idManutencao, int idInstrumento, String nomeinstrumento, String dataEntrada,
			String dataSaida) {
		this.idManutencao = idManutencao;
		this.idInstrumento = idInstrumento;
		this.nomeinstrumento = nomeinstrumento;
		this.dataEntrada = dataEntrada;
		this.dataSaida = dataSaida;
	}

	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public int getIdManutencao() {
		return idManutencao;
	}
	public void setIdManutencao(int idManutencao) {
		this.idManutencao = idManutencao;
	}
	public int getIdInstrumento() {
		return idInstrumento;
	}
	public void setIdInstrumento(int idInstrumento) {
		this.idInstrumento = idInstrumento;
	}
	public String getNomeinstrumento() {
		return nomeinstrumento;
	}
	public void setNomeinstrumento(String nomeinstrumento) {
		this.nomeinstrumento = nomeinstrumento;
	}
	public String getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(String dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public String getDataSaida() {
		return dataSaida;
	}
	public void setDataSaida(String dataSaida) {
		this.dataSaida = dataSaida;
	}
	public String getUltimoCliente() {
		return ultimoCliente;
	}
	public void setUltimoCliente(String ultimoCliente) {
		this.ultimoCliente = ultimoCliente;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
}
