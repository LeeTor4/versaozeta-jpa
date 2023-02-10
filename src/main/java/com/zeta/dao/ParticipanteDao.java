package com.zeta.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.zeta.model.Participante;
import com.zeta.util.JPAUtil;

public class ParticipanteDao {

	  private EntityManager em = JPAUtil.getEntityManager();
	  
	  private DAO<Participante> dao;
	  
	  
	  public ParticipanteDao() {
		this.dao = new DAO<Participante>(em, Participante.class);
	  }
	  
	  public void adiciona(Participante t) {
		//dao.adiciona(t);
		dao.adicionarBatch(t);
	  }
	  
	  public void adicionaLote(List<Participante> t) {
			//dao.adiciona(t);
			dao.adicionarBatchLote(t);
	   }

		public void remove(Participante t) {
			dao.remove(t);
		}

		public void atualiza(Participante t) {
			dao.atualiza(t);
		}

		public List<Participante> listaTodos() {
			return dao.listaTodos();
		}

		public Participante buscaPorId(Long id) {
			return dao.buscaPorId(id);
		}

		public int contaTodos() {
			return dao.contaTodos();
		}
}
