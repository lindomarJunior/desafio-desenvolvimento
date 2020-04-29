package br.com.cresol.desafio.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author evandro
 *
 */
public class SimularEmprestimoPayload {
	@ApiModelProperty
	private String cpf;
	@ApiModelProperty
	private String nome;
	@ApiModelProperty
	private String email;
	@ApiModelProperty
	private BigDecimal valorContrato;
	@ApiModelProperty
	private Integer quantidadeParcelas;
	
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
	
}
