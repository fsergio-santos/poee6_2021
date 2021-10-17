package com.projeto.view.autor;

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

import com.projeto.model.model.Autor;
import com.projeto.model.service.AutorService;
import com.projeto.util.ProcessamentoDados;

import javax.swing.ImageIcon;

public class BuscarAutor extends JDialog {

	private static final long serialVersionUID = 8200661408680044468L;
	
	private static final int CODIGO     = 0;
	private static final int NOME       = 1;

	private final JPanel contentPanel = new JPanel();
	private JLabel lblPesquisaAutor;
	private JTextField textFieldBuscaAutor;
	private JScrollPane scrollPane;
	private JTable tabelaAutor;
    private JButton okButton;
    private JButton btnFechar;
    
	private TabelaAutorModel tabelaAutorModel;
	private TableRowSorter<TabelaAutorModel> sortTabelaAutor;
	private List<Autor> listaAutor;
	private List<RowSorter.SortKey> sortKeys;
	
    private boolean isConfirmado; 
	
	private Autor autor;

	private JButton btnIncluir;
	
	private int row=0;
	private JPanel buttonPane;
	private JPanel panel;
	private JPanel panelCadastrar;
	
	
	public BuscarAutor(JFrame frame, boolean modal) {
		super(frame, modal);
		initComponents();
		createEvents();
		iniciarDados();
		
	}
	
	private void iniciarDados(){
		listaAutor = new ArrayList<Autor>();
	}
	
    private void createEvents() {
    	okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionaAutor();
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
		tabelaAutor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selecionaAutor(e);
			}
		});
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirAutor();
			}
		});
		btnIncluir.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_C ) {
					inserirAutor();
				}
				
			}
		});
		lblPesquisaAutor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_P) {
					textFieldBuscaAutor.requestFocus();
				}
			}
		});
		textFieldBuscaAutor.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				filtraNomeAutor();
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtraNomeAutor();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				filtraNomeAutor();
			}
		});



    }
	
	private void initComponents() {
		setTitle("Busca Autor");
		setResizable(false);
		setConfirmado(ProcessamentoDados.FALSO);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1424, 551);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		scrollPane.setBounds(18, 71, 1377, 384);
	
		buttonPane = new JPanel();
		buttonPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		buttonPane.setBounds(18, 463, 1377, 47);
		
		okButton = new JButton("OK");
		okButton.setIcon(new ImageIcon(BuscarAutor.class.getResource(ProcessamentoDados.BUTTON_ICON_OK)));
		okButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		okButton.setBounds(1120, 9, 118, 31);
		okButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		okButton.setMnemonic(KeyEvent.VK_O);
				
		buttonPane.setLayout(null);
		
		okButton.setActionCommand("OK");
		
		buttonPane.add(okButton);
		
		getRootPane().setDefaultButton(okButton);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setIcon(new ImageIcon(BuscarAutor.class.getResource(ProcessamentoDados.BUTTON_ICON_FECHAR)));
		btnFechar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFechar.setBounds(1249, 9, 105, 31);
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
	    btnFechar.setActionCommand("Cancel");
	
	    buttonPane.add(btnFechar);
	
		tabelaAutorModel = new TabelaAutorModel();
	    listaAutor = carregarListaAutor();
		tabelaAutorModel.setListaAutor(listaAutor);
		tabelaAutor = new JTable();
		tabelaAutor.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
	
		tabelaAutor.setModel(tabelaAutorModel);
		tabelaAutor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaAutor.setFillsViewportHeight(true);
		sortTabelaAutor = new TableRowSorter<TabelaAutorModel>(tabelaAutorModel);
		tabelaAutor.setRowSorter(sortTabelaAutor);
		
		sortKeys = new ArrayList<RowSorter.SortKey>();
		sortKeys.add(new RowSorter.SortKey(CODIGO, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(NOME, SortOrder.ASCENDING));	
		
		tabelaAutor.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		tabelaAutor.getColumnModel().getColumn(CODIGO).setPreferredWidth(11);
		tabelaAutor.getColumnModel().getColumn(NOME).setPreferredWidth(100);
		
   		scrollPane.setViewportView(tabelaAutor);
		contentPanel.setLayout(null);
		contentPanel.add(buttonPane);
		contentPanel.add(scrollPane);
		
		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(19, 16, 1103, 44);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		lblPesquisaAutor = new JLabel("Pesquisar Autor:");
		lblPesquisaAutor.setBounds(14, 15, 129, 14);
		panel.add(lblPesquisaAutor);
		lblPesquisaAutor.setDisplayedMnemonic(KeyEvent.VK_P);
				
		textFieldBuscaAutor = new JTextField();
		textFieldBuscaAutor.setBounds(146, 12, 928, 20);
		panel.add(textFieldBuscaAutor);
		textFieldBuscaAutor.setColumns(15);
		lblPesquisaAutor.setLabelFor(textFieldBuscaAutor);
		
		panelCadastrar = new JPanel();
		panelCadastrar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelCadastrar.setBounds(1132, 16, 263, 44);
		contentPanel.add(panelCadastrar);
		panelCadastrar.setLayout(null);
		
		btnIncluir = new JButton("Cadastrar Autor");
		btnIncluir.setBounds(36, 6, 174, 31);
		panelCadastrar.add(btnIncluir);
		
		btnIncluir.setIcon(new ImageIcon(BuscarAutor.class.getResource(ProcessamentoDados.BUTTON_ICON_INCLUIR)));
		btnIncluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnIncluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		btnIncluir.setMnemonic(KeyEvent.VK_C);

	}



	protected void inserirAutor() {
		AutorFrame autorFrame = new AutorFrame(new JFrame(), true, ProcessamentoDados.INCLUIR_REGISTRO, tabelaAutor, tabelaAutorModel, 0);
		autorFrame.setLocationRelativeTo(null);
		autorFrame.setVisible(true);
	    tabelaAutorModel.fireTableDataChanged();	
	}

	protected void selecionaAutor(MouseEvent e) {
		row = tabelaAutor.getSelectedRow();
		
		 if ( tabelaAutor.getRowSorter() != null ) {
			row =  tabelaAutor.getRowSorter().convertRowIndexToModel(row);
		 }
		
	}

	protected void selecionaAutor() {
		if ( tabelaAutor.getSelectedRow() != -1 && 
			 tabelaAutor.getSelectedRow() < tabelaAutorModel.getRowCount() ) {
			 autor = new Autor();
	   	     setConfirmado(ProcessamentoDados.VERDADEIRO);
			 autor = tabelaAutorModel.getAutor(row);
			 dispose();
		} else {
			setConfirmado(ProcessamentoDados.FALSO);
		}
		
	}

	private List<Autor> carregarListaAutor() {
		AutorService autorService = new AutorService();
		listaAutor = autorService.carregaListaAutor();
		return listaAutor;
	}
	
	
	private void filtraNomeAutor() {
		RowFilter<TabelaAutorModel, Object> rowFilter = null;
		String filter = textFieldBuscaAutor.getText();
		try {
			rowFilter = RowFilter.regexFilter(filter);
		} catch(PatternSyntaxException e) {
			return;
		}
		sortTabelaAutor.setRowFilter(rowFilter);
		
	}


	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public boolean isConfirmado() {
		return isConfirmado;
	}

	public void setConfirmado(boolean isConfirmado) {
		this.isConfirmado = isConfirmado;
	}
}


