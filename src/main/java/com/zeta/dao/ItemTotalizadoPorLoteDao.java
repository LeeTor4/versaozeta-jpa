package com.zeta.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.zeta.model.InventarioDeclaradoSped;
import com.zeta.model.ItemTotalizadoPorLote;
import com.zeta.model.ItemTotalizadoPorLoteJoinProduto;
import com.zeta.model.SaldoItemAnual;
import com.zeta.util.JPAUtil;
import com.zeta.util.UtilsEConverters;

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
						.getResultList();
			} catch (Exception e) {
				System.out.println(e.getMessage() + " Não existe essa operação para esse item");
			}
			
			return singleResult;
		}
		
		
		public  List<ItemTotalizadoPorLoteJoinProduto> buscaListaItensPorAnoJoinProduto(String cnpj){
			
			Query query = em.createNativeQuery(
	                "SELECT tot.ano,tot.cnpj,tot.codItem,prod.descricao,prod.UnidadedeMedidaPadrao,tot.mes,tot.operacao,if(tot.operacao = 'S',tot.vlTotQtde*(-1),tot.vlTotQtde) as vlTotQtde,tot.vlTotItem\r\n"
	                + "FROM tb_saldo_itn_tot_lote as tot\r\n"
	                + "join tb_produto as prod\r\n"
	                + "on tot.codItem = prod.codUtilizEstab where tot.cnpj = :cnpj",
	                "mapeamento.ItemTotalizadoPorLoteJoinProduto");

			query.setParameter("cnpj", cnpj);
			List<ItemTotalizadoPorLoteJoinProduto> lista = query.getResultList();
				
//	        lista.stream().forEach(u -> System.out.println(
//	                String.format("Item => ano: %s, CodItem: %s, Descrição: %s", u.getAno(), u.getCodItem(), u.getDescricao())));
	        
	        return lista;
		}
		
		
		@SuppressWarnings("unchecked")
		public List<ItemTotalizadoPorLote> buscarListaItensPorAno(String ano, String cnpj) {
		
			String jpql = "SELECT tot FROM ItemTotalizadoPorLote tot WHERE tot.ano = :ano and tot.cnpj = :cnpj group by tot.codItem";
			
			return (List<ItemTotalizadoPorLote>) em.createQuery(jpql)
					.setParameter("ano", ano)
					.setParameter("cnpj", cnpj)
					.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		public SaldoItemAnual buscarSaldoItemPorAno(String cnpj, String ano, String codItem) {
			SaldoItemAnual retorno = new SaldoItemAnual();
			String jpql = "SELECT cnpj,ano,codItem, SUM(vlTotQtde) AS vlTotQtde,SUM(vlTotItem) AS vlTotItem FROM ItemTotalizadoPorLote where cnpj = :cnpj and ano < :ano and codItem = :codItem";
			TypedQuery<Object[]> result = 
					em.createQuery(jpql, Object[].class)
					.setParameter("cnpj", cnpj)
					.setParameter("ano", ano)
					.setParameter("codItem", codItem);

			 List<Object[]> listaArr =  result.getResultList();			 
			 //listaArr.forEach(arr -> System.out.println(String.format("%s, %s , %s , %f ", arr)));
			 List<Object[]> collect = listaArr.stream().collect(Collectors.toList());
			 for(Object[] attr : collect){
				 //System.out.println(attr[0] + "|" + attr[1] + "|" + attr[2]+ "|" + attr[3]+ "|" + attr[4]);
				 retorno.setCnpj((String) attr[0]);
				 retorno.setAno((String) attr[1]);
				 retorno.setCodItem((String) attr[2]);
				 retorno.setVlTotQtde((Double) attr[3]);
				 retorno.setVlTotItem((Double) attr[4]);
			 }
			 return retorno;
		}
		

		public List<InventarioDeclaradoSped> buscarInvDecSped(String cnpj, int ano) {
			List<InventarioDeclaradoSped> retorno = new ArrayList<InventarioDeclaradoSped>();
			
			String jpql = "SELECT lote.cnpj, year(inv.dataInv), itn.codItem, itn.qtde, itn.und, itn.vlUnit, itn.vlItem  FROM LoteImportacaoSpedFiscal lote JOIN lote.invDec inv JOIN inv.itensInv itn WHERE lote.cnpj = :cnpj and year(inv.dataInv) = :ano";
		
			TypedQuery<Object[]> result = 
					em.createQuery(jpql, Object[].class)
					.setParameter("cnpj", cnpj)
					.setParameter("ano", ano);
			 
			 List<Object[]> listaArr =  result.getResultList();					 
			 List<Object[]> collect = listaArr.stream().collect(Collectors.toList());
			 for(Object[] attr : collect){
				 InventarioDeclaradoSped obj = new InventarioDeclaradoSped();
				 //System.out.println(attr[0] + "|" + attr[1] + "|" + UtilsEConverters.getRemoverZeroAEsquerda((String) attr[2]) + "|" + attr[3] + "|" + attr[4] + "|" + attr[5]+ "|" + attr[6]);
				 obj.setCnpj((String) attr[0]);
				 obj.setAno((int) attr[1]);				
				 obj.setCodItem(UtilsEConverters.getRemoverZeroAEsquerda((String) attr[2]));
				 obj.setQtde((Double) attr[3]);
				 obj.setUnd((String) attr[4]);
				 obj.setVlUnit((Double) attr[5]);
				 obj.setVlItem((Double) attr[6]); 
				 retorno.add(obj);
			 }
			
			return retorno;
		}
	
}
