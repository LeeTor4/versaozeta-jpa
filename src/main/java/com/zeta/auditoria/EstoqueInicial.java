package com.zeta.auditoria;

public class EstoqueInicial {
	
    private String codigoItem;
    private String operacao;
    private Double qtde;
    private Double vlUnit;
    private Double vlBruto;
    
	public String getCodigoItem() {
		return codigoItem;
	}
	public void setCodigoItem(String codigoItem) {
		this.codigoItem = codigoItem;
	}
	public String getOperacao() {
		return operacao;
	}
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	public Double getQtde() {
		return qtde;
	}
	public void setQtde(Double qtde) {
		this.qtde = qtde;
	}
	public Double getVlUnit() {
		return vlUnit;
	}
	public void setVlUnit(Double vlUnit) {
		this.vlUnit = vlUnit;
	}
	public Double getVlBruto() {
		return vlBruto;
	}
	public void setVlBruto(Double vlBruto) {
		this.vlBruto = vlBruto;
	}

    
}
