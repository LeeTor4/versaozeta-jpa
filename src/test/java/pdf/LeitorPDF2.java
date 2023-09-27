package pdf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class LeitorPDF2 {

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		
		String ano = "2015";
		String emp = "SELLENE";
		String estab = "MATRIZ";
		String cnpj  = "05329222000176";
		
		List<String> novasLinhas =  new ArrayList<String>();   
		File file = new File("C:\\Users\\chico\\Downloads\\PRODUTOS AGRUPADOS SELLENE 2013.pdf");
	    Path pV12 = Paths.get("C:\\Users\\chico\\Downloads\\PRODUTOS AGRUPADOS SELLENE 2013_alterado.txt");
		Path dest = pV12;
		 try (PDDocument document = PDDocument.load( file )) {
			 
             String header1 = "Auditor Eletrônico";
             String header2 = "Secretaria da Fazenda do Estado do Ceará";
             String header3 = "Levantamento Quantitativo Financeiro Mensal";
             String header4 = "Coordenadoria de Administração Tributária";
             String header5 = "Tipo de Execução:";
             String header6 = "Agrupamento de Produtos";
             String footer = "Tipo";
			 
			  if (!document.isEncrypted()) {
				  
				 PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                 stripper.setSortByPosition(true);


                 PDFTextStripper tStripper = new PDFTextStripper();
                 tStripper.setStartPage(1);
                 tStripper.setEndPage(6849);

                 String pdfFileInText = tStripper.getText(document);

                 String lines[] = pdfFileInText.split("\\r?\\n");
                 
                 for (String line : lines) {
                	     
                	// System.out.println(line + " =>  " + line.length());
//                	 if(!line.contains(header1)) {
//                		 if(!line.contains(header2)) {
//                			 if(!line.contains(header3)) {
//                				 if(!line.contains(header4)) {
//                					 if(!line.contains(header5)) {
//                						 if(!line.contains(header6)) {
//                    						 if(!line.contains(footer)) {
//                    							 System.out.println(line + " =>  " + line.length());
//                    		            		 novasLinhas.add(line);
//                    						 }
//                						 }
//
//                					 }
//                				 }
//                			 }
//                		 }
//                	 }

                		 
                 }  
			  }
		 }
		 
		// Files.write(dest, novasLinhas, StandardOpenOption.CREATE);
		 
		 int x=(int)'A'; 
		 int y=(int)'a'; 
		 //System.out.println("ASCII " + y);
		 int cont = 0;
		 String str = "BCAA PLUS 800 C/120 CAPS";
		 List<Integer> st1 = new ArrayList<Integer>();
		 for(int i=0;i<str.length();i++){
			 char c = str.charAt(i);
			 int x1 = (int) c;
			 System.out.println(c +"=>"+ x1);
			 st1.add(x1);
	     }
		 
		 String str2 = "BCAA PLUS 800 MG C/120 CAPS";
		 int tam = str2.length() + st1.size();
		 for(int i=0;i<tam;i++){
			 if(i < str2.length()) {
				 System.out.println(i);
				 char c = str2.charAt(i);
				 int x1 = (int) c;
				 if(st1.contains(x1)) {
					 System.out.println(c +"=>"+ x1);
				 }else {
					 cont++;
					 System.out.println(" Não contem =>"+ x1);
				 }
			 }
			
		 }
		 
		 System.out.println("Percentual " + cont);
		 System.out.println("Percentual " + str.length());
		 
		 Integer.valueOf(cont);
		 Integer.valueOf(str.length());
		 
		 Long res = (long) ((cont*100)/(str.length()*100));
		 
		 System.out.println("Percentual " + res);			 
		 
	}
     
}
