package com.zeta.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.zeta.dao.ItemTotalizadoPorLoteDao;
import com.zeta.dao.ProdutoDao;
import com.zeta.model.ItemTotalizadoPorLote;
import com.zeta.model.Produto;
import com.zeta.model.TotalizadoresDosSaldosMensais;

public class ExportaQuantitativoEstoque {

	public void exportaControleQuantitativos(String file, String cnpj, String ano) {

		ProdutoDao daoProd = new ProdutoDao();
		ItemTotalizadoPorLoteDao dao = new ItemTotalizadoPorLoteDao();
		List<ItemTotalizadoPorLote> listaProdutos = dao.listaTodos().stream().filter(cgc -> cgc.getCnpj().equals(cnpj))
				.filter(year -> year.getAno().equals(ano)).distinct().collect(Collectors.toList());

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));
			String linha = " ";

			linha = cabecalho();

			writer.write(linha);
			writer.newLine();

			for (ItemTotalizadoPorLote lista : listaProdutos) {
				TotalizadoresDosSaldosMensais saldos = new TotalizadoresDosSaldosMensais();

				
				Double qtdeEntJan = 0.0;
				Double vlEntJan   = 0.0;
				Double qtdeSaiJan = 0.0;				
				Double vlSaiJan   = 0.0;
                if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "1", "E") != null) {
                	qtdeEntJan = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "1", "E").getVlTotQtde();
    				vlEntJan   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "1", "E").getVlTotItem();
                }
                if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "1", "S") != null) {
                	 qtdeSaiJan = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "1", "S").getVlTotQtde();				
    			     vlSaiJan   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "1", "S").getVlTotItem();
                }
				
				saldos.setQtdeEntJan(qtdeEntJan);
				saldos.setVrEntJan(vlEntJan);
				saldos.setQtdeSaiJan(qtdeSaiJan);
				saldos.setVrSaiJan(vlSaiJan);
				

				Double qtdeEntFev = 0.0;		
				Double vlEntFev   = 0.0;
				Double qtdeSaiFev = 0.0;
				Double vlSaiFev   = 0.0;

				if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "2", "E") != null) {
					   qtdeEntFev = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "2", "E").getVlTotQtde();		
					   vlEntFev   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "2", "E").getVlTotItem();
				}
				
				if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "2", "S") != null) {
			           qtdeSaiFev = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "2", "S").getVlTotQtde();
				       vlSaiFev   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "2", "S").getVlTotItem();
				}
				
				saldos.setQtdeEntFev(qtdeEntFev);
				saldos.setVrEntFev(vlEntFev);
				saldos.setQtdeSaiFev(qtdeSaiFev);
				saldos.setVrSaiFev(vlSaiFev);

				Double qtdeEntMar = 0.0;	
				Double vrEntMar   = 0.0;	
				Double qtdeSaiMar = 0.0;	
				Double vrSaiMar   = 0.0;	

				if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "3", "E") != null) {
					   qtdeEntMar = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "3", "E").getVlTotQtde();	
					   vrEntMar   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "3", "E").getVlTotItem();	
				}
				
				if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "3", "S") != null) {
					   qtdeSaiMar = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "3", "S").getVlTotQtde();	
					   vrSaiMar   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "3", "S").getVlTotItem();	
				}
				saldos.setQtdeEntMar(qtdeEntMar);
				saldos.setVrEntMar(vrEntMar);
				saldos.setQtdeSaiMar(qtdeSaiMar);
				saldos.setVrSaiMar(vrSaiMar);

				Double qtdeEntAbr = 0.0;	
				Double vrEntAbr   = 0.0;	
				Double qtdeSaiAbr = 0.0;	
				Double vrSaiAbr   = 0.0;	
				
				if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "4", "E") != null){
				       qtdeEntAbr = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "4", "E").getVlTotQtde();	
			           vrEntAbr   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "4", "E").getVlTotItem();	
				}

				if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "4", "S") != null) {
					   qtdeSaiAbr = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "4", "S").getVlTotQtde();	
					   vrSaiAbr   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "4", "S").getVlTotItem();	
				}
				saldos.setQtdeEntAbr(qtdeEntAbr);
				saldos.setVrEntAbr(vrEntAbr);
				saldos.setQtdeSaiAbr(qtdeSaiAbr);
				saldos.setVrSaiAbr(vrSaiAbr);

				Double qtdeEntMai = 0.0;	
				Double vrEntMai   = 0.0;	
				Double qtdeSaiMai = 0.0;	
				Double vrSaiMai   = 0.0;	

				if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "5", "E") != null) {
					       qtdeEntMai = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "5", "E").getVlTotQtde();	
					       vrEntMai   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "5", "E").getVlTotItem();	
				}
				
				if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "5", "S") != null) {
					       qtdeSaiMai = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "5", "S").getVlTotQtde();	
					       vrSaiMai   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "5", "S").getVlTotItem();	
				}
				saldos.setQtdeEntMai(qtdeEntMai);
				saldos.setVrEntMai(vrEntMai);
				saldos.setQtdeSaiMai(qtdeSaiMai);
				saldos.setVrSaiMai(vrSaiMai);

				Double qtdeEntJun = 0.0;	
				Double vrEntJun   = 0.0;
				Double qtdeSaiJun = 0.0;
				Double vrSaiJun   = 0.0;
                if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "6", "E") != null) {
                	 qtdeEntJun = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "6", "E").getVlTotQtde();	
    				 vrEntJun   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "6", "E").getVlTotItem();
                }
                if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "6", "S") != null) {
                	 qtdeSaiJun = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "6", "S").getVlTotQtde();
    				 vrSaiJun   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "6", "S").getVlTotItem();
                }
				saldos.setQtdeEntJun(qtdeEntJun);
				saldos.setVrEntJun(vrEntJun);
				saldos.setQtdeSaiJun(qtdeSaiJun);
				saldos.setVrSaiJun(vrSaiJun);

				Double qtdeEntJul =  0.0;	
				Double vrEntJul   =  0.0;	
				Double qtdeSaiJul =  0.0;	
				Double vrSaiJul   =  0.0;	
                if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "7", "E") != null) {
                           qtdeEntJul =  dao.buscarTotalizadorComFiltros(lista.getCodItem(), "7", "E").getVlTotQtde();	
    				       vrEntJul   =  dao.buscarTotalizadorComFiltros(lista.getCodItem(), "7", "E").getVlTotItem();	
                }
                if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "7", "S") != null) {
                	       qtdeSaiJul =  dao.buscarTotalizadorComFiltros(lista.getCodItem(), "7", "S").getVlTotQtde();	
    			           vrSaiJul   =  dao.buscarTotalizadorComFiltros(lista.getCodItem(), "7", "S").getVlTotItem();	
                }
				saldos.setQtdeEntJul(qtdeEntJul);
				saldos.setVrEntJul(vrEntJul);
				saldos.setQtdeSaiJul(qtdeSaiJul);
				saldos.setVrSaiJul(vrSaiJul);

				Double qtdeEntAgo = 0.0;	
				Double vrEntAgo   = 0.0;	
				Double qtdeSaiAgo = 0.0;	
				Double vrSaiAgo   = 0.0;	
                if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "8", "E") != null) {
                	   qtdeEntAgo = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "8", "E").getVlTotQtde();	
    				   vrEntAgo   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "8", "E").getVlTotItem();	
                }
                if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "8", "S") != null) {
                           qtdeSaiAgo = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "8", "S").getVlTotQtde();	
    				       vrSaiAgo   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "8", "S").getVlTotItem();	
                }
				saldos.setQtdeEntAgo(qtdeEntAgo);
				saldos.setVrEntAgo(vrEntAgo);
				saldos.setQtdeSaiAgo(qtdeSaiAgo);
				saldos.setVrSaiAgo(vrSaiAgo);

				Double qtdeEntSet = 0.0;
				Double vrEntSet   = 0.0;
				Double qtdeSaiSet = 0.0;
				Double vrSaiSet   = 0.0;
                if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "9", "E") != null) {
                	   qtdeEntSet = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "9", "E").getVlTotQtde();
    				   vrEntSet   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "9", "E").getVlTotItem();
                }
                if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "9", "S") != null) {
                	   qtdeSaiSet = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "9", "S").getVlTotQtde();
    				   vrSaiSet   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "9", "S").getVlTotItem();
                }
				saldos.setQtdeEntSet(qtdeEntSet);
				saldos.setVrEntSet(vrEntSet);
				saldos.setQtdeSaiSet(qtdeSaiSet);
				saldos.setVrSaiSet(vrSaiSet);

				Double qtdeEntOut = 0.0;
				Double vrEntOut   = 0.0;
				Double qtdeSaiOut = 0.0;
				Double vrSaiOut   = 0.0;
                if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "10", "E") != null) {
                	   qtdeEntOut = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "10", "E").getVlTotQtde();
    				   vrEntOut   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "10", "E").getVlTotItem();
                }
                if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "10", "S") != null) {
                	   qtdeSaiOut = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "10", "S").getVlTotQtde();
    				   vrSaiOut   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "10", "S").getVlTotItem();
                }
				saldos.setQtdeEntOut(qtdeEntOut);
				saldos.setVrEntOut(vrEntOut);
				saldos.setQtdeSaiOut(qtdeSaiOut);
				saldos.setVrSaiOut(vrSaiOut);

				Double qtdeEntNov = 0.0;
				Double vrEntNov   = 0.0;
				Double qtdeSaiNov = 0.0;
				Double vrSaiNov   = 0.0;
                if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "11", "E") != null) {
                       qtdeEntNov = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "11", "E").getVlTotQtde();
    			       vrEntNov   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "11", "E").getVlTotItem();
                }
                if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "11", "S") != null) {
                	  qtdeSaiNov = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "11", "S").getVlTotQtde();
    				  vrSaiNov   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "11", "S").getVlTotItem();
                }
				saldos.setQtdeEntNov(qtdeEntNov);
				saldos.setVrEntNov(vrEntNov);
				saldos.setQtdeSaiNov(qtdeSaiNov);
				saldos.setVrSaiNov(vrSaiNov);

				Double qtdeEntDez = 0.0;
				Double vrEntDez   = 0.0;
				Double qtdeSaiDez = 0.0;
				Double vrSaiDez   = 0.0;
                if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "12", "E") != null) {
                	   qtdeEntDez = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "12", "E").getVlTotQtde();
    				   vrEntDez   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "12", "E").getVlTotItem();
                }
                if(dao.buscarTotalizadorComFiltros(lista.getCodItem(), "12", "S") != null) {
                	   qtdeSaiDez = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "12", "S").getVlTotQtde();
    				   vrSaiDez   = dao.buscarTotalizadorComFiltros(lista.getCodItem(), "12", "S").getVlTotItem();
                }
				saldos.setQtdeEntDez(qtdeEntDez);
				saldos.setVrEntDez(vrEntDez);
				saldos.setQtdeSaiDez(qtdeSaiDez);
				saldos.setVrSaiDez(vrSaiDez);

				Produto prod = daoProd.buscaPorCodigo(lista.getCodItem());
				saldos.setCodItem(lista.getCodItem());
				saldos.setCodAntItem("");
				saldos.setDescricao(prod.getDescricao());
				saldos.setUnidMedida(prod.getUnidadedeMedidaPadrao());
				linha = formatacaoPlanilha(saldos);
				writer.write(linha);
				writer.newLine();
				
				
//					if (!formatacaoPlanilha(saldos).contains(";0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00")) {
//
//						//System.out.println("linha " + formatacaoPlanilha(saldos));
//						
//					}
				
				

			}
			writer.close();

			System.out.println("Exportado com Sucesso!!!");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private String formatacaoPlanilha(TotalizadoresDosSaldosMensais totaisMensais) {
		String linha = "";
		// System.out.println(totaisMensais.getCodItem()+"|"+totaisMensais.getCodAntItem());

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

		saldoJan = totaisMensais.getQteIniInv() + totaisMensais.getQtdeEntJan() - totaisMensais.getQtdeSaiJan();

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

		String invIni = String.format("%.2f", totaisMensais.getQteIniInv()); // Implementar depois
		String invDec = String.format("%.2f", totaisMensais.getQteInvDec());

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
