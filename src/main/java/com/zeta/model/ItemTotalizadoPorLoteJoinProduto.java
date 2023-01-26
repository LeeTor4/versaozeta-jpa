package com.zeta.model;

import java.util.Objects;

import javax.persistence.ColumnResult;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
@SqlResultSetMappings(
    {
	   @SqlResultSetMapping(name = "mapeamento.ItemTotalizadoPorLoteJoinProduto", classes = {
               @ConstructorResult(targetClass = ItemTotalizadoPorLoteJoinProduto.class, columns = {
                       @ColumnResult(name = "ano", type = String.class),
                       @ColumnResult(name = "cnpj", type = String.class),
                       @ColumnResult(name = "codItem", type = String.class),
                       @ColumnResult(name = "descricao", type = String.class),
                       @ColumnResult(name = "UnidadedeMedidaPadrao", type = String.class),
                       @ColumnResult(name = "mes", type = String.class),
                       @ColumnResult(name = "operacao", type = String.class),
                       @ColumnResult(name = "vlTotQtde", type = Double.class),
                       @ColumnResult(name = "vlTotItem", type = Double.class),
                      
               }) 
	   })
	}	
)
public class ItemTotalizadoPorLoteJoinProduto {

	private String ano;
	private String cnpj;
	private String codItem;
	private String descricao;
	private String UnidadeDeMedidaPadrao;
	private String mes;
	private String operacao;
	private Double vlTotQtde;
	private Double vlTotItem;
	
	public ItemTotalizadoPorLoteJoinProduto() {
		
	}
	public ItemTotalizadoPorLoteJoinProduto(String ano, String cnpj, String codItem, String descricao,
			String unidadeDeMedidaPadrao, String mes, String operacao, Double vlTotQtde, Double vlTotItem) {
	
		this.ano = ano;
		this.cnpj = cnpj;
		this.codItem = codItem;
		this.descricao = descricao;
		this.UnidadeDeMedidaPadrao = unidadeDeMedidaPadrao;
		this.mes = mes;
		this.operacao = operacao;
		this.vlTotQtde = vlTotQtde;
		this.vlTotItem = vlTotItem;
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
	public String getUnidadeDeMedidaPadrao() {
		return UnidadeDeMedidaPadrao;
	}
	public void setUnidadeDeMedidaPadrao(String unidadeDeMedidaPadrao) {
		UnidadeDeMedidaPadrao = unidadeDeMedidaPadrao;
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
		ItemTotalizadoPorLoteJoinProduto other = (ItemTotalizadoPorLoteJoinProduto) obj;
		return Objects.equals(ano, other.ano) && Objects.equals(cnpj, other.cnpj)
				&& Objects.equals(codItem, other.codItem);
	}
	
	
	
}
