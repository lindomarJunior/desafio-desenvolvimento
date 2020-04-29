package br.com.cresol.desafio.dto;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author evandro
 *
 */
@Entity
@Table(name = "simulacao_emprestimo")
public class SimulacaoEmprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private LocalDate data;
	@Column(name = "data_validade")
	private LocalDate dataValidade;
	@OneToOne(mappedBy="simulacaoEmprestimo", cascade = CascadeType.ALL)
	private Contrato contrato;
	@ManyToOne
	private Cliente cliente;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public LocalDate getDataValidade() {
		return dataValidade;
	}
	public void setDataValidade(LocalDate dataValidade) {
		this.dataValidade = dataValidade;
	}
	public Contrato getContrato() {
		return contrato;
	}
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void addContrato(Contrato contrato) {
		this.contrato = contrato;
		contrato.setSimulacaoEmprestimo(this);
	}
}
