package com.projeto.model.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AutorLivroPK implements Serializable{

	private static final long serialVersionUID = -7561391881319835868L;
	
	private Integer IdAutor;
	private Integer IdLivro;
	
	
	public AutorLivroPK() {
	}

	public AutorLivroPK(Integer idAutor, Integer idLivro) {
		super();
		IdAutor = idAutor;
		IdLivro = idLivro;
	}
	
	@Column(name="AUTOR_ID", insertable = false, updatable = false, nullable = false)
	public Integer getIdAutor() {
		return IdAutor;
	}
	
	public void setIdAutor(Integer idAutor) {
		IdAutor = idAutor;
	}
	
	@Column(name="LIVRO_ID", insertable = false, updatable = false, nullable = false)
	public Integer getIdLivro() {
		return IdLivro;
	}
	
	public void setIdLivro(Integer idLivro) {
		IdLivro = idLivro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((IdAutor == null) ? 0 : IdAutor.hashCode());
		result = prime * result + ((IdLivro == null) ? 0 : IdLivro.hashCode());
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
		AutorLivroPK other = (AutorLivroPK) obj;
		if (IdAutor == null) {
			if (other.IdAutor != null)
				return false;
		} else if (!IdAutor.equals(other.IdAutor))
			return false;
		if (IdLivro == null) {
			if (other.IdLivro != null)
				return false;
		} else if (!IdLivro.equals(other.IdLivro))
			return false;
		return true;
	}
	
	
	
	

}
