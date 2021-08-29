package com.projeto.model.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "TAB_AUTOR")
public class Autor {
	

	private Integer id;
	private String  nome;
	private String  rua;
	private String  bairro;
	private String  cidade;
	private String  cep;
	//private Date    dataNascimento;
	private String  cpf;
	private String  rg;
	private String  sexo; 
	private String  telefoneFixo;
	private String  telefoneCelular;
	
	public Autor() {
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="AUTOR_ID")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="AUTOR_NOME", length = 80, nullable = false)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name="AUTOR_RUA", length = 80, nullable = false)
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	
	@Column(name="AUTOR_BAIRRO", length = 40, nullable = false)
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	@Column(name="AUTOR_CIDADE", length = 40, nullable = false)
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	@Column(name="AUTOR_CEP", length = 20, nullable = false)
	public String getCep() {
		return cep;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	//public Date getDataNascimento() {
	//	return dataNascimento;
	//}
	
	//public void setDataNascimento(Date dataNascimento) {
	//	this.dataNascimento = dataNascimento;
	//}
	
	@Column(name="AUTOR_CPF", length = 20, nullable = false)
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Column(name="AUTOR_RG", length = 20, nullable = false)
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	
	@Column(name="AUTOR_SEXO", length = 1, nullable = false)
	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	@Column(name="AUTOR_TELEFONE_FIXO", length = 20, nullable = false)
	public String getTelefoneFixo() {
		return telefoneFixo;
	}
	
	public void setTelefoneFixo(String telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	@Column(name="AUTOR_TELEFONE_CELULAR", length = 20, nullable = false)
	public String getTelefoneCelular() {
		return telefoneCelular;
	}
	
	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autor other = (Autor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Autor [id=" + id + ", nome=" + nome + ", rua=" + rua + ", bairro=" + bairro + ", cidade=" + cidade
				+ ", cep=" + cep + ", cpf=" + cpf + ", rg=" + rg + ", sexo=" + sexo + ", telefoneFixo=" + telefoneFixo
				+ ", telefoneCelular=" + telefoneCelular + "]";
	}


	
	
	

}
