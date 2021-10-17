package com.projeto.model.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TAB_LIVRO")
public class Livro {

	private Integer id;
	private String nome;
	private String ISBN;
	private Date   dataEdicao;
	private Integer volume;
	private String edicao;
	private Integer ano;
	private Integer qtdExemplares;
	
	private List<AutorLivro> listaAutoresLivros;
	
	public Livro() {
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LIVRO_ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "LIVRO_NOME", length = 80, nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "LIVRO_ISBN", length = 80, nullable = false)
	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LIVRO_DATA_EDICAO", nullable = false)
	public Date getDataEdicao() {
		return dataEdicao;
	}

	public void setDataEdicao(Date dataEdicao) {
		this.dataEdicao = dataEdicao;
	}

	@Column(name = "LIVRO_VOLUME",  nullable = false)
	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	@Column(name = "LIVRO_EDICAO", nullable = false)
	public String getEdicao() {
		return edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}

	@Column(name = "LIVRO_ANO", nullable = false)
	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	@Column(name = "LIVRO_QTD_EXEMPLARES", nullable = false)
	public Integer getQtdExemplares() {
		return qtdExemplares;
	}
	
	public void setQtdExemplares(Integer qtdExemplares) {
		this.qtdExemplares = qtdExemplares;
	}



	@OneToMany(mappedBy = "livro")
	public List<AutorLivro> getListaAutoresLivros() {
		return listaAutoresLivros;
	}

	public void setListaAutoresLivros(List<AutorLivro> listaAutoresLivros) {
		this.listaAutoresLivros = listaAutoresLivros;
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
		Livro other = (Livro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "Livro [id=" + id + ", nome=" + nome + "]";
	}
	
	
	
}
