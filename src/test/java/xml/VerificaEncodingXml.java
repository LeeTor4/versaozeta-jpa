package xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class VerificaEncodingXml {

	 public boolean isUTF8(InputStream entrada) throws XMLStreamException {
	        XMLInputFactory factory = XMLInputFactory.newInstance();
	        XMLStreamReader xmlReader = factory.createXMLStreamReader(entrada);
	        //System.out.println(xmlReader.getEncoding());
	        return xmlReader.getEncoding().equalsIgnoreCase("UTF-8");
	 }
	 
	 static void copyFile(File src, File dst, String cnpj) throws IOException {
			InputStream in = new FileInputStream(src);
			
			if(src.getName().toString().contains(cnpj)){
				if(src.getName().toString().endsWith("-nfe.xml") || src.getName().toString().startsWith("ADCFe")) {
					OutputStream out = new FileOutputStream(dst);  // Transferindo bytes de entrada para saída
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {	
						out.write(buf, 0, len);
					}
					in.close();
					out.close();
					
				}
				
			}

		}
	 
	 public static void copyDirectory(File srcDir, File dstDir, String cnpj) throws IOException {
			if (srcDir.isDirectory()) {
				if (!dstDir.exists()) {
					dstDir.mkdir();
				}
				String[] children = srcDir.list();
				for (int i = 0; i < children.length; i++) {
					copyDirectory(new File(srcDir, children[i]), new File(dstDir, children[i]), cnpj);
				}
				
				progresso(children.length);
			} else {
				// Este método está implementado na dica – Copiando um arquivo utilizando o Java
				copyFile(srcDir, dstDir, cnpj);
				
			}
			
			
	    }
	 
	 public static void progresso(int maximo) {
		 final SwingProgressBarExample it = new SwingProgressBarExample();
		    JFrame frame = new JFrame("Progress Bar Example");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setContentPane(it);
		    frame.pack();
		    frame.setVisible(true);
		    
		    for (int i = 0; i <= maximo; i++) {
		    	
		    	final int percent = i;
		    	
				try {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							it.updateBar((percent*100)/100);
						}
					});
					java.lang.Thread.sleep(100);
					
				} catch (InterruptedException e) {

				}
		    }
		  
	 }
	    
	 public static void main(String[] args) throws IOException {
	    	
	    	String ano = "2021";
			String emp = "SELLENE";
			String estab = "MEGADIET";
			String cnpj  = "05329222000419";
			
			String anomes1  = ano.concat("01").concat(".txt");
			String anomesV1  = ano.concat("01_V2").concat(".txt");
			String anomes2  = ano.concat("02").concat(".txt");
			String anomesV2  = ano.concat("02_V2").concat(".txt");
			String anomes3  = ano.concat("03").concat(".txt");
			String anomesV3  = ano.concat("03_V2").concat(".txt");
			String anomes4  = ano.concat("04").concat(".txt");
			String anomesV4  = ano.concat("04_V2").concat(".txt");
			String anomes5  = ano.concat("05").concat(".txt");
			String anomesV5  = ano.concat("05_V2").concat(".txt");
			String anomes6  = ano.concat("06").concat(".txt");
			String anomesV6  = ano.concat("06_V2").concat(".txt");
			String anomes7  = ano.concat("07").concat(".txt");
			String anomesV7  = ano.concat("07_V2").concat(".txt");
			String anomes8  = ano.concat("08").concat(".txt");
			String anomesV8  = ano.concat("08_V2").concat(".txt");
			String anomes9  = ano.concat("09").concat(".txt");
			String anomesV9  = ano.concat("09_V2").concat(".txt");
			String anomes10 = ano.concat("10").concat(".txt");
			String anomesV10 = ano.concat("10_V2").concat(".txt");
			String anomes11 = ano.concat("11").concat(".txt");
			String anomesV11 = ano.concat("11_V2").concat(".txt");
			String anomes12 = ano.concat("12").concat(".txt");
			String anomesV12 = ano.concat("12_V2").concat(".txt");
			
			Path xP1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\jan"));
	        Path xT1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Terceiros").concat("\\").concat("\\jan"));
		    Path p1  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes1));
			
		    Path xP2 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\fev"));
		    Path p2  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes2));
			
		    Path xP3 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\mar"));
		    Path p3  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes3));
		    
		    Path xP4 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\abr"));
		    Path p4  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes4));
		    
		    Path xP5 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\mai"));
		    Path p5  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes5));
		    
		    Path xP6 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\jun"));
		    Path p6  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes6));
		    
		    Path xP7 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\jul"));
		    Path p7  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes7));
		    
		    Path xP8 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\ago"));
		    Path p8  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes8));
		    
		    Path xP9 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\set"));
		    Path p9  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes9));
		     
		    Path xP10 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\out"));
		    Path p10  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes10));
		    
		    Path xP11 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\nov"));
		    Path p11  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes11));
		    
		    Path xP12 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\dez"));
		    Path xT12 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Terceiros").concat("\\").concat("\\dez"));
		    Path p12  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes12));
	    	
//		    try(DirectoryStream<Path>  stream = Files.newDirectoryStream(xP9)){		    	
//		    	for(Path path : stream){
//		    		//System.out.println(path);
//				    VerificaEncodingXml reader = new VerificaEncodingXml();
//			        try {	            
//			        	InputStream strm = new FileInputStream(path.toString());
//			        	
//			            if(reader.isUTF8(strm)){
//			                System.out.println(path.getFileName() + " => O documento é UTF-8");
//			            }else{
//			                System.out.println(path.getFileName() + " => O documento não é UTF-8");
//			            }
//			            	            
//			        } catch (Exception e) {
//			            e.printStackTrace();
//			        } 
//		    	}
//		    }
		    
		    
//		    Path xml = Paths.get("C:\\Users\\chico\\Downloads\\txt-xml\\23210905329222000419550010000265451000265454-nfe.xml");
//		    Path txt = Paths.get("C:\\Users\\chico\\Downloads\\txt-xml\\SpedEFD-05329222000419-063882345-Remessa de arquivo original-ago2021.txt");
//		    VerificaEncodingXml reader = new VerificaEncodingXml();
//	        try {	            
//	        	InputStream strm = new FileInputStream(xml.toString());
//	        	
//	            if(reader.isUTF8(strm)){
//	                System.out.println("O documento é UTF-8");
//	            }else{
//	                System.out.println("O documento não é UTF-8");
//	            }
//	            	            
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        } 
		    
		    String codUf = "23";
		    String ano1 = "21";
		    String mes = "06";
		    String cnpj1 = "05329222000680";
		    Path dir = Paths.get("C:\\Users\\chico\\Downloads\\txt-xml");
//		    try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir, codUf.concat(ano1).concat(mes).concat(cnpj1).concat("*.xml"))){    
//		    	for(Path path : stream) {
//		    		System.out.println(path.getFileName());
//		    	}
//		    }
		    
		    //|0000|015|0|01062021|30062021|SELLENE COMERCIO E REPRESENTAÇÕES LTDA|05329222000419||CE|063882345|2304400|||B|1|
		    try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.txt")){    
		    	String retorno = "";
		    	for(Path path : stream) {
		    		try {
		    			List<String> lines = Files.readAllLines(path, Charset.forName("ISO-8859-1"));
		    			
		    			for (String line : lines) {
		    				if(line.startsWith("|0000|")) {
		    					System.out.println(line);
		    					System.out.println(line.substring(85,87)
		    							  .concat("|")
		    							  .concat(line.substring(18,20)
		    							  .concat(line.substring(14,16)
		    							  .concat("|")
		    							  .concat(line.substring(69,83)))));
		    				}
		    				
		    			}

		    		} catch (IOException e) {
		    			e.printStackTrace();
		    		}
		    	}
		    }
		    //System.out.println(codUf.concat(ano1).concat(mes).concat(cnpj1));    
		    
		    
		    Path dirOrigem = Paths.get("E:\\XML");
		    Path dirDestino = Paths.get("E:\\xml-servidor");
		    copyDirectory(dirOrigem.toFile(), dirDestino.toFile(),"05329222000419");
		    
		    
		    
	}
}
