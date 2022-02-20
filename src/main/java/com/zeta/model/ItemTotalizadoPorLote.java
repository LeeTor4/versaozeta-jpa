package com.zeta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_saldo_itn_tot_lote")
public class ItemTotalizadoPorLote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long            id;
	@Column(name = "lote_id")
	private Long        idLote;
	private String        cnpj;
	private int            ano;
	private int            mes;
	private String    operacao;
	private int    frequencia;
	private String     codItem;
	private Double   vlTotQtde;
	private Double   vlTotItem;
	
	public ItemTotalizadoPorLote() {
		
	}
	
	
	public ItemTotalizadoPorLote(String cnpj, int ano, int mes, String operacao, int frequencia,
			String codItem, Double vlTotQtde, Double vlTotItem) {
	
		this.cnpj = cnpj;
		this.ano = ano;
		this.mes = mes;
		this.operacao = operacao;
		this.frequencia = frequencia;
		this.codItem = codItem;
		this.vlTotQtde = vlTotQtde;
		this.vlTotItem = vlTotItem;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdLote() {
		return idLote;
	}
	public void setIdLote(Long idLote) {
		this.idLote = idLote;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public String getOperacao() {
		return operacao;
	}
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	public int getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(int frequencia) {
		this.frequencia = frequencia;
	}
	public String getCodItem() {
		return codItem;
	}
	public void setCodItem(String codItem) {
		this.codItem = codItem;
	}
	public Double getVlTotQtde() {
		return vlTotQtde;
	}
	public void setVlTotQtde(Double vlTotQtde) {
		this.vlTotQtde = vlTotQtde;
	}
	public Double getVlTotItem() {
		return vlTotItem;
	}
	public void setVlTotItem(Double vlTotItem) {
		this.vlTotItem = vlTotItem;
	}
	
	
	
}
