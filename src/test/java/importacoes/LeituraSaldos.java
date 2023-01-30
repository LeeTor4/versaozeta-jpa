package importacoes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import com.zeta.dao.HistoricoItensDao;
import com.zeta.dao.InventarioDeclaradoDao;
import com.zeta.dao.ItemTotalizadoPorLoteDao;
import com.zeta.dao.ItensInventarioDao;
import com.zeta.dao.OutrasUnidDao;
import com.zeta.dao.ProdutoDao;
import com.zeta.handler.ExportaRelacaoInventario;
import com.zeta.handler.ExportaRelacaoInventario.SaldoInicialControleEstoque;
import com.zeta.model.CadastroItensPorMovimentacao;
import com.zeta.model.HistoricoItens;
import com.zeta.model.InventarioDeclarado;
import com.zeta.model.InventarioDeclaradoSped;
import com.zeta.model.ItemTotalizadoPorLote;
import com.zeta.model.ItemTotalizadoPorLoteJoinProduto;
import com.zeta.model.ItensInventario;
import com.zeta.model.OutrasUnid;
import com.zeta.model.Produto;
import com.zeta.model.SaldoItemAnual;

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

		
		
		
		ItemTotalizadoPorLoteDao dao = new ItemTotalizadoPorLoteDao();

//	    Map<String, List<ItemTotalizadoPorLote>> collect = dao.listaTodos().stream()
//				.filter(cgc -> cgc.getCnpj().equals("05329222000176"))
//				.filter(year -> year.getAno().equals("2015"))
//				.filter(oper -> oper.getOperacao().equals("S"))
//				.collect(Collectors.groupingBy(codigo -> codigo.getCodItem()));
//	
//	
//	    
//	    Double qtdeEnt1  = 0.0;
//		Double vlTotEnt1 = 0.0;
//	    Double vlUnitEnt = 0.0;  
//	    for(ItemTotalizadoPorLote cod : collect.get("5943")){	    	
//	    	qtdeEnt1  += cod.getVlTotQtde();
//	    	vlTotEnt1 += cod.getVlTotItem();			 
//	    }
//	    vlUnitEnt = (vlTotEnt1/qtdeEnt1);
//	    System.out.println("5943" + "|" + qtdeEnt1 + "|" + vlUnitEnt + "|" + vlTotEnt1);
//		int cont = 0;
		
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
		
		
//	    HistoricoItensDao dao = new HistoricoItensDao();
//	    List<HistoricoItens> lista = dao.buscaHisItemPorCnpjCodigoAno("05329222000680", "8790", 2017);
//	    for(HistoricoItens h : lista){
//	    	System.out.println(h.getChaveDoc() + "|" + h.getCfop());
//	    }
	    
	    
	    //"05329222000761", "23814", 2020
//	   List<HistoricoItens> buscarUltimaCompra = dao.buscarUltimaCompra("05329222000680", "5518", 2017);
//       System.out.println(buscarUltimaCompra.get(0).getDtDoc() +"|"+ buscarUltimaCompra.get(0).getCfop()
//    		   +"|"+ buscarUltimaCompra.get(0).getCodItem() +"|"+ buscarUltimaCompra.get(0).getVlUnit());
	    
//       List<HistoricoItens> buscarUltimaTrans = dao.buscarUltimaTransferencia("05329222000680", "24512", 2017);
//       System.out.println(buscarUltimaTrans.get(0).getDtDoc() +"|"+ buscarUltimaTrans.get(0).getCfop()
//    		   +"|"+ buscarUltimaTrans.get(0).getCodItem() +"|"+ buscarUltimaTrans.get(0).getVlUnit()); 
       
       
        //dao.buscarUltimaTransferencia("05329222000680", "24512", 2017).getDescricao();
//	    if(dao.buscarUltimaCompra("05329222000680", "30786", 2017) != null){
//	    	 System.out.println( dao.buscarUltimaCompra("05329222000680", "30786", 2017).getCodItem() +"|"+
//	         		dao.buscarUltimaCompra("05329222000680", "30786", 2017).getDescricao());
//	    }else if(dao.buscarUltimaTransferencia("05329222000680", "30786", 2017) != null){
//	        System.out.println( dao.buscarUltimaTransferencia("05329222000680", "30786", 2017).getCodItem() +"|"+
//    		dao.buscarUltimaTransferencia("05329222000680", "30786", 2017).getDescricao());
//	    }
       
        
//        System.out.println( dao.buscarUltimaTransferencia("05329222000680", "24512", 2017).getCodItem() +"|"+
//        		dao.buscarUltimaTransferencia("05329222000680", "24512", 2017).getDescricao());
       
	    
//		String dirPlanHistItem   = "E:\\EMPRESAS".concat("\\").concat("SELLENE").concat("\\").concat("MEGAFARMA").concat("\\").concat("fichas_estoques").concat("\\");
//		String dirListaProds                     = "E:\\EMPRESAS".concat("\\").concat("SELLENE").concat("\\").concat("MEGAFARMA").concat("\\").concat("ListaCodItem2.csv");
//	    
//		
//		
//		System.out.println(dirListaProds);
		
		
//		ExportaRelacaoInventario relInv = new ExportaRelacaoInventario();
//		String dirListaProds  = "E:\\EMPRESAS".concat("\\").concat("SELLENE").concat("\\").concat("MEGAFARMA").concat("\\").concat("INV_INI_2017.csv");
//		List<SaldoInicialControleEstoque> importaListaProdutos = relInv.importaListaProdutos(dirListaProds);
//		
//	    for(SaldoInicialControleEstoque inv : importaListaProdutos){
//	    	if( inv.getQtdeInicial() > 0) {
//	    		System.out.println(inv.getAno() +"|" + inv.getCodItem()+"|" + inv.getQtdeInicial());
//	    	}
//	    	
//	    }
		
//		SaldoItemAnual buscarSaldoItemPorAno = dao.buscarSaldoItemPorAno("24653373000120", "2021","32100");
//		System.out.println(buscarSaldoItemPorAno.getAno() + " " + buscarSaldoItemPorAno.getCnpj()+ " " + buscarSaldoItemPorAno.getCodItem()
//		     + " " + buscarSaldoItemPorAno.getVlTotQtde()+ " " + buscarSaldoItemPorAno.getVlTotItem());  
		
//		InventarioDeclaradoSped buscarInvDecSped = dao.buscarInvDecSped("24653373000120", 2020-1, "21541");
//		System.out.println(buscarInvDecSped.getCnpj()+"|"+buscarInvDecSped.getAno()+"|"+ buscarInvDecSped.getCodItem()
//		     +"|"+buscarInvDecSped.getQtde()+"|"+buscarInvDecSped.getUnd()+"|"+buscarInvDecSped.getVlUnit()+"|"+buscarInvDecSped.getVlItem());
	    
		
		
		
		
		//========================================================================================================================
		
		
	
//		List<ItemTotalizadoPorLoteJoinProduto> lista = dao.buscaListaItensPorAnoJoinProduto("24653373000120");
//		List<InventarioDeclaradoSped> buscarInvDecSped = dao.buscarInvDecSped("24653373000120", 2019);
//		List<CadastroItensPorMovimentacao> lista2 = dao.buscaListaItensPorAnoJoinTotalizadorJoinInvJoinProduto("24653373000120", 2020);
		
//	    lista.stream()
//		        .filter(c -> Integer.parseInt(c.getAno()) == 2019)
//		        .distinct()
//		        .forEach(u -> System.out.println(
//			                String.format("Item => ano: %s, CodItem: %s, Descrição: %s, Und: %s", u.getAno(), u.getCodItem(), u.getDescricao(), u.getUnidadeDeMedidaPadrao())));
	    
//		lista2.stream()
//        .forEach(u -> System.out.println(
//	                String.format("Item => CodItem: %s, Descrição: %s, Und: %s", u.getCodItem(), u.getDescricao(), u.getUndMed())));
		
//	    lista.stream()
//	           .filter(c -> Integer.parseInt(c.getAno()) < 2020)
//	           .forEach(u -> System.out.println(
//		                String.format("Item => ano: %s, CodItem: %s, Descrição: %s", u.getAno(), u.getCodItem(), u.getDescricao())));
	
	
		
//		Double qtdeEntSum = 0.0;
//		Double qtdeSaiSum = 0.0;
//		Double saldo = 0.0;
//		qtdeEntSum = lista.stream()
//					 .filter(ano -> Integer.parseInt(ano.getAno()) < 2020)
//					 .filter(operacao -> operacao.getOperacao().equals("E"))
//					 .filter(codItem -> codItem.getCodItem().equals("22363"))
//					 .mapToDouble(ItemTotalizadoPorLoteJoinProduto::getVlTotQtde).sum();
//		qtdeSaiSum = lista.stream()
//					 .filter(ano -> Integer.parseInt(ano.getAno()) < 2020)
//					 .filter(operacao -> operacao.getOperacao().equals("S"))
//					 .filter(codItem -> codItem.getCodItem().equals("22363"))
//					 .mapToDouble(ItemTotalizadoPorLoteJoinProduto::getVlTotQtde).sum();
//		
//		saldo = qtdeEntSum - (-qtdeSaiSum);
//		
//		System.out.println("22363 : " + qtdeEntSum + " - " + qtdeSaiSum + " = " + saldo);
	
		
	    
		
		
//	    Map<String, Double> collect = lista.stream()
//	    		 .filter(ano -> Integer.parseInt(ano.getAno()) < 2020)
//	    		 .collect(
//             Collectors.groupingBy(ItemTotalizadoPorLoteJoinProduto::getCodItem, Collectors.summingDouble(ItemTotalizadoPorLoteJoinProduto::getVlTotQtde))
//        );
//
//
//       for(String key : collect.keySet()){
//    	   System.out.println(key + " => " + collect.get(key));
//       }
    		   

//		Double mapToDouble = buscarInvDecSped.stream()
//				 .filter(codItem -> codItem.getCodItem().equals("4944"))
//				 .mapToDouble(qtde -> qtde.getQtde())
//				 .sum();
//		
//		System.out.println(mapToDouble);
		
		
//		buscarInvDecSped.forEach(u -> System.out.println(
//                String.format("Item => CodItem: %s, Descrição: %s, Und: %s", u.getCodItem(), u.getQtde(), u.getVlUnit())));
		
		

//		Double mapToDouble = buscarInvDecSped.stream().filter(codItem -> codItem.getCodItem().equals("102")).mapToDouble(qtde -> qtde.getQtde()).sum();
//		
//		System.out.println(mapToDouble);
		
		
		//======================================================================================
		
		
		//36292|NULL
		//4915|1.0
		String codItem = "13";
		
		
		OutrasUnidDao daoOut = new OutrasUnidDao();
		List<OutrasUnid> collectOutUnd = daoOut.listaTodos().stream()
		           .collect(Collectors.toList());	
		
		ProdutoDao daoProd = new ProdutoDao();
		List<Produto> collectProd = daoProd.listaTodos().stream()
				.collect(Collectors.toList());	
		
		Map<String,Produto> mapProduto = new HashMap<String, Produto>();		
		for(Produto p :  collectProd){
			mapProduto.put(p.getCodUtilizEstab(), p);
		}
		
		Map<String,OutrasUnid> mapOutUndMedida = new HashMap<String, OutrasUnid>();
		for(OutrasUnid p :  collectOutUnd){
			mapOutUndMedida.put(p.getCodProd(), p);
		}
		
		
		System.out.println(mapProduto.get(codItem).getCodUtilizEstab() +"|"+(mapOutUndMedida.get(codItem) == null ? "NULL":mapOutUndMedida.get(codItem).getUndEquivPadrao()));
			
		//System.out.println(mapProduto.get(codItem));
		
		
		
	    
		
		
	    
		

//		for(String key : mapOutUndMedida.keySet()){
//			System.out.println(key+"|"+mapOutUndMedida.get(key).getUndEquivPadrao());
//		}
		
		
		
		
		
		
		
		
		
	
//		if(codigos.contains("38341")) {
//			System.out.println("Produto consta na base");
//		}else {
//			System.out.println("Produto não consta na base");
//		}

	}

}
