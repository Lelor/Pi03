package address.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Rent {
	
	int idLocacao, idFuncionario, idCliente, idInstrument;
	BigDecimal desconto, multa, valorLocacao;
	String descricao, observacao, nomeInstrumento, nomeCliente, nomeFuncionario;
	String dataDevolucao, dataRealizacao, dataTermino, dataDevolucaoEfetiva;
	boolean pago, ativo;
	ArrayList<Integer> idInstrumentList;
	ArrayList<String> dataDevolucaoList;
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	
	public Rent() {
	}
	
	// Construtor para listagem de locação
	public Rent(int idLocacao, String nomeCliente, String dataRealizacao) {
		this.idLocacao = idLocacao;
		this.nomeCliente = nomeCliente;
		this.dataRealizacao = dataRealizacao;
	}

	// Construtoe para listagem de instrumentos locados
	public Rent(int idInstrument, String nomeInstrumento, String dataDevolucao, BigDecimal valorLocacao, BigDecimal multa) {
		this.idInstrument = idInstrument;
		this.valorLocacao = valorLocacao;
		this.nomeInstrumento = nomeInstrumento;
		this.dataDevolucao = dataDevolucao;
		this.multa = multa;
	}

	public BigDecimal getValorLocacao() {
		return valorLocacao;
	}

	public void setValorLocacao(BigDecimal valorLocacao) {
		this.valorLocacao = valorLocacao;
	}

	public int getIdLocacao() {
		return idLocacao;
	}

	public String getNomeInstrumento() {
		return nomeInstrumento;
	}

	public void setNomeInstrumento(String nomeInstrumento) {
		this.nomeInstrumento = nomeInstrumento;
	}

	public void setIdLocacao(int idLocacao) {
		this.idLocacao = idLocacao;
	}

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdInstrument() {
		return idInstrument;
	}

	public void setIdInstrument(int idInstrument) {
		this.idInstrument = idInstrument;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public BigDecimal getMulta() {
		return multa;
	}

	public void setMulta(BigDecimal multa) {
		this.multa = multa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	public String getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(String dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public String getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(String dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public String getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(String dataTermino) {
		this.dataTermino = dataTermino;
	}

	public String getDataDevolucaoEfetiva() {
		return dataDevolucaoEfetiva;
	}

	public void setDataDevolucaoEfetiva(String dataDevolucaoEfetiva) {
		this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
	}

	public boolean isPago() {
		return pago;
	}

	public void setPago(boolean pago) {
		this.pago = pago;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public ArrayList<Integer> getIdInstrumentList() {
		return idInstrumentList;
	}

	public void setIdInstrumentList(ArrayList<Integer> idInstrumentList) {
		this.idInstrumentList = idInstrumentList;
	}

	public ArrayList<String> getDataDevolucaoList() {
		return dataDevolucaoList;
	}

	public void setDataDevolucaoList(ArrayList<String> dataDevolucaoList) {
		this.dataDevolucaoList = dataDevolucaoList;
	}

	public DateTimeFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(DateTimeFormatter formatter) {
		this.formatter = formatter;
	}

}
