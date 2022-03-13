package com.zeta.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.zeta.model.ItensInventario;
import com.zeta.util.JPAUtil;

public class ItensInventarioDao {

	private EntityManager em = JPAUtil.getEntityManager();
	
	private DAO<ItensInventario> dao;
	
	
	public ItensInventarioDao() {
		this.dao = new DAO<ItensInventario>(em, ItensInventario.class);
	}
	
	public void adiciona(ItensInventario t) {
		dao.adiciona(t);
		//dao.adicionarBatch(t);
	}

	public void remove(ItensInventario t) {
		dao.remove(t);
	}

	public void atualiza(ItensInventario t) {
		dao.atualiza(t);
	}

	public List<ItensInventario> listaTodos() {
		return dao.listaTodos();
	}

	public ItensInventario buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public ItensInventario buscaPorCodigo(String codItem) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "SELECT inv FROM ItensInventario inv WHERE inv.codItem = :pcodItem";
		
		TypedQuery<ItensInventario> createQuery = em.createQuery(jpql,ItensInventario.class);
		createQuery.setParameter("pcodItem", codItem);
		
		return createQuery.getSingleResult();
	}
	
	public List<ItensInventario> buscaPorIdPai(Long idPai) {
		List<ItensInventario> retorno = new ArrayList<ItensInventario>();
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "SELECT inv FROM ItensInventario inv WHERE inv.idPai = :pidPai";
		TypedQuery<ItensInventario> createQuery = null;
		try {
			
			createQuery = em.createQuery(jpql,ItensInventario.class);
			createQuery.setParameter("pidPai", idPai);
			retorno = createQuery.getResultList();
		
		
		} catch (Exception e) {
			System.out.println(e.getMessage() + " Não existe essa operação para esse item");
		}
		
		
		return retorno;
	}
	
	public int contaTodos() {
		return dao.contaTodos();
	}
}
