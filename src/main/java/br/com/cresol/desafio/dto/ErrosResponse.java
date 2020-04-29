package br.com.cresol.desafio.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrosResponse {
	private List<String> erros;
	
	public ErrosResponse() {
		erros = new ArrayList<>();
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}
	
	public void add(String erro) {
		this.erros.add(erro);
	}
}
