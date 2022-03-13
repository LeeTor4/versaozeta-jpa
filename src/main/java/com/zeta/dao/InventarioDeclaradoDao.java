package com.zeta.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.zeta.model.InventarioDeclarado;
import com.zeta.util.JPAUtil;

public class InventarioDeclaradoDao {

	private EntityManager em = JPAUtil.getEntityManager();
	
	private DAO<InventarioDeclarado> dao;
	
	
	public InventarioDeclaradoDao() {
		this.dao = new DAO<InventarioDeclarado>(em, InventarioDeclarado.class);
	}
	
	public void adiciona(InventarioDeclarado t) {
		dao.adiciona(t);
		//dao.adicionarBatch(t);
	}

	public void remove(InventarioDeclarado t) {
		dao.remove(t);
	}

	public void atualiza(InventarioDeclarado t) {
		dao.atualiza(t);
	}

	public List<InventarioDeclarado> listaTodos() {
		return dao.listaTodos();
	}

	public InventarioDeclarado buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
	
	public List<InventarioDeclarado> buscaPorAnoEmpresaEstab(int ano, Long idEmp, Long idEst) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "SELECT invDec FROM InventarioDeclarado invDec where year(invDec.dataInv) = :pAno and invDec.idEmp = :pIdEmp and invDec.idEst = :pIdEst";
		TypedQuery<InventarioDeclarado>  query = em.createQuery(jpql,InventarioDeclarado.class);
		query.setParameter("pAno", ano);
		query.setParameter("pIdEmp",idEmp);
		query.setParameter("pIdEst", idEst);

		
		
		return query.getResultList();
	}
}
