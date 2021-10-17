package com.projeto.view.livro;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import com.projeto.model.model.Livro;
import com.projeto.model.service.LivroService;
import com.projeto.util.ProcessamentoDados;

import javax.swing.ImageIcon;

public class BuscarLivro extends JDialog {

	private static final long serialVersionUID = 8200661408680044468L;
	
	private static final int CODIGO     = 0;
	private static final int NOME       = 1;
	
	private final JPanel contentPanel = new JPanel();
	private JLabel lblPesquisaLivro;
	private JTextField textFieldBuscaLivro;
	private JScrollPane scrollPane;
	private JTable tabelaLivro;
    private JButton okButton;
    private JButton btnFechar;
    
	private TabelaLivroModel tabelaLivroModel;
	private TableRowSorter<TabelaLivroModel> sortTabelaLivro;
	private List<Livro> listaLivro;
	private List<RowSorter.SortKey> sortKeys;
	
    private boolean isConfirmado; 
	
	private Livro livro;

	private JButton btnInserir;
	
	private int row=0;
	private JPanel buttonPane;
	private JPanel panel;
	private JPanel panelCadastrar;
	
	
	public BuscarLivro(JFrame frame, boolean modal) {
		super(frame, modal);
		initComponents();
		createEvents();
		iniciarDados();
		
	}
	
	private void iniciarDados(){
		listaLivro = new ArrayList<Livro>();
	}
	
    private void createEvents() {
    	okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionaLivro();
			}
		});
		btnFechar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				setConfirmado(false);
				dispose();
			}
		});
		btnFechar.setMnemonic(KeyEvent.VK_A);
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setConfirmado(false);
				dispose();
			}
		});
		tabelaLivro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selecionaLivro(e);
			}
		});
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirLivro();
			}
		});
		btnInserir.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_C ) {
					inserirLivro();
				}
				
			}
		});
		lblPesquisaLivro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_P) {
					textFieldBuscaLivro.requestFocus();
				}
			}
		});
		textFieldBuscaLivro.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				filtraNomeLivro();
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtraNomeLivro();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				filtraNomeLivro();
			}
		});



    }
	
	private void initComponents() {
		setTitle("Busca Livro");
		setResizable(false);
		setConfirmado(ProcessamentoDados.FALSO);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1150, 449);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		scrollPane.setBounds(18, 71, 1110, 283);
	
		buttonPane = new JPanel();
		buttonPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		buttonPane.setBounds(18, 355, 1110, 47);
		
		okButton = new JButton("OK");
		okButton.setIcon(new ImageIcon(BuscarLivro.class.getResource(ProcessamentoDados.BUTTON_ICON_OK)));
		okButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		okButton.setBounds(864, 7, 118, 31);
		okButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		okButton.setMnemonic(KeyEvent.VK_O);
				
		buttonPane.setLayout(null);
		
		okButton.setActionCommand("OK");
		
		buttonPane.add(okButton);
		
		getRootPane().setDefaultButton(okButton);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setIcon(new ImageIcon(BuscarLivro.class.getResource(ProcessamentoDados.BUTTON_ICON_FECHAR)));
		btnFechar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFechar.setBounds(993, 7, 105, 31);
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
	    btnFechar.setActionCommand("Cancel");
	
	    buttonPane.add(btnFechar);
	
		tabelaLivroModel = new TabelaLivroModel();
	    listaLivro = carregarListaLivro();
		tabelaLivroModel.setListaLivro(listaLivro);
		tabelaLivro = new JTable();
		tabelaLivro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
	
		tabelaLivro.setModel(tabelaLivroModel);
		tabelaLivro.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaLivro.setFillsViewportHeight(true);
		sortTabelaLivro = new TableRowSorter<TabelaLivroModel>(tabelaLivroModel);
		tabelaLivro.setRowSorter(sortTabelaLivro);
		
		sortKeys = new ArrayList<RowSorter.SortKey>();
		sortKeys.add(new RowSorter.SortKey(CODIGO, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(NOME, SortOrder.ASCENDING));	
		
		tabelaLivro.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
   		scrollPane.setViewportView(tabelaLivro);

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentPanel.setLayout(null);
		contentPanel.add(buttonPane);
		contentPanel.add(scrollPane);
		
		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(19, 16, 781, 44);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		lblPesquisaLivro = new JLabel("Pesquisar Livro:");
		lblPesquisaLivro.setBounds(14, 15, 129, 14);
		panel.add(lblPesquisaLivro);
		lblPesquisaLivro.setDisplayedMnemonic(KeyEvent.VK_P);
				
		textFieldBuscaLivro = new JTextField();
		textFieldBuscaLivro.setBounds(146, 12, 608, 20);
		panel.add(textFieldBuscaLivro);
		textFieldBuscaLivro.setColumns(15);
		lblPesquisaLivro.setLabelFor(textFieldBuscaLivro);
		
		panelCadastrar = new JPanel();
		panelCadastrar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelCadastrar.setBounds(810, 16, 310, 44);
		contentPanel.add(panelCadastrar);
		panelCadastrar.setLayout(null);
		
		btnInserir = new JButton("Cadastrar Livro");
		btnInserir.setBounds(68, 8, 174, 28);
		panelCadastrar.add(btnInserir);
		
		btnInserir.setIcon(new ImageIcon(BuscarLivro.class.getResource(ProcessamentoDados.BUTTON_ICON_INCLUIR)));
		btnInserir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnInserir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		btnInserir.setMnemonic(KeyEvent.VK_C);

	}

	protected void inserirLivro() {
		LivroFrame livroFrame = new LivroFrame(new JFrame(), true, ProcessamentoDados.INCLUIR_REGISTRO, tabelaLivro, tabelaLivroModel, 0);
		livroFrame.setLocationRelativeTo(null);
		livroFrame.setVisible(true);
	    tabelaLivroModel.fireTableDataChanged();	
	}

	protected void selecionaLivro(MouseEvent e) {
		row = tabelaLivro.getSelectedRow();
		
		 if ( tabelaLivro.getRowSorter() != null ) {
			row =  tabelaLivro.getRowSorter().convertRowIndexToModel(row);
		 }
		
	}

	protected void selecionaLivro() {
		if ( tabelaLivro.getSelectedRow() != -1 && 
			 tabelaLivro.getSelectedRow() < tabelaLivroModel.getRowCount() ) {
			 livro = new Livro();
			 setConfirmado(ProcessamentoDados.VERDADEIRO);
			 livro = tabelaLivroModel.getLivro(row);
			 dispose();
		} else {
			setConfirmado(ProcessamentoDados.FALSO);
		}
		
	}

	private List<Livro> carregarListaLivro() {
		LivroService livroService = new LivroService();
		listaLivro = livroService.carregaListaLivro();
		return listaLivro;
	}
	
	
	private void filtraNomeLivro() {
		RowFilter<TabelaLivroModel, Object> rowFilter = null;
		String filter = textFieldBuscaLivro.getText();
		try {
			rowFilter = RowFilter.regexFilter(filter);
		} catch(PatternSyntaxException e) {
			return;
		}
		sortTabelaLivro.setRowFilter(rowFilter);
		
	}


	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public boolean isConfirmado() {
		return isConfirmado;
	}

	public void setConfirmado(boolean isConfirmado) {
		this.isConfirmado = isConfirmado;
	}


	
	
}


