package pdf;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

public class LeitorPDFTIPI {

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		
	
//		int number = 1;
//		String formatted = String.format("%02d", number);
//		System.out.println(formatted);
		
		
		List<String> novasLinhas =  new ArrayList<String>();   
		File file = new File("C:\\Users\\chico\\Downloads\\NCM_ TIPI2012.pdf");
		File fileNcm = new File("C:\\Users\\chico\\Downloads\\NCMS.txt");
	    Path pV12 = Paths.get("C:\\Users\\chico\\Downloads\\NCMsEncontradas.txt");
		Path dest = pV12;
		try (PDDocument document = PDDocument.load( file )) {
			 
			  if (!document.isEncrypted()) {
				  
				 PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                 stripper.setSortByPosition(true);


                 PDFTextStripper tStripper = new PDFTextStripper();
                 tStripper.setStartPage(1);
                 tStripper.setEndPage(310);
                 
            
                 
                 String pdfFileInText = tStripper.getText(document);

                 String lines[] = pdfFileInText.split("\\r?\\n");
                 
                 String header1 = "Secretaria da Fazenda do Estado do Ceará";
                 List<String> linesNCMS = Files.readAllLines(fileNcm.toPath(), StandardCharsets.ISO_8859_1);

                 
                 for (String line : lines) {
                	  
                     for (String lineNCM : linesNCMS) {
                    	 //System.out.println(line + " =>  " + line.length());
                    	 if(line.contains(lineNCM)) {
                    		 System.out.println(line + " =>  " + line.length());
                    		 novasLinhas.add(line);
                    	 } 
                     }
                     

                 }
			  } 
		 }
		 
		 Files.write(dest, novasLinhas, StandardOpenOption.CREATE);
	}

}
