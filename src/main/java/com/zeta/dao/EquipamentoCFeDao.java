package com.zeta.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.zeta.model.EquipamentoCFe;
import com.zeta.util.JPAUtil;

public class EquipamentoCFeDao {
	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<EquipamentoCFe> dao;
	
	
	public EquipamentoCFeDao() {
		this.dao = new DAO<EquipamentoCFe>(em, EquipamentoCFe.class);
	}
	
	public void adiciona(EquipamentoCFe t) {
		dao.adiciona(t);
	}

	public void remove(EquipamentoCFe t) {
		dao.remove(t);
	}

	public void atualiza(EquipamentoCFe t) {
		dao.atualiza(t);
	}

	public List<EquipamentoCFe> listaTodos() {
		return dao.listaTodos();
	}

	public EquipamentoCFe buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
