package br.com.cresol.desafio.dto;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class SimularEmprestimoResponse {
	private String numeroContrato;
	private String dataSimulacao;
	private String dataValidade;
	private BigDecimal valorContrato;
	private Integer quantidadeParcelas;
	private BigDecimal valorParcela;
	private Double taxaJuros;
	
	public SimularEmprestimoResponse() {
		super();
	}
	
	public SimularEmprestimoResponse(SimulacaoEmprestimo simulacaoEmprestimo) {
		super();
		this.numeroContrato = simulacaoEmprestimo.getContrato().getNumero();
		this.dataSimulacao = simulacaoEmprestimo.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.dataValidade = simulacaoEmprestimo.getDataValidade().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.valorContrato = simulacaoEmprestimo.getContrato().getValor();
		this.quantidadeParcelas = simulacaoEmprestimo.getContrato().getQuantidadeParcelas();
		this.valorParcela = simulacaoEmprestimo.getContrato().getParcelas().get(0).getValor();
		this.taxaJuros = simulacaoEmprestimo.getContrato().getTaxaJuros();
	}
	
	public String getNumeroContrato() {
		return numeroContrato;
	}
	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}
	public String getDataSimulacao() {
		return dataSimulacao;
	}
	public void setDataSimulacao(String dataSimulacao) {
		this.dataSimulacao = dataSimulacao;
	}
	public String getDataValidade() {
		return dataValidade;
	}
	public void setDataValidade(String dataValidade) {
		this.dataValidade = dataValidade;
	}
	public BigDecimal getValorContrato() {
		return valorContrato;
	}
	public void setValorContrato(BigDecimal valorContrato) {
		this.valorContrato = valorContrato;
	}
	public Integer getQuantidadeParcelas() {
		return quantidadeParcelas;
	}
	public void setQuantidadeParcelas(Integer quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}
	public BigDecimal getValorParcela() {
		return valorParcela;
	}
	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}
	public Double getTaxaJuros() {
		return taxaJuros;
	}
	public void setTaxaJuros(Double taxaJuros) {
		this.taxaJuros = taxaJuros;
	}

}
