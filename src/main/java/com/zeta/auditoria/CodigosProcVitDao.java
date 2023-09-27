package com.zeta.auditoria;

import java.util.List;

import javax.persistence.EntityManager;

import com.zeta.dao.DAO;
import com.zeta.util.JPAUtil;

public class CodigosProcVitDao {

	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<CodigosProcVitModel> dao;
	
	
	public CodigosProcVitDao() {
		this.dao = new DAO<CodigosProcVitModel>(em, CodigosProcVitModel.class);
	}
	
	
	public void adiciona(CodigosProcVitModel t) {
		dao.adiciona(t);
		//dao.adicionarBatch(t);
	}

	public void remove(CodigosProcVitModel t) {
		dao.remove(t);
	}

	public void atualiza(CodigosProcVitModel t) {
		dao.atualiza(t);
	}

	public List<CodigosProcVitModel> listaTodos() {
		return dao.listaTodos();
	}

	public CodigosProcVitModel buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
