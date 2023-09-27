package auditoria;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
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
import com.zeta.auditoria.EstoqueInicial;
import com.zeta.auditoria.HistoricoItemDao;
import com.zeta.auditoria.HistoricoItemModel;
import com.zeta.auditoria.ItensInvDeclaradoDao;
import com.zeta.auditoria.ItensInvDeclaradoModel;
import com.zeta.auditoria.PlanilhaMovimentacao;
import com.zeta.auditoria.ProdutosLevantamento2012Dao;
import com.zeta.auditoria.ProdutosLevantamento2012Model;
import com.zeta.util.UtilsEConverters;

public class Main3 {
	
	private static String cabecalho() {
		String linha = "";
	    StringBuilder sb = new StringBuilder();
	    
	    linha = sb.append("CodigoAntigo1")
	    		.append(";")
	    		.append("CodigoAntigo2")
	    		.append(";")
	    		.append("CodigoAtual")
	    		.append(";")
	    		.append("Descricao")
	    		.append(";")
	    		.append("UnidMed")
	    		.append(";")
	    		.append("QtdInicial")
	    		.append(";")
	    		.append("QtdEntradas")
	    		.append(";")
	    		.append("QtdSaidas")
	    		.append(";")
	    		.append("QtdFinal")
	    		.append(";")
	    		.append("QtdOm")
	    		.append(";")
	    		.append("VrMedEnt")
	    		.append(";")
	    		.append("VrOmEnt")
	    		.append(";")
	    		.append("VrOmSai")
	    		.toString();
	    
	    return linha;
	}
	
	private static String formatacaoPlanilha(PlanilhaMovimentacao mov) {
		String linha = "";
		
		String qtdInicial  =  String.format("%.2f", mov.getQtdInicial());
		String qtdEntradas  =  String.format("%.2f", mov.getQtdEntradas());
		String qtdSaidas  =  String.format("%.2f", mov.getQtdSaidas());
		
		String qtdFinal  =  String.format("%.2f", mov.getQtdFinal());
		String qtdOm     =  String.format("%.2f", mov.getQtdOm());
		String vrMedEnt     =  String.format("%.2f", mov.getVrMedEnt());
		String vrOmEnt     =  String.format("%.2f", mov.getVrOmEnt());
		String vrOmSai     =  String.format("%.2f", mov.getVrOmSai());
		
		linha  = mov.getCodigoAntigo1();
		linha += ";";
		linha += mov.getCodigoAntigo2();
		linha += ";";
		linha += mov.getCodigoAtual();
		linha += ";";
		linha += mov.getDescricao();
		linha += ";";
		linha += mov.getUnidMed();
		linha += ";";
		linha += (qtdInicial==null ? 0.0 : qtdInicial);
		linha += ";";
		linha += (qtdEntradas==null ? 0.0 : qtdEntradas);
		linha += ";";
		linha += (qtdSaidas==null ? 0.0 : qtdSaidas);
		linha += ";";
		linha += (qtdFinal==null ? 0.0 : qtdFinal);
		linha += ";";
		linha += (qtdOm==null ? 0.0 : qtdOm);
		linha += ";";
		linha += (vrMedEnt==null ? 0.0 : vrMedEnt);
		linha += ";";
		linha += (vrOmEnt==null ? 0.0 : vrOmEnt);
		linha += ";";
		linha += (vrOmSai==null ? 0.0 : vrOmSai);
		
		
		return linha;
	}
	
	
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
	
	public static EstoqueInicial estoqueInicial(List<HistoricoItemModel> collectovimento, String codigoAntigo1,
			String codigoAntigo2, String atual, int ano) {
		EstoqueInicial retorno = new EstoqueInicial();
		Stream<HistoricoItemModel> filter = collectovimento.stream()
				.filter(item -> item.getCodItem().equals(codigoAntigo1)
						     || item.getCodItem().equals(codigoAntigo2)
						     || item.getCodItem().equals(atual))
				.filter(item -> item.getOperacao().equals("EST"))
				.filter(item -> item.getDtDoc().getYear() == ano);
    	
		List<HistoricoItemModel> collect2 = filter.collect(Collectors.toList());
    	
		for(HistoricoItemModel movItem :  collect2){
				System.out.println(movItem.getDtDoc() + "|" + movItem.getOperacao() + "|" + movItem.getQtde()  + "|" + movItem.getVlUnit()
				 + "|" + movItem.getVlLiq());
				retorno.setCodigoItem(movItem.getCodItem());
				retorno.setOperacao("EST");
				retorno.setQtde(movItem.getQtde().doubleValue());
				retorno.setVlUnit( movItem.getVlUnit().doubleValue());
				retorno.setVlBruto(movItem.getVlBruto().doubleValue());
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
		
	    int ano = 2012;
        HistoricoItemDao dao = new HistoricoItemDao();
		
		List<HistoricoItemModel> collectovimento = dao.listaTodos().stream().collect(Collectors.toList());

		ProdutosLevantamento2012Dao levantamento2012Dao = new ProdutosLevantamento2012Dao(); 
		
		List<ProdutosLevantamento2012Model> collect3 = levantamento2012Dao.listaTodos().stream()

				.collect(Collectors.toList());

		List<String> novasLinhas =  new ArrayList<String>();   
	    String pV12 = Paths.get("E:\\EMPRESAS\\SELLENE\\LOJA03\\PlanilhaMovimentacao2012_v4.csv").toString();
	    BufferedWriter writer = new BufferedWriter(new FileWriter(new File(pV12)));;    	
    	String linha = " ";
		linha = cabecalho();

		writer.write(linha);
		writer.newLine();
	    for(ProdutosLevantamento2012Model obj :  collect3){	  
//	    	System.out.println(obj.getProdutoSefaz()+"|"+obj.getCodigoAntigo1()
//	    	    +"|"+obj.getCodigoAntigo2()+"|"+obj.getCodigoAtual()+"|"+obj.getDescricao()+"|"+obj.getUnidade()+"|"+obj.getFator());

	    	String antigo1 = ("00000000" + obj.getCodigoAntigo1()).substring(obj.getCodigoAntigo1().length());  
	    	String antigo2 = ("00000" + obj.getCodigoAntigo2()).substring(obj.getCodigoAntigo2().length());  
	    	String atual   = (obj.getCodigoAtual());  
	       	
	    	EstoqueInicial estoqueInicial = estoqueInicial(collectovimento, antigo1, antigo2, atual, ano-1);
            
			Stream<HistoricoItemModel> filter2 = collectovimento.stream()
					.filter(item -> item.getCodItem().equals(antigo1)
							     || item.getCodItem().equals(antigo2)
							     || item.getCodItem().equals(atual))
					.filter(item -> item.getDtDoc().getYear() == ano);
			
		    List<HistoricoItemModel> collect4 = filter2.collect(Collectors.toList());
			
            Double totalQtdeEntrada2012 = collect4.stream()
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.filter(cfop -> !cfop.getCfop().equals("1407"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
            
            Double totalVlEntrada2012 = collect4.stream()
					.filter(codItem -> codItem.getOperacao().equals("E"))
					.filter(cfop -> !cfop.getCfop().equals("1407"))
					.mapToDouble(vlEnt -> vlEnt.getVlBruto().doubleValue()).sum();
            
            Double totalQtdeSaidas2012 = collect4.stream()
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.filter(cfop -> !cfop.getCfop().equals("5929"))
					.filter(cfop -> !cfop.getCfop().equals("6929"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();
            
            Double totalVlSaidas2012 = collect4.stream()
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.filter(cfop -> !cfop.getCfop().equals("5929"))
					.filter(cfop -> !cfop.getCfop().equals("6929"))
					.mapToDouble(vlEnt -> vlEnt.getVlBruto().doubleValue()).sum();
           
            Double vlMedioEntrada = 0.0;
            if(totalQtdeEntrada2012 > 0) {
            	   vlMedioEntrada = (totalVlEntrada2012/totalQtdeEntrada2012);
            }else {
            	   vlMedioEntrada = (totalVlSaidas2012/totalQtdeSaidas2012);
            }

            Double totalSaida2012 = collect4.stream()
					.filter(codItem -> codItem.getOperacao().equals("S"))
					.filter(cfop -> !cfop.getCfop().equals("5929"))
					.filter(cfop -> !cfop.getCfop().equals("6929"))
					.mapToDouble(qtde -> qtde.getQtde().doubleValue()).sum();

            System.out.println(obj.getCodigoAntigo1() + "|" + obj.getCodigoAtual() + "|" + obj.getDescricao() 
                            + "|" + estoqueInicial.getQtde() + "|" + totalQtdeEntrada2012 + "|" + totalSaida2012*(-1)
                            + "|" + totalVlEntrada2012 + "|" + vlMedioEntrada);
            
            Double saldoFinal = (estoqueInicial.getQtde()==null ? 0.0 : estoqueInicial.getQtde()) + (totalQtdeEntrada2012==null ? 0.0 : totalQtdeEntrada2012)
            		- totalSaida2012*(-1);
            PlanilhaMovimentacao mov = new PlanilhaMovimentacao();
            mov.setCodigoAntigo1(obj.getCodigoAntigo1());
            mov.setCodigoAntigo2(obj.getCodigoAntigo2());
            mov.setCodigoAtual(obj.getCodigoAtual());
            mov.setDescricao(obj.getDescricao() );
            mov.setQtdInicial((estoqueInicial.getQtde()==null ? 0.0 : estoqueInicial.getQtde()));
            mov.setQtdEntradas((totalQtdeEntrada2012==null ? 0.0 : totalQtdeEntrada2012));
            mov.setQtdSaidas(totalSaida2012*(-1));
            mov.setQtdFinal(saldoFinal);
            mov.setQtdOm(Math.abs((saldoFinal==null ?  0.0 : saldoFinal)));
            mov.setVrMedEnt((vlMedioEntrada==null ? 0.0 : Math.abs(vlMedioEntrada)));
           
            Double res = 0.0;
            if(mov.getQtdFinal() < 0){
            	res = mov.getQtdOm()*mov.getVrMedEnt();
            	mov.setVrOmEnt((res == null ? 0.0 : res));
            }else {
            	res = mov.getQtdOm()*mov.getVrMedEnt();
            	mov.setVrOmSai((res == null ? 0.0 : res));
            }

            linha = formatacaoPlanilha(mov);
			
			writer.write(linha);
			writer.newLine();
			

	        //BufferedWriter buffWrite = Files.newBufferedWriter(pV12);
	       
	    } 
	    
		writer.close();
		System.out.println("Exportado com Sucesso!!!");
	}
}
