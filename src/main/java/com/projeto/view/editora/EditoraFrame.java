package com.projeto.view.editora;

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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import com.projeto.model.model.Editora;
import com.projeto.model.service.EditoraService;
import com.projeto.util.MaskFields;
import com.projeto.util.ProcessamentoDados;

public class EditoraFrame extends JDialog {


	private static final long serialVersionUID = 6717485292269251800L;
	
	private JPanel contentPane;
	private JTextField textFieldNome;
	private JButton btnFechar;
	private JButton btnIncluir;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JTextField textFieldEndereco;

	private Editora editora;
	private EditoraService editoraService; 
	private JLabel lblBairro;
	private JTextField textFieldBairro;
	private JLabel lblCidade;
	private JTextField textFieldCidade;
	private JLabel lblCep;
	private JFormattedTextField textFieldCep;
	
	private JTable tabelaEditoraes;
	private TabelaEditoraModel tabelaEditoraModel;
	private JLabel lblShowErroNome;
	private JLabel lblShowErroEndereco;
	private JLabel lblShowErroBairro;
	private JLabel lblShowErroCidade;
	private JLabel lblShowErroCep;
	
	private int acao = 0;
	private int codigo = 0;
	private int linha = 0;
	
	
	public EditoraFrame(JFrame frame, boolean modal, int acao, JTable tabelaEditoraes, TabelaEditoraModel tabelaEditoraModel, int linha  ) {
		super(frame, modal);
		this.acao = acao;
		this.tabelaEditoraes = tabelaEditoraes;
		this.tabelaEditoraModel = tabelaEditoraModel;
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
		  buscarEditora();
		  btnIncluir.setEnabled(ProcessamentoDados.FALSO);
		  btnAlterar.setEnabled(ProcessamentoDados.VERDADEIRO);
		  btnExcluir.setEnabled(ProcessamentoDados.FALSO);
	  }
	  if (this.acao == ProcessamentoDados.EXCLUIR_REGISTRO ) {
		  buscarEditora();
		  btnIncluir.setEnabled(ProcessamentoDados.FALSO);
		  btnAlterar.setEnabled(ProcessamentoDados.FALSO);
		  btnExcluir.setEnabled(ProcessamentoDados.VERDADEIRO);
	  }
	  if (this.acao == ProcessamentoDados.CONSULTAR_REGISTRO ) {
		  buscarEditora();
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
			        textFieldEndereco.requestFocus();
				}
	
			}
		});
		textFieldEndereco.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB ) {
					textFieldBairro.requestFocus();
				}
				
			}
		});
		textFieldEndereco.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroEndereco.setVisible(ProcessamentoDados.FALSO);
			}
		});
		textFieldBairro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB ) {
					textFieldCidade.requestFocus();
				}
			}
		});
		textFieldBairro.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroBairro.setVisible(ProcessamentoDados.FALSO);
			}
		});
		textFieldCidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB ) {
					textFieldCep.requestFocus();
				}
		
			}
		});
		textFieldCidade.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroCidade.setVisible(ProcessamentoDados.FALSO);
			}
		});
		textFieldCep.addKeyListener(new KeyAdapter() {
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
						lblShowErroCep.setVisible(ProcessamentoDados.FALSO);
						textFieldCep.setEditable(ProcessamentoDados.VERDADEIRO);;
					} else {
						lblShowErroCep.setVisible(ProcessamentoDados.VERDADEIRO);
						textFieldCep.setEditable(ProcessamentoDados.FALSO);;
					}
				}
			}
		});
		textFieldCep.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroCep.setVisible(ProcessamentoDados.FALSO);
			}
		});
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean toReturn = ProcessamentoDados.FALSO;
				toReturn = verificarDigitacao();
				if (toReturn == ProcessamentoDados.FALSO) {
					pegarDadosEditoraDaTela();
					incluirEditora();
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
						pegarDadosEditoraDaTela();
						incluirEditora();
					}	
				}
			}
		});
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean toReturn = ProcessamentoDados.FALSO;
				toReturn = verificarDigitacao();
				if (toReturn == ProcessamentoDados.FALSO) {
					pegarDadosEditoraDaTela();
					alterarEditora();
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
						pegarDadosEditoraDaTela();
						alterarEditora();
					}
				}
			}
		});
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirEditora();
			}
		});
		btnExcluir.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_E) {
					excluirEditora();
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
	
	
	private boolean verificarDigitacao() {
		
		if (ProcessamentoDados.digitacaoCampo(textFieldNome.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do nome da Editora ", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroNome.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		if ( textFieldNome.getText().length() > 80  ) {
			ProcessamentoDados.showMensagem("Erro na digitação do nome da Editora", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroNome.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		if (ProcessamentoDados.digitacaoCampo(textFieldEndereco.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do nome do endereço da Editora ", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroEndereco.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		if ( textFieldEndereco.getText().length() > 80  ) {
			ProcessamentoDados.showMensagem("Erro na digitação do nome do endereço da Editora", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroEndereco.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		if (ProcessamentoDados.digitacaoCampo(textFieldBairro.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do bairro da editora ", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroBairro.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		if ( textFieldBairro.getText().length() > 80  ) {
			ProcessamentoDados.showMensagem("Erro na digitação do bairro da Editora", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroBairro.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		if (ProcessamentoDados.digitacaoCampo(textFieldCidade.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do nome da cidade da Editora ", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroCidade.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		if ( textFieldCidade.getText().length() > 80  ) {
			ProcessamentoDados.showMensagem("Erro na digitação do nome da cidade da Editora", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroCidade.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
	    if (ProcessamentoDados.digitacaoCampoFormatado(textFieldCep.getText())) {
	    	ProcessamentoDados.showMensagem("Erro na digitação no CEP", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
	    	lblShowErroCep.setVisible(ProcessamentoDados.VERDADEIRO);
	    	return ProcessamentoDados.VERDADEIRO;
	    }	
		return ProcessamentoDados.FALSO;
	}
	
	private void pegarDadosEditoraDaTela() {
		editora = new Editora();
		if ( codigo != 0 ) {
			editora.setId(codigo);
		}
		editora.setNome(textFieldNome.getText());
		editora.setRua(textFieldEndereco.getText());
		editora.setBairro(textFieldBairro.getText());
		editora.setCidade(textFieldCidade.getText());
		editora.setCep(textFieldCep.getText());
		
	}
	
	private void incluirEditora() {
	    editoraService = new EditoraService();	
		editoraService.save(editora);
		tabelaEditoraModel.saveEditora(editora);
        tabelaEditoraes.setModel(tabelaEditoraModel);	
        tabelaEditoraModel.fireTableDataChanged();
		limparDadosDigitacao();

	}
	
	private void alterarEditora() {
		editoraService = new EditoraService();
		editoraService.update(editora);
		limparDadosDigitacao();
		tabelaEditoraModel.updateEditora(editora, this.linha);
		tabelaEditoraes.setModel(tabelaEditoraModel);
		tabelaEditoraModel.fireTableDataChanged();
	}
	
	private void excluirEditora() {
		editoraService = new EditoraService();
		editoraService.delete(editora);
		limparDadosDigitacao();
		tabelaEditoraModel.removeEditora(this.linha);
		tabelaEditoraes.setModel(tabelaEditoraModel);
		tabelaEditoraModel.fireTableDataChanged();
	}

    
	private void buscarEditora() {
		editora = new Editora();
		editora = this.tabelaEditoraModel.getEditora(this.linha);
		codigo = editora.getId();
		textFieldNome.setText(editora.getNome());
		textFieldEndereco.setText(editora.getRua());
		textFieldBairro.setText(editora.getBairro());
		textFieldCidade.setText(editora.getCidade());
		textFieldCep.setText(editora.getCep());
	}
	
	private void limparDadosDigitacao() {
		textFieldNome.setText("");
		textFieldEndereco.setText("");
		textFieldBairro.setText("");
		textFieldCidade.setText("");
		textFieldCep.setText("");
		
		
	}
	
	
	private void initComponents() {
		
		setResizable(false);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditoraFrame.class.getResource(ProcessamentoDados.SHOW_ICON_MENU_EDITORA)));
		setTitle("Cadastro de Editoras");

		
		setBounds(100, 100, 1026, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelPrincipal.setBounds(44, 11, 922, 335);
		contentPane.add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(116, 83, 37, 14);
		panelPrincipal.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(153, 80, 371, 20);
		panelPrincipal.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JLabel lblEndereco = new JLabel("Endere\u00E7o:");
		lblEndereco.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEndereco.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEndereco.setBounds(71, 130, 81, 14);
		panelPrincipal.add(lblEndereco);
		
		textFieldEndereco = new JTextField();
		textFieldEndereco.setBounds(153, 127, 661, 20);
		panelPrincipal.add(textFieldEndereco);
		textFieldEndereco.setColumns(10);
		
		lblBairro = new JLabel("Bairro:");
		lblBairro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBairro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBairro.setBounds(107, 174, 46, 14);
		panelPrincipal.add(lblBairro);
		
		textFieldBairro = new JTextField();
		textFieldBairro.setBounds(153, 171, 661, 20);
		panelPrincipal.add(textFieldBairro);
		textFieldBairro.setColumns(10);
		
		lblCidade = new JLabel("Cidade:");
		lblCidade.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCidade.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCidade.setBounds(108, 214, 46, 14);
		panelPrincipal.add(lblCidade);
		
		textFieldCidade = new JTextField();
		textFieldCidade.setBounds(155, 211, 659, 20);
		panelPrincipal.add(textFieldCidade);
		textFieldCidade.setColumns(10);
		
		lblCep = new JLabel("Cep:");
		lblCep.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCep.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCep.setBounds(104, 258, 46, 14);
		panelPrincipal.add(lblCep);
		
		MaskFields mascara = new MaskFields();
		textFieldCep = new JFormattedTextField(mascara.mascaraCEP(textFieldCep));
		textFieldCep.setBounds(156, 255, 105, 20);
		panelPrincipal.add(textFieldCep);
		textFieldCep.setColumns(10);
				
		lblShowErroNome = new JLabel("");
		lblShowErroNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroNome.setIcon(new ImageIcon(EditoraFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroNome.setBounds(534, 83, 46, 14);
		panelPrincipal.add(lblShowErroNome);
		
		lblShowErroEndereco = new JLabel("");
		lblShowErroEndereco.setIcon(new ImageIcon(EditoraFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroEndereco.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroEndereco.setBounds(824, 130, 46, 14);
		panelPrincipal.add(lblShowErroEndereco);
		
		lblShowErroBairro = new JLabel("");
		lblShowErroBairro.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroBairro.setIcon(new ImageIcon(EditoraFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroBairro.setBounds(824, 174, 46, 14);
		panelPrincipal.add(lblShowErroBairro);
		
		lblShowErroCidade = new JLabel("");
		lblShowErroCidade.setIcon(new ImageIcon(EditoraFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroCidade.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCidade.setBounds(824, 214, 46, 14);
		panelPrincipal.add(lblShowErroCidade);
		
		lblShowErroCep = new JLabel("");
		lblShowErroCep.setIcon(new ImageIcon(EditoraFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroCep.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCep.setBounds(271, 258, 46, 14);
		panelPrincipal.add(lblShowErroCep);
		
		JPanel panelBotoes = new JPanel();
		panelBotoes.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelBotoes.setBounds(44, 369, 922, 55);
		contentPane.add(panelBotoes);
		panelBotoes.setLayout(null);
		
		btnIncluir = new JButton("Incluir");
		btnIncluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnIncluir.setMnemonic(KeyEvent.VK_I);
		btnIncluir.setToolTipText("Incluir informa\u00E7\u00F5es do Editora");
		btnIncluir.setIcon(new ImageIcon(EditoraFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_INCLUIR)));
		btnIncluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));

		btnIncluir.setBounds(24, 15, 89, 23);
		panelBotoes.add(btnIncluir);
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAlterar.setMnemonic(KeyEvent.VK_A);
		btnAlterar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlterar.setToolTipText("Alterar informa\u00E7\u00E3o do editora");
		btnAlterar.setIcon(new ImageIcon(EditoraFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_ALTERAR)));
		btnAlterar.setBounds(123, 15, 89, 23);
		panelBotoes.add(btnAlterar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnExcluir.setMnemonic(KeyEvent.VK_E);
		
		btnExcluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnExcluir.setIcon(new ImageIcon(EditoraFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_EXCLUIR)));
		btnExcluir.setBounds(222, 15, 89, 23);
		panelBotoes.add(btnExcluir);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setToolTipText("Encerrar o programa");
		btnFechar.setIcon(new ImageIcon(EditoraFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_FECHAR)));
		
		btnFechar.setBounds(321, 15, 89, 23);
		panelBotoes.add(btnFechar);
		
		lblShowErroBairro.setVisible(ProcessamentoDados.FALSO);
		lblShowErroCep.setVisible(ProcessamentoDados.FALSO);
		lblShowErroCidade.setVisible(ProcessamentoDados.FALSO);
		lblShowErroEndereco.setVisible(ProcessamentoDados.FALSO);
		lblShowErroNome.setVisible(ProcessamentoDados.FALSO);
		
		
	}
}
