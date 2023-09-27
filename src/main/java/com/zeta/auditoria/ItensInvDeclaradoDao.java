package com.zeta.auditoria;

import java.util.List;

import javax.persistence.EntityManager;

import com.zeta.dao.DAO;
import com.zeta.util.JPAUtil;

public class ItensInvDeclaradoDao {

	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<ItensInvDeclaradoModel> dao;
	
	
	public ItensInvDeclaradoDao() {
		this.dao = new DAO<ItensInvDeclaradoModel>(em, ItensInvDeclaradoModel.class);
	}
	
	
	public void adiciona(ItensInvDeclaradoModel t) {
		dao.adiciona(t);
		//dao.adicionarBatch(t);
	}

	public void remove(ItensInvDeclaradoModel t) {
		dao.remove(t);
	}

	public void atualiza(ItensInvDeclaradoModel t) {
		dao.atualiza(t);
	}

	public List<ItensInvDeclaradoModel> listaTodos() {
		return dao.listaTodos();
	}

	public ItensInvDeclaradoModel buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
