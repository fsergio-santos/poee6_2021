package com.projeto.view.autorlivro;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.swing.DebugGraphics;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.projeto.model.model.Autor;
import com.projeto.model.model.AutorLivro;
import com.projeto.model.model.AutorLivroPK;
import com.projeto.model.model.Livro;
import com.projeto.model.service.AutorLivroService;
import com.projeto.model.service.AutorService;
import com.projeto.model.service.LivroService;
import com.projeto.util.ProcessamentoDados;
import com.projeto.view.autor.BuscarAutor;
import com.projeto.view.livro.BuscarLivro;
import com.toedter.calendar.JDateChooser;

public class AutorLivroFrame extends JDialog {


	private static final long serialVersionUID = 6717485292269251800L;
	
	private JPanel contentPane;
	private JTextField textFieldNomeAutor;
	private JButton btnFechar;
	private JButton btnIncluir;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JTextField textFieldNomeLivro;
	
	private AutorLivro autorLivro;
	private Autor autor;
	private Livro livro;
	private AutorLivroService autorLivroService; 
	private JDateChooser dateChooserAutorLivro;
	
	private int acao=0;
	private AutorLivroPK autorLivroPK = null;
	private Integer codigoAutor = 0; 
	private Integer codigoLivro = 0;
	private int linha = 0;
	private JTable tabelaAutorLivro;
	private TabelaAutorLivroModel tabelaAutorLivroModel;
	private JLabel lblShowErroNomeAutor;
	private JLabel lblShowErroNomeLivro;
	private JLabel lblData;
	private JLabel lblShowErroData;
	private JButton btnBuscaAutor;
	private JButton btnBuscaLivro;
	private JList<String> listAutor;
	private DefaultListModel<String> listModelAutor;
    private Integer autorSelecionado[] = {};
    private JList<String> listLivro;
    private DefaultListModel<String> listModelLivro;
    private Integer livroSelecionado[] = {};
	
	public AutorLivroFrame(JFrame frame, boolean modal, int acao, JTable tabelaAutorLivro, TabelaAutorLivroModel tabelaAutorLivroModel, int linha  ) {
		super(frame, modal);
		this.acao = acao;
		this.tabelaAutorLivro = tabelaAutorLivro;
		this.tabelaAutorLivroModel = tabelaAutorLivroModel;
		this.linha = linha;
		autorLivroService = new AutorLivroService();
		autorLivro = new AutorLivro();
	
		initComponents();
	    createEvents();
	    configuraAcao();
	
	}



	private void configuraAcao() {
	  if (this.acao == ProcessamentoDados.INCLUIR_REGISTRO ) {
		  btnIncluir.setEnabled(ProcessamentoDados.VERDADEIRO);
		  btnAlterar.setEnabled(ProcessamentoDados.FALSO);
		  btnExcluir.setEnabled(ProcessamentoDados.FALSO);
	  }
	  if (this.acao == ProcessamentoDados.ALTERAR_REGISTRO ) {
		  buscarAutorLivro();
		  btnIncluir.setEnabled(ProcessamentoDados.FALSO);
		  btnAlterar.setEnabled(ProcessamentoDados.VERDADEIRO);
		  btnExcluir.setEnabled(ProcessamentoDados.FALSO);
	  }
	  if (this.acao == ProcessamentoDados.EXCLUIR_REGISTRO ) {
		  buscarAutorLivro();
		  btnIncluir.setEnabled(ProcessamentoDados.FALSO);
		  btnAlterar.setEnabled(ProcessamentoDados.FALSO);
		  btnExcluir.setEnabled(ProcessamentoDados.VERDADEIRO);
	  }
	  if (this.acao == ProcessamentoDados.CONSULTAR_REGISTRO ) {
		  buscarAutorLivro();
		  btnIncluir.setEnabled(ProcessamentoDados.FALSO);
		  btnAlterar.setEnabled(ProcessamentoDados.FALSO);
		  btnExcluir.setEnabled(ProcessamentoDados.FALSO);
	  }

	  
	}


	private void createEvents() {
	    this.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentShown(ComponentEvent e) {
					textFieldNomeAutor.requestFocus();
				}
		});
		textFieldNomeAutor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroNomeAutor.setVisible(ProcessamentoDados.FALSO);
				textFieldNomeLivro.setVisible(ProcessamentoDados.VERDADEIRO);
				dateChooserAutorLivro.setVisible(ProcessamentoDados.VERDADEIRO);
				listAutor.setVisible(ProcessamentoDados.FALSO);
			}
		});
		textFieldNomeAutor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldNomeLivro.setVisible(ProcessamentoDados.VERDADEIRO);
					dateChooserAutorLivro.setVisible(ProcessamentoDados.VERDADEIRO);
					textFieldNomeLivro.requestFocus();
				}
				pesquisaAutor();
			}
		});
		listAutor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ( e.getButton() == MouseEvent.BUTTON1 ) {
					Integer item = listAutor.getSelectedIndex();
					Integer id = autorSelecionado[item];
					autor = new Autor();
					AutorService autorService = new AutorService();
					autor = autorService.findAutorById(id);
					textFieldNomeAutor.setText(autor.getNome());
					listAutor.setVisible(ProcessamentoDados.FALSO);
					textFieldNomeLivro.setVisible(ProcessamentoDados.VERDADEIRO);
					dateChooserAutorLivro.setVisible(ProcessamentoDados.VERDADEIRO);
					textFieldNomeLivro.requestFocus();
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Integer item = listAutor.getSelectedIndex();
				Integer id = autorSelecionado[item];
				autor = new Autor();
				AutorService autorService = new AutorService();
				autor = autorService.findAutorById(id);
				textFieldNomeAutor.setText(autor.getNome());
				listAutor.setVisible(ProcessamentoDados.FALSO);
				textFieldNomeLivro.setVisible(ProcessamentoDados.VERDADEIRO);
				dateChooserAutorLivro.setVisible(ProcessamentoDados.VERDADEIRO);
				textFieldNomeLivro.requestFocus();
			}
		});
		textFieldNomeLivro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB ) {
					dateChooserAutorLivro.setVisible(ProcessamentoDados.VERDADEIRO);
					dateChooserAutorLivro.requestFocus();
				}
				pesquisaLivro();
			}
		});
		textFieldNomeLivro.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroNomeLivro.setVisible(ProcessamentoDados.FALSO);
				dateChooserAutorLivro.setVisible(ProcessamentoDados.VERDADEIRO);
				listLivro.setVisible(ProcessamentoDados.FALSO);
			}
		});
		listLivro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ( e.getButton() == MouseEvent.BUTTON1 ) {
					Integer item = listLivro.getSelectedIndex();
					Integer id = livroSelecionado[item];
					livro = new Livro();
					LivroService livroService = new LivroService();
					livro = livroService.findLivroById(id);
					textFieldNomeLivro.setText(livro.getNome());
					listLivro.setVisible(ProcessamentoDados.FALSO);
					dateChooserAutorLivro.setVisible(ProcessamentoDados.VERDADEIRO);
					dateChooserAutorLivro.requestFocus();
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Integer item = listLivro.getSelectedIndex();
				Integer id = livroSelecionado[item];
				livro = new Livro();
				LivroService livroService = new LivroService();
				livro = livroService.findLivroById(id);
				textFieldNomeLivro.setText(livro.getNome());
				listLivro.setVisible(ProcessamentoDados.FALSO);
				dateChooserAutorLivro.setVisible(ProcessamentoDados.VERDADEIRO);
				dateChooserAutorLivro.requestFocus();
			}
		});
		dateChooserAutorLivro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB ) {
					if (acao == ProcessamentoDados.INCLUIR_REGISTRO) {
						btnIncluir.requestFocus();
					} else if (acao == ProcessamentoDados.ALTERAR_REGISTRO) {
						btnAlterar.requestFocus();
					} else if (acao == ProcessamentoDados.EXCLUIR_REGISTRO) {
						btnExcluir.requestFocus();
					} else if (acao == ProcessamentoDados.CONSULTAR_REGISTRO) {
						btnFechar.requestFocus();
					}
				}	

			}
		});
		dateChooserAutorLivro.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (acao == ProcessamentoDados.INCLUIR_REGISTRO) {
					btnIncluir.requestFocus();
				} else if (acao == ProcessamentoDados.ALTERAR_REGISTRO) {
					btnAlterar.requestFocus();
				} else if (acao == ProcessamentoDados.EXCLUIR_REGISTRO) {
					btnExcluir.requestFocus();
				} else if (acao == ProcessamentoDados.CONSULTAR_REGISTRO) {
					btnFechar.requestFocus();
				}
				lblShowErroData.setVisible(ProcessamentoDados.FALSO);
			}
		});
		btnBuscaLivro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_L) {
					buscarLivro();
				}
			}
		});
		btnBuscaLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarLivro();
			}
		});
		btnBuscaAutor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_A) {
					buscarAutor();
				}
			}
		});
		btnBuscaAutor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarAutor();
			}
		});
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean toReturn = ProcessamentoDados.FALSO;
				toReturn = verificarDigitacao();
				if (toReturn == ProcessamentoDados.FALSO) {
					pegarDadosAutorLivroDaTela();
					incluirAutorLivro();
				}	
			}
		});
		btnIncluir.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_I) {
					boolean toReturn = ProcessamentoDados.FALSO;
					toReturn = verificarDigitacao();
					if (toReturn == ProcessamentoDados.FALSO) {
						pegarDadosAutorLivroDaTela();
						incluirAutorLivro();
					}	
				}
			}
		});
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean toReturn = ProcessamentoDados.FALSO;
				toReturn = verificarDigitacao();
				if (toReturn == ProcessamentoDados.FALSO) {
					pegarDadosAutorLivroDaTela();
					alterarAutorLivro();
				}
					
			}
		});
		btnAlterar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					boolean toReturn = ProcessamentoDados.FALSO;
					toReturn = verificarDigitacao();
					if (toReturn == ProcessamentoDados.FALSO) {
						pegarDadosAutorLivroDaTela();
						alterarAutorLivro();
					}
				}
			}
		});
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirAutorLivro();
			}
		});
		btnExcluir.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_E) {
					excluirAutorLivro();
				}
			}
		});
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
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
		
	}
	
	protected void pesquisaAutor() {
		listModelAutor.removeAllElements();
		listAutor.setVisible(ProcessamentoDados.VERDADEIRO);
		textFieldNomeLivro.setVisible(ProcessamentoDados.FALSO);
		dateChooserAutorLivro.setVisible(ProcessamentoDados.FALSO);
		AutorService autorService = new AutorService();
		List<Autor> listaAutor = autorService.carregarListaAutor(textFieldNomeAutor.getText());
		autorSelecionado = new Integer[listaAutor.size()];
		for ( int i = 0 ; i < listaAutor.size(); i++ ) {
			listModelAutor.addElement(listaAutor.get(i).getNome());
			autorSelecionado[i] = listaAutor.get(i).getId();
		}
		listAutor.setModel(listModelAutor);
	}
	
	protected void pesquisaLivro() {
		listModelLivro.removeAllElements();
		listLivro.setVisible(ProcessamentoDados.VERDADEIRO);
		dateChooserAutorLivro.setVisible(ProcessamentoDados.FALSO);
		LivroService livroService = new LivroService();
		List<Livro> listaLivro = livroService.carregarListaLivro(textFieldNomeLivro.getText());
		livroSelecionado = new Integer[listaLivro.size()];
		for ( int i = 0 ; i < listaLivro.size(); i++ ) {
			listModelLivro.addElement(listaLivro.get(i).getNome());
			livroSelecionado[i] = listaLivro.get(i).getId();
		}
		listLivro.setModel(listModelLivro);
	}



	protected void buscarAutor() {
		BuscarAutor buscarAutor = new BuscarAutor(new JFrame(), ProcessamentoDados.VERDADEIRO);
		buscarAutor.setLocationRelativeTo(null);
		buscarAutor.setVisible(ProcessamentoDados.VERDADEIRO);
		if (buscarAutor.isConfirmado()) {
			autor = new Autor();
			autor = buscarAutor.getAutor();
			textFieldNomeAutor.setText(autor.getNome());
		}
	}
	
	protected void buscarLivro() {
		BuscarLivro buscarLivro = new BuscarLivro(new JFrame(), ProcessamentoDados.VERDADEIRO);
		buscarLivro.setLocationRelativeTo(null);
		buscarLivro.setVisible(ProcessamentoDados.VERDADEIRO);
		if (buscarLivro.isConfirmado()) {
			livro = new Livro();
			livro = buscarLivro.getLivro();
			textFieldNomeLivro.setText(livro.getNome());
		}
	}
	
	private boolean verificarDigitacao() {
		
		if (ProcessamentoDados.digitacaoCampo(textFieldNomeAutor.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do nome do Autor ", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroNomeAutor.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if (ProcessamentoDados.digitacaoCampo(textFieldNomeLivro.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do nome do Livro ", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroNomeLivro.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if ( ProcessamentoDados.digitacaoCampoData(dateChooserAutorLivro.getDate())){
			ProcessamentoDados.showMensagem("Erro na digitação do data do cadastro", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroData.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}

	
	
		return ProcessamentoDados.FALSO;
	}
	
	private void pegarDadosAutorLivroDaTela() {
		autorLivroPK = new AutorLivroPK();
        if (codigoAutor !=0 && codigoLivro != 0) { 
        	autorLivroPK.setIdAutor(codigoAutor);
        	autorLivroPK.setIdLivro(codigoLivro);
        } else {
        	autorLivroPK.setIdAutor(autor.getId());
        	autorLivroPK.setIdLivro(livro.getId());
        }
		autorLivro = new AutorLivro();
		autorLivro.setId(autorLivroPK);
		autorLivro.setAutor(autor);
		autorLivro.setLivro(livro);
		autorLivro.setDataCadastro(dateChooserAutorLivro.getDate());
	}
	
	private void incluirAutorLivro() {
		AutorLivro autorLivroEncontrado = autorLivroService.findAutorLivroById(autorLivro.getId());
		if (Objects.isNull(autorLivroEncontrado)) {
			autorLivroService.save(autorLivro);
			tabelaAutorLivroModel.saveAutorLivro(autorLivro);
	        tabelaAutorLivro.setModel(tabelaAutorLivroModel);	
	        tabelaAutorLivroModel.fireTableDataChanged();
			limparDadosDigitacao();
		} else {
			ProcessamentoDados.showMensagem("Registro já cadastrado!!", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
		}
		limparDadosDigitacao();
	}
	
	private void alterarAutorLivro() {
		AutorLivro autorLivroEncontrado = autorLivroService.findAutorLivroById(autorLivro.getId());
		if (Objects.isNull(autorLivroEncontrado)) {
			ProcessamentoDados.showMensagem("Registro não cadastrado!!", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
		} else {
			autorLivroService.update(autorLivro);
			tabelaAutorLivroModel.updateAutorLivro(autorLivro, this.linha);
			tabelaAutorLivro.setModel(tabelaAutorLivroModel);
			tabelaAutorLivroModel.fireTableDataChanged();
		}
    	limparDadosDigitacao();
	}
	
	private void excluirAutorLivro() {
		AutorLivro autorLivroEncontrado = autorLivroService.findAutorLivroById(autorLivro.getId());
		if (Objects.isNull(autorLivroEncontrado)) {
			ProcessamentoDados.showMensagem("Registro não cadastrado!!", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
		} else { 
			autorLivroService.delete(autorLivro);
			tabelaAutorLivroModel.removeAutorLivro(this.linha);
			tabelaAutorLivro.setModel(tabelaAutorLivroModel);
			tabelaAutorLivroModel.fireTableDataChanged();
		}
		limparDadosDigitacao();
	}

    
	private void buscarAutorLivro() {
		autorLivro = new AutorLivro();
		autorLivro = this.tabelaAutorLivroModel.getAutorLivro(this.linha);
		codigoAutor = autorLivro.getId().getIdAutor();
		codigoLivro = autorLivro.getId().getIdLivro();
		dateChooserAutorLivro.setDate(autorLivro.getDataCadastro());
	}
	
	private void limparDadosDigitacao() {
		textFieldNomeAutor.setText("");
		textFieldNomeLivro.setText("");
		dateChooserAutorLivro.setDate(new Date());
		
	}
	
	private void initComponents() {
		setBounds(100, 100, 1029, 632);
		setFont(new Font("Dialog", Font.BOLD, 12));
		setTitle("Cadastro de Autores e LIvros");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AutorLivroFrame.class.getResource(ProcessamentoDados.SHOW_ICON_MENU_WRITING)));

		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setOpaque(false);
		panelPrincipal.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelPrincipal.setBounds(50, 10, 922, 462);
		contentPane.add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAutor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAutor.setBounds(110, 83, 37, 14);
		panelPrincipal.add(lblAutor);
		
		textFieldNomeAutor = new JTextField();
		textFieldNomeAutor.setBounds(153, 80, 533, 20);
		panelPrincipal.add(textFieldNomeAutor);
		textFieldNomeAutor.setColumns(10);
		
		JLabel lblLivro = new JLabel("Livro:");
		lblLivro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLivro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLivro.setBounds(65, 130, 81, 14);
		panelPrincipal.add(lblLivro);
		
		textFieldNomeLivro = new JTextField();
		textFieldNomeLivro.setBounds(153, 127, 533, 20);
		panelPrincipal.add(textFieldNomeLivro);
		textFieldNomeLivro.setColumns(10);
		
		lblShowErroNomeAutor = new JLabel("");
		lblShowErroNomeAutor.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroNomeAutor.setIcon(new ImageIcon(AutorLivroFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroNomeAutor.setBounds(824, 83, 46, 14);
		panelPrincipal.add(lblShowErroNomeAutor);
		
		lblShowErroNomeLivro = new JLabel("");
		lblShowErroNomeLivro.setIcon(new ImageIcon(AutorLivroFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroNomeLivro.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroNomeLivro.setBounds(824, 130, 46, 14);
		panelPrincipal.add(lblShowErroNomeLivro);
		
		lblData = new JLabel("Data:");
		lblData.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblData.setHorizontalAlignment(SwingConstants.RIGHT);
		lblData.setDebugGraphicsOptions(DebugGraphics.FLASH_OPTION);
		lblData.setBounds(101, 176, 46, 14);
		panelPrincipal.add(lblData);
		
		dateChooserAutorLivro = new JDateChooser("dd/MM/yyyy","##/##/####",'_');
		dateChooserAutorLivro.setBounds(153, 170, 269, 20);
		panelPrincipal.add(dateChooserAutorLivro);
		
		lblShowErroData = new JLabel("");
		lblShowErroData.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroData.setIcon(new ImageIcon(AutorLivroFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroData.setBounds(428, 175, 46, 14);
		panelPrincipal.add(lblShowErroData);
		
		btnBuscaAutor = new JButton("Autor");
		btnBuscaAutor.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBuscaAutor.setMnemonic(KeyEvent.VK_A);
		btnBuscaAutor.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBuscaAutor.setIcon(new ImageIcon(AutorLivroFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_SEARCH)));
		btnBuscaAutor.setBounds(707, 79, 89, 23);
		panelPrincipal.add(btnBuscaAutor);
		
		btnBuscaLivro = new JButton("Livro");
		btnBuscaLivro.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBuscaLivro.setMnemonic(KeyEvent.VK_L);
		btnBuscaLivro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBuscaLivro.setIcon(new ImageIcon(AutorLivroFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_SEARCH)));
		btnBuscaLivro.setBounds(707, 126, 89, 23);
		panelPrincipal.add(btnBuscaLivro);
		
		listModelAutor = new DefaultListModel<>();
		
		listAutor = new JList<String>();
		listAutor.setBorder(new LineBorder(new Color(0, 0, 0)));
		listAutor.setBounds(153, 101, 533, 145);
		panelPrincipal.add(listAutor);
		
		listModelLivro = new DefaultListModel<>();
		listLivro = new JList<String>();
		listLivro.setBorder(new LineBorder(new Color(0, 0, 0)));
		listLivro.setBounds(153, 147, 533, 187);
		panelPrincipal.add(listLivro);
		
		JPanel panelBotoes = new JPanel();
		panelBotoes.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelBotoes.setBounds(50, 514, 922, 55);
		contentPane.add(panelBotoes);
		panelBotoes.setLayout(null);
		
		btnIncluir = new JButton("Incluir");
		btnIncluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnIncluir.setToolTipText("Incluir informa\u00E7\u00F5es do AutorLivro");
		btnIncluir.setMnemonic(KeyEvent.VK_I);
		btnIncluir.setIcon(new ImageIcon(AutorLivroFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_INCLUIR)));
		btnIncluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnIncluir.setBounds(24, 15, 89, 23);
		panelBotoes.add(btnIncluir);
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAlterar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlterar.setMnemonic(KeyEvent.VK_A);
		btnAlterar.setToolTipText("Alterar informa\u00E7\u00E3o do autorLivro");
		btnAlterar.setIcon(new ImageIcon(AutorLivroFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_ALTERAR)));
		btnAlterar.setBounds(123, 15, 89, 23);
		panelBotoes.add(btnAlterar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnExcluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnExcluir.setMnemonic(KeyEvent.VK_E);
		btnExcluir.setIcon(new ImageIcon(AutorLivroFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_EXCLUIR)));
		btnExcluir.setBounds(222, 15, 89, 23);
		panelBotoes.add(btnExcluir);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.setToolTipText("Encerrar o programa");
		btnFechar.setIcon(new ImageIcon(AutorLivroFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_FECHAR)));
		
		btnFechar.setBounds(321, 15, 89, 23);
		panelBotoes.add(btnFechar);
		lblShowErroNomeLivro.setVisible(ProcessamentoDados.FALSO);
		lblShowErroNomeAutor.setVisible(ProcessamentoDados.FALSO);
		lblShowErroData.setVisible(ProcessamentoDados.FALSO);
		
		listAutor.setVisible(ProcessamentoDados.FALSO);
		listLivro.setVisible(ProcessamentoDados.FALSO);
	}



	public AutorLivroPK getAutorLivroPK() {
		return autorLivroPK;
	}



	public void setAutorLivroPK(AutorLivroPK autorLivroPK) {
		this.autorLivroPK = autorLivroPK;
	}



	public Integer getCodigoAutor() {
		return codigoAutor;
	}



	public void setCodigoAutor(Integer codigoAutor) {
		this.codigoAutor = codigoAutor;
	}



	public Integer getCodigoLivro() {
		return codigoLivro;
	}



	public void setCodigoLivro(Integer codigoLivro) {
		this.codigoLivro = codigoLivro;
	}
}
