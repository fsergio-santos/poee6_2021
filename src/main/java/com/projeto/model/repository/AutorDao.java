package com.projeto.model.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.projeto.model.model.Autor;

public class AutorDao {
	
	
	private EntityManager entityManager;
	
	
	public AutorDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	public void save(Autor autor) {
		this.getEntityManager().persist(autor);
	}
	
	
	public void update(Autor autor) {
		this.getEntityManager().merge(autor);
	}
	
	
	public void delete(Autor autor) {
		this.getEntityManager().remove(autor);
	}
	
	
	public Autor findById(Integer id) {
		return this.getEntityManager().find(Autor.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Autor> findAll(){
		Query query = this.getEntityManager().createQuery("SELECT a FROM Autor a ");
		List<Autor> listaAutor = query.getResultList();
		return listaAutor;
	}
	

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
