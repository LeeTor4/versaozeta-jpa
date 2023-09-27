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

public class Main2 {
	
	
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
		
	    int ano = 2012;
        HistoricoItemDao dao = new HistoricoItemDao();
		
		List<HistoricoItemModel> collectovimento = dao.listaTodos().stream()
				.limit(10)
				.collect(Collectors.toList());

		ProdutosLevantamento2012Dao levantamento2012Dao = new ProdutosLevantamento2012Dao(); 
		
		List<ProdutosLevantamento2012Model> collect3 = levantamento2012Dao.listaTodos().stream()
				.filter(item -> item.getCodigoAtual().equals("6576"))
				.collect(Collectors.toList());

		List<String> novasLinhas =  new ArrayList<String>();   
	    Path pV12 = Paths.get("C:\\Users\\chico\\Downloads\\ArquivoFinal.txt");
	    for(ProdutosLevantamento2012Model obj :  collect3){	  
//	    	System.out.println(obj.getProdutoSefaz()+"|"+obj.getCodigoAntigo1()
//	    	    +"|"+obj.getCodigoAntigo2()+"|"+obj.getCodigoAtual()+"|"+obj.getDescricao()+"|"+obj.getUnidade()+"|"+obj.getFator());
	   
	    	String antigo1 = ("00000000" + obj.getCodigoAntigo1()).substring(obj.getCodigoAntigo1().length());  
	    	String antigo2 = ("00000" + obj.getCodigoAntigo2()).substring(obj.getCodigoAntigo2().length());  
	    	String atual   = (obj.getCodigoAtual());  
	        
	        //BufferedWriter buffWrite = Files.newBufferedWriter(pV12);
	       
			Stream<HistoricoItemModel> filter = collectovimento.stream();
		 
			List<HistoricoItemModel> collect2 = filter.collect(Collectors.toList());
			novasLinhas.add("Produto:" + antigo1 +"/" + antigo2 +"/"+atual+ " Descrição:" + obj.getDescricao() + "Unidade:" + obj.getUnidade());
			//novasLinhas.add("Produto: 1004 Descrição:EXODUS 10 MG C/30 CPR [C1]  Unidade:  UN " );
			novasLinhas.add("--------------------------------------------------------------------------------------------------------------------------");
			novasLinhas.add("          |                                                       |                                               |");
			novasLinhas.add("Data      |               M O V I M E N T O                       |              E S T O Q U E                    |");                 
			novasLinhas.add("          |   Numero|CFOP|Quantidade|Preço Unitário|         Valor|       Quantidade|Preço Médio   |Valor         |                          ");
			novasLinhas.add("--------------------------------------------------------------------------------------------------------------------------");
			
            Double saldoInicial = collect2.stream()
					.filter(codItem -> codItem.getOperacao().equals("EST"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
            Double precoMedio = collect2.stream()
					.filter(codItem -> codItem.getOperacao().equals("EST"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
            Double precoValor = collect2.stream()
					.filter(codItem -> codItem.getOperacao().equals("EST"))
					.mapToDouble(qtde -> qtde.getVlLiq().doubleValue()).sum();
            
            String pMedio = String.valueOf(String.format("%.2f", precoMedio));
            String campoMedio = ("              " + pMedio).substring(pMedio.length());  
            
            String pValor = String.valueOf(String.format("%.2f", precoValor));
            String campoValorEstoque = ("              " + pValor).substring(pValor.length());  
          
			novasLinhas.add("                                                                  |            " + String.valueOf(String.format("%.2f", saldoInicial))+"|"+campoMedio+"|"+campoValorEstoque+"|");
			
            for(HistoricoItemModel objMovimento : collect2){                                       
            	
                if(objMovimento.getDtDoc().getMonthValue() == 1) {
                	String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
            	    String campoNumero = ("         " + objMovimento.getNumDoc()).substring(objMovimento.getNumDoc().length()); 
            	    String campoQtde = ("          " + qtde).substring(qtde.length());  
            	    String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
            	    String campoVlUnit = ("              " + vUnit).substring(vUnit.length());  
            	    String valor =  String.format("%.2f",objMovimento.getVlLiq());
            	    String campoValor = ("              " + valor).substring(valor.length());  
                	System.out.println(objMovimento.getCodItem() + "|" + objMovimento.getCodItemAnterior() + "|" + objMovimento.getOperacao()
            	    + "|" + objMovimento.getQtde()+ "|" + objMovimento.getSaldoAcumuladoQtde());
            		novasLinhas.add(objMovimento.getDtDoc() + "|" + campoNumero + "|" + objMovimento.getCfop()
            		+ "|" + campoQtde + "|" + campoVlUnit + "|" + campoValor + "|" + "                 |" + "              |"+"              |");
            	}      
            }
            
            novasLinhas.add("________________________________________________________________________________________________________________________");
            Double totalEntradaJan = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 1)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
            
            Double totalVrunitEntradaJan = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 1)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitEntradaJan = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 1)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.count();
            
            Double vrUnitTotaisEntJan = 0.0;
            if(qtdeTotalVrunitEntradaJan > 0) {
            	vrUnitTotaisEntJan = totalVrunitEntradaJan/qtdeTotalVrunitEntradaJan;
            	
            }
            
            Double valorTotaisEntJan = totalEntradaJan * vrUnitTotaisEntJan;
            

			Double totalSaidaJan = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 1)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
            Double totalVrunitSaidaJan = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 1)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitSaidaJan = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 1)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.count();
            
            Double vrUnitTotaisSaidaJan = 0.0;
            if(qtdeTotalVrunitSaidaJan > 0) {
            	vrUnitTotaisSaidaJan = totalVrunitSaidaJan/qtdeTotalVrunitSaidaJan;
            }
            
            Double valorTotaisSaiJan = totalSaidaJan * vrUnitTotaisSaidaJan;
			
			System.out.println("Total do Mês 01/"   + ano + " Entradas " + Math.abs(totalEntradaJan) + "|         " + vrUnitTotaisEntJan + (saldoInicial+totalEntradaJan)+"|");
			System.out.println("Total do Mês 01/" + ano + " Saidas " + Math.abs(totalSaidaJan) + "|    " + (saldoInicial+totalEntradaJan-totalSaidaJan)+"|");

			String totEnt1 = String.valueOf(String.format("%.2f", Math.abs(totalEntradaJan)));
			String campoTotEnt1 = ("      " + totEnt1).substring(totEnt1.length()); 
			
			
			String totSai1 = String.valueOf(String.format("%.2f", Math.abs(totalSaidaJan)));
			String campoTotSai1 =  ("        " + totSai1).substring(totSai1.length()); 
			Double saldoInicialSumTotalEntradaJan = saldoInicial+totalEntradaJan;
			Double saldoInicialSumtotalEntradaJanMenusMathTotalSaidaJan = (saldoInicial+totalEntradaJan)-(Math.abs(totalSaidaJan));
			
			Double precoMedioEstoque = 0.0;
			if(vrUnitTotaisEntJan < 1) {
				precoMedioEstoque = precoMedio;
			}else {
				precoMedioEstoque = vrUnitTotaisEntJan;
			}
			
			String precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			Double valorTotalEstoque = 0.0;
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaJan;
			}
			
			novasLinhas.add("Total do Mês 01/" + ano + " Entradas " + campoTotEnt1 + "|          " + String.format("%.2f", vrUnitTotaisEntJan)   +"|          "+ String.format("%.2f", valorTotaisEntJan) + "|            " +  String.format("%.2f", saldoInicialSumTotalEntradaJan)+"|         "+precoMedioEstoque_+"|        "+String.format("%.2f", valorTotalEstoque)+"|");
			novasLinhas.add("Total do Mês 01/" + ano + " Saidas " + campoTotSai1 +   "|         " + String.format("%.2f", vrUnitTotaisSaidaJan) +"|        "+ String.format("%.2f", Math.abs(valorTotaisSaiJan)) + "|             " +String.format("%.2f",saldoInicialSumtotalEntradaJanMenusMathTotalSaidaJan)+"|"+"              |"+"              |");
		
			novasLinhas.add("________________________________________________________________________________________________________________________");
			saldoJanAnterior = (saldoInicial+totalEntradaJan-Math.abs(totalSaidaJan));
	   
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 2) {
                	String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
            	    String campoNumero = ("         " + objMovimento.getNumDoc()).substring(objMovimento.getNumDoc().length()); 
            	    String campoQtde = ("          " + qtde).substring(qtde.length());  
            	    String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
            	    String campoVlUnit = ("              " + vUnit).substring(vUnit.length());  
            	    String valor =  String.format("%.2f",objMovimento.getVlLiq());
            	    String campoValor = ("              " + valor).substring(valor.length());  
                	System.out.println(objMovimento.getCodItem() + "|" + objMovimento.getCodItemAnterior() + "|" + objMovimento.getOperacao()
            	    + "|" + objMovimento.getQtde()+ "|" + objMovimento.getSaldoAcumuladoQtde());
            		novasLinhas.add(objMovimento.getDtDoc() + "|" + campoNumero + "|" + objMovimento.getCfop()
            		+ "|" + campoQtde + "|" + campoVlUnit + "|" + campoValor + "|" + "                 |" + "              |"+"              |");
            		
            	}
			}
			novasLinhas.add("________________________________________________________________________________________________________________________");
            Double totalEntradaFev = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 2)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
            
            Double totalVrunitEntradaFev = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 2)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitEntradaFev = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 2)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.count();
            
            Double vrUnitTotaisEntFev = 0.0;
            if(qtdeTotalVrunitEntradaFev > 0) {
            	vrUnitTotaisEntFev = totalVrunitEntradaFev/qtdeTotalVrunitEntradaFev;
            	
            }
            
            Double valorTotaisEntFev = totalEntradaFev * vrUnitTotaisEntFev;
            
			Double totalSaidaFev = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 2)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
            Double totalVrunitSaidaFev = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 2)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitSaidaFev = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 2)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.count();
			
			Double vrUnitTotaisSaidaFev = 0.0;
            if(qtdeTotalVrunitSaidaFev > 0) {
            	vrUnitTotaisSaidaFev = totalVrunitSaidaFev/qtdeTotalVrunitSaidaFev;
            }
            
            Double valorTotaisSaiFev = totalSaidaFev * vrUnitTotaisSaidaFev;
			
			System.out.println("Total do Mês 02/"   + ano + " Entradas " + Math.abs(totalEntradaFev) + "    " + (saldoJanAnterior+totalEntradaFev)+"|"+"              |");
			System.out.println("Total do Mês 02/" + ano + " Saidas " + Math.abs(totalSaidaFev) + "    " + (saldoJanAnterior+totalEntradaJan-totalSaidaFev));
			
			String totEnt2 = String.valueOf(String.format("%.2f", Math.abs(totalEntradaJan)));
			String campoTotEnt2 = ("      " + totEnt2).substring(totEnt2.length()); 
			
			String totSai2 = String.valueOf(String.format("%.2f", Math.abs(totalSaidaJan)));
			String campoTotSai2 =  ("        " + totSai2).substring(totSai2.length()); 
			
			Double saldoInicialSumTotalEntradaFev = saldoInicial+totalEntradaFev;
			Double saldoInicialSumtotalEntradaFevMenusMathTotalSaidaFev = (saldoInicial+totalEntradaFev)-(Math.abs(totalSaidaFev));
			
			if(vrUnitTotaisEntFev < 1) {
				precoMedioEstoque = precoMedio;
			}else {
				precoMedioEstoque = vrUnitTotaisEntFev;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaFev;
			}
			
			novasLinhas.add("Total do Mês 02/" + ano + " Entradas " + campoTotEnt2 + "|         " + String.format("%.2f", vrUnitTotaisEntFev)   +"|        "+ String.format("%.2f", valorTotaisEntFev) + "|            " +  String.format("%.2f", saldoInicialSumTotalEntradaFev)+"|         "+precoMedioEstoque_+"|       "+String.format("%.2f", valorTotalEstoque)+"|");
			novasLinhas.add("Total do Mês 02/" + ano + " Saidas " + campoTotSai2 +   "|         " + String.format("%.2f", vrUnitTotaisSaidaFev) +"|        "+ String.format("%.2f", Math.abs(valorTotaisSaiFev)) + "|            " +String.format("%.2f",saldoInicialSumtotalEntradaFevMenusMathTotalSaidaFev)+"|"+"              |"+"              |");
				    
			novasLinhas.add("________________________________________________________________________________________________________________________");		
			saldoFevAnterior = (saldoJanAnterior+totalEntradaFev-Math.abs(totalSaidaFev));
			
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 3) {
                	String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
            	    String campoNumero = ("         " + objMovimento.getNumDoc()).substring(objMovimento.getNumDoc().length()); 
            	    String campoQtde = ("          " + qtde).substring(qtde.length());  
            	    String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
            	    String campoVlUnit = ("              " + vUnit).substring(vUnit.length());  
            	    String valor =  String.format("%.2f",objMovimento.getVlLiq());
            	    String campoValor = ("              " + valor).substring(valor.length());  
                	System.out.println(objMovimento.getCodItem() + "|" + objMovimento.getCodItemAnterior() + "|" + objMovimento.getOperacao()
            	    + "|" + objMovimento.getQtde()+ "|" + objMovimento.getSaldoAcumuladoQtde());
            		novasLinhas.add(objMovimento.getDtDoc() + "|" + campoNumero + "|" + objMovimento.getCfop()
            		+ "|" + campoQtde + "|" + campoVlUnit + "|" + campoValor + "|" + "                 |" + "              |"+"              |");
            		
            	}
			}
			novasLinhas.add("________________________________________________________________________________________________________________________");
			Double totalEntradaMar = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 3)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
            Double totalVrunitEntradaMar = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 3)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitEntradaMar = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 3)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.count();
            
            Double vrUnitTotaisEntMar = 0.0;
            if(qtdeTotalVrunitEntradaMar > 0) {
            	vrUnitTotaisEntMar = totalVrunitEntradaMar/qtdeTotalVrunitEntradaMar;
            	
            }
            
            Double valorTotaisEntMar = totalEntradaMar * vrUnitTotaisEntMar;
            
			Double totalSaidaMar = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 3)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
			Double totalVrunitSaidaMar = collect2.stream()
						.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
						.filter(codItem -> codItem.getDtDoc().getMonthValue() == 3)	
						.filter(codItem -> codItem.getOperacao().equals("S"))
						.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
				
            Long qtdeTotalVrunitSaidaMar = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 3)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.count();
			
			Double vrUnitTotaisSaidaMar = 0.0;
            if(qtdeTotalVrunitSaidaMar > 0) {
            	vrUnitTotaisSaidaMar = totalVrunitSaidaMar/qtdeTotalVrunitSaidaMar;
            }
            
            Double valorTotaisSaiMar = totalSaidaMar * vrUnitTotaisSaidaMar;
	            
			System.out.println("Total do Mês 03/"   + ano + " Entradas " + Math.abs(totalEntradaMar) + "    " + (saldoFevAnterior+totalEntradaMar));
			System.out.println("Total do Mês 03/" + ano + " Saidas " + Math.abs(totalSaidaMar) + "    " + (saldoFevAnterior+totalEntradaFev-totalSaidaMar));
			
			String totEnt3 = String.valueOf(String.format("%.2f", Math.abs(totalEntradaMar)));
			String campoTotEnt3 = ("      " + totEnt3).substring(totEnt3.length()); 
			
			String totSai3 = String.valueOf(String.format("%.2f", Math.abs(totalSaidaMar)));
			String campoTotSai3 =  ("        " + totSai2).substring(totSai3.length()); 
			
			Double saldoInicialSumTotalEntradaMar = saldoInicial+totalEntradaMar;
			Double saldoInicialSumtotalEntradaMarMenusMathTotalSaidaMar = (saldoInicial+totalEntradaMar)-(Math.abs(totalSaidaMar));
			
			if(vrUnitTotaisEntMar < 1) {
				precoMedioEstoque = precoMedio;
			}else {
				precoMedioEstoque = vrUnitTotaisEntMar;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaMar;
			}
			
			novasLinhas.add("Total do Mês 03/" + ano + " Entradas " + campoTotEnt3 + "|         " + String.format("%.2f", vrUnitTotaisEntMar)   +"|        "+ String.format("%.2f", valorTotaisEntMar) + "|            " +  String.format("%.2f", saldoInicialSumTotalEntradaMar)+"|         "+precoMedioEstoque_+"|        "+String.format("%.2f", valorTotalEstoque)+"|");
			novasLinhas.add("Total do Mês 03/" + ano + " Saidas " + campoTotSai3 +   "|         " + String.format("%.2f", vrUnitTotaisSaidaMar) +"|        "+ String.format("%.2f", Math.abs(valorTotaisSaiMar)) + "|            " +String.format("%.2f",saldoInicialSumtotalEntradaMarMenusMathTotalSaidaMar)+"|"+"              |"+"              |");
			
		
			novasLinhas.add("________________________________________________________________________________________________________________________");
			saldoMarAnterior = (saldoFevAnterior+totalEntradaMar-Math.abs(totalSaidaMar));
			
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 4) {
                	String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
            	    String campoNumero = ("         " + objMovimento.getNumDoc()).substring(objMovimento.getNumDoc().length()); 
            	    String campoQtde = ("          " + qtde).substring(qtde.length());  
            	    String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
            	    String campoVlUnit = ("              " + vUnit).substring(vUnit.length());  
            	    String valor =  String.format("%.2f",objMovimento.getVlLiq());
            	    String campoValor = ("              " + valor).substring(valor.length());  
                	System.out.println(objMovimento.getCodItem() + "|" + objMovimento.getCodItemAnterior() + "|" + objMovimento.getOperacao()
            	    + "|" + objMovimento.getQtde()+ "|" + objMovimento.getSaldoAcumuladoQtde());
            		novasLinhas.add(objMovimento.getDtDoc() + "|" + campoNumero + "|" + objMovimento.getCfop()
            		+ "|" + campoQtde + "|" + campoVlUnit + "|" + campoValor + "|" + "                 |" + "              |"+"              |");
            	}
			}
			novasLinhas.add("________________________________________________________________________________________________________________________");
			Double totalEntradaAbr = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 4)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
            Double totalVrunitEntradaAbr = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 4)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitEntradaAbr = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 4)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.count();
			
            
            Double vrUnitTotaisEntAbr = 0.0;
            if(qtdeTotalVrunitEntradaAbr > 0) {
            	vrUnitTotaisEntAbr = totalVrunitEntradaAbr/qtdeTotalVrunitEntradaAbr;
            	
            }
            
            Double valorTotaisEntAbr = totalEntradaAbr * vrUnitTotaisEntAbr;
            
			Double totalSaidaAbr = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 4)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
			Double totalVrunitSaidaAbr = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 4)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitSaidaAbr = collect2.stream()
				.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
				.filter(codItem -> codItem.getDtDoc().getMonthValue() == 4)	
				.filter(codItem -> codItem.getOperacao().equals("S"))
				.count();
			
			Double vrUnitTotaisSaidaAbr = 0.0;
            if(qtdeTotalVrunitSaidaAbr > 0) {
            	vrUnitTotaisSaidaAbr = totalVrunitSaidaAbr/qtdeTotalVrunitSaidaAbr;
            }
            
            Double valorTotaisSaiAbr = totalSaidaAbr * vrUnitTotaisSaidaAbr;
            
            
			System.out.println("Total do Mês 04/"   + ano + " Entradas " + Math.abs(totalEntradaAbr) + "    " + (saldoMarAnterior+totalEntradaAbr));
			System.out.println("Total do Mês 04/" + ano + " Saidas " + Math.abs(totalSaidaAbr) + "    " + (saldoMarAnterior+totalEntradaFev-totalSaidaAbr));
			if(saldoMarAnterior < 0) {
				saldoMarAnterior = 0.0;
			}
			
			String totEnt4 = String.valueOf(String.format("%.2f", Math.abs(totalEntradaAbr)));
			String campoTotEnt4 = ("      " + totEnt4).substring(totEnt4.length()); 
			
			String totSai4 = String.valueOf(String.format("%.2f", Math.abs(totalSaidaAbr)));
			String campoTotSai4 =  ("        " + totSai4).substring(totSai4.length()); 
			
			Double saldoInicialSumTotalEntradaAbr = saldoInicial+totalEntradaAbr;
			Double saldoInicialSumtotalEntradaAbrMenusMathTotalSaidaAbr = (saldoInicial+totalEntradaAbr)-(Math.abs(totalSaidaAbr));
			
			if(vrUnitTotaisEntAbr < 1) {
				precoMedioEstoque = precoMedio;
			}else {
				precoMedioEstoque = vrUnitTotaisEntAbr;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaAbr;
			}
			
			novasLinhas.add("Total do Mês 04/" + ano + " Entradas " + campoTotEnt4 + "|         " + String.format("%.2f", vrUnitTotaisEntAbr)   +"|        "+ String.format("%.2f", valorTotaisEntAbr) + "|            " +  String.format("%.2f", saldoInicialSumTotalEntradaAbr)+"|         "+precoMedioEstoque_+"|       "+String.format("%.2f", valorTotalEstoque)+"|");
			novasLinhas.add("Total do Mês 04/" + ano + " Saidas " + campoTotSai4 +   "|         " + String.format("%.2f", vrUnitTotaisSaidaAbr) +"|        "+ String.format("%.2f", Math.abs(valorTotaisSaiAbr)) + "|            " +String.format("%.2f",saldoInicialSumtotalEntradaAbrMenusMathTotalSaidaAbr)+"|"+"              |"+"              |");
					
			novasLinhas.add("________________________________________________________________________________________________________________________");
			saldoAbrAnterior = (saldoMarAnterior+totalEntradaAbr-Math.abs(totalSaidaAbr));
			
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 5) {
                	String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
            	    String campoNumero = ("         " + objMovimento.getNumDoc()).substring(objMovimento.getNumDoc().length()); 
            	    String campoQtde = ("          " + qtde).substring(qtde.length());  
            	    String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
            	    String campoVlUnit = ("              " + vUnit).substring(vUnit.length());  
            	    String valor =  String.format("%.2f",objMovimento.getVlLiq());
            	    String campoValor = ("              " + valor).substring(valor.length());  
                	System.out.println(objMovimento.getCodItem() + "|" + objMovimento.getCodItemAnterior() + "|" + objMovimento.getOperacao()
            	    + "|" + objMovimento.getQtde()+ "|" + objMovimento.getSaldoAcumuladoQtde());
            		novasLinhas.add(objMovimento.getDtDoc() + "|" + campoNumero + "|" + objMovimento.getCfop()
            		+ "|" + campoQtde + "|" + campoVlUnit + "|" + campoValor + "|" + "                 |" + "              |"+"              |");
            	}
			}
			novasLinhas.add("________________________________________________________________________________________________________________________");
			Double totalEntradaMai = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 5)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
            Double totalVrunitEntradaMai = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 5)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitEntradaMai = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 5)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.count();
            
            Double vrUnitTotaisEntMai = 0.0;
            if(qtdeTotalVrunitEntradaMai > 0) {
            	vrUnitTotaisEntMai = totalVrunitEntradaMai/qtdeTotalVrunitEntradaMai;	
            }
            
            Double valorTotaisEntMai = totalEntradaMai * vrUnitTotaisEntMai;
            
            
			Double totalSaidaMai = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 5)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
			Double totalVrunitSaidaMai = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 5)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitSaidaMai = collect2.stream()
				.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
				.filter(codItem -> codItem.getDtDoc().getMonthValue() == 5)	
				.filter(codItem -> codItem.getOperacao().equals("S"))
				.count();
			
			Double vrUnitTotaisSaidaMai = 0.0;
            if(qtdeTotalVrunitSaidaMai > 0) {
            	vrUnitTotaisSaidaMai = totalVrunitSaidaMai/qtdeTotalVrunitSaidaMai;
            }
            
            Double valorTotaisSaiMai = totalSaidaMai * vrUnitTotaisSaidaMai;
			
			System.out.println("Total do Mês 05/"   + ano + " Entradas " + Math.abs(totalEntradaMai) + "    " + (saldoAbrAnterior+totalEntradaMai));
			System.out.println("Total do Mês 05/" + ano + " Saidas " + Math.abs(totalSaidaMai) + "    " + (saldoAbrAnterior+totalEntradaMai-totalSaidaMai));
			if(saldoAbrAnterior < 0) {
				saldoAbrAnterior = 0.0;
			}

			String totEnt5 = String.valueOf(String.format("%.2f", Math.abs(totalEntradaMai)));
			String campoTotEnt5 = ("      " + totEnt5).substring(totEnt5.length()); 
			
			String totSai5 = String.valueOf(String.format("%.2f", Math.abs(totalSaidaMai)));
			String campoTotSai5 =  ("        " + totSai5).substring(totSai5.length()); 
			
			Double saldoInicialSumTotalEntradaMai = saldoInicial+totalEntradaMai;
			Double saldoInicialSumtotalEntradaMaiMenusMathTotalSaidaMai = (saldoInicial+totalEntradaMai)-(Math.abs(totalSaidaMai));
			
			if(vrUnitTotaisEntMai < 1) {
				precoMedioEstoque = precoMedio;
			}else {
				precoMedioEstoque = vrUnitTotaisEntMai;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaMai;
			}
			
			novasLinhas.add("Total do Mês 05/" + ano + " Entradas " + campoTotEnt5 + "|         " + String.format("%.2f", vrUnitTotaisEntMai)   +"|        "+ String.format("%.2f", valorTotaisEntMai) + "|            " +  String.format("%.2f", saldoInicialSumTotalEntradaMai)+"|         "+precoMedioEstoque_+"|       "+String.format("%.2f", valorTotalEstoque)+"|");
			novasLinhas.add("Total do Mês 05/" + ano + " Saidas " + campoTotSai5 +   "|         " + String.format("%.2f", vrUnitTotaisSaidaMai) +"|        "+ String.format("%.2f", Math.abs(valorTotaisSaiMai)) + "|            " +String.format("%.2f",saldoInicialSumtotalEntradaMaiMenusMathTotalSaidaMai)+"|"+"              |"+"              |");
						
			novasLinhas.add("________________________________________________________________________________________________________________________");
			saldoMaiAnterior =  (saldoAbrAnterior+totalEntradaMai-Math.abs(totalSaidaMai));
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 6) {
                	String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
            	    String campoNumero = ("         " + objMovimento.getNumDoc()).substring(objMovimento.getNumDoc().length()); 
            	    String campoQtde = ("          " + qtde).substring(qtde.length());  
            	    String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
            	    String campoVlUnit = ("              " + vUnit).substring(vUnit.length());  
            	    String valor =  String.format("%.2f",objMovimento.getVlLiq());
            	    String campoValor = ("              " + valor).substring(valor.length());  
                	System.out.println(objMovimento.getCodItem() + "|" + objMovimento.getCodItemAnterior() + "|" + objMovimento.getOperacao()
            	    + "|" + objMovimento.getQtde()+ "|" + objMovimento.getSaldoAcumuladoQtde());
            		novasLinhas.add(objMovimento.getDtDoc() + "|" + campoNumero + "|" + objMovimento.getCfop()
            		+ "|" + campoQtde + "|" + campoVlUnit + "|" + campoValor + "|" + "                 |" + "              |"+"              |");
            	}
			}
			novasLinhas.add("________________________________________________________________________________________________________________________");
			Double totalEntradaJun = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 6)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
            Double totalVrunitEntradaJun = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 6)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitEntradaJun = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 6)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.count();
			
            Double vrUnitTotaisEntJun = 0.0;
            if(qtdeTotalVrunitEntradaJun > 0) {
            	vrUnitTotaisEntJun = totalVrunitEntradaJun/qtdeTotalVrunitEntradaJun;	
            }
            
            Double valorTotaisEntJun = totalEntradaJun * vrUnitTotaisEntJun;
            
			Double totalSaidaJun = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 6)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
			Double totalVrunitSaidaJun = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 6)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitSaidaJun = collect2.stream()
				.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
				.filter(codItem -> codItem.getDtDoc().getMonthValue() == 6)	
				.filter(codItem -> codItem.getOperacao().equals("S"))
				.count();
			
			Double vrUnitTotaisSaidaJun = 0.0;
            if(qtdeTotalVrunitSaidaJun > 0) {
            	vrUnitTotaisSaidaJun = totalVrunitSaidaJun/qtdeTotalVrunitSaidaJun;
            }
            
            Double valorTotaisSaiJun = totalSaidaJun * vrUnitTotaisSaidaJun;
			
			System.out.println("Total do Mês 06/"   + ano + " Entradas " + Math.abs(totalEntradaJun) + "    " + (saldoMaiAnterior+totalEntradaJun));
			System.out.println("Total do Mês 06/" + ano + " Saidas " + Math.abs(totalSaidaJun) + "    " + (saldoMaiAnterior+totalEntradaMar-totalSaidaJun));
			
			if(saldoMaiAnterior < 0) {
				saldoMaiAnterior = 0.0;
			}

			String totEnt6 = String.valueOf(String.format("%.2f", Math.abs(totalEntradaJun)));
			String campoTotEnt6 = ("      " + totEnt6).substring(totEnt6.length()); 
			
			String totSai6 = String.valueOf(String.format("%.2f", Math.abs(totalSaidaJun)));
			String campoTotSai6 =  ("        " + totSai6).substring(totSai6.length()); 
			
			Double saldoInicialSumTotalEntradaJun = saldoInicial+totalEntradaJun;
			Double saldoInicialSumtotalEntradaJunMenusMathTotalSaidaJun = (saldoInicial+totalEntradaJun)-(Math.abs(totalSaidaJun));
			
			if(vrUnitTotaisEntJun < 1) {
				precoMedioEstoque = precoMedio;
			}else {
				precoMedioEstoque = vrUnitTotaisEntJun;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaJun;
			}
			
			novasLinhas.add("Total do Mês 06/" + ano + " Entradas " + campoTotEnt6 + "|          " + String.format("%.2f", vrUnitTotaisEntJun)   +"|          "+ String.format("%.2f", valorTotaisEntJun) + "|            " +  String.format("%.2f", saldoInicialSumTotalEntradaJun)+"|         "+precoMedioEstoque_+"|        "+String.format("%.2f", valorTotalEstoque)+"|");
			novasLinhas.add("Total do Mês 06/" + ano + " Saidas " + campoTotSai6 +   "|         " + String.format("%.2f", vrUnitTotaisSaidaJun) +"|        "+ String.format("%.2f", Math.abs(valorTotaisSaiJun)) + "|             " +String.format("%.2f",saldoInicialSumtotalEntradaJunMenusMathTotalSaidaJun)+"|"+"              |"+"              |");		    
			
			novasLinhas.add("________________________________________________________________________________________________________________________");	
			saldoJunAnterior = (saldoMaiAnterior+totalEntradaJun-Math.abs(totalSaidaJun));
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 7) {
                	String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
            	    String campoNumero = ("         " + objMovimento.getNumDoc()).substring(objMovimento.getNumDoc().length()); 
            	    String campoQtde = ("          " + qtde).substring(qtde.length());  
            	    String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
            	    String campoVlUnit = ("              " + vUnit).substring(vUnit.length());  
            	    String valor =  String.format("%.2f",objMovimento.getVlLiq());
            	    String campoValor = ("              " + valor).substring(valor.length());  
                	System.out.println(objMovimento.getCodItem() + "|" + objMovimento.getCodItemAnterior() + "|" + objMovimento.getOperacao()
            	    + "|" + objMovimento.getQtde()+ "|" + objMovimento.getSaldoAcumuladoQtde());
            		novasLinhas.add(objMovimento.getDtDoc() + "|" + campoNumero + "|" + objMovimento.getCfop()
            		+ "|" + campoQtde + "|" + campoVlUnit + "|" + campoValor + "|" + "                 |" + "              |"+"              |");
            	}
			}
			novasLinhas.add("________________________________________________________________________________________________________________________");
			Double totalEntradaJul = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 7)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
            Double totalVrunitEntradaJul = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 7)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitEntradaJul = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 7)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.count();
            
            Double vrUnitTotaisEntJul = 0.0;
            if(qtdeTotalVrunitEntradaJul > 0) {
            	vrUnitTotaisEntJul = totalVrunitEntradaJul/qtdeTotalVrunitEntradaJul;	
            }
            
            Double valorTotaisEntJul = totalEntradaJul * vrUnitTotaisEntJul;
			
			Double totalSaidaJul = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 7)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
			Double totalVrunitSaidaJul = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 7)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitSaidaJul = collect2.stream()
				.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
				.filter(codItem -> codItem.getDtDoc().getMonthValue() == 7)	
				.filter(codItem -> codItem.getOperacao().equals("S"))
				.count();
			
			Double vrUnitTotaisSaidaJul = 0.0;
            if(qtdeTotalVrunitSaidaJul > 0) {
            	vrUnitTotaisSaidaJul = totalVrunitSaidaJul/qtdeTotalVrunitSaidaJul;
            }
            
            Double valorTotaisSaiJul = totalSaidaJul * vrUnitTotaisSaidaJul;
			
			System.out.println("Total do Mês 07/"   + ano + " Entradas " + Math.abs(totalEntradaJul) + "    " + (saldoJunAnterior+totalEntradaJul));
			System.out.println("Total do Mês 07/" + ano + " Saidas " + Math.abs(totalSaidaJul) + "    " + (saldoJunAnterior+totalEntradaMar-totalSaidaJul));
			if(saldoJunAnterior < 0) {
				saldoJunAnterior = 0.0;
			}
			
			String totEnt7 = String.valueOf(String.format("%.2f", Math.abs(totalEntradaJul)));
			String campoTotEnt7 = ("      " + totEnt7).substring(totEnt5.length()); 
			
			String totSai7 = String.valueOf(String.format("%.2f", Math.abs(totalSaidaJul)));
			String campoTotSai7 =  ("        " + totSai7).substring(totSai7.length()); 
			
			Double saldoInicialSumTotalEntradaJul = saldoInicial+totalEntradaJul;
			Double saldoInicialSumtotalEntradaJulMenusMathTotalSaidaJul = (saldoInicial+totalEntradaJul)-(Math.abs(totalSaidaJul));
			
			if(vrUnitTotaisEntJul < 1) {
				precoMedioEstoque = precoMedio;
			}else {
				precoMedioEstoque = vrUnitTotaisEntJul;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaJul;
			}
			
			novasLinhas.add("Total do Mês 07/" + ano + " Entradas " + campoTotEnt7 + "|         " + String.format("%.2f", vrUnitTotaisEntJul)   +"|        "+ String.format("%.2f", valorTotaisEntJul) + "|            " +  String.format("%.2f", saldoInicialSumTotalEntradaJul)+"|         "+precoMedioEstoque_+"|       "+String.format("%.2f", valorTotalEstoque)+"|");
			novasLinhas.add("Total do Mês 07/" + ano + " Saidas " + campoTotSai7 +   "|         " + String.format("%.2f", vrUnitTotaisSaidaJul) +"|         "+ String.format("%.2f", Math.abs(valorTotaisSaiJul)) + "|            " +String.format("%.2f",saldoInicialSumtotalEntradaJulMenusMathTotalSaidaJul)+"|"+"              |"+"              |");
			
			novasLinhas.add("________________________________________________________________________________________________________________________");
			saldoJulAnterior =  (saldoJunAnterior+totalEntradaJul-Math.abs(totalSaidaJul));
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 8) {
                	String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
            	    String campoNumero = ("         " + objMovimento.getNumDoc()).substring(objMovimento.getNumDoc().length()); 
            	    String campoQtde = ("          " + qtde).substring(qtde.length());  
            	    String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
            	    String campoVlUnit = ("              " + vUnit).substring(vUnit.length());  
            	    String valor =  String.format("%.2f",objMovimento.getVlLiq());
            	    String campoValor = ("              " + valor).substring(valor.length());  
                	System.out.println(objMovimento.getCodItem() + "|" + objMovimento.getCodItemAnterior() + "|" + objMovimento.getOperacao()
            	    + "|" + objMovimento.getQtde()+ "|" + objMovimento.getSaldoAcumuladoQtde());
            		novasLinhas.add(objMovimento.getDtDoc() + "|" + campoNumero + "|" + objMovimento.getCfop()
            		+ "|" + campoQtde + "|" + campoVlUnit + "|" + campoValor + "|" + "                 |" + "              |"+"              |");
            	}
			}
			novasLinhas.add("________________________________________________________________________________________________________________________");
			Double totalEntradaAgo = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 8)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
            Double totalVrunitEntradaAgo = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 8)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitEntradaAgo = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 8)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.count();
			
            Double vrUnitTotaisEntAgo = 0.0;
            if(qtdeTotalVrunitEntradaAgo > 0) {
            	vrUnitTotaisEntAgo = totalVrunitEntradaAgo/qtdeTotalVrunitEntradaAgo;	
            }
            
            Double valorTotaisEntAgo = totalEntradaAgo * vrUnitTotaisEntAgo;
            
			Double totalSaidaAgo = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 8)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
			Double totalVrunitSaidaAgo = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 8)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitSaidaAgo = collect2.stream()
				.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
				.filter(codItem -> codItem.getDtDoc().getMonthValue() == 8)	
				.filter(codItem -> codItem.getOperacao().equals("S"))
				.count();
			
			Double vrUnitTotaisSaidaAgo = 0.0;
            if(qtdeTotalVrunitSaidaAgo > 0) {
            	vrUnitTotaisSaidaAgo = totalVrunitSaidaAgo/qtdeTotalVrunitSaidaAgo;
            }
            
            Double valorTotaisSaiAgo = totalSaidaAgo * vrUnitTotaisSaidaAgo;
			
			System.out.println("Total do Mês 08/"   + ano + " Entradas " + Math.abs(totalEntradaAgo) + "    " + (saldoJulAnterior+totalEntradaAgo));
			System.out.println("Total do Mês 08/" + ano + " Saidas " + Math.abs(totalSaidaAgo) + "    " + (saldoJulAnterior+totalEntradaAgo-totalSaidaAgo));
			if(saldoJulAnterior < 0) {
				saldoJulAnterior = 0.0;
			}
			
			String totEnt8 = String.valueOf(String.format("%.2f", Math.abs(totalEntradaAgo)));
			String campoTotEnt8 = ("      " + totEnt8).substring(totEnt8.length()); 
			
			String totSai8 = String.valueOf(String.format("%.2f", Math.abs(totalSaidaAgo)));
			String campoTotSai8 =  ("        " + totSai8).substring(totSai8.length()); 
			
			Double saldoInicialSumTotalEntradaAgo = saldoInicial+totalEntradaAgo;
			Double saldoInicialSumtotalEntradaAgoMenusMathTotalSaidaAgo = (saldoInicial+totalEntradaAgo)-(Math.abs(totalSaidaAgo));
			
			if(vrUnitTotaisEntAgo < 1) {
				precoMedioEstoque = precoMedio;
			}else {
				precoMedioEstoque = vrUnitTotaisEntAgo;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaAgo;
			}
			
			novasLinhas.add("Total do Mês 08/" + ano + " Entradas " + campoTotEnt8 + "|          " + String.format("%.2f", vrUnitTotaisEntAgo)   +"|          "+ String.format("%.2f", valorTotaisEntAgo) + "|            " +  String.format("%.2f", saldoInicialSumTotalEntradaAgo)+"|         "+precoMedioEstoque_+"|        "+String.format("%.2f", valorTotalEstoque)+"|");
			novasLinhas.add("Total do Mês 08/" + ano + " Saidas " + campoTotSai8 +   "|         " + String.format("%.2f", vrUnitTotaisSaidaAgo) +"|        "+ String.format("%.2f", Math.abs(valorTotaisSaiAgo)) + "|            " +String.format("%.2f",saldoInicialSumtotalEntradaAgoMenusMathTotalSaidaAgo)+"|"+"              |"+"              |");
			
			novasLinhas.add("________________________________________________________________________________________________________________________");
			saldoAgoAnterior = (saldoJulAnterior+totalEntradaAgo-Math.abs(totalSaidaAgo));
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 9) {
                	String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
            	    String campoNumero = ("         " + objMovimento.getNumDoc()).substring(objMovimento.getNumDoc().length()); 
            	    String campoQtde = ("          " + qtde).substring(qtde.length());  
            	    String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
            	    String campoVlUnit = ("              " + vUnit).substring(vUnit.length());  
            	    String valor =  String.format("%.2f",objMovimento.getVlLiq());
            	    String campoValor = ("              " + valor).substring(valor.length());  
                	System.out.println(objMovimento.getCodItem() + "|" + objMovimento.getCodItemAnterior() + "|" + objMovimento.getOperacao()
            	    + "|" + objMovimento.getQtde()+ "|" + objMovimento.getSaldoAcumuladoQtde());
            		novasLinhas.add(objMovimento.getDtDoc() + "|" + campoNumero + "|" + objMovimento.getCfop()
            		+ "|" + campoQtde + "|" + campoVlUnit + "|" + campoValor + "|" + "                 |" + "              |"+"              |");
            	}
			}
			novasLinhas.add("________________________________________________________________________________________________________________________");
			Double totalEntradaSet = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 9)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
            Double totalVrunitEntradaSet = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 9)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitEntradaSet = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 9)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.count();
			
            Double vrUnitTotaisEntSet = 0.0;
            if(qtdeTotalVrunitEntradaSet > 0) {
            	vrUnitTotaisEntSet = totalVrunitEntradaSet/qtdeTotalVrunitEntradaSet;	
            }
            
            Double valorTotaisEntSet = totalEntradaSet * vrUnitTotaisEntSet;
            
			Double totalSaidaSet = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 9)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
			Double totalVrunitSaidaSet = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 9)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitSaidaSet = collect2.stream()
				.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
				.filter(codItem -> codItem.getDtDoc().getMonthValue() == 9)	
				.filter(codItem -> codItem.getOperacao().equals("S"))
				.count();
			
			Double vrUnitTotaisSaidaSet = 0.0;
            if(qtdeTotalVrunitSaidaSet > 0) {
            	vrUnitTotaisSaidaSet = totalVrunitSaidaSet/qtdeTotalVrunitSaidaSet;
            }
            
            Double valorTotaisSaiSet = totalSaidaSet * vrUnitTotaisSaidaSet;
            
            
			System.out.println("Total do Mês 09/"   + ano + " Entradas " + Math.abs(totalEntradaSet) + "    " + (saldoJulAnterior+totalEntradaSet));
			System.out.println("Total do Mês 09/" + ano + " Saidas " + Math.abs(totalSaidaAgo) + "    " + (saldoJulAnterior+totalEntradaAgo-totalSaidaSet));
			if(saldoAgoAnterior < 0) {
				saldoAgoAnterior = 0.0;
			}
			
			String totEnt9 = String.valueOf(String.format("%.2f", Math.abs(totalEntradaSet)));
			String campoTotEnt9 = ("      " + totEnt9).substring(totEnt9.length()); 
			
			String totSai9 = String.valueOf(String.format("%.2f", Math.abs(totalSaidaSet)));
			String campoTotSai9 =  ("       " + totSai4).substring(totSai9.length()); 
			
			Double saldoInicialSumTotalEntradaSet = saldoInicial+totalEntradaSet;
			Double saldoInicialSumtotalEntradaSetMenusMathTotalSaidaSet = (saldoInicial+totalEntradaSet)-(Math.abs(totalSaidaSet));
			
			if(vrUnitTotaisEntSet < 1) {
				precoMedioEstoque = precoMedio;
			}else {
				precoMedioEstoque = vrUnitTotaisEntSet;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaSet;
			}
			
			novasLinhas.add("Total do Mês 09/" + ano + " Entradas " + campoTotEnt9 + "|         " + String.format("%.2f", vrUnitTotaisEntSet)   +"|        "+ String.format("%.2f", valorTotaisEntSet) + "|            " +  String.format("%.2f", saldoInicialSumTotalEntradaSet)+"|         "+precoMedioEstoque_+"|       "+String.format("%.2f", valorTotalEstoque)+"|");
			novasLinhas.add("Total do Mês 09/" + ano + " Saidas " + campoTotSai9 +   "|         " + String.format("%.2f", vrUnitTotaisSaidaSet) +"|        "+ String.format("%.2f", Math.abs(valorTotaisSaiSet)) + "|            " +String.format("%.2f",saldoInicialSumtotalEntradaSetMenusMathTotalSaidaSet)+"|"+"              |"+"              |");
			
			novasLinhas.add("________________________________________________________________________________________________________________________");
			saldoSetAnterior = (saldoAgoAnterior+totalEntradaSet-Math.abs(totalSaidaSet));
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 10) {
                	String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
            	    String campoNumero = ("         " + objMovimento.getNumDoc()).substring(objMovimento.getNumDoc().length()); 
            	    String campoQtde = ("          " + qtde).substring(qtde.length());  
            	    String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
            	    String campoVlUnit = ("              " + vUnit).substring(vUnit.length());  
            	    String valor =  String.format("%.2f",objMovimento.getVlLiq());
            	    String campoValor = ("              " + valor).substring(valor.length());  
                	System.out.println(objMovimento.getCodItem() + "|" + objMovimento.getCodItemAnterior() + "|" + objMovimento.getOperacao()
            	    + "|" + objMovimento.getQtde()+ "|" + objMovimento.getSaldoAcumuladoQtde());
            		novasLinhas.add(objMovimento.getDtDoc() + "|" + campoNumero + "|" + objMovimento.getCfop()
            		+ "|" + campoQtde + "|" + campoVlUnit + "|" + campoValor + "|" + "                 |" + "              |"+"              |");
            	}
			}
			novasLinhas.add("________________________________________________________________________________________________________________________");		
			Double totalEntradaOut = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 10)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
            Double totalVrunitEntradaOut = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 10)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitEntradaOut = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 10)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.count();
			
            Double vrUnitTotaisEntOut = 0.0;
            if(qtdeTotalVrunitEntradaOut > 0) {
            	vrUnitTotaisEntOut = totalVrunitEntradaOut/qtdeTotalVrunitEntradaOut;	
            }
            
            Double valorTotaisEntOut = totalEntradaOut * vrUnitTotaisEntOut;
            
			Double totalSaidaOut = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 10)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
			Double totalVrunitSaidaOut = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 10)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitSaidaOut = collect2.stream()
				.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
				.filter(codItem -> codItem.getDtDoc().getMonthValue() == 10)	
				.filter(codItem -> codItem.getOperacao().equals("S"))
				.count();
			
			Double vrUnitTotaisSaidaOut = 0.0;
            if(qtdeTotalVrunitSaidaOut > 0) {
            	vrUnitTotaisSaidaOut = totalVrunitSaidaOut/qtdeTotalVrunitSaidaOut;
            }
            
            Double valorTotaisSaiOut = totalSaidaOut * vrUnitTotaisSaidaOut;
			
			System.out.println("Total do Mês 10/"   + ano + " Entradas " + Math.abs(totalEntradaOut) + "    " + (saldoJulAnterior+totalEntradaOut));
			System.out.println("Total do Mês 10/" + ano + " Saidas " + Math.abs(totalSaidaOut) + "    " + (saldoJulAnterior+totalEntradaAgo-totalSaidaOut));
			if(saldoSetAnterior < 0) {
				saldoSetAnterior = 0.0;
			}

			String totEnt10 = String.valueOf(String.format("%.2f", Math.abs(totalEntradaOut)));
			String campoTotEnt10 = ("      " + totEnt10).substring(totEnt10.length()); 
			
			String totSai10 = String.valueOf(String.format("%.2f", Math.abs(totalSaidaOut)));
			String campoTotSai10 =  ("        " + totSai10).substring(totSai10.length()); 
			
			Double saldoInicialSumTotalEntradaOut = saldoInicial+totalEntradaOut;
			Double saldoInicialSumtotalEntradaOutMenusMathTotalSaidaOut = (saldoInicial+totalEntradaOut)-(Math.abs(totalSaidaOut));
			
			if(vrUnitTotaisEntOut < 1) {
				precoMedioEstoque = precoMedio;
			}else {
				precoMedioEstoque = vrUnitTotaisEntOut;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaOut;
			}
			
			novasLinhas.add("Total do Mês 10/" + ano + " Entradas " + campoTotEnt10 + "|          " + String.format("%.2f", vrUnitTotaisEntOut)   +"|          "+ String.format("%.2f", valorTotaisEntOut) + "|            " +  String.format("%.2f", saldoInicialSumTotalEntradaOut)+"|         "+precoMedioEstoque_+"|        "+String.format("%.2f", valorTotalEstoque)+"|");
			novasLinhas.add("Total do Mês 10/" + ano + " Saidas " + campoTotSai10 +   "|         " + String.format("%.2f", vrUnitTotaisSaidaOut) +"|        "+ String.format("%.2f", Math.abs(valorTotaisSaiOut)) + "|            " +String.format("%.2f",saldoInicialSumtotalEntradaOutMenusMathTotalSaidaOut)+"|"+"              |"+"              |");

			
			novasLinhas.add("________________________________________________________________________________________________________________________");
			saldoOutAnterior = (saldoSetAnterior+totalEntradaOut-Math.abs(totalSaidaOut));
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 11) {
                	String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
            	    String campoNumero = ("         " + objMovimento.getNumDoc()).substring(objMovimento.getNumDoc().length()); 
            	    String campoQtde = ("          " + qtde).substring(qtde.length());  
            	    String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
            	    String campoVlUnit = ("              " + vUnit).substring(vUnit.length());  
            	    String valor =  String.format("%.2f",objMovimento.getVlLiq());
            	    String campoValor = ("              " + valor).substring(valor.length());  
                	System.out.println(objMovimento.getCodItem() + "|" + objMovimento.getCodItemAnterior() + "|" + objMovimento.getOperacao()
            	    + "|" + objMovimento.getQtde()+ "|" + objMovimento.getSaldoAcumuladoQtde());
            		novasLinhas.add(objMovimento.getDtDoc() + "|" + campoNumero + "|" + objMovimento.getCfop()
            		+ "|" + campoQtde + "|" + campoVlUnit + "|" + campoValor + "|" + "                 |" + "              |"+"              |");
            	}
			}
			novasLinhas.add("________________________________________________________________________________________________________________________");		
			Double totalEntradaNov = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 11)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
            Double totalVrunitEntradaNov = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 11)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitEntradaNov = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 11)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.count();
			
            Double vrUnitTotaisEntNov = 0.0;
            if(qtdeTotalVrunitEntradaNov > 0) {
            	vrUnitTotaisEntNov = totalVrunitEntradaNov/qtdeTotalVrunitEntradaNov;	
            }
            
            Double valorTotaisEntNov = totalEntradaNov * vrUnitTotaisEntNov;
            
			Double totalSaidaNov = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 11)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
			Double totalVrunitSaidaNov = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 11)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitSaidaNov = collect2.stream()
				.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
				.filter(codItem -> codItem.getDtDoc().getMonthValue() == 11)	
				.filter(codItem -> codItem.getOperacao().equals("S"))
				.count();
			
			Double vrUnitTotaisSaidaNov = 0.0;
            if(qtdeTotalVrunitSaidaNov > 0) {
            	vrUnitTotaisSaidaNov = totalVrunitSaidaNov/qtdeTotalVrunitSaidaNov;
            }
            
            Double valorTotaisSaiNov = totalSaidaNov * vrUnitTotaisSaidaNov;
            
			System.out.println("Total do Mês 11/"   + ano + " Entradas " + Math.abs(totalEntradaNov) + "    " + (saldoOutAnterior+totalEntradaNov));
			System.out.println("Total do Mês 11/" + ano + " Saidas " + Math.abs(totalSaidaNov) + "    " + (saldoJulAnterior+totalEntradaAgo-totalSaidaNov));
			if(saldoOutAnterior < 0) {
				saldoOutAnterior = 0.0;
			}

			String totEnt11 = String.valueOf(String.format("%.2f", Math.abs(totalEntradaNov)));
			String campoTotEnt11 = ("      " + totEnt11).substring(totEnt11.length()); 
			
			String totSai11 = String.valueOf(String.format("%.2f", Math.abs(totalSaidaNov)));
			String campoTotSai11 =  ("        " + totSai11).substring(totSai11.length()); 
			
			Double saldoInicialSumTotalEntradaNov = saldoInicial+totalEntradaNov;
			Double saldoInicialSumtotalEntradaNovMenusMathTotalSaidaNov = (saldoInicial+totalEntradaNov)-(Math.abs(totalSaidaNov));
			
			if(vrUnitTotaisEntNov < 1) {
				precoMedioEstoque = precoMedio;
			}else {
				precoMedioEstoque = vrUnitTotaisEntNov;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaNov;
			}
			
			novasLinhas.add("Total do Mês 11/" + ano + " Entradas " + campoTotEnt11 + "|          " + String.format("%.2f", vrUnitTotaisEntNov)   +"|          "+ String.format("%.2f", valorTotaisEntNov) + "|            " +  String.format("%.2f", saldoInicialSumTotalEntradaNov)+"|         "+precoMedioEstoque_+"|        "+String.format("%.2f", valorTotalEstoque)+"|");
			novasLinhas.add("Total do Mês 11/" + ano + " Saidas " + campoTotSai11 +   "|         " + String.format("%.2f", vrUnitTotaisSaidaNov) +"|        "+ String.format("%.2f", Math.abs(valorTotaisSaiNov)) + "|            " +String.format("%.2f",saldoInicialSumtotalEntradaNovMenusMathTotalSaidaNov)+"|"+"              |"+"              |");
			
			novasLinhas.add("________________________________________________________________________________________________________________________");	
			saldoNovAnterior = (saldoOutAnterior+totalEntradaOut-Math.abs(totalSaidaNov));
			for(HistoricoItemModel objMovimento : collect2){
				if(objMovimento.getDtDoc().getYear() == ano) {
	            	if(objMovimento.getDtDoc().getMonthValue() == 12) {
	                	String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
	            	    String campoNumero = ("         " + objMovimento.getNumDoc()).substring(objMovimento.getNumDoc().length()); 
	            	    String campoQtde = ("          " + qtde).substring(qtde.length());  
	            	    String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
	            	    String campoVlUnit = ("              " + vUnit).substring(vUnit.length());  
	            	    String valor =  String.format("%.2f",objMovimento.getVlLiq());
	            	    String campoValor = ("              " + valor).substring(valor.length());  
	                	System.out.println(objMovimento.getCodItem() + "|" + objMovimento.getCodItemAnterior() + "|" + objMovimento.getOperacao()
	            	    + "|" + objMovimento.getQtde()+ "|" + objMovimento.getSaldoAcumuladoQtde());
	            		novasLinhas.add(objMovimento.getDtDoc() + "|" + campoNumero + "|" + objMovimento.getCfop()
	            		+ "|" + campoQtde + "|" + campoVlUnit + "|" + campoValor + "|" + "                 |" + "              |"+"              |");
	            	}
				}

			}
			novasLinhas.add("________________________________________________________________________________________________________________________");	
			Double totalEntradaDez = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 12)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
            Double totalVrunitEntradaDez = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 12)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitEntradaDez = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 12)	
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.count();
			
            Double vrUnitTotaisEntDez = 0.0;
            if(qtdeTotalVrunitEntradaDez > 0) {
            	vrUnitTotaisEntDez = totalVrunitEntradaDez/qtdeTotalVrunitEntradaDez;	
            }
            
            Double valorTotaisEntDez = totalEntradaDez * vrUnitTotaisEntDez;
            
			Double totalSaidaDez = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 12)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
			Double totalVrunitSaidaDez = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getDtDoc().getMonthValue() == 12)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
			
            Long qtdeTotalVrunitSaidaDez = collect2.stream()
				.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
				.filter(codItem -> codItem.getDtDoc().getMonthValue() == 12)	
				.filter(codItem -> codItem.getOperacao().equals("S"))
				.count();
			
			Double vrUnitTotaisSaidaDez = 0.0;
            if(qtdeTotalVrunitSaidaDez > 0) {
            	vrUnitTotaisSaidaDez = totalVrunitSaidaDez/qtdeTotalVrunitSaidaDez;
            }
            
            Double valorTotaisSaiDez = totalSaidaDez * vrUnitTotaisSaidaDez;
			
			System.out.println("Total do Mês 12/"   + ano + " Entradas " + Math.abs(totalEntradaDez) + "    " + (saldoOutAnterior+totalEntradaDez));
			System.out.println("Total do Mês 12/" + ano + " Saidas " + Math.abs(totalSaidaNov) + "    " + (saldoJulAnterior+totalEntradaAgo-totalSaidaNov));
			if(saldoNovAnterior < 0) {
				saldoNovAnterior = 0.0;
			}

			String totEnt12 = String.valueOf(String.format("%.2f", Math.abs(totalEntradaDez)));
			String campoTotEnt12 = ("      " + totEnt12).substring(totEnt12.length()); 
			
			String totSai12 = String.valueOf(String.format("%.2f", Math.abs(totalSaidaDez)));
			String campoTotSai12 =  ("        " + totSai12).substring(totSai12.length()); 
			
			Double saldoInicialSumTotalEntradaDez = saldoInicial+totalEntradaDez;
			Double saldoInicialSumtotalEntradaDezMenusMathTotalSaidaDez = (saldoInicial+totalEntradaDez)-(Math.abs(totalSaidaDez));
			
			if(vrUnitTotaisEntDez < 1) {
				precoMedioEstoque = precoMedio;
			}else {
				precoMedioEstoque = vrUnitTotaisEntDez;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaDez;
			}
			
			novasLinhas.add("Total do Mês 12/" + ano + " Entradas " + campoTotEnt12 + "|          " + String.format("%.2f", vrUnitTotaisEntDez)   +"|          "+ String.format("%.2f", valorTotaisEntDez) + "|            " +  String.format("%.2f", saldoInicialSumTotalEntradaDez)+"|         "+precoMedioEstoque_+"|        "+String.format("%.2f", valorTotalEstoque)+"|");
			novasLinhas.add("Total do Mês 12/" + ano + " Saidas " + campoTotSai12 +   "|         " + String.format("%.2f", vrUnitTotaisSaidaDez) +"|         "+ String.format("%.2f", Math.abs(valorTotaisSaiDez)) + "|            " +String.format("%.2f",saldoInicialSumtotalEntradaDezMenusMathTotalSaidaDez)+"|"+"              |"+"              |");
			
			novasLinhas.add("________________________________________________________________________________________________________________________");		
			
			Double somaEntrada = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
			Double somaSaida = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
			System.out.println("Soma das Entradas " + somaEntrada);
			System.out.println("Soma das Saidas " +  somaSaida);
			Double estoqueFinal = saldoInicialSumTotalEntradaDez-Math.abs(totalSaidaDez);
			
			novasLinhas.add("Soma das Entradas              " + String.valueOf(String.format("%.2f", somaEntrada))+"|"+"              |"+"              |"+"                 |");
			novasLinhas.add("Soma das Saidas                " + String.valueOf(String.format("%.2f", Math.abs(somaSaida)))+"|"+"              |"+"              |"+"                 |");
			novasLinhas.add("Estoque Final                       |" +"              |"+"              |"+"            "+String.format("%.2f", estoqueFinal)+"|");
			
			novasLinhas.add("________________________________________________________________________________________________________________________");		
	    }  	
			
	    Files.write(pV12, novasLinhas, StandardOpenOption.CREATE);
	}
}
