package com.zeta.model;

import java.time.LocalDate;

// {"Codigo":"01","Descricao":"Animais vivos.","Data_Inicio":"01/04/2022","Data_Fim":"31/12/9999","Tipo_Ato":"Res Camex","Numero_Ato":"272","Ano_Ato":"2021"}
public class Nomenclatura_Comum_Mercosul {

	private String codigo;
	private String descricao;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private String tipoAto;
	private String numeroAto;
	private String anoAto;
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
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalDate getDataFim() {
		return dataFim;
	}
	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
	public String getTipoAto() {
		return tipoAto;
	}
	public void setTipoAto(String tipoAto) {
		this.tipoAto = tipoAto;
	}
	public String getNumeroAto() {
		return numeroAto;
	}
	public void setNumeroAto(String numeroAto) {
		this.numeroAto = numeroAto;
	}
	public String getAnoAto() {
		return anoAto;
	}
	public void setAnoAto(String anoAto) {
		this.anoAto = anoAto;
	}
	
	
}
