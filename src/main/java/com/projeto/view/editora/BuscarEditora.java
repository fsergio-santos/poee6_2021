package com.projeto.view.editora;

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

import com.projeto.model.model.Editora;
import com.projeto.model.service.EditoraService;
import com.projeto.util.ProcessamentoDados;

import javax.swing.ImageIcon;

public class BuscarEditora extends JDialog {

	private static final long serialVersionUID = 8200661408680044468L;
	
	private static final int CODIGO     = 0;
	private static final int NOME       = 1;
	private static final int RUA        = 2;
	private static final int BAIRRO     = 3;
	private static final int CIDADE     = 4;
	private static final int CEP        = 5;

	private final JPanel contentPanel = new JPanel();
	private JLabel lblPesquisaEditora;
	private JTextField textFieldBuscaEditora;
	private JScrollPane scrollPane;
	private JTable tabelaEditora;
    private JButton okButton;
    private JButton btnFechar;
    
	private TabelaEditoraModel tabelaEditoraModel;
	private TableRowSorter<TabelaEditoraModel> sortTabelaEditora;
	private List<Editora> listaEditora;
	private List<RowSorter.SortKey> sortKeys;
	
    private boolean isConfirmado; 
	
	private Editora editora;

	private JButton btnIncluir;
	
	private int row=0;
	private JPanel buttonPane;
	private JPanel panel;
	private JPanel panelCadastrar;
	
	
	public BuscarEditora(JFrame frame, boolean modal) {
		super(frame, modal);
		initComponents();
		createEvents();
		iniciarDados();
		
	}
	
	private void iniciarDados(){
		listaEditora = new ArrayList<Editora>();
	}
	
    private void createEvents() {
    	okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionaEditora();
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
		tabelaEditora.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selecionaEditora(e);
			}
		});
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirEditora();
			}
		});
		btnIncluir.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_C ) {
					inserirEditora();
				}
				
			}
		});
		lblPesquisaEditora.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_P) {
					textFieldBuscaEditora.requestFocus();
				}
			}
		});
		textFieldBuscaEditora.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				filtraNomeEditora();
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtraNomeEditora();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				filtraNomeEditora();
			}
		});



    }
	
	private void initComponents() {
		setTitle("Busca Editora");
		setResizable(false);
		setConfirmado(ProcessamentoDados.FALSO);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1095, 452);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		scrollPane.setBounds(18, 71, 1043, 283);
	
		buttonPane = new JPanel();
		buttonPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		buttonPane.setBounds(18, 355, 1043, 47);
		
		okButton = new JButton("OK");
		okButton.setIcon(new ImageIcon(BuscarEditora.class.getResource(ProcessamentoDados.BUTTON_ICON_OK)));
		okButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		okButton.setBounds(818, 7, 118, 31);
		okButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		okButton.setMnemonic(KeyEvent.VK_O);
				
		buttonPane.setLayout(null);
		
		okButton.setActionCommand("OK");
		
		buttonPane.add(okButton);
		
		getRootPane().setDefaultButton(okButton);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setIcon(new ImageIcon(BuscarEditora.class.getResource(ProcessamentoDados.BUTTON_ICON_FECHAR)));
		btnFechar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFechar.setBounds(938, 6, 105, 32);
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
	    btnFechar.setActionCommand("Cancel");
	
	    buttonPane.add(btnFechar);
	
		tabelaEditoraModel = new TabelaEditoraModel();
	    listaEditora = carregarListaEditora();
		tabelaEditoraModel.setListaEditora(listaEditora);
		tabelaEditora = new JTable();
		tabelaEditora.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
	
		tabelaEditora.setModel(tabelaEditoraModel);
		tabelaEditora.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaEditora.setFillsViewportHeight(true);
		sortTabelaEditora = new TableRowSorter<TabelaEditoraModel>(tabelaEditoraModel);
		tabelaEditora.setRowSorter(sortTabelaEditora);
		
		sortKeys = new ArrayList<RowSorter.SortKey>();
		sortKeys.add(new RowSorter.SortKey(CODIGO, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(NOME, SortOrder.ASCENDING));	
		
		tabelaEditora.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		tabelaEditora.getColumnModel().getColumn(CODIGO).setPreferredWidth(11);
		tabelaEditora.getColumnModel().getColumn(NOME).setPreferredWidth(100);
		tabelaEditora.getColumnModel().getColumn(RUA).setPreferredWidth(100);
		tabelaEditora.getColumnModel().getColumn(BAIRRO).setPreferredWidth(100);
		tabelaEditora.getColumnModel().getColumn(CIDADE).setPreferredWidth(100);
		tabelaEditora.getColumnModel().getColumn(CEP).setPreferredWidth(100);
		
   		scrollPane.setViewportView(tabelaEditora);

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPanel.setLayout(null);
		contentPanel.add(buttonPane);
		contentPanel.add(scrollPane);
		
		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(19, 16, 781, 44);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		lblPesquisaEditora = new JLabel("Pesquisar Editora:");
		lblPesquisaEditora.setBounds(14, 15, 129, 14);
		panel.add(lblPesquisaEditora);
		lblPesquisaEditora.setDisplayedMnemonic(KeyEvent.VK_P);
				
		textFieldBuscaEditora = new JTextField();
		textFieldBuscaEditora.setBounds(146, 12, 608, 20);
		panel.add(textFieldBuscaEditora);
		textFieldBuscaEditora.setColumns(15);
		lblPesquisaEditora.setLabelFor(textFieldBuscaEditora);
		
		panelCadastrar = new JPanel();
		panelCadastrar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelCadastrar.setBounds(810, 16, 255, 44);
		contentPanel.add(panelCadastrar);
		panelCadastrar.setLayout(null);
		
		btnIncluir = new JButton("Cadastrar Editora");
		btnIncluir.setBounds(30, 6, 174, 31);
		panelCadastrar.add(btnIncluir);
		
		btnIncluir.setIcon(new ImageIcon(BuscarEditora.class.getResource(ProcessamentoDados.BUTTON_ICON_INCLUIR)));
		btnIncluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnIncluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		btnIncluir.setMnemonic(KeyEvent.VK_C);

	}



	protected void inserirEditora() {
		EditoraFrame editoraFrame = new EditoraFrame(new JFrame(), true, ProcessamentoDados.INCLUIR_REGISTRO, tabelaEditora, tabelaEditoraModel, 0);
		editoraFrame.setLocationRelativeTo(null);
		editoraFrame.setVisible(true);
	    tabelaEditoraModel.fireTableDataChanged();	
	}

	protected void selecionaEditora(MouseEvent e) {
		row = tabelaEditora.getSelectedRow();
		
		 if ( tabelaEditora.getRowSorter() != null ) {
			row =  tabelaEditora.getRowSorter().convertRowIndexToModel(row);
		 }
		
	}

	protected void selecionaEditora() {
		if ( tabelaEditora.getSelectedRow() != -1 && 
			 tabelaEditora.getSelectedRow() < tabelaEditoraModel.getRowCount() ) {
			 editora = new Editora();
			 //setCodigoEditora(Integer.valueOf(tabelaEditora.getValueAt(tabelaEditora.getSelectedRow(), CODIGO).toString()));
			 //setNomeEditora(tabelaEditora.getValueAt(tabelaEditora.getSelectedRow(), NOME).toString());
			 //row = tabelaEditora.getSelectedRow();
			 //if ( tabelaEditora.getRowSorter() != null ) {
			 //	 row =  tabelaEditora.getRowSorter().convertRowIndexToModel(row);
			 //}
			 setConfirmado(ProcessamentoDados.VERDADEIRO);
			 editora = tabelaEditoraModel.getEditora(row);
			 dispose();
		} else {
			setConfirmado(ProcessamentoDados.FALSO);
		}
		
	}

	private List<Editora> carregarListaEditora() {
		EditoraService editoraService = new EditoraService();
		listaEditora = editoraService.carregaListaEditora();
		return listaEditora;
	}
	
	
	private void filtraNomeEditora() {
		RowFilter<TabelaEditoraModel, Object> rowFilter = null;
		String filter = textFieldBuscaEditora.getText();
		try {
			rowFilter = RowFilter.regexFilter(filter);
		} catch(PatternSyntaxException e) {
			return;
		}
		sortTabelaEditora.setRowFilter(rowFilter);
		
	}


	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	public boolean isConfirmado() {
		return isConfirmado;
	}

	public void setConfirmado(boolean isConfirmado) {
		this.isConfirmado = isConfirmado;
	}


	
	
}


