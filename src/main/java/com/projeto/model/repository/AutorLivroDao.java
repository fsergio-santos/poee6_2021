package com.projeto.model.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.projeto.model.model.Autor;
import com.projeto.model.model.AutorLivro;
import com.projeto.model.model.AutorLivroPK;
import com.projeto.model.model.Livro;

public class AutorLivroDao {
	
	private EntityManager entityManager;
	
	
	public AutorLivroDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	public void save(AutorLivro autorLivro) {
		this.getEntityManager().persist(autorLivro);
	}
	
	
	public void update(AutorLivro autorLivro) {
		this.getEntityManager().merge(autorLivro); 
	}
	
	
	public void delete(AutorLivro autorLivro) {
		this.getEntityManager().remove(autorLivro);
	}
	
	
	public AutorLivro findById(AutorLivroPK id) {
		return this.getEntityManager().find(AutorLivro.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<AutorLivro> findAll(){
		Query query = this.getEntityManager().createQuery("SELECT a FROM AutorLivro a ");
		List<AutorLivro> listaAutorLivro = query.getResultList();
		return listaAutorLivro;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<AutorLivro> listaAutorLivroPorPaginacao(int paginaAtual, int registrosPorPagina){
		List<AutorLivro> listaAutorLivro = new ArrayList<AutorLivro>();
		if (paginaAtual >= 0 ) {
			Query query = this.getEntityManager().createQuery("SELECT a FROM AutorLivro a")
											 .setFirstResult(paginaAtual)
											 .setMaxResults(registrosPorPagina);
			listaAutorLivro = query.getResultList();
		}
		return listaAutorLivro;
	}
	
	
	public Integer countTotalRegistrosAutorLivroes() {
		Query query = this.getEntityManager().createQuery("SELECT count(a) FROM AutorLivro a");
		Long total = (Long) query.getSingleResult();
		return total.intValue();
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public List<AutorLivro> carregarListaLivro(String name) {
		List<AutorLivro> listaAutorLivro = new ArrayList<AutorLivro>();
   	    TypedQuery<AutorLivro> query = this.getEntityManager()
   	    		          .createQuery("SELECT a FROM AutorLivro a WHERE a.nome LIKE :name", AutorLivro.class)
   	    		          .setParameter(name, "%"+ name +"%" );
     	listaAutorLivro = query.getResultList();
		return listaAutorLivro;
	}


	public List<Livro> carregaListaLivroPorAutor(Integer id) {
		List<Livro> listaLivro = new ArrayList<Livro>();
   	    TypedQuery<Livro> query = this.getEntityManager()
   	    		          .createQuery("SELECT l FROM Livro l "
   	    		          		+ " JOIN "
   	    		          		+ "   l.listaAutoresLivros al "
   	    		          		+ " JOIN "
   	    		          		+ "   al.autor a  "
   	    		          		+ " WHERE "
   	    		          		+ "    l.id = al.id.idLivro "
   	    		          		+ " AND "
   	    		          		+ "    a.id =:id", Livro.class)
   	    		          .setParameter("id",id);
     	listaLivro = query.getResultList();
		return listaLivro;
	}


	public List<Autor> carregaListaAutorPorLivro(Integer id) {
		List<Autor> listaAutor = new ArrayList<Autor>();
   	    TypedQuery<Autor> query = this.getEntityManager()
   	    		          .createQuery("SELECT a FROM Autor a "
   	    		          		+ " JOIN "
   	    		          		+ "   a.listaAutoresLivros al "
   	    		          		+ " JOIN "
   	    		          		+ "   al.livro l  "
   	    		          		+ " WHERE "
   	    		          		+ "    a.id = al.id.idAutor "
   	    		          		+ " AND "
   	    		          		+ "    l.id =:id", Autor.class)
   	    		          .setParameter("id",id);
     	listaAutor = query.getResultList();
		return listaAutor;
	}

}
