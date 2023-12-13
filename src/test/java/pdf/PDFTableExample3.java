 package pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.text.MaskFormatter;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.leetor4.handler.ParseDocXML;
import com.leetor4.model.nfe.DocumentoFiscalEltronico;
import com.leetor4.model.nfe.Pagamento;
import com.leetor4.model.nfe.Produtos;
import com.zeta.util.UtilsEConverters;

public class PDFTableExample3 {
	
	
	public static final String RESULT =
			"E:\\CFe23230105329222000680592300979500500863735706.pdf";
	
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
	            	
	                	String compl = value.getEmitente().getEnd().getComplemento();
	                	
	                	Paragraph pLogradouro = new Paragraph( 
	                			value.getEmitente().getEnd().getLogradouro().concat(", ").concat( value.getEmitente().getEnd().getNumero()
	                					.concat("\n").concat((compl==null?"":compl))));
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
				        
				        if(value.getDestinatario().getCpf() != null) {
					        Paragraph pCpfCnpj = new Paragraph("CNPJ/CPF do Consumidor: ".concat(UtilsEConverters.mascaraCpf(value.getDestinatario().getCpf())));
					        pdfCellTabLayout[i] = new PdfPCell(pCpfCnpj);
					        pdfCellTabLayout[i].setColspan(7);
					        pdfCellTabLayout[i].setBorderWidthTop(0);
					        pdfCellTabLayout[i].setBorderWidthBottom(0);
					        pdfCellTabLayout[i].setHorizontalAlignment(0);
					        pdfCellTabLayout[i].setPaddingLeft(42);
					        cells.add(pdfCellTabLayout[i]);
					        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				        }

				       
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
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setBorderWidthRight(0);
	        			cells.add(pdfCellTabLayout[i]);
	            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	        	}else if(i==2) {
	        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph("Código"));
	        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
	        			pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setBorderWidthRight(0);
				        pdfCellTabLayout[i].setBorderWidthLeft(0);
	        			cells.add(pdfCellTabLayout[i]);
	            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	        	}else if(i==3) {
	        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph("Descrição"));
	        			pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setBorderWidthRight(0);
				        pdfCellTabLayout[i].setBorderWidthLeft(0);
	        			cells.add(pdfCellTabLayout[i]);
	            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	        	}else if(i==4) {
	        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph("Qtde"));
	        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
	        			pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setBorderWidthRight(0);
				        pdfCellTabLayout[i].setBorderWidthLeft(0);
	        			cells.add(pdfCellTabLayout[i]);
	            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	        	}else if(i==5) {
	        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph("Un"));
	        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
	        			pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setBorderWidthRight(0);
				        pdfCellTabLayout[i].setBorderWidthLeft(0);
	        			cells.add(pdfCellTabLayout[i]);
	            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	        	}else if(i==6) {
	        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph("Valor Un."));
	        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
	        			pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setBorderWidthRight(0);
				        pdfCellTabLayout[i].setBorderWidthLeft(0);
	        			cells.add(pdfCellTabLayout[i]);
	            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	        	}else if(i==7) {
	        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph("Valor Total"));
	        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
	        			pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setBorderWidthLeft(0);
	        			cells.add(pdfCellTabLayout[i]);
	            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	            		
	        	}
		    		
	    	} 	
	    	
	    }
      
	    pdfPTableLayout.getRows().add(new PdfPRow(cells.toArray(new PdfPCell[cells.size()]))); 
	}
	
	private static void addRowListaItens(PdfWriter writer,PdfPTable pdfPTableLayout, List<DocumentoFiscalEltronico> doc) {
		List<PdfPCell> cells = new ArrayList<PdfPCell>();

	    for (DocumentoFiscalEltronico value : doc) {
	    	PdfPCell[] pdfCellTabLayout = new PdfPCell[8];
	    	for(Produtos p: value.getProds()){
		    	
		    	for(int i =0; i < pdfCellTabLayout.length; i++){
		    		if(i==1){
		    			
	        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(p.getNumItem()));
	        			pdfCellTabLayout[i].setHorizontalAlignment(1);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setBorderWidthRight(0);
	        			cells.add(pdfCellTabLayout[i]);
	            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
	        	     }else if(i==2) {
		        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(p.getCodItem()));
					        pdfCellTabLayout[i].setBorderWidthTop(0);
					        pdfCellTabLayout[i].setBorderWidthBottom(0);
					        pdfCellTabLayout[i].setBorderWidthRight(0);
					        pdfCellTabLayout[i].setBorderWidthLeft(0);
		        			cells.add(pdfCellTabLayout[i]);
		            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
		        	}else if(i==3) {
		        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(p.getDescricao()));
					        pdfCellTabLayout[i].setBorderWidthTop(0);
					        pdfCellTabLayout[i].setBorderWidthBottom(0);
					        pdfCellTabLayout[i].setBorderWidthRight(0);
					        pdfCellTabLayout[i].setBorderWidthLeft(0);
		        			cells.add(pdfCellTabLayout[i]);
		            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
		        	}else if(i==4) {
		        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(UtilsEConverters.valorFormatadoPadrao(p.getQtdComercial())));
		        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					        pdfCellTabLayout[i].setBorderWidthTop(0);
					        pdfCellTabLayout[i].setBorderWidthBottom(0);
					        pdfCellTabLayout[i].setBorderWidthRight(0);
					        pdfCellTabLayout[i].setBorderWidthLeft(0);
		        			cells.add(pdfCellTabLayout[i]);
		            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
		        	}else if(i==5) {
		        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(p.getUndComercial()));
		        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					        pdfCellTabLayout[i].setBorderWidthTop(0);
					        pdfCellTabLayout[i].setBorderWidthBottom(0);
					        pdfCellTabLayout[i].setBorderWidthRight(0);
					        pdfCellTabLayout[i].setBorderWidthLeft(0);
		        			cells.add(pdfCellTabLayout[i]);
		            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
		        	}else if(i==6) {
		        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(
		        					 UtilsEConverters.valorFormatadoPadrao(p.getVlUnComerial())));
		        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_RIGHT);
					        pdfCellTabLayout[i].setBorderWidthTop(0);
					        pdfCellTabLayout[i].setBorderWidthBottom(0);
					        pdfCellTabLayout[i].setBorderWidthRight(0);
					        pdfCellTabLayout[i].setBorderWidthLeft(0);
		        			cells.add(pdfCellTabLayout[i]);
		            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
		        	}else if(i==7) {
		        			pdfCellTabLayout[i] = new PdfPCell(new Paragraph(UtilsEConverters.valorFormatadoPadrao(p.getVlProduto())));
		        			pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_RIGHT);
		        			pdfCellTabLayout[i].setPaddingRight(5);
					        pdfCellTabLayout[i].setBorderWidthTop(0);
					        pdfCellTabLayout[i].setBorderWidthBottom(0);
					        pdfCellTabLayout[i].setBorderWidthLeft(0);
					        pdfCellTabLayout[i].setPaddingRight(5);
		        			cells.add(pdfCellTabLayout[i]);
		            		pdfPTableLayout.addCell(pdfCellTabLayout[i]);
		        	}
		    		
		    	}
	    	}
	    	
	    	//Linha Valor Produtos/Serviços
			for (int i = 0; i < pdfCellTabLayout.length; i++) {
				if (i == 0) {
					pdfCellTabLayout[i] = new PdfPCell(new Paragraph("Produtos/Serviços"));
					pdfCellTabLayout[i].setColspan(6);
					pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_LEFT);
			        pdfCellTabLayout[i].setBorderWidthTop(0);
			        pdfCellTabLayout[i].setBorderWidthBottom(0);
			        pdfCellTabLayout[i].setBorderWidthRight(0);
			        pdfCellTabLayout[i].setPaddingLeft(6);
					cells.add(pdfCellTabLayout[i]);
					pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				} else if (i == 1) {
					pdfCellTabLayout[i] = new PdfPCell(new Paragraph(UtilsEConverters.valorFormatadoReal(value.getTotal().getIcmsTot().getVlProd())));
					pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_RIGHT);
        			pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
			        pdfCellTabLayout[i].setBorderWidthTop(0);
			        pdfCellTabLayout[i].setBorderWidthBottom(0);
			        pdfCellTabLayout[i].setBorderWidthLeft(0);
			        pdfCellTabLayout[i].setPaddingRight(5);
					cells.add(pdfCellTabLayout[i]);
					pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				}
				
			}
			
	    	
	    	if(!value.getTotal().getIcmsTot().getVlDesc().equals("0.00")) {
		    	//Linha Valor Desconto
				for (int i = 0; i < pdfCellTabLayout.length; i++) {
					if (i == 0) {
						pdfCellTabLayout[i] = new PdfPCell(new Paragraph("Desconto"));
						pdfCellTabLayout[i].setColspan(6);
						pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_LEFT);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setBorderWidthRight(0);
				        pdfCellTabLayout[i].setPaddingLeft(6);
						cells.add(pdfCellTabLayout[i]);
						pdfPTableLayout.addCell(pdfCellTabLayout[i]);
					} else if (i == 1) {
						pdfCellTabLayout[i] = new PdfPCell(new Paragraph(UtilsEConverters.valorFormatadoReal(value.getTotal().getIcmsTot().getVlDesc())));
						pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_RIGHT);
	        			pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setBorderWidthLeft(0);
				        pdfCellTabLayout[i].setPaddingRight(5);
						cells.add(pdfCellTabLayout[i]);
						pdfPTableLayout.addCell(pdfCellTabLayout[i]);
					}
					
				}
				
		    	//Linha Valor Produtos/Serviços
				for (int i = 0; i < pdfCellTabLayout.length; i++) {
					if (i == 0) {
						pdfCellTabLayout[i] = new PdfPCell(new Paragraph("Valor Total"));
						pdfCellTabLayout[i].setColspan(6);
						pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_LEFT);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setBorderWidthRight(0);
				        pdfCellTabLayout[i].setPaddingLeft(6);
						cells.add(pdfCellTabLayout[i]);
						pdfPTableLayout.addCell(pdfCellTabLayout[i]);
					} else if (i == 1) {
						pdfCellTabLayout[i] = new PdfPCell(new Paragraph(UtilsEConverters.valorFormatadoReal(value.getTotal().getvCFe())));
						pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_RIGHT);
	        			pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setBorderWidthLeft(0);
				        pdfCellTabLayout[i].setPaddingRight(5);
						cells.add(pdfCellTabLayout[i]);
						pdfPTableLayout.addCell(pdfCellTabLayout[i]);
					}
					
				}
	    	}

			
			

			
			//Linha Valor Pagamentos
			for (int i = 0; i < pdfCellTabLayout.length; i++) {
				if (i == 0) {
					pdfCellTabLayout[i] = new PdfPCell(new Paragraph("Pagamento"));
					pdfCellTabLayout[i].setColspan(7);
					pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_LEFT);
			        pdfCellTabLayout[i].setBorderWidthTop(0);
			        pdfCellTabLayout[i].setBorderWidthBottom(0);
			        pdfCellTabLayout[i].setPaddingLeft(6);
					cells.add(pdfCellTabLayout[i]);
					pdfPTableLayout.addCell(pdfCellTabLayout[i]);
				}
			}
			//Linha Valor Pagamentos
			for(Pagamento pagamento : value.getPagamentos()){
				
				for (int i = 0; i < pdfCellTabLayout.length; i++) {
					if (i == 0) {
						pdfCellTabLayout[i] = new PdfPCell(new Paragraph(pagamento.getDescricaoDoMeioDePagamento()));
						pdfCellTabLayout[i].setColspan(6);
						pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_LEFT);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setBorderWidthRight(0);
				        pdfCellTabLayout[i].setPaddingLeft(6);
						cells.add(pdfCellTabLayout[i]);
						pdfPTableLayout.addCell(pdfCellTabLayout[i]);
					} else if (i == 1) {
						pdfCellTabLayout[i] = new PdfPCell(new Paragraph(UtilsEConverters.valorFormatadoReal(pagamento.getValorDoMeioDePagamento())));
						pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_RIGHT);
	        			pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
				        pdfCellTabLayout[i].setBorderWidthLeft(0);
				        pdfCellTabLayout[i].setPaddingRight(5);
						cells.add(pdfCellTabLayout[i]);
						pdfPTableLayout.addCell(pdfCellTabLayout[i]);
					}
					
				}
				
			}
			

	    	//Linha código barras
			for (int i = 0; i < pdfCellTabLayout.length; i++) {
				if (i == 0) {
					
					Paragraph pSAT = new Paragraph("SAT Nº ".concat(value.getIdent().getNumSerieSAT()));
			        pdfCellTabLayout[i] = new PdfPCell(pSAT);
			        pdfCellTabLayout[i].setColspan(7);
			        pdfCellTabLayout[i].setPaddingTop(10);
			        pdfCellTabLayout[i].setBorderWidthTop(0);
			        pdfCellTabLayout[i].setBorderWidthBottom(0);
					pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
					pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
			        cells.add(pdfCellTabLayout[i]);
			        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
			        
			        MaskFormatter mask;
					try {
						mask = new MaskFormatter("##:##:##");
						mask.setValueContainsLiteralCharacters(false);
						Paragraph pDataHora = new Paragraph(
								UtilsEConverters.getDataParaString2(UtilsEConverters.getStringParaData3(value.getIdent().getDataEmissao()))
								.concat(" ")
								.concat(mask.valueToString(value.getIdent().getHorarioEmissao())));
				        pdfCellTabLayout[i] = new PdfPCell(pDataHora);
				        pdfCellTabLayout[i].setColspan(7);
				        pdfCellTabLayout[i].setPaddingTop(0);
				        pdfCellTabLayout[i].setBorderWidthTop(0);
				        pdfCellTabLayout[i].setBorderWidthBottom(0);
						pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
						pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				        cells.add(pdfCellTabLayout[i]);
				        pdfPTableLayout.addCell(pdfCellTabLayout[i]);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        
					
					//Comecando a configurar o cod de barras			 
					PdfContentByte cb = writer.getDirectContent();
					Barcode128 codigoBarrasChave = new Barcode128();
					codigoBarrasChave.setCodeType(codigoBarrasChave.CODE128);
					codigoBarrasChave.setCode(value.getIdent().getChaveeletronica());
					Image imageCodBarra = codigoBarrasChave.createImageWithBarcode(cb, null, null);
					//pdfCellTabLayout[i] = new PdfPCell(new Paragraph("Francisco Lee"));
					pdfCellTabLayout[i] = new PdfPCell(new Paragraph(new Chunk(imageCodBarra,10, 20, true)));
					pdfCellTabLayout[i].setBorderWidthTop(0);
					pdfCellTabLayout[i].setBorderWidthBottom(0);
					pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
					pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					pdfCellTabLayout[i].setColspan(7);
					cells.add(pdfCellTabLayout[i]);
					pdfPTableLayout.addCell(pdfCellTabLayout[i]);
					
				    
					BarcodeQRCode my_code = new BarcodeQRCode(value.getIdent().getAssinaturaQRCODE(),200,200, null);
		            try {
						Image qr_image = my_code.getImage();
						pdfCellTabLayout[i] = new PdfPCell(new Paragraph(new Chunk(qr_image,10,1, true)));
						pdfCellTabLayout[i].setBorderWidthTop(0);
						pdfCellTabLayout[i].setBorderWidthBottom(0);
						pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
					    pdfCellTabLayout[i].setHorizontalAlignment(Element.ALIGN_CENTER);
						pdfCellTabLayout[i].setColspan(7);
						pdfCellTabLayout[i].setPaddingBottom(25);
						cells.add(pdfCellTabLayout[i]);
						pdfPTableLayout.addCell(pdfCellTabLayout[i]);
		            
		            
		            
		            } catch (BadElementException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		          
		
		            Paragraph pRodaPe = new Paragraph("Documentos gerenciados pelo Sistema LR-Fisco");
			        pdfCellTabLayout[i] = new PdfPCell(pRodaPe);
			        pdfCellTabLayout[i].setColspan(7);
			        pdfCellTabLayout[i].setPaddingBottom(20);
			        pdfCellTabLayout[i].setBorderWidthTop(0);
			        pdfCellTabLayout[i].setBorderWidthBottom(1);
					pdfCellTabLayout[i].setVerticalAlignment(Element.ALIGN_CENTER);
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
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
		 
		        //Open the document.
		        document.open();
		        
		        //Create Table object, Here 4 specify the no. of columns
		        float[] colsWidth = {0.2f, 0.4f,1.6f,0.3f, 0.2f,0.5f, 0.6f};
		        PdfPTable pdfPTableLayout = new PdfPTable(colsWidth);
		        pdfPTableLayout.setWidthPercentage(80); //Width 100%
		        pdfPTableLayout.setSpacingBefore(10f); //Space before table
		        pdfPTableLayout.setSpacingAfter(10f); //Space after table
		        
		        List<DocumentoFiscalEltronico> validaTipoDeParseNFE = parse.validaTipoDeParseNFE(diretorio)
		        		.stream().filter(mod -> mod.getIdent().getModeloDoc().equals("59"))
		        		.collect(Collectors.toList());
 	
		        addRowCabecalho(pdfPTableLayout,validaTipoDeParseNFE);
		        addRowListaItens(writer,pdfPTableLayout,validaTipoDeParseNFE);
		      
		        
                //Add content to the document using Table objects.
		        document.add(pdfPTableLayout);
		        
		        // Adicionar nova página
		        //document.newPage();
		        
		        //Close document and outputStream.
		        document.close();
		        outputStream.close();
		 
		        System.out.println("Pdf created successfully.");
		        
		    } catch (Exception e) {
			    e.printStackTrace();
		    }
   }
}
