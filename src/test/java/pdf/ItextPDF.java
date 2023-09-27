package pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfWriter.PdfBody;

public class ItextPDF {

	public static final String RESULT =
			"E:\\treinando.pdf";
	
//	private static void addRow(PdfPTable table, boolean header, List<String> values) {
//	    List<PdfPCell> cells = new ArrayList<PdfPCell>();
//
//	    for (Object value : values) {
//	        PdfPCell cell = new PdfPCell(new Phrase(value + "", header ? boldFont : normalFont));
//	        if (header) {
//	            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	        }/*from w w w.  j  a  v a  2s .  co m*/
//
//	        cells.add(cell);
//	    }
//
//	    table.getRows().add(new PdfPRow(cells.toArray(new PdfPCell[cells.size()])));
//	}
	
	public static void main(String[] args) throws FileNotFoundException, DocumentException{
		
//		 Rectangle pagesize = new Rectangle(216f, 720f);
//         Document document = new Document(pagesize, 36f, 72f, 108f, 180f);
//         PdfWriter.getInstance(document, new FileOutputStream(RESULT));
//         document.open();         
//         document.add(new Paragraph("Primeiro Exemplo com iText!"));
//         document.close();
         
         //=================================================================
         
         
         Document document = new Document();
          try {
	           //Cria o documento PDF
	           PdfWriter.getInstance(document , new FileOutputStream(RESULT));
	           //Abrir documento para gerar o conteudo
	           document.open(); 
	           document.add(new Paragraph("Estudando Tabelas")); 
	           document.add(new Paragraph(" ")); 
	           //Criar uma tabela
	           PdfPTable tabela = new PdfPTable(1);
	           //Cabeçalho
	           PdfPCell coluna1 = new PdfPCell(new Paragraph("Coluna 1"));
	           tabela.addCell(coluna1);
	           tabela.getRows(1,3);        		   
	           List<PdfPCell> cells = new ArrayList<PdfPCell>();

	           PdfPCell cellCorpo = new PdfPCell(new Phrase("Corpo"));

	           cells.add(cellCorpo);

	           tabela.getRows().add(new PdfPRow(cells.toArray(new PdfPCell[cells.size()])));

	           for (PdfPRow row : tabela.getRows()) {	        	 
	        	   PdfPCell[] cells2 = row.getCells();
	        	   for (PdfPCell cellToAlign : cells2) {
	        		   cellToAlign.setHorizontalAlignment(Element.ALIGN_CENTER);
	                   cellToAlign.setVerticalAlignment(Element.ALIGN_CENTER);
	        	   }
	           }

	           document.add(tabela);
	           
           } catch(DocumentException de) {
           System.err.println(de.getMessage());
          }
          document.close();

	}

}
