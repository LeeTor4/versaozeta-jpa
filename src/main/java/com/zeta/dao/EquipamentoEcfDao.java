package com.zeta.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.zeta.model.EquipamentoECF;
import com.zeta.util.JPAUtil;


public class EquipamentoEcfDao {

private EntityManager em = JPAUtil.getEntityManager();
	
	private DAO<EquipamentoECF> dao;
	
	public EquipamentoEcfDao() {
		this.dao = new DAO<EquipamentoECF>(em, EquipamentoECF.class);
	}
	
	public void adiciona(EquipamentoECF t) {
		dao.adiciona(t);
	}

	public void remove(EquipamentoECF t) {
		dao.remove(t);
	}

	public void atualiza(EquipamentoECF t) {
		dao.atualiza(t);
	}

	public List<EquipamentoECF> listaTodos() {
		return dao.listaTodos();
	}

	public EquipamentoECF buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
	
	public EquipamentoECF buscaPorNumFab(String nome) {
		
		String jpql = ("select e from EquipamentoECF e where e.numSerieFabECF =  :pNome");
		Query query = em.createQuery(jpql);
		query.setParameter("pNome", nome);
		EquipamentoECF equipamento = (EquipamentoECF) query.getSingleResult();
		return equipamento;
	}
}
