package com.projeto.model.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.projeto.model.model.Livro;
import com.projeto.model.repository.LivroDao;
import com.projeto.persistencia.ConexaoBancoDados;

public class LivroService {

private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName=UNIT_NAME)
	private EntityManager entityManager;

	private LivroDao livroDao;
	
	public LivroService() {
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
		livroDao = new LivroDao(this.entityManager);
		
	}
	
	private void fecharBancoDados() {
		if (this.getEntityManager().isOpen()) {
			this.getEntityManager().close();
		}
	}
	
	

	public void save(Livro autor) {
		abrirBancoDados();
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try {
			trx.begin();
			livroDao.save(autor);
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

	public void update(Livro autor) {
		abrirBancoDados();
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try {
			trx.begin();
			livroDao.update(autor);
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
	
	public void delete(Livro autor) {
		abrirBancoDados();
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try {
			trx.begin();
			livroDao.delete(autor);
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
	
	
	public Livro findLivroById(Integer id) {
		abrirBancoDados();
		Livro autor = livroDao.findById(id);
		fecharBancoDados();
		return autor;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public Integer countTotalRegistroLivroes() {
		return livroDao.countTotalRegistrosLivroes();
	}


	public List<Livro> carregarListaLivro(Integer paginaAtual, Integer registrosPorPagina) {
		return livroDao.listaLivroPorPaginacao(paginaAtual, registrosPorPagina);
	}


	public List<Livro> carregarListaLivro(String buscarNomeLivro) {
		return livroDao.carregarListaLivro(buscarNomeLivro);
	}


	public List<Livro> carregaListaLivro() {
		return livroDao.carregaListaLivro();
	}


}
