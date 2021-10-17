package com.projeto.view.autorlivro;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableRowSorter;

import com.projeto.model.model.Autor;
import com.projeto.model.model.Livro;
import com.projeto.model.service.AutorLivroService;
import com.projeto.util.ProcessamentoDados;
import com.projeto.view.livro.TabelaLivroModel;

public class BuscarLivroPorAutor extends JDialog {

	private static final long serialVersionUID = 8200661408680044468L;
	
	private final JPanel painelPrincipal = new JPanel();
	private JLabel lblPesquisaLivro;
	private JTextField textFieldNomeAutor;
	private JScrollPane scrollPane;
	private JTable tabelaLivro;
    private JButton btnFechar;
    
	private TabelaLivroModel tabelaLivroModel;
	private TableRowSorter<TabelaLivroModel> sortTabelaLivro;
	private List<Livro> listaLivro;
	
	private Autor autor;
	
	private JPanel buttonPane;
	private JPanel panelNomeAutor;
	
	
	public BuscarLivroPorAutor(JFrame frame, boolean modal, Autor autor) {
		super(frame, modal);
		this.autor = autor;
		initComponents();
		createEvents();
		iniciarDados();
		
	}
	
	private void iniciarDados(){
		listaLivro = new ArrayList<Livro>();
	}
	
    private void createEvents() {
		btnFechar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				dispose();
			}
		});
		btnFechar.setMnemonic(KeyEvent.VK_A);
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		

    }
	
	private void initComponents() {
		setTitle("Buscar Livro por Autor");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1150, 449);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AutorLivroFrame.class.getResource(ProcessamentoDados.SHOW_ICON_MENU_SEARCH_BOOK)));

		getContentPane().setLayout(new BorderLayout());
		painelPrincipal.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(painelPrincipal, BorderLayout.CENTER);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		scrollPane.setBounds(18, 71, 1110, 283);
	
		buttonPane = new JPanel();
		buttonPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		buttonPane.setBounds(18, 355, 1110, 47);
				
		buttonPane.setLayout(null);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setIcon(new ImageIcon(BuscarLivroPorAutor.class.getResource(ProcessamentoDados.BUTTON_ICON_FECHAR)));
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
		
		tabelaLivro.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
   		scrollPane.setViewportView(tabelaLivro);

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		painelPrincipal.setLayout(null);
		painelPrincipal.add(buttonPane);
		painelPrincipal.add(scrollPane);
		
		panelNomeAutor = new JPanel();
		panelNomeAutor.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelNomeAutor.setBounds(19, 16, 1109, 44);
		painelPrincipal.add(panelNomeAutor);
		panelNomeAutor.setLayout(null);
		
		lblPesquisaLivro = new JLabel("Autor:");
		lblPesquisaLivro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesquisaLivro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPesquisaLivro.setBounds(14, 15, 129, 14);
		panelNomeAutor.add(lblPesquisaLivro);
		lblPesquisaLivro.setDisplayedMnemonic(KeyEvent.VK_P);
				
		textFieldNomeAutor = new JTextField();
		textFieldNomeAutor.setEditable(false);
		textFieldNomeAutor.setBounds(146, 12, 939, 20);
		panelNomeAutor.add(textFieldNomeAutor);
		textFieldNomeAutor.setColumns(15);
		
		textFieldNomeAutor.setText(autor.getNome());
		
		lblPesquisaLivro.setLabelFor(textFieldNomeAutor);
		
		

	}

	private List<Livro> carregarListaLivro() {
		AutorLivroService autorLivroService = new AutorLivroService();
		listaLivro = autorLivroService.carregaListaLivroPorAutor(autor.getId());
		return listaLivro;
	}

	
	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}


	
	
}


