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
import com.zeta.model.Produto;
import com.zeta.util.UtilsEConverters;

public class PDFTableExample3 {
	
	
	public static final String RESULT =
			"E:\\treinando.pdf";
	
	private static void addRowCabecalho(PdfPTable pdfPTableLayout, List<DocumentoFiscalEltronico> doc) {
		List<PdfPCell> cells = new ArrayList<PdfPCell>();

	    for (DocumentoFiscalEltronico value : doc) {
	    	PdfPCell[] pdfCellTabLayout = new PdfPCell[8];
	    	for(int i =0; i < pdfCellTabLayout.length; i++){
	    		
		    		if(i==0) {
		    			
	            		pdfCellTabLayout[i] = new PdfPCell(new Paragraph(value.getEmitente().getNome()));
	                	pdfCellTabLayout[i].setColspan(7);
	                	pdfCellTabLayout[i].setBorderWidthTop(1);
	                	pdfCellTabLayout[i].setBorderWidthBottom(Rectangle.NO_BORDER);
	                	pdfCellTabLayout[i].setPaddingTop(10);
	                	pdfCellTabLayout[i].setHorizontalAlignment(1);
	                	cells.add(pdfCellTabLayout[i]);
	                	pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	            	
	                	Paragraph pLogradouro = new Paragraph( 
	                			value.getEmitente().getEnd().getLogradouro().concat(", ").concat( value.getEmitente().getEnd().getNumero()));
	                	pdfCellTabLayout[i] = new PdfPCell(pLogradouro);
	                	pdfCellTabLayout[i].setColspan(7);
	                	pdfCellTabLayout[i].setBorderWidthTop(0);
	                	pdfCellTabLayout[i].setBorderWidthBottom(0);
	                	pdfCellTabLayout[i].setHorizontalAlignment(1);
	                	cells.add(pdfCellTabLayout[i]);
	                	pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				        
				        Paragraph pBairro = new Paragraph( 
				        		value.getEmitente().getEnd().getBairro().concat(", ").concat( value.getEmitente().getEnd().getDescMun()));
				        pdfCellTabLayout[i] = new PdfPCell(pBairro);
				        pdfCellTabLayout[i].setColspan(7);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setHorizontalAlignment(1);
				        cells.add(pdfCellTabLayout[i]);
				        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				        
				        Paragraph pCep = new Paragraph("CEP: ".concat(UtilsEConverters.mascaraCep(value.getEmitente().getEnd().getCep())));
				        pdfCellTabLayout[i] = new PdfPCell(pCep);
				        pdfCellTabLayout[i].setColspan(7);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setHorizontalAlignment(1);
				        cells.add(pdfCellTabLayout[i]);
				        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				        
				        Paragraph pCnpjIE = new Paragraph(
				           "CNPJ: ".concat(UtilsEConverters.mascaraCnpj(value.getEmitente().getCnpj())
				        		   .concat(" I.E: ").concat(value.getEmitente().getIe())));
				        pdfCellTabLayout[i] = new PdfPCell(pCnpjIE);
				        pdfCellTabLayout[i].setColspan(7);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setHorizontalAlignment(1);
				        cells.add(pdfCellTabLayout[i]);
				        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				        
	            	
				        Paragraph p = new Paragraph("------------------------------------------------------------------------------------");
				        pdfCellTabLayout[i] = new PdfPCell(p);
				        pdfCellTabLayout[i].setColspan(7);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setHorizontalAlignment(1);
				        cells.add(pdfCellTabLayout[i]);
				        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				        
				        Paragraph pExtrato = new Paragraph("Extrato Nº: ".concat(value.getIdent().getNumDoc()));
				        pdfCellTabLayout[i] = new PdfPCell(pExtrato);
				        pdfCellTabLayout[i].setColspan(7);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setHorizontalAlignment(1);
				        cells.add(pdfCellTabLayout[i]);
				        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				        
				        Paragraph pCfeSAT = new Paragraph("CUPOM FISCAL ELETRÔNICO - SAT");
				        pdfCellTabLayout[i] = new PdfPCell(pCfeSAT);
				        pdfCellTabLayout[i].setColspan(7);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setHorizontalAlignment(1);
				        cells.add(pdfCellTabLayout[i]);
				        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				        
				        Paragraph p1 = new Paragraph("------------------------------------------------------------------------------------");
				        pdfCellTabLayout[i] = new PdfPCell(p1);
				        pdfCellTabLayout[i].setColspan(7);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setHorizontalAlignment(1);
				        cells.add(pdfCellTabLayout[i]);
				        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				       
				        Paragraph pNomeConsumidor = new Paragraph("Razão social/Nome: ".concat(value.getDestinatario().getNome()));
				        pdfCellTabLayout[i] = new PdfPCell(pNomeConsumidor);
				        pdfCellTabLayout[i].setColspan(7);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setHorizontalAlignment(0);
				        pdfCellTabLayout[i].setPaddingLeft(42);
				        cells.add(pdfCellTabLayout[i]);
				        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				        
				        Paragraph p2 = new Paragraph("------------------------------------------------------------------------------------");
				        pdfCellTabLayout[i] = new PdfPCell(p2);
				        pdfCellTabLayout[i].setColspan(7);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setHorizontalAlignment(1);
				        cells.add(pdfCellTabLayout[i]);
				        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	  
		    	}else if(i==1){
	        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph("#"));
	        			pdfCellTabLayout[i].setHorizontalAlignment(1);
	        			cells.add(pdfCellTabLayout[i]);
	            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	        	}else if(i==2) {
	        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(" Código"));
	        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
	        			pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
	        			cells.add(pdfCellTabLayout[i]);
	            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	        	}else if(i==3) {
	        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(" Descrição"));
	        			pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
	        			cells.add(pdfCellTabLayout[i]);
	            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	        	}else if(i==4) {
	        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(" Qtde"));
	        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
	        			pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
	        			cells.add(pdfCellTabLayout[i]);
	            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	        	}else if(i==5) {
	        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(" Un"));
	        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
	        			pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
	        			cells.add(pdfCellTabLayout[i]);
	            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	        	}else if(i==6) {
	        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph("Valor Un."));
	        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
	        			pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
	        			cells.add(pdfCellTabLayout[i]);
	            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	        	}else if(i==7) {
	        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph("Valor Total"));
	        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
	        			pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
	        			cells.add(pdfCellTabLayout[i]);
	            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	            		
	        	}
		    		
	    	} 	
	    	
	    }
      
	    pdfPTableLayout.getRows().add(new PdfPRow(cells.toArray(new PdfPCell[cells.size()]))); 
	}
	
	private static void addRowListaItens(PdfPTable pdfPTableLayout, List<DocumentoFiscalEltronico> doc) {
		List<PdfPCell> cells = new ArrayList<PdfPCell>();

	    for (DocumentoFiscalEltronico value : doc) {
	    	PdfPCell[] pdfCellTabLayout = new PdfPCell[8];
	    	for(Produtos p: value.getProds()){
		    	
		    	for(int i =0; i < pdfCellTabLayout.length; i++){
		    		if(i==1){
		    			
	        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(p.getNumItem()));
	        			pdfCellTabLayout[i].setHorizontalAlignment(1);
	        			cells.add(pdfCellTabLayout[i]);
	            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	        	     }else if(i==2) {
		        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(p.getCodItem()));
		        			cells.add(pdfCellTabLayout[i]);
		            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
		        	}else if(i==3) {
		        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(p.getDescricao()));
		        			cells.add(pdfCellTabLayout[i]);
		            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
		        	}else if(i==4) {
		        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(UtilsEConverters.valorFormatadoPadrao(p.getQtdComercial())));
		        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
		        			cells.add(pdfCellTabLayout[i]);
		            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
		        	}else if(i==5) {
		        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(p.getUndComercial()));
		        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
		        			cells.add(pdfCellTabLayout[i]);
		            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
		        	}else if(i==6) {
		        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(
		        					 UtilsEConverters.valorFormatadoReal(p.getVlUnComerial())));
		        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
		        			cells.add(pdfCellTabLayout[i]);
		            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
		        	}else if(i==7) {
		        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(UtilsEConverters.valorFormatadoReal(p.getVlProduto())));
		        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
		        			cells.add(pdfCellTabLayout[i]);
		            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
		        	}
		    		
		    	}
	    	}
	    	
	    	
			for (int i = 0; i < pdfCellTabLayout.length; i++) {
				if (i == 1) {
					pdfCellTabLayout[i] = new PdfPCell(new Paragraph("Valor Produtos/Serviços"));
					pdfCellTabLayout[i].setColspan(3);
					pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.add(pdfCellTabLayout[i]);
					pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				} else if (i == 2) {
					pdfCellTabLayout[i] = new PdfPCell(new Paragraph("2"));
					cells.add(pdfCellTabLayout[i]);
					pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				} else if (i == 3) {
					pdfCellTabLayout[i] = new PdfPCell(new Paragraph("3"));
					cells.add(pdfCellTabLayout[i]);
					pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				} else if (i == 4) {
					pdfCellTabLayout[i] = new PdfPCell(new Paragraph("4"));
					pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.add(pdfCellTabLayout[i]);
					pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				} else if (i == 5) {
					pdfCellTabLayout[i] = new PdfPCell(new Paragraph(UtilsEConverters.valorFormatadoReal(value.getTotal().getvCFe())));
					pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.add(pdfCellTabLayout[i]);
					pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				} else if (i == 6) {
					pdfCellTabLayout[i] = new PdfPCell(new Paragraph("6"));
					pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.add(pdfCellTabLayout[i]);
					pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				} else if (i == 7) {
					pdfCellTabLayout[i] = new PdfPCell(new Paragraph(UtilsEConverters.valorFormatadoReal(value.getTotal().getvCFe())));
					pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.add(pdfCellTabLayout[i]);
					pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				}

			}
			

	    }
	    
	    pdfPTableLayout.getRows().add(new PdfPRow(cells.toArray(new PdfPCell[cells.size()])));
	}
	
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
		        float[] colsWidth = {0.2f, 0.4f,1.2f,0.3f, 0.2f,0.4f, 0.6f};
		        PdfPTable pdfPTableLayout = new PdfPTable(colsWidth);
		        pdfPTableLayout.setWidthPercentage(80); //Width 100%
		        pdfPTableLayout.setSpacingBefore(10f); //Space before table
		        pdfPTableLayout.setSpacingAfter(10f); //Space after table
		        
		        List<DocumentoFiscalEltronico> validaTipoDeParseNFE = parse.validaTipoDeParseNFE(diretorio);
 	
		        addRowCabecalho(pdfPTableLayout,validaTipoDeParseNFE);
		        addRowListaItens(pdfPTableLayout,validaTipoDeParseNFE);
		        //addRowTotaisEFormasPagamentos(pdfPTableLayout, validaTipoDeParseNFE);
		        
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
