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
import com.leetor4.model.nfe.Produtos;
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
		        float[] colsWidth = {0.2f, 0.3f,1f,0.3f, 0.2f,0.3f, 0.3f};
		        PdfPTable pdfPTableLayout = new PdfPTable(colsWidth);
		        pdfPTableLayout.setWidthPercentage(80); //Width 100%
		        pdfPTableLayout.setSpacingBefore(10f); //Space before table
		        pdfPTableLayout.setSpacingAfter(10f); //Space after table
		        
		        float[] colsWidth2 = {0.2f, 0.3f,1f,0.3f, 0.2f,0.3f, 0.3f};
		        PdfPTable pdfPTableLayoutListaProdutos = new PdfPTable(colsWidth2);
		        pdfPTableLayoutListaProdutos.setWidthPercentage(80); //Width 100%
		        pdfPTableLayoutListaProdutos.setSpacingBefore(10f); //Space before table
		        pdfPTableLayoutListaProdutos.setSpacingAfter(10f); //Space after table
		            	
		        
		        for(DocumentoFiscalEltronico doc : parse.validaTipoDeParseNFE(diretorio)){
			        PdfPCell[] pdfCellTabLayout = new PdfPCell[8];
			        PdfPCell[] pdfCellTabProdutos = new PdfPCell[doc.getProds().size()];
	                for(int i =0; i < pdfCellTabLayout.length; i++){
			                	if(i==0) {
		
			                		pdfCellTabLayout[i] = new PdfPCell(new Paragraph(doc.getEmitente().getNome()));
			                    	pdfCellTabLayout[i].setColspan(7);
			                    	pdfCellTabLayout[i].setBorderWidthTop(1);
			                    	pdfCellTabLayout[i].setBorderWidthBottom(Rectangle.NO_BORDER);
			                    	pdfCellTabLayout[i].setPaddingTop(10);
			                    	pdfCellTabLayout[i].setHorizontalAlignment(1);
			                    	pdfPTableLayout.addCell(pdfCellTabLayout[i]);	
		
			                	
			                    	Paragraph pLogradouro = new Paragraph( 
			    			        		doc.getEmitente().getEnd().getLogradouro().concat(", ").concat( doc.getEmitente().getEnd().getNumero()));
			                    	pdfCellTabLayout[i] = new PdfPCell(pLogradouro);
			                    	pdfCellTabLayout[i].setColspan(7);
			                    	pdfCellTabLayout[i].setBorderWidthTop(0);
			                    	pdfCellTabLayout[i].setBorderWidthBottom(0);
			                    	pdfCellTabLayout[i].setHorizontalAlignment(1);
			    			        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			    			        
			    			        Paragraph pBairro = new Paragraph( 
			    			        		doc.getEmitente().getEnd().getBairro().concat(", ").concat( doc.getEmitente().getEnd().getDescMun()));
			    			        pdfCellTabLayout[i] = new PdfPCell(pBairro);
			    			        pdfCellTabLayout[i].setColspan(7);
			    			        pdfCellTabLayout[i].setBorderWidthTop(0);
			    			        pdfCellTabLayout[i].setBorderWidthBottom(0);
			    			        pdfCellTabLayout[i].setHorizontalAlignment(1);
			    			        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			    			        
			    			        Paragraph pCep = new Paragraph("CEP: ".concat(UtilsEConverters.mascaraCep(doc.getEmitente().getEnd().getCep())));
			    			        pdfCellTabLayout[i] = new PdfPCell(pCep);
			    			        pdfCellTabLayout[i].setColspan(7);
			    			        pdfCellTabLayout[i].setBorderWidthTop(0);
			    			        pdfCellTabLayout[i].setBorderWidthBottom(0);
			    			        pdfCellTabLayout[i].setHorizontalAlignment(1);
			    			        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			    			        
			    			        Paragraph pCnpjIE = new Paragraph(
			    			           "CNPJ: ".concat(UtilsEConverters.mascaraCnpj(doc.getEmitente().getCnpj())
			    			        		   .concat(" I.E: ").concat(doc.getEmitente().getIe())));
			    			        pdfCellTabLayout[i] = new PdfPCell(pCnpjIE);
			    			        pdfCellTabLayout[i].setColspan(7);
			    			        pdfCellTabLayout[i].setBorderWidthTop(0);
			    			        pdfCellTabLayout[i].setBorderWidthBottom(0);
			    			        pdfCellTabLayout[i].setHorizontalAlignment(1);
			    			        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			    			        
			                	
			    			        Paragraph p = new Paragraph("------------------------------------------------------------------------------------");
			    			        pdfCellTabLayout[i] = new PdfPCell(p);
			    			        pdfCellTabLayout[i].setColspan(7);
			    			        pdfCellTabLayout[i].setBorderWidthTop(0);
			    			        pdfCellTabLayout[i].setBorderWidthBottom(0);
			    			        pdfCellTabLayout[i].setHorizontalAlignment(1);
			    			        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			    			        
			    			        Paragraph pExtrato = new Paragraph("Extrato Nº: ".concat(doc.getIdent().getNumDoc()));
			    			        pdfCellTabLayout[i] = new PdfPCell(pExtrato);
			    			        pdfCellTabLayout[i].setColspan(7);
			    			        pdfCellTabLayout[i].setBorderWidthTop(0);
			    			        pdfCellTabLayout[i].setBorderWidthBottom(0);
			    			        pdfCellTabLayout[i].setHorizontalAlignment(1);
			    			        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			    			        
			    			        Paragraph pCfeSAT = new Paragraph("CUPOM FISCAL ELETRÔNICO - SAT");
			    			        pdfCellTabLayout[i] = new PdfPCell(pCfeSAT);
			    			        pdfCellTabLayout[i].setColspan(7);
			    			        pdfCellTabLayout[i].setBorderWidthTop(0);
			    			        pdfCellTabLayout[i].setBorderWidthBottom(0);
			    			        pdfCellTabLayout[i].setHorizontalAlignment(1);
			    			        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			    			        
			    			        Paragraph p1 = new Paragraph("------------------------------------------------------------------------------------");
			    			        pdfCellTabLayout[i] = new PdfPCell(p1);
			    			        pdfCellTabLayout[i].setColspan(7);
			    			        pdfCellTabLayout[i].setBorderWidthTop(0);
			    			        pdfCellTabLayout[i].setBorderWidthBottom(0);
			    			        pdfCellTabLayout[i].setHorizontalAlignment(1);
			    			        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			    			        
			    			        Paragraph pNomeConsumidor = new Paragraph("Razão social/Nome: ".concat(doc.getDestinatario().getNome()));
			    			        pdfCellTabLayout[i] = new PdfPCell(pNomeConsumidor);
			    			        pdfCellTabLayout[i].setColspan(7);
			    			        pdfCellTabLayout[i].setBorderWidthTop(0);
			    			        pdfCellTabLayout[i].setBorderWidthBottom(0);
			    			        pdfCellTabLayout[i].setHorizontalAlignment(0);
			    			        pdfCellTabLayout[i].setPaddingLeft(42);
			    			        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			    			        
			    			        Paragraph p2 = new Paragraph("------------------------------------------------------------------------------------");
			    			        pdfCellTabLayout[i] = new PdfPCell(p2);
			    			        pdfCellTabLayout[i].setColspan(7);
			    			        pdfCellTabLayout[i].setBorderWidthTop(0);
			    			        pdfCellTabLayout[i].setBorderWidthBottom(0);
			    			        pdfCellTabLayout[i].setHorizontalAlignment(1);
			    			        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			    			        
			                	}else if(i==1){
			                			pdfCellTabLayout[i] = new PdfPCell(new Paragraph("#"));
			                			pdfCellTabLayout[i].setHorizontalAlignment(1);
				                		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			                	}else if(i==2) {
			                			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(" Código"));
				                		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			                	}else if(i==3) {
			                			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(" Descrição"));
				                		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			                	}else if(i==4) {
			                			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(" Qtde"));
				                		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			                	}else if(i==5) {
			                			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(" Un"));
				                		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			                	}else if(i==6) {
			                			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(" Valor Un."));
				                		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			                	}else if(i==7) {
			                			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(" Valor Total"));
				                		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			                	}
	                			
	                		
	                	}
	                    
		                
		        }

		        
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
