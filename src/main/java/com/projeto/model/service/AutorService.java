package com.projeto.model.service;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.projeto.model.model.Autor;
import com.projeto.model.repository.AutorDao;
import com.projeto.persistencia.ConexaoBancoDados;

public class AutorService {
	
	private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName=UNIT_NAME)
	private EntityManager entityManager;

	private AutorDao autorDao;
	
	public AutorService() {
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
		autorDao = new AutorDao(this.entityManager);
		
	}
	
	private void fecharBancoDados() {
		if (this.getEntityManager().isOpen()) {
			this.getEntityManager().close();
		}
	}
	
	

	public void save(Autor autor) {
		abrirBancoDados();
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try {
			trx.begin();
			autorDao.save(autor);
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

	public void update(Autor autor) {
		abrirBancoDados();
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try {
			trx.begin();
			autorDao.update(autor);
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
	
	public void delete(Autor autor) {
		abrirBancoDados();
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try {
			trx.begin();
			autorDao.delete(autor);
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
	
	
	public Autor findAutorById(Integer id) {
		abrirBancoDados();
		Autor autor = autorDao.findById(id);
		fecharBancoDados();
		return autor;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
