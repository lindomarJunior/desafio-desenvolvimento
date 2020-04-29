package br.com.cresol.desafio.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String cpf;
	private String nome;
	private String email;
	@OneToMany(mappedBy="cliente", cascade = CascadeType.ALL)
	private List<SimulacaoEmprestimo> simulacaoEmprestimo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<SimulacaoEmprestimo> getSimulacaoEmprestimo() {
		return simulacaoEmprestimo;
	}
	public void setSimulacaoEmprestimo(List<SimulacaoEmprestimo> simulacaoEmprestimo) {
		this.simulacaoEmprestimo = simulacaoEmprestimo;
	}
	
	public void addSimulacaoEmprestimo(SimulacaoEmprestimo simulacaoEmprestimo) {
		if(this.simulacaoEmprestimo == null) {
			this.simulacaoEmprestimo = new ArrayList<SimulacaoEmprestimo>(Arrays.asList(simulacaoEmprestimo));
		}else {
			this.simulacaoEmprestimo.add(simulacaoEmprestimo);
		}
		simulacaoEmprestimo.setCliente(this);
	}
}
