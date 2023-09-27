package auditoria;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.zeta.auditoria.HistoricoItemDao;
import com.zeta.auditoria.HistoricoItemModel;
import com.zeta.auditoria.ProdutosLevantamento2012Dao;
import com.zeta.auditoria.ProdutosLevantamento2012Model;

public class Main4 {

	public static void main(String[] args) throws IOException {
		
//		PrintWriter pw = new PrintWriter("E:\\arquivotexto.txt");
//		String string1 = new String();
//		String string2 = new String();
//		for(int i = 0; i < 200 ; i++) {
//			
//			string1 = "linha " + i + " campo 1 ";
//			string2 = "linha " + i + " campo 2 ";
//			pw.printf("%-20s",string1);
//			pw.printf("%-20s\n",string2);
//		}		
//		pw.close();
		
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
	    
		int anoInv = 2011;
	    int ano = 2012;
        HistoricoItemDao dao = new HistoricoItemDao();
		
		List<HistoricoItemModel> collectovimento = dao.listaTodos().stream()
				.collect(Collectors.toList());		
		
		ProdutosLevantamento2012Dao levantamento2012Dao = new ProdutosLevantamento2012Dao(); 
		
		List<ProdutosLevantamento2012Model> collect3 = levantamento2012Dao.listaTodos().stream()
			      .filter(item -> item.getCodigoAtual().equals("2336") || item.getCodigoAntigo1().equals("00005116")|| item.getCodigoAntigo2().equals("05116"))
				//.filter(item -> item.getCodigoAntigo1().equals("00008744")|| item.getCodigoAntigo2().equals("08744"))
				//.filter(item ->  item.getCodigoAntigo2().equals("06826"))
			    //.limit(50)
				.collect(Collectors.toList());

		PrintWriter pw = new PrintWriter("E:\\arquivotexto.txt");
		for(ProdutosLevantamento2012Model obj :  collect3){	 
	    	System.out.println(obj.getProdutoSefaz()+"|"+obj.getCodigoAntigo1()
    	    +"|"+obj.getCodigoAntigo2()+"|"+obj.getCodigoAtual()+"|"+obj.getDescricao()+"|"+obj.getUnidade()+"|"+obj.getFator());
	    	
	    	String antigo1 = ("00000000" + obj.getCodigoAntigo1()).substring(obj.getCodigoAntigo1().length());  
	    	String antigo2 = ("00000" + obj.getCodigoAntigo2()).substring(obj.getCodigoAntigo2().length());  
	    	String atual   = (obj.getCodigoAtual());  
		
			Stream<HistoricoItemModel> filter = collectovimento.stream()
					.filter(item -> item.getCodItem().equals(antigo1)||
							        item.getCodItem().equals(antigo2)||
							        item.getCodItem().equals(atual))
					.filter(item -> item.getDtDoc().getYear()==anoInv || item.getDtDoc().getYear()==ano);
			
			List<HistoricoItemModel> collect2 = 
					filter
					.filter(cfop -> !cfop.getCfop().equals("1407"))
					.filter(cfop -> !cfop.getCfop().equals("5929"))
					.filter(cfop -> !cfop.getCfop().equals("6929"))
					.collect(Collectors.toList());
           
			
			Double saldoInicial = collect2.stream()
					.filter(codItem -> codItem.getOperacao().equals("EST"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
            Double precoMedio = collect2.stream()
					.filter(codItem -> codItem.getOperacao().equals("EST"))
					.mapToDouble(qtde -> qtde.getVlUnit().doubleValue()).sum();
            Double precoValor = collect2.stream()
					.filter(codItem -> codItem.getOperacao().equals("EST"))
					.mapToDouble(qtde -> qtde.getVlLiq().doubleValue()).sum();
		
            String qtdeInicial = String.valueOf(String.format("%.2f", saldoInicial));
            String pMedio = String.valueOf(String.format("%.2f", precoMedio));
            String pValor = String.valueOf(String.format("%.2f", precoValor));
            
            
            pw.printf("%8s","Produto:"); 
            pw.printf("%8s",antigo1); 
            pw.printf("%1s","/"); 
            pw.printf("%8s",antigo2); 
            pw.printf("%1s","/"); 
            pw.printf("%8s",atual); 
            pw.printf("%1s"," "); 
            pw.printf("%10s","Descrição:");
            pw.printf("%1s"," ");
            pw.printf("%-20s",obj.getDescricao());
            pw.printf("%1s"," ");
            pw.printf("%-5s","Unidade:");
            pw.printf("%1s","");
            pw.printf("%5s",obj.getUnidade());
            pw.printf("%1s"," ");
            pw.printf("%8s\n","AGRUP_".concat(String.valueOf(obj.getId())));
            pw.printf("%-20s\n","-----------------------------------------------------------------------------------------------------------------------\n"
            		             + "          |Nota Fiscal |              M O V I M E N T O                |                E S T O Q U E                  |\n"
            		             + "Data      |------------------------------------------------------------------------------------------------------------|\n"
            		             + "          |Número      |CFOP|	Quantidade|Preço Unitário|		Valor|	Quantidade| 	Preço Médio| 	Valor|\n"
            		             + "-----------------------------------------------------------------------------------------------------------------------");
            
            pw.printf("%1s","                                                                       |");
            pw.printf("%16s",qtdeInicial);
            pw.printf("%1s","|");
            pw.printf("%18s",pMedio);
            pw.printf("%1s","|");
            pw.printf("%11s",pValor);
            pw.printf("%1s\n","|");
            pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
            for(HistoricoItemModel objMovimento : collect2){   
            	if(objMovimento.getDtDoc().getMonthValue() == 1) {
            		 String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
            		 String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
            		 String valor =  String.format("%.2f",objMovimento.getVlLiq());
            		 pw.printf("%-10s",objMovimento.getDtDoc());
            		 pw.printf("%1s","|");
            		 pw.printf("%-11s",objMovimento.getNumDoc());
            		 pw.printf("%1s"," |");
            		 pw.printf("%4s",objMovimento.getCfop());
            		 pw.printf("%1s","|");
            		 pw.printf("%11s",qtde);
            		 pw.printf("%1s","|");
            		 pw.printf("%14s",vUnit);
            		 pw.printf("%1s","|");
            		 pw.printf("%15s",valor);
               		 pw.printf("%1s","|");
               		 pw.printf("%16s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%18s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%11s","");
               		 pw.printf("%1s\n","|");
            		
            	}
            }

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
			 pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			 pw.printf("%-16s","Total do Mês 01/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%8s","Entradas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalEntradaJan))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisEntJan));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", valorTotaisEntJan));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumTotalEntradaJan));
			 pw.printf("%1s","|");
			 pw.printf("%18s",precoMedioEstoque_);
			 pw.printf("%1s","|");
			 pw.printf("%11s",String.format("%.2f", valorTotalEstoque));
			 pw.printf("%1s\n","|");
			 
			 pw.printf("%-16s","Total do Mês 01/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%-8s","Saidas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalSaidaJan))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisSaidaJan));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", Math.abs(valorTotaisSaiJan)));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumtotalEntradaJanMenusMathTotalSaidaJan));
			 pw.printf("%1s","|");
			 pw.printf("%18s","");
			 pw.printf("%1s","|");
			 pw.printf("%11s","");
			 pw.printf("%1s\n","|");
			
			 
			saldoJanAnterior = (saldoInicial+totalEntradaJan-Math.abs(totalSaidaJan));
			pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
            for(HistoricoItemModel objMovimento : collect2){   
            	if(objMovimento.getDtDoc().getMonthValue() == 2) {
            		 String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
            		 String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
            		 String valor =  String.format("%.2f",objMovimento.getVlLiq());
            		 pw.printf("%-10s",objMovimento.getDtDoc());
            		 pw.printf("%1s","|");
            		 pw.printf("%-11s",objMovimento.getNumDoc());
            		 pw.printf("%1s"," |");
            		 pw.printf("%4s",objMovimento.getCfop());
            		 pw.printf("%1s","|");
            		 pw.printf("%11s",qtde);
            		 pw.printf("%1s","|");
            		 pw.printf("%14s",vUnit);
            		 pw.printf("%1s","|");
            		 pw.printf("%15s",valor);
               		 pw.printf("%1s","|");
               		 pw.printf("%16s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%18s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%11s","");
               		 pw.printf("%1s\n","|");
            		
            	}
            }
			
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
			
			Double saldoInicialSumTotalEntradaFev = saldoJanAnterior+totalEntradaFev;
			Double saldoInicialSumtotalEntradaFevMenusMathTotalSaidaFev = saldoInicialSumTotalEntradaFev-(Math.abs(totalSaidaFev));
			
			if(vrUnitTotaisEntFev < 1) {
				if(precoMedio == 0.0) {
					precoMedioEstoque = vrUnitTotaisEntJan;
				}else {
					precoMedioEstoque = precoMedio;
				}
			}else if(vrUnitTotaisEntFev > 1){
				precoMedioEstoque = vrUnitTotaisEntFev;
			}else {
				precoMedioEstoque = vrUnitTotaisEntJan;
			}	
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaFev;
			}
			
			Double precoMedioSaidaEstoque=0.0;
			if(vrUnitTotaisSaidaFev < 1){
				precoMedioSaidaEstoque=vrUnitTotaisSaidaJan;
			}else {
				precoMedioSaidaEstoque=vrUnitTotaisSaidaFev;
			}
			
			 pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			 pw.printf("%-16s","Total do Mês 02/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%8s","Entradas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalEntradaFev))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisEntFev));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", valorTotaisEntFev));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumTotalEntradaFev));
			 pw.printf("%1s","|");
			 pw.printf("%18s",precoMedioEstoque_);
			 pw.printf("%1s","|");
			 pw.printf("%11s",String.format("%.2f", valorTotalEstoque));
			 pw.printf("%1s\n","|");
			 
			 pw.printf("%-16s","Total do Mês 02/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%-8s","Saidas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalSaidaFev))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisSaidaFev));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", Math.abs(valorTotaisSaiFev)));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumtotalEntradaFevMenusMathTotalSaidaFev));
			 pw.printf("%1s","|");
			 pw.printf("%18s","");
			 pw.printf("%1s","|");
			 pw.printf("%11s","");
			 pw.printf("%1s\n","|");
			 
			 
			saldoFevAnterior = (saldoJanAnterior+totalEntradaFev-Math.abs(totalSaidaFev));
			pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 3) {
           		 String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
           		 String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
           		 String valor =  String.format("%.2f",objMovimento.getVlLiq());
           		 pw.printf("%-10s",objMovimento.getDtDoc());
           		 pw.printf("%1s","|");
           		 pw.printf("%-11s",objMovimento.getNumDoc());
           		 pw.printf("%1s"," |");
           		 pw.printf("%4s",objMovimento.getCfop());
           		 pw.printf("%1s","|");
           		 pw.printf("%11s",qtde);
           		 pw.printf("%1s","|");
           		 pw.printf("%14s",vUnit);
           		 pw.printf("%1s","|");
           		 pw.printf("%15s",valor);
           		 pw.printf("%1s","|");
           		 pw.printf("%16s","");
           		 pw.printf("%1s","|");
           		 pw.printf("%18s","");
           		 pw.printf("%1s","|");
           		 pw.printf("%11s","");
           		 pw.printf("%1s\n","|");
            	}
			}
			
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
			
			Double saldoInicialSumTotalEntradaMar = saldoFevAnterior+totalEntradaMar;
			Double saldoInicialSumtotalEntradaMarMenusMathTotalSaidaMar = saldoInicialSumTotalEntradaMar-(Math.abs(totalSaidaMar));
			
			if(vrUnitTotaisEntMar < 1) {
				if(precoMedio == 0.0) {
					precoMedioEstoque = vrUnitTotaisEntFev;
				}else {
					precoMedioEstoque = precoMedio;
				}
			}else {
				precoMedioEstoque = (vrUnitTotaisEntJan+vrUnitTotaisEntFev+vrUnitTotaisEntMar)/3;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaMar;
			}
			
			if(vrUnitTotaisSaidaMar < 1){
				precoMedioSaidaEstoque=vrUnitTotaisSaidaFev;
			}else {
				precoMedioSaidaEstoque=vrUnitTotaisSaidaMar;
			}
			
			 pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			 pw.printf("%-16s","Total do Mês 03/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%8s","Entradas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalEntradaMar))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisEntMar));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", valorTotaisEntMar));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumTotalEntradaMar));
			 pw.printf("%1s","|");
			 pw.printf("%18s",precoMedioEstoque_);
			 pw.printf("%1s","|");
			 pw.printf("%11s",String.format("%.2f", valorTotalEstoque));
			 pw.printf("%1s\n","|");
			 
			 pw.printf("%-16s","Total do Mês 03/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%-8s","Saidas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalSaidaMar))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisSaidaMar));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", Math.abs(valorTotaisSaiMar)));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumtotalEntradaMarMenusMathTotalSaidaMar));
			 pw.printf("%1s","|");
			 pw.printf("%18s","");
			 pw.printf("%1s","|");
			 pw.printf("%11s","");
			 pw.printf("%1s\n","|");
			
			 
			saldoMarAnterior = (saldoFevAnterior+totalEntradaMar-Math.abs(totalSaidaMar));
			pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 4) {
              		 String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
               		 String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
               		 String valor =  String.format("%.2f",objMovimento.getVlLiq());
               		 pw.printf("%-10s",objMovimento.getDtDoc());
               		 pw.printf("%1s","|");
               		 pw.printf("%-11s",objMovimento.getNumDoc());
               		 pw.printf("%1s"," |");
               		 pw.printf("%4s",objMovimento.getCfop());
               		 pw.printf("%1s","|");
               		 pw.printf("%11s",qtde);
               		 pw.printf("%1s","|");
               		 pw.printf("%14s",vUnit);
               		 pw.printf("%1s","|");
               		 pw.printf("%15s",valor);
               		 pw.printf("%1s","|");
               		 pw.printf("%16s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%18s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%11s","");
               		 pw.printf("%1s\n","|");
               		
            	}
			}
			
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

			Double saldoInicialSumTotalEntradaAbr = saldoMarAnterior+totalEntradaAbr;
			Double saldoInicialSumtotalEntradaAbrMenusMathTotalSaidaAbr = saldoInicialSumTotalEntradaAbr-(Math.abs(totalSaidaAbr));
			
			if(vrUnitTotaisEntAbr < 1) {
				if(precoMedio == 0.0) {
					precoMedioEstoque = vrUnitTotaisEntMar;
				}else {
					precoMedioEstoque = precoMedio;
				}
			}else {
				precoMedioEstoque = vrUnitTotaisEntAbr;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaAbr;
			}
			
			if(vrUnitTotaisSaidaAbr < 1){
				precoMedioSaidaEstoque=vrUnitTotaisSaidaMar;
			}else {
				precoMedioSaidaEstoque=vrUnitTotaisSaidaAbr;
			}
			
			if(vrUnitTotaisSaidaAbr < 1){
				precoMedioSaidaEstoque=vrUnitTotaisSaidaMar;
			}else {
				precoMedioSaidaEstoque=vrUnitTotaisSaidaAbr;
			}
			
			 pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			 pw.printf("%-16s","Total do Mês 04/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%8s","Entradas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalEntradaAbr))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisEntAbr));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", valorTotaisEntAbr));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumTotalEntradaAbr));
			 pw.printf("%1s","|");
			 pw.printf("%18s",precoMedioEstoque_);
			 pw.printf("%1s","|");
			 pw.printf("%11s",String.format("%.2f", valorTotalEstoque));
			 pw.printf("%1s\n","|");
			 
			 pw.printf("%-16s","Total do Mês 04/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%-8s","Saidas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalSaidaAbr))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisSaidaAbr));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", Math.abs(valorTotaisSaiAbr)));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumtotalEntradaAbrMenusMathTotalSaidaAbr));
			 pw.printf("%1s","|");
			 pw.printf("%18s","");
			 pw.printf("%1s","|");
			 pw.printf("%11s","");
			 pw.printf("%1s\n","|");
			
			 
			saldoAbrAnterior = (saldoMarAnterior+totalEntradaAbr-Math.abs(totalSaidaAbr));
			pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");			
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 5) {
             		 String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
               		 String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
               		 String valor =  String.format("%.2f",objMovimento.getVlLiq());
               		 pw.printf("%-10s",objMovimento.getDtDoc());
               		 pw.printf("%1s","|");
               		 pw.printf("%-11s",objMovimento.getNumDoc());
               		 pw.printf("%1s"," |");
               		 pw.printf("%4s",objMovimento.getCfop());
               		 pw.printf("%1s","|");
               		 pw.printf("%11s",qtde);
               		 pw.printf("%1s","|");
               		 pw.printf("%14s",vUnit);
               		 pw.printf("%1s","|");
               		 pw.printf("%15s",valor);
               		 pw.printf("%1s","|");
               		 pw.printf("%16s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%18s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%11s","");
               		 pw.printf("%1s\n","|");
            	}
			}
			
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
			
			Double saldoInicialSumTotalEntradaMai = saldoAbrAnterior+totalEntradaMai;
			Double saldoInicialSumtotalEntradaMaiMenusMathTotalSaidaMai = saldoInicialSumTotalEntradaMai-(Math.abs(totalSaidaMai));
			
			if(vrUnitTotaisEntMai < 1) {
				if(precoMedio == 0.0) {
					precoMedioEstoque = vrUnitTotaisEntAbr;
				}else {
					precoMedioEstoque = precoMedio;
				}
			}else {
				precoMedioEstoque = vrUnitTotaisEntMai;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaMai;
			}
			
			if(vrUnitTotaisSaidaMai < 1){
				precoMedioSaidaEstoque=vrUnitTotaisSaidaAbr;
			}else {
				precoMedioSaidaEstoque=vrUnitTotaisSaidaMai;
			}
			
			 pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			 pw.printf("%-16s","Total do Mês 05/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%8s","Entradas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalEntradaMai))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisEntMai));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", valorTotaisEntMai));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumTotalEntradaMai));
			 pw.printf("%1s","|");
			 pw.printf("%18s",precoMedioEstoque_);
			 pw.printf("%1s","|");
			 pw.printf("%11s",String.format("%.2f", valorTotalEstoque));
			 pw.printf("%1s\n","|");
			 
			 pw.printf("%-16s","Total do Mês 05/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%-8s","Saidas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalSaidaMai))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisSaidaMai));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", Math.abs(valorTotaisSaiMai)));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumtotalEntradaMaiMenusMathTotalSaidaMai));
			 pw.printf("%1s","|");
			 pw.printf("%18s","");
			 pw.printf("%1s","|");
			 pw.printf("%11s","");
			 pw.printf("%1s\n","|");
			
			
			saldoMaiAnterior =  (saldoAbrAnterior+totalEntradaMai-Math.abs(totalSaidaMai));
			pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 6) {
            		 String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
               		 String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
               		 String valor =  String.format("%.2f",objMovimento.getVlLiq());
               		 pw.printf("%-10s",objMovimento.getDtDoc());
               		 pw.printf("%1s","|");
               		 pw.printf("%-11s",objMovimento.getNumDoc());
               		 pw.printf("%1s"," |");
               		 pw.printf("%4s",objMovimento.getCfop());
               		 pw.printf("%1s","|");
               		 pw.printf("%11s",qtde);
               		 pw.printf("%1s","|");
               		 pw.printf("%14s",vUnit);
               		 pw.printf("%1s","|");
               		 pw.printf("%15s",valor);
               		 pw.printf("%1s","|");
               		 pw.printf("%16s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%18s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%11s","");
               		 pw.printf("%1s\n","|");
            	}
			}
			
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
			
			Double saldoInicialSumTotalEntradaJun = saldoMaiAnterior+totalEntradaJun;
			Double saldoInicialSumtotalEntradaJunMenusMathTotalSaidaJun = saldoInicialSumTotalEntradaJun-(Math.abs(totalSaidaJun));
			
			if(vrUnitTotaisEntJun < 1) {
				if(precoMedio == 0.0) {
					precoMedioEstoque = vrUnitTotaisEntMai;
				}else {
					precoMedioEstoque = precoMedio;
				}
			}else {
				precoMedioEstoque = vrUnitTotaisEntJun;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaJun;
			}
			
			if(vrUnitTotaisSaidaJun < 1){
				precoMedioSaidaEstoque=vrUnitTotaisSaidaMai;
			}else {
				precoMedioSaidaEstoque=vrUnitTotaisSaidaJun;
			}
			
			 pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			 pw.printf("%-16s","Total do Mês 06/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%8s","Entradas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalEntradaJun))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisEntJun));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", valorTotaisEntJun));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumTotalEntradaJun));
			 pw.printf("%1s","|");
			 pw.printf("%18s",precoMedioEstoque_);
			 pw.printf("%1s","|");
			 pw.printf("%11s",String.format("%.2f", valorTotalEstoque));
			 pw.printf("%1s\n","|");
			 
			 pw.printf("%-16s","Total do Mês 06/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%-8s","Saidas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalSaidaJun))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisSaidaJun));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", Math.abs(valorTotaisSaiJun)));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumtotalEntradaJunMenusMathTotalSaidaJun));
			 pw.printf("%1s","|");
			 pw.printf("%18s","");
			 pw.printf("%1s","|");
			 pw.printf("%11s","");
			 pw.printf("%1s\n","|");
			 
			 
			saldoJunAnterior = (saldoMaiAnterior+totalEntradaJun-Math.abs(totalSaidaJun));
			pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 7) {
            		 String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
               		 String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
               		 String valor =  String.format("%.2f",objMovimento.getVlLiq());
               		 pw.printf("%-10s",objMovimento.getDtDoc());
               		 pw.printf("%1s","|");
               		 pw.printf("%-11s",objMovimento.getNumDoc());
               		 pw.printf("%1s"," |");
               		 pw.printf("%4s",objMovimento.getCfop());
               		 pw.printf("%1s","|");
               		 pw.printf("%11s",qtde);
               		 pw.printf("%1s","|");
               		 pw.printf("%14s",vUnit);
               		 pw.printf("%1s","|");
               		 pw.printf("%15s",valor);
               		 pw.printf("%1s","|");
               		 pw.printf("%16s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%18s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%11s","");
               		 pw.printf("%1s\n","|");
            	}
			}
			

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
						
			Double saldoInicialSumTotalEntradaJul = saldoJunAnterior+totalEntradaJul;
			Double saldoInicialSumtotalEntradaJulMenusMathTotalSaidaJul = saldoInicialSumTotalEntradaJul-(Math.abs(totalSaidaJul));
			
			if(vrUnitTotaisEntJul < 1) {
				if(precoMedio == 0.0) {
					precoMedioEstoque = vrUnitTotaisEntJun;
				}else {
					precoMedioEstoque = precoMedio;
				}
			}else {
				precoMedioEstoque = vrUnitTotaisEntJul;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaJul;
			}
			
			if(vrUnitTotaisSaidaJul < 1){
				precoMedioSaidaEstoque=vrUnitTotaisSaidaJun;
			}else {
				precoMedioSaidaEstoque=vrUnitTotaisSaidaJul;
			}
			
			 pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			 pw.printf("%-16s","Total do Mês 07/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%8s","Entradas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalEntradaJul))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisEntJul));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", valorTotaisEntJul));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumTotalEntradaJul));
			 pw.printf("%1s","|");
			 pw.printf("%18s",precoMedioEstoque_);
			 pw.printf("%1s","|");
			 pw.printf("%11s",String.format("%.2f", valorTotalEstoque));
			 pw.printf("%1s\n","|");
			 
			 pw.printf("%-16s","Total do Mês 07/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%-8s","Saidas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalSaidaJul))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisSaidaJul));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", Math.abs(valorTotaisSaiJul)));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumtotalEntradaJulMenusMathTotalSaidaJul));
			 pw.printf("%1s","|");
			 pw.printf("%18s","");
			 pw.printf("%1s","|");
			 pw.printf("%11s","");
			 pw.printf("%1s\n","|");
			
			 
			 
			saldoJulAnterior =  (saldoJunAnterior+totalEntradaJul-Math.abs(totalSaidaJul));
			pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 8) {
           		 String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
           		 String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
           		 String valor =  String.format("%.2f",objMovimento.getVlLiq());
           		 pw.printf("%-10s",objMovimento.getDtDoc());
           		 pw.printf("%1s","|");
           		 pw.printf("%-11s",objMovimento.getNumDoc());
           		 pw.printf("%1s"," |");
           		 pw.printf("%4s",objMovimento.getCfop());
           		 pw.printf("%1s","|");
           		 pw.printf("%11s",qtde);
           		 pw.printf("%1s","|");
           		 pw.printf("%14s",vUnit);
           		 pw.printf("%1s","|");
           		 pw.printf("%15s",valor);
           		 pw.printf("%1s","|");
           		 pw.printf("%16s","");
           		 pw.printf("%1s","|");
           		 pw.printf("%18s","");
           		 pw.printf("%1s","|");
           		 pw.printf("%11s","");
           		 pw.printf("%1s\n","|");
            	}
			}
			
		
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
						
			Double saldoInicialSumTotalEntradaAgo = saldoJulAnterior+totalEntradaAgo;
			Double saldoInicialSumtotalEntradaAgoMenusMathTotalSaidaAgo = saldoInicialSumTotalEntradaAgo-(Math.abs(totalSaidaAgo));
			
			if(vrUnitTotaisEntAgo < 1) {
				if(precoMedio == 0.0) {
					precoMedioEstoque = vrUnitTotaisEntJul;
				}else {
					precoMedioEstoque = precoMedio;
				}
			}else {
				precoMedioEstoque = vrUnitTotaisEntAgo;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaAgo;
			}
			
			if(vrUnitTotaisSaidaAgo < 1){
				precoMedioSaidaEstoque=vrUnitTotaisSaidaJul;
			}else {
				precoMedioSaidaEstoque=vrUnitTotaisSaidaAgo;
			}
			
			 pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			 pw.printf("%-16s","Total do Mês 08/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%8s","Entradas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalEntradaAgo))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisEntAgo));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", valorTotaisEntAgo));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumTotalEntradaAgo));
			 pw.printf("%1s","|");
			 pw.printf("%18s",precoMedioEstoque_);
			 pw.printf("%1s","|");
			 pw.printf("%11s",String.format("%.2f", valorTotalEstoque));
			 pw.printf("%1s\n","|");
			 
			 pw.printf("%-16s","Total do Mês 08/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%-8s","Saidas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalSaidaAgo))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisSaidaAgo));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", Math.abs(valorTotaisSaiJul)));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumtotalEntradaAgoMenusMathTotalSaidaAgo));
			 pw.printf("%1s","|");
			 pw.printf("%18s","");
			 pw.printf("%1s","|");
			 pw.printf("%11s","");
			 pw.printf("%1s\n","|");
			
			 
		    saldoAgoAnterior = (saldoJulAnterior+totalEntradaAgo-Math.abs(totalSaidaAgo));
		    pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 9) {
              		 String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
               		 String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
               		 String valor =  String.format("%.2f",objMovimento.getVlLiq());
               		 pw.printf("%-10s",objMovimento.getDtDoc());
               		 pw.printf("%1s","|");
               		 pw.printf("%-11s",objMovimento.getNumDoc());
               		 pw.printf("%1s"," |");
               		 pw.printf("%4s",objMovimento.getCfop());
               		 pw.printf("%1s","|");
               		 pw.printf("%11s",qtde);
               		 pw.printf("%1s","|");
               		 pw.printf("%14s",vUnit);
               		 pw.printf("%1s","|");
               		 pw.printf("%15s",valor);
               		 pw.printf("%1s","|");
               		 pw.printf("%16s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%18s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%11s","");
               		 pw.printf("%1s\n","|");
            	}
			}
			
			
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
	
			Double saldoInicialSumTotalEntradaSet = saldoAgoAnterior+totalEntradaSet;
			Double saldoInicialSumtotalEntradaSetMenusMathTotalSaidaSet = saldoInicialSumTotalEntradaSet-(Math.abs(totalSaidaSet));
			
			if(vrUnitTotaisEntSet < 1) {
				if(precoMedio == 0.0) {
					precoMedioEstoque = vrUnitTotaisEntAgo;
				}else {
					precoMedioEstoque = precoMedio;
				}
			}else {
				precoMedioEstoque = vrUnitTotaisEntSet;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaSet;
			}
			
			if(vrUnitTotaisSaidaSet < 1){
				precoMedioSaidaEstoque=vrUnitTotaisSaidaAgo;
			}else {
				precoMedioSaidaEstoque=vrUnitTotaisSaidaSet;
			}
			
			 pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			 pw.printf("%-16s","Total do Mês 09/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%8s","Entradas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalEntradaSet))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisEntSet));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", valorTotaisEntSet));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumTotalEntradaSet));
			 pw.printf("%1s","|");
			 pw.printf("%18s",precoMedioEstoque_);
			 pw.printf("%1s","|");
			 pw.printf("%11s",String.format("%.2f", valorTotalEstoque));
			 pw.printf("%1s\n","|");
			 
			 pw.printf("%-16s","Total do Mês 09/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%-8s","Saidas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalSaidaSet))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisSaidaSet));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", Math.abs(valorTotaisSaiSet)));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumtotalEntradaSetMenusMathTotalSaidaSet));
			 pw.printf("%1s","|");
			 pw.printf("%18s","");
			 pw.printf("%1s","|");
			 pw.printf("%11s","");
			 pw.printf("%1s\n","|");
			
			 
			saldoSetAnterior = (saldoAgoAnterior+totalEntradaSet-Math.abs(totalSaidaSet));
			pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 10) {
             		 String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
               		 String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
               		 String valor =  String.format("%.2f",objMovimento.getVlLiq());
               		 pw.printf("%-10s",objMovimento.getDtDoc());
               		 pw.printf("%1s","|");
               		 pw.printf("%-11s",objMovimento.getNumDoc());
               		 pw.printf("%1s"," |");
               		 pw.printf("%4s",objMovimento.getCfop());
               		 pw.printf("%1s","|");
               		 pw.printf("%11s",qtde);
               		 pw.printf("%1s","|");
               		 pw.printf("%14s",vUnit);
               		 pw.printf("%1s","|");
               		 pw.printf("%15s",valor);
               		 pw.printf("%1s","|");
               		 pw.printf("%16s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%18s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%11s","");
               		 pw.printf("%1s\n","|");
            	}
			}
			
			
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
			
			Double saldoInicialSumTotalEntradaOut = saldoSetAnterior+totalEntradaOut;
			Double saldoInicialSumtotalEntradaOutMenusMathTotalSaidaOut = saldoInicialSumTotalEntradaOut-(Math.abs(totalSaidaOut));
			
			if(vrUnitTotaisEntOut < 1) {
				if(precoMedio == 0.0) {
					precoMedioEstoque = vrUnitTotaisEntSet;
				}else {
					precoMedioEstoque = precoMedio;
				}
			}else {
				precoMedioEstoque = vrUnitTotaisEntOut;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaOut;
			}
			
			if(vrUnitTotaisSaidaOut < 1){
				precoMedioSaidaEstoque=vrUnitTotaisSaidaSet;
			}else {
				precoMedioSaidaEstoque=vrUnitTotaisSaidaOut;
			}
			
			 pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			 pw.printf("%-16s","Total do Mês 10/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%8s","Entradas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalEntradaOut))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisEntOut));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", valorTotaisEntOut));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumTotalEntradaOut));
			 pw.printf("%1s","|");
			 pw.printf("%18s",precoMedioEstoque_);
			 pw.printf("%1s","|");
			 pw.printf("%11s",String.format("%.2f", valorTotalEstoque));
			 pw.printf("%1s\n","|");
			 
			 pw.printf("%-16s","Total do Mês 10/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%-8s","Saidas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalSaidaOut))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisSaidaSet));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", Math.abs(valorTotaisSaiSet)));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumtotalEntradaOutMenusMathTotalSaidaOut));
			 pw.printf("%1s","|");
			 pw.printf("%18s","");
			 pw.printf("%1s","|");
			 pw.printf("%11s","");
			 pw.printf("%1s\n","|");
			
			 
			saldoOutAnterior = (saldoSetAnterior+totalEntradaOut-Math.abs(totalSaidaOut));
			pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			for(HistoricoItemModel objMovimento : collect2){
            	if(objMovimento.getDtDoc().getMonthValue() == 11) {
            		 String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
               		 String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
               		 String valor =  String.format("%.2f",objMovimento.getVlLiq());
               		 pw.printf("%-10s",objMovimento.getDtDoc());
               		 pw.printf("%1s","|");
               		 pw.printf("%-11s",objMovimento.getNumDoc());
               		 pw.printf("%1s"," |");
               		 pw.printf("%4s",objMovimento.getCfop());
               		 pw.printf("%1s","|");
               		 pw.printf("%11s",qtde);
               		 pw.printf("%1s","|");
               		 pw.printf("%14s",vUnit);
               		 pw.printf("%1s","|");
               		 pw.printf("%15s",valor);
               		 pw.printf("%1s","|");
               		 pw.printf("%16s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%18s","");
               		 pw.printf("%1s","|");
               		 pw.printf("%11s","");
               		 pw.printf("%1s\n","|");
            	}
			}
			
			
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
			
			Double saldoInicialSumTotalEntradaNov = saldoOutAnterior+totalEntradaNov;
			Double saldoInicialSumtotalEntradaNovMenusMathTotalSaidaNov = saldoInicialSumTotalEntradaNov-(Math.abs(totalSaidaNov));
			
			if(vrUnitTotaisEntNov < 1) {
				if(precoMedio == 0.0) {
					precoMedioEstoque = vrUnitTotaisEntOut;
				}else {
					precoMedioEstoque = precoMedio;
				}
			}else {
				precoMedioEstoque = vrUnitTotaisEntNov;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaNov;
			}
			
			if(vrUnitTotaisSaidaNov < 1){
				precoMedioSaidaEstoque=vrUnitTotaisSaidaOut;
			}else {
				precoMedioSaidaEstoque=vrUnitTotaisSaidaNov;
			}
			
			 pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			 pw.printf("%-16s","Total do Mês 11/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%8s","Entradas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalEntradaNov))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisEntNov));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", valorTotaisEntNov));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumTotalEntradaNov));
			 pw.printf("%1s","|");
			 pw.printf("%18s",precoMedioEstoque_);
			 pw.printf("%1s","|");
			 pw.printf("%11s",String.format("%.2f", valorTotalEstoque));
			 pw.printf("%1s\n","|");
			 
			 pw.printf("%-16s","Total do Mês 11/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%-8s","Saidas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalSaidaNov))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisSaidaNov));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", Math.abs(valorTotaisSaiNov)));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumtotalEntradaNovMenusMathTotalSaidaNov));
			 pw.printf("%1s","|");
			 pw.printf("%18s","");
			 pw.printf("%1s","|");
			 pw.printf("%11s","");
			 pw.printf("%1s\n","|");
			
			 
			saldoNovAnterior = (saldoOutAnterior+totalEntradaNov-Math.abs(totalSaidaNov));
			pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			for(HistoricoItemModel objMovimento : collect2){
				if(objMovimento.getDtDoc().getYear() == ano) {
	            	if(objMovimento.getDtDoc().getMonthValue() == 12) {
	            		 String qtde = String.format("%.2f", Math.abs(objMovimento.getQtde().doubleValue()));
	               		 String vUnit =  String.format("%.2f",objMovimento.getVlUnit());
	               		 String valor =  String.format("%.2f",objMovimento.getVlLiq());
	               		 pw.printf("%-10s",objMovimento.getDtDoc());
	               		 pw.printf("%1s","|");
	               		 pw.printf("%-11s",objMovimento.getNumDoc());
	               		 pw.printf("%1s"," |");
	               		 pw.printf("%4s",objMovimento.getCfop());
	               		 pw.printf("%1s","|");
	               		 pw.printf("%11s",qtde);
	               		 pw.printf("%1s","|");
	               		 pw.printf("%14s",vUnit);
	               		 pw.printf("%1s","|");
	               		 pw.printf("%15s",valor);
	               		 pw.printf("%1s","|");
	               		 pw.printf("%16s","");
	               		 pw.printf("%1s","|");
	               		 pw.printf("%18s","");
	               		 pw.printf("%1s","|");
	               		 pw.printf("%11s","");
	               		 pw.printf("%1s\n","|");
	            	}
				}

			}
			
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

			
			Double saldoInicialSumTotalEntradaDez = saldoNovAnterior+totalEntradaDez;
			Double saldoInicialSumtotalEntradaDezMenusMathTotalSaidaDez = saldoInicialSumTotalEntradaDez-(Math.abs(totalSaidaDez));
			
			if(vrUnitTotaisEntDez < 1) {
				if(precoMedio == 0.0) {
					precoMedioEstoque = vrUnitTotaisEntNov;
				}else {
					precoMedioEstoque = precoMedio;
				}
				
			}else {
				precoMedioEstoque = vrUnitTotaisEntDez;
			}
			
			precoMedioEstoque_ = String.format("%.2f", precoMedioEstoque);
			
			if(precoMedioEstoque > 0) {
				valorTotalEstoque = precoMedioEstoque*saldoInicialSumTotalEntradaDez;
			}
			
			
			Double somaEntrada = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
			Double somaSaida = collect2.stream()
					.filter(codItem -> codItem.getDtDoc().getYear() == ano)	
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
			
			
			if(vrUnitTotaisSaidaDez < 1){
				precoMedioSaidaEstoque=vrUnitTotaisSaidaNov;
			}else {
				precoMedioSaidaEstoque=vrUnitTotaisSaidaDez;
			}
			
			 pw.printf("-----------------------------------------------------------------------------------------------------------------------\n");
			 pw.printf("%-16s","Total do Mês 12/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%8s","Entradas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalEntradaDez))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisEntDez));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", valorTotaisEntDez));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumTotalEntradaDez));
			 pw.printf("%1s","|");
			 pw.printf("%18s",precoMedioEstoque_);
			 pw.printf("%1s","|");
			 pw.printf("%11s",String.format("%.2f", valorTotalEstoque));
			 pw.printf("%1s\n","|");
			 
			 pw.printf("%-16s","Total do Mês 12/");
			 pw.printf("%-4s",ano);
			 pw.printf("%1s","  ");
			 pw.printf("%-8s","Saidas");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(totalSaidaDez))));
			 pw.printf("%1s","|");
			 pw.printf("%14s",String.format("%.2f", vrUnitTotaisSaidaDez));
			 pw.printf("%1s","|");
			 pw.printf("%15s",String.format("%.2f", Math.abs(valorTotaisSaiDez)));
			 pw.printf("%1s","|");
			 pw.printf("%16s",String.format("%.2f", saldoInicialSumtotalEntradaDezMenusMathTotalSaidaDez));
			 pw.printf("%1s","|");
			 pw.printf("%18s","");
			 pw.printf("%1s","|");
			 pw.printf("%11s","");
			 pw.printf("%1s\n","|");
			 
			
			 pw.printf("_______________________________________________________________________________________________________________________\n");
			
			System.out.println("Soma das Entradas " + somaEntrada);
			System.out.println("Soma das Saidas " +  somaSaida);
			Double estoqueFinal   = (saldoInicial+somaEntrada)-Math.abs(somaSaida);
			
			if(precoMedioEstoque < 1) {
				precoMedioEstoque = precoMedioSaidaEstoque;
			}
			
			Double vlFinalEstoque = estoqueFinal * precoMedioEstoque;
			
			//novasLinhas.add("Soma das Entradas              " + String.valueOf(String.format("%.2f", somaEntrada))+"|"+"              |"+"              |"+"                 |");
			//novasLinhas.add("Soma das Saidas                " + String.valueOf(String.format("%.2f", Math.abs(somaSaida)))+"|"+"              |"+"              |"+"                 |");
			//novasLinhas.add("Estoque Final                       |" +"              |"+"              |"+"            "+String.format("%.2f", estoqueFinal)+"|");
			 pw.printf("%-17s","Soma das Entradas");
			 pw.printf("%1s","             ");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", somaEntrada)));
			 pw.printf("%1s\n","|");
			 pw.printf("%-17s","Soma das Saidas");
			 pw.printf("%1s","             ");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(somaSaida))));
			 pw.printf("%1s\n","|");			 
			 if(estoqueFinal < 0) {
				 pw.printf("%-17s","Estoque Final(OE)");
			 }else {
				 pw.printf("%-17s","Estoque Final");
			 }
			 pw.printf("%1s","                                                             ");
			 pw.printf("%10s",String.valueOf(String.format("%.2f", Math.abs(estoqueFinal))));
			 pw.printf("%1s","|        ");
			 pw.printf("%10s",String.format("%.2f", precoMedioEstoque));
			 pw.printf("%1s","| ");
			 pw.printf("%10s",String.format("%.2f", Math.abs(vlFinalEstoque)));
			 pw.printf("%1s\n","|");
			 pw.printf("_______________________________________________________________________________________________________________________|\n");
			 pw.printf("%1s\n","");
		}
		
		pw.close();
	    System.out.println("Exportado");
	}

}
