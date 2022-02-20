package com.zeta.handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.leetor4.handler.ParseDocXML;
import com.leetor4.model.nfe.DocumentoFiscalEltronico;

import modulos.efdicms.entidades.RegC100;
import modulos.efdicms.manager.LeitorEfdIcms;

public class CruzamentoNotasSpedsComXMLs {
	
	
	public List<String> listaDeChavesEmissoesTerceirosSpedICMS(LeitorEfdIcms leitor){		
		List<String> retorno = new ArrayList<String>();		
		for(RegC100 c100 : leitor.getRegsC100()){
			if(c100.getIndEmit().equals("1")) {
				retorno.add(c100.getChvNfe());
			}
		}
		return retorno;		
	}
	
	public List<String> listaDeChavesEmissoesPropriasSpedICMS(LeitorEfdIcms leitor){		
		List<String> retorno = new ArrayList<String>();		
		for(RegC100 c100 : leitor.getRegsC100()){
			if(c100.getIndEmit().equals("0")) {
				retorno.add(c100.getChvNfe());
			}
		}
		return retorno;		
	}
	
	public List<String> listaDeChavesNotasXML(String file){
		List<String> retorno = new ArrayList<String>();	
		ParseDocXML parseDocXML = new ParseDocXML();
		File f = new File(file);
		try {
			
			for (DocumentoFiscalEltronico doc : parseDocXML.validaTipoDeParseNFE(f)) {  
				if(doc.getIdent().getModeloDoc().equals("55")){
					retorno.add(doc.getIdent().getChaveeletronica());
				}else {	
					break;
				}	
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return retorno;		
	}
	
	public void cruzamentosNotasSpedFiscalComXMLTerceiros(String x, LeitorEfdIcms leitor) {
		List<String> listaDeChavesXMLNF = listaDeChavesNotasXML(x);
		List<String> listaDeChavesSpedICMS = listaDeChavesEmissoesTerceirosSpedICMS(leitor);

		for(String chaves :  listaDeChavesSpedICMS){
			if(!listaDeChavesXMLNF.contains(chaves)) {
				System.out.println(chaves + " -> XML não encontrado!!");
			}
		}
		for(String chaves :  listaDeChavesXMLNF){
			if(!listaDeChavesSpedICMS.contains(chaves)) {
				System.out.println(chaves + " -> Nota não consta na escrita!!");
			}
		}
		System.out.println("Terminou !!!");
	}
	
	public void cruzamentosNotasSpedFiscalComXMLProprios(String x, LeitorEfdIcms leitor) {
		List<String> listaDeChavesXMLNF = listaDeChavesNotasXML(x);
		List<String> listaDeChavesSpedICMS = listaDeChavesEmissoesPropriasSpedICMS(leitor);

		for(String chaves :  listaDeChavesSpedICMS){
			if(!listaDeChavesXMLNF.contains(chaves)) {
				System.out.println(chaves + " -> XML não encontrado!!");
			}
		}
		for(String chaves :  listaDeChavesXMLNF){
			if(!listaDeChavesSpedICMS.contains(chaves)) {
				System.out.println(chaves + " -> Nota não consta na escrita!!");
			}
		}
		System.out.println("Terminou !!!");
	}

}
