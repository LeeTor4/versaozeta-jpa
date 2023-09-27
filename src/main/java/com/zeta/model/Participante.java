package com.zeta.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity 
@Table(name="tb_participante")
public class Participante implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2425326786787893773L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   Id;
	@JoinColumn(name = "est_id")
	private Long   IdPai;
	private String codPart;
	private String nome;
	private String codPais;
	private String cnpj;
	private String cpf;
	private String ie;
	private String codMun;
	private String suframa;
	private String endereco;
	private String num;
	private String compl;
	private String bairro;
	private String ativPrincipal;
	private String  naturezaJuridica;
	
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public Long getIdPai() {
		return IdPai;
	}
	public void setIdPai(Long idPai) {
		IdPai = idPai;
	}
	public String getCodPart() {
		return codPart;
	}
	public void setCodPart(String codPart) {
		this.codPart = codPart;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCodPais() {
		return codPais;
	}
	public void setCodPais(String codPais) {
		this.codPais = codPais;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getIe() {
		return ie;
	}
	public void setIe(String ie) {
		this.ie = ie;
	}
	public String getCodMun() {
		return codMun;
	}
	public void setCodMun(String codMun) {
		this.codMun = codMun;
	}
	public String getSuframa() {
		return suframa;
	}
	public void setSuframa(String suframa) {
		this.suframa = suframa;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getCompl() {
		return compl;
	}
	public void setCompl(String compl) {
		this.compl = compl;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getAtivPrincipal() {
		return ativPrincipal;
	}
	public void setAtivPrincipal(String ativPrincipal) {
		this.ativPrincipal = ativPrincipal;
	}
	public String getNaturezaJuridica() {
		return naturezaJuridica;
	}
	public void setNaturezaJuridica(String naturezaJuridica) {
		this.naturezaJuridica = naturezaJuridica;
	}
	@Override
	public int hashCode() {
		return Objects.hash(IdPai, codPart);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participante other = (Participante) obj;
		return Objects.equals(IdPai, other.IdPai) && Objects.equals(codPart, other.codPart);
	}
	
	
}
