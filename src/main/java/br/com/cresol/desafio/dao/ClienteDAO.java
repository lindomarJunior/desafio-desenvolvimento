package br.com.cresol.desafio.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.cresol.desafio.dto.Cliente;

public class ClienteDAO {
	
	EntityManager entityManager;
	
	public ClienteDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Cliente consultar(String cpf) {
		Query query = entityManager.createQuery("select a from Cliente a where a.cpf = :cpf")
				.setParameter("cpf", cpf);
		try {
			return (Cliente) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
}
