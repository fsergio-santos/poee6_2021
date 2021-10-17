package com.projeto.view.editora;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.projeto.model.model.Editora;

public class TabelaEditoraModel extends AbstractTableModel {

	private static final long serialVersionUID = -1931542755651631378L;
	
	
	private final String colunas[] = {
			"Código",
			"Nome",
			"Rua",
			"Bairro",
			"Cidade",
			"Cep"
	};
	
	private final Integer tamanhoCampo[] = {11, 100, 100, 50, 50, 20};
	
	private final Integer campoTabela = 6;
	
	public static final int CODIGO     = 0;
	public static final int NOME       = 1;
	public static final int RUA        = 2;
	public static final int BAIRRO     = 3;
	public static final int CIDADE     = 4;
	public static final int CEP        = 5;
	
	private List<Editora> listaEditora;
	
	
	public TabelaEditoraModel() {
       listaEditora = new ArrayList<Editora>();
	}

	
	public List<Editora> getListaEditora() {
		return listaEditora;
	}


	public void setListaEditora(List<Editora> listaEditora) {
		this.listaEditora = listaEditora;
	}


    public Editora getEditora(int index) {
    	return getListaEditora().get(index);
    }
    
    
    public void saveEditora(Editora editora) {
    	getListaEditora().add(editora);
    	fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
    }
    
    
    public void updateEditora(Editora editora, int index) {
    	getListaEditora().set(index, editora);
    	fireTableRowsUpdated(index, index);
    }
    
    
    public void removeEditora(int index) {
    	getListaEditora().remove(index);
    	fireTableRowsDeleted(index, index);
    }
    
    
    public void removeAll() {
    	getListaEditora().clear();
    	fireTableDataChanged();
    }


    public String getColumnName(int index) {
    	return getColunas()[index];
    }


	@Override
	public int getRowCount() {
		return getListaEditora().size();
	}

	@Override
	public int getColumnCount() {
		return getColunas().length;
	}
		
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Editora editora = getListaEditora().get(rowIndex);
		switch(columnIndex) {
			case CODIGO:
				return editora.getId();
			case NOME:
				return editora.getNome();
			case RUA:
				return editora.getRua();
			case BAIRRO:
				return editora.getBairro();
			case CIDADE:
				return editora.getCidade();
			case CEP:
				return editora.getCep();
			default:
				return editora;
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


	public Integer getCampoTabela() {
		return campoTabela;
	}
	
	

}
