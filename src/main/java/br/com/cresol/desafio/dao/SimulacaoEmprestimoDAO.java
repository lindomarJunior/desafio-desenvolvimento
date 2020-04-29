package br.com.cresol.desafio.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import javassist.NotFoundException;

public class SimulacaoEmprestimoDAO {
	
	EntityManager entityManager;
	
	public SimulacaoEmprestimoDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public SimulacaoEmprestimo consultar(String numeroContrato) throws NotFoundException {
		Query query = entityManager.createQuery("select a from SimulacaoEmprestimo a where a.contrato.numero = :numeroContrato")
				.setParameter("numeroContrato", numeroContrato);
		try {
			return (SimulacaoEmprestimo) query.getSingleResult();
		} catch (NoResultException e) {
			throw new NotFoundException(e.getMessage());
		}
	}
	
	public void excluir(String numeroContrato) throws NotFoundException {	
		try {
			entityManager.remove(consultar(numeroContrato));
		} catch (NoResultException e) {
			throw new NotFoundException(e.getMessage());
		}
	}
	
}
