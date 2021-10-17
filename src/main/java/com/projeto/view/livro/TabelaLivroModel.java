package com.projeto.view.livro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.projeto.model.model.Livro;

public class TabelaLivroModel extends AbstractTableModel {

	private static final long serialVersionUID = -1931542755651631378L;
	
	
	private final String colunas[] = {
			"Código",
			"Nome",
			"ISBN",
			"Data Edição",
			"Volume",
			"Edição",
			"Ano",
			"Quantidade",
	};
	
	private final Integer tamanhoCampo[] = {11, 100, 100, 50, 50, 20, 20, 11};
	
	public static final int CODIGO      = 0;
	public static final int NOME        = 1;
	public static final int ISBN        = 2;
	public static final int DATA_EDICAO = 3;
	public static final int VOLUME      = 4;
	public static final int EDICAO      = 5;
	public static final int ANO         = 6;
	public static final int TOTAL       = 7;
	
	private List<Livro> listaLivro;
	
	
	public TabelaLivroModel() {
       listaLivro = new ArrayList<Livro>();
	}

	
	public List<Livro> getListaLivro() {
		return listaLivro;
	}


	public void setListaLivro(List<Livro> listaLivro) {
		this.listaLivro = listaLivro;
	}


    public Livro getLivro(int index) {
    	return getListaLivro().get(index);
    }
    
    
    public void saveLivro(Livro livro) {
    	getListaLivro().add(livro);
    	fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
    }
    
    
    public void updateLivro(Livro livro, int index) {
    	getListaLivro().set(index, livro);
    	fireTableRowsUpdated(index, index);
    }
    
    
    public void removeLivro(int index) {
    	getListaLivro().remove(index);
    	fireTableRowsDeleted(index, index);
    }
    
    
    public void removeAll() {
    	getListaLivro().clear();
    	fireTableDataChanged();
    }


    public String getColumnName(int index) {
    	return getColunas()[index];
    }


	@Override
	public int getRowCount() {
		return getListaLivro().size();
	}

	@Override
	public int getColumnCount() {
		return getColunas().length;
	}
		
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Livro livro = getListaLivro().get(rowIndex);
		switch(columnIndex) {
			case CODIGO:
				return livro.getId();
			case NOME:
				return livro.getNome();
			case ISBN:
				return livro.getISBN();
			case DATA_EDICAO:
				return livro.getDataEdicao();
			case VOLUME:
				return livro.getVolume();
			case EDICAO:
				return livro.getEdicao();
			case ANO:
				return livro.getAno();
			case TOTAL:
				return livro.getQtdExemplares();
			default:
				return livro;
		}

	}

	
	
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
			case CODIGO:
				return Integer.class;
			case NOME:
				return String.class;
			case ISBN:
				return String.class;
			case DATA_EDICAO:
				return Date.class;
			case VOLUME:
				return Integer.class;
			case EDICAO:
				return String.class;
			case ANO:
				return Integer.class;
			case TOTAL:
				return Integer.class;
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
