package com.zeta.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="tb_invdeclarado")
public class InventarioDeclarado implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -7656527313989886889L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long      id;
	@Column(name = "lote_id")
	private Long      idPai;
	private Long      idEmp;
	private Long      idEst;
	private LocalDate dataInv;
	private Double    vlTotal;
	private String    motivoInventario;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="idPai")
	private List<ItensInventario> itensInv = new ArrayList<ItensInventario>();
	
	
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
	public LocalDate getDataInv() {
		return dataInv;
	}
	public void setDataInv(LocalDate dataInv) {
		this.dataInv = dataInv;
	}
	public Double getVlTotal() {
		return vlTotal;
	}
	public void setVlTotal(Double vlTotal) {
		this.vlTotal = vlTotal;
	}
	public String getMotivoInventario() {
		return motivoInventario;
	}
	public void setMotivoInventario(String motivoInventario) {
		this.motivoInventario = motivoInventario;
	}
	public List<ItensInventario> getItensInv() {
		return itensInv;
	}
	public void setItensInv(List<ItensInventario> itensInv) {
		this.itensInv = itensInv;
	}
	
	public void adicionaItemInv(ItensInventario itnDec) {
		this.itensInv.add(itnDec);
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
	
}
