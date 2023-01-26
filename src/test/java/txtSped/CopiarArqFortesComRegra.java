package txtSped;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CopiarArqFortesComRegra {
	
    public static String getQtdeDeLinha(List<String> lines,int i,String reg, String qtdeLinha) {
    	String retorno = "";
    	 if(lines.get(i).startsWith(reg)){
    		 String[] campos = lines.get(i).split("\\|");
    		 for (int z = 0; z < campos.length; z++) {
    			 if (z == 3) {
    				 retorno = qtdeLinha;
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
	    
	    Path csv  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat("Megadiet_201702.csv"));
	    Path dest = pV2;
	    List<String> novasLinhas =  new ArrayList<String>();   
	    List<String> lines = Files.readAllLines(p2, StandardCharsets.ISO_8859_1);
	    String linha = "";
	    String chave = "";
	    
	    int contC100 = 0;
	    int contC170 = 0;
	    int contC190 = 0;
	    for (int i = 0; i < lines.size(); i++) {
	    	 novasLinhas.add(lines.get(i));
	    	 

	    	 if(lines.get(i).startsWith("|C100|")){
		    	 
	    		 if(!lines.get(i).startsWith("|C100|1|0|")){
	    			 contC100++;
		    		 System.out.println(lines.get(i)); 
		    	 }
		    	 
	    	 }
	    	 
	    	 if(lines.get(i).startsWith("|C170|")){
		    	 if(lines.get(i).startsWith("|C170|")){
		    		 contC170++;
		    		 System.out.println(lines.get(i));  
		    	 }
	    	 }
    	 
	    	 if(lines.get(i).startsWith("|C190|")){
	    		 if(!lines.get(i).substring(10, 11).equals("5") && !lines.get(i).substring(10, 11).equals("6")) {
	    			 contC190++;
	    			 System.out.println(lines.get(i));
	    		 }
	    	 }
	    	 
	    	 
	    	 
	    	//Trecho de código dos totalizadores dos registros
	    	 if(lines.get(i).startsWith("|9900|")){
	    		 
	    		 if(!lines.get(i).substring(0,11).equals("|9900|C100|") && !lines.get(i).substring(0,11).equals("|9900|C170|")
	    				 && !lines.get(i).substring(0,11).equals("|9900|C190|") 
	    				 && !lines.get(i).substring(0,11).equals("|9900|C400|")
	    				 && !lines.get(i).substring(0,11).equals("|9900|C405|")
	    				 && !lines.get(i).substring(0,11).equals("|9900|C420|")
	    				 && !lines.get(i).substring(0,11).equals("|9900|C425|")
	    				 && !lines.get(i).substring(0,11).equals("|9900|C490|")) {	    
	    			 
	    			 System.out.println(lines.get(i));
	    		 }
	    		 
	    		 
	    		 if(lines.get(i).substring(0,11).equals("|9900|C100|")) {	    			
	    			"|9900|C100|".concat(getQtdeDeLinha(lines, i, lines.get(i).substring(0,11),String.valueOf(contC100)));
	    			 System.out.println("|9900|C100|".concat(getQtdeDeLinha(lines, i, lines.get(i).substring(0,11),String.valueOf(contC100))).concat("|"));
	    		 }
	    		 
	    		 if(lines.get(i).substring(0,11).equals("|9900|C170|")) {	    			
	    			 "|9900|C170|".concat(getQtdeDeLinha(lines, i, lines.get(i).substring(0,11),String.valueOf(contC170)));
	    			 System.out.println("|9900|C170|".concat(getQtdeDeLinha(lines, i, lines.get(i).substring(0,11),String.valueOf(contC170))).concat("|"));
	    		 }
	    		 
	    		 if(lines.get(i).substring(0,11).equals("|9900|C190|")) {	    			
	    			 "|9900|C190|".concat(getQtdeDeLinha(lines, i, lines.get(i).substring(0,11),String.valueOf(contC190)));
	    			 System.out.println("|9900|C190|".concat(getQtdeDeLinha(lines, i, lines.get(i).substring(0,11),String.valueOf(contC190))).concat("|"));
	    		 }
	    		
	    	 }

	    }
	}

}
