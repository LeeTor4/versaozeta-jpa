package com.zeta.handler;

import java.io.File;
import java.util.concurrent.Callable;

import com.leetor4.model.nfe.DocumentoFiscalEltronico;
import com.leetor4.model.nfe.Produtos;
import com.zeta.model.HistoricoItens;

import modulos.efdicms.entidades.RegC860;
import modulos.efdicms.manager.LeitorEfdIcms;


public class CallableHistItensCFes implements Callable<HistoricoItens>{

	private ImportaEfdIcms importacoes = new ImportaEfdIcms();
	private LeitorEfdIcms leitor;
	private Produtos p;
	private RegC860 regC860;
	private File f;
	private DocumentoFiscalEltronico doc;
	private int pDia;
	private int uDia;
	
	public CallableHistItensCFes(LeitorEfdIcms leitor, RegC860 regC860, File f,Produtos p, 
			DocumentoFiscalEltronico doc,int pDia, int uDia) {
		
		this.leitor = leitor;
		this.p = p;
		this.regC860 = regC860;
		this.f = f;
		this.doc = doc;
		this.pDia = pDia;
		this.uDia = uDia;
	}
	
	@Override
	public HistoricoItens call() throws Exception {
		HistoricoItens retorno = null;
		if(importacoes.insereHistCFes(leitor,regC860 ,f, p, doc).getDtDoc().getDayOfMonth() >= pDia
				&& importacoes.insereHistCFes(leitor,regC860 ,f, p, doc).getDtDoc().getDayOfMonth() < uDia) {
			 retorno = importacoes.insereHistCFes(leitor,regC860 ,f, p, doc);
		}
		//importacoes.insereNotasProprias(leitor,p, doc, lote);
		return retorno;
	}

}
