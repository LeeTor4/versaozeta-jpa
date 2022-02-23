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
		
		public ItemTotalizadoPorLote buscarTotalizadorComFiltros(String codItem, String mes, String operacao) {
			
			String jpql = "SELECT tot FROM ItemTotalizadoPorLote tot WHERE tot.codItem = :codItem AND tot.mes = :mes AND tot.operacao = :operacao";
			ItemTotalizadoPorLote singleResult = null;
			try {
				singleResult = (ItemTotalizadoPorLote) em.createQuery(jpql)
						.setParameter("codItem", codItem)
						.setParameter("mes", mes)
						.setParameter("operacao", operacao)
						.getSingleResult();
			} catch (Exception e) {
				System.out.println(e.getMessage() + " Não existe essa operação para esse item");
			}
			
			return singleResult;
		}
		
				
}
