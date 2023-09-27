package com.zeta.auditoria;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_produtos_depara_procvit")
public class CodigosProcVitModel implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1429506906133647367L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long  id;
	@Column(name = "CODIGO_PROC")
	private String codigoProc;
	@Column(name = "CODIGO_VIT")
	private String codigoVit;
	@Column(name = "DESCRICAO_PRODUTO")
	private String descricaoProduto;
	@Column(name = "DESCRICAO_REDUZIDA")
	private String descricaoReduzida;
	@Column(name = "UNIDADE_MEDIDA")
	private String unidadeMedida;
	@Column(name = "EMBALAGEM_INDUSTRIA")
	
	private int embalagemIndustria;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigoProc() {
		return codigoProc;
	}
	public void setCodigoProc(String codigoProc) {
		this.codigoProc = codigoProc;
	}
	public String getCodigoVit() {
		return codigoVit;
	}
	public void setCodigoVit(String codigoVit) {
		this.codigoVit = codigoVit;
	}
	public String getDescricaoProduto() {
		return descricaoProduto;
	}
	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}
	public String getDescricaoReduzida() {
		return descricaoReduzida;
	}
	public void setDescricaoReduzida(String descricaoReduzida) {
		this.descricaoReduzida = descricaoReduzida;
	}
	public String getUnidadeMedida() {
		return unidadeMedida;
	}
	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}
	public int getEmbalagemIndustria() {
		return embalagemIndustria;
	}
	public void setEmbalagemIndustria(int embalagemIndustria) {
		this.embalagemIndustria = embalagemIndustria;
	}
	
	
	
}
