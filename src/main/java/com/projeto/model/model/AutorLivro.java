package com.projeto.model.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TAB_AUTORES_LIVROS")
public class AutorLivro implements Serializable{
	

	private static final long serialVersionUID = 8679681626876930860L;
	
	private AutorLivroPK id;
	private Date dataCadastro;


	private Autor autor;
	private Livro livro;
	
	
	public AutorLivro() {
	}

	public AutorLivro(AutorLivroPK id) {
		this.id = id;
	}

	@EmbeddedId
	public AutorLivroPK getId() {
		return id;
	}

	public void setId(AutorLivroPK id) {
		this.id = id;
	}
	
	@Temporal(TemporalType.DATE)
    @Column(name = "DATA_CADASTRO", nullable = false)
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	
    @ManyToOne
    @JoinColumn(name="AUTOR_ID", nullable = false, insertable = false, updatable = false)
	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	@ManyToOne
    @JoinColumn(name="LIVRO_ID", nullable = false, insertable = false, updatable = false)
	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
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
		AutorLivro other = (AutorLivro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
