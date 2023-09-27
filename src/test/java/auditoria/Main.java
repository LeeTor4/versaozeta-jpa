package auditoria;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.map.HashedMap;

import com.zeta.auditoria.CodigosProcVitDao;
import com.zeta.auditoria.CodigosProcVitModel;
import com.zeta.auditoria.HistoricoItemDao;
import com.zeta.auditoria.HistoricoItemModel;
import com.zeta.auditoria.ItensInvDeclaradoDao;
import com.zeta.auditoria.ItensInvDeclaradoModel;
import com.zeta.auditoria.ProdutosLevantamento2012Dao;
import com.zeta.auditoria.ProdutosLevantamento2012Model;
import com.zeta.util.UtilsEConverters;

public class Main {
	
	
	public static ItensInvDeclaradoModel itemDoInventario(String codigo) {
		ItensInvDeclaradoModel retorno = new ItensInvDeclaradoModel();
		String formatted = ("00000000" + codigo).substring(codigo.length());
		ItensInvDeclaradoDao invDao = new ItensInvDeclaradoDao();
		List<ItensInvDeclaradoModel> collect = invDao.listaTodos().stream().collect(Collectors.toList());
		Stream<ItensInvDeclaradoModel> filter = collect.stream().filter(item -> item.getCodItem().equals(formatted));
		List<ItensInvDeclaradoModel> collect2 = filter.collect(Collectors.toList());
		for(ItensInvDeclaradoModel obj : collect2){
			retorno = obj;
		}
		return retorno;
	}
	
	public static void main(String[] args) throws IOException{
		
//       ItensInvDeclaradoDao invDao = new ItensInvDeclaradoDao();
//		
//       List<ItensInvDeclaradoModel> collect = invDao.listaTodos().stream().collect(Collectors.toList());
//		
//       Stream<ItensInvDeclaradoModel> filter = collect.stream().filter(item -> item.getCodItem().equals("00000188"));
//       
//       List<ItensInvDeclaradoModel> collect2 = filter.collect(Collectors.toList());
//       
//       for(ItensInvDeclaradoModel obj : collect2){
//    	   System.out.println(obj.getCodItem() + "|" + obj.getQtde());
//       }
       
      
		CodigosProcVitDao procVitDao = new CodigosProcVitDao();
		
		List<CodigosProcVitModel> collect = procVitDao.listaTodos().stream().collect(Collectors.toList());
		
//		Stream<CodigosProcVitModel> filter = collect.stream().filter(item -> item.getCodigoProc().equals("3403"));
//		
//		List<CodigosProcVitModel> collect2 = filter.collect(Collectors.toList());
//		
//		for(CodigosProcVitModel obj : collect2){
//			String formatted = ("00000000" + obj.getCodigoVit()).substring(obj.getCodigoVit().length());
//			System.out.println(formatted);
//		}
		
		
	    Double totalEntradaJanGlobal = 0.0;
	    Double totalSaidaJanGlobal = 0.0;
	    Double totalEntradaFevGlobal = 0.0;
	    Double totalSaidaFevGlobal = 0.0;
	    Double totalEntradaMarGlobal = 0.0;
	    Double totalSaidaMarGlobal = 0.0;
	    Double totalEntradaAbrGlobal = 0.0;
	    Double totalSaidaAbrGlobal = 0.0;
	    Double totalEntradaMaiGlobal = 0.0;
	    Double totalSaidaMaiGlobal = 0.0;
	    Double totalEntradaJunGlobal = 0.0;
	    Double totalSaidaJunGlobal = 0.0;
	    Double totalEntradaJulGlobal = 0.0;
	    Double totalSaidaJulGlobal = 0.0;
	    Double totalEntradaAgoGlobal = 0.0;
	    Double totalSaidaAgoGlobal = 0.0;
	    Double totalEntradaSetGlobal = 0.0;
	    Double totalSaidaSetGlobal = 0.0;
	    Double totalEntradaOutGlobal = 0.0;
	    Double totalSaidaOutGlobal = 0.0;
	    Double totalEntradaNovGlobal = 0.0;
	    Double totalSaidaNovGlobal = 0.0;
	    Double totalEntradaDezGlobal = 0.0;
	    Double totalSaidaDezGlobal = 0.0;
	    
	    Double totalEntrada = 0.0;
	    Double totalSaida = 0.0;
	    
	    Double saldoInicial = 0.0;
	    Double saldoJan = 0.0, saldoJanAnterior = 0.0;
	    Double saldoFev = 0.0, saldoFevAnterior = 0.0;
	    Double saldoMar = 0.0, saldoMarAnterior = 0.0;
	    Double saldoAbr = 0.0, saldoAbrAnterior = 0.0;
	    Double saldoMai = 0.0, saldoMaiAnterior = 0.0;
	    Double saldoJun = 0.0, saldoJunAnterior = 0.0;
	    Double saldoJul = 0.0, saldoJulAnterior = 0.0;
	    Double saldoAgo = 0.0, saldoAgoAnterior = 0.0;
	    Double saldoSet = 0.0, saldoSetAnterior = 0.0;
	    Double saldoOut = 0.0, saldoOutAnterior = 0.0;
	    Double saldoNov = 0.0, saldoNovAnterior = 0.0;
	    Double saldoDez = 0.0, saldoDezAnterior = 0.0;
	    
	    int ano = 2012;
        HistoricoItemDao dao = new HistoricoItemDao();
		
		List<HistoricoItemModel> collectovimento = dao.listaTodos().stream().collect(Collectors.toList());

		ProdutosLevantamento2012Dao levantamento2012Dao = new ProdutosLevantamento2012Dao(); 
		
		List<ProdutosLevantamento2012Model> collect3 = levantamento2012Dao.listaTodos().stream()
				.limit(50)
				.collect(Collectors.toList());

		List<String> novasLinhas =  new ArrayList<String>();   
	    Path pV12 = Paths.get("C:\\Users\\chico\\Downloads\\ArquivoFinal.txt");
	    for(ProdutosLevantamento2012Model obj :  collect3){	  }	   
//	    	System.out.println(obj.getProdutoSefaz()+"|"+obj.getCodigoAntigo1()
//	    	    +"|"+obj.getCodigoAntigo2()+"|"+obj.getCodigoAtual()+"|"+obj.getDescricao()+"|"+obj.getUnidade()+"|"+obj.getFator());
	   
//	    	String antigo1 = ("00000000" + obj.getCodigoAntigo1()).substring(obj.getCodigoAntigo1().length());  
//	    	String antigo2 = ("00000" + obj.getCodigoAntigo2()).substring(obj.getCodigoAntigo2().length());  
//	    	String atual   = (obj.getCodigoAtual());  
	        
	        //BufferedWriter buffWrite = Files.newBufferedWriter(pV12);
	    
			Stream<HistoricoItemModel> filter = collectovimento.stream()
					.filter(item -> item.getCodItem().equals("00008669")
							     || item.getCodItem().equals("00008669")
							     || item.getCodItem().equals("00008669"));
		 
			List<HistoricoItemModel> collect2 = filter.collect(Collectors.toList());
			
			//System.out.println("Produto: " + antigo1 + " - " + antigo2 + " - " + atual + " Descrição: " + obj.getDescricao());
			
			//novasLinhas.add("Produto: " + antigo1 +"/" + antigo2 +"/" + atual + " Descrição: " + obj.getDescricao());
			novasLinhas.add("_____________________________________________________");
			novasLinhas.add("          |");
			novasLinhas.add("Data      |      M O V I M E N T O                    |         E S T O Q U E");
			novasLinhas.add("__________|___________________________________________");
			//System.out.println(itemDoInventario(antigo1).getQtde());
			saldoInicial = (itemDoInventario("00008669").getQtde()==null?0:itemDoInventario("00008669").getQtde());
			novasLinhas.add("                                                         " + String.valueOf(saldoInicial));
			for(HistoricoItemModel objMovimento : collect2){
			
				if (objMovimento.getDtDoc().getYear() == ano) {
					if (objMovimento.getDtDoc().getMonth().getValue() == 1) {
						Double totalEntradaJan = 0.0;
						Double totalSaidaJan = 0.0;
						System.out.println(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						novasLinhas.add(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						if (objMovimento.getOperacao().equals("E")) {
							totalEntradaJan += objMovimento.getQtde().doubleValue();
							 totalEntradaJanGlobal = totalEntradaJan;
							
						}
						if (objMovimento.getOperacao().equals("S")) {
							totalSaidaJan  += objMovimento.getQtde().doubleValue();
							totalSaidaJanGlobal = totalSaidaJan;
						}

		                if((totalEntradaJan > 0) || (totalSaidaJan > 0)) {

		        			System.out.println("Total do Mês 01/"   + ano + " Entradas " + totalEntradaJan + "    " + (saldoInicial+totalEntradaJan));
		        			System.out.println("Total do Mês 01/" + ano + " Saidas " + totalSaidaJan + "    " + (saldoInicial+totalEntradaJan-totalSaidaJan));
		        			
		        			novasLinhas.add("Total do Mês 01/"   + ano + " Entradas " + totalEntradaJan + "    " + (saldoInicial+totalEntradaJan));
		        			novasLinhas.add("Total do Mês 01/" + ano + " Saidas " + totalSaidaJan + "    " + (saldoInicial+totalEntradaJan-totalSaidaJan));
		        			
		        			saldoJanAnterior = (saldoInicial+totalEntradaJan-totalSaidaJan);
		                }
		                
		                break;
					}
				}
			}
			

               saldoJan = saldoJanAnterior;

			for(HistoricoItemModel objMovimento : collect2){
				
				if (objMovimento.getDtDoc().getYear() == ano) {
					
					if (objMovimento.getDtDoc().getMonth().getValue() == 2) {
						Double totalEntradaFev = 0.0;
						Double totalSaidaFev = 0.0;
						System.out.println(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						novasLinhas.add(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						if (objMovimento.getOperacao().equals("E")) {
							totalEntradaFev += objMovimento.getQtde().doubleValue();
							totalEntradaFevGlobal = totalEntradaFev;
						}

						if (objMovimento.getOperacao().equals("S")) {			
							totalSaidaFev  += objMovimento.getQtde().doubleValue();
							totalSaidaFevGlobal = totalSaidaFev;
						}
						
						
						 if((totalEntradaFev > 0) || (totalSaidaFev > 0) ) {
							
							
								System.out.println("Total do Mês 02/"+ ano + " Entradas " + totalEntradaFev + "  " + (saldoJan + totalEntradaFev));
								System.out.println("Total do Mês 02/" + ano + " Saidas " + totalSaidaFev + "  " + ((saldoJan + totalEntradaFev)-totalSaidaFev));
								
								novasLinhas.add("Total do Mês 02/"+ ano + " Entradas " + totalEntradaFev + "  " + (saldoInicial+saldoJan + totalEntradaFev));
			        			novasLinhas.add("Total do Mês 02/" + ano + " Saidas " + totalSaidaFev + "  " + ((saldoInicial+saldoJan + totalEntradaFev)-totalSaidaFev));
			        			
			        			saldoFevAnterior = ((saldoJan + totalEntradaFev)-totalSaidaFev);
						 }
						 
						 break;
					}
				}
			}
			


			saldoFev = saldoFevAnterior;
			
			for(HistoricoItemModel objMovimento : collect2){
				
				if (objMovimento.getDtDoc().getYear() == ano) {					
					if (objMovimento.getDtDoc().getMonth().getValue() == 3) {
						Double totalEntradaMar = 0.0;
						Double totalSaidaMar = 0.0;
						System.out.println(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						novasLinhas.add(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						if (objMovimento.getOperacao().equals("E")) {
							totalEntradaMar += objMovimento.getQtde().doubleValue();
							totalEntradaMarGlobal = totalEntradaMar;
						}

						if (objMovimento.getOperacao().equals("S")) {
							totalSaidaMar  += objMovimento.getQtde().doubleValue();
							totalSaidaMarGlobal = totalSaidaMar;  
						}
						if((totalEntradaMar > 0) || (totalSaidaMar > 0)) {

								System.out.println("Total do Mês 03/"+ ano + " Entradas " + totalEntradaMar + "  " + (saldoFev + totalEntradaMar));
								System.out.println("Total do Mês 03/" + ano + " Saidas " + totalSaidaMar + "  " + ((saldoFev + totalEntradaMar)-totalSaidaMar));
								novasLinhas.add("Total do Mês 03/"+ ano + " Entradas " + totalEntradaMar + "  " + (saldoFev + totalEntradaMar));
								novasLinhas.add("Total do Mês 03/" + ano + " Saidas " + totalSaidaMar + "  " + ((saldoFev + totalEntradaMar)-totalSaidaMar));
								saldoMarAnterior = ((saldoFev + totalEntradaMar)-totalSaidaMar);
						}
						break;
					}
				}
			}
			

			
           saldoMar = saldoMarAnterior;  
            
			for(HistoricoItemModel objMovimento : collect2){
				
				if (objMovimento.getDtDoc().getYear() == ano) {
					
					if (objMovimento.getDtDoc().getMonth().getValue() == 4) {
						Double totalEntradaAbr = 0.0;
						Double totalSaidaAbr = 0.0;
						System.out.println(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						novasLinhas.add(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						
						if (objMovimento.getOperacao().equals("E")) {
							totalEntradaAbr += objMovimento.getQtde().doubleValue();
							 totalEntradaAbrGlobal = totalEntradaAbr;
						}

						if (objMovimento.getOperacao().equals("S")) {
							totalSaidaAbr  += objMovimento.getQtde().doubleValue();
							totalSaidaAbrGlobal = totalSaidaAbr;
						}
			            
						 if((totalEntradaAbr > 0 || totalSaidaAbr > 0)) {
							
							 
								System.out.println("Total do Mês 04/" + ano + " Entradas " + totalEntradaAbr + "  " + (saldoInicial+saldoInicial+saldoMar + totalEntradaAbr));
								System.out.println("Total do Mês 04/" + ano + " Saidas " + totalSaidaAbr + "  " + ((saldoInicial+saldoInicial+saldoMar + totalEntradaAbr)-totalSaidaAbr));
					
								novasLinhas.add("Total do Mês 04/" + ano + " Entradas " + totalEntradaAbr + "  " + (saldoInicial+saldoInicial+saldoMar + totalEntradaAbr));
						        novasLinhas.add("Total do Mês 04/" + ano + " Saidas " + totalSaidaAbr + "  " + ((saldoInicial+saldoInicial+saldoMar + totalEntradaAbr)-totalSaidaAbr));
						        
						        saldoAbrAnterior = ((saldoMar + totalEntradaAbr)-totalSaidaAbr);
						 }       

						break;
					}
					
					
				}
				
			}

            saldoAbr = saldoAbrAnterior;
            
			for(HistoricoItemModel objMovimento : collect2){
				
				if (objMovimento.getDtDoc().getYear() == ano) {
					
					if (objMovimento.getDtDoc().getMonth().getValue() == 5) {
						Double totalEntradaMai = 0.0;
						Double totalSaidaMai = 0.0;
						System.out.println(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						novasLinhas.add(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						if (objMovimento.getOperacao().equals("E")) {
							totalEntradaMai += objMovimento.getQtde().doubleValue();
							 totalEntradaMaiGlobal = totalEntradaMai;
						}

						if (objMovimento.getOperacao().equals("S")) {
							totalSaidaMai  += objMovimento.getQtde().doubleValue();
							 totalSaidaMaiGlobal = totalSaidaMai;
						}
						
						 if((totalEntradaMai > 0) || (totalSaidaMai > 0) ) {
							
							
								System.out.println("Total do Mês 05/" + ano + " Entradas " + totalEntradaMai + "  " + (saldoInicial+saldoAbr+totalEntradaMai));
								System.out.println("Total do Mês 05/" + ano + " Saidas " + totalSaidaMai + "  " + ((saldoInicial+saldoAbr+totalEntradaMai)-totalSaidaMai));
						        novasLinhas.add("Total do Mês 05/" + ano + " Entradas " + totalEntradaMai + "  " + (saldoInicial+saldoAbr+totalEntradaMai));
						        novasLinhas.add("Total do Mês 05/" + ano + " Saidas " + totalSaidaMai + "  " + ((saldoInicial+saldoAbr+totalEntradaMai)-totalSaidaMai));
						   
								saldoMaiAnterior = ((saldoAbr+totalEntradaMai)-totalSaidaMai);
						 }
						 
						 break;
					}
				}
				
			}

			saldoMai = saldoMaiAnterior;
			
			for(HistoricoItemModel objMovimento : collect2){
				
				if (objMovimento.getDtDoc().getYear() == ano) {
					
					if (objMovimento.getDtDoc().getMonth().getValue() == 6) {
						Double totalEntradaJun = 0.0;
						Double totalSaidaJun = 0.0;
						System.out.println(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						novasLinhas.add(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						if (objMovimento.getOperacao().equals("E")) {
							totalEntradaJun += objMovimento.getQtde().doubleValue();
							totalEntradaJunGlobal = totalEntradaJun;
						}

						if (objMovimento.getOperacao().equals("S")) {
							totalSaidaJun  += objMovimento.getQtde().doubleValue();
							totalSaidaJunGlobal = totalSaidaJun;
						}
			            
						 if((totalEntradaJun > 0) || (totalSaidaJun > 0)) {
							
							
								System.out.println("Total do Mês 06/" + ano + " Entradas " + totalEntradaJun + "   " + (saldoInicial+saldoMai+totalEntradaJun));
								System.out.println("Total do Mês 06/" + ano + " Saidas " + totalSaidaJun + "  " + ((saldoInicial+saldoMai+totalEntradaJun)-totalSaidaJun));
								novasLinhas.add("Total do Mês 06/" + ano + " Entradas " + totalEntradaJun + "   " + (saldoInicial+saldoMai+totalEntradaJun));
								novasLinhas.add("Total do Mês 06/" + ano + " Saidas " + totalSaidaJun + "  " + ((saldoInicial+saldoMai+totalEntradaJun)-totalSaidaJun));
								saldoJunAnterior = ((saldoMai+totalEntradaJun)-totalSaidaJun);
						 }
						 break;
					}
				}
				
			}
			saldoJun = saldoJunAnterior;
			
			for(HistoricoItemModel objMovimento : collect2){
				
				if (objMovimento.getDtDoc().getYear() == ano) {
					
					if (objMovimento.getDtDoc().getMonth().getValue() == 7) {
						Double totalEntradaJul = 0.0;
						Double totalSaidaJul = 0.0;
						System.out.println(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						novasLinhas.add(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						if (objMovimento.getOperacao().equals("E")) {
							totalEntradaJul += objMovimento.getQtde().doubleValue();
							totalEntradaJulGlobal = totalEntradaJul;
						}

						if (objMovimento.getOperacao().equals("S")) {
							totalSaidaJul  += objMovimento.getQtde().doubleValue();
							totalSaidaJulGlobal = totalSaidaJul;
						}
			            
						 if((totalEntradaJul > 0) || (totalSaidaJul > 0)) {
							
							
								System.out.println("Total do Mês 07/" + ano + " Entradas " + totalEntradaJul + "  " + (saldoInicial+saldoJun + totalEntradaJul));
								System.out.println("Total do Mês 07/" + ano + " Saidas " + totalSaidaJul  + "  " + ((saldoInicial+saldoJun + totalEntradaJul)-totalSaidaJul));
						        
								novasLinhas.add("Total do Mês 07/" + ano + " Entradas " + totalEntradaJul + "  " + (saldoInicial+saldoJun + totalEntradaJul));
								novasLinhas.add("Total do Mês 07/" + ano + " Entradas " + totalEntradaJul + "  " + (saldoInicial+saldoJun + totalEntradaJul));
								
								saldoJulAnterior = ((saldoJun + totalEntradaJul)-totalSaidaJul);
						 }
						 break;
					}
				}
				
			}
			
			saldoJul =  saldoJulAnterior;
			 
			for(HistoricoItemModel objMovimento : collect2){
				
				if (objMovimento.getDtDoc().getYear() == ano) {
					
					if (objMovimento.getDtDoc().getMonth().getValue() == 8) {
						Double totalEntradaAgo = 0.0;
						Double totalSaidaAgo = 0.0;
						System.out.println(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						novasLinhas.add(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						if (objMovimento.getOperacao().equals("E")) {
							totalEntradaAgo += objMovimento.getQtde().doubleValue();
							totalEntradaAgoGlobal = totalEntradaAgo;
						}

						if (objMovimento.getOperacao().equals("S")) {
							totalSaidaAgo  += objMovimento.getQtde().doubleValue();
							totalSaidaAgoGlobal = totalSaidaAgo;
						}
						
						 if((totalEntradaAgo > 0) || (totalSaidaAgo > 0)) {
							
							 
								System.out.println("Total do Mês 08/" + ano + " Entradas " + totalEntradaAgo + "  " + (saldoInicial+saldoJul + totalEntradaAgo));
								System.out.println("Total do Mês 08/" + ano + " Saidas " + totalSaidaAgo + "  " + ((saldoInicial+saldoJul + totalEntradaAgo)-totalSaidaAgo));
						 
						        novasLinhas.add("Total do Mês 08/" + ano + " Entradas " + totalEntradaAgo + "  " + (saldoInicial+saldoJul + totalEntradaAgo));
						        novasLinhas.add("Total do Mês 08/" + ano + " Saidas " + totalSaidaAgo + "  " + ((saldoInicial+saldoJul + totalEntradaAgo)-totalSaidaAgo));
						        
						        saldoAgoAnterior = ((saldoJul + totalEntradaAgo)-totalSaidaAgo);
						 }
						 
						 break;
					}
				}
				
			}
			

     
			saldoAgo = saldoAgoAnterior;
			
			for(HistoricoItemModel objMovimento : collect2){
				
				if (objMovimento.getDtDoc().getYear() == ano) {
					
					if (objMovimento.getDtDoc().getMonth().getValue() == 9) {
						Double totalEntradaSet = 0.0;
						Double totalSaidaSet = 0.0;
						System.out.println(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						novasLinhas.add(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						if (objMovimento.getOperacao().equals("E")) {
							totalEntradaSet += objMovimento.getQtde().doubleValue();
							totalEntradaSetGlobal = totalEntradaSet;
						}

						if (objMovimento.getOperacao().equals("S")) {
							totalSaidaSet  += objMovimento.getQtde().doubleValue();
						    totalSaidaSetGlobal = totalSaidaSet;
						}
			            
						 if((totalEntradaSet > 0) || (totalSaidaSet > 0)) {
							 
							
								System.out.println("Total do Mês 09/" + ano + " Entradas " + totalEntradaSet + "  " + (saldoInicial+saldoAgo + totalEntradaSet));
								System.out.println("Total do Mês 09/" + ano + " Saidas " + totalSaidaSet + "  " + ((saldoInicial+saldoAgo + totalEntradaSet)-totalSaidaSet));
								
								novasLinhas.add("Total do Mês 09/" + ano + " Entradas " + totalEntradaSet + "  " + (saldoInicial+saldoAgo + totalEntradaSet));
								novasLinhas.add("Total do Mês 09/" + ano + " Saidas " + totalSaidaSet + "  " + ((saldoInicial+saldoAgo + totalEntradaSet)-totalSaidaSet));
								
								saldoSetAnterior = ((saldoAgo + totalEntradaSet)-totalSaidaSet);
						 }
						 break;
					}
				}
				
			}
			


			saldoSet = saldoSetAnterior; 
			
			for(HistoricoItemModel objMovimento : collect2){
				
				if (objMovimento.getDtDoc().getYear() == ano) {
					
					if (objMovimento.getDtDoc().getMonth().getValue() == 10) {
						Double totalEntradaOut = 0.0;
						Double totalSaidaOut = 0.0;
						System.out.println(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						novasLinhas.add(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						if (objMovimento.getOperacao().equals("E")) {
							totalEntradaOut += objMovimento.getQtde().doubleValue();
						    totalEntradaOutGlobal = totalEntradaOut;
						}

						if (objMovimento.getOperacao().equals("S")) {
							totalSaidaOut  += objMovimento.getQtde().doubleValue();
							totalSaidaOutGlobal = totalSaidaOut;
						}
						
						 if((totalEntradaOut > 0) || (totalSaidaOut > 0)) {
							
							
								System.out.println("Total do Mês 10/" + ano + " Entradas " + totalEntradaOut +  "  "  + (saldoInicial+saldoSet+totalEntradaOut));
								System.out.println("Total do Mês 10/" + ano + " Saidas " + totalSaidaOut + "  " + ((saldoInicial+saldoSet+totalEntradaOut)-totalSaidaOut));
								
								novasLinhas.add("Total do Mês 10/" + ano + " Entradas " + totalEntradaOut +  "  "  + (saldoInicial+saldoSet+totalEntradaOut));
								novasLinhas.add("Total do Mês 10/" + ano + " Saidas " + totalSaidaOut + "  " + ((saldoInicial+saldoSet+totalEntradaOut)-totalSaidaOut));
								
								saldoOutAnterior = ((saldoSet+totalEntradaOut)-totalSaidaOut);
						 }
						 break;
					}
				}
				
			}
			


			saldoOut = saldoOutAnterior;
			
			for(HistoricoItemModel objMovimento : collect2){
				
				if (objMovimento.getDtDoc().getYear() == ano) {
					if (objMovimento.getDtDoc().getMonth().getValue() == 11) {
						Double totalEntradaNov = 0.0;
						Double totalSaidaNov = 0.0;
						System.out.println(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						novasLinhas.add(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						if (objMovimento.getOperacao().equals("E")) {
							totalEntradaNov += objMovimento.getQtde().doubleValue();
							totalEntradaNovGlobal = totalEntradaNov;
						}

						if (objMovimento.getOperacao().equals("S")) {
							totalSaidaNov  += objMovimento.getQtde().doubleValue();
							totalSaidaNovGlobal = totalSaidaNov;
						}
			            
						 if((totalEntradaNov > 0) || (totalSaidaNov > 0) ) {
							
							 
								System.out.println("Total do Mês 11/" +ano  + " Entradas " + totalEntradaNov + "  " + (saldoInicial+saldoOut + totalEntradaNov));
								System.out.println("Total do Mês 11/" +ano + " Saidas " + totalSaidaNov + " " + ( (saldoInicial+saldoOut + totalEntradaNov)-totalSaidaNov));
								
								novasLinhas.add("Total do Mês 11/" +ano  + " Entradas " + totalEntradaNov + "  " + (saldoInicial+saldoOut + totalEntradaNov));
								novasLinhas.add("Total do Mês 11/" +ano + " Saidas " + totalSaidaNov + " " + ( (saldoInicial+saldoOut + totalEntradaNov)-totalSaidaNov));
								
								saldoNovAnterior = ( (saldoOut + totalEntradaNov)-totalSaidaNov);
						 }
						 break;
					}
				}
				
				
			}
	    

             
			saldoNov = saldoNovAnterior;
			 
			for(HistoricoItemModel objMovimento : collect2){
				
				if (objMovimento.getDtDoc().getYear() == ano) {
					if (objMovimento.getDtDoc().getMonth().getValue() == 12) {
						Double totalEntradaDez = 0.0;
						Double totalSaidaDez = 0.0;
						System.out.println(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						novasLinhas.add(objMovimento.getDtDoc() + "|" + objMovimento.getNumDoc() + "|" + objMovimento.getCfop()+ "|" + objMovimento.getQtde());
						if (objMovimento.getOperacao().equals("E")) {
							totalEntradaDez += objMovimento.getQtde().doubleValue();	
						    totalEntradaDezGlobal = totalEntradaDez;
						}

						if (objMovimento.getOperacao().equals("S")) {
							totalSaidaDez  += objMovimento.getQtde().doubleValue();
							totalSaidaDezGlobal = totalSaidaDez;
						}
		                
		                if((totalEntradaDez > 0) || (totalSaidaDez > 0)) {
							
							 
		    				System.out.println("Total do Mês 12/" +ano  + " Entradas " + totalEntradaDez + "  " + (saldoInicial+saldoNov + totalEntradaDez));
		    				System.out.println("Total do Mês 12/" +ano + " Saidas " + totalSaidaDez + "  " + ((saldoInicial+saldoNov + totalEntradaDez)-totalSaidaDez));
		    				
		    				novasLinhas.add("Total do Mês 12/" +ano  + " Entradas " + totalEntradaDez + "  " + (saldoInicial+saldoNov + totalEntradaDez));
		    				novasLinhas.add("Total do Mês 12/" +ano + " Saidas " + totalSaidaDez + "  " + ((saldoInicial+saldoNov + totalEntradaDez)-totalSaidaDez));
		                }
		                
		                break;
					}
				}

			}



			System.out.println("---------------------------------------------------------------");

//			Double totalDasEntrada = collectovimento.stream()
//					.filter(codItem -> codItem.getDtDoc().getYear() == ano)
//					.filter(codItem -> codItem.getOperacao().equals("E"))
//					.filter(codItem -> codItem.getCodItem().equals(antigo1) 
//							|| codItem.getCodItem().equals(antigo2)
//							|| codItem.getCodItem().equals(atual)).mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
//			
//			
//			Double totalDasSaidas = collectovimento.stream()
//					.filter(codItem -> codItem.getDtDoc().getYear() == ano)
//					.filter(codItem -> codItem.getOperacao().equals("S"))
//					.filter(codItem -> codItem.getCodItem().equals(antigo1) 
//							|| codItem.getCodItem().equals(antigo2)
//							|| codItem.getCodItem().equals(atual)).mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
//			
//			System.out.println("Soma das Entradas " + totalDasEntrada);
//			System.out.println("Soma das Saidas " +  totalDasSaidas);
//			
//			novasLinhas.add("Soma das Entradas " + totalDasEntrada);
//			novasLinhas.add("Soma das Saidas " +  totalDasSaidas);
			novasLinhas.add("_________________________________________________________");
			
			
	   
	    Files.write(pV12, novasLinhas, StandardOpenOption.CREATE);
	}
}
