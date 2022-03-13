package com.zeta.handler;

import java.util.concurrent.Callable;

import com.zeta.model.HistoricoItens;

import modulos.efdicms.manager.LeitorEfdIcms;


public class CallableHistItensECFs implements Callable<HistoricoItens >{

	private ImportaEfdIcms importacoes = new ImportaEfdIcms();
	private LeitorEfdIcms leitor;
	private int i; 
	private int z; 
	private int l; 
	private int m;
	private int pDia;
	private int uDia;
	private Long idEst;
	
	public CallableHistItensECFs(LeitorEfdIcms leitor,int i, int z, int l, int m,Long idEst,int pDia, int uDia) {
		
		this.leitor = leitor;
		this.i = i;
		this.z = z;
		this.l = l;
		this.m = m;
		this.pDia = pDia;
		this.uDia = uDia;
		this.idEst = idEst;
	}
	
	@Override
	public HistoricoItens call() throws Exception {
		HistoricoItens retorno = null;
		if(importacoes.insereReducoes(leitor,i, z, l, m,idEst).getDtDoc().getDayOfMonth() >= pDia
				&& importacoes.insereReducoes(leitor,i, z, l, m,idEst).getDtDoc().getDayOfMonth() < uDia) {
			 retorno = importacoes.insereReducoes(leitor,i, z, l, m,idEst);
		}
		return retorno;
	}

}
