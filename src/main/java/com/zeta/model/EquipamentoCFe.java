package com.zeta.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_equipamentocfe")
public class EquipamentoCFe implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8017060055716719792L;
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long      id;
	@Column(name="lote_id")
	private Long      idPai;
	private String    codModDocFiscal;
	private String    numSerieEquipSat;
	private LocalDate dtEmissao;
	private String    docInicial;
	private String    docFinal;
	
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
	public String getCodModDocFiscal() {
		return codModDocFiscal;
	}
	public void setCodModDocFiscal(String codModDocFiscal) {
		this.codModDocFiscal = codModDocFiscal;
	}
	public String getNumSerieEquipSat() {
		return numSerieEquipSat;
	}
	public void setNumSerieEquipSat(String numSerieEquipSat) {
		this.numSerieEquipSat = numSerieEquipSat;
	}
	public LocalDate getDtEmissao() {
		return dtEmissao;
	}
	public void setDtEmissao(LocalDate dtEmissao) {
		this.dtEmissao = dtEmissao;
	}
	public String getDocInicial() {
		return docInicial;
	}
	public void setDocInicial(String docInicial) {
		this.docInicial = docInicial;
	}
	public String getDocFinal() {
		return docFinal;
	}
	public void setDocFinal(String docFinal) {
		this.docFinal = docFinal;
	}
}
