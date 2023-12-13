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
		
		String s="GeeksForGeeks";
		
		int count=0;
        
	      for(int i=0;i<s.length();i++)
	      {
	      i=s.indexOf('e',i);
	        if(i<0)
	              break;
	        count++;
	      }
	      System.out.println("Count is "+ count);
     }
	
}
