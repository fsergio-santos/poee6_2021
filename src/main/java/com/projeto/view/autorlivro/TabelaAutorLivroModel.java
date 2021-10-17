package com.projeto.view.autorlivro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.projeto.model.model.AutorLivro;

public class TabelaAutorLivroModel extends AbstractTableModel {

	private static final long serialVersionUID = -1931542755651631378L;
	
	
	private final String colunas[] = {
			"Autor",
			"Livro",
			"Data Cadastro"
	};
	
	private final Integer tamanhoCampo[] = {100, 100, 100 };
	
	private static final int AUTOR      = 0;
	private static final int LIVRO      = 1;
	private static final int CADASTRO   = 2;
	
	
	private List<AutorLivro> listaAutorLivro;
	
	
	public TabelaAutorLivroModel() {
       listaAutorLivro = new ArrayList<AutorLivro>();
	}

	
	public List<AutorLivro> getListaAutorLivro() {
		return listaAutorLivro;
	}


	public void setListaAutorLivro(List<AutorLivro> listaAutorLivro) {
		this.listaAutorLivro = listaAutorLivro;
	}


    public AutorLivro getAutorLivro(int index) {
    	return getListaAutorLivro().get(index);
    }
    
    
    public void saveAutorLivro(AutorLivro autorLivro) {
    	getListaAutorLivro().add(autorLivro);
    	fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
    }
    
    
    public void updateAutorLivro(AutorLivro autorLivro, int index) {
    	getListaAutorLivro().set(index, autorLivro);
    	fireTableRowsUpdated(index, index);
    }
    
    
    public void removeAutorLivro(int index) {
    	getListaAutorLivro().remove(index);
    	fireTableRowsDeleted(index, index);
    }
    
    
    public void removeAll() {
    	getListaAutorLivro().clear();
    	fireTableDataChanged();
    }


    public String getColumnName(int index) {
    	return getColunas()[index];
    }


	@Override
	public int getRowCount() {
		return getListaAutorLivro().size();
	}

	@Override
	public int getColumnCount() {
		return getColunas().length;
	}
		
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		AutorLivro autorLivro = getListaAutorLivro().get(rowIndex);
		switch(columnIndex) {
			case AUTOR:
				return autorLivro.getAutor().getNome();
			case LIVRO:
				return autorLivro.getLivro().getNome();
			case CADASTRO:
				return autorLivro.getDataCadastro();
	
			default:
				return autorLivro;
		}

	}

	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
			case AUTOR:
				return String.class;
			case LIVRO:
				return String.class;
			case CADASTRO:
				return Date.class;
			default:
				return null;
		}
	}
	

	public String[] getColunas() {
		return colunas;
	}


	public Integer[] getTamanhoCampo() {
		return tamanhoCampo;
	}
	
	

}
