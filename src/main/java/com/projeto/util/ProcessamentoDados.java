package com.projeto.util;

import java.util.Date;
import java.util.Objects;

import javax.swing.JOptionPane;

public class ProcessamentoDados {
	
	
	public static final boolean FALSO = false;
	public static final boolean VERDADEIRO = true;
	public static final String IMAGENS = "/imagens/";
	
	public static final Integer INCLUIR_REGISTRO      = 0;
	public static final Integer ALTERAR_REGISTRO      = 1;
	public static final Integer EXCLUIR_REGISTRO      = 2;
	public static final Integer CONSULTAR_REGISTRO    = 3;
	
	public static final String BUTTON_ICON_INCLUIR     = IMAGENS+"book_add.png";
	public static final String BUTTON_ICON_ALTERAR     = IMAGENS+"book_edit.png";
	public static final String BUTTON_ICON_EXCLUIR     = IMAGENS+"book_delete.png";
	public static final String BUTTON_ICON_CONSULTAR   = IMAGENS+"book_open.png";
	public static final String BUTTON_ICON_FECHAR      = IMAGENS+"saida.png";	
	public static final String BUTTON_ICON_SEARCH      = IMAGENS+"search.png";
	public static final String BUTTON_ICON_OK          = IMAGENS+"ok.png"; 
	
	public static final String BUTTON_ICON_PRIMEIRO    = IMAGENS+"go-first.png";
	public static final String BUTTON_ICON_ANTERIOR    = IMAGENS+"go-previous.png";
	public static final String BUTTON_ICON_PROXIMO     = IMAGENS+"go-next.png";
	public static final String BUTTON_ICON_ULTIMO      = IMAGENS+"go-last.png";
	
	public static final String LBL_ICON_SHOW_ERROR     = IMAGENS+"iconFechar.png";
	
	public static final String SHOW_ICON_MENU_BAR_FILE     = IMAGENS+"fila.png";
	public static final String SHOW_ICON_SYSTEM            = IMAGENS+"system.png";
	public static final String SHOW_ICON_MENU_BAR_REL      = IMAGENS+"pdf.png";
	public static final String SHOW_ICON_MENU_AUTOR        = IMAGENS+"author.png";
	public static final String SHOW_ICON_MENU_EDITORA      = IMAGENS+"bank.png";
	public static final String SHOW_ICON_MENU_LIVRO        = IMAGENS+"book1.png";
	public static final String SHOW_ICON_MENU_SAIR         = IMAGENS+"sair.png";
	public static final String SHOW_ICON_MENU_WRITING      = IMAGENS+"writing.png";
	public static final String SHOW_ICON_MENU_SEARCH_BOOK  = IMAGENS+"search_book.png";
	public static final String SHOW_ICON_MENU_SEARCH_AUTOR = IMAGENS+"search_autor.png";
 	
	public static boolean digitacaoCampo(String texto) {
		if (Objects.isNull(texto)) {
			return VERDADEIRO;
		}
		if ("".equals(texto.trim())) {
			return VERDADEIRO;
		}
		return FALSO;
	}
	
	public static boolean digitacaoCampoFormatado(String texto) {
		int value=0;
	    for (int i = 0; i < texto.length(); i++ ) {
	    	 if (texto.charAt(i)=='_') {
	    		 value++;
	    	 }
	    }
	    return value == 0 ? FALSO : VERDADEIRO;
	}
	
	public static boolean digitacaoCampoData(Date date) {
		return Objects.isNull(date) ? VERDADEIRO : FALSO;
	}
	
	
	public static Integer converterParaInteiro(String texto) {
		return Integer.parseInt(texto);
	}
	
	
	public static String converterInteiroParaString(Integer texto) {
		return texto.toString();
	}
	
	public static void showMensagem(String mensagem, String status, int option) {
		JOptionPane.showMessageDialog(null,  mensagem, status, option);
	}
	
	

}
