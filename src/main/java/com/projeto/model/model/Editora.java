package com.projeto.model.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TAB_EDITORA")
public class Editora {
	
	private Integer id;
	private String nome;
	private String rua;
	private String bairro;
	private String cidade;
	private String cep;
	
	
	private List<Autor> listaAutores;
	
	
	public Editora() {
	}


	public Editora(Integer id, String nome, String rua, String bairro, String cidade, String cep) {
		this.id = id;
		this.nome = nome;
		this.rua = rua;
		this.bairro = bairro;
		this.cidade = cidade;
		this.cep = cep;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EDITORA_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "EDITORA_NOME",length = 50, nullable = false)
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "EDITORA_RUA",length = 50, nullable = false)
	public String getRua() {
		return rua;
	}
	
	public void setRua(String rua) {
		this.rua = rua;
	}
	
	@Column(name = "EDITORA_BAIRRO",length = 50, nullable = false)
	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	@Column(name = "EDITORA_CIDADE",length = 50, nullable = false)
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	@Column(name = "EDITORA_CEP",length = 20, nullable = false)
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	
	@OneToMany(mappedBy = "editora")
	public List<Autor> getListaAutores() {
		return listaAutores;
	}


	public void setListaAutores(List<Autor> listaAutores) {
		this.listaAutores = listaAutores;
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
		Editora other = (Editora) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Editora [id=" + id + ", nome=" + nome + ", rua=" + rua + ", bairro=" + bairro + ", cidade=" + cidade
				+ ", cep=" + cep + "]";
	}
	
	
	

}
