package com.projeto.view.livro;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import com.projeto.model.model.Livro;
import com.projeto.model.service.LivroService;
import com.projeto.util.ProcessamentoDados;
import com.toedter.calendar.JDateChooser;
import java.awt.Font;

public class LivroFrame extends JDialog {


	private static final long serialVersionUID = 6717485292269251800L;

	private JTable tabelaLivro;
	private TabelaLivroModel tabelaLivroModel;

	private JPanel contentPane;
	private JButton btnFechar;
	private JButton btnIncluir;
	private JButton btnAlterar;
	private JButton btnExcluir;
	
	private JTextField textFieldIsbn;
	private JTextField textFieldNome;
	
	private Livro livro;
	private LivroService livroService; 
	private JLabel lblDataPublicacao;
	private JDateChooser dateChooserDataEdicao;
	private JLabel lblVolume;
	private JTextField textFieldVolume;
	private JLabel lblCep;
	private JTextField textFieldEdicao;
	private JTextField textFieldAno;
	
	private int acao=0;
	private int codigo = 0;
	private int linha = 0;
	private JLabel lblShowErroNome;
	private JLabel lblShowErroIsbn;
	private JLabel lblShowErroDataEdicao;
	private JLabel lblShowErroVolume;
	private JLabel lblShowErroEdicao;
	private JLabel lblShowErrorAno;
	private JLabel lblTotalExemplares;
	private JTextField textFieldTotalExemplares;
	private JLabel lblShowErroTotalExemplares;
	
	
	public LivroFrame(JFrame frame, boolean modal, int acao, JTable tabelaLivro, TabelaLivroModel tabelaLivroModel, int linha  ) {
		super(frame, modal);
		this.acao = acao;
		this.tabelaLivro = tabelaLivro;
		this.tabelaLivroModel = tabelaLivroModel;
		this.linha = linha;
	    initComponents();
	    createEvents();
	    configuraAcao();
	    addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				textFieldNome.requestFocus();
			}
		});
	}



	private void configuraAcao() {
	  if (this.acao == ProcessamentoDados.INCLUIR_REGISTRO ) {
		  btnIncluir.setEnabled(ProcessamentoDados.VERDADEIRO);
		  btnAlterar.setEnabled(ProcessamentoDados.FALSO);
		  btnExcluir.setEnabled(ProcessamentoDados.FALSO);
	  }
	  if (this.acao == ProcessamentoDados.ALTERAR_REGISTRO ) {
		  buscarLivro();
		  btnIncluir.setEnabled(ProcessamentoDados.FALSO);
		  btnAlterar.setEnabled(ProcessamentoDados.VERDADEIRO);
		  btnExcluir.setEnabled(ProcessamentoDados.FALSO);
	  }
	  if (this.acao == ProcessamentoDados.EXCLUIR_REGISTRO ) {
		  buscarLivro();
		  btnIncluir.setEnabled(ProcessamentoDados.FALSO);
		  btnAlterar.setEnabled(ProcessamentoDados.FALSO);
		  btnExcluir.setEnabled(ProcessamentoDados.VERDADEIRO);
	  }
	  if (this.acao == ProcessamentoDados.CONSULTAR_REGISTRO ) {
		  buscarLivro();
		  btnIncluir.setEnabled(ProcessamentoDados.FALSO);
		  btnAlterar.setEnabled(ProcessamentoDados.FALSO);
		  btnExcluir.setEnabled(ProcessamentoDados.FALSO);
	  }

	  
	}


	private void createEvents() {
		textFieldNome.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroNome.setVisible(ProcessamentoDados.FALSO);
			}
		});
		textFieldNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
			        textFieldIsbn.requestFocus();
				}
	
			}
		});
		textFieldIsbn.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB ) {
					dateChooserDataEdicao.requestFocus();
				}
				
			}
		});
		textFieldIsbn.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroIsbn.setVisible(ProcessamentoDados.FALSO);
			}
		});
		dateChooserDataEdicao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB ) {
					textFieldVolume.requestFocus();
				}	
			}
		});
		dateChooserDataEdicao.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroDataEdicao.setVisible(ProcessamentoDados.FALSO);
			}
		});
		textFieldVolume.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB ) {
					textFieldEdicao.requestFocus();
				} else if ( e.getKeyCode() != KeyEvent.VK_BACK_SPACE ) {
					 if (e.getKeyChar()>='0' && e.getKeyChar()<='9') {
						lblShowErroVolume.setVisible(ProcessamentoDados.FALSO);
						textFieldVolume.setEditable(ProcessamentoDados.VERDADEIRO);;
					} else {
						lblShowErroVolume.setVisible(ProcessamentoDados.VERDADEIRO);
						textFieldVolume.setEditable(ProcessamentoDados.FALSO);;
					}
				}
	
			}
		});
		textFieldVolume.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroVolume.setVisible(ProcessamentoDados.FALSO);
			}
		});
		textFieldEdicao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB ) {
					textFieldAno.requestFocus();
				}	
	
			}
		});
		textFieldEdicao.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroEdicao.setVisible(ProcessamentoDados.FALSO);
			}
		});
		textFieldAno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB ) {
					textFieldTotalExemplares.requestFocus(); 
				} else if ( e.getKeyCode() != KeyEvent.VK_BACK_SPACE ) {
					 if (e.getKeyChar()>='0' && e.getKeyChar()<='9') {
						lblShowErrorAno.setVisible(ProcessamentoDados.FALSO);
						textFieldAno.setEditable(ProcessamentoDados.VERDADEIRO);;
					} else {
						lblShowErrorAno.setVisible(ProcessamentoDados.VERDADEIRO);
						textFieldAno.setEditable(ProcessamentoDados.FALSO);;
					}
				}
	
			}
		});
		textFieldAno.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErrorAno.setVisible(ProcessamentoDados.FALSO);
			}
		});
		textFieldTotalExemplares.addKeyListener(new KeyAdapter() {
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
				} else if ( e.getKeyCode() != KeyEvent.VK_BACK_SPACE ) {
					 if (e.getKeyChar()>='0' && e.getKeyChar()<='9') {
						lblShowErroTotalExemplares.setVisible(ProcessamentoDados.FALSO);
						textFieldTotalExemplares.setEditable(ProcessamentoDados.VERDADEIRO);;
					} else {
						lblShowErroTotalExemplares.setVisible(ProcessamentoDados.VERDADEIRO);
						textFieldTotalExemplares.setEditable(ProcessamentoDados.FALSO);;
					}
				}
	
			}
		});
		textFieldTotalExemplares.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroTotalExemplares.setVisible(ProcessamentoDados.FALSO);
			}
		});
		
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean toReturn = ProcessamentoDados.FALSO;
				toReturn = verificarDigitacao();
				if (toReturn == ProcessamentoDados.FALSO) {
					pegarDadosLivroDaTela();
					incluirLivro();
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
						pegarDadosLivroDaTela();
						incluirLivro();
					}
				}
			}
		});
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean toReturn = ProcessamentoDados.FALSO;
				toReturn = verificarDigitacao();
				if (toReturn == ProcessamentoDados.FALSO) {
					pegarDadosLivroDaTela();
					alterarLivro();
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
						pegarDadosLivroDaTela();
						alterarLivro();
					}
				}
			}
		});
		
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirLivro();
			}
		});
		btnAlterar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_E) {
					excluirLivro();
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
				if (e.getKeyCode()==KeyEvent.VK_F) {
					dispose();
				}
			}
		});
		
	}
	
	
	private boolean verificarDigitacao() {
		
		if (ProcessamentoDados.digitacaoCampo(textFieldNome.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do Nome do livro ", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroNome.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		if ( textFieldNome.getText().length() > 80  ) {
			ProcessamentoDados.showMensagem("Erro na digitação do Nome do Livro", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroNome.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if (ProcessamentoDados.digitacaoCampo(textFieldIsbn.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do número do ISBN do livro ", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroIsbn.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		if ( textFieldIsbn.getText().length() > 80  ) {
			ProcessamentoDados.showMensagem("Erro na digitação do numero do ISBN do Livro", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroIsbn.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if ( ProcessamentoDados.digitacaoCampo(dateChooserDataEdicao.getDate().toString())){
			ProcessamentoDados.showMensagem("Erro na digitação do data de publicação do Livro", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroDataEdicao.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if (ProcessamentoDados.digitacaoCampo(textFieldVolume.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do Volume do Livro", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroVolume.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		if ( textFieldVolume.getText().length() > Integer.MAX_VALUE  ) {
			ProcessamentoDados.showMensagem("Erro na digitação do Volume do Livro", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroVolume.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
	
		if (ProcessamentoDados.digitacaoCampo(textFieldEdicao.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação da Edição do Livro", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroEdicao.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		if ( textFieldEdicao.getText().length() > 20  ) {
			ProcessamentoDados.showMensagem("Erro na digitação da Edição do Livro", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroEdicao.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if (ProcessamentoDados.digitacaoCampo(textFieldAno.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do Ano do Livro", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErrorAno.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		if ( textFieldAno.getText().length() > Integer.MAX_VALUE  ) {
			ProcessamentoDados.showMensagem("Erro na digitação do Ano do Livro", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErrorAno.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if (ProcessamentoDados.digitacaoCampo(textFieldTotalExemplares.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do total de exemplares do Livro", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErrorAno.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		if ( textFieldTotalExemplares.getText().length() > Integer.MAX_VALUE  ) {
			ProcessamentoDados.showMensagem("Erro na digitação do total de exemplares do Livro", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErrorAno.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		return ProcessamentoDados.FALSO;
	}
	
	private void pegarDadosLivroDaTela() {
		livro = new Livro();
		if (codigo !=0 ) {
			livro.setId(codigo);
		}
		livro.setNome(textFieldNome.getText());
		livro.setISBN(textFieldIsbn.getText());
		livro.setDataEdicao(dateChooserDataEdicao.getDate());
		livro.setVolume(ProcessamentoDados.converterParaInteiro(textFieldVolume.getText()));
		livro.setEdicao(textFieldEdicao.getText());
		livro.setAno(ProcessamentoDados.converterParaInteiro(textFieldAno.getText()));
		livro.setQtdExemplares(ProcessamentoDados.converterParaInteiro(textFieldTotalExemplares.getText()));
	}
	
	private void incluirLivro() {
		livroService = new LivroService();
		livroService.save(livro);
		tabelaLivroModel.saveLivro(livro);
        tabelaLivro.setModel(tabelaLivroModel);	
        tabelaLivroModel.fireTableDataChanged();
		limparDadosDigitacao();

	}
	
	private void alterarLivro() {
		livroService = new LivroService();
		livroService.update(livro);
		limparDadosDigitacao();
		tabelaLivroModel.updateLivro(livro, this.linha);
		tabelaLivro.setModel(tabelaLivroModel);
		tabelaLivroModel.fireTableDataChanged();
	}
	
	private void excluirLivro() {
		livroService = new LivroService();
		livroService.delete(livro);
		limparDadosDigitacao();
		tabelaLivroModel.removeLivro(this.linha);
		tabelaLivro.setModel(tabelaLivroModel);
		tabelaLivroModel.fireTableDataChanged();
	}

    
	private void buscarLivro() {
		livro = new Livro();
		livro = this.tabelaLivroModel.getLivro(this.linha);
		codigo = livro.getId();
		textFieldNome.setText(livro.getNome());
		textFieldIsbn.setText(livro.getISBN());
		dateChooserDataEdicao.setDate(livro.getDataEdicao());
		textFieldVolume.setText(ProcessamentoDados.converterInteiroParaString(livro.getVolume()));
		textFieldEdicao.setText(livro.getEdicao());
		textFieldAno.setText(ProcessamentoDados.converterInteiroParaString(livro.getAno()));
	}
	
	private void limparDadosDigitacao() {
		textFieldNome.setText("");
		textFieldIsbn.setText("");
		dateChooserDataEdicao.setDate(null);
		textFieldVolume.setText("");
		textFieldEdicao.setText("");
		textFieldAno.setText("");
		textFieldTotalExemplares.setText("");
	}
	
	
	private void initComponents() {
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LivroFrame.class.getResource(ProcessamentoDados.SHOW_ICON_MENU_LIVRO)));
		setTitle("Cadastro de Livros");
		
		setBounds(100, 100, 733, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelPrincipal.setBounds(44, 11, 625, 335);
		contentPane.add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(116, 47, 37, 14);
		panelPrincipal.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(153, 44, 371, 20);
		panelPrincipal.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JLabel lblIsbn = new JLabel("ISBN:");
		lblIsbn.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblIsbn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIsbn.setBounds(71, 94, 81, 14);
		panelPrincipal.add(lblIsbn);
		
		textFieldIsbn = new JTextField();
		textFieldIsbn.setBounds(153, 91, 191, 20);
		panelPrincipal.add(textFieldIsbn);
		textFieldIsbn.setColumns(10);
		
		lblDataPublicacao = new JLabel("Data Publicação:");
		lblDataPublicacao.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDataPublicacao.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDataPublicacao.setBounds(25, 138, 128, 14);
		panelPrincipal.add(lblDataPublicacao);
		
		dateChooserDataEdicao = new JDateChooser("dd/MM/yyyy","##/##/####",'_');
		dateChooserDataEdicao.setBounds(153, 135, 191, 20);
		panelPrincipal.add(dateChooserDataEdicao);
		
		lblVolume = new JLabel("Volume:");
		lblVolume.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVolume.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVolume.setBounds(108, 178, 46, 14);
		panelPrincipal.add(lblVolume);
		
		textFieldVolume = new JTextField();
		textFieldVolume.setBounds(155, 175, 191, 20);
		panelPrincipal.add(textFieldVolume);
		textFieldVolume.setColumns(10);
		
		lblCep = new JLabel("Edição:");
		lblCep.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCep.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCep.setBounds(104, 222, 46, 14);
		panelPrincipal.add(lblCep);
		
		textFieldEdicao = new JTextField();
    	textFieldEdicao.setBounds(156, 219, 191, 20);
		panelPrincipal.add(textFieldEdicao);
		textFieldEdicao.setColumns(10);
		
		lblShowErroNome = new JLabel("");
		lblShowErroNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroNome.setIcon(new ImageIcon(LivroFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroNome.setBounds(534, 47, 46, 14);
		panelPrincipal.add(lblShowErroNome);
		
		lblShowErroIsbn = new JLabel("");
		lblShowErroIsbn.setIcon(new ImageIcon(LivroFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroIsbn.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroIsbn.setBounds(354, 94, 46, 14);
		panelPrincipal.add(lblShowErroIsbn);
		
		lblShowErroDataEdicao = new JLabel("");
		lblShowErroDataEdicao.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroDataEdicao.setIcon(new ImageIcon(LivroFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroDataEdicao.setBounds(354, 138, 46, 14);
		panelPrincipal.add(lblShowErroDataEdicao);
		
		lblShowErroVolume = new JLabel("");
		lblShowErroVolume.setIcon(new ImageIcon(LivroFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroVolume.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroVolume.setBounds(355, 178, 46, 14);
		panelPrincipal.add(lblShowErroVolume);
		
		lblShowErroEdicao = new JLabel("");
		lblShowErroEdicao.setIcon(new ImageIcon(LivroFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroEdicao.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroEdicao.setBounds(354, 222, 46, 14);
		panelPrincipal.add(lblShowErroEdicao);
		
		JLabel lblAno = new JLabel("Ano:");
		lblAno.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAno.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAno.setBounds(105, 261, 46, 14);
		panelPrincipal.add(lblAno);
		
		textFieldAno = new JTextField();
		textFieldAno.setBounds(155, 258, 191, 20);
		panelPrincipal.add(textFieldAno);
		textFieldAno.setColumns(10);
		
		lblShowErrorAno = new JLabel("");
		lblShowErrorAno.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErrorAno.setIcon(new ImageIcon(LivroFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErrorAno.setBounds(354, 261, 46, 14);
		panelPrincipal.add(lblShowErrorAno);
		
		lblTotalExemplares = new JLabel("Total Exemplares:");
		lblTotalExemplares.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalExemplares.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalExemplares.setBounds(25, 297, 128, 14);
		panelPrincipal.add(lblTotalExemplares);
		
		textFieldTotalExemplares = new JTextField();
		textFieldTotalExemplares.setBounds(158, 294, 191, 20);
		panelPrincipal.add(textFieldTotalExemplares);
		textFieldTotalExemplares.setColumns(10);
		
		lblShowErroTotalExemplares = new JLabel("");
		lblShowErroTotalExemplares.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroTotalExemplares.setBounds(354, 297, 46, 14);
		lblShowErroTotalExemplares.setIcon(new ImageIcon(LivroFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		panelPrincipal.add(lblShowErroTotalExemplares);
		
		JPanel panelBotoes = new JPanel();
		panelBotoes.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelBotoes.setBounds(44, 369, 625, 55);
		contentPane.add(panelBotoes);
		panelBotoes.setLayout(null);
		
		btnIncluir = new JButton("Incluir");
		btnIncluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnIncluir.setMnemonic(KeyEvent.VK_I);
		btnIncluir.setToolTipText("Incluir informa\u00E7\u00F5es do Livro");
		btnIncluir.setIcon(new ImageIcon(LivroFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_INCLUIR)));
		btnIncluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));

		btnIncluir.setBounds(24, 15, 89, 23);
		panelBotoes.add(btnIncluir);
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnIncluir.setMnemonic(KeyEvent.VK_A);
		btnAlterar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlterar.setToolTipText("Alterar informa\u00E7\u00E3o do livro");
		btnAlterar.setIcon(new ImageIcon(LivroFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_ALTERAR)));
		btnAlterar.setBounds(123, 15, 89, 23);
		panelBotoes.add(btnAlterar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnIncluir.setMnemonic(KeyEvent.VK_E);
		btnExcluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnExcluir.setIcon(new ImageIcon(LivroFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_EXCLUIR)));
		btnExcluir.setBounds(222, 15, 89, 23);
		panelBotoes.add(btnExcluir);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setToolTipText("Encerrar o programa");
		btnFechar.setIcon(new ImageIcon(LivroFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_FECHAR)));
		btnFechar.setBounds(321, 15, 89, 23);
		panelBotoes.add(btnFechar);
		
		lblShowErroDataEdicao.setVisible(ProcessamentoDados.FALSO);
		lblShowErroEdicao.setVisible(ProcessamentoDados.FALSO);
		lblShowErroVolume.setVisible(ProcessamentoDados.FALSO);
		lblShowErroIsbn.setVisible(ProcessamentoDados.FALSO);
		lblShowErroNome.setVisible(ProcessamentoDados.FALSO);
		lblShowErroDataEdicao.setVisible(ProcessamentoDados.FALSO);
		lblShowErrorAno.setVisible(ProcessamentoDados.FALSO);
		lblShowErroTotalExemplares.setVisible(ProcessamentoDados.FALSO);
		
		
	}
}
