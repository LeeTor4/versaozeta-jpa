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

public class PDFTableExample2 {
	
	
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
		        PdfPTable pdfPTableLayout = new PdfPTable(1);
		        pdfPTableLayout.setWidthPercentage(80); //Width 100%
		        pdfPTableLayout.setSpacingBefore(10f); //Space before table
		        pdfPTableLayout.setSpacingAfter(10f); //Space after table
		       
		        
		        for(DocumentoFiscalEltronico doc : parse.validaTipoDeParseNFE(diretorio)){
		        	
		        	Paragraph pNome = new Paragraph(doc.getEmitente().getNome());
			        PdfPCell pdfPNome = new PdfPCell(pNome);
			        pdfPNome.setBorderWidthTop(1);
			        pdfPNome.setBorderWidthBottom(Rectangle.NO_BORDER);
			        pdfPNome.setPaddingTop(10);
			        pdfPNome.setHorizontalAlignment(1);
			        pdfPTableLayout.addCell(pdfPNome);
			        
			        Paragraph pLogradouro = new Paragraph( 
			        		doc.getEmitente().getEnd().getLogradouro().concat(", ").concat( doc.getEmitente().getEnd().getNumero()));
			        PdfPCell pdfPLogradouro = new PdfPCell(pLogradouro);
			        pdfPLogradouro.setBorderWidthTop(0);
			        pdfPLogradouro.setBorderWidthBottom(0);
			        pdfPLogradouro.setHorizontalAlignment(1);
			        pdfPTableLayout.addCell(pdfPLogradouro);
			        
			        Paragraph pBairro = new Paragraph( 
			        		doc.getEmitente().getEnd().getBairro().concat(", ").concat( doc.getEmitente().getEnd().getDescMun()));
			        PdfPCell pdfPBairro = new PdfPCell(pBairro);
			        pdfPBairro.setBorderWidthTop(0);
			        pdfPBairro.setBorderWidthBottom(0);
			        pdfPBairro.setHorizontalAlignment(1);
			        pdfPTableLayout.addCell(pdfPBairro);
			        
			        Paragraph pCep = new Paragraph("CEP: ".concat(UtilsEConverters.mascaraCep(doc.getEmitente().getEnd().getCep())));
			        PdfPCell pdfPpCep = new PdfPCell(pCep);
			        pdfPpCep.setBorderWidthTop(0);
			        pdfPpCep.setBorderWidthBottom(0);
			        pdfPpCep.setHorizontalAlignment(1);
			        pdfPTableLayout.addCell(pdfPpCep);
			        
			        Paragraph pCnpjIE = new Paragraph(
			           "CNPJ: ".concat(UtilsEConverters.mascaraCnpj(doc.getEmitente().getCnpj())
			        		   .concat(" I.E: ").concat(doc.getEmitente().getIe())));
			        PdfPCell pdfPCnpjIE = new PdfPCell(pCnpjIE);
			        pdfPCnpjIE.setBorderWidthTop(0);
			        pdfPCnpjIE.setBorderWidthBottom(0);
			        pdfPCnpjIE.setHorizontalAlignment(1);
			        pdfPTableLayout.addCell(pdfPCnpjIE);
			        
			        Paragraph p = new Paragraph("------------------------------------------------------------------------------------");
			        PdfPCell pdfP = new PdfPCell(p);
			        pdfP.setBorderWidthTop(0);
			        pdfP.setBorderWidthBottom(0);
			        pdfP.setHorizontalAlignment(1);
			        pdfPTableLayout.addCell(pdfP);
			        
			        Paragraph pExtrato = new Paragraph("Extrato N�: ".concat(doc.getIdent().getNumDoc()));
			        PdfPCell pdfPExtrato = new PdfPCell(pExtrato);
			        pdfPExtrato.setBorderWidthTop(0);
			        pdfPExtrato.setBorderWidthBottom(0);
			        pdfPExtrato.setHorizontalAlignment(1);
			        pdfPTableLayout.addCell(pdfPExtrato);
			        
			        Paragraph pCfeSAT = new Paragraph("CUPOM FISCAL ELETR�NICO - SAT");
			        PdfPCell pdfPCfeSAT = new PdfPCell(pCfeSAT);
			        pdfPCfeSAT.setBorderWidthTop(0);
			        pdfPCfeSAT.setBorderWidthBottom(0);
			        pdfPCfeSAT.setHorizontalAlignment(1);
			        pdfPTableLayout.addCell(pdfPCfeSAT);
			        
			        Paragraph p1 = new Paragraph("------------------------------------------------------------------------------------");
			        PdfPCell pdfP1 = new PdfPCell(p1);
			        pdfP1.setBorderWidthTop(0);
			        pdfP1.setBorderWidthBottom(0);
			        pdfP1.setHorizontalAlignment(1);
			        pdfPTableLayout.addCell(pdfP1);
			        
			        Paragraph pNomeConsumidor = new Paragraph("Raz�o social/Nome: ".concat(doc.getDestinatario().getNome()));
			        PdfPCell pdfPNomeConsumidor = new PdfPCell(pNomeConsumidor);
			        pdfPNomeConsumidor.setBorderWidthTop(0);
			        pdfPNomeConsumidor.setBorderWidthBottom(0);
			        pdfPNomeConsumidor.setHorizontalAlignment(0);
			        pdfPNomeConsumidor.setPaddingLeft(42);
			        pdfPTableLayout.addCell(pdfPNomeConsumidor);
			        
			        Paragraph p2 = new Paragraph("------------------------------------------------------------------------------------");
			        PdfPCell pdfP2 = new PdfPCell(p2);
			        pdfP2.setBorderWidthTop(0);
			        pdfP2.setBorderWidthBottom(0);
			        pdfP2.setHorizontalAlignment(1);
			        pdfPTableLayout.addCell(pdfP2);
			        
			        
			        PdfPTable pdfPTableListaProdutos = new PdfPTable(1);
			       
			        pdfPTableLayout.addCell(pdfPTableListaProdutos);
//			        Paragraph pColumn1 = new Paragraph("#");
//			        PdfPCell pdfPColumn1 = new PdfPCell(pColumn1);
//			        pdfPColumn1.setBorderWidthTop(1);
//			        pdfPColumn1.setBorderWidthBottom(Rectangle.NO_BORDER);
//			        pdfPColumn1.setPaddingTop(10);
//			        pdfPColumn1.setHorizontalAlignment(1);
//			        pdfPTableListaProdutos.addCell(pdfPColumn1);
//			        
//			        Paragraph pColumn2 = new Paragraph("C�digo");
//			        PdfPCell pdfPColumn2 = new PdfPCell(pColumn2);
//			        pdfPColumn2.setBorderWidthTop(1);
//			        pdfPColumn2.setBorderWidthBottom(Rectangle.NO_BORDER);
//			        pdfPColumn2.setPaddingTop(10);
//			        pdfPColumn2.setHorizontalAlignment(1);
//			        pdfPTableListaProdutos.addCell(pdfPColumn2);
//			        
//			        
//			        Paragraph pColumn3 = new Paragraph("Descri��o");
//			        PdfPCell pdfPColumn3 = new PdfPCell(pColumn3);
//			        pdfPColumn3.setBorderWidthTop(1);
//			        pdfPColumn3.setBorderWidthBottom(Rectangle.NO_BORDER);
//			        pdfPColumn3.setPaddingTop(10);
//			        pdfPColumn3.setHorizontalAlignment(1);
//			        pdfPTableListaProdutos.addCell(pdfPColumn3);
			        
			        
	                
//			        PdfPCell[] pdfCellTbInterna = new PdfPCell[10];
//			        for(int i =0; i < pdfCellTbInterna.length; i++){
//			        	
//			        	pdfCellTbInterna[i] = new PdfPCell(new Paragraph("#".concat(String.valueOf(i))));
//			        	pdfCellTbInterna[i].setBorderWidthTop(Rectangle.NO_BORDER);
//			        	pdfCellTbInterna[i].setBorderWidthBottom(Rectangle.NO_BORDER);
//			        	pdfCellTbInterna[i].setBorder(0);
//			        	pdfPTableListaProdutos.addCell(pdfCellTbInterna[i]);
//			        }
			        
			        
			        
			       
			        
			        
			        Paragraph p3 = new Paragraph("------------------------------------------------------------------------------------");
			        PdfPCell pdfP3 = new PdfPCell(p3);
			        pdfP3.setBorderWidthTop(0);
			        pdfP3.setBorderWidthBottom(0);
			        pdfP3.setHorizontalAlignment(1);
			        pdfPTableLayout.addCell(pdfP3);
			        
			        
			        
			        
			        
			        PdfPCell[] pdfCellTabLayout = new PdfPCell[30];
	                for(int i =0; i < pdfCellTabLayout.length; i++){
	                	
	                	if(i==5) {
	                    	pdfCellTabLayout[i] = new PdfPCell(new Paragraph("#"));
	                    	pdfCellTabLayout[i].setBorderWidthTop(Rectangle.NO_BORDER);
	                    	pdfCellTabLayout[i].setBorderWidthBottom(Rectangle.NO_BORDER);
	                    	pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	                	}else if(i==15) { 
	                		pdfCellTabLayout[i] = new PdfPCell(new Paragraph("#"));
		                	pdfCellTabLayout[i].setBorderWidthTop(Rectangle.NO_BORDER);
		                	pdfCellTabLayout[i].setBorderWidthBottom(Rectangle.NO_BORDER);
		                	pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	                	}else{
	                		
	                    	pdfCellTabLayout[i] = new PdfPCell(new Paragraph("#"));
	                    	pdfCellTabLayout[i].setBorderWidthTop(Rectangle.NO_BORDER);
	                    	pdfCellTabLayout[i].setBorderWidthBottom(Rectangle.NO_BORDER);
	                    	pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	                	}

	                }
	                
	                
	                PdfPCell pdfPCell12 = new PdfPCell(new Paragraph("#"));
	                pdfPCell12.setBorderWidthTop(Rectangle.NO_BORDER);
	                pdfPCell12.setBorderWidthBottom(1);
	                pdfPTableLayout.addCell(pdfPCell12);
	                
	               
	               
		        }
		 

		       
		 
//		        PdfPCell[] cells1 = { pdfPCell1,pdfPCell2,pdfPCell3,pdfPCell4 };
//		        PdfPCell[] cells2 = { pdfPCell5,pdfPCell6,pdfPCell7,pdfPCell8 };
//		        PdfPCell[] cells3 = { pdfPCell8,pdfPCell9,pdfPCell11,pdfPCell12 };
//		       
//		        
//		        PdfPRow row1 = new PdfPRow(cells1);
//		        PdfPRow row2 = new PdfPRow(cells2);
		        
		       
		        
		        //Add content to the document using Table objects.
		      
		        document.add(pdfPTableLayout);
		 
		        //Close document and outputStream.
		        document.close();
		        outputStream.close();
		 
		        System.out.println("Pdf created successfully.");
		    } catch (Exception e) {
			e.printStackTrace();
		    }
   }
}
