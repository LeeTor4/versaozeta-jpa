package com.zeta.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.zeta.dao.ItemTotalizadoPorLoteDao;
import com.zeta.model.InventarioDeclaradoSped;
import com.zeta.model.ItemTotalizadoPorLoteJoinProduto;
import com.zeta.model.TotalizadoresDosSaldosMensais;

public class ExportaQuantitativoEstoque {
	
	public void exportaControleQuantitativos(String file, String cnpj, String ano) {

		ItemTotalizadoPorLoteDao dao = new ItemTotalizadoPorLoteDao();
		
		
		List<InventarioDeclaradoSped> buscarInvDecSped = dao.buscarInvDecSped(cnpj, Integer.parseInt(ano)-1);
		
		List<ItemTotalizadoPorLoteJoinProduto> listaProdutos = dao.buscaListaItensPorAnoJoinProduto(cnpj);
		
		List<ItemTotalizadoPorLoteJoinProduto> listaEnt = listaProdutos.stream()
				                                 .filter(oper -> oper.getOperacao().equals("E"))
				                                 .filter(year -> year.getAno().equals(ano)).collect(Collectors.toList());
		List<ItemTotalizadoPorLoteJoinProduto> listaSai = listaProdutos.stream()
				                                 .filter(oper -> oper.getOperacao().equals("S"))
				                                 .filter(year -> year.getAno().equals(ano)).collect(Collectors.toList());
		
		Map<String, Double> collect = listaProdutos.stream()
				 .filter(year -> Integer.parseInt(year.getAno()) < Integer.parseInt(ano))
	    		 .collect(
            Collectors.groupingBy(ItemTotalizadoPorLoteJoinProduto::getCodItem, Collectors.summingDouble(ItemTotalizadoPorLoteJoinProduto::getVlTotQtde))
        );

		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpEntJan = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpSaiJan = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpEntFev = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpSaiFev = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpEntMar = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpSaiMar = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpEntAbr = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpSaiAbr = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpEntMai = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpSaiMai = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpEntJun = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpSaiJun = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpEntJul = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpSaiJul = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpEntAgo = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpSaiAgo = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpEntSet = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpSaiSet = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpEntOut = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpSaiOut = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpEntNov = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpSaiNov = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpEntDez = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		Map<String , ItemTotalizadoPorLoteJoinProduto>   mpSaiDez = new HashMap<String , ItemTotalizadoPorLoteJoinProduto>();
		for(ItemTotalizadoPorLoteJoinProduto e : listaEnt){
			switch (e.getMes()) {
			case "1":
				mpEntJan.put(e.getCodItem(), e);
				break;
			case "2":
				mpEntFev.put(e.getCodItem(), e);
				break;
			case "3":
				mpEntMar.put(e.getCodItem(), e);
				break;
			case "4":
				mpEntAbr.put(e.getCodItem(), e);
				break;
			case "5":
				mpEntMai.put(e.getCodItem(), e);
				break;
			case "6":
				mpEntJun.put(e.getCodItem(), e);
				break;
			case "7":
				mpEntJul.put(e.getCodItem(), e);
				break;
			case "8":
				mpEntAgo.put(e.getCodItem(), e);
				break;
			case "9":
				mpEntSet.put(e.getCodItem(), e);
				break;
			case "10":
				mpEntOut.put(e.getCodItem(), e);
				break;
			case "11":
				mpEntNov.put(e.getCodItem(), e);
				break;
			case "12":
				mpEntDez.put(e.getCodItem(), e);
				break;
			}
		}
				
		for(ItemTotalizadoPorLoteJoinProduto e : listaSai){
			
			switch (e.getMes()) {
			case "1":
				mpSaiJan.put(e.getCodItem(), e);
				break;
			case "2":
				mpSaiFev.put(e.getCodItem(), e);
				break;
			case "3":
				mpSaiMar.put(e.getCodItem(), e);
				break;
			case "4":
				mpSaiAbr.put(e.getCodItem(), e);
				break;
			case "5":
				mpSaiMai.put(e.getCodItem(), e);
				break;
			case "6":
				mpSaiJun.put(e.getCodItem(), e);
				break;
			case "7":
				mpSaiJul.put(e.getCodItem(), e);
				break;
			case "8":
				mpSaiAgo.put(e.getCodItem(), e);
				break;
			case "9":
				mpSaiSet.put(e.getCodItem(), e);
				break;
			case "10":
				mpSaiOut.put(e.getCodItem(), e);
				break;
			case "11":
				mpSaiNov.put(e.getCodItem(), e);
				break;
			case "12":
				mpSaiDez.put(e.getCodItem(), e);
				break;
			}
			
		}
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));
			String linha = " ";

			linha = cabecalho();

			writer.write(linha);
			writer.newLine();

			for (ItemTotalizadoPorLoteJoinProduto lista : listaProdutos) {
				TotalizadoresDosSaldosMensais saldos = new TotalizadoresDosSaldosMensais();
				
				
				Double mapToDouble = buscarInvDecSped.stream()
						 .filter(codItem -> codItem.getCodItem().equals(lista.getCodItem()))
						 .mapToDouble(qtde -> qtde.getQtde())
						 .sum();
				
				if(mapToDouble != null) {
					saldos.setQteInvDec(mapToDouble);
				}
				
			    if(collect.keySet() != null) {
			    	saldos.setQteIniInv(collect.get(lista.getCodItem()));
			    }else {
			    	saldos.setQteIniInv(0.0);
			    }
				
								
				Double qtdeEntJan = 0.0;
				Double vlEntJan   = 0.0;
				Double qtdeSaiJan = 0.0;				
				Double vlSaiJan   = 0.0;								
                if(mpEntJan.get(lista.getCodItem()) != null) {               	                	
                	qtdeEntJan = mpEntJan.get(lista.getCodItem()).getVlTotQtde();
                	vlEntJan   = mpEntJan.get(lista.getCodItem()).getVlTotItem();	
                }                
                if(mpSaiJan.get(lista.getCodItem()) != null) {                	                
                	qtdeSaiJan = mpSaiJan.get(lista.getCodItem()).getVlTotQtde();
                	vlSaiJan   = mpSaiJan.get(lista.getCodItem()).getVlTotItem();                	
                }				
				saldos.setQtdeEntJan(qtdeEntJan);
				saldos.setVrEntJan(vlEntJan);
				saldos.setQtdeSaiJan(qtdeSaiJan*(-1));
				saldos.setVrSaiJan(vlSaiJan);
				

				Double qtdeEntFev = 0.0;		
				Double vlEntFev   = 0.0;
				Double qtdeSaiFev = 0.0;
				Double vlSaiFev   = 0.0;
                if(mpEntFev.get(lista.getCodItem()) != null) {               	                	
                	qtdeEntFev = mpEntFev.get(lista.getCodItem()).getVlTotQtde();
                	vlEntFev   = mpEntFev.get(lista.getCodItem()).getVlTotItem();
                	
                }                
                if(mpSaiFev.get(lista.getCodItem()) != null) {                	                
                	qtdeSaiFev = mpSaiFev.get(lista.getCodItem()).getVlTotQtde();
                	vlSaiFev   = mpSaiFev.get(lista.getCodItem()).getVlTotItem();                	
                }
				saldos.setQtdeEntFev(qtdeEntFev);
				saldos.setVrEntFev(vlEntFev);
				saldos.setQtdeSaiFev(qtdeSaiFev*(-1));
				saldos.setVrSaiFev(vlSaiFev);

				
				Double qtdeEntMar = 0.0;	
				Double vrEntMar   = 0.0;	
				Double qtdeSaiMar = 0.0;	
				Double vrSaiMar   = 0.0;	
                if(mpEntMar.get(lista.getCodItem()) != null) {               	                	
            		qtdeEntMar = mpEntMar.get(lista.getCodItem()).getVlTotQtde();
            		vrEntMar   = mpEntMar.get(lista.getCodItem()).getVlTotItem();
                	
                }                
                if(mpSaiMar.get(lista.getCodItem()) != null) {                	                
                	qtdeSaiMar = mpSaiMar.get(lista.getCodItem()).getVlTotQtde();
                	vrSaiMar   = mpSaiMar.get(lista.getCodItem()).getVlTotItem();                	
                }
				saldos.setQtdeEntMar(qtdeEntMar);
				saldos.setVrEntMar(vrEntMar);
				saldos.setQtdeSaiMar(qtdeSaiMar*(-1));
				saldos.setVrSaiMar(vrSaiMar);

				Double qtdeEntAbr = 0.0;	
				Double vrEntAbr   = 0.0;	
				Double qtdeSaiAbr = 0.0;	
				Double vrSaiAbr   = 0.0;	
                if(mpEntAbr.get(lista.getCodItem()) != null) {               	                	
                	qtdeEntAbr = mpEntAbr.get(lista.getCodItem()).getVlTotQtde();
                	vrEntAbr   = mpEntAbr.get(lista.getCodItem()).getVlTotItem();
                	
                }                
                if(mpSaiAbr.get(lista.getCodItem()) != null) {                	                
                	qtdeSaiAbr = mpSaiAbr.get(lista.getCodItem()).getVlTotQtde();
                	vrSaiAbr   = mpSaiAbr.get(lista.getCodItem()).getVlTotItem();                	
                }
				saldos.setQtdeEntAbr(qtdeEntAbr);
				saldos.setVrEntAbr(vrEntAbr);
				saldos.setQtdeSaiAbr(qtdeSaiAbr*(-1));
				saldos.setVrSaiAbr(vrSaiAbr);

				Double qtdeEntMai = 0.0;	
				Double vrEntMai   = 0.0;	
				Double qtdeSaiMai = 0.0;	
				Double vrSaiMai   = 0.0;	
                if(mpEntMai.get(lista.getCodItem()) != null) {               	                	
                	qtdeEntMai = mpEntMai.get(lista.getCodItem()).getVlTotQtde();
                	vrEntMai   = mpEntMai.get(lista.getCodItem()).getVlTotItem();
                	
                }                
                if(mpSaiMai.get(lista.getCodItem()) != null) {                	                
                	qtdeSaiMai = mpSaiMai.get(lista.getCodItem()).getVlTotQtde();
                	vrSaiMai   = mpSaiMai.get(lista.getCodItem()).getVlTotItem();                	
                }
				saldos.setQtdeEntMai(qtdeEntMai);
				saldos.setVrEntMai(vrEntMai);
				saldos.setQtdeSaiMai(qtdeSaiMai*(-1));
				saldos.setVrSaiMai(vrSaiMai);

				Double qtdeEntJun = 0.0;	
				Double vrEntJun   = 0.0;
				Double qtdeSaiJun = 0.0;
				Double vrSaiJun   = 0.0;
                if(mpEntJun.get(lista.getCodItem()) != null) {               	                	
                	qtdeEntJun = mpEntJun.get(lista.getCodItem()).getVlTotQtde();
                	vrEntJun   = mpEntJun.get(lista.getCodItem()).getVlTotItem();
                	
                }                
                if(mpSaiJun.get(lista.getCodItem()) != null) {                	                
                	qtdeSaiJun = mpSaiJun.get(lista.getCodItem()).getVlTotQtde();
                	vrSaiJun   = mpSaiJun.get(lista.getCodItem()).getVlTotItem();                	
                }
				saldos.setQtdeEntJun(qtdeEntJun);
				saldos.setVrEntJun(vrEntJun);
				saldos.setQtdeSaiJun(qtdeSaiJun*(-1));
				saldos.setVrSaiJun(vrSaiJun);

				Double qtdeEntJul =  0.0;	
				Double vrEntJul   =  0.0;	
				Double qtdeSaiJul =  0.0;	
				Double vrSaiJul   =  0.0;	
                if(mpEntJul.get(lista.getCodItem()) != null) {               	                	
                	qtdeEntJul = mpEntJul.get(lista.getCodItem()).getVlTotQtde();
                	vrEntJul   = mpEntJul.get(lista.getCodItem()).getVlTotItem();
                	
                }                
                if(mpSaiJul.get(lista.getCodItem()) != null) {                	                
                	qtdeSaiJul = mpSaiJul.get(lista.getCodItem()).getVlTotQtde();
                	vrSaiJul   = mpSaiJul.get(lista.getCodItem()).getVlTotItem();                	
                }
				saldos.setQtdeEntJul(qtdeEntJul);
				saldos.setVrEntJul(vrEntJul);
				saldos.setQtdeSaiJul(qtdeSaiJul*(-1));
				saldos.setVrSaiJul(vrSaiJul);

				Double qtdeEntAgo = 0.0;	
				Double vrEntAgo   = 0.0;	
				Double qtdeSaiAgo = 0.0;	
				Double vrSaiAgo   = 0.0;	
                if(mpEntAgo.get(lista.getCodItem()) != null) {               	                	
                	qtdeEntAgo = mpEntAgo.get(lista.getCodItem()).getVlTotQtde();
                	vrEntAgo   = mpEntAgo.get(lista.getCodItem()).getVlTotItem();
                	
                }                
                if(mpSaiAgo.get(lista.getCodItem()) != null) {                	                
                	qtdeSaiAgo = mpSaiAgo.get(lista.getCodItem()).getVlTotQtde();
                	vrSaiAgo   = mpSaiAgo.get(lista.getCodItem()).getVlTotItem();                	
                }
				saldos.setQtdeEntAgo(qtdeEntAgo);
				saldos.setVrEntAgo(vrEntAgo);
				saldos.setQtdeSaiAgo(qtdeSaiAgo*(-1));
				saldos.setVrSaiAgo(vrSaiAgo);

				Double qtdeEntSet = 0.0;
				Double vrEntSet   = 0.0;
				Double qtdeSaiSet = 0.0;
				Double vrSaiSet   = 0.0;
                if(mpEntSet.get(lista.getCodItem()) != null) {               	                	
                	qtdeEntSet = mpEntSet.get(lista.getCodItem()).getVlTotQtde();
                	vrEntSet   = mpEntSet.get(lista.getCodItem()).getVlTotItem();
                	
                }                
                if(mpSaiSet.get(lista.getCodItem()) != null) {                	                
                	qtdeSaiSet = mpSaiSet.get(lista.getCodItem()).getVlTotQtde();
                	vrSaiSet   = mpSaiSet.get(lista.getCodItem()).getVlTotItem();                	
                }
				saldos.setQtdeEntSet(qtdeEntSet);
				saldos.setVrEntSet(vrEntSet);
				saldos.setQtdeSaiSet(qtdeSaiSet*(-1));
				saldos.setVrSaiSet(vrSaiSet);

				Double qtdeEntOut = 0.0;
				Double vrEntOut   = 0.0;
				Double qtdeSaiOut = 0.0;
				Double vrSaiOut   = 0.0;
                if(mpEntOut.get(lista.getCodItem()) != null) {               	                	
                	qtdeEntOut = mpEntOut.get(lista.getCodItem()).getVlTotQtde();
                	vrEntOut   = mpEntOut.get(lista.getCodItem()).getVlTotItem();
                	
                }                
                if(mpSaiOut.get(lista.getCodItem()) != null) {                	                
                	qtdeSaiOut = mpSaiOut.get(lista.getCodItem()).getVlTotQtde();
                	vrSaiOut   = mpSaiOut.get(lista.getCodItem()).getVlTotItem();                	
                }
				saldos.setQtdeEntOut(qtdeEntOut);
				saldos.setVrEntOut(vrEntOut);
				saldos.setQtdeSaiOut(qtdeSaiOut*(-1));
				saldos.setVrSaiOut(vrSaiOut);

				
				Double qtdeEntNov = 0.0;
				Double vrEntNov   = 0.0;
				Double qtdeSaiNov = 0.0;
				Double vrSaiNov   = 0.0;
                if(mpEntNov.get(lista.getCodItem()) != null) {               	                	
                	qtdeEntNov = mpEntNov.get(lista.getCodItem()).getVlTotQtde();
                	vrEntNov   = mpEntNov.get(lista.getCodItem()).getVlTotItem();
                	
                }                
                if(mpSaiNov.get(lista.getCodItem()) != null) {                	                
                	qtdeSaiNov = mpSaiNov.get(lista.getCodItem()).getVlTotQtde();
                	vrSaiNov   = mpSaiNov.get(lista.getCodItem()).getVlTotItem();                	
                }
				saldos.setQtdeEntNov(qtdeEntNov);
				saldos.setVrEntNov(vrEntNov);
				saldos.setQtdeSaiNov(qtdeSaiNov*(-1));
				saldos.setVrSaiNov(vrSaiNov);

				Double qtdeEntDez = 0.0;
				Double vrEntDez   = 0.0;
				Double qtdeSaiDez = 0.0;
				Double vrSaiDez   = 0.0;
                if(mpEntDez.get(lista.getCodItem()) != null) {               	                	
                	qtdeEntDez = mpEntDez.get(lista.getCodItem()).getVlTotQtde();
                	vrEntDez   = mpEntDez.get(lista.getCodItem()).getVlTotItem();
                	
                }                
                if(mpSaiDez.get(lista.getCodItem()) != null) {                	                
                	qtdeSaiDez = mpSaiDez.get(lista.getCodItem()).getVlTotQtde();
                	vrSaiDez   = mpSaiDez.get(lista.getCodItem()).getVlTotItem();                	
                }
				saldos.setQtdeEntDez(qtdeEntDez);
				saldos.setVrEntDez(vrEntDez);
				saldos.setQtdeSaiDez(qtdeSaiDez*(-1));
				saldos.setVrSaiDez(vrSaiDez);

				saldos.setCodItem(lista.getCodItem());
				saldos.setCodAntItem("");
				saldos.setDescricao(lista.getDescricao());
				saldos.setUnidMedida(lista.getUnidadeDeMedidaPadrao());	
				
				linha = formatacaoPlanilha(saldos);
				writer.write(linha);
				writer.newLine();
				
			}
			writer.close();

			System.out.println("Exportado com Sucesso!!!");
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private String formatacaoPlanilha(TotalizadoresDosSaldosMensais totaisMensais) {
		String linha = "";
		// System.out.println(totaisMensais.getCodItem()+"|"+totaisMensais.getCodAntItem());

		Double saldoInvIni = 0.0;
		Double saldoInvDec = 0.0;
		Double saldoJan = 0.0;
		Double saldoFev = 0.0;
		Double saldoMar = 0.0;
		Double saldoAbr = 0.0;
		Double saldoMai = 0.0;
		Double saldoJun = 0.0;
		Double saldoJul = 0.0;
		Double saldoAgo = 0.0;
		Double saldoSet = 0.0;
		Double saldoOut = 0.0;
		Double saldoNov = 0.0;
		Double saldoDez = 0.0;

		if(totaisMensais.getQteIniInv() != null) {
			saldoInvIni = totaisMensais.getQteIniInv() ;
		}else{
			saldoInvIni = 0.0;
		}
		
		if(totaisMensais.getQteInvDec() != null) {
			saldoInvDec = totaisMensais.getQteInvDec();
		}else{
			saldoInvDec = 0.0;
		}
		
		saldoJan = saldoInvIni + totaisMensais.getQtdeEntJan() - totaisMensais.getQtdeSaiJan();
		saldoFev = saldoJan + totaisMensais.getQtdeEntFev() - totaisMensais.getQtdeSaiFev();
		saldoMar = saldoFev + totaisMensais.getQtdeEntMar() - totaisMensais.getQtdeSaiMar();
		saldoAbr = saldoMar + totaisMensais.getQtdeEntAbr() - totaisMensais.getQtdeSaiAbr();
		saldoMai = saldoAbr + totaisMensais.getQtdeEntMai() - totaisMensais.getQtdeSaiMai();
		saldoJun = saldoMai + totaisMensais.getQtdeEntJun() - totaisMensais.getQtdeSaiJun();
		saldoJul = saldoJun + totaisMensais.getQtdeEntJul() - totaisMensais.getQtdeSaiJul();
		saldoAgo = saldoJul + totaisMensais.getQtdeEntAgo() - totaisMensais.getQtdeSaiAgo();
		saldoSet = saldoAgo + totaisMensais.getQtdeEntSet() - totaisMensais.getQtdeSaiSet();
		saldoOut = saldoSet + totaisMensais.getQtdeEntOut() - totaisMensais.getQtdeSaiOut();

		saldoNov = saldoOut + totaisMensais.getQtdeEntNov() - totaisMensais.getQtdeSaiNov();
		saldoDez = saldoNov + totaisMensais.getQtdeEntDez() - totaisMensais.getQtdeSaiDez();

		String invIni = String.format("%.2f", saldoInvIni); // Implementar depois
		String invDec = String.format("%.2f", saldoInvDec);

		String qteEntJan = String.format("%.2f", totaisMensais.getQtdeEntJan());
		String vlEntJan = String.format("%.2f", totaisMensais.getVrEntJan());
		String qteSaiJan = String.format("%.2f", totaisMensais.getQtdeSaiJan());
		String vlSaiJan = String.format("%.2f", totaisMensais.getVrSaiJan());
		String saldJan = String.format("%.2f", saldoJan);

		String EntQtdeFev = String.format("%.2f", totaisMensais.getQtdeEntFev());
		String EntVlFev = String.format("%.2f", totaisMensais.getVrEntFev());
		String SaiQtdeFev = String.format("%.2f", totaisMensais.getQtdeSaiFev());
		String SaiVlFev = String.format("%.2f", totaisMensais.getVrSaiFev());
		String saldFev = String.format("%.2f", saldoFev);

		String EntQtdeMar = String.format("%.2f", totaisMensais.getQtdeEntMar());
		String EntVlMar = String.format("%.2f", totaisMensais.getVrEntMar());
		String SaiQtdeMar = String.format("%.2f", totaisMensais.getQtdeSaiMar());
		String SaiVlMar = String.format("%.2f", totaisMensais.getVrSaiMar());
		String saldMar = String.format("%.2f", saldoMar);

		String EntQtdeAbr = String.format("%.2f", totaisMensais.getQtdeEntAbr());
		String EntVlAbr = String.format("%.2f", totaisMensais.getVrEntAbr());
		String SaiQtdeAbr = String.format("%.2f", totaisMensais.getQtdeSaiAbr());
		String SaiVLAbr = String.format("%.2f", totaisMensais.getVrSaiAbr());
		String saldAbr = String.format("%.2f", saldoAbr);

		String EntQtdeMai = String.format("%.2f", totaisMensais.getQtdeEntMai());
		String EntVlMai = String.format("%.2f", totaisMensais.getVrEntMai());
		String SaiQtdeMai = String.format("%.2f", totaisMensais.getQtdeSaiMai());
		String SaiVlMai = String.format("%.2f", totaisMensais.getVrSaiMai());
		String saldMai = String.format("%.2f", saldoMai);

		String EntQtdeJun = String.format("%.2f", totaisMensais.getQtdeEntJun());
		String EntVlJun = String.format("%.2f", totaisMensais.getVrEntJun());
		String SaiQtdeJun = String.format("%.2f", totaisMensais.getQtdeSaiJun());
		String SaiVlJun = String.format("%.2f", totaisMensais.getVrSaiJun());
		String saldJun = String.format("%.2f", saldoJun);

		String EntQtdeJul = String.format("%.2f", totaisMensais.getQtdeEntJul());
		String EntVlJul = String.format("%.2f", totaisMensais.getVrEntJul());
		String SaiQtdeJul = String.format("%.2f", totaisMensais.getQtdeSaiJul());
		String SaiVlJul = String.format("%.2f", totaisMensais.getVrSaiJul());
		String saldJul = String.format("%.2f", saldoJul);

		String EntQtdeAgo = String.format("%.2f", totaisMensais.getQtdeEntAgo());
		String EntVlAgo = String.format("%.2f", totaisMensais.getVrEntAgo());
		String SaiQtdeAgo = String.format("%.2f", totaisMensais.getQtdeSaiAgo());
		String SaiVlAgo = String.format("%.2f", totaisMensais.getVrSaiAgo());
		String saldAgo = String.format("%.2f", saldoAgo);

		String EntQtdeSet = String.format("%.2f", totaisMensais.getQtdeEntSet());
		String EntVlSet = String.format("%.2f", totaisMensais.getVrEntSet());
		String SaiQtdeSet = String.format("%.2f", totaisMensais.getQtdeSaiSet());
		String SaiVlSet = String.format("%.2f", totaisMensais.getVrSaiSet());
		String saldSet = String.format("%.2f", saldoSet);

		String EntQtdeOut = String.format("%.2f", totaisMensais.getQtdeEntOut());
		String EntVlOut = String.format("%.2f", totaisMensais.getVrEntOut());
		String SaiQtdeOut = String.format("%.2f", totaisMensais.getQtdeSaiOut());
		String SaiVlOut = String.format("%.2f", totaisMensais.getVrSaiOut());
		String saldOut = String.format("%.2f", saldoOut);

		String EntQtdeNov = String.format("%.2f", totaisMensais.getQtdeEntNov());
		String EntVlNov = String.format("%.2f", totaisMensais.getVrEntNov());
		String SaiQtdeNov = String.format("%.2f", totaisMensais.getQtdeSaiNov());
		String SaiVlNov = String.format("%.2f", totaisMensais.getVrSaiNov());
		String saldNov = String.format("%.2f", saldoNov);

		String EntQtdeDez = String.format("%.2f", totaisMensais.getQtdeEntDez());
		String EntVlDez = String.format("%.2f", totaisMensais.getVrEntDez());
		String SaiQtdeDez = String.format("%.2f", totaisMensais.getQtdeSaiDez());
		String SaiVlDez = String.format("%.2f", totaisMensais.getVrSaiDez());
		String saldDez = String.format("%.2f", saldoDez);

		linha = totaisMensais.getCodItem();
		linha += ";";
		linha += totaisMensais.getCodAntItem();
		linha += ";";
		linha += totaisMensais.getDescricao();
		linha += ";";
		linha += invIni.replace(".", ",");
		linha += ";";
		linha += invDec.replace(".", ",");
		linha += ";";

		linha += qteEntJan.replace(".", ",");
		linha += ";";
		linha += vlEntJan.replace(".", ",");
		linha += ";";
		linha += qteSaiJan.replace(".", ",");
		linha += ";";
		linha += vlSaiJan.replace(".", ",");
		linha += ";";
		linha += saldJan.replace(".", ",");

		linha += ";";
		linha += EntQtdeFev.replace(".", ",");
		linha += ";";
		linha += EntVlFev.replace(".", ",");
		linha += ";";
		linha += SaiQtdeFev.replace(".", ",");
		linha += ";";
		linha += SaiVlFev.replace(".", ",");
		linha += ";";
		linha += saldFev.replace(".", ",");

		linha += ";";
		linha += EntQtdeMar.replace(".", ",");
		linha += ";";
		linha += EntVlMar.replace(".", ",");
		linha += ";";
		linha += SaiQtdeMar.replace(".", ",");
		linha += ";";
		linha += SaiVlMar.replace(".", ",");
		linha += ";";
		linha += saldMar.replace(".", ",");

		linha += ";";
		linha += EntQtdeAbr.replace(".", ",");
		linha += ";";
		linha += EntVlAbr.replace(".", ",");
		linha += ";";
		linha += SaiQtdeAbr.replace(".", ",");
		linha += ";";
		linha += SaiVLAbr.replace(".", ",");
		linha += ";";
		linha += saldAbr.replace(".", ",");

		linha += ";";
		linha += EntQtdeMai.replace(".", ",");
		linha += ";";
		linha += EntVlMai.replace(".", ",");
		linha += ";";
		linha += SaiQtdeMai.replace(".", ",");
		linha += ";";
		linha += SaiVlMai.replace(".", ",");
		linha += ";";
		linha += saldMai.replace(".", ",");

		linha += ";";
		linha += EntQtdeJun.replace(".", ",");
		linha += ";";
		linha += EntVlJun.replace(".", ",");
		linha += ";";
		linha += SaiQtdeJun.replace(".", ",");
		linha += ";";
		linha += SaiVlJun.replace(".", ",");
		linha += ";";
		linha += saldJun.replace(".", ",");

		linha += ";";
		linha += EntQtdeJul.replace(".", ",");
		linha += ";";
		linha += EntVlJul.replace(".", ",");
		linha += ";";
		linha += SaiQtdeJul.replace(".", ",");
		linha += ";";
		linha += SaiVlJul.replace(".", ",");
		linha += ";";
		linha += saldJul.replace(".", ",");

		linha += ";";
		linha += EntQtdeAgo.replace(".", ",");
		linha += ";";
		linha += EntVlAgo.replace(".", ",");
		linha += ";";
		linha += SaiQtdeAgo.replace(".", ",");
		linha += ";";
		linha += SaiVlAgo.replace(".", ",");
		linha += ";";
		linha += saldAgo.replace(".", ",");

		linha += ";";
		linha += EntQtdeSet.replace(".", ",");
		linha += ";";
		linha += EntVlSet.replace(".", ",");
		linha += ";";
		linha += SaiQtdeSet.replace(".", ",");
		linha += ";";
		linha += SaiVlSet.replace(".", ",");
		linha += ";";
		linha += saldSet.replace(".", ",");

		linha += ";";
		linha += EntQtdeOut.replace(".", ",");
		linha += ";";
		linha += EntVlOut.replace(".", ",");
		linha += ";";
		linha += SaiQtdeOut.replace(".", ",");
		linha += ";";
		linha += SaiVlOut.replace(".", ",");
		linha += ";";
		linha += saldOut.replace(".", ",");

		linha += ";";
		linha += EntQtdeNov.replace(".", ",");
		linha += ";";
		linha += EntVlNov.replace(".", ",");
		linha += ";";
		linha += SaiQtdeNov.replace(".", ",");
		linha += ";";
		linha += SaiVlNov.replace(".", ",");
		linha += ";";
		linha += saldNov.replace(".", ",");

		linha += ";";
		linha += EntQtdeDez.replace(".", ",");
		linha += ";";
		linha += EntVlDez.replace(".", ",");
		linha += ";";
		linha += SaiQtdeDez.replace(".", ",");
		linha += ";";
		linha += SaiVlDez.replace(".", ",");
		linha += ";";
		linha += saldDez.replace(".", ",");

		return linha;
	}

	private String cabecalho() {
		String linha;
		linha = "CÓDIGO ITEM";
		linha += ";";
		linha += "CÓDIGO ANTERIOR ITEM";
		linha += ";";
		linha += "DESCRIÇÃO";
		linha += ";";
		linha += "QTDE INV INICIAL/APURADO";
		linha += ";";
		linha += "QTDE INV DECLARADO";
		linha += ";";

		linha += "QTDE_ENTRADA_JAN";
		linha += ";";
		linha += "VALOR_ENTRADA_JAN";
		linha += ";";
		linha += "QTDE_SAIDA_JAN";
		linha += ";";
		linha += "VALOR_SAIDA_JAN";
		linha += ";";
		linha += "SALDO_QTDE_JAN";

		linha += ";";
		linha += "QTDE_ENTRADA_FEV";
		linha += ";";
		linha += "VALOR_ENTRADA_FEV";
		linha += ";";
		linha += "QTDE_SAIDA_FEV";
		linha += ";";
		linha += "VALOR_SAIDA_FEV";
		linha += ";";
		linha += "SALDO_QTDE_FEV";

		linha += ";";
		linha += "QTDE_ENTRADA_MAR";
		linha += ";";
		linha += "VALOR_ENTRADA_MAR";
		linha += ";";
		linha += "QTDE_SAIDA_MAR";
		linha += ";";
		linha += "VALOR_SAIDA_MAR";
		linha += ";";
		linha += "SALDO_QTDE_MAR";

		linha += ";";
		linha += "QTDE_ENTRADA_ABR";
		linha += ";";
		linha += "VALOR_ENTRADA_ABR";
		linha += ";";
		linha += "QTDE_SAIDA_ABR";
		linha += ";";
		linha += "VALOR_SAIDA_ABR";
		linha += ";";
		linha += "SALDO_QTDE_ABR";

		linha += ";";
		linha += "QTDE_ENTRADA_MAI";
		linha += ";";
		linha += "VALOR_ENTRADA_MAI";
		linha += ";";
		linha += "QTDE_SAIDA_MAI";
		linha += ";";
		linha += "VALOR_SAIDA_MAI";
		linha += ";";
		linha += "SALDO_QTDE_MAI";

		linha += ";";
		linha += "QTDE_ENTRADA_JUN";
		linha += ";";
		linha += "VALOR_ENTRADA_JUN";
		linha += ";";
		linha += "QTDE_SAIDA_JUN";
		linha += ";";
		linha += "VALOR_SAIDA_JUN";
		linha += ";";
		linha += "SALDO_QTDE_JUN";

		linha += ";";
		linha += "QTDE_ENTRADA_JUL";
		linha += ";";
		linha += "VALOR_ENTRADA_JUL";
		linha += ";";
		linha += "QTDE_SAIDA_JUL";
		linha += ";";
		linha += "VALOR_SAIDA_JUL";
		linha += ";";
		linha += "SALDO_QTDE_JUL";

		linha += ";";
		linha += "QTDE_ENTRADA_AGO";
		linha += ";";
		linha += "VALOR_ENTRADA_AGO";
		linha += ";";
		linha += "QTDE_SAIDA_AGO";
		linha += ";";
		linha += "VALOR_SAIDA_AGO";
		linha += ";";
		linha += "SALDO_QTDE_AGO";

		linha += ";";
		linha += "QTDE_ENTRADA_SET";
		linha += ";";
		linha += "VALOR_ENTRADA_SET";
		linha += ";";
		linha += "QTDE_SAIDA_SET";
		linha += ";";
		linha += "VALOR_SAIDA_SET";
		linha += ";";
		linha += "SALDO_QTDE_SET";

		linha += ";";
		linha += "QTDE_ENTRADA_OUT";
		linha += ";";
		linha += "VALOR_ENTRADA_OUT";
		linha += ";";
		linha += "QTDE_SAIDA_OUT";
		linha += ";";
		linha += "VALOR_SAIDA_OUT";
		linha += ";";
		linha += "SALDO_QTDE_OUT";

		linha += ";";
		linha += "QTDE_ENTRADA_NOV";
		linha += ";";
		linha += "VALOR_ENTRADA_NOV";
		linha += ";";
		linha += "QTDE_SAIDA_NOV";
		linha += ";";
		linha += "VALOR_SAIDA_NOV";
		linha += ";";
		linha += "SALDO_QTDE_NOV";

		linha += ";";
		linha += "QTDE_ENTRADA_DEZ";
		linha += ";";
		linha += "VALOR_ENTRADA_DEZ";
		linha += ";";
		linha += "QTDE_SAIDA_DEZ";
		linha += ";";
		linha += "VALOR_SAIDA_DEZ";
		linha += ";";
		linha += "SALDO_QTDE_DEZ";
		return linha;
	}

}
