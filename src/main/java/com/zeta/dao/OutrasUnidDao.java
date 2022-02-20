package com.zeta.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.zeta.model.OutrasUnid;
import com.zeta.util.JPAUtil;

public class OutrasUnidDao {

	 private EntityManager em = JPAUtil.getEntityManager();
		
		private DAO<OutrasUnid> dao;
		
		public OutrasUnidDao() {
			this.dao = new DAO<OutrasUnid>(em, OutrasUnid.class);
		}
		
		public void adiciona(OutrasUnid t) {
			dao.adiciona(t);
		}

		public void remove(OutrasUnid t) {
			dao.remove(t);
		}

		public void atualiza(OutrasUnid t) {
			dao.atualiza(t);
		}

		public List<OutrasUnid> listaTodos() {
			return dao.listaTodos();
		}

		public OutrasUnid buscaPorId(Long id) {
			return dao.buscaPorId(id);
		}

		public int contaTodos() {
			return dao.contaTodos();
		}
}
