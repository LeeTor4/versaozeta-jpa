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

public class AlteraTxtSped {

    public static Map<String,NumeroChave> getNumeroChaves(String p3) throws FileNotFoundException{
    
    	Map<String,NumeroChave> retorno = new HashedMap<String, NumeroChave>();
	    File arquivoCSV = new File(p3);
	    String linhaDoArquivo = new String();
	    
	    @SuppressWarnings("resource")
		Scanner leitor = new Scanner(arquivoCSV);
	    leitor.nextLine();
		 while(leitor.hasNext()){
			 NumeroChave obj = new NumeroChave();
			 linhaDoArquivo = leitor.nextLine();
			 String[] valoresEntreVirgula = linhaDoArquivo.split("\\;");	 
			 for(int i=0;i<valoresEntreVirgula.length;i++){
				 
				    if(i==0){
				    	obj.setChave(valoresEntreVirgula[0]);
				    }
				    if(i==1) {
				    	obj.setNumero("|00|001|".concat(valoresEntreVirgula[1]));
				    }
				   
				   
			 }
			 retorno.put(obj.getNumero(),obj);
		 }
    	
    	return retorno;
    }
    
    public static String getLinha( List<String> lines,String num, String chave) {
    	String retorno = "";
    	for (int i = 0; i < lines.size(); i++) {
    		
    		if (lines.get(i).contains(num)) {
    			
				String[] campos = lines.get(i).split("\\|");

				for (int z = 0; z < campos.length; z++) {

					if (z == 9) {
						campos[z] = chave;
					}

					retorno += campos[z] + "|";
				}
				//System.out.println(" => " + retorno);
    			break;
    		}
    		
    	}
    	
    	return retorno;
    }
    
	public static void main(String[] args) throws Exception {
		
		String ano = "2015";
		String emp = "SELLENE";
		String estab = "MATRIZ";
		String cnpj  = "05329222000176";
		
		String anomes1  = ano.concat("01").concat(".txt");
		String anomes2  = ano.concat("02").concat(".txt");
		String anomes3  = ano.concat("03").concat(".txt");
		String anomesV3  = ano.concat("03_V2").concat(".txt");
		String anomes4  = ano.concat("04").concat(".txt");
		String anomesV4  = ano.concat("04_V2").concat(".txt");
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
	    Path p2 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes2));
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
	    
	    Path csv  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat("Matriz_201512_serie2.csv"));
	    Path dest = pV12;
	    List<String> novasLinhas =  new ArrayList<String>();   
	    List<String> lines = Files.readAllLines(p12, StandardCharsets.ISO_8859_1);
	    String linha = "";
	    String chave = "";
//	   for(int i=0; i < lines.size(); i++) {		  
//		   novasLinhas.add(lines.get(i));
//		  
//		   
//			   if(lines.get(i).contains("|4315|")){
//				   
//				  String[] campos = lines.get(i).split("\\|");
//				  
//				  for(int z=0; z < campos.length; z++){
//					  
//					  if(z==9) {
//						  campos[z] = "23150105329222000176550020000043151000140009";
//					  }
//					  
//					 
//					  linha += campos[z]+"|" ;
//					 
//				  }
//				  
//				  System.out.println(" => " + linha);			  
//				
//				  // String novoConteudo = lines.get(i).replace("9999", "XXXX");	
//				   String novoConteudo = linha;	
//				  
//				   novasLinhas.remove(i);
//				   novasLinhas.add(i, novoConteudo);
//				 
//			   }
//
//	   }	
//	   
//	   
//	   Files.write(p2, novasLinhas, StandardOpenOption.CREATE);

      
       
		for (int i = 0; i < lines.size(); i++) {
			novasLinhas.add(lines.get(i));
			if (lines.get(i).contains("|00|001|")) {

				String[] campoC100 = lines.get(i).split("\\|");

				for (int c = 0; c < campoC100.length; c++) {

					if (c == 9) {
						if (getNumeroChaves(csv.toString())
								.get("|00|001|".concat(campoC100[c].substring(30, 34))) != null) {
							// System.out.println(getNumeroChaves(p3.toString()).get("|00|001|".concat(campoC100[c].substring(30,34))).getChave());
							// campoC100[c] =
							// getNumeroChaves(p3.toString()).get("|00|001|".concat(campoC100[c].substring(30,34))).getChave();
                           

						    System.out.println(getLinha(lines, "|00|001|".concat(campoC100[c].substring(30,34)), getNumeroChaves(csv.toString()).get("|00|001|".concat(campoC100[c].substring(30,34))).getChave()));
						    String novoConteudo = getLinha(lines, "|00|001|".concat(campoC100[c].substring(30,34)), getNumeroChaves(csv.toString()).get("|00|001|".concat(campoC100[c].substring(30,34))).getChave());	
						    novasLinhas.remove(i);
						    novasLinhas.add(i, novoConteudo);
						}
					}

				}

			}

		}	
   
       Files.write(dest, novasLinhas, StandardOpenOption.CREATE);
	}
	
	


}


