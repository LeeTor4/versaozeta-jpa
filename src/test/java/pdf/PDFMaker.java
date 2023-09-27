package pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFMaker {

	public static final String RESULT =
			"E:\\treinando.pdf";
	
	 public static void main(String args[]) throws DocumentException, FileNotFoundException{
		 
		 // Cria um novo documento com tamanho e margens definidas pelo
	        // usuário
	        // new Document(tamanho da página, margem esquerda, margem direita,
	        // margem topo, margem rodapé);
	        Document doc = new Document();
	 
	        // Criando o arquivo de saída.
	        OutputStream os = new FileOutputStream(RESULT);
	 
	        // Associando o doc ao arquivo de saída.
	        PdfWriter.getInstance(doc, os);
	 
	        // Abrindo o documento para a edição
	        doc.open();
	 
	    
	        // Definindo uma fonte, com tamanho 20 e negrito
	        Font f = new Font(FontFamily.COURIER, 20, Font.BOLD);
	 
	        // Adicionando um parágrafo ao PDF, com a fonte definida acima
	        Paragraph p = new Paragraph("Utilizando a API iText", f);
	 
	        // Setando o alinhamento p/ o centro
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	 
	        // Definindo
	        p.setSpacingAfter(50);
	        doc.add(p);
	 
	        // Criando uma tabela com 3 colunas
	        PdfPTable table = new PdfPTable(3);
	        // Título para a tabela
	        Paragraph tableHeader = new Paragraph("Um título de uma tabela simples");
	 
	        PdfPCell header = new PdfPCell(tableHeader);
	        // Definindo que o header vai ocupar as 3 colunas
	        header.setColspan(3);
	        // Definindo alinhamento do header
	        header.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
	        // Adicionando o header à tabela
	        table.addCell(header);
	 
	        List<String> list = new ArrayList<String>();
	 
	        list.add("Testando linha 1, coluna 1");
	        list.add("Testando linha 1, coluna 2");
	        list.add("Testando linha 1, coluna 3");
	        list.add("Testando linha 2, coluna 1");
	        list.add("Testando linha 2, coluna 2");
	        list.add("Testando linha 2, coluna 3");
	        list.add("Testando linha 3, coluna 1");
	        list.add("Testando linha 3, coluna 2");
	        list.add("Testando linha 3, coluna 3");
	 
	        for (String s : list) {
	            table.addCell(s);
	        }
	 
	        doc.add(table);
	 
	        doc.close();
	 }
}
