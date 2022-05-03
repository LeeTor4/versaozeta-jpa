package com.zeta.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.zeta.model.FichaFinanceiroPorItens;

class Produto{
	
	String codItem;
	String codAntItem;
	
	
	public String getCodItem() {
		return codItem;
	}
	public void setCodItem(String codItem) {
		this.codItem = codItem;
	}
	public String getCodAntItem() {
		return codAntItem;
	}
	public void setCodAntItem(String codAntItem) {
		this.codAntItem = codAntItem;
	}
}

public class LeituraCadastroProdutoDePara {
	
	public static List<Produto> deParaProdutos(Path path) {
		List<Produto> retorno = new ArrayList<Produto>();
		try {
		
			List<String> lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
			
			for (int i = 0; i < lines.size(); i++) {
				
				String[] campos = lines.get(i).split("\\;");
				Produto p = new Produto();	
				for (int c = 0; c < campos.length; c++) {	
					
					if (c == 0) {						
						//System.out.println(campos[c]);
						p.setCodItem(campos[c]);
					}
					
					if (c == 2) {		
                       p.setCodAntItem(campos[c]);
					}
					
				}	
				
				retorno.add(p);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return retorno;
	}
	
	public static List<String> linhasTotalizadorFinanceiro(Path path) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1).stream().skip(1).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
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
					if (c == 4) {	
						System.out.println(campos[c]);
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
	
	
	
	
	
	
	public static String getCodAntItem(String codAntItem) {
		return codAntItem;
	}
	
	
	public static void main(String[] args) {
		String ano = "2015";
		String emp = "SELLENE";
		String estab = "MATRIZ";
		String cnpj  = "05329222000176";
		
		Path csv1  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\").concat("CONTROLE_FINANCEIRO_05329222000176_2015.csv"));
		Path csv2  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat("ListaCompletaProdutosProcFitVit.csv"));
	    
		//deParaProdutos(csv2);
		
		//for(Produto p: deParaProdutos(csv2)){}
		//ficha(csv1, p.getCodItem(), p.getCodAntItem());
		//System.out.println(fichaTotalizada(csv1, "1564", "4800").getQtdeEnt()+"|"+fichaTotalizada(csv1, "1564", "4800").getQtdeEnt());

//        for(int i = 0 ; i < leituraTotalizadorFinanceiro(csv1).size(); i++){
//        	
//        	
//        	System.out.println( leituraTotalizadorFinanceiro(csv1).get(i).getStatus());
//        		
//        	  for(int z = 0 ; z < leituraTotalizadorFinanceiro(csv1).size(); z++){
//        		  
//        		  if(!leituraTotalizadorFinanceiro(csv1).get(i).getCodItem().equals(leituraTotalizadorFinanceiro(csv1).get(z).getCodAntItem())) {
////        				System.out.println(leituraTotalizadorFinanceiro(csv1).get(i).getCodItem()+"|"+
////        						leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem());      				
//        				System.out.println(
//        						leituraTotalizadorFinanceiro(csv1).get(i).getCodItem()+"|"+
//        				        leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()+"|"+
//        				        leituraTotalizadorFinanceiro(csv1).get(i).getDescricao()+"|"+
//        		 fichaTotalizada(csv1, leituraTotalizadorFinanceiro(csv1).get(i).getCodItem(), leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()).getQtdeEnt()
//        						 +"|"+fichaTotalizada(csv1, leituraTotalizadorFinanceiro(csv1).get(i).getCodItem(), leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem()).getQtdeSai());       				
//        				break;
//        		  }
//        	  }
//        }
		
	    Map<String,String> mpLinhas = new HashMap<String,String>();
		for(String s : linhasTotalizadorFinanceiro(csv1)) {
			String cod = "";
			String[] campo = s.split("\\;");
			for(int i = 0; i < campo.length; i++){
				if(i == 1) {
					cod = campo[i];
					mpLinhas.put(cod, s);
					//System.out.println(cod +" => "+ s);
				}
			}  
		}
		
		for (int i = 0; i < leituraTotalizadorFinanceiro(csv1).size(); i++) {
		
//			if(mpLinhas.get(leituraTotalizadorFinanceiro(csv1).get(i).getCodItem()) == null) {
//				
//				
//				 System.out.println(leituraTotalizadorFinanceiro(csv1).get(i).getCodItem()+"|"+leituraTotalizadorFinanceiro(csv1).get(i).getCodAntItem());
//			}
		}
	   
	}
	
	
	
}
