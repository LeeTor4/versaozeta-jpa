package pdf;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class LeitorTxt {

	public static void main(String[] args) throws IOException {
		
		  List<String> novasLinhas =  new ArrayList<String>();   
		
		  File file = new File("C:\\Users\\chico\\Downloads\\PRODUTOS_AGRUPADOS_2012.txt");
		  Path pV12 = Paths.get("C:\\Users\\chico\\Downloads\\PRODUTOS_AGRUPADOS_2012_alterado.txt");
		  Path dest = pV12;
		  
          String header1 = "Secretaria da Fazenda do Estado do Ceará";
          String header2 = "Administração Tributária - CATRI";
          String header3 = "Agrupamento de Produtos";
          String header4 = "Tipo";
          String header5 = "-----------------------------------------------------------------------------------";

		  List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);
		  for (int i = 0; i < lines.size(); i++) {
			  
			  if(!lines.get(i).contains(header1)) {
				  if(!lines.get(i).contains(header2)) {
					  if(!lines.get(i).contains(header3)) {
						  if(!lines.get(i).contains(header4)) {
							  if(!lines.get(i).contains(header5)) {
								  novasLinhas.add(lines.get(i));
								  System.out.println(lines.get(i));
							  }

							
						  }

					  }  
				  }
			  }

		  }
		  
		 Files.write(dest, novasLinhas, StandardOpenOption.CREATE);
	}
}
