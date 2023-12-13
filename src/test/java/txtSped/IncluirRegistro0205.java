package txtSped;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.collections4.map.HashedMap;

public class IncluirRegistro0205 {
	
	
	public static List<String> removerAssinaturaEfdIcms(List<String> lines) throws FileNotFoundException{
		  List<String> novasLinhas =  new ArrayList<String>();   
			for (int i = 0; i < lines.size(); i++) {
				if (lines.get(i).startsWith("|")) {
					
					novasLinhas.add(lines.get(i));					
				}
			}
			System.out.println("Assinatura removida com sucesso!!!");
			return novasLinhas;
	}
	 public static List<String> geraArquivoAtualizado0205(List<String> lines,String file) throws FileNotFoundException{
		    List<String> novasLinhas =  new ArrayList<String>();   
		    mpIndiceReg0200(lines);
		    mpIndiceReg9999(lines);
		    int cont = 0;
		   
			for (int i = 0; i < lines.size(); i++) {
				
				if(!lines.get(i).startsWith("|9999|")) {
					novasLinhas.add(lines.get(i));
				}
				
				
				
				if (lines.get(i).startsWith("|0200|")) {
					
					String[] campo0200 = lines.get(i).split("\\|");
					
					for (int c = 0; c < campo0200.length; c++) {
						if(c == 2) {
							String codItem = "";
							codItem = campo0200[c];	
							if(getCoditoDePara(file).get(codItem) != null) {
								cont++;
									indiceReg0200(mpIndiceReg0200(lines), codItem);
									String novoConteudo = 
											getLinhaReg0200(getCoditoDePara(file).get(codItem).getCodigoPara(),
													mpRegistro0200(lines).get(codItem).getDESCR_ITEM(), 
													mpRegistro0200(lines).get(codItem).getCOD_BARRA(),
													mpRegistro0200(lines).get(codItem).getCOD_ANT_ITEM(),
													mpRegistro0200(lines).get(codItem).getUNID_INV(),
													mpRegistro0200(lines).get(codItem).getTIPO_ITEM(),
													mpRegistro0200(lines).get(codItem).getCOD_NCM(),
													mpRegistro0200(lines).get(codItem).getEX_IPI(),
													mpRegistro0200(lines).get(codItem).getCOD_GEN(),
													(mpRegistro0200(lines).get(codItem).getCOD_LST()==null?"":mpRegistro0200(lines).get(codItem).getCOD_LST()),
													(mpRegistro0200(lines).get(codItem).getALIQ_ICMS()==null?"":mpRegistro0200(lines).get(codItem).getALIQ_ICMS()),
													(mpRegistro0200(lines).get(codItem).getCEST()==null?"":mpRegistro0200(lines).get(codItem).getCEST()));
								    
									
									novasLinhas.add(indiceReg0200(mpIndiceReg0200(lines), codItem),
								    		 novoConteudo.concat("\n").concat(getLinhaReg0205(getCoditoDePara(file).get(codItem).getCodigoDeMatriz())).concat("|"));
								    novasLinhas.remove(indiceReg0200(mpIndiceReg0200(lines), codItem)-1);
							
								    

//								String novoConteudo0200 = getCoditoDePara(file).get(codItem).getCodigoPara();
//								 
//								 novasLinhas.add(i,novoConteudo0200);
//								 novasLinhas.remove(i-1);
								 
								//String novoConteudo2 = getLinhaReg0205(getCoditoDePara(file).get(codItem).getCodigoDeMatriz());
								//novasLinhas.add(indiceReg0200(mpIndiceReg0200(lines), codItem),novoConteudo2.concat("|"));  /*ajustes conforme layout*/
								//System.out.println((indiceReg0200(mpIndiceReg0200(lines), codItem)) + " = " + i + " = " + cont + " = " + codItem + "= " + getCoditoDePara(file).get(codItem).getCodigoDeMatriz());

							}
							 
						}
											
						

					}
					
					
				}
				
				//Bloco final 0990
				if (lines.get(i).startsWith("|0990|")) {
			 
					String[] campo0990 = lines.get(i).split("\\|");
					for (int c = 0; c < campo0990.length; c++) {
						if(c == 2) {
							int totalizador09900 = Integer.parseInt(campo0990[c]);
							int res = totalizador09900 + cont;
							String novoConteudo = "|0990|".concat(String.valueOf(res)).concat("|");
							novasLinhas.remove(i);
							novasLinhas.add(novoConteudo);  /*ajustes conforme layout*/
						}
					}
				}

				if (lines.get(i).startsWith("|9900|0200|")) {
					String novoConteudo =  "|9900|0205|".concat(String.valueOf(cont)).concat("|");
					novasLinhas.add(novoConteudo);  /*ajustes conforme layout*/
				}
				
				
//				int res = 0;
//				if (lines.get(i).startsWith("|9999|")) {
//					String[] campo9999 = lines.get(i).split("\\|");
//					
//					for (int c = 0; c < campo9999.length; c++) {
//						if(c == 2) {
//							int totalizador9999 = Integer.parseInt(campo9999[c]);
//							res = totalizador9999;
//							
//						}
//						
//					}
//					
//					
//				}
//				
//				String novoConteudo = "|9999|".concat(String.valueOf(res+cont+1)).concat("|");
//				int pos = mpIndiceReg9999(lines).get("9999");
//				novasLinhas.remove(pos);
//				novasLinhas.add(pos,novoConteudo);
//
//				System.out.println("Posição final " + i + " = " + lines.get(i) + " = " + cont + " = " + res + " = " + (res+cont+1));
	           
//				if (lines.get(i).startsWith("|9900|9900|")) {
//
//					String[] campo99009900 = lines.get(i).split("\\|");
//					for (int c = 0; c < campo99009900.length; c++) {
//						
//						if(c == 3) {
//							int totalizador99009900 = Integer.parseInt(campo99009900[c]);
//							//System.out.println(i+3 + " => "+ totalizador99009900);
//							String novoConteudo = "|9900|9900|".concat(String.valueOf(totalizador99009900)).concat("|");
//							novasLinhas.remove(i);
//						    novasLinhas.add(novoConteudo);  
//						}
//					}
//				}
			
               
				
				//Blocos finais
//				if (lines.get(i).startsWith("|9900|9900|")) {
	//
//					String[] campo99009900 = lines.get(i).split("\\|");
//					for (int c = 0; c < campo99009900.length; c++) {
//						
//						if(c == 3) {
//							int totalizador99009900 = Integer.parseInt(campo99009900[c]);
//							//System.out.println(i + " => "+ totalizador99009900);
//							String novoConteudo = "|9900|9900|".concat(String.valueOf(totalizador99009900)).concat("|");
//							//novasLinhas.add(i, novoConteudo );  
//						}
//					}
//				}
	//
//				
//				if (lines.get(i).startsWith("|9990|")) {
//					String[] campo9990 = lines.get(i).split("\\|");
//					for (int c = 0; c < campo9990.length; c++) {
//						if(c == 2) {
//							int totalizador9990 = Integer.parseInt(campo9990[c]);
//							//System.out.println(totalizador9990);
//						}
//					}
//				}
//				
//				if (lines.get(i).startsWith("|9999|")) {
//					String[] campo9999 = lines.get(i).split("\\|");
//					for (int c = 0; c < campo9999.length; c++) {
//						if(c == 2) {
//							int totalizador9999 = Integer.parseInt(campo9999[c]);
//							//System.out.println(totalizador9999);
//						}
//					}
//				}
				if (lines.get(i).startsWith("|9900|9900|")) {
					String[] campo9900 = lines.get(i).split("\\|");
					novasLinhas.remove(lines.get(i));
					int totalizador9900=0;
					for (int c = 0; c < campo9900.length; c++) {
						if (c == 3) {
						    totalizador9900 = Integer.parseInt(campo9900[c]);
						}
					}
					novasLinhas.add("|9900|9900|".concat(String.valueOf(totalizador9900+1).concat("|")));
				}
				if (lines.get(i).startsWith("|9990|")) {
					String[] campo9990 = lines.get(i).split("\\|");
					novasLinhas.remove(lines.get(i));
					int totalizador9990=0;
					for (int c = 0; c < campo9990.length; c++) {
						if (c == 2) {
						    totalizador9990 = Integer.parseInt(campo9990[c]);
						}
					}
					novasLinhas.add("|9990|".concat(String.valueOf(totalizador9990+1).concat("|")));
				}
				if(lines.get(i).startsWith("|9999|")) {
					//novasLinhas.remove(lines.get(i));
					novasLinhas.remove(lines.get(i));
					novasLinhas.remove(lines.get(i));
					novasLinhas.remove(lines.get(i));

					System.out.println("Posição = " + i + " = " + lines.get(i)+ " = " + mpIndiceReg9999(lines).get("9999"));				
					String[] campo9999 = lines.get(i).split("\\|");
					for (int c = 0; c < campo9999.length; c++) {
						if(c == 2) {
							int totalizador9999 = Integer.parseInt(campo9999[c]);
							novasLinhas.add("|9999|".concat(String.valueOf(totalizador9999+cont+1).concat("|")));
						}
					}
				
				}
				
				
				
				
			}
		    System.out.println("Arquivo gerado com sucesso!!");
			return novasLinhas;
	 }

    public static Map<String,CodigosDePara> getCoditoDePara(String p3) throws FileNotFoundException{
    
    	Map<String,CodigosDePara> retorno = new HashedMap<String, CodigosDePara>();
	    File arquivoCSV = new File(p3);
	    String linhaDoArquivo = new String();
	    
	    @SuppressWarnings("resource")
		Scanner leitor = new Scanner(arquivoCSV);
	    leitor.nextLine();
		 while(leitor.hasNext()){
			 CodigosDePara obj = new CodigosDePara();
			 linhaDoArquivo = leitor.nextLine();
			 String[] valoresEntreVirgula = linhaDoArquivo.split("\\;");	 
			 for(int i=0;i<valoresEntreVirgula.length;i++){
				 
				    if(i==0){
				    	obj.setCodigoDe(valoresEntreVirgula[0]);
				    	
				    }
				    if(i==1) {
				    	obj.setCodigoPara(valoresEntreVirgula[1]);
				    }
				    if(i==2) {
				    	obj.setCodigoDeMatriz(valoresEntreVirgula[2]);
				    }
				   
			 }
			 retorno.put(obj.getCodigoDe(),obj);
		 }
    	
    	return retorno;
    }
    
    public static Map<String,CodigosDePara> getCodigoLojaMatriz(String p3) throws FileNotFoundException{
        
    	Map<String,CodigosDePara> retorno = new HashedMap<String, CodigosDePara>();
	    File arquivoCSV = new File(p3);
	    String linhaDoArquivo = new String();
	    
	    @SuppressWarnings("resource")
		Scanner leitor = new Scanner(arquivoCSV);
	    leitor.nextLine();
		 while(leitor.hasNext()){
			 CodigosDePara obj = new CodigosDePara();
			 linhaDoArquivo = leitor.nextLine();
			 String[] valoresEntreVirgula = linhaDoArquivo.split("\\;");	 
			 for(int i=0;i<valoresEntreVirgula.length;i++){
				 
				    if(i==0){
				    	obj.setCodigoDe(valoresEntreVirgula[0]);
				    }
				    if(i==1) {
				    	obj.setCodigoPara(valoresEntreVirgula[1]);
				    }
				   
				   
			 }
			 retorno.put(obj.getCodigoPara(),obj);
		 }
    	
    	return retorno;
    }
    
    public static String getLinhaReg0200(String codigoItem,String descricao,String codigobarra
    		,String codigoantitem,String unidunv,String tipoitem,String codigoncm
    		,String exipi,String codigogen,String codigolst,String aliqicms,String cest) {
    	
    	String retorno = "|0200".
			       concat("|").
			       concat(codigoItem).
			       concat("|").
			       concat(descricao).
			       concat("|").
			       concat(codigobarra).
			       concat("|").
			       concat(codigoantitem).
			       concat("|").
			       concat(unidunv).
			       concat("|").
			       concat(tipoitem).
			       concat("|").
			       concat(codigoncm).
			       concat("|").
			       concat(exipi).
			       concat("|").
			       concat(codigogen).
			       concat("|").
			       concat(codigolst).
			       concat("|").
			       concat(aliqicms).
			       concat("|").
			       concat(cest).
			       concat("|");
    	
    	return retorno;    	
    }
    public static String getLinhaReg0205(String codigoItem) {
    	
    	String retorno = "|0205".
    			       concat("|").
    			       concat("|").
    			       concat("01012010").
    			       concat("|").
    			       concat("31122022").
    			       concat("|").
    			       concat(codigoItem);
    	     
    	return retorno;
    }
    
    public static Map<String,Registro0200> mpRegistro0200(List<String> lines) {
    	Map<String,Registro0200> retorno = new HashedMap<String, Registro0200>();
        
    	for (int i = 0; i < lines.size(); i++) {
    		
    		if (lines.get(i).startsWith("|0200|")) {
    			String[] campo0200 = lines.get(i).split("\\|");
    			Registro0200 obj = new Registro0200();
				for (int c = 0; c < campo0200.length; c++) {
					
                    if(c == 1) {
                    	obj.setREG(campo0200[c]);
					}
					if(c == 2) {	
						obj.setCOD_ITEM(campo0200[c]);
						
					}
                    if(c == 3) {
                    	obj.setDESCR_ITEM(campo0200[c]);
					}
					if(c == 4) {
						obj.setCOD_BARRA(campo0200[c]);
					}
                    if(c == 5) {
						obj.setCOD_ANT_ITEM(campo0200[c]);
					}
					if(c == 6) {
						obj.setUNID_INV(campo0200[c]);
						
					}
                    if(c == 7) {
                    	obj.setTIPO_ITEM(campo0200[c]);
					}
					if(c == 8) {
						
						obj.setCOD_NCM(campo0200[c]);
						
					}
                    if(c == 9) {
                    	obj.setEX_IPI(campo0200[c]);
                    	
					}
					if(c == 10) {
						obj.setCOD_GEN(campo0200[c]);
					}
                    if(c == 11) {
                    	obj.setCOD_LST(campo0200[c]);
						
					}
					if(c == 12) {
						obj.setALIQ_ICMS(campo0200[c]);
					}
					if(c == 13) {
						obj.setCEST(campo0200[c]);
						
					}
					
				}
				retorno.put(obj.getCOD_ITEM(), obj);
    		}
    	}
    	
    	return retorno;
    }
    
    
    public static Map<String,Integer> mpIndiceReg0200(List<String> lines) {
    	Map<String,Integer> retorno = new HashedMap<String, Integer>();
    	for (int i = 0; i < lines.size(); i++) {
    		
    		if (lines.get(i).startsWith("|0200|")) {
    			String[] campo0200 = lines.get(i).split("\\|");

				for (int c = 0; c < campo0200.length; c++) {
					if(c == 2) {
						retorno.put(campo0200[c], i+1);
					}
				}
    			
    		}
    	}
    	
    	return retorno;
    }
    
    public static int indiceReg0200(Map<String,Integer> mpcodigo,String codigo) {
    	int integer = mpcodigo.get(codigo);   	
    	return integer;
    }
    
    public static Map<String,Integer> mpIndiceReg0990(String reg,Integer i) {
    	Map<String,Integer> retorno = new HashedMap<String, Integer>();
    	retorno.put(reg, i+1);
    	return retorno;
    }
    
    public static Map<String,Integer> mpIndiceReg9999(List<String> lines) {
    	Map<String,Integer> retorno = new HashedMap<String, Integer>();
    	for (int i = 0; i < lines.size(); i++) {
    		
    		if (lines.get(i).startsWith("|9999|")) {
    			String[] campo9999 = lines.get(i).split("\\|");

				for (int c = 0; c < campo9999.length; c++) {
					if(c == 1) {
						retorno.put(campo9999[c], i);
						System.out.println("Chave " + campo9999[c]);
					}
				}
    			
    		}
    	}
    	
    	return retorno;
    }
	public static void main(String[] args) throws Exception {
		
		String ano = "2023";
		String emp = "SELLENE";
		String estab = "MATRIZ";
		String cnpj  = "05329222000176";
		
		String anomes1  = ano.concat("01").concat(".txt");
		String anomesV1  = ano.concat("01_reg0205").concat(".txt");
		String anomes1SA  = ano.concat("01_semassinatura").concat(".txt");
		String anomes2  = ano.concat("02").concat(".txt");
		String anomesV2  = ano.concat("02_reg0205").concat(".txt");
		String anomes2SA  = ano.concat("02_semassinatura").concat(".txt");
		String anomes3  = ano.concat("03").concat(".txt");
		String anomesV3  = ano.concat("03_V2").concat(".txt");
		String anomes3SA  = ano.concat("01_semassinatura").concat(".txt");
		String anomes4  = ano.concat("04").concat(".txt");
		String anomesV4  = ano.concat("04_V2").concat(".txt");
		String anomes4SA  = ano.concat("01_semassinatura").concat(".txt");
		String anomes5  = ano.concat("05").concat(".txt");
		String anomesV5  = ano.concat("05_V2").concat(".txt");
		String anomes6  = ano.concat("06").concat(".txt");
		String anomesV6  = ano.concat("06_V2").concat(".txt");
		String anomes7  = ano.concat("07").concat(".txt");
		String anomesV7  = ano.concat("07_V2").concat(".txt");
		String anomes8  = ano.concat("08").concat(".txt");
		String anomesV8  = ano.concat("08_V2").concat(".txt");
		String anomes9  = ano.concat("09").concat(".txt");
		String anomesV9  = ano.concat("09_V2").concat(".txt");
		String anomes10 = ano.concat("10").concat(".txt");
		String anomesV10 = ano.concat("10_V2").concat(".txt");
		String anomes11 = ano.concat("11").concat(".txt");
		String anomesV11 = ano.concat("11_V2").concat(".txt");
		String anomes12 = ano.concat("12").concat(".txt");
		String anomesV12 = ano.concat("12_V2").concat(".txt");
		
	    Path p1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes1));
	    Path pV1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV1));
	    Path pV1SA = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes1SA));
	    Path p2 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes2));
	    Path pV2 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV2));
	    Path pV2SA = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes2SA));
	    Path p3 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes3));
	    Path pV3 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV3));
	    Path p4 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes4));
	    Path pV4 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV4));
	    Path p5 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes5));
	    Path pV5 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV5));
	    Path p6 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes6));
	    Path pV6 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV6));
	    Path p7 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes7));
	    Path pV7 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV7));
	    Path p8 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes8));
	    Path pV8 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV8));
	    Path p9 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes9));
	    Path pV9 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV9));
	    Path p10 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes10));
	    Path pV10 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV10));
	    Path p11 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes11));
	    Path pV11 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV11));
	    Path p12 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes12));
	    Path pV12 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV12));
	    
	    Path csv  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat("Megadiet_201702.csv"));
	    Path dest = pV1; //Setar aqui
	    Path dest2 = pV1SA;//Setar aqui
	   
	    List<String> lines = Files.readAllLines(p2, StandardCharsets.ISO_8859_1);//Seta o arquivo origem sped
	    String linha = "";
	    String chave = "";
	
//	    List<String> novasLinhas2 = removerAssinaturaEfdIcms(lines);
//        Files.write(dest2, novasLinhas2, StandardOpenOption.CREATE);
        
        
        List<String> novalines = Files.readAllLines(dest2, StandardCharsets.ISO_8859_1);//Seta o arquivo origem sped
	    Path fileCsvDePara  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\Codigos_De_Para.csv"));
        mpRegistro0200(novalines);
	    List<String> novasLinhas = geraArquivoAtualizado0205(novalines,fileCsvDePara.toString());
        Files.write(dest, novasLinhas, StandardOpenOption.CREATE);
        
        
        
        
        
        
        
        
        
        
        
	
//        for(String key : mpRegistro0200(novalines).keySet()) {
//        	System.out.println(key + " = " + mpRegistro0200(novalines).get(key).getCOD_LST());
//        }
        
//          for(String key : getCoditoDePara(fileCsvDePara.toString()).keySet()) {
//        	  
//        	  if(key.equals("270")) {
//        		  getCoditoDePara(fileCsvDePara.toString()).get(key).getCodigoDe();
//        		  System.out.println(getCoditoDePara(fileCsvDePara.toString()).get(key).getCodigoDe()
//        				    + " = " + getCoditoDePara(fileCsvDePara.toString()).get(key).getCodigoDeMatriz());
//        	  }
//          }
	
	}
	
}


