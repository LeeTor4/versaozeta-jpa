package cruzamentos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.zeta.model.Cfop;
import com.zeta.util.UtilsEConverters;

public class ArquivosSiget {

	public static void lerRelacaoNfeSitram(String caminho) {
		File arquivoCSV = new File(caminho);
		String linhaDoArquivo = new String();

		try {
			@SuppressWarnings("resource")
			Scanner leitor = new Scanner(arquivoCSV);
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
		 
		String caminhoSitram = "C:\\Users\\chico\\Downloads\\Matriz_sitram_202301.csv";		
		String caminho = "C:\\Users\\chico\\Downloads\\NfeRelacaoEmitidas.csv";		
		String caminhoCfop = "src\\main\\resources\\utils\\rel_cfop_v1.csv";	
		//lerRelacaoNfeSiget(caminho);
		lerRelacaoNfeSitram(caminhoSitram);
		
	
		
//		List<String> codigosCfops = new ArrayList<String>();
//		List<Cfop> lerRelacaoCfopQueMovimentaEstoque = UtilsEConverters.lerRelacaoCfop(caminhoCfop).stream()
//				   .filter(c -> c.getMovimentaEstoque().equals("S"))
//				   .collect(Collectors.toList());
//		for(Cfop cfop : lerRelacaoCfopQueMovimentaEstoque){
//			codigosCfops.add(cfop.getCodigo());
//		}
//		
//		System.out.println(codigosCfops.contains("2923"));
		
	}
}
