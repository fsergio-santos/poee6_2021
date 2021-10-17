package com.projeto.view.autor;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.projeto.model.model.Autor;

public class TabelaAutorModel extends AbstractTableModel {

	private static final long serialVersionUID = -1931542755651631378L;
	
	
	private final String colunas[] = {"Código",	"Nome", "Rua", "Bairro", "Cidade", "Cep", 
			                          "Data Nascimento", "CPF", "RG", "Sexo", "Telefone Fixo", "Celular"	};
	
	private final Integer tamanhoCampo[] = {
			50,
			150,
			150,
			150,
			150,
			80,
			130,
			100,
			80,
			50,
			100,
			100
	};
	
	private static final int CODIGO     = 0;
	private static final int NOME       = 1;
	private static final int RUA        = 2;
	private static final int BAIRRO     = 3;
	private static final int CIDADE     = 4;
	private static final int CEP        = 5;
	private static final int NASCIMENTO = 6;
	private static final int CPF        = 7;
	private static final int RG         = 8;
	private static final int SEXO       = 9;
	private static final int FIXO       = 10;
	private static final int CELULAR    = 11;
	
	
	private List<Autor> listaAutor;
	
	
	public TabelaAutorModel() {
       listaAutor = new ArrayList<Autor>();
	}

	
	public List<Autor> getListaAutor() {
		return listaAutor;
	}


	public void setListaAutor(List<Autor> listaAutor) {
		this.listaAutor = listaAutor;
	}


    public Autor getAutor(int index) {
    	return getListaAutor().get(index);
    }
    
    
    public void saveAutor(Autor autor) {
    	getListaAutor().add(autor);
    	fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
    }
    
    
    public void updateAutor(Autor autor, int index) {
    	getListaAutor().set(index, autor);
    	fireTableRowsUpdated(index, index);
    }
    
    
    public void removeAutor(int index) {
    	getListaAutor().remove(index);
    	fireTableRowsDeleted(index, index);
    }
    
    
    public void removeAll() {
    	getListaAutor().clear();
    	fireTableDataChanged();
    }


    public String getColumnName(int index) {
    	return getColunas()[index];
    }


	@Override
	public int getRowCount() {
		return getListaAutor().size();
	}

	@Override
	public int getColumnCount() {
		return getColunas().length;
	}
		
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Autor autor = getListaAutor().get(rowIndex);
		switch(columnIndex) {
			case CODIGO:
				return autor.getId();
			case NOME:
				return autor.getNome();
			case RUA:
				return autor.getRua();
			case BAIRRO:
				return autor.getBairro();
			case CIDADE:
				return autor.getCidade();
			case CEP:
				return autor.getCep();
			case NASCIMENTO:
				return autor.getDataNascimento();
			case CPF:
				return autor.getCpf();
			case RG:
				return autor.getRg();
			case SEXO:
				return autor.getSexo();
			case FIXO:
				return autor.getTelefoneFixo();
			case CELULAR:
				return autor.getTelefoneCelular();
			default:
				return autor;
		}

	}

	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
			case CODIGO:
				return Integer.class;
			case NOME:
				return String.class;
			case RUA:
				return String.class;
			case BAIRRO:
				return String.class;
			case CIDADE:
				return String.class;
			case CEP:
				return String.class;
			case NASCIMENTO:
				return Date.class;
			case CPF:
				return String.class;
			case RG:
				return String.class;
			case SEXO:
				return String.class;
			case FIXO:
				return String.class;
			case CELULAR:
				return String.class;
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
