package com.zeta.model;

public class Cfop {
	
	private String codigo;
	private String descricao;
	private String dataIni;
	private String dataFin;
	private String movimentaEstoque;
	
	public Cfop() {
		
	}
	
	public Cfop(String codigo,String descricao, String dataIni,String dataFin,String movimentaEstoque) {
		this.codigo = codigo;
		this.descricao =  descricao;
		this.dataIni = dataIni;
		this.dataFin = dataFin;
		this.movimentaEstoque = movimentaEstoque;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDataIni() {
		return dataIni;
	}
	public void setDataIni(String dataIni) {
		this.dataIni = dataIni;
	}
	public String getDataFin() {
		return dataFin;
	}
	public void setDataFin(String dataFin) {
		this.dataFin = dataFin;
	}
	public String getMovimentaEstoque() {
		return movimentaEstoque;
	}
	public void setMovimentaEstoque(String movimentaEstoque) {
		this.movimentaEstoque = movimentaEstoque;
	}
	
	
	
}
