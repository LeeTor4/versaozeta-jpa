package com.zeta.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.zeta.model.ItemTotalizadoPorLote;
import com.zeta.util.JPAUtil;

public class ItemTotalizadoPorLoteDao {

	 private EntityManager em = JPAUtil.getEntityManager();
	 
	 private DAO<ItemTotalizadoPorLote> dao;
	 
	 public ItemTotalizadoPorLoteDao() {
		 this.dao = new DAO<ItemTotalizadoPorLote>(em, ItemTotalizadoPorLote.class);
	}
	 
	 public void adiciona(ItemTotalizadoPorLote t) {
			//dao.adiciona(t);
			dao.adicionarBatch(t);
		}

		public void remove(ItemTotalizadoPorLote t) {
			dao.remove(t);
		}

		public void atualiza(ItemTotalizadoPorLote t) {
			dao.atualiza(t);
		}

		public List<ItemTotalizadoPorLote> listaTodos() {
			return dao.listaTodos();
		}

		public ItemTotalizadoPorLote buscaPorId(Long id) {
			return dao.buscaPorId(id);
		}

		public int contaTodos() {
			return dao.contaTodos();
		}
		
		public void closeEm() {
			JPAUtil.fecha();
		}
		
}
