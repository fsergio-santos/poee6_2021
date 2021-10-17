package com.projeto.model.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.projeto.model.model.Livro;

public class LivroDao {

	private EntityManager entityManager;
	
	
	public LivroDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	public void save(Livro livro) {
		this.getEntityManager().persist(livro);
	}
	
	
	public void update(Livro livro) {
		this.getEntityManager().merge(livro); 
	}
	
	
	public void delete(Livro livro) {
		this.getEntityManager().remove(livro);
	}
	
	
	public Livro findById(Integer id) {
		return this.getEntityManager().find(Livro.class, id);
	}
	
	public List<Livro> findAll(){
		TypedQuery<Livro> query = this.getEntityManager().createQuery("SELECT l FROM Livro l ", Livro.class);
		List<Livro> listaLivro = query.getResultList();
		return listaLivro;
	}
	
	
	public List<Livro> listaLivroPorPaginacao(int paginaAtual, int registrosPorPagina){
		List<Livro> listaLivro = new ArrayList<Livro>();
		if (paginaAtual >= 0 ) {
			TypedQuery<Livro> query = this.getEntityManager().createQuery("SELECT l FROM Livro l", Livro.class)
											 .setFirstResult(paginaAtual)
											 .setMaxResults(registrosPorPagina);
			listaLivro = query.getResultList();
		}
		return listaLivro;
	}
	
	
	public Integer countTotalRegistrosLivroes() {
		Query query = this.getEntityManager().createQuery("SELECT count(l) FROM Livro l");
		Long total = (Long) query.getSingleResult();
		return total.intValue();
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public List<Livro> carregarListaLivro(String name) {
		List<Livro> listaLivro = new ArrayList<Livro>();
   	    TypedQuery<Livro> query = this.getEntityManager()
   	    		          .createQuery("SELECT l FROM Livro l WHERE l.nome LIKE :name", Livro.class)
   	    		          .setParameter("name", "%"+ name +"%" );
     	listaLivro = query.getResultList();
		return listaLivro;
	}


	public List<Livro> carregaListaLivro() {
		List<Livro> listaLivro = new ArrayList<Livro>();
		TypedQuery<Livro> query = this.getEntityManager().createQuery("SELECT l FROM Livro l", Livro.class);
		listaLivro = query.getResultList();
		return listaLivro;
	}

}
