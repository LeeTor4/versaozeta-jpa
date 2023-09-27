package com.zeta.auditoria;

import java.util.List;

import javax.persistence.EntityManager;

import com.zeta.dao.DAO;
import com.zeta.util.JPAUtil;

public class HistoricoItemDao {

	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<HistoricoItemModel> dao;
	
	
	public HistoricoItemDao() {
		this.dao = new DAO<HistoricoItemModel>(em, HistoricoItemModel.class);
	}
	
	
	public void adiciona(HistoricoItemModel t) {
		dao.adiciona(t);
		//dao.adicionarBatch(t);
	}

	public void remove(HistoricoItemModel t) {
		dao.remove(t);
	}

	public void atualiza(HistoricoItemModel t) {
		dao.atualiza(t);
	}

	public List<HistoricoItemModel> listaTodos() {
		return dao.listaTodos();
	}

	public HistoricoItemModel buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
