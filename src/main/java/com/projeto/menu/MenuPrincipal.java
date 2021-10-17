package com.projeto.menu;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import com.projeto.util.ProcessamentoDados;
import com.projeto.view.autor.TabelaAutor;
import com.projeto.view.autorlivro.TabelaAutorLivro;
import com.projeto.view.editora.TabelaEditora;
import com.projeto.view.livro.TabelaLivro;
import javax.swing.JSeparator;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 2780451606851741442L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu menuCadastro;
	private JMenuItem menuAutores;
	private JMenuItem menuEditoras;
	private JMenuItem menuLivros;
	private JMenuItem menuLivrosAutores;
	private JSeparator separatorCadastro;
	private JMenuItem menuSair; 
	
	private JMenu menuRelatorios; 
	private JMenuItem menuRelAutores;
	
	public MenuPrincipal() {
		initComponents();
	}
	
	
	private void initComponents() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource(ProcessamentoDados.SHOW_ICON_SYSTEM)));
		setTitle("Controle de Publicações por Autor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1600, 1000);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuCadastro = new JMenu("Cadastro");
		menuCadastro.setMnemonic(KeyEvent.VK_C);
		menuCadastro.setIcon(new ImageIcon(MenuPrincipal.class.getResource(ProcessamentoDados.SHOW_ICON_MENU_BAR_FILE)));
		menuBar.add(menuCadastro);
		
		menuAutores = new JMenuItem("Autores");
		menuAutores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		menuAutores.setIcon(new ImageIcon(MenuPrincipal.class.getResource(ProcessamentoDados.SHOW_ICON_MENU_AUTOR)));
		menuAutores.setMnemonic(KeyEvent.VK_A);
		menuAutores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaAutor tabelaAutor = new TabelaAutor();
				contentPane.add(tabelaAutor);
				centralizaFormulario(tabelaAutor);
				tabelaAutor.setVisible(ProcessamentoDados.VERDADEIRO);
			}
		});
		menuCadastro.add(menuAutores);
		menuEditoras = new JMenuItem("Editoras");
		menuEditoras.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		menuEditoras.setMnemonic(KeyEvent.VK_E);
		menuEditoras.setIcon(new ImageIcon(MenuPrincipal.class.getResource(ProcessamentoDados.SHOW_ICON_MENU_EDITORA)));
		menuEditoras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaEditora tabelaEditora = new TabelaEditora();
				contentPane.add(tabelaEditora);
				centralizaFormulario(tabelaEditora);
				tabelaEditora.setVisible(ProcessamentoDados.VERDADEIRO);
			}
		});
		menuCadastro.add(menuEditoras);
		
		menuLivros = new JMenuItem("Livros");
		menuLivros.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		menuLivros.setMnemonic(KeyEvent.VK_L);
		menuLivros.setIcon(new ImageIcon(MenuPrincipal.class.getResource(ProcessamentoDados.SHOW_ICON_MENU_LIVRO)));
		menuLivros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaLivro tabelaLivro = new TabelaLivro();
				contentPane.add(tabelaLivro);
				centralizaFormulario(tabelaLivro);
				tabelaLivro.setVisible(ProcessamentoDados.VERDADEIRO);
			}
		});
		menuCadastro.add(menuLivros);
		
		menuLivrosAutores = new JMenuItem("Livros - Autores");
		menuLivrosAutores.setIcon(new ImageIcon(MenuPrincipal.class.getResource(ProcessamentoDados.SHOW_ICON_MENU_WRITING)));
		menuLivrosAutores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaAutorLivro tabelaAutorLivro = new TabelaAutorLivro();
				contentPane.add(tabelaAutorLivro);
				centralizaFormulario(tabelaAutorLivro);
				tabelaAutorLivro.setVisible(ProcessamentoDados.VERDADEIRO);;
			}
		});
		menuCadastro.add(menuLivrosAutores);
		
		menuSair = new JMenuItem("Sair");
		menuSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		menuSair.setIcon(new ImageIcon(MenuPrincipal.class.getResource(ProcessamentoDados.SHOW_ICON_MENU_SAIR)));
		menuSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		separatorCadastro = new JSeparator();
		menuCadastro.add(separatorCadastro);
		menuCadastro.add(menuSair);
		
		menuRelatorios = new JMenu("Relatórios");
		menuRelatorios.setIcon(new ImageIcon(MenuPrincipal.class.getResource(ProcessamentoDados.SHOW_ICON_MENU_BAR_REL)));
		menuBar.add(menuRelatorios);
		
		menuRelAutores = new JMenuItem("Autores");
		menuRelatorios.add(menuRelAutores);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	
	private void centralizaFormulario(JInternalFrame frame) {
		Dimension janelaPrincipal = this.getSize();
		Dimension janelaInterna = frame.getSize();
		frame.setLocation( (janelaPrincipal.width - janelaInterna.width) / 2,
				           (janelaPrincipal.height - janelaInterna.height) /2 );
		
	}
}
