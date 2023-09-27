package com.zeta.auditoria;

import java.util.List;

import javax.persistence.EntityManager;

import com.zeta.dao.DAO;
import com.zeta.util.JPAUtil;

public class ProdutosLevantamento2012Dao {

	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<ProdutosLevantamento2012Model> dao;
	
	
	public ProdutosLevantamento2012Dao() {
		this.dao = new DAO<ProdutosLevantamento2012Model>(em, ProdutosLevantamento2012Model.class);
	}
	
	
	public void adiciona(ProdutosLevantamento2012Model t) {
		dao.adiciona(t);
		//dao.adicionarBatch(t);
	}

	public void remove(ProdutosLevantamento2012Model t) {
		dao.remove(t);
	}

	public void atualiza(ProdutosLevantamento2012Model t) {
		dao.atualiza(t);
	}

	public List<ProdutosLevantamento2012Model> listaTodos() {
		return dao.listaTodos();
	}

	public ProdutosLevantamento2012Model buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
