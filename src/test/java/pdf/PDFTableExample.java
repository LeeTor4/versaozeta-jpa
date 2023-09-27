package pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.Border;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.leetor4.handler.ParseDocXML;
import com.leetor4.model.nfe.DocumentoFiscalEltronico;
import com.zeta.util.UtilsEConverters;

public class PDFTableExample {
	
	
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
	
   public static void main(String[] args) {
	
	   try {
		   
		    ParseDocXML parse = new ParseDocXML();
			
			File diretorio  = new File("E:\\XML");
		   
		   
			//Create Document instance.
			Document document = new Document();
		 
			//Create OutputStream instance.
			OutputStream outputStream = 
			    new FileOutputStream(new File(RESULT));
		 
			//Create PDFWriter instance.
		        PdfWriter.getInstance(document, outputStream);
		 
		        //Open the document.
		        document.open();
		        
		      //Create Table object, Here 4 specify the no. of columns
		        PdfPTable pdfPTable = new PdfPTable(1);
		        pdfPTable.setWidthPercentage(80); //Width 100%
		        pdfPTable.setSpacingBefore(10f); //Space before table
		        pdfPTable.setSpacingAfter(10f); //Space after table
		       
		        
		        for(DocumentoFiscalEltronico doc : parse.validaTipoDeParseNFE(diretorio)){
		        	
		        	Paragraph pNome = new Paragraph(doc.getEmitente().getNome());
			        PdfPCell pdfPNome = new PdfPCell(pNome);
			        pdfPNome.setBorderWidthTop(1);
			        pdfPNome.setBorderWidthBottom(Rectangle.NO_BORDER);
			        pdfPNome.setPaddingTop(10);
			        pdfPNome.setHorizontalAlignment(1);
			        pdfPTable.addCell(pdfPNome);
			        
			        Paragraph pLogradouro = new Paragraph( 
			        		doc.getEmitente().getEnd().getLogradouro().concat(", ").concat( doc.getEmitente().getEnd().getNumero()));
			        PdfPCell pdfPLogradouro = new PdfPCell(pLogradouro);
			        pdfPLogradouro.setBorderWidthTop(0);
			        pdfPLogradouro.setBorderWidthBottom(0);
			        pdfPLogradouro.setHorizontalAlignment(1);
			        pdfPTable.addCell(pdfPLogradouro);
			        
			        Paragraph pBairro = new Paragraph( 
			        		doc.getEmitente().getEnd().getBairro().concat(", ").concat( doc.getEmitente().getEnd().getDescMun()));
			        PdfPCell pdfPBairro = new PdfPCell(pBairro);
			        pdfPBairro.setBorderWidthTop(0);
			        pdfPBairro.setBorderWidthBottom(0);
			        pdfPBairro.setHorizontalAlignment(1);
			        pdfPTable.addCell(pdfPBairro);
			        
			        Paragraph pCep = new Paragraph("CEP: ".concat(UtilsEConverters.mascaraCep(doc.getEmitente().getEnd().getCep())));
			        PdfPCell pdfPpCep = new PdfPCell(pCep);
			        pdfPpCep.setBorderWidthTop(0);
			        pdfPpCep.setBorderWidthBottom(0);
			        pdfPpCep.setHorizontalAlignment(1);
			        pdfPTable.addCell(pdfPpCep);
			        
			        Paragraph pCnpjIE = new Paragraph(
			           "CNPJ: ".concat(UtilsEConverters.mascaraCnpj(doc.getEmitente().getCnpj())
			        		   .concat(" I.E: ").concat(doc.getEmitente().getIe())));
			        PdfPCell pdfPCnpjIE = new PdfPCell(pCnpjIE);
			        pdfPCnpjIE.setBorderWidthTop(0);
			        pdfPCnpjIE.setBorderWidthBottom(0);
			        pdfPCnpjIE.setHorizontalAlignment(1);
			        pdfPTable.addCell(pdfPCnpjIE);
			        
			        Paragraph p = new Paragraph("------------------------------------------------------------------------------------");
			        PdfPCell pdfP = new PdfPCell(p);
			        pdfP.setBorderWidthTop(0);
			        pdfP.setBorderWidthBottom(0);
			        pdfP.setHorizontalAlignment(1);
			        pdfPTable.addCell(pdfP);
			        
			        Paragraph pExtrato = new Paragraph("Extrato Nº: ".concat(doc.getIdent().getNumDoc()));
			        PdfPCell pdfPExtrato = new PdfPCell(pExtrato);
			        pdfPExtrato.setBorderWidthTop(0);
			        pdfPExtrato.setBorderWidthBottom(0);
			        pdfPExtrato.setHorizontalAlignment(1);
			        pdfPTable.addCell(pdfPExtrato);
			        
			        Paragraph pCfeSAT = new Paragraph("CUPOM FISCAL ELETRÔNICO - SAT");
			        PdfPCell pdfPCfeSAT = new PdfPCell(pCfeSAT);
			        pdfPCfeSAT.setBorderWidthTop(0);
			        pdfPCfeSAT.setBorderWidthBottom(0);
			        pdfPCfeSAT.setHorizontalAlignment(1);
			        pdfPTable.addCell(pdfPCfeSAT);
			        
			        Paragraph p1 = new Paragraph("------------------------------------------------------------------------------------");
			        PdfPCell pdfP1 = new PdfPCell(p1);
			        pdfP1.setBorderWidthTop(0);
			        pdfP1.setBorderWidthBottom(0);
			        pdfP1.setHorizontalAlignment(1);
			        pdfPTable.addCell(pdfP1);
			        
			        Paragraph pNomeConsumidor = new Paragraph("Razão social/Nome: ".concat(doc.getDestinatario().getNome()));
			        PdfPCell pdfPNomeConsumidor = new PdfPCell(pNomeConsumidor);
			        pdfPNomeConsumidor.setBorderWidthTop(0);
			        pdfPNomeConsumidor.setBorderWidthBottom(0);
			        pdfPNomeConsumidor.setHorizontalAlignment(0);
			        pdfPNomeConsumidor.setPaddingLeft(42);
			        pdfPTable.addCell(pdfPNomeConsumidor);
			        
			        PdfPCell[] pdfCell = new PdfPCell[30];
	                for(int i =0; i < pdfCell.length; i++){
	                	
	                	if(i==10) {
	                    	pdfCell[i] = new PdfPCell(new Paragraph("#"));
	                    	pdfCell[i].setBorderWidthTop(1);
	                    	pdfCell[i].setBorderWidthBottom(Rectangle.NO_BORDER);
	                    	pdfPTable.addCell(pdfCell[i]);
	                	}else if(i==15) { 
	                		pdfCell[i] = new PdfPCell(new Paragraph("#"));
		                	pdfCell[i].setBorderWidthTop(0);
		                	pdfCell[i].setBorderWidthBottom(1);
		                	pdfPTable.addCell(pdfCell[i]);
	                	}else{
	                		
	                    	pdfCell[i] = new PdfPCell(new Paragraph("#"));
	                    	pdfCell[i].setBorderWidthTop(Rectangle.NO_BORDER);
	                    	pdfCell[i].setBorderWidthBottom(Rectangle.NO_BORDER);
	                    	pdfPTable.addCell(pdfCell[i]);
	                	}

	                }
	                
	                
	                PdfPCell pdfPCell12 = new PdfPCell(new Paragraph("#"));
	                pdfPCell12.setBorderWidthTop(Rectangle.NO_BORDER);
	                pdfPCell12.setBorderWidthBottom(1);
	                pdfPTable.addCell(pdfPCell12);
		        }
		 

		       
		 
//		        PdfPCell[] cells1 = { pdfPCell1,pdfPCell2,pdfPCell3,pdfPCell4 };
//		        PdfPCell[] cells2 = { pdfPCell5,pdfPCell6,pdfPCell7,pdfPCell8 };
//		        PdfPCell[] cells3 = { pdfPCell8,pdfPCell9,pdfPCell11,pdfPCell12 };
//		       
//		        
//		        PdfPRow row1 = new PdfPRow(cells1);
//		        PdfPRow row2 = new PdfPRow(cells2);
		        
		       
		        
		        //Add content to the document using Table objects.
		        document.add(pdfPTable);
		 
		        //Close document and outputStream.
		        document.close();
		        outputStream.close();
		 
		        System.out.println("Pdf created successfully.");
		    } catch (Exception e) {
			e.printStackTrace();
		    }
   }
}
