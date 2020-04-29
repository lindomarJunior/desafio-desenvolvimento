package br.com.cresol.desafio.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contrato")
public class Contrato {

    @Id
    private Long id;
	private String numero;
	private LocalDate data;
	private BigDecimal valor;
	@Column(name = "quantidade_parcelas")
	private Integer quantidadeParcelas;
	@Column(name = "taxa_juros")
	private Double taxaJuros;
	private Integer iof;
	@OneToMany(mappedBy="contrato", cascade = CascadeType.ALL)
	private List<Parcela> parcelas;
	@OneToOne
	@MapsId
	private SimulacaoEmprestimo simulacaoEmprestimo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Integer getQuantidadeParcelas() {
		return quantidadeParcelas;
	}
	public void setQuantidadeParcelas(Integer quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}
	public Double getTaxaJuros() {
		return taxaJuros;
	}
	public void setTaxaJuros(Double taxaJuros) {
		this.taxaJuros = taxaJuros;
	}
	public Integer getIof() {
		return iof;
	}
	public void setIof(Integer iof) {
		this.iof = iof;
	}
	public List<Parcela> getParcelas() {
		return parcelas;
	}
	public void setParcelas(List<Parcela> parcelas) {
		this.parcelas = parcelas;
	}
	public SimulacaoEmprestimo getSimulacaoEmprestimo() {
		return simulacaoEmprestimo;
	}
	public void setSimulacaoEmprestimo(SimulacaoEmprestimo simulacaoEmprestimo) {
		this.simulacaoEmprestimo = simulacaoEmprestimo;
	}
	
}
