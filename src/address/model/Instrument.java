package address.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Instrument {
	
	/*
	 * Status 1 = disponivel
	 * Status 2 = locado
	 * Status 3 = manutencao
	 */
	private int id, numSerie, idFornecedor, idTipo, idMarca, idCor, statusId;
	private String nome, nomeFornecedor, ano, foto, tipo, cor, marca, ativo, status;
	private BigDecimal valorCompra, valorLocacao;
	private BooleanProperty selected;
	private SimpleStringProperty dataDevolucao;

	public Instrument() {
	}
	
	// contrutor para listar instrumentos
	public Instrument(Integer id, String nome, String marca, BigDecimal valorLocacao, String status, int statusId, BooleanProperty selected) {
		this.id = id;
		this.nome = nome;
		this.marca = marca;
		this.valorLocacao = valorLocacao;
		this.status = status;
		this.statusId = statusId;
		this.selected = selected;
	}
	
	// contrutor para listar instrumentos na tela de locaçao
	public Instrument(Integer id, String nome, SimpleStringProperty dataDevolucao, BigDecimal valorLocacao) {
		this.id = id;
		this.nome = nome;
		this.dataDevolucao = dataDevolucao;
		this.valorLocacao = valorLocacao;
	}
	
	public SimpleStringProperty getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(SimpleStringProperty dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	
	public BooleanProperty getSelected() {
		return selected;
	}

	public void setSelected(BooleanProperty selected) {
		this.selected = selected;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(int numSerie) {
		this.numSerie = numSerie;
	}

	public int getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(int idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public int getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}

	public int getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(int idMarca) {
		this.idMarca = idMarca;
	}

	public int getIdCor() {
		return idCor;
	}

	public void setIdCor(int idCor) {
		this.idCor = idCor;
	}

	public String getNome() {
		return nome;
	}

	public void setStatusId(int statusId) {
		
		this.statusId = statusId;
		
		if(statusId == 1) {
			this.status = "Disponível";
		}else if(statusId == 2){
			this.status = "Locado";
		}else {
			this.status = "Manutenção";
		}
		
	}
	
	public int getStatusId() {
		return statusId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeFornecedor() {
		return nomeFornecedor;
	}

	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public BigDecimal getvalorCompra() {
		return valorCompra;
	}

	public void setvalorCompra(BigDecimal valorCompra) {
		this.valorCompra = valorCompra;
	}

	public BigDecimal getValorLocacao() {
		return valorLocacao;
	}

	public void setValorLocacao(BigDecimal valorLocacao) {
		this.valorLocacao = valorLocacao;
	}
	
}
