package cruzamentos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ArquivosSiget {

	public static void lerRelacaoNfeSiget(String caminho) {
		File arquivoCSV = new File(caminho);
		String linhaDoArquivo = new String();

		try {
			@SuppressWarnings("resource")
			Scanner leitor = new Scanner(arquivoCSV);
			leitor.nextLine();
			leitor.nextLine();
			leitor.nextLine();
			leitor.nextLine();
			while (leitor.hasNext()) {
				linhaDoArquivo = leitor.nextLine();
				String[] valoresEntreVirgula = linhaDoArquivo.split("\\;");
				for (int i = 0; i < valoresEntreVirgula.length; i++) {
					if(linhaDoArquivo.length() > 50) {
						System.out.println(linhaDoArquivo.length() + " => " + valoresEntreVirgula[i]);
					}
				}
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		 
		String caminho = "C:\\Users\\chico\\Downloads\\NfeRelacaoEmitidas.csv";
		
		lerRelacaoNfeSiget(caminho);
	}
}
