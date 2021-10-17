package com.projeto.model.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.projeto.model.model.Editora;
import com.projeto.model.repository.EditoraDao;
import com.projeto.persistencia.ConexaoBancoDados;

public class EditoraService {

private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName=UNIT_NAME)
	private EntityManager entityManager;

	private EditoraDao editoraDao;
	
	public EditoraService() {
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
		editoraDao = new EditoraDao(this.entityManager);
		
	}
	
	private void fecharBancoDados() {
		if (this.getEntityManager().isOpen()) {
			this.getEntityManager().close();
		}
	}
	
	

	public void save(Editora autor) {
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try {
			trx.begin();
			editoraDao.save(autor);
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

	public void update(Editora autor) {
	    EntityTransaction trx = this.getEntityManager().getTransaction();
		try {
			trx.begin();
			editoraDao.update(autor);
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
	
	public void delete(Editora autor) {
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try {
			trx.begin();
			editoraDao.delete(autor);
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
	
	
	public Editora findEditoraById(Integer id) {
		Editora autor = editoraDao.findById(id);
		fecharBancoDados();
		return autor;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public Integer countTotalRegistroEditoraes() {
		return editoraDao.countTotalRegistrosEditoraes();
	}


	public List<Editora> carregarListaEditora(Integer paginaAtual, Integer registrosPorPagina) {
		return editoraDao.listaEditoraPorPaginacao(paginaAtual, registrosPorPagina);
	}

	public List<Editora> carregaListaEditora() {
		return editoraDao.carregarListaEditora();
	}

	public List<Editora> carregarListaEditora(String buscarNomeAutor) {
		return editoraDao.carregarListaEditora(buscarNomeAutor);
	}


	

}
