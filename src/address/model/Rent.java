package address.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class Rent {
	
	int idLocacao, idFuncionario, idCliente;
	LocalDate dataRealizacao, dataTermino, dataDevolucaoEfetiva;
	BigDecimal desconto, multa;
	String descricao, observacao;
	boolean pago, ativo;
	ArrayList<Integer> idInstrument;
	ArrayList<LocalDate> dataDevolucao;
	
	public int getIdLocacao() {
		return idLocacao;
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
	public LocalDate getDataRealizacao() {
		return dataRealizacao;
	}
	public void setDataRealizacao(LocalDate dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}
	public LocalDate getDataTermino() {
		return dataTermino;
	}
	public void setDataTermino(LocalDate dataTermino) {
		this.dataTermino = dataTermino;
	}
	public LocalDate getDataDevolucaoEfetiva() {
		return dataDevolucaoEfetiva;
	}
	public void setDataDevolucaoEfetiva(LocalDate dataDevolucaoEfetiva) {
		this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
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
	public ArrayList<Integer> getIdInstrument() {
		return idInstrument;
	}
	public void setIdInstrument(ArrayList<Integer> idInstrument) {
		this.idInstrument = idInstrument;
	}
	public ArrayList<LocalDate> getDataDevolucao() {
		return dataDevolucao;
	}
	public void setDataDevolucao(ArrayList<LocalDate> dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	
}
