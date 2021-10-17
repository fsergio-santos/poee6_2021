package com.projeto.util;

import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

public class MaskFields {

	private MaskFormatter maskFormatter=null;
		
	public MaskFields() {
		maskFormatter = new MaskFormatter();
	}


	public MaskFormatter mascaraCPF(JFormattedTextField textFieldCpf ) {
		try {
			this.getMaskFormatter().setMask("###.###.###-##");
			this.getMaskFormatter().setOverwriteMode(ProcessamentoDados.VERDADEIRO);
			this.getMaskFormatter().setValidCharacters("0123456789");
			this.getMaskFormatter().setPlaceholderCharacter('_');
			this.getMaskFormatter().install(textFieldCpf);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return this.getMaskFormatter();
		
	}


	public MaskFormatter mascaraCEP(JFormattedTextField textFieldCep) {
		try {
			this.getMaskFormatter().setMask("##.###-###");
			this.getMaskFormatter().setOverwriteMode(ProcessamentoDados.VERDADEIRO);
			this.getMaskFormatter().setValidCharacters("0123456789");
			this.getMaskFormatter().setPlaceholderCharacter('_');
			this.getMaskFormatter().install(textFieldCep);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return this.getMaskFormatter();
	}


	public MaskFormatter mascaraTelefoneFixo(JFormattedTextField textFieldTelefoneFixo) {
		try {
			this.getMaskFormatter().setMask("(##)-####-####");
			this.getMaskFormatter().setOverwriteMode(ProcessamentoDados.VERDADEIRO);
			this.getMaskFormatter().setValidCharacters("0123456789");
			this.getMaskFormatter().setPlaceholderCharacter('_');
			this.getMaskFormatter().install(textFieldTelefoneFixo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return this.getMaskFormatter();
	}


	public MaskFormatter mascaraCelular(JFormattedTextField textFieldCelular) {
		try {
			this.getMaskFormatter().setMask("(##)-#####-####");
			this.getMaskFormatter().setOverwriteMode(ProcessamentoDados.VERDADEIRO);
			this.getMaskFormatter().setValidCharacters("0123456789");
			this.getMaskFormatter().setPlaceholderCharacter('_');
			this.getMaskFormatter().install(textFieldCelular);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return this.getMaskFormatter();
	}

	public MaskFormatter getMaskFormatter() {
		return maskFormatter;
	}


	public MaskFormatter mascaraRG(JFormattedTextField textFieldRG) {
		try {
			this.getMaskFormatter().setMask("##.###.###-#");
			this.getMaskFormatter().setOverwriteMode(ProcessamentoDados.VERDADEIRO);
			this.getMaskFormatter().setValidCharacters("0123456789");
			this.getMaskFormatter().setPlaceholderCharacter('_');
			this.getMaskFormatter().install(textFieldRG);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return this.getMaskFormatter();
	}

}
