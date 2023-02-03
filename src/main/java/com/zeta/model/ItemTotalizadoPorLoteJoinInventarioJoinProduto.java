package com.zeta.model;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;

@MappedSuperclass
@SqlResultSetMappings(
    {
	   @SqlResultSetMapping(name = "mapeamento.ItemTotalizadoPorLoteJoinInventarioJoinProduto", classes = {
               @ConstructorResult(targetClass = ItemTotalizadoPorLoteJoinInventarioJoinProduto.class, columns = {
                       @ColumnResult(name = "ano", type = String.class),
                       @ColumnResult(name = "cnpj", type = String.class),
                       @ColumnResult(name = "codItem", type = String.class),
                       @ColumnResult(name = "descricao", type = String.class),
                       @ColumnResult(name = "UnidadeMedidaPadrao", type = String.class),
                       @ColumnResult(name = "operacao", type = String.class),
                       @ColumnResult(name = "mes", type = String.class),
                       @ColumnResult(name = "qtde", type = Double.class),
                       @ColumnResult(name = "vlItem", type = Double.class),
                       @ColumnResult(name = "saldo_acum_qtde", type = Double.class)
                      
               }) 
	   })
	}	
)
public class ItemTotalizadoPorLoteJoinInventarioJoinProduto {

	private String ano;
	private String cnpj;
	private String codItem;
	private String descricao;
	private String UnidadedeMedidaPadrao;
	private String operacao;
	private String mes;
	private Double qtde;
	private Double vlItem;
	private Double saldo_acum_qtde;
	
	public ItemTotalizadoPorLoteJoinInventarioJoinProduto() {
		
	}

	public ItemTotalizadoPorLoteJoinInventarioJoinProduto(String ano, String cnpj, String codItem, String descricao,
			String unidadedeMedidaPadrao, String operacao, String mes, Double qtde, Double vlItem,
			Double saldo_acum_qtde) {
		super();
		this.ano = ano;
		this.cnpj = cnpj;
		this.codItem = codItem;
		this.descricao = descricao;
		UnidadedeMedidaPadrao = unidadedeMedidaPadrao;
		this.operacao = operacao;
		this.mes = mes;
		this.qtde = qtde;
		this.vlItem = vlItem;
		this.saldo_acum_qtde = saldo_acum_qtde;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCodItem() {
		return codItem;
	}

	public void setCodItem(String codItem) {
		this.codItem = codItem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUnidadedeMedidaPadrao() {
		return UnidadedeMedidaPadrao;
	}

	public void setUnidadedeMedidaPadrao(String unidadedeMedidaPadrao) {
		UnidadedeMedidaPadrao = unidadedeMedidaPadrao;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public Double getQtde() {
		return qtde;
	}

	public void setQtde(Double qtde) {
		this.qtde = qtde;
	}

	public Double getVlItem() {
		return vlItem;
	}

	public void setVlItem(Double vlItem) {
		this.vlItem = vlItem;
	}

	public Double getSaldo_acum_qtde() {
		return saldo_acum_qtde;
	}

	public void setSaldo_acum_qtde(Double saldo_acum_qtde) {
		this.saldo_acum_qtde = saldo_acum_qtde;
	}
	
	
}
