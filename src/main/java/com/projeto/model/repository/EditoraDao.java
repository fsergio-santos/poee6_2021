package com.projeto.model.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.projeto.model.model.Editora;

public class EditoraDao {
	
	private EntityManager entityManager;
	
	
	public EditoraDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	public void save(Editora editora) {
		this.getEntityManager().persist(editora);
	}
	
	
	public void update(Editora editora) {
		this.getEntityManager().merge(editora); 
	}
	
	
	public void delete(Editora editora) {
		this.getEntityManager().remove(editora);
	}
	
	
	public Editora findById(Integer id) {
		return this.getEntityManager().find(Editora.class, id);
	}
	
	public List<Editora> findAll(){
		TypedQuery<Editora> query = this.getEntityManager()
				                        .createQuery("SELECT a FROM Editora a ", Editora.class);
		List<Editora> listaEditora = query.getResultList();
		return listaEditora;
	}
	
	

	public List<Editora> listaEditoraPorPaginacao(int paginaAtual, int registrosPorPagina){
		List<Editora> listaEditora = new ArrayList<Editora>();
		if (paginaAtual >= 0 ) {
			TypedQuery<Editora> query = this.getEntityManager()
					                        .createQuery("SELECT a FROM Editora a",Editora.class)
											.setFirstResult(paginaAtual)
											.setMaxResults(registrosPorPagina);
			listaEditora = query.getResultList();
		}
		return listaEditora;
	}
	
	
	public List<Editora> listaEditoraFromKeyPressed(String name){
		List<Editora> listaEditora = new ArrayList<Editora>();
   	    TypedQuery<Editora> query = this.getEntityManager()
   	    		          .createQuery("SELECT a FROM Editora a WHERE a.nome LIKE :name", Editora.class)
   	    		          .setParameter(name, "%"+ name +"%" );
     	listaEditora = query.getResultList();
		return listaEditora;
	}
	
	
	public Integer countTotalRegistrosEditoraes() {
		Query query = this.getEntityManager().createQuery("SELECT count(a) FROM Editora a");
		Long total = (Long) query.getSingleResult();
		return total.intValue();
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public List<Editora> carregarListaEditora() {
        List<Editora> listaEditora = new ArrayList<>();	
        TypedQuery<Editora> query = this.getEntityManager().createQuery("SELECT e FROM Editora e", Editora.class);
        listaEditora = query.getResultList();
    	return listaEditora;
	}


	public List<Editora> carregarListaEditora(String name) {
		List<Editora> listaEditora = new ArrayList<>();	
	    TypedQuery<Editora> query = this.getEntityManager().createQuery("SELECT e FROM Editora e WHERE e.nome LIKE :name", Editora.class)
	    								.setParameter("name", "%"+ name +"%" );
	    listaEditora = query.getResultList();
	    return listaEditora;
	}

}
