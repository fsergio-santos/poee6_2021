package com.projeto.util;

import java.util.Objects;

import javax.swing.JOptionPane;

public class ProcessamentoDados {
	
	
	public static final boolean FALSO = false;
	public static final boolean VERDADEIRO = true;
	
	
	public static boolean digitacaoCampo(String texto) {
		if (Objects.isNull(texto)) {
			return VERDADEIRO;
		}
		if ("".equals(texto.trim())) {
			return VERDADEIRO;
		}
		return FALSO;
	}
	
	
	public static Integer converterParaInteiro(String texto) {
		return Integer.parseInt(texto);
	}
	
	
	public static void showMensagem(String mensagem, String status, int option) {
		JOptionPane.showMessageDialog(null,  mensagem, status, option);
	}
	
	

}
