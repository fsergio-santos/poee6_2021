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
import com.projeto.view.autor.TabelaAutorModel;

public class BuscarAutorPorLivro extends JDialog {

	private static final long serialVersionUID = 8200661408680044468L;
	
	private final JPanel contentPanel = new JPanel();
	private JLabel lblPesquisaAutorPorLivro;
	private JTextField textFieldBuscaLivro;
	private JScrollPane scrollPane;
	private JTable tabelaAutor;
    private JButton btnFechar;
    
	private TabelaAutorModel tabelaAutorModel;
	private TableRowSorter<TabelaAutorModel> sortTabelaAutor;
	private List<Autor> listaAutor;
	
    private Livro livro;

	private JPanel buttonPane;
	private JPanel panel;
	
	
	public BuscarAutorPorLivro(JFrame frame, boolean modal, Livro livro) {
		super(frame, modal);
		this.livro = livro;
		initComponents();
		createEvents();
		iniciarDados();
		
	}
	
	private void iniciarDados(){
		listaAutor = new ArrayList<Autor>();
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
		lblPesquisaAutorPorLivro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_P) {
					textFieldBuscaLivro.requestFocus();
				}
			}
		});

    }
	
	private void initComponents() {
		setTitle("Busca Autor por Livro");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1424, 551);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AutorLivroFrame.class.getResource(ProcessamentoDados.SHOW_ICON_MENU_SEARCH_AUTOR)));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		scrollPane.setBounds(18, 71, 1377, 384);
	
		buttonPane = new JPanel();
		buttonPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		buttonPane.setBounds(18, 463, 1377, 47);
				
		buttonPane.setLayout(null);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setIcon(new ImageIcon(BuscarAutorPorLivro.class.getResource(ProcessamentoDados.BUTTON_ICON_FECHAR)));
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
		
		tabelaAutor.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	
		scrollPane.setViewportView(tabelaAutor);
		contentPanel.setLayout(null);
		contentPanel.add(buttonPane);
		contentPanel.add(scrollPane);
		
		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(19, 16, 1376, 44);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		lblPesquisaAutorPorLivro = new JLabel("Livro:");
		lblPesquisaAutorPorLivro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesquisaAutorPorLivro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPesquisaAutorPorLivro.setBounds(14, 15, 129, 14);
		panel.add(lblPesquisaAutorPorLivro);
		lblPesquisaAutorPorLivro.setDisplayedMnemonic(KeyEvent.VK_P);
				
		textFieldBuscaLivro = new JTextField();
		textFieldBuscaLivro.setEditable(false);
		textFieldBuscaLivro.setBounds(146, 12, 1183, 20);
		panel.add(textFieldBuscaLivro);
		textFieldBuscaLivro.setColumns(15);
		lblPesquisaAutorPorLivro.setLabelFor(textFieldBuscaLivro);

        textFieldBuscaLivro.setText(livro.getNome());
	}

	
	private List<Autor> carregarListaAutor() {
		AutorLivroService autorLivroService = new AutorLivroService();
		listaAutor = autorLivroService.carregaListaAutorPorLivro(livro.getId());
		return listaAutor;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	

	

}


