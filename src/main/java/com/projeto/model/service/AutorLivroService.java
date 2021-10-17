package com.projeto.model.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.projeto.model.model.Autor;
import com.projeto.model.model.AutorLivro;
import com.projeto.model.model.AutorLivroPK;
import com.projeto.model.model.Livro;
import com.projeto.model.repository.AutorLivroDao;
import com.projeto.persistencia.ConexaoBancoDados;

public class AutorLivroService {

private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName=UNIT_NAME)
	private EntityManager entityManager;

	private AutorLivroDao autorLivroDao;
	
	public AutorLivroService() {
	  this.entityManager = null;
	  abrirBancoDados();
	}

	
	private void abrirBancoDados() {
		if (Objects.isNull(this.entityManager)) {
			setEntityManager(ConexaoBancoDados.getConexao().getEntityManager());
		}
		
		if (!this.getEntityManager().isOpen()) {
			setEntityManager(ConexaoBancoDados.getConexao().getEntityManager());
		}
		autorLivroDao = new AutorLivroDao(this.entityManager);
		
	}
	
	private void fecharBancoDados() {
		if (this.getEntityManager().isOpen()) {
			this.getEntityManager().close();
		}
	}
	
	

	public void save(AutorLivro autor) {
		abrirBancoDados();
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try {
			trx.begin();
			autorLivroDao.save(autor);
			trx.commit();
		}catch(Throwable t) {
			t.printStackTrace();
			if (trx.isActive()) {
				trx.rollback();
			}
		} finally {
			fecharBancoDados();
		}
	}

	public void update(AutorLivro autor) {
		abrirBancoDados();
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try {
			trx.begin();
			autorLivroDao.update(autor);
			trx.commit();
		}catch(Throwable t) {
			t.printStackTrace();
			if (trx.isActive()) {
				trx.rollback();
			}
		} finally {
			fecharBancoDados();
		}
	}
	
	public void delete(AutorLivro autor) {
		abrirBancoDados();
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try {
			trx.begin();
			autorLivroDao.delete(autor);
			trx.commit();
		}catch(Throwable t) {
			t.printStackTrace();
			if (trx.isActive()) {
				trx.rollback();
			}
		} finally {
			fecharBancoDados();
		}
	}
	
	
	public AutorLivro findAutorLivroById(AutorLivroPK id) {
		abrirBancoDados();
		AutorLivro autor = autorLivroDao.findById(id);
		fecharBancoDados();
		return autor;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public Integer countTotalRegistroAutorLivros() {
		return autorLivroDao.countTotalRegistrosAutorLivroes();
	}


	public List<AutorLivro> carregarListaAutorLivro(Integer paginaAtual, Integer registrosPorPagina) {
		return autorLivroDao.listaAutorLivroPorPaginacao(paginaAtual, registrosPorPagina);
	}


	public List<AutorLivro> carregarListaLivro(String buscarNomeLivro) {
		return autorLivroDao.carregarListaLivro(buscarNomeLivro);
	}


	public List<Livro> carregaListaLivroPorAutor(Integer id) {
		return autorLivroDao.carregaListaLivroPorAutor(id);
	}


	public List<Autor> carregaListaAutorPorLivro(Integer id) {
		return autorLivroDao.carregaListaAutorPorLivro(id);
	}

}
