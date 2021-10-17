package com.projeto.view.editora;

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

import com.projeto.model.model.Editora;
import com.projeto.model.service.EditoraService;
import com.projeto.util.ProcessamentoDados;
import com.projeto.util.RenderTable;
import java.awt.Font;

public class TabelaEditora extends JInternalFrame {
	

	private static final long serialVersionUID = 5387318767520552262L;
	
	
	private JTable tabelaEditora;
	private JTextField textFieldBuscarNomeEditora;
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
	
	private TabelaEditoraModel tabelaEditoraModel;
	private TableRowSorter<TabelaEditoraModel> sortTabelaEditora;
	
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
	
	
	public TabelaEditora() {
		initComponents();
		createEvents();
		iniciarPaginacao();
		
	}

	private void createEvents() {
	    tabelaEditora.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	           selecionaEditora(e);
	    	}
		});
		tabelaEditora.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
   			   selecionaEditora(e);
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
					System.out.println("anterior "+paginaAtual);
					iniciarPaginacao();
				}
				
			}
		});
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (paginaAtual < totalPaginas) {
					paginaAtual = paginaAtual + 1;
					System.out.println("proximo "+paginaAtual);
					iniciarPaginacao();
				}
			}
		});
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = totalPaginas;
				System.out.println("ultimo "+paginaAtual);
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
		textFieldBuscarNomeEditora.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtraNomeEditora();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtraNomeEditora();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filtraNomeEditora();
			}
		});
	}
	
	
	
	private void incluirNovoRegistro() {
		EditoraFrame autorFrame = new EditoraFrame(new JFrame(), true, ProcessamentoDados.INCLUIR_REGISTRO, tabelaEditora, tabelaEditoraModel, 0);
		autorFrame.setLocationRelativeTo(null);
		autorFrame.setVisible(true);
	}
	
	
	private void alterarRegistroCadastrado() {
		if (tabelaEditora.getSelectedRow() != -1 && tabelaEditora.getSelectedRow() < tabelaEditoraModel.getRowCount()) {
			linha = tabelaEditora.getSelectedRow();
			EditoraFrame autorFrame = new EditoraFrame(new JFrame(), true, ProcessamentoDados.ALTERAR_REGISTRO, tabelaEditora, tabelaEditoraModel, linha );
			autorFrame.setLocationRelativeTo(null);
			autorFrame.setVisible(true);
		} else {
			ProcessamentoDados.showMensagem("Selecione um Registro!!!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void excluirRegistroCadastrado() {
		if (tabelaEditora.getSelectedRow() != -1 && tabelaEditora.getSelectedRow() < tabelaEditoraModel.getRowCount()) {
			linha = tabelaEditora.getSelectedRow();
			EditoraFrame autorFrame = new EditoraFrame(new JFrame(), true, ProcessamentoDados.EXCLUIR_REGISTRO, tabelaEditora, tabelaEditoraModel, linha );
			autorFrame.setLocationRelativeTo(null);
			autorFrame.setVisible(true);
		} else {
			ProcessamentoDados.showMensagem("Selecione um Registro!!!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void consultarRegistroCadastrado() {
		if (tabelaEditora.getSelectedRow() != -1 && tabelaEditora.getSelectedRow() < tabelaEditoraModel.getRowCount()) {
			linha = tabelaEditora.getSelectedRow();
			EditoraFrame autorFrame = new EditoraFrame(new JFrame(), true, ProcessamentoDados.CONSULTAR_REGISTRO, tabelaEditora, tabelaEditoraModel, linha );
			autorFrame.setLocationRelativeTo(null);
			autorFrame.setVisible(true);
		} else {
			ProcessamentoDados.showMensagem("Selecione um Registro!!!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	protected void filtraNomeEditora() {
		
		String buscarNomeEditora = textFieldBuscarNomeEditora.getText();
		
		List<Editora> listaEditora = new ArrayList<Editora>();
		
		tabelaEditoraModel = new TabelaEditoraModel();

		
		listaEditora = carregarListaEditora(buscarNomeEditora); 
		
		tabelaEditoraModel.setListaEditora(listaEditora);
		tabelaEditora.setModel(tabelaEditoraModel);
		tabelaEditora.setFillsViewportHeight(true);
		tabelaEditora.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tabelaEditoraModel.fireTableDataChanged();
		
		tabelaEditora.setAutoCreateRowSorter(true);
		
		tabelaEditora.getTableHeader().setReorderingAllowed(ProcessamentoDados.FALSO);
		
		sortTabelaEditora = new TableRowSorter<TabelaEditoraModel>(tabelaEditoraModel);
		
		tabelaEditora.setRowSorter(sortTabelaEditora);
		
		tabelaEditora.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		RenderTable renderTable = new RenderTable();
		
		for (int coluna=0; coluna < tabelaEditoraModel.getColumnCount();coluna++) {
			tabelaEditora.setDefaultRenderer(tabelaEditoraModel.getColumnClass(coluna), renderTable);
		}
		
		for (int coluna=0; coluna < tabelaEditoraModel.getTamanhoCampo().length; coluna++) {
			tabelaEditora.getColumnModel()
			             .getColumn(coluna)
			             .setPreferredWidth(tabelaEditoraModel.getTamanhoCampo()[coluna]);
		}
		
	}

	private List<Editora> carregarListaEditora(String buscarNomeAutor) {
		EditoraService editoraService = new EditoraService();
		return editoraService.carregarListaEditora(buscarNomeAutor);
	}

	private void iniciarPaginacao() {
		
		List<Editora> listaEditora = new ArrayList<Editora>();
		
		totalRegistros = buscarTotalRegistrosEditoraes();
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
		
       
		tabelaEditoraModel = new TabelaEditoraModel();
		
		listaEditora = carregarListaEditora(paginaAtual, registrosPorPagina); 
		
		tabelaEditoraModel.setListaEditora(listaEditora);
		tabelaEditora.setModel(tabelaEditoraModel);
		tabelaEditora.setFillsViewportHeight(true);
		tabelaEditora.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tabelaEditoraModel.fireTableDataChanged();
		
		tabelaEditora.setAutoCreateRowSorter(true);
		
		tabelaEditora.getTableHeader().setReorderingAllowed(ProcessamentoDados.FALSO);
		
		sortTabelaEditora = new TableRowSorter<TabelaEditoraModel>(tabelaEditoraModel);
		
		tabelaEditora.setRowSorter(sortTabelaEditora);
		
		tabelaEditora.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		RenderTable renderTable = new RenderTable();
		
		for (int coluna=0; coluna < tabelaEditoraModel.getColumnCount();coluna++) {
			tabelaEditora.setDefaultRenderer(tabelaEditoraModel.getColumnClass(coluna), renderTable);
		}
		
		for (int coluna=0; coluna < tabelaEditoraModel.getTamanhoCampo().length; coluna++) {
			tabelaEditora.getColumnModel()
			             .getColumn(coluna)
			             .setPreferredWidth(tabelaEditoraModel.getTamanhoCampo()[coluna]);
		}
		
	}


	private List<Editora> carregarListaEditora(Integer paginaAtual, Integer registrosPorPagina) {
		EditoraService autorService = new EditoraService();
		return autorService.carregarListaEditora( ( registrosPorPagina * ( paginaAtual - 1 )), registrosPorPagina);
	}

	private Integer buscarTotalRegistrosEditoraes() {
		EditoraService autorService = new EditoraService();
		return autorService.countTotalRegistroEditoraes();
	}
	
	protected void selecionaEditora(MouseEvent e) {
	  if (e.getButton() == MouseEvent.BUTTON1) {
		linha = tabelaEditora.getSelectedRow();
		 if ( tabelaEditora.getRowSorter() != null ) {
			linha =  tabelaEditora.getRowSorter().convertRowIndexToModel(linha);
		 }
	  }		 
	}
	
	protected void selecionaEditora(KeyEvent e) {
		if ( e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_DOWN ) {
			linha = tabelaEditora.getSelectedRow();
			 if ( tabelaEditora.getRowSorter() != null ) {
				linha =  tabelaEditora.getRowSorter().convertRowIndexToModel(linha);
			 }
		}
	}

	private void initComponents() {

		setFrameIcon(new ImageIcon(TabelaEditora.class.getResource("/imagens/bank.png")));
		setTitle("Cadastro de Editora");
		setBounds(new Rectangle(100, 100, 1150, 700));

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		getContentPane().setLayout(null);
		setBounds(100,100,1200,671);
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelPrincipal.setBounds(0, 0, 1185, 556);
		getContentPane().add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(62, 52, 1061, 417);
		panelPrincipal.add(scrollPane);
		
	
		tabelaEditora = new JTable();
		scrollPane.setViewportView(tabelaEditora);
		tabelaEditora.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		comboBoxRegistrosTabela = new JComboBox<String>();
		comboBoxRegistrosTabela.setModel(new DefaultComboBoxModel<String>(new String[] {"5", "10", "15", "20", "50"}));
		comboBoxRegistrosTabela.setBounds(277, 21, 46, 20);
		panelPrincipal.add(comboBoxRegistrosTabela);
		
		JLabel lblNewLabel_1 = new JLabel("Quantidade de Registros por Página:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(60, 24, 207, 14);
		panelPrincipal.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Busca pelo Nome:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setBounds(401, 24, 150, 14);
		panelPrincipal.add(lblNewLabel_3);
		
		textFieldBuscarNomeEditora = new JTextField();
		textFieldBuscarNomeEditora.setBounds(573, 21, 491, 20);
		panelPrincipal.add(textFieldBuscarNomeEditora);
		textFieldBuscarNomeEditora.setColumns(10);
		
		JPanel panelNavegacao = new JPanel();
		panelNavegacao.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelNavegacao.setBounds(62, 489, 1061, 56);
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
		btnPrimeiro.setIcon(new ImageIcon(TabelaEditora.class.getResource(ProcessamentoDados.BUTTON_ICON_PRIMEIRO)));
		btnPrimeiro.setBounds(10, 11, 89, 23);
		panelPaginacao.add(btnPrimeiro);
		
		btnAnterior = new JButton("");
		btnAnterior.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAnterior.setToolTipText("Anterior");
		btnAnterior.setIcon(new ImageIcon(TabelaEditora.class.getResource(ProcessamentoDados.BUTTON_ICON_ANTERIOR)));
		btnAnterior.setBounds(109, 11, 89, 23);
		panelPaginacao.add(btnAnterior);
		
		btnProximo = new JButton("");
		btnProximo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnProximo.setToolTipText("Pr\u00F3ximo");
		btnProximo.setIcon(new ImageIcon(TabelaEditora.class.getResource(ProcessamentoDados.BUTTON_ICON_PROXIMO)));
		btnProximo.setBounds(208, 11, 89, 23);
		panelPaginacao.add(btnProximo);
		
		btnUltimo = new JButton("");
		btnUltimo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUltimo.setToolTipText("\u00DAltimo");
		btnUltimo.setIcon(new ImageIcon(TabelaEditora.class.getResource(ProcessamentoDados.BUTTON_ICON_ULTIMO)));
		btnUltimo.setBounds(307, 11, 89, 23);
		panelPaginacao.add(btnUltimo);
		
		lblShowPaginaAtual = new JLabel("P\u00E1gina Atual:");
		lblShowPaginaAtual.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblShowPaginaAtual.setHorizontalAlignment(SwingConstants.RIGHT);
		lblShowPaginaAtual.setBounds(510, 22, 88, 14);
		panelNavegacao.add(lblShowPaginaAtual);
		
		lblPaginaAtual = new JLabel("10");
		lblPaginaAtual.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPaginaAtual.setBounds(608, 22, 46, 14);
		panelNavegacao.add(lblPaginaAtual);
		
		lblShowTotalPaginas = new JLabel("Total de P\u00E1ginas:");
		lblShowTotalPaginas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblShowTotalPaginas.setHorizontalAlignment(SwingConstants.RIGHT);
		lblShowTotalPaginas.setBounds(615, 22, 116, 14);
		panelNavegacao.add(lblShowTotalPaginas);
		
		lblTotalPaginas = new JLabel("100");
		lblTotalPaginas.setFont(new Font("Tahoma", Font.BOLD, 11));
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
		
		JPanel panelBotoes = new JPanel();
		panelBotoes.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelBotoes.setBounds(141, 580, 622, 45);
		getContentPane().add(panelBotoes);
		panelBotoes.setLayout(null);
		
		btnIncluir = new JButton("Inclus\u00E3o");
		btnIncluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnIncluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnIncluir.setMnemonic(KeyEvent.VK_I);
		btnIncluir.setToolTipText("Cadastro de um novo registro");
		btnIncluir.setIcon(new ImageIcon(TabelaEditora.class.getResource(ProcessamentoDados.BUTTON_ICON_INCLUIR)));
		btnIncluir.setBounds(10, 11, 114, 23);
		panelBotoes.add(btnIncluir);
		
		btnAlterar = new JButton("Altera\u00E7\u00E3o");
		btnAlterar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAlterar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlterar.setToolTipText("Alterar registro cadastrado");
		btnAlterar.setMnemonic(KeyEvent.VK_A);
		btnAlterar.setIcon(new ImageIcon(TabelaEditora.class.getResource(ProcessamentoDados.BUTTON_ICON_ALTERAR)));
		btnAlterar.setBounds(134, 11, 114, 23);
		panelBotoes.add(btnAlterar);
		
		btnExcluir = new JButton("Exclus\u00E3o");
		btnExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnExcluir.setMnemonic(KeyEvent.VK_E);
		btnExcluir.setToolTipText("Excluir registro cadastrado");
		btnExcluir.setIcon(new ImageIcon(TabelaEditora.class.getResource(ProcessamentoDados.BUTTON_ICON_EXCLUIR)));
		btnExcluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnExcluir.setBounds(269, 11, 89, 23);
		panelBotoes.add(btnExcluir);
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnConsultar.setMnemonic(KeyEvent.VK_C);
		btnConsultar.setIcon(new ImageIcon(TabelaEditora.class.getResource(ProcessamentoDados.BUTTON_ICON_CONSULTAR)));
		btnConsultar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnConsultar.setBounds(370, 11, 89, 23);
		panelBotoes.add(btnConsultar);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFechar.setToolTipText("Fechar programa ");
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.setIcon(new ImageIcon(TabelaEditora.class.getResource(ProcessamentoDados.BUTTON_ICON_FECHAR)));
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setBounds(469, 11, 89, 23);
		panelBotoes.add(btnFechar);
		
	}
}
