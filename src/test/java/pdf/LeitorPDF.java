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

public class LeitorPDF {

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		
		String ano = "2015";
		String emp = "SELLENE";
		String estab = "MATRIZ";
		String cnpj  = "05329222000176";
		
		List<String> novasLinhas =  new ArrayList<String>();   
		File file = new File("C:\\Users\\chico\\Downloads\\SELLENE_QUANTITATIVO_FINANCEIRO _2012.pdf");
	    Path pV12 = Paths.get("C:\\Users\\chico\\Downloads\\SELLENE_QUANTITATIVO_FINANCEIRO _2012_alterado.txt");
		Path dest = pV12;
		 try (PDDocument document = PDDocument.load( file )) {
			 
			  if (!document.isEncrypted()) {
				  
				 PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                 stripper.setSortByPosition(true);


                 PDFTextStripper tStripper = new PDFTextStripper();
                 tStripper.setStartPage(1);
                 tStripper.setEndPage(6849);
                 
            
                 
                 String pdfFileInText = tStripper.getText(document);

                 String lines[] = pdfFileInText.split("\\r?\\n");
                 
                 String header1 = "Secretaria da Fazenda do Estado do Ceará";
                 String header2 = "Coordenadoria de Administração Tributária";
                 String header3 = "Levantamento Quantitativo Financeiro Mensal";
                 String header4 = "SELLENE COMERCIO E REPRESENTACOES LTDA";
                 String header5 = "Tipo de Execução:";
                 String footer = "Auditor Eletrônico  Versão";
                 
                 
                 for (String line : lines) {
                	  
                	 if(line.contains("Produto:")) {
                		 System.out.println(line + " =>  " + line.length());
                		 novasLinhas.add(line);
                	 }
                	// System.out.println(line + " =>  " + line.length());
//                	 if(!line.contains(header1)) {
//                		 if(!line.contains(header2)) {
//                			 if(!line.contains(header3)) {
//                				 if(!line.contains(header4)) {
//                					 if(!line.contains(header5)) {
//                						 if(!line.contains(footer)) {
//                							 if(line.length() != 131) {
//                								 if(line.length() != 60) {
//                									 if(line.length() != 104) {
//                										 if(line.length() != 90) {
//                											 if(line.length() != 119) {
//                												 if(line.length() != 94) {
//                													 if(line.length() != 102) {
//                														 if(line.length() != 74) {
//                															 if(line.length() != 69) {
//                																 if(line.length() != 32) {
//                                													 System.out.println(line + " =>  " + line.length());
//                                	                                        		 novasLinhas.add(line);
//                																 }
//
//                															 }
//                														 }
//
//                													 }
//
//                												 }
//                											 }
//                											 
//                										 }
//                									 }
//                								 }
//                                        		 
//                							 }
//
//                						 }
//
//                					 }
//                				 }
//
//                			 }
//
//                		 }
//
//                	 }
                		 
                 }
                 
                 
			  }
			 
		 }
		 Files.write(dest, novasLinhas, StandardOpenOption.CREATE);
	}

}
