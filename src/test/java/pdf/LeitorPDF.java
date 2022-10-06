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
		File file = new File("C:\\Users\\chico\\Downloads\\2012.01-ENTRADAS (1).pdf");
	    Path pV12 = Paths.get("C:\\Users\\chico\\Downloads\\2012.01-ENTRADAS (1).txt");
		Path dest = pV12;
		 try (PDDocument document = PDDocument.load( file )) {
			 
			  if (!document.isEncrypted()) {
				  
				 PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                 stripper.setSortByPosition(true);

                 PDFTextStripper tStripper = new PDFTextStripper();

                 String pdfFileInText = tStripper.getText(document);

           
                 
                 String lines[] = pdfFileInText.split("\\r?\\n");
                 
                 for (String line : lines) {
                	     
                	 if(line.length() > 100) {
                		 System.out.println(line + " =>  " + line.length());
                		 novasLinhas.add(line);
                	 }
                		 
                 }
                 
                 
			  }
			 
		 }
		 Files.write(dest, novasLinhas, StandardOpenOption.CREATE);
	}

}
