package com.zeta.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.zeta.dao.InventarioDeclaradoDao;
import com.zeta.dao.ItemTotalizadoPorLoteDao;
import com.zeta.dao.ItensInventarioDao;
import com.zeta.dao.ProdutoDao;
import com.zeta.model.CadastroItensPorMovimentacao;
import com.zeta.model.FichaFinanceiroPorItens;
import com.zeta.model.InventarioDeclarado;
import com.zeta.model.InventarioDeclaradoSped;
import com.zeta.model.ItemTotalizadoPorLote;
import com.zeta.model.ItemTotalizadoPorLoteJoinProduto;
import com.zeta.model.ItensInventario;
import com.zeta.util.UtilsEConverters;

public class ExportaTotalizadorFinanceiroAnual {
	
	private Double qtdeMDV = 0.00;
	private Double custoMedioMDV = 0.00;
	private Double custoTotMDV = 0.00;

	private Double qtdeS_EF = 0.00;
	private Double precoMedio = 0.00;
	private Double precoTotal = 0.00;

	private Double qtde_OC = 0.00;
	private Double custoUnit_OC = 0.00;
	private Double vrTotal_OC = 0.00;
	
	private Double qtde_OV = 0.00;
	private Double custoUnit_OV = 0.00;
	private Double vrTotal_OV = 0.00;
	
	private ItemTotalizadoPorLoteDao dao = new ItemTotalizadoPorLoteDao();
	
	public List<CadastroItensPorMovimentacao> listaDeProdutos(ItemTotalizadoPorLoteDao dao1,String ano, String cnpj, Long idEmp, Long idEst){
		
		
		List<ItemTotalizadoPorLoteJoinProduto> lista = dao.buscaListaItensPorAnoJoinProduto(cnpj);
		
		
		ProdutoDao prodDao = new ProdutoDao();
		List<CadastroItensPorMovimentacao> retorno = new ArrayList<CadastroItensPorMovimentacao>();
 		Set<String> codigos = new HashSet<String>();
		InventarioDeclaradoDao invDec = new InventarioDeclaradoDao();
		ItensInventarioDao itemInv = new ItensInventarioDao();
		List<InventarioDeclarado> inventarios = invDec.buscaPorAnoEmpresaEstab(Integer.valueOf(ano), idEmp, idEst);
		List<ItemTotalizadoPorLoteJoinProduto> listaProdutos = lista.stream()
				.filter(cgc -> cgc.getCnpj().equals(cnpj))
				.filter(year -> year.getAno().equals(String.valueOf(ano)))
				.distinct().collect(Collectors.toList());
		
		for(InventarioDeclarado busca  : inventarios){
			if(busca.getVlTotal() > 0) {
				for(ItensInventario item : itemInv.buscaPorIdPai(busca.getId())){					
					codigos.add(UtilsEConverters.getRemoverZeroAEsquerda(item.getCodItem()));
				}
			}
		}
		
		for(ItemTotalizadoPorLoteJoinProduto itens : listaProdutos){
			codigos.add(itens.getCodItem());
		}
		
		for(String codigo : codigos){
			CadastroItensPorMovimentacao obj = new CadastroItensPorMovimentacao();			
			obj.setCodItem(codigo);
			obj.setDescricao(prodDao.buscaPorCodigo(codigo).getDescricao());
			obj.setUndMed(prodDao.buscaPorCodigo(codigo).getUnidadedeMedidaPadrao());
			retorno.add(obj);
		}
		return retorno;
	}
	
	public List<InventarioDeclaradoSped> listaInventario (String cnpj,Integer ano){
		
		List<InventarioDeclaradoSped> retorno = dao.buscarInvDecSped(cnpj, ano);
		
		return retorno;
	}
	

	public static List<FichaFinanceiroPorItens> leituraTotalizadorFinanceiro(Path path) {
		List<FichaFinanceiroPorItens> retorno = new ArrayList<FichaFinanceiroPorItens>();
		try {
			List<String> lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1).stream().skip(1).collect(Collectors.toList());
           
			for (int i = 0; i < lines.size(); i++) {
				
				String[] campos = lines.get(i).split("\\;");
				FichaFinanceiroPorItens obj = new FichaFinanceiroPorItens();
				for (int c = 0; c < campos.length; c++) {					
					
					if (c == 0) {						
						obj.setCodItem(campos[c]);
						
					}
					if (c == 1) {						
						obj.setCodAntItem(campos[c]);
						
					}
					if (c == 2) {	
						obj.setDescricao(campos[c]);
					}
					if (c == 3) {	
						obj.setUnd(campos[c]);
					}
					if (c == 4) {	
						obj.setStatus(campos[c]);
					}
					if (c == 5) {
						if(!campos[c].isEmpty()) {
							obj.setQtdeEi(Double.valueOf(campos[c].replace(",", ".")));
							
						}
					}
					if (c == 6) {
						if(!campos[c].isEmpty()) {
							obj.setVrUnitEi(Double.valueOf(campos[c].replace(",", ".")));
						}
					}
					if (c == 7) {
						if(!campos[c].isEmpty()) {
							obj.setVrItemEi(Double.valueOf(campos[c].replace(",", ".")));
						}
					}
					
					
					if (c == 9) {
						if(!campos[c].isEmpty()) {
							obj.setQtdeEnt(Double.valueOf(campos[c].replace(",", ".")));
						}
					}
					if (c == 10) {
						if(!campos[c].isEmpty()) {
							obj.setVlUnitEnt(Double.valueOf(campos[c].replace(",", ".")));
						}
					}
					if (c == 11) {
						if(!campos[c].isEmpty()) {
							obj.setVlItemEnt(Double.valueOf(campos[c].replace(",", ".")));
						}
					}
					
					if (c == 13) {
						if(!campos[c].isEmpty()) {
							obj.setQtdeSai(Double.valueOf(campos[c].replace(",", ".")));
						}
					}
					if (c == 14) {
						if(!campos[c].isEmpty()) {
							obj.setVlUnitSai(Double.valueOf(campos[c].replace(",", ".")));
						}
					}
					if (c == 15) {
						if(!campos[c].isEmpty()) {
							obj.setVlItemSai(Double.valueOf(campos[c].replace(",", ".")));
						}
					}
					
					
					if (c == 17) {
						if(!campos[c].isEmpty()) {
							obj.setQtdeEf(Double.valueOf(campos[c].replace(",", ".")));
						}
					}
					if (c == 18) {
						if(!campos[c].isEmpty()) {
							obj.setVrUnitEf(Double.valueOf(campos[c].replace(",", ".")));
						}
					}
					if (c == 19) {
						if(!campos[c].isEmpty()) {
							obj.setVrItemEf(Double.valueOf(campos[c].replace(",", ".")));
						}
					}
				}
				
				retorno.add(obj);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	public static FichaFinanceiroPorItens fichaTotalizada(Path path, String codItem, String codAntItem) {
		FichaFinanceiroPorItens ficha = new FichaFinanceiroPorItens();
		Double qtdeEi   = 0.0;
		Double vrUnitEi = 0.0;
		Double vrItemEi = 0.0;
		

		Double qtdeEnt   = 0.0;
		Double vlUnitEnt = 0.0;
		Double vlItemEnt = 0.0;
		

		Double qtdeSai   = 0.0;
		Double vlUnitSai = 0.0;
		Double vlItemSai = 0.0;
		
		Double qtdeEf   = 0.0;
		Double vrUnitEf = 0.0;
		Double vrItemEf = 0.0;
		// c.getCodItem().equals("4800") || c.getCodItem().equals("1564")
	   List<FichaFinanceiroPorItens> collect = leituraTotalizadorFinanceiro(path).stream()
	             .filter(c -> c.getCodItem().equals(codItem) || c.getCodItem().equals(codAntItem))
	             .collect(Collectors.toList());
	   

	   for(FichaFinanceiroPorItens l :   collect){
		   
//		   System.out.println(
//				   l.getCodItem() +"|"+ l.getCodAntItem() +"|"+
//				   l.getQtdeEi() +"|"+ l.getVrUnitEi() +"|"+ l.getVrItemEi() +"|"+ 
//				              l.getQtdeEnt() +"|"+ l.getVlUnitEnt() +"|"+ l.getVlItemEnt() +"|"+
//				              l.getQtdeSai() +"|"+ l.getVlUnitSai() +"|"+ l.getVlItemSai() +"|"+
//				              l.getQtdeEf() +"|"+  l.getVrUnitEf() +"|"+ l.getVrItemEf());

	    	qtdeEi    += l.getQtdeEi();
	    	vrUnitEi  += l.getVrUnitEi();
	    	vrItemEi  += l.getVrItemEi();
	    	
	    	qtdeEnt   += l.getQtdeEnt();
			vlUnitEnt += l.getVlUnitEnt();
			vlItemEnt += l.getVlItemEnt();
			
			
			qtdeSai   += l.getQtdeSai();
			vlUnitSai += l.getVlUnitSai();
			vlItemSai += l.getVlItemSai();
			
			qtdeEf   += l.getQtdeEf();
			vrUnitEf += l.getVrUnitEf();
			vrItemEf += l.getVrItemEf();
			
			
			
	    }

	    ficha.setQtdeEi(qtdeEi);
	    ficha.setVrUnitEi(vrUnitEi);
	    ficha.setVrItemEi(vrItemEi);

	    ficha.setQtdeEnt(qtdeEnt);
	    ficha.setVlUnitEnt(vlUnitEnt);
	    ficha.setVlItemEnt(vlItemEnt);
	    
	    ficha.setQtdeSai(qtdeSai);
	    ficha.setVlUnitSai(vlUnitSai);
	    ficha.setVlItemSai(vlItemSai);
	    
	    ficha.setQtdeEf(qtdeEf);
	    ficha.setVrUnitEf(vrUnitEf);
	    ficha.setVrItemEf(vrItemEf);
	   
		return ficha;
	}
	
	public List<String> linhasTotalizadorFinanceiro(Path path) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1).stream().skip(1).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	public void exportaTotalizadorFinanceiroEstoqueDaPlanilha(String caminho,Path csv1) {
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(caminho)));
		
		
			String linha = " ";

			linha = cabecalho();

			writer.write(linha);
			writer.newLine();
		
		    Map<String,String> mpLinhas = new HashMap<String,String>();
			for(String s : linhasTotalizadorFinanceiro(csv1)) {
				String cod = "";
				String codAnt = "";
				String[] campo = s.split("\\;");
				for(int i = 0; i < campo.length; i++){
					if(i == 0) {
						cod = campo[i];
					}
					if(i == 1) {
						codAnt = campo[i];
						
						mpLinhas.put(codAnt, s);
					}
				}
				   
			}
			
		    List<String> lsLinhas = new ArrayList<String>();
		    List<String> lsLinhasFF = new ArrayList<String>();
			for(String s : linhasTotalizadorFinanceiro(csv1)) {
				String cod = "";
				String[] campo = s.split("\\;");
				for(int i = 0; i < campo.length; i++){
					if(i == 0) {
						lsLinhasFF.add(campo[i]);
					}
					if(i == 1) {
						cod = campo[i];
						lsLinhas.add(campo[i]);
					}
				}
				   
			}
			
	        for(int i = 0 ; i < leituraTotalizadorFinanceiro(csv1).size(); i++){
	        	FichaFinanceiroPorItens ficha = new FichaFinanceiroPorItens();
	        	     
	        	     
	        	if(leituraTotalizadorFinanceiro(csv1).get(i).getStatus().equals("V")){
	        		
	       	         if( lsLinhas.contains(leituraTotalizadorFinanceiro(csv1).get(i).getCodItem()) != true) {
	    	        	 
	//    	        	 System.out.println(leituraTotalizadorFinanceiro(csv1).get(i).getCodItem() +"|"+
	//    	        			 leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()+"|"+
	//    		        	     lsLinhas.contains(leituraTotalizadorFinanceiro(csv1).get(i).getCodItem()));
	    	         
	                     System.out.println(leituraTotalizadorFinanceiro(csv1).get(i).getCodItem() +"|"+
	                  		   leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem() +"|"+
	                  		   fichaTotalizada(csv1, leituraTotalizadorFinanceiro(csv1).get(i).getCodItem(), leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()).getQtdeEnt());
	   
	                     
					    	    //System.out.println(leituraTotalizadorFinanceiro(csv1).get(i).getCodItem()+"|"+leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem());
					   
		                    System.out.println(fichaTotalizada(csv1, leituraTotalizadorFinanceiro(csv1).get(i).getCodItem(), leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()).getQtdeEi());
		                    ficha.setQtdeEi(fichaTotalizada(csv1, leituraTotalizadorFinanceiro(csv1).get(i).getCodItem(), leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()).getQtdeEi());                   
		                    ficha.setVrUnitEi( fichaTotalizada(csv1, leituraTotalizadorFinanceiro(csv1).get(i).getCodItem(), leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()).getVrUnitEi());
							ficha.setVrItemEi( fichaTotalizada(csv1, leituraTotalizadorFinanceiro(csv1).get(i).getCodItem(), leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()).getVrItemEi());
							System.out.println("INV INI " + ficha.getQtdeEi()+"|"+ficha.getVrUnitEi()+"|"+ficha.getVrItemEi());	
			        				
			        				
							ficha.setQtdeEnt(fichaTotalizada(csv1, leituraTotalizadorFinanceiro(csv1).get(i).getCodItem(), leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()).getQtdeEnt());
							ficha.setVlUnitEnt(fichaTotalizada(csv1, leituraTotalizadorFinanceiro(csv1).get(i).getCodItem(), leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()).getVlUnitEnt());
							ficha.setVlItemEnt(fichaTotalizada(csv1, leituraTotalizadorFinanceiro(csv1).get(i).getCodItem(), leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()).getVlItemEnt());		
			        				
			        	
							ficha.setQtdeSai(fichaTotalizada(csv1, leituraTotalizadorFinanceiro(csv1).get(i).getCodItem(), leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()).getQtdeSai());
							ficha.setVlUnitSai(fichaTotalizada(csv1, leituraTotalizadorFinanceiro(csv1).get(i).getCodItem(), leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()).getVlUnitSai());
							ficha.setVlItemSai(fichaTotalizada(csv1, leituraTotalizadorFinanceiro(csv1).get(i).getCodItem(), leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()).getVlItemSai());
	
							ficha.setQtdeEf(fichaTotalizada(csv1, leituraTotalizadorFinanceiro(csv1).get(i).getCodItem(), leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()).getQtdeEf());
							ficha.setVrUnitEf(fichaTotalizada(csv1, leituraTotalizadorFinanceiro(csv1).get(i).getCodItem(), leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()).getVrUnitEf());
							ficha.setVrItemEf(fichaTotalizada(csv1, leituraTotalizadorFinanceiro(csv1).get(i).getCodItem(), leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()).getVrItemEf());
							System.out.println("INV FIN " + ficha.getQtdeEf()+"|"+ficha.getVrUnitEf()+"|"+ficha.getVrItemEf());	
			        	   
							
							ficha.setCodItem(leituraTotalizadorFinanceiro(csv1).get(i).getCodItem());
							ficha.setCodAntItem( leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem());
					        ficha.setDescricao( leituraTotalizadorFinanceiro(csv1).get(i).getDescricao());
					        ficha.setUnd(leituraTotalizadorFinanceiro(csv1).get(i).getUnd());
	    	         
	    	         }
	       	         
	       	         
	        	}else if(!leituraTotalizadorFinanceiro(csv1).get(i).getStatus().equals("FF")){
	        		
	        		// if(lsLinhasFF.contains(leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()) != true) {}
	        			 
	                     System.out.println(leituraTotalizadorFinanceiro(csv1).get(i).getCodItem() +"|"+
		                  		   leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem() +"|"+
		                  		   leituraTotalizadorFinanceiro(csv1).get(i).getQtdeEnt());
		   
		                     
						    	    //System.out.println(leituraTotalizadorFinanceiro(csv1).get(i).getCodItem()+"|"+leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem());
						   
			                    System.out.println(leituraTotalizadorFinanceiro(csv1).get(i).getQtdeEi());
			                    ficha.setQtdeEi(leituraTotalizadorFinanceiro(csv1).get(i).getQtdeEi());                   
			                    ficha.setVrUnitEi(leituraTotalizadorFinanceiro(csv1).get(i).getVrUnitEi());
								ficha.setVrItemEi( leituraTotalizadorFinanceiro(csv1).get(i).getVrItemEi());
								System.out.println("INV INI " + ficha.getQtdeEi()+"|"+ficha.getVrUnitEi()+"|"+ficha.getVrItemEi());	
				        				
				        				
								ficha.setQtdeEnt(leituraTotalizadorFinanceiro(csv1).get(i).getQtdeEnt());
								ficha.setVlUnitEnt(leituraTotalizadorFinanceiro(csv1).get(i).getVlUnitEnt());
								ficha.setVlItemEnt(leituraTotalizadorFinanceiro(csv1).get(i).getVlItemEnt());		
				        				
				        	
								ficha.setQtdeSai(leituraTotalizadorFinanceiro(csv1).get(i).getQtdeSai());
								ficha.setVlUnitSai(leituraTotalizadorFinanceiro(csv1).get(i).getVlUnitSai());
								ficha.setVlItemSai(leituraTotalizadorFinanceiro(csv1).get(i).getVlItemSai());
		
								ficha.setQtdeEf(leituraTotalizadorFinanceiro(csv1).get(i).getQtdeEf());
								ficha.setVrUnitEf(leituraTotalizadorFinanceiro(csv1).get(i).getVrUnitEf());
								ficha.setVrItemEf(leituraTotalizadorFinanceiro(csv1).get(i).getVrItemEf());
								System.out.println("INV FIN " + ficha.getQtdeEf()+"|"+ficha.getVrUnitEf()+"|"+ficha.getVrItemEf());	
				        	   
								
								ficha.setCodItem(leituraTotalizadorFinanceiro(csv1).get(i).getCodItem());
								ficha.setCodAntItem( leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem());
						        ficha.setDescricao( leituraTotalizadorFinanceiro(csv1).get(i).getDescricao());
						        ficha.setUnd(leituraTotalizadorFinanceiro(csv1).get(i).getUnd());
	        		 
	        		 
	        	}
	        	

	        	    if(!formatacaoPlanilha(ficha).contains("null;")) {
				        linha = formatacaoPlanilha(ficha);
						
						writer.write(linha);
						writer.newLine();
	        	    }
	        	   

	        	

	        }
	        writer.close();
			System.out.println("Exportado com Sucesso!!!");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	public void exportaTotalizadorFinanceiroEstoque(String caminho, int ano, String cnpj, Long idEmp, Long idEst) {

		ItemTotalizadoPorLoteDao dao = new ItemTotalizadoPorLoteDao();
		List<ItemTotalizadoPorLote> lista = dao.listaTodos();
		List<CadastroItensPorMovimentacao> listaCadastroItensPorMovimentao = dao.buscaListaItensPorAnoJoinTotalizadorJoinInvJoinProduto(cnpj, ano);
		List<InventarioDeclaradoSped>  listaInv1 = listaInventario(cnpj, ano-1).stream().collect(Collectors.toList());
		List<InventarioDeclaradoSped>  listaInv2 = listaInventario(cnpj, ano).stream().collect(Collectors.toList());
		
		 Map<String, List<ItemTotalizadoPorLote>> listaEnt = lista.stream()
					.filter(cgc -> cgc.getCnpj().equals(cnpj))
					.filter(year -> year.getAno().equals(String.valueOf(ano)))
					.filter(oper -> oper.getOperacao().equals("E"))
					.collect(Collectors.groupingBy(codigo -> codigo.getCodItem()));

		 Map<String, List<ItemTotalizadoPorLote>> listaSai = lista.stream()
					.filter(cgc -> cgc.getCnpj().equals(cnpj))
					.filter(year -> year.getAno().equals(String.valueOf(ano)))
					.filter(oper -> oper.getOperacao().equals("S"))
					.collect(Collectors.groupingBy(codigo -> codigo.getCodItem()));


		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(caminho)));
		
			String linha = " ";

			linha = cabecalho();

			writer.write(linha);
			writer.newLine();
		
			for (CadastroItensPorMovimentacao codigo : listaCadastroItensPorMovimentao) {
				FichaFinanceiroPorItens ficha = new FichaFinanceiroPorItens();
				
								
				if(listaInv1 != null) {
					
					ficha.setQtdeEi(listaInv1.stream().filter(codItem -> codItem.getCodItem().equals(codigo.getCodItem())).mapToDouble(qtde -> qtde.getQtde()).sum());
					ficha.setVrUnitEi(listaInv1.stream().filter(codItem -> codItem.getCodItem().equals(codigo.getCodItem())).mapToDouble(vlUnit -> vlUnit.getVlUnit()).sum());
					ficha.setVrItemEi(listaInv1.stream().filter(codItem -> codItem.getCodItem().equals(codigo.getCodItem())).mapToDouble(vlItem -> vlItem.getVlItem()).sum());
					System.out.println("INV INI " + ficha.getQtdeEi()+"|"+ficha.getVrUnitEi()+"|"+ficha.getVrItemEi());
				
				
				}else {
					ficha.setQtdeEi(0.0);
					ficha.setVrUnitEi(0.0);
					ficha.setVrItemEi(0.0);
					System.out.println("INV INI " + ficha.getQtdeEi()+"|"+ficha.getVrUnitEi()+"|"+ficha.getVrItemEi());
				}
				
					
				Double qtdeEnt1  = 0.0;
				Double vlTotEnt1 = 0.0;
			    Double vlUnitEnt = 0.0;
			    if(listaEnt.get(codigo.getCodItem()) !=null) {
					for (ItemTotalizadoPorLote cod : listaEnt.get(codigo.getCodItem())) {
						qtdeEnt1 += cod.getVlTotQtde();
						vlTotEnt1 += cod.getVlTotItem();
					}
					vlUnitEnt = (vlTotEnt1 / qtdeEnt1);
					ficha.setQtdeEnt(qtdeEnt1);
					ficha.setVlUnitEnt(vlUnitEnt);
					ficha.setVlItemEnt(vlTotEnt1);
			    }		
			    
			    
				Double qtdeSai1  = 0.0;
				Double vlTotSai1 = 0.0;
				Double vlUnitSai = 0.0;
				if(listaSai.get(codigo.getCodItem()) != null){
				    for(ItemTotalizadoPorLote cod : listaSai.get(codigo.getCodItem())){	 
				    	qtdeSai1  += cod.getVlTotQtde();
				    	vlTotSai1 += cod.getVlTotItem();			 
				    }
				    vlUnitSai = (vlTotSai1/qtdeSai1);
					ficha.setQtdeSai(qtdeSai1);
					ficha.setVlUnitSai(vlUnitSai);
					ficha.setVlItemSai(vlTotSai1);
				}

				
				if(listaInv2 != null) {
					ficha.setQtdeEf(listaInv2.stream().filter(codItem -> codItem.getCodItem().equals(codigo.getCodItem())).mapToDouble(qtde -> qtde.getQtde()).sum());
					ficha.setVrUnitEf(listaInv2.stream().filter(codItem -> codItem.getCodItem().equals(codigo.getCodItem())).mapToDouble(vlUnit -> vlUnit.getVlUnit()).sum());
					ficha.setVrItemEf(listaInv2.stream().filter(codItem -> codItem.getCodItem().equals(codigo.getCodItem())).mapToDouble(vlItem -> vlItem.getVlItem()).sum());
					System.out.println("INV FIN " + ficha.getQtdeEf()+"|"+ficha.getVrUnitEf()+"|"+ficha.getVrItemEf());
				}else {
					ficha.setQtdeEi(0.0);
					ficha.setVrUnitEi(0.0);
					ficha.setVrItemEi(0.0);
					System.out.println("INV FIN " + ficha.getQtdeEi()+"|"+ficha.getVrUnitEi()+"|"+ficha.getVrItemEi());
				}
				

				ficha.setCodItem(codigo.getCodItem());
				ficha.setCodAntItem("");
		        ficha.setDescricao(codigo.getDescricao());
		        ficha.setUnd(codigo.getUndMed());

		        linha = formatacaoPlanilha(ficha);
				
				writer.write(linha);
				writer.newLine();
				
			}
		
			writer.close();
			System.out.println("Exportado com Sucesso!!!");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	private String formatacaoPlanilha(FichaFinanceiroPorItens totAnual) {
		String linha;
		


		//custoTotMDV = vrItemInicialInv + vlItemEntrada;
		  qtdeMDV = calcMDV((totAnual.getQtdeEi()==null?0.0:totAnual.getQtdeEi()), (totAnual.getQtdeEnt()==null?0.0:totAnual.getQtdeEnt()));
		
		  custoTotMDV = custoTotMDV((totAnual.getVrItemEi()==null?0.0:totAnual.getVrItemEi()),(totAnual.getVlItemEnt()==null?0.0:totAnual.getVlItemEnt()));
		
			
		  custoMedioMDV =  custoTotMDV/qtdeMDV;
		  
		//precoTotal = vrItemFinalInv + vlItemSaida;
		
		   qtdeS_EF   =  qtdeS_EF((totAnual.getQtdeEf()==null?0.0:totAnual.getQtdeEf()),(totAnual.getQtdeSai()==null?0.0:totAnual.getQtdeSai()));
		
		   precoTotal = precoTotal((totAnual.getVrItemEf()==null?0.0:totAnual.getVrItemEf()), (totAnual.getVlItemSai()==null?0.0:totAnual.getVlItemSai()));
		
		   precoMedio = precoTotal/qtdeS_EF;
		   
		//  System.out.println("Calc Ent " + qtdeMDV + "|" + custoMedioMDV + "|"  +custoTotMDV 
		//		                  + " Calc Sai " + qtdeS_EF + "|" + precoTotal + "|" + precoMedio);
		// =========== Omiss�es de Vendas
		

		if (qtdeS_EF >= qtdeMDV) {

			qtde_OC = qtdeS_EF - qtdeMDV;
			custoUnit_OC = custoMedioMDV;
			vrTotal_OC = qtde_OC * custoMedioMDV;

		} else {

			qtde_OC = 0.0;
			custoUnit_OC = 0.0;
			vrTotal_OC = 0.0;

		}

		if (qtdeMDV.equals(0.0)) {

			qtde_OC = qtdeS_EF;
			custoUnit_OC = precoMedio;
			vrTotal_OC = precoTotal;
		}

		if (qtde_OC.equals(0.0)) {

			custoUnit_OC = 0.0;
		}

		// =========== Omiss�es de Vendas
		
		if (qtdeMDV  >= qtdeS_EF) {

			qtde_OV = qtdeMDV - qtdeS_EF;
			custoUnit_OV = precoMedio;
			vrTotal_OV = qtde_OV * custoUnit_OV;

		} else {

			qtde_OV = 0.0;
			custoUnit_OV = 0.0;
			vrTotal_OV = 0.0;

		}
		
		if (qtdeS_EF.equals(0.0)) {

			qtde_OV = qtdeS_EF;
			custoUnit_OV = precoMedio;
			vrTotal_OV = precoTotal;
		}

		if (qtde_OV.equals(0.0)) {

			custoUnit_OV = 0.0;
		}
		
		String invIniQtde = String.format("%.2f", (totAnual.getQtdeEi()==null?0.0:totAnual.getQtdeEi()));
		String vrUnitIniInv = String.format("%.2f",(totAnual.getVrUnitEi()==null?0.0:totAnual.getVrUnitEi()));
		String vrItemIniInv = String.format("%.2f", (totAnual.getVrItemEi()==null?0.0:totAnual.getVrItemEi()));

		String qtdeItnEnt = String.format("%.2f", (totAnual.getQtdeEnt()==null?0.0:totAnual.getQtdeEnt()));
		String vlUnitItnEnt = String.format("%.2f", (totAnual.getVlUnitEnt()==null?0.0:totAnual.getVlUnitEnt()));
		String vlItnEnt = String.format("%.2f", (totAnual.getVlItemEnt()==null?0.0:totAnual.getVlItemEnt()));

		String qtdeItnSai = String.format("%.2f",(totAnual.getQtdeSai()==null?0.0:totAnual.getQtdeSai()));
		String vlUnitItnSai = String.format("%.2f",(totAnual.getVlUnitSai()==null?0.0:totAnual.getVlUnitSai()));
		String vlItnSai = String.format("%.2f", (totAnual.getVlItemSai()==null?0.0:totAnual.getVlItemSai()));

		String invFinQtde = String.format("%.2f", (totAnual.getQtdeEf()==null?0.0:totAnual.getQtdeEf()));
		String vrUnitFinInv = String.format("%.2f",(totAnual.getVrUnitEf()==null?0.0:totAnual.getVrUnitEf()));
		String vrItemFinInv = String.format("%.2f",(totAnual.getVrItemEf()==null?0.0:totAnual.getVrItemEf()));

		String qtdMDV = String.format("%.2f", qtdeMDV);
		String custMedioMDV = String.format("%.2f", (custoMedioMDV.isNaN()?0.0:custoMedioMDV));
		String custTotMDV = String.format("%.2f", custoTotMDV);

		String qtd_S_EF = String.format("%.2f", qtdeS_EF);
		String precoMedio_S_EF = String.format("%.2f", (precoMedio.isNaN()?0.0:precoMedio));
		String precoTot_S_EF = String.format("%.2f", precoTotal);

		String qtd_OC = String.format("%.2f", qtde_OC);
		String custUnit_OC = String.format("%.4f", custoUnit_OC);
		String vlTot_OC = String.format("%.2f", vrTotal_OC);
		
		String qtd_OV = String.format("%.2f", qtde_OV);
		String custUnit_OV = String.format("%.4f", custoUnit_OV);
		String vlTot_OV = String.format("%.2f", vrTotal_OV);

		linha  = totAnual.getCodItem(); 
		linha += ";";
		linha +=  totAnual.getCodAntItem(); //(getMpProdutos().get(id) == null ? "" : getMpProdutos().get(id).getCodAntItem().toString());
		linha += ";";
		linha += totAnual.getDescricao();  //(getMpProdutos().get(id) == null ? "" :getMpProdutos().get(id).getDescricao());
		linha += ";";
		linha += totAnual.getUnd();
		linha += ";";

		linha += ";";
		linha += invIniQtde.replace(".", ",");
		linha += ";";
		linha += vrUnitIniInv.replace(".", ",");
		linha += ";";
		linha += vrItemIniInv.replace(".", ",");
		linha += ";";

		linha += ";";
		linha += qtdeItnEnt.replace(".", ",");
		linha += ";";
		linha += vlUnitItnEnt.replace(".", ",");
		linha += ";";
		linha += vlItnEnt.replace(".", ",");
		linha += ";";

		linha += ";";
		linha += qtdeItnSai.replace(".", ",");
		linha += ";";
		linha += vlUnitItnSai.replace(".", ",");
		linha += ";";
		linha += vlItnSai.replace(".", ",");
		linha += ";";

		linha += ";";
		linha += invFinQtde.replace(".", ",");
		linha += ";";
		linha += vrUnitFinInv.replace(".", ",");
		linha += ";";
		linha += vrItemFinInv.replace(".", ",");
		linha += ";";

		linha += ";";
		linha += qtdMDV.replace(".", ",");
		linha += ";";
		linha += custMedioMDV.replace(".", ",");
		linha += ";";
		linha += custTotMDV.replace(".", ",");
		linha += ";";

		linha += ";";
		linha += qtd_S_EF.replace(".", ",");
		linha += ";";
		linha += precoMedio_S_EF.replace(".", ",");
		linha += ";";
		linha += precoTot_S_EF.replace(".", ",");
		linha += ";";

		linha += ";";
		linha += qtd_OC.replace(".", ",");
		linha += ";";
		linha += custUnit_OC.replace(".", ",");
		linha += ";";
		linha += vlTot_OC.replace(".", ",");
		linha += ";";
		
		linha += ";";
		linha += qtd_OV.replace(".", ",");
		linha += ";";
		linha += custUnit_OV.replace(".", ",");
		linha += ";";
		linha += vlTot_OV.replace(".", ",");
		return linha;
		
		
	}
	
	
	private String cabecalho() {
		String linha;

		linha  = "C�DIGO ITEM";
		linha += ";";
		linha += "C�DIGO ANTERIOR ITEM";
		linha += ";";
		linha += "DESCRI��O";
		linha += ";";
		linha += "UND";
		linha += ";";

		linha += ";";
		linha += "QTDE_EI";
		linha += ";";
		linha += "VR_UNIT_EI";
		linha += ";";
		linha += "VR_ITEM_EI";
		linha += ";";

		linha += ";";
		linha += "QTDE_ENT";
		linha += ";";
		linha += "VL_UNIT_ENT";
		linha += ";";
		linha += "VL_ITEM_ENT";
		linha += ";";

		linha += ";";
		linha += "QTDE_SAI";
		linha += ";";
		linha += "VL_UNIT_SAI";
		linha += ";";
		linha += "VL_ITEM_SAI";
		linha += ";";

		linha += ";";
		linha += "QTDE_EF";
		linha += ";";
		linha += "VR_UNIT_EF";
		linha += ";";
		linha += "VR_ITEM_EF";
		linha += ";";

		linha += ";";
		linha += "QTDE_MDV";
		linha += ";";
		linha += "CUSTO_MEDIO";
		linha += ";";
		linha += "CUSTO TOTAL";
		linha += ";";

		linha += ";";
		linha += "QTDE_(S + EF)";
		linha += ";";
		linha += "PRE�O_MEDIO";
		linha += ";";
		linha += "PRE�O TOTAL";
		linha += ";";

		linha += ";";
		linha += "QTDE_OC";
		linha += ";";
		linha += "CUSTO_UNIT";
		linha += ";";
		linha += "VR_TOTAL";
		linha += ";";
		
		linha += ";";
		linha += "QTDE_OV";
		linha += ";";
		linha += "CUSTO_UNIT";
		linha += ";";
		linha += "VR_TOTAL";
		
		return linha;
	}
	
	private Double calcMDV(Double qteInicialInv , Double qteItemEntrada) {
		
		if(qteInicialInv==null) {
			qteInicialInv = 0.0;
		}
		
		if(qteItemEntrada==null){
			qteItemEntrada =0.0;
		}
		return qteInicialInv + qteItemEntrada;
	}
	
	private Double custoTotMDV(Double vrItemInicialInv , Double vlItemEntrada) {
		if(vrItemInicialInv == null) {
			vrItemInicialInv = 0.0;
		}
		
		if(vlItemEntrada == null) {
			vlItemEntrada = 0.0;
		}
		return vrItemInicialInv + vlItemEntrada;
	}
	
	private Double qtdeS_EF(Double qteFinalInv ,Double qteItemSaida) {
		if(qteFinalInv == null) {
			qteFinalInv = 0.0;
		}
		
		if(qteItemSaida == null){
			qteItemSaida = 0.0;
		}
		return qteFinalInv+qteItemSaida;
	}
	
	private Double precoTotal(Double vrItemFinalInv ,Double vlItemSaida) {
		if(vrItemFinalInv == null){
			vrItemFinalInv = 0.0;
		}
		
		if(vlItemSaida == null){
			vlItemSaida = 0.0;
		}
		return vrItemFinalInv + vlItemSaida;
	}

}


