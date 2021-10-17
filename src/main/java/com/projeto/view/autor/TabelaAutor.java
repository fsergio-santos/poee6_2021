package com.projeto.view.autor;

import java.awt.Font;
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
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.projeto.model.model.Autor;
import com.projeto.model.service.AutorService;
import com.projeto.util.ProcessamentoDados;
import com.projeto.util.RenderTable;
import com.projeto.view.autorlivro.BuscarLivroPorAutor;

public class TabelaAutor extends JInternalFrame {
	

	private static final long serialVersionUID = 5387318767520552262L;
	
	private JTable tabelaAutores;
	private JTextField textFieldBuscarNomeAutor;
	private JComboBox<String> comboBoxRegistrosTabela;
	private JButton btnPrimeiro;
	private JButton btnAnterior;	
	private JButton btnProximo;
	private JButton btnUltimo;
	
	private JButton btnIncluir;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JButton btnFechar;
	private JButton btnConsultar;
	
	private TabelaAutorModel tabelaAutorModel;
	private TableRowSorter<TabelaAutorModel> sortTabelaAutor;
	
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
	private JScrollPane scrollPane;
	private JButton btnConsultarLivro;
	
	private Autor autor;
	
	public TabelaAutor() {
	
		setFrameIcon(new ImageIcon(TabelaAutor.class.getResource("/imagens/author.png")));
		setTitle("Cadastro de Autores");
		initComponents();
		createEvents();
		iniciarPaginacao();
		
	}

	private void createEvents() {
	   tabelaAutores.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	   		   selecionaAutor(e);
	    	}
		});
		tabelaAutores.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
   			   selecionaAutor(e);
			}
		});
		btnPrimeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = 1;
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
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incluirNovoRegistro();
			}
		});
		btnIncluir.addKeyListener(new KeyAdapter() {
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
		textFieldBuscarNomeAutor.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtraNomeAutor();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				filtraNomeAutor();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				filtraNomeAutor();
			}
		});
		btnConsultarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarLivroPorAutor();
			}
		});
	}
	
	private void buscarLivroPorAutor() {
		if (tabelaAutores.getSelectedRow() != -1 && tabelaAutores.getSelectedRow() < tabelaAutorModel.getRowCount()) {
			linha = tabelaAutores.getSelectedRow();
			autor = new Autor();
			autor = this.tabelaAutorModel.getAutor(this.linha);
			BuscarLivroPorAutor buscarLivroAutor = new BuscarLivroPorAutor(new JFrame(), true, autor );
			buscarLivroAutor.setLocationRelativeTo(null);
			buscarLivroAutor.setVisible(true);
			tabelaAutores.requestFocus();
		} else {
			ProcessamentoDados.showMensagem("Selecione um Registro!!!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	private void incluirNovoRegistro() {
		AutorFrame autorFrame = new AutorFrame(new JFrame(), true, ProcessamentoDados.INCLUIR_REGISTRO, tabelaAutores, tabelaAutorModel, 0);
		autorFrame.setLocationRelativeTo(null);
		autorFrame.setVisible(true);
		tabelaAutores.requestFocus();
	}
	
	
	private void alterarRegistroCadastrado() {
		if (tabelaAutores.getSelectedRow() != -1 && tabelaAutores.getSelectedRow() < tabelaAutorModel.getRowCount()) {
			linha = tabelaAutores.getSelectedRow();
			AutorFrame autorFrame = new AutorFrame(new JFrame(), true, ProcessamentoDados.ALTERAR_REGISTRO, tabelaAutores, tabelaAutorModel, linha );
			autorFrame.setLocationRelativeTo(null);
			autorFrame.setVisible(true);
			tabelaAutores.requestFocus();
		} else {
			ProcessamentoDados.showMensagem("Selecione um Registro!!!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void excluirRegistroCadastrado() {
		if (tabelaAutores.getSelectedRow() != -1 && tabelaAutores.getSelectedRow() < tabelaAutorModel.getRowCount()) {
			linha = tabelaAutores.getSelectedRow();
			AutorFrame autorFrame = new AutorFrame(new JFrame(), true, ProcessamentoDados.EXCLUIR_REGISTRO, tabelaAutores, tabelaAutorModel, linha );
			autorFrame.setLocationRelativeTo(null);
			autorFrame.setVisible(true);
			tabelaAutores.requestFocus();
			
		} else {
			ProcessamentoDados.showMensagem("Selecione um Registro!!!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void consultarRegistroCadastrado() {
		if (tabelaAutores.getSelectedRow() != -1 && tabelaAutores.getSelectedRow() < tabelaAutorModel.getRowCount()) {
			linha = tabelaAutores.getSelectedRow();
			AutorFrame autorFrame = new AutorFrame(new JFrame(), true, ProcessamentoDados.CONSULTAR_REGISTRO, tabelaAutores, tabelaAutorModel, linha );
			autorFrame.setLocationRelativeTo(null);
			autorFrame.setVisible(true);
		} else {
			ProcessamentoDados.showMensagem("Selecione um Registro!!!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	protected void filtraNomeAutor() {
		
		String buscarNomeAutor = textFieldBuscarNomeAutor.getText();
		
		List<Autor> listaAutor = new ArrayList<Autor>();
		
		tabelaAutorModel = new TabelaAutorModel();
		
		listaAutor = carregarListaAutor(buscarNomeAutor); 
		
		tabelaAutorModel.setListaAutor(listaAutor);
		tabelaAutores.setModel(tabelaAutorModel);
		tabelaAutores.setFillsViewportHeight(true);
		tabelaAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tabelaAutorModel.fireTableDataChanged();
		
		tabelaAutores.setAutoCreateRowSorter(true);
		
		sortTabelaAutor = new TableRowSorter<TabelaAutorModel>(tabelaAutorModel);
		
		RenderTable renderTable = new RenderTable();
		
		for (int coluna=0; coluna < tabelaAutorModel.getColumnCount();coluna++) {
			tabelaAutores.setDefaultRenderer(tabelaAutorModel.getColumnClass(coluna), renderTable);
		}
	
		for (int coluna=0; coluna < tabelaAutorModel.getTamanhoCampo().length; coluna++) {
			tabelaAutores.getColumnModel().getColumn(coluna).setPreferredWidth(tabelaAutorModel.getTamanhoCampo()[coluna]);
		}
		
		tabelaAutores.setRowSorter(sortTabelaAutor);
	
	}


	private List<Autor> carregarListaAutor(String buscarNomeAutor) {
		AutorService autorService = new AutorService();
		return autorService.carregarListaAutor(buscarNomeAutor);
	}

	private void iniciarPaginacao() {
		
		List<Autor> listaAutor = new ArrayList<Autor>();
		
		totalRegistros = buscarTotalRegistrosAutores();
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
		
       
		tabelaAutorModel = new TabelaAutorModel();
		
		listaAutor = carregarListaAutor(paginaAtual, registrosPorPagina); 
		
		tabelaAutorModel.setListaAutor(listaAutor);
		tabelaAutores.setModel(tabelaAutorModel);
		tabelaAutores.setFillsViewportHeight(true);
		tabelaAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaAutorModel.fireTableDataChanged();
		
		tabelaAutores.setAutoCreateRowSorter(true);
		
		sortTabelaAutor = new TableRowSorter<TabelaAutorModel>(tabelaAutorModel);
		
		tabelaAutores.setRowSorter(sortTabelaAutor);
		
		tabelaAutores.getTableHeader().setReorderingAllowed(ProcessamentoDados.FALSO);
		
		tabelaAutores.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		RenderTable renderTable = new RenderTable();
		
		for (int coluna=0; coluna < tabelaAutorModel.getColumnCount();coluna++) {
			tabelaAutores.setDefaultRenderer(tabelaAutorModel.getColumnClass(coluna), renderTable);
			
		}
		
		
		for (int coluna=0; coluna < tabelaAutorModel.getColumnCount();coluna++) {
		    TableColumn column = tabelaAutores.getColumnModel().getColumn(coluna);
            column.setPreferredWidth(tabelaAutorModel.getTamanhoCampo()[coluna]);
            column.setMinWidth(tabelaAutorModel.getTamanhoCampo()[coluna]);
            column.setMaxWidth(tabelaAutorModel.getTamanhoCampo()[coluna]);
     	}
	
	}


	private List<Autor> carregarListaAutor(Integer paginaAtual, Integer registrosPorPagina) {
		AutorService autorService = new AutorService();
		return autorService.carregarListaAutor( ( registrosPorPagina * ( paginaAtual - 1 )), registrosPorPagina);
	}

	private Integer buscarTotalRegistrosAutores() {
		AutorService autorService = new AutorService();
		return autorService.countTotalRegistroAutores();
	}
	
	protected void selecionaAutor(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) { 
			linha = tabelaAutores.getSelectedRow();
 		    if ( tabelaAutores.getRowSorter() != null ) {
				linha =  tabelaAutores.getRowSorter().convertRowIndexToModel(linha);
			}
		} 
	}
	

	protected void selecionaAutor(KeyEvent e) {
		if ( e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_DOWN ) {
			linha = tabelaAutores.getSelectedRow();
            if ( tabelaAutores.getRowSorter() != null ) {
				linha =  tabelaAutores.getRowSorter().convertRowIndexToModel(linha);
			}
		}
	}


	private void initComponents() {
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		getContentPane().setLayout(null);
		setBounds(100, 100, 1423, 749);
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelPrincipal.setBounds(10, 11, 1387, 641);
		getContentPane().add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		comboBoxRegistrosTabela = new JComboBox<String>();
		comboBoxRegistrosTabela.setModel(new DefaultComboBoxModel<String>(new String[] {"5", "10", "15", "20", "50"}));
		comboBoxRegistrosTabela.setBounds(269, 21, 46, 20);
		panelPrincipal.add(comboBoxRegistrosTabela);
		
		JLabel lblQuantidadeResgistros = new JLabel("Quantidade de Registros por Página:");
		lblQuantidadeResgistros.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblQuantidadeResgistros.setHorizontalAlignment(SwingConstants.RIGHT);
		lblQuantidadeResgistros.setBounds(52, 24, 207, 14);
		panelPrincipal.add(lblQuantidadeResgistros);
		
		JLabel lblBuscaNome = new JLabel("Busca pelo Nome:");
		lblBuscaNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBuscaNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBuscaNome.setBounds(401, 24, 150, 14);
		panelPrincipal.add(lblBuscaNome);
		
		textFieldBuscarNomeAutor = new JTextField();
		textFieldBuscarNomeAutor.setBounds(573, 21, 459, 20);
		panelPrincipal.add(textFieldBuscarNomeAutor);
		textFieldBuscarNomeAutor.setColumns(10);
		
		JPanel panelNavegacao = new JPanel();
		panelNavegacao.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelNavegacao.setBounds(41, 566, 1297, 56);
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
		btnPrimeiro.setIcon(new ImageIcon(TabelaAutor.class.getResource(ProcessamentoDados.BUTTON_ICON_PRIMEIRO)));
		btnPrimeiro.setBounds(10, 11, 89, 23);
		panelPaginacao.add(btnPrimeiro);
		
		btnAnterior = new JButton("");
		btnAnterior.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAnterior.setToolTipText("Anterior");
		btnAnterior.setIcon(new ImageIcon(TabelaAutor.class.getResource(ProcessamentoDados.BUTTON_ICON_ANTERIOR)));
		btnAnterior.setBounds(109, 11, 89, 23);
		panelPaginacao.add(btnAnterior);
		
		btnProximo = new JButton("");
		btnProximo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnProximo.setToolTipText("Pr\u00F3ximo");
		btnProximo.setIcon(new ImageIcon(TabelaAutor.class.getResource(ProcessamentoDados.BUTTON_ICON_PROXIMO)));
		btnProximo.setBounds(208, 11, 89, 23);
		panelPaginacao.add(btnProximo);
		
		btnUltimo = new JButton("");
		btnUltimo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUltimo.setToolTipText("\u00DAltimo");
		btnUltimo.setIcon(new ImageIcon(TabelaAutor.class.getResource(ProcessamentoDados.BUTTON_ICON_ULTIMO)));
		btnUltimo.setBounds(307, 11, 89, 23);
		panelPaginacao.add(btnUltimo);
		
		lblShowPaginaAtual = new JLabel("P\u00E1gina Atual:");
		lblShowPaginaAtual.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblShowPaginaAtual.setHorizontalAlignment(SwingConstants.RIGHT);
		lblShowPaginaAtual.setBounds(510, 22, 88, 14);
		panelNavegacao.add(lblShowPaginaAtual);
		
		lblPaginaAtual = new JLabel("10");
		lblPaginaAtual.setBounds(608, 22, 46, 14);
		panelNavegacao.add(lblPaginaAtual);
		
		lblShowTotalPaginas = new JLabel("Total de P\u00E1ginas:");
		lblShowTotalPaginas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblShowTotalPaginas.setHorizontalAlignment(SwingConstants.RIGHT);
		lblShowTotalPaginas.setBounds(615, 22, 116, 14);
		panelNavegacao.add(lblShowTotalPaginas);
		
		lblTotalPaginas = new JLabel("100");
		lblTotalPaginas.setBounds(741, 22, 46, 14);
		panelNavegacao.add(lblTotalPaginas);
		
		lblShowTotalRegistros = new JLabel("Total Registros:");
		lblShowTotalRegistros.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblShowTotalRegistros.setHorizontalAlignment(SwingConstants.RIGHT);
		lblShowTotalRegistros.setBounds(797, 22, 101, 14);
		panelNavegacao.add(lblShowTotalRegistros);
		
		lblTotalRegistros = new JLabel("100");
		lblTotalRegistros.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalRegistros.setBounds(908, 22, 46, 14);
		panelNavegacao.add(lblTotalRegistros);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 72, 1297, 483);
		panelPrincipal.add(scrollPane);
		
		tabelaAutores = new JTable();
		scrollPane.setViewportView(tabelaAutores);
		
		JPanel panelBotoes = new JPanel();
		panelBotoes.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelBotoes.setBounds(143, 663, 840, 45);
		getContentPane().add(panelBotoes);
		panelBotoes.setLayout(null);
		
		btnIncluir = new JButton("Inclus\u00E3o");
		btnIncluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnIncluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnIncluir.setMnemonic(KeyEvent.VK_I);
		btnIncluir.setToolTipText("Cadastro de um novo registro");
		btnIncluir.setIcon(new ImageIcon(TabelaAutor.class.getResource(ProcessamentoDados.BUTTON_ICON_INCLUIR)));
		btnIncluir.setBounds(10, 11, 114, 23);
		panelBotoes.add(btnIncluir);
		
		btnAlterar = new JButton("Altera\u00E7\u00E3o");
		btnAlterar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAlterar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlterar.setToolTipText("Alterar registro cadastrado");
		btnAlterar.setMnemonic(KeyEvent.VK_A);
		btnAlterar.setIcon(new ImageIcon(TabelaAutor.class.getResource(ProcessamentoDados.BUTTON_ICON_ALTERAR)));
		btnAlterar.setBounds(134, 11, 114, 23);
		panelBotoes.add(btnAlterar);
		
		btnExcluir = new JButton("Exclus\u00E3o");
		btnExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnExcluir.setMnemonic(KeyEvent.VK_E);
		btnExcluir.setToolTipText("Excluir registro cadastrado");
		btnExcluir.setIcon(new ImageIcon(TabelaAutor.class.getResource(ProcessamentoDados.BUTTON_ICON_EXCLUIR)));
		btnExcluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnExcluir.setBounds(269, 11, 89, 23);
		panelBotoes.add(btnExcluir);
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnConsultar.setMnemonic(KeyEvent.VK_C);
		btnConsultar.setIcon(new ImageIcon(TabelaAutor.class.getResource(ProcessamentoDados.BUTTON_ICON_CONSULTAR)));
		btnConsultar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnConsultar.setBounds(370, 11, 89, 23);
		panelBotoes.add(btnConsultar);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFechar.setToolTipText("Fechar programa ");
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.setIcon(new ImageIcon(TabelaAutor.class.getResource(ProcessamentoDados.BUTTON_ICON_FECHAR)));
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setBounds(469, 11, 89, 23);
		panelBotoes.add(btnFechar);
		
		btnConsultarLivro = new JButton("Consultar Livro");
		btnConsultarLivro.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnConsultarLivro.setIcon(new ImageIcon(TabelaAutor.class.getResource(ProcessamentoDados.SHOW_ICON_MENU_SEARCH_BOOK)));
		btnConsultarLivro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnConsultarLivro.setBounds(635, 11, 152, 23);
		panelBotoes.add(btnConsultarLivro);
		
		
		
	}
}
