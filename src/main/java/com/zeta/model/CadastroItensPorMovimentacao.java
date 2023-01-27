package com.zeta.model;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;

@MappedSuperclass
@SqlResultSetMappings(
    {
	   @SqlResultSetMapping(name = "mapeamento.CadastroItensPorMovimentacao", classes = {
               @ConstructorResult(targetClass = CadastroItensPorMovimentacao.class, columns = {
                       @ColumnResult(name = "cnpj", type = String.class),
                       @ColumnResult(name = "codItem", type = String.class),
                       @ColumnResult(name = "descricao", type = String.class),
                       @ColumnResult(name = "und", type = String.class)
               }) 
	   })
	}	
)
public class CadastroItensPorMovimentacao {

	private String cnpj;
	private String codItem;
	private String descricao;
	private String und;
	
	public CadastroItensPorMovimentacao() {
		
	}
	
	
	public CadastroItensPorMovimentacao(String cnpj, String codItem, String descricao,
			String und) {
		super();
		this.cnpj = cnpj;
		this.codItem = codItem;
		this.descricao = descricao;
		this.und = und;
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
	public String getUndMed() {
		return und;
	}
	public void setUndMed(String und) {
		this.und = und;
	}
}
