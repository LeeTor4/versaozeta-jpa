package xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

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
		    
		    
		    Path xml = Paths.get("C:\\Users\\chico\\Downloads\\txt-xml\\23210905329222000419550010000265451000265454-nfe.xml");
		    Path txt = Paths.get("C:\\Users\\chico\\Downloads\\txt-xml\\SpedEFD-05329222000419-063882345-Remessa de arquivo original-ago2021.txt");
		    VerificaEncodingXml reader = new VerificaEncodingXml();
	        try {	            
	        	InputStream strm = new FileInputStream(xml.toString());
	        	
	            if(reader.isUTF8(strm)){
	                System.out.println("O documento é UTF-8");
	            }else{
	                System.out.println("O documento não é UTF-8");
	            }
	            	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
		    
		    
		    
	    }
}
