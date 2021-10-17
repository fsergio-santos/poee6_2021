package com.projeto.model.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
	

	public List<Autor> findAll(){
		TypedQuery<Autor> query = this.getEntityManager().createQuery("SELECT a FROM Autor a ",Autor.class);
		List<Autor> listaAutor = query.getResultList();
		return listaAutor;
	}
	
	
	
	public List<Autor> listaAutorPorPaginacao(int paginaAtual, int registrosPorPagina){
		List<Autor> listaAutor = new ArrayList<Autor>();
		if (paginaAtual >= 0 ) {
			TypedQuery<Autor> query = this.getEntityManager().createQuery("SELECT a FROM Autor a",Autor.class)
											 .setFirstResult(paginaAtual)
											 .setMaxResults(registrosPorPagina);
			listaAutor = query.getResultList();
		}
		return listaAutor;
	}
	
	
	public Integer countTotalRegistrosAutores() {
		TypedQuery<Long> query = this.getEntityManager().createQuery("SELECT count(a) FROM Autor a",Long.class);
		Long total = (Long) query.getSingleResult();
		return total.intValue();
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public List<Autor> carregarListaAutor(String name) {
		List<Autor> listaAutor = new ArrayList<Autor>();
   	    TypedQuery<Autor> query = this.getEntityManager()
   	    		          .createQuery("SELECT a FROM Autor a WHERE a.nome LIKE :name", Autor.class)
   	    		          .setParameter("name", "%"+ name +"%" );
     	listaAutor = query.getResultList();
		return listaAutor;
	}


	public List<Autor> carregaListaAutor() {
		List<Autor> listaAutor = new ArrayList<Autor>();
		TypedQuery<Autor> query = this.getEntityManager().createQuery("SELECT a FROM Autor a",Autor.class);
		listaAutor = query.getResultList();
		return listaAutor;
	}

}
