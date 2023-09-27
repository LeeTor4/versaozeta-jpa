package cruzamentos;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.leetor4.handler.ParseDocXML;
import com.leetor4.model.nfe.DocumentoFiscalEltronico;

public class RenomearXML {

	public static void main(String[] args) throws IOException, JAXBException {

		ParseDocXML xmls = new ParseDocXML();
		String mes = "set";

		File folder = new File("E:\\EMPRESAS\\SELLENE\\MATRIZ\\SPED\\2014\\XML\\Terceiros\\".concat(mes).concat("\\"));
		//File folder = new File("E:\\XML"); // Pasta do seu xml
		List<DocumentoFiscalEltronico> validaTipoDeParseNFE = xmls.validaTipoDeParseNFE(folder);

		File[] listOfFiles = folder.listFiles();// pegando todos os arquivos que estao na pasta

		for (int i = 0; i < validaTipoDeParseNFE.size(); i++) {
			if (listOfFiles[i].isFile()) {// Checa se é arquivo, você pode checar se tem .xml no nome

				File f = new File("E:\\EMPRESAS\\SELLENE\\MATRIZ\\SPED\\2014\\XML\\Terceiros\\".concat(mes).concat("\\") + listOfFiles[i].getName());

				f.renameTo(new File("E:\\EMPRESAS\\SELLENE\\MATRIZ\\SPED\\2014\\XML\\Terceiros\\".concat(mes).concat("\\") + validaTipoDeParseNFE.get(i).getIdent().getChaveeletronica() + ".xml"));
			}
		}
		
		//=====================================================================================
		
//		List<File> arquivos = new ArrayList<File>();
//		String filter = "23200305329222000419";
//		Path dir = Paths.get("E:\\XML");
//		try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir, filter.concat("*.xml"))){
//			for(Path path : stream) {
//				//System.out.println(path.toFile());
//				arquivos.add(path.toFile());
//			}
//		}
//		
//		for (File arquivo : arquivos) {
//			System.out.println(arquivo);
//		}

	}
}
