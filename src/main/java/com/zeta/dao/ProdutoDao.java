package com.zeta.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.zeta.model.OutrasUnid;
import com.zeta.model.Produto;
import com.zeta.util.JPAUtil;

public class ProdutoDao {

    private EntityManager em = JPAUtil.getEntityManager();
	
	private DAO<Produto> dao;
	
	public ProdutoDao() {
		this.dao = new DAO<Produto>(em, Produto.class);
		
	}
	
	public void adiciona(Produto t) {
		//dao.adiciona(t);
		dao.adicionarBatch(t);
	}
	
	public void adicionaLote(List<Produto> t) {
		//dao.adiciona(t);
		dao.adicionarBatchLote(t);
	}
	
	public void remove(Produto t) {
		dao.remove(t);
	}
	
	public void removeLote(List<Produto> t) {
		dao.removeLote(t);
	}

	public void atualizaLote(List<Produto> t) {
		dao.atualizaLote(t);
	}
	
	public void atualiza(Produto t) {
		dao.atualiza(t);
	}

	public List<Produto> listaTodos() {
		return dao.listaTodos();
	}

	public Produto buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
	
	public Produto buscaPorCodigo(String codigo) {
		EntityManager em = JPAUtil.getEntityManager();
		String sql = "SELECT * FROM tb_produto where codUtilizEstab = " + "'"+ codigo + "'";
		Query query = null;
	    Produto singleResult = null;
		try {
			query = em.createNativeQuery(sql, Produto.class);
			singleResult = (Produto) query.getSingleResult(); 
		}catch (NoResultException e) {
			//System.out.println("Produto n�o encontrado : " + e.getMessage()); 
		}
		
       
       
		return singleResult;
	}
	
	public Map<String,Produto> getMpProdutos(Long id_pai_emp,Long id_pai_est) {
		 Map<String,Produto> retorno = new HashMap<String, Produto>();
		 String sql = "SELECT * FROM tb_produto where idEmp = " + "'"+ id_pai_emp + "'"+ " and idEst = " + "'" + id_pai_est + "'";
		 Query query =  em.createNativeQuery(sql, Produto.class);
		 List<Produto> produtos = query.getResultList();
		 for(Produto p:  produtos){
			 retorno.put(p.getCodUtilizEstab(), p);
		 }
		 return retorno;
	}
	
	public String produtoJoinOutUnidadeMedida(Long id_pai_emp, String codUtilizEstab) {
		  String out = "";
		 String jpql = "select p, out from Produto p left join p.outrasUnds out where p.idEmp = " + "'"+ id_pai_emp + "'" + "and p.codUtilizEstab = " +"'"+ codUtilizEstab  +"'";
		 
		 TypedQuery<Object[]> typedQuery = em.createQuery(jpql, Object[].class);
	        List<Object[]> lista = typedQuery.getResultList();
	       
	        
	      for(Object[] arr : lista){
	    	    out = ((Produto) arr[0]).getCodUtilizEstab();
				if (arr[1] == null) {
					out += "|";
					out += "NULL";
				} else {
					out += "|" + ((OutrasUnid) arr[1]).getUndEquivPadrao();
				}
	      }

//	        lista.forEach(arr -> {
//	            String out = ((Produto) arr[0]).getCodUtilizEstab();
//	            if (arr[1] == null) {
//	                out += "|NULL";
//	            } else {
//	                out += "|" + ((OutrasUnid) arr[1]).getUndEquivPadrao();
//	            }
//
//	            System.out.println(out);
//	        });  
	        
	      return out;
	}
}
