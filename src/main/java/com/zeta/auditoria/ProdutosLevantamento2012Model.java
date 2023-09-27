package com.zeta.auditoria;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_produtos_levantamento_2012_ajustada")
public class ProdutosLevantamento2012Model implements Serializable, Comparator<ProdutosLevantamento2012Model> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8090698342679274090L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "produto_sefaz")
	private String produtoSefaz;
	@Column(name = "codigo_antigo1")
	private String codigoAntigo1;
	@Column(name = "codigo_antigo2")
	private String codigoAntigo2;
	@Column(name = "codigo_atual")
	private String codigoAtual;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "unidade")
	private String unidade;
	@Column(name = "fator")
	private int fator;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProdutoSefaz() {
		return produtoSefaz;
	}
	public void setProdutoSefaz(String produtoSefaz) {
		this.produtoSefaz = produtoSefaz;
	}
	public String getCodigoAntigo1() {
		return codigoAntigo1;
	}
	public void setCodigoAntigo1(String codigoAntigo1) {
		this.codigoAntigo1 = codigoAntigo1;
	}
	public String getCodigoAntigo2() {
		return codigoAntigo2;
	}
	public void setCodigoAntigo2(String codigoAntigo2) {
		this.codigoAntigo2 = codigoAntigo2;
	}
	public String getCodigoAtual() {
		return codigoAtual;
	}
	public void setCodigoAtual(String codigoAtual) {
		this.codigoAtual = codigoAtual;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public int getFator() {
		return fator;
	}
	public void setFator(int fator) {
		this.fator = fator;
	}
	@Override
	public int compare(ProdutosLevantamento2012Model o1, ProdutosLevantamento2012Model o2) {		
		return o1.getDescricao().compareTo(o2.getDescricao());
	}
	
	
   
}
