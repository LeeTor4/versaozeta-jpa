package com.zeta.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "tb_equipecf")
public class EquipamentoECF implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long              id;
	@Column(name="est_id")
	private Long              idPai;
	private Long              idEmp;
	private Long              idEst;
	private String            codModDocFiscal;
	private String            modeloEquip;
	private String            numSerieFabECF;
	private String            numECF;
   
	
	public EquipamentoECF() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPai() {
		return idPai;
	}
	public void setIdPai(Long idPai) {
		this.idPai = idPai;
	}
	
	
	public Long getIdEmp() {
		return idEmp;
	}

	public void setIdEmp(Long idEmp) {
		this.idEmp = idEmp;
	}

	public Long getIdEst() {
		return idEst;
	}

	public void setIdEst(Long idEst) {
		this.idEst = idEst;
	}

	public String getCodModDocFiscal() {
		return codModDocFiscal;
	}
	public void setCodModDocFiscal(String codModDocFiscal) {
		this.codModDocFiscal = codModDocFiscal;
	}
	public String getModeloEquip() {
		return modeloEquip;
	}
	public void setModeloEquip(String modeloEquip) {
		this.modeloEquip = modeloEquip;
	}
	public String getNumSerieFabECF() {
		return numSerieFabECF;
	}
	public void setNumSerieFabECF(String numSerieFabECF) {
		this.numSerieFabECF = numSerieFabECF;
	}
	public String getNumECF() {
		return numECF;
	}
	public void setNumECF(String numECF) {
		this.numECF = numECF;
	}

}
