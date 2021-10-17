package com.projeto.view.autorlivro;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import com.projeto.model.model.AutorLivro;
import com.projeto.model.service.AutorLivroService;
import com.projeto.util.ProcessamentoDados;
import com.projeto.util.RenderTable;

public class TabelaAutorLivro extends JInternalFrame {

	private static final long serialVersionUID = 5387318767520552262L;
	
	private JTable tabelaAutorLivro;
	private JTextField textFieldBuscarNomeLivro;
	private JComboBox<String> comboBoxRegistrosTabela;
	private JButton btnPrimeiro;
	private JButton btnAnterior;	
	private JButton btnProximo;
	private JButton btnUltimo;
	
	private JButton btnIncluirLivro;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JButton btnFechar;
	private JButton btnConsultar;
	
	private TabelaAutorLivroModel tabelaAutorLivroModel;
	private TableRowSorter<TabelaAutorLivroModel> sortTabelaAutorLivro;
	
	private Integer totalRegistros = 0; 
	private Integer registrosPorPagina = 5;
	private Integer totalPaginas = 1;
	private Integer paginaAtual = 1;
	private Integer linha = 0; 
	
	private JLabel lblShowPaginaAtual; 
	private JLabel lblPaginaAtual;  
	private JLabel lblShowTotalPaginas;
	private JLabel lblTotalPaginas; 
	private JLabel lblShowTotalRegistros; 
	private JLabel lblTotalRegistros; 
	
	
	public TabelaAutorLivro() {
		initComponents();
		createEvents();
		iniciarPaginacao();
		
	}

	private void createEvents() {
	    tabelaAutorLivro.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	           selecionaAutorLivro(e);
	    	}
		});
		tabelaAutorLivro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
   			   selecionaAutorLivro(e);
			}
		});
		btnPrimeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = 1;
				System.out.println("primeiro "+paginaAtual);
				iniciarPaginacao();
			}
		});
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (paginaAtual > 1) {
					paginaAtual = paginaAtual - 1;
					iniciarPaginacao();
				}
				
			}
		});
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (paginaAtual < totalPaginas) {
					paginaAtual = paginaAtual + 1;
					iniciarPaginacao();
				}
			}
		});
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = totalPaginas;
				iniciarPaginacao();
				
			}
		});
		comboBoxRegistrosTabela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrosPorPagina = Integer.valueOf(comboBoxRegistrosTabela.getSelectedItem().toString());
				iniciarPaginacao();
			}
		});
		btnIncluirLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incluirNovoRegistro();
			}
		});
		btnIncluirLivro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_I) {
					incluirNovoRegistro();
				}
			}
		});
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarRegistroCadastrado();
			}
		});
		btnAlterar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					alterarRegistroCadastrado();
				}		
			}
		});
		btnExcluir.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F) {
					excluirRegistroCadastrado();
				}
			}
		});
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirRegistroCadastrado();
			}
		});
		btnConsultar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_C) {
					consultarRegistroCadastrado();
				}
			}
		});
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultarRegistroCadastrado();
			}
		});
		btnFechar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F) {
					dispose();
				}
			}
		});
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		textFieldBuscarNomeLivro.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtraNomeLivro();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				filtraNomeLivro();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				filtraNomeLivro();
			}
		});
	}
	
	
	
	private void incluirNovoRegistro() {
		AutorLivroFrame autorLivroFrame = new AutorLivroFrame(new JFrame(), true, ProcessamentoDados.INCLUIR_REGISTRO, tabelaAutorLivro, tabelaAutorLivroModel, 0);
		autorLivroFrame.setLocationRelativeTo(null);
		autorLivroFrame.setVisible(true);
		tabelaAutorLivro.requestFocus();
	}
	
	
	private void alterarRegistroCadastrado() {
		if (tabelaAutorLivro.getSelectedRow() != -1 && tabelaAutorLivro.getSelectedRow() < tabelaAutorLivroModel.getRowCount()) {
			linha = tabelaAutorLivro.getSelectedRow();
			AutorLivroFrame autorLivroFrame = new AutorLivroFrame(new JFrame(), true, ProcessamentoDados.ALTERAR_REGISTRO, tabelaAutorLivro, tabelaAutorLivroModel, linha );
			autorLivroFrame.setLocationRelativeTo(null);
			autorLivroFrame.setVisible(true);
			tabelaAutorLivro.requestFocus();
		} else {
			ProcessamentoDados.showMensagem("Selecione um Registro!!!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void excluirRegistroCadastrado() {
		if (tabelaAutorLivro.getSelectedRow() != -1 && tabelaAutorLivro.getSelectedRow() < tabelaAutorLivroModel.getRowCount()) {
			linha = tabelaAutorLivro.getSelectedRow();
			AutorLivroFrame autorLivroFrame = new AutorLivroFrame(new JFrame(), true, ProcessamentoDados.EXCLUIR_REGISTRO, tabelaAutorLivro, tabelaAutorLivroModel, linha );
			autorLivroFrame.setLocationRelativeTo(null);
			autorLivroFrame.setVisible(true);
			tabelaAutorLivro.requestFocus();
		} else {
			ProcessamentoDados.showMensagem("Selecione um Registro!!!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void consultarRegistroCadastrado() {
		if (tabelaAutorLivro.getSelectedRow() != -1 && tabelaAutorLivro.getSelectedRow() < tabelaAutorLivroModel.getRowCount()) {
			linha = tabelaAutorLivro.getSelectedRow();
			AutorLivroFrame autorLivroFrame = new AutorLivroFrame(new JFrame(), true, ProcessamentoDados.CONSULTAR_REGISTRO, tabelaAutorLivro, tabelaAutorLivroModel, linha );
			autorLivroFrame.setLocationRelativeTo(null);
			autorLivroFrame.setVisible(true);
			tabelaAutorLivro.requestFocus();
		} else {
			ProcessamentoDados.showMensagem("Selecione um Registro!!!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	protected void filtraNomeLivro() {
		
		String buscarNomeLivro = textFieldBuscarNomeLivro.getText();
		
		List<AutorLivro> listaLivro = new ArrayList<AutorLivro>();
		
		tabelaAutorLivroModel = new TabelaAutorLivroModel();
	
		listaLivro = carregarListaAutorLivro(buscarNomeLivro); 
		
		tabelaAutorLivroModel.setListaAutorLivro(listaLivro);
		tabelaAutorLivro.setModel(tabelaAutorLivroModel);
		tabelaAutorLivro.setFillsViewportHeight(true);
		tabelaAutorLivro.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tabelaAutorLivroModel.fireTableDataChanged();
		
		tabelaAutorLivro.setAutoCreateRowSorter(true);
		
		tabelaAutorLivro.getTableHeader().setReorderingAllowed(ProcessamentoDados.FALSO);
		
		sortTabelaAutorLivro = new TableRowSorter<TabelaAutorLivroModel>(tabelaAutorLivroModel);
		
		tabelaAutorLivro.setRowSorter(sortTabelaAutorLivro);
		
		tabelaAutorLivro.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		RenderTable renderTable = new RenderTable();
		
		for (int coluna=0; coluna < tabelaAutorLivroModel.getColumnCount();coluna++) {
			tabelaAutorLivro.setDefaultRenderer(tabelaAutorLivroModel.getColumnClass(coluna), renderTable);
		}
		
		for (int coluna=0; coluna < tabelaAutorLivroModel.getTamanhoCampo().length; coluna++) {
			tabelaAutorLivro.getColumnModel()
  		               .getColumn(coluna)
			           .setPreferredWidth(tabelaAutorLivroModel.getTamanhoCampo()[coluna]);
		}
		
	}

	private List<AutorLivro> carregarListaAutorLivro(String buscarNomeLivro) {
		AutorLivroService autorLivroService = new AutorLivroService();
		return autorLivroService.carregarListaLivro(buscarNomeLivro);
	}

	private void iniciarPaginacao() {
		
		List<AutorLivro> listaLivro = new ArrayList<AutorLivro>();
		
		totalRegistros = buscarTotalRegistrosAutorLivros();
		registrosPorPagina = Integer.valueOf(comboBoxRegistrosTabela.getSelectedItem().toString());
		Double totalPaginasTabela = Math.ceil( totalRegistros.doubleValue() / registrosPorPagina.doubleValue() );
		
		totalPaginas = totalPaginasTabela.intValue();
		
		lblTotalRegistros.setText(ProcessamentoDados.converterInteiroParaString(totalRegistros));
		lblTotalPaginas.setText(ProcessamentoDados.converterInteiroParaString(totalPaginas));
		lblPaginaAtual.setText(ProcessamentoDados.converterInteiroParaString(paginaAtual));            
		
		if (paginaAtual.equals(1)) {
			btnPrimeiro.setEnabled(false);
			btnPrimeiro.setEnabled(false);
		} else {
			btnPrimeiro.setEnabled(true);
			btnPrimeiro.setEnabled(true);
		}
		
		if (paginaAtual.equals(totalPaginas)) {
			btnUltimo.setEnabled(false);
			btnProximo.setEnabled(false);
		} else {
			btnUltimo.setEnabled(true);
			btnProximo.setEnabled(true);
     	}
		
		
        if ( paginaAtual > totalPaginas ) {
        	paginaAtual = totalPaginas;
        }
		
       
		tabelaAutorLivroModel = new TabelaAutorLivroModel();
		
		listaLivro = carregarListaAutorLivro(paginaAtual, registrosPorPagina); 
		
		tabelaAutorLivroModel.setListaAutorLivro(listaLivro);
		tabelaAutorLivro.setModel(tabelaAutorLivroModel);
		tabelaAutorLivro.setFillsViewportHeight(true);
		tabelaAutorLivro.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tabelaAutorLivroModel.fireTableDataChanged();
		
		tabelaAutorLivro.setAutoCreateRowSorter(true);
		
		tabelaAutorLivro.getTableHeader().setReorderingAllowed(ProcessamentoDados.FALSO);
		
		sortTabelaAutorLivro = new TableRowSorter<TabelaAutorLivroModel>(tabelaAutorLivroModel);
		
		tabelaAutorLivro.setRowSorter(sortTabelaAutorLivro);
		
		tabelaAutorLivro.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		RenderTable renderTable = new RenderTable();
		
		for (int coluna=0; coluna < tabelaAutorLivroModel.getColumnCount();coluna++) {
			tabelaAutorLivro.setDefaultRenderer(tabelaAutorLivroModel.getColumnClass(coluna), renderTable);
		}
		
		for (int coluna=0; coluna < tabelaAutorLivroModel.getTamanhoCampo().length; coluna++) {
			tabelaAutorLivro.getColumnModel()
			             .getColumn(coluna)
			             .setPreferredWidth(tabelaAutorLivroModel.getTamanhoCampo()[coluna]);
		}
		
	}

	private List<AutorLivro> carregarListaAutorLivro(Integer paginaAtual, Integer registrosPorPagina) {
		AutorLivroService autorLivroService = new AutorLivroService();
		return autorLivroService.carregarListaAutorLivro( ( registrosPorPagina * ( paginaAtual - 1 )), registrosPorPagina);
	}

	private Integer buscarTotalRegistrosAutorLivros() {
		AutorLivroService autorLivroService = new AutorLivroService();
		return autorLivroService.countTotalRegistroAutorLivros();
	}
	
	protected void selecionaAutorLivro(MouseEvent e) {
	  if (e.getButton() == MouseEvent.BUTTON1) {
		linha = tabelaAutorLivro.getSelectedRow();
		 if ( tabelaAutorLivro.getRowSorter() != null ) {
			linha =  tabelaAutorLivro.getRowSorter().convertRowIndexToModel(linha);
		 }
	  }		 
	}
	
	protected void selecionaAutorLivro(KeyEvent e) {
		if ( e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_DOWN ) {
			linha = tabelaAutorLivro.getSelectedRow();
			 if ( tabelaAutorLivro.getRowSorter() != null ) {
				linha =  tabelaAutorLivro.getRowSorter().convertRowIndexToModel(linha);
			 }
		}
	}

	private void initComponents() {
		setFrameIcon(new ImageIcon(TabelaAutorLivro.class.getResource(ProcessamentoDados.SHOW_ICON_MENU_WRITING)));
		setTitle("Cadastro de Livro");
		setBounds(new Rectangle(100, 100, 1150, 700));		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(null);
		setBounds(100,100,1150,700);
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelPrincipal.setBounds(0, 0, 1131, 556);
		getContentPane().add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(62, 52, 1012, 417);
		panelPrincipal.add(scrollPane);
		
	
		tabelaAutorLivro = new JTable();
		scrollPane.setViewportView(tabelaAutorLivro);
		tabelaAutorLivro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		comboBoxRegistrosTabela = new JComboBox<String>();
		comboBoxRegistrosTabela.setModel(new DefaultComboBoxModel<String>(new String[] {"5", "10", "15", "20", "50"}));
		comboBoxRegistrosTabela.setBounds(269, 21, 46, 20);
		panelPrincipal.add(comboBoxRegistrosTabela);
		
		JLabel lblNewLabel_1 = new JLabel("Quantidade de Registros por Página:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(52, 24, 207, 14);
		panelPrincipal.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Busca pelo Nome:");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setBounds(401, 24, 150, 14);
		panelPrincipal.add(lblNewLabel_3);
		
		textFieldBuscarNomeLivro = new JTextField();
		textFieldBuscarNomeLivro.setBounds(573, 21, 491, 20);
		panelPrincipal.add(textFieldBuscarNomeLivro);
		textFieldBuscarNomeLivro.setColumns(10);
		
		JPanel panelNavegacao = new JPanel();
		panelNavegacao.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelNavegacao.setBounds(62, 489, 1012, 56);
		panelPrincipal.add(panelNavegacao);
		panelNavegacao.setLayout(null);
		
		JPanel panelPaginacao = new JPanel();
		panelPaginacao.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelPaginacao.setBounds(85, 7, 408, 41);
		panelNavegacao.add(panelPaginacao);
		panelPaginacao.setLayout(null);
		
		btnPrimeiro = new JButton("");
		btnPrimeiro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnPrimeiro.setToolTipText("Primeira P\u00E1gina");
		btnPrimeiro.setIcon(new ImageIcon(TabelaAutorLivro.class.getResource(ProcessamentoDados.BUTTON_ICON_PRIMEIRO)));
		btnPrimeiro.setBounds(10, 11, 89, 23);
		panelPaginacao.add(btnPrimeiro);
		
		btnAnterior = new JButton("");
		btnAnterior.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAnterior.setToolTipText("Anterior");
		btnAnterior.setIcon(new ImageIcon(TabelaAutorLivro.class.getResource(ProcessamentoDados.BUTTON_ICON_ANTERIOR)));
		btnAnterior.setBounds(109, 11, 89, 23);
		panelPaginacao.add(btnAnterior);
		
		btnProximo = new JButton("");
		btnProximo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnProximo.setToolTipText("Pr\u00F3ximo");
		btnProximo.setIcon(new ImageIcon(TabelaAutorLivro.class.getResource(ProcessamentoDados.BUTTON_ICON_PROXIMO)));
		btnProximo.setBounds(208, 11, 89, 23);
		panelPaginacao.add(btnProximo);
		
		btnUltimo = new JButton("");
		btnUltimo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUltimo.setToolTipText("\u00DAltimo");
		btnUltimo.setIcon(new ImageIcon(TabelaAutorLivro.class.getResource(ProcessamentoDados.BUTTON_ICON_ULTIMO)));
		btnUltimo.setBounds(307, 11, 89, 23);
		panelPaginacao.add(btnUltimo);
		
		lblShowPaginaAtual = new JLabel("P\u00E1gina Atual:");
		lblShowPaginaAtual.setHorizontalAlignment(SwingConstants.RIGHT);
		lblShowPaginaAtual.setBounds(510, 22, 88, 14);
		panelNavegacao.add(lblShowPaginaAtual);
		
		lblPaginaAtual = new JLabel("10");
		lblPaginaAtual.setBounds(608, 22, 46, 14);
		panelNavegacao.add(lblPaginaAtual);
		
		lblShowTotalPaginas = new JLabel("Total de P\u00E1ginas:");
		lblShowTotalPaginas.setHorizontalAlignment(SwingConstants.RIGHT);
		lblShowTotalPaginas.setBounds(615, 22, 116, 14);
		panelNavegacao.add(lblShowTotalPaginas);
		
		lblTotalPaginas = new JLabel("100");
		lblTotalPaginas.setBounds(741, 22, 46, 14);
		panelNavegacao.add(lblTotalPaginas);
		
		lblShowTotalRegistros = new JLabel("Total Registros:");
		lblShowTotalRegistros.setHorizontalAlignment(SwingConstants.RIGHT);
		lblShowTotalRegistros.setBounds(797, 22, 101, 14);
		panelNavegacao.add(lblShowTotalRegistros);
		
		lblTotalRegistros = new JLabel("100");
		lblTotalRegistros.setBounds(908, 22, 46, 14);
		panelNavegacao.add(lblTotalRegistros);
		
		JPanel panelBotoes = new JPanel();
		panelBotoes.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelBotoes.setBounds(141, 580, 622, 45);
		getContentPane().add(panelBotoes);
		panelBotoes.setLayout(null);
		
		btnIncluirLivro = new JButton("Inclus\u00E3o");
		btnIncluirLivro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnIncluirLivro.setMnemonic(KeyEvent.VK_I);
		btnIncluirLivro.setToolTipText("Cadastro de um novo registro");
		btnIncluirLivro.setIcon(new ImageIcon(TabelaAutorLivro.class.getResource("/imagens/book_add.png")));
		btnIncluirLivro.setBounds(10, 11, 114, 23);
		panelBotoes.add(btnIncluirLivro);
		
		btnAlterar = new JButton("Altera\u00E7\u00E3o");
		btnAlterar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlterar.setToolTipText("Alterar registro cadastrado");
		btnAlterar.setMnemonic(KeyEvent.VK_A);
		btnAlterar.setIcon(new ImageIcon(TabelaAutorLivro.class.getResource("/imagens/book_edit.png")));
		btnAlterar.setBounds(134, 11, 114, 23);
		panelBotoes.add(btnAlterar);
		
		btnExcluir = new JButton("Exclus\u00E3o");
		btnExcluir.setMnemonic(KeyEvent.VK_E);
		btnExcluir.setToolTipText("Excluir registro cadastrado");
		btnExcluir.setIcon(new ImageIcon(TabelaAutorLivro.class.getResource("/imagens/book_delete.png")));
		btnExcluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnExcluir.setBounds(269, 11, 89, 23);
		panelBotoes.add(btnExcluir);
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.setMnemonic(KeyEvent.VK_C);
		btnConsultar.setIcon(new ImageIcon(TabelaAutorLivro.class.getResource("/imagens/book_open.png")));
		btnConsultar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnConsultar.setBounds(370, 11, 89, 23);
		panelBotoes.add(btnConsultar);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setToolTipText("Fechar programa ");
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.setIcon(new ImageIcon(TabelaAutorLivro.class.getResource("/imagens/saida.png")));
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setBounds(469, 11, 89, 23);
		panelBotoes.add(btnFechar);
		
	}
}
