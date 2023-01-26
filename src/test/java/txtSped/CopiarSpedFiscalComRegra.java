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

public class CopiarSpedFiscalComRegra {
	

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
				    	obj.setNumero(valoresEntreVirgula[1]);
				    }
				   
				   
			 }
			 retorno.put(obj.getNumero(),obj);
		 }
    	
    	return retorno;
    }
    
    public static String getLinha( List<String> lines,String modDoc,String num, String chave) {
    	String retorno = "";
    	for (int i = 0; i < lines.size(); i++) {
    		
    		if(lines.get(i).startsWith("NFM")) {
	    		if (lines.get(i).contains(num.concat("|"))) {
	    			
					String[] campos = lines.get(i).split("\\|");
	
					for (int z = 0; z < campos.length; z++) {
						if (z == 3) {
							campos[z] = modDoc;
						}
						if (z == 67) {
							campos[z] = chave;
						}
	
						retorno += campos[z] + "|";
					}
					//System.out.println(" => " + retorno);
	    			break;
	    		}	
    		}
	
    	}	
    	return retorno;
    }
    
    
	public static void main(String[] args) throws Exception{
		String ano = "2017";
		String emp = "SELLENE";
		String estab = "MEGADIET";
		String cnpj  = "05329222000419";
		
		String anomes1  = ano.concat("01").concat(".txt");
		String anomesV1  = ano.concat("01_V2").concat(".txt");
		String anomesfs  = ano.concat("01_v2").concat(".fs");
		String anomesfsV2  = ano.concat("01_v3").concat(".fs");
		
		String anomes2  = ano.concat("02").concat(".txt");
		String anomesV2  = ano.concat("02_V2").concat(".txt");
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
	    Path pV1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV1));
	   
	    Path pVFOrig = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesfs));
	    Path pVFDest = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesfsV2));
	    
	    Path p2 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes2));
	    Path pV2 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV2));
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
	    
	    Path csv  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat("Megadiet_201701.csv"));
	    Path dest = pVFDest;
	    List<String> novasLinhas =  new ArrayList<String>();   
	    List<String> lines = Files.readAllLines(pVFOrig, StandardCharsets.ISO_8859_1);
	    String linha = "";
	    String chave = "";
	    
	    int cont = 0;
	    int tra = 0;
	    for (int i = 0; i < lines.size(); i++) {
	    	
	   	     novasLinhas.add(lines.get(i));

	    	 if(lines.get(i).startsWith("NFM|0003|E|NF1|")) {
	    		 cont = 0;
	    	 }else if(lines.get(i).startsWith("NFM|0003|E|NFE|")){
	    		 cont = -1000;	    		 
	    	 }else {
	    		 cont++; 
	    	 }

	    	 if(cont >= 0) { 
	    		 tra++;
	    		
	    		 System.out.println(lines.get(i)); 
	    		
	    		 if (lines.get(i).startsWith("NFM|0003|E|NF1|")){

	    		
	    			 String[] campoC100 = lines.get(i).split("\\|");
	 				 String numeros = "";
	 				 for (int c = 0; c < campoC100.length; c++) {
	 					if(c == 8) {
							//System.out.println(campoC100[c]);
							numeros = campoC100[c];
							numeros = Integer.valueOf(numeros).toString();
							//System.out.println(numeros);
						}
	 					if(c == 67) {
	 						   if(getNumeroChaves(csv.toString()).get(numeros) != null) {
	 							  //System.out.println(getNumeroChaves(csv.toString()).get(numeros).getChave());
	 							  String novoConteudo = getLinha(lines,"NFE",numeros, getNumeroChaves(csv.toString()).get(numeros).getChave());	
	 							  	 					
	 							  novasLinhas.remove(i);
	 							  novasLinhas.add(i, novoConteudo);
	 							 
	 						   } 
	 						 
						}
	 				 }
	    		 }
	    		
	    	 }
	    	 
	    	 
//	    	 if (lines.get(i).startsWith("TRA|")){
//		    	 //System.out.println("TRA|".concat(String.valueOf(tra))); 
//			     novasLinhas.add("TRA|".concat(String.valueOf(tra)));
//	    	 }

	    }
	   
	     Files.write(dest, novasLinhas, StandardOpenOption.CREATE);
	}

}
