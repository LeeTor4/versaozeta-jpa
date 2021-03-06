package com.zeta.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.zeta.model.HistoricoItens;
import com.zeta.util.JPAUtil;


public class HistoricoItensDao {
	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<HistoricoItens> dao;
	
	
	public HistoricoItensDao() {
		this.dao = new DAO<HistoricoItens>(em, HistoricoItens.class);
	}
	
	
	public void adiciona(HistoricoItens t) {
		dao.adiciona(t);
		//dao.adicionarBatch(t);
	}

	public void remove(HistoricoItens t) {
		dao.remove(t);
	}

	public void atualiza(HistoricoItens t) {
		dao.atualiza(t);
	}

	public List<HistoricoItens> listaTodos() {
		return dao.listaTodos();
	}

	public HistoricoItens buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
	
	
	public List<HistoricoItens> buscaHisItemPorCnpjCodigoAno(String cnpj, String codigo, int ano) {
		
		String jpql = "SELECT h FROM HistoricoItens h WHERE h.empresa = :cnpj AND h.codItem IN(:codigo,:codigo) AND YEAR(h.dtDoc)= :ano ORDER BY h.dtDoc,h.codItem,h.operacao";
		
		TypedQuery<HistoricoItens>  query = em.createQuery(jpql,HistoricoItens.class);
		query.setParameter("cnpj", cnpj);
		query.setParameter("codigo", codigo);
		query.setParameter("ano", ano);
		
		return query.getResultList();
	}
	
	public HistoricoItens buscarUltimaCompra(String cnpj, String codigo, int ano) {
		String jpql = "SELECT h FROM HistoricoItens h WHERE h.empresa = :cnpj AND h.codItem IN(:codigo,:codigo) and h.cfop in ('1102','1403','2102','2403') AND YEAR(h.dtDoc) < :ano ORDER BY h.dtDoc DESC";
	
		TypedQuery<HistoricoItens>  query = em.createQuery(jpql,HistoricoItens.class);
		query.setParameter("cnpj", cnpj);
		query.setParameter("codigo", codigo);
		query.setParameter("ano", ano);
		query.setMaxResults(1);
		
		HistoricoItens singleResult = null;
		try {			
			singleResult = query.getSingleResult();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		return singleResult;
	
	}
	
	public HistoricoItens buscarUltimaTransferencia(String cnpj, String codigo, int ano) {
		String jpql = "SELECT h FROM HistoricoItens h WHERE h.empresa = :cnpj AND h.codItem IN(:codigo,:codigo) and h.cfop in ('1152','1409') AND YEAR(h.dtDoc) < :ano ORDER BY h.dtDoc DESC";
	
		TypedQuery<HistoricoItens>  query = em.createQuery(jpql,HistoricoItens.class);
		query.setParameter("cnpj", cnpj);
		query.setParameter("codigo", codigo);
		query.setParameter("ano", ano);
		query.setMaxResults(1);
		
		HistoricoItens singleResult = null;
		try {			
			singleResult = query.getSingleResult();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		return singleResult;
	
	}
}
