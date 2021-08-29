package com.projeto.view.autor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

public class TabelaAutor extends JFrame {
	private JTable table;
	private JTextField textField;
	public TabelaAutor() {
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1131, 540);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		table = new JTable();
		table.setBounds(52, 60, 1012, 417);
		panel.add(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(52, 488, 408, 41);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnNewButton = new JButton("Primeiro");
		btnNewButton.setBounds(10, 11, 89, 23);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Anterior");
		btnNewButton_1.setBounds(109, 11, 89, 23);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Pr\u00F3ximo");
		btnNewButton_2.setBounds(208, 11, 89, 23);
		panel_1.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("\u00DAltimo");
		btnNewButton_3.setBounds(307, 11, 89, 23);
		panel_1.add(btnNewButton_3);
		
		JLabel lblNewLabel = new JLabel("P\u00E1gina Atual:");
		lblNewLabel.setBounds(492, 500, 97, 14);
		panel.add(lblNewLabel);
		
		JLabel lblPaginaAtual = new JLabel("10");
		lblPaginaAtual.setBounds(558, 500, 46, 14);
		panel.add(lblPaginaAtual);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"5", "10", "15", "20", "50"}));
		comboBox.setBounds(258, 21, 46, 20);
		panel.add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("Quantidade de Registros por P\u00E1gina");
		lblNewLabel_1.setBounds(52, 24, 196, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Total de P\u00E1ginas");
		lblNewLabel_2.setBounds(599, 500, 97, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblTotalPaginas = new JLabel("100");
		lblTotalPaginas.setBounds(723, 500, 46, 14);
		panel.add(lblTotalPaginas);
		
		JLabel lblNewLabel_3 = new JLabel("Busca pelo Nome:");
		lblNewLabel_3.setBounds(401, 24, 150, 14);
		panel.add(lblNewLabel_3);
		
		textField = new JTextField();
		textField.setBounds(573, 21, 491, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(53, 551, 518, 45);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnNewButton_4 = new JButton("Inclus\u00E3o");
		btnNewButton_4.setBounds(10, 11, 89, 23);
		panel_2.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("Altera\u00E7\u00E3o");
		btnNewButton_5.setBounds(109, 11, 89, 23);
		panel_2.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("Exclus\u00E3o");
		btnNewButton_6.setBounds(208, 11, 89, 23);
		panel_2.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("Consulta");
		btnNewButton_7.setBounds(309, 11, 89, 23);
		panel_2.add(btnNewButton_7);
		
		JButton btnNewButton_8 = new JButton("Fechar");
		btnNewButton_8.setBounds(408, 11, 89, 23);
		panel_2.add(btnNewButton_8);
	}
}
