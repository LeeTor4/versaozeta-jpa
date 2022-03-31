package importacoes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.zeta.dao.HistoricoItensDao;
import com.zeta.dao.InventarioDeclaradoDao;
import com.zeta.dao.ItemTotalizadoPorLoteDao;
import com.zeta.dao.ItensInventarioDao;
import com.zeta.model.HistoricoItens;
import com.zeta.model.InventarioDeclarado;
import com.zeta.model.ItemTotalizadoPorLote;
import com.zeta.model.ItensInventario;

public class LeituraSaldos {
	
	
	public static void main(String[] args) {
//		ItemTotalizadoPorLoteDao dao = new ItemTotalizadoPorLoteDao();
//		
//		List<ItemTotalizadoPorLote> lista =  dao.listaTodos().stream()
//										            .filter(cnpj -> cnpj.getCnpj().equals("05329222000680"))
//										            .filter(ano -> ano.getAno().equals("2017"))
//										            .distinct()
//		
//										            .collect(Collectors.toList());
//		System.out.println(lista); 
//		
//		Double qtdeEntJan = dao.buscarTotalizadorComFiltros("22998", "1", "E").getVlTotQtde();
//		Double vlEntJan   = dao.buscarTotalizadorComFiltros("22998", "1", "E").getVlTotItem();
//		Double qtdeSaiJan = 0.0;				
//		//Double vlSaiJan   = dao.buscarTotalizadorComFiltros("22998", "1", "S").getVlTotItem();
//
//		if(dao.buscarTotalizadorComFiltros("22998", "1", "S") != null) {
//			qtdeSaiJan = dao.buscarTotalizadorComFiltros("22998", "1", "S").getVlTotQtde();		
//		}
//		System.out.println(qtdeEntJan + "|" + vlEntJan + "|" + qtdeSaiJan );
		 
//		InventarioDeclaradoDao invDec = new InventarioDeclaradoDao();
//		InventarioDeclarado buscaPorId = invDec.buscaPorAnoEmpresaEstab(2020, 1L, 3L);
//		ItensInventarioDao     itemDao = new ItensInventarioDao();		
//		if( itemDao.buscaPorIdPai(buscaPorId.getId(), "00023339") != null){
//			System.out.println(
//				     itemDao.buscaPorIdPai(buscaPorId.getId(), "00023339").getCodItem()
//				+"|"+itemDao.buscaPorIdPai(buscaPorId.getId(), "00023339").getQtde()
//				+"|"+itemDao.buscaPorIdPai(buscaPorId.getId(), "00023339").getVlUnit()
//				+"|"+itemDao.buscaPorIdPai(buscaPorId.getId(), "00023339").getUnd()
//				+"|"+itemDao.buscaPorIdPai(buscaPorId.getId(), "00023339").getVlItem()); 
//		}

		
		
		
//		ItemTotalizadoPorLoteDao dao = new ItemTotalizadoPorLoteDao();
//
//	    Map<String, List<ItemTotalizadoPorLote>> collect = dao.listaTodos().stream()
//				.filter(cgc -> cgc.getCnpj().equals("05329222000761"))
//				.filter(year -> year.getAno().equals("2017"))
//				.filter(oper -> oper.getOperacao().equals("E"))
//				.collect(Collectors.groupingBy(codigo -> codigo.getCodItem()));
//	
//	
//	    
//	    Double qtdeEnt1  = 0.0;
//		Double vlTotEnt1 = 0.0;
//	    Double vlUnitEnt = 0.0;  
//	    for(ItemTotalizadoPorLote cod : collect.get("5805")){	    	
//	    	qtdeEnt1  += cod.getVlTotQtde();
//	    	vlTotEnt1 += cod.getVlTotItem();			 
//	    }
//	    vlUnitEnt = (vlTotEnt1/qtdeEnt1);
//	    System.out.println("5805" + "|" + qtdeEnt1 + "|" + vlUnitEnt + "|" + vlTotEnt1);
//		int cont = 0;
//		ItemTotalizadoPorLoteDao dao = new ItemTotalizadoPorLoteDao();
//		List<ItemTotalizadoPorLote> listaProdutos = dao.buscarListaItensPorAno("2019","05329222000761");
//		for (ItemTotalizadoPorLote lista : listaProdutos) {
//			cont++;
//			System.out.println(cont+"|"+lista.getCodItem());
//		}
		
//		InventarioDeclaradoDao invDec = new InventarioDeclaradoDao();
//		ItensInventarioDao itemInv = new ItensInventarioDao();
//		List<InventarioDeclarado> inventarios = invDec.buscaPorAnoEmpresaEstab(2017, 1L, 3L);
//        
//		for(InventarioDeclarado busca  : inventarios){
//			if(busca.getVlTotal() > 0) {
//				for(ItensInventario item : itemInv.buscaPorIdPai(busca.getId())){
//					System.out.println(busca.getId()+"|"+busca.getIdEmp()+"|"+busca.getIdEst()
//		            +"|"+busca.getDataInv() +"|"+busca.getVlTotal() + "|" + item.getCodItem()
//		            + "|" + item.getUnd() 
//		            + "|" + item.getQtde()+ "|" + item.getVlUnit()+ "|" + item.getVlItem());	
//				}
//		    }
//		}
		
		
	    HistoricoItensDao dao = new HistoricoItensDao();
//	    List<HistoricoItens> lista = dao.buscaHisItemPorCnpjCodigoAno("05329222000680", "8790", 2017);
//	    for(HistoricoItens h : lista){
//	    	System.out.println(h.getChaveDoc() + "|" + h.getCfop());
//	    }
	    
	    
	    //"05329222000761", "23814", 2020
//	   List<HistoricoItens> buscarUltimaCompra = dao.buscarUltimaCompra("05329222000761", "23814", 2020);
//       System.out.println(buscarUltimaCompra.get(0).getDtDoc() +"|"+ buscarUltimaCompra.get(0).getCfop()
//    		   +"|"+ buscarUltimaCompra.get(0).getCodItem() +"|"+ buscarUltimaCompra.get(0).getVlUnit());
	    
//       List<HistoricoItens> buscarUltimaTrans = dao.buscarUltimaTransferencia("05329222000761", "23814", 2020);
//       System.out.println(buscarUltimaTrans.get(0).getDtDoc() +"|"+ buscarUltimaTrans.get(0).getCfop()
//    		   +"|"+ buscarUltimaTrans.get(0).getCodItem() +"|"+ buscarUltimaTrans.get(0).getVlUnit()); 
       
       
       
       
	    
//		String dirPlanHistItem   = "E:\\EMPRESAS".concat("\\").concat("SELLENE").concat("\\").concat("MEGAFARMA").concat("\\").concat("fichas_estoques").concat("\\");
//		String dirListaProds                     = "E:\\EMPRESAS".concat("\\").concat("SELLENE").concat("\\").concat("MEGAFARMA").concat("\\").concat("ListaCodItem2.csv");
//	    
//		
//		
//		System.out.println(dirListaProds);
	
	}

}
