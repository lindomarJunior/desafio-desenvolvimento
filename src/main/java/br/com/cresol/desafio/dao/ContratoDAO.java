package br.com.cresol.desafio.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.cresol.desafio.dto.Contrato;

public class ContratoDAO {
	EntityManager entityManager;
	
	public ContratoDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Contrato consultarUltimoPorNumero(String numeroContrato) {
		Query query = entityManager.createQuery(
				"select a from Contrato a where a.numero like :numeroContrato order by a.id desc")
				.setParameter("numeroContrato", numeroContrato+"%");
		try {
			return (Contrato) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
