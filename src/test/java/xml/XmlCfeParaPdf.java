package xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.leetor4.handler.ParseDocXML;
import com.leetor4.model.nfe.DocumentoFiscalEltronico;
import com.leetor4.model.nfe.Produtos;

public class XmlCfeParaPdf {
	
	public static void main(String[] args) {
		
		  //String format = String.format("%-12s%-12s%s\n","Coluna 1","Coluna 2","Coluna 3");
		  //System.out.println(format);
		
		  //System.out.printf("%-12s%-12s%s\n","Coluna 1","Coluna 2","Coluna 3");
	     //System.out.printf("%-12d%-12d%07d\n",15,12,5);
	     

	        
        ParseDocXML parse = new ParseDocXML();
		
		File diretorio  = new File("E:\\XML");
		
		Document document = new Document();
		 try {

             PdfWriter.getInstance(document, new FileOutputStream("E:\\Cfe.pdf"));
             document.open();

             for(DocumentoFiscalEltronico doc : parse.validaTipoDeParseNFE(diretorio)){
            	
            	 // adicionando um parágrafo no documento
                 
            	 Paragraph paragraph = new Paragraph(doc.getEmitente().getNome());
            	 
            	 Font f3 = new Font(Font.FontFamily.TIMES_ROMAN, 8f, Font.NORMAL);
                 f3.getCalculatedLeading(1f);
                 f3.setSize(5f);
                 paragraph.setFont(f3);
                 
            	 paragraph.setLeading(10);//Movimenta vertical
            	 paragraph.setIndentationLeft(120f);//Movimenta horizontal
            	 document.add(paragraph);
            	 
            	 Paragraph paragraph1 = new Paragraph(
            			  doc.getEmitente().getEnd().getLogradouro().concat(", ").concat( doc.getEmitente().getEnd().getNumero()));
            	 
            	 Paragraph paragraph12 = new Paragraph(
           			  doc.getEmitente().getEnd().getBairro());
            	
            	 paragraph1.setIndentationLeft(155f);//Movimenta horizontal
            	 document.add(paragraph1);
            	 paragraph12.setIndentationLeft(155f);//Movimenta horizontal
            	 document.add(paragraph12);
            	 
       
            	 for(Produtos p : doc.getProds()){
            		
            	 }
            	 
 
             }
            
             System.out.println("Arquivo convertido com sucesso!!!");
         }
         catch(DocumentException de) {
             System.err.println(de.getMessage());
         }
         catch(IOException ioe) {
             System.err.println(ioe.getMessage());
         } catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         document.close();
     }
	
}
