package com.zeta.auditoria;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_historico_item_com_saldo")
public class HistoricoItemModel implements Serializable{
    
      
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 2333232451578184275L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long           id;  
	private String      empresa;
	private String      operacao;
	private String      ecfCx;
	private LocalDate   dtDoc;
	private String      numItem;
	private String      codItem;
	@Column(name = "codItem_anterior")
	private String      codItemAnterior;
	private BigDecimal  qtde;
	private String      und;
	private BigDecimal  vlUnit;
	private BigDecimal  vlBruto;
	private BigDecimal  desconto;
	private BigDecimal  vlLiq;
	private String      cst;
	private String      cfop;
	private String      codMod;
	private String      codSitDoc;
	private String      descricao;
	private String      numDoc;
	private String      chaveDoc;
	private String      nome;
	private String      cpfCnpj;
	@Column(name = "saldo_acum_qtde")
	private Double     saldoAcumuladoQtde;
	
	public HistoricoItemModel() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getOperacao() {
		return operacao;
	}
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	public String getEcfCx() {
		return ecfCx;
	}
	public void setEcfCx(String ecfCx) {
		this.ecfCx = ecfCx;
	}
	public LocalDate getDtDoc() {
		return dtDoc;
	}
	public void setDtDoc(LocalDate dtDoc) {
		this.dtDoc = dtDoc;
	}
	public String getNumItem() {
		return numItem;
	}
	public void setNumItem(String numItem) {
		this.numItem = numItem;
	}
	public String getCodItem() {
		return codItem;
	}
	public void setCodItem(String codItem) {
		this.codItem = codItem;
	}
	public String getCodItemAnterior() {
		return codItemAnterior;
	}
	public void setCodItemAnterior(String codItemAnterior) {
		this.codItemAnterior = codItemAnterior;
	}
	public BigDecimal getQtde() {
		return qtde;
	}
	public void setQtde(BigDecimal qtde) {
		this.qtde = qtde;
	}
	public String getUnd() {
		return und;
	}
	public void setUnd(String und) {
		this.und = und;
	}
	public BigDecimal getVlUnit() {
		return vlUnit;
	}
	public void setVlUnit(BigDecimal vlUnit) {
		this.vlUnit = vlUnit;
	}
	public BigDecimal getVlBruto() {
		return vlBruto;
	}
	public void setVlBruto(BigDecimal vlBruto) {
		this.vlBruto = vlBruto;
	}
	public BigDecimal getDesconto() {
		return desconto;
	}
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	public BigDecimal getVlLiq() {
		return vlLiq;
	}
	public void setVlLiq(BigDecimal vlLiq) {
		this.vlLiq = vlLiq;
	}
	public String getCst() {
		return cst;
	}
	public void setCst(String cst) {
		this.cst = cst;
	}
	public String getCfop() {
		return cfop;
	}
	public void setCfop(String cfop) {
		this.cfop = cfop;
	}
	public String getCodMod() {
		return codMod;
	}
	public void setCodMod(String codMod) {
		this.codMod = codMod;
	}
	public String getCodSitDoc() {
		return codSitDoc;
	}
	public void setCodSitDoc(String codSitDoc) {
		this.codSitDoc = codSitDoc;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getNumDoc() {
		return numDoc;
	}
	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}
	public String getChaveDoc() {
		return chaveDoc;
	}
	public void setChaveDoc(String chaveDoc) {
		this.chaveDoc = chaveDoc;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	public Double getSaldoAcumuladoQtde() {
		return saldoAcumuladoQtde;
	}
	public void setSaldoAcumuladoQtde(Double saldoAcumuladoQtde) {
		this.saldoAcumuladoQtde = saldoAcumuladoQtde;
	}
	
	
}
