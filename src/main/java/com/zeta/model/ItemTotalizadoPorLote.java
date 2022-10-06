package com.zeta.model;

import java.util.Objects;

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
	private String         ano;
	private String         mes;
	private String    operacao;
	private int     frequencia;
	private String     codItem;
	private Double   vlTotQtde;
	private Double   vlTotItem;
	
	public ItemTotalizadoPorLote() {
		
	}
	
	
	public ItemTotalizadoPorLote(String operacao,
			String codItem, Double vlTotQtde, Double vlTotItem) {

		this.operacao = operacao;
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
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
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


	@Override
	public int hashCode() {
		return Objects.hash(ano, cnpj, codItem);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemTotalizadoPorLote other = (ItemTotalizadoPorLote) obj;
		return Objects.equals(ano, other.ano) && Objects.equals(cnpj, other.cnpj)
				&& Objects.equals(codItem, other.codItem);
	}
	
	
	
}
