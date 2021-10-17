package com.projeto.view.autor;

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

import javax.swing.DebugGraphics;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
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
import com.projeto.model.model.Editora;
import com.projeto.model.service.AutorService;
import com.projeto.model.service.EditoraService;
import com.projeto.util.MaskFields;
import com.projeto.util.ProcessamentoDados;
import com.projeto.view.editora.BuscarEditora;
import com.toedter.calendar.JDateChooser;
import javax.swing.JScrollPane;

public class AutorFrame extends JDialog {


	private static final long serialVersionUID = 6717485292269251800L;
	
	private JPanel contentPane;
	private JTextField textFieldNome;
	private JButton btnFechar;
	private JButton btnIncluir;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JTextField textFieldEndereco;
	private JButton btnBuscaEditora;
	private JComboBox<String> comboBoxAutor;
    private MaskFields mascara;
	
	
	private Autor autor;
	private Editora editora;
	private AutorService autorService; 
	private JLabel lblBairro;
	private JTextField textFieldBairro;
	private JLabel lblCidade;
	private JTextField textFieldCidade;
	private JLabel lblCep;
	private JFormattedTextField textFieldCep;
	private JFormattedTextField textFieldTelefoneFixo;
	private JFormattedTextField textFieldCelular;
	private JFormattedTextField textFieldCPF;
	private JFormattedTextField textFieldRG;
	private JLabel lblSexo;
	private JDateChooser dateChooserAutor;
	
	private int acao=0;
	private int codigo = 0;
	private int linha = 0;
	
	private JTable tabelaAutores;
	private TabelaAutorModel tabelaAutorModel;
	private JLabel lblShowErroNome;
	private JLabel lblShowErroEndereco;
	private JLabel lblShowErroBairro;
	private JLabel lblShowErroCidade;
	private JLabel lblShowErroCep;
	private JLabel lblShowErroCpf;
	private JLabel lblShowErroRg;
	private JLabel lblShowErroTelFixo;
	private JLabel lblShowErroTelCelular;
	private JLabel lblShowErroSexo;
	private JLabel lblData;
	private JLabel lblShowErroData;
	private JTextField textFieldNomeEditora;
	private JLabel lblShowErroEditora;
    private DefaultListModel<String> listModelEditora;
	private JList<String> listEditora;
	private Integer editoraSelecionada[] = {};
	private JScrollPane scrollPane;
	
	public AutorFrame(JFrame frame, boolean modal, int acao, JTable tabelaAutores, TabelaAutorModel tabelaAutorModel, int linha  ) {
		super(frame, modal);
		this.acao = acao;
		this.tabelaAutores = tabelaAutores;
		this.tabelaAutorModel = tabelaAutorModel;
		this.linha = linha;
		autor = new Autor();
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
		  buscarAutor();
		  btnIncluir.setEnabled(ProcessamentoDados.FALSO);
		  btnAlterar.setEnabled(ProcessamentoDados.VERDADEIRO);
		  btnExcluir.setEnabled(ProcessamentoDados.FALSO);
	  }
	  if (this.acao == ProcessamentoDados.EXCLUIR_REGISTRO ) {
		  buscarAutor();
		  btnIncluir.setEnabled(ProcessamentoDados.FALSO);
		  btnAlterar.setEnabled(ProcessamentoDados.FALSO);
		  btnExcluir.setEnabled(ProcessamentoDados.VERDADEIRO);
	  }
	  if (this.acao == ProcessamentoDados.CONSULTAR_REGISTRO ) {
		  buscarAutor();
		  btnIncluir.setEnabled(ProcessamentoDados.FALSO);
		  btnAlterar.setEnabled(ProcessamentoDados.FALSO);
		  btnExcluir.setEnabled(ProcessamentoDados.FALSO);
	  }

	  
	}


	private void createEvents() {
		
		textFieldNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
			        comboBoxAutor.requestFocus();
				}
			}
		});
		textFieldNome.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroNome.setVisible(ProcessamentoDados.FALSO);
			}
		});
		comboBoxAutor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB ) {
					textFieldEndereco.requestFocus();
				}
			}
		});
		comboBoxAutor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroSexo.setVisible(ProcessamentoDados.FALSO);
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
					textFieldCPF.requestFocus();
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
		textFieldCPF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB ) {
					textFieldRG.requestFocus();
				}else if ( e.getKeyCode() != KeyEvent.VK_BACK_SPACE ) {
					if (e.getKeyChar()>='0' && e.getKeyChar()<='9') {
						lblShowErroCpf.setVisible(ProcessamentoDados.FALSO);
						textFieldCPF.setEditable(ProcessamentoDados.VERDADEIRO);;
					} else {
						lblShowErroCpf.setVisible(ProcessamentoDados.VERDADEIRO);
						textFieldCPF.setEditable(ProcessamentoDados.FALSO);;
					}
				}
			}
		});
		textFieldCPF.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroCpf.setVisible(ProcessamentoDados.FALSO);
			}
		});
		textFieldRG.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB ) {
					dateChooserAutor.requestFocus();
				} else if ( e.getKeyCode() != KeyEvent.VK_BACK_SPACE ) {
					if (e.getKeyChar()>='0' && e.getKeyChar()<='9') {
						lblShowErroRg.setVisible(ProcessamentoDados.FALSO);
						textFieldRG.setEditable(ProcessamentoDados.VERDADEIRO);;
					} else {
						lblShowErroRg.setVisible(ProcessamentoDados.VERDADEIRO);
						textFieldRG.setEditable(ProcessamentoDados.FALSO);;
					}
				}
			}
		});
		textFieldRG.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroRg.setVisible(ProcessamentoDados.FALSO);
			}
			
		});
		dateChooserAutor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB ) {
					textFieldTelefoneFixo.requestFocus();
				}
			}
		});
		dateChooserAutor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroData.setVisible(ProcessamentoDados.FALSO);
			}
		});
		textFieldTelefoneFixo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB ) {
					textFieldCelular.requestFocus();			
				} else if ( e.getKeyCode() != KeyEvent.VK_BACK_SPACE ) {
					if (e.getKeyChar()>='0' && e.getKeyChar()<='9') {
						lblShowErroTelFixo.setVisible(ProcessamentoDados.FALSO);
						textFieldTelefoneFixo.setEditable(ProcessamentoDados.VERDADEIRO);;
					} else {
						lblShowErroTelFixo.setVisible(ProcessamentoDados.VERDADEIRO);
						textFieldTelefoneFixo.setEditable(ProcessamentoDados.FALSO);;
					}
				}
			}
		});
		textFieldTelefoneFixo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroTelFixo.setVisible(ProcessamentoDados.FALSO);
			}
		});
		textFieldCelular.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB ) {
					textFieldNomeEditora.requestFocus();
				} else if ( e.getKeyCode() != KeyEvent.VK_BACK_SPACE ) {
					if (e.getKeyChar()>='0' && e.getKeyChar()<='9') {
						lblShowErroTelCelular.setVisible(ProcessamentoDados.FALSO);
						textFieldCelular.setEditable(ProcessamentoDados.VERDADEIRO);;
					} else {
						lblShowErroTelCelular.setVisible(ProcessamentoDados.VERDADEIRO);
						textFieldCelular.setEditable(ProcessamentoDados.FALSO);;
					}
				}
			}
		});
		textFieldCelular.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroTelCelular.setVisible(ProcessamentoDados.FALSO);
			}
		});
		textFieldNomeEditora.addKeyListener(new KeyAdapter() {
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
				pesquisaEditora();
				
			}
		});
		textFieldNomeEditora.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroEditora.setVisible(ProcessamentoDados.FALSO);
				listEditora.setVisible(ProcessamentoDados.FALSO);
				scrollPane.setVisible(ProcessamentoDados.FALSO);
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
		});
		textFieldNomeEditora.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textFieldNomeEditora.requestFocus();
				listEditora.setVisible(ProcessamentoDados.VERDADEIRO);
				scrollPane.setVisible(ProcessamentoDados.VERDADEIRO);
			}
		});
		listEditora.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ( e.getButton() == MouseEvent.BUTTON1 ) {
					Integer item = listEditora.getSelectedIndex();
					Integer id = editoraSelecionada[item];
					editora = new Editora();
					EditoraService editoraService = new EditoraService();
					editora = editoraService.findEditoraById(id);
					textFieldNomeEditora.setText(editora.getNome());
					listEditora.setVisible(ProcessamentoDados.FALSO);
					scrollPane.setVisible(ProcessamentoDados.FALSO);
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
			@Override
			public void mousePressed(MouseEvent e) {
				Integer item = listEditora.getSelectedIndex();
				Integer id = editoraSelecionada[item];
				editora = new Editora();
				EditoraService editoraService = new EditoraService();
				editora = editoraService.findEditoraById(id);
				textFieldNomeEditora.setText(editora.getNome());
				listEditora.setVisible(ProcessamentoDados.FALSO);
				scrollPane.setVisible(ProcessamentoDados.FALSO);
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
		});
		btnBuscaEditora.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarEditora();
			}
		});
		btnBuscaEditora.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_D) {
					buscarEditora();
				}
			}
		});
		btnBuscaEditora.addFocusListener(new FocusAdapter() {
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
			}
		});
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean toReturn = ProcessamentoDados.FALSO;
				toReturn = verificarDigitacao();
				if (toReturn == ProcessamentoDados.FALSO) {
					pegarDadosAutorDaTela();
					incluirAutor();
				}	
			}
		});
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean toReturn = ProcessamentoDados.FALSO;
				toReturn = verificarDigitacao();
				if (toReturn == ProcessamentoDados.FALSO) {
					pegarDadosAutorDaTela();
					alterarAutor();
				}
					
			}
		});
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirAutor();
			}
		});
		
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
	}
	
	
	
	protected void buscarEditora() {
		BuscarEditora buscarEditora = new BuscarEditora(new JFrame(), ProcessamentoDados.VERDADEIRO);
		buscarEditora.setLocationRelativeTo(null);
		buscarEditora.setVisible(ProcessamentoDados.VERDADEIRO);
		if (buscarEditora.isConfirmado()) {
			editora = new Editora();
			editora = buscarEditora.getEditora();
			textFieldNomeEditora.setText(editora.getNome());
		}
		
		
	}
	
	
	protected void pesquisaEditora() {
		listModelEditora.removeAllElements();
		listEditora.setVisible(ProcessamentoDados.VERDADEIRO);
		scrollPane.setVisible(ProcessamentoDados.VERDADEIRO);
		EditoraService editoraService = new EditoraService();
		List<Editora> listaEditora = editoraService.carregarListaEditora(textFieldNomeEditora.getText());
		editoraSelecionada = new Integer[listaEditora.size()];
		for ( int i = 0 ; i < listaEditora.size(); i++ ) {
			listModelEditora.addElement(listaEditora.get(i).getNome());
			editoraSelecionada[i] = listaEditora.get(i).getId();
		}
		listEditora.setModel(listModelEditora);
	}

	private boolean verificarDigitacao() {
		
		if (ProcessamentoDados.digitacaoCampo(textFieldNome.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do nome do Autor ", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroNome.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		if ( textFieldNome.getText().length() > 80  ) {
			ProcessamentoDados.showMensagem("Erro na digitação do nome do Autor", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroNome.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if (ProcessamentoDados.digitacaoCampo(textFieldEndereco.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação no endereço do Autor ", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroEndereco.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		if ( textFieldEndereco.getText().length() > 80  ) {
			ProcessamentoDados.showMensagem("Erro na digitação no endereço do Autor", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroNome.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
	
		if (ProcessamentoDados.digitacaoCampo(textFieldBairro.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação no bairro do Autor ", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroBairro.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if ( textFieldBairro.getText().length() > 80  ) {
			ProcessamentoDados.showMensagem("Erro na digitação no bairro do Autor", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroBairro.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}

		if (ProcessamentoDados.digitacaoCampo(textFieldCidade.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação no nome da cidade do Autor ", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroCidade.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if ( textFieldCidade.getText().length() > 80  ) {
			ProcessamentoDados.showMensagem("Erro na digitação no nome da cidade do Autor", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroNome.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if (ProcessamentoDados.digitacaoCampoFormatado(textFieldCep.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do Cep", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroCep.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if (ProcessamentoDados.digitacaoCampo(textFieldNomeEditora.getText())) {
			ProcessamentoDados.showMensagem("Editora não selecionada", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			return ProcessamentoDados.VERDADEIRO;
		}
	
		if ( ProcessamentoDados.digitacaoCampoData(dateChooserAutor.getDate())){
			ProcessamentoDados.showMensagem("Erro na digitação do data de nascimento do Autor", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroData.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if (ProcessamentoDados.digitacaoCampoFormatado(textFieldCPF.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do Cpf do Autor", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroCpf.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
	
		if (ProcessamentoDados.digitacaoCampoFormatado(textFieldRG.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do RG do Autor", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroRg.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if (ProcessamentoDados.digitacaoCampo(comboBoxAutor.getSelectedItem().toString())) {
			ProcessamentoDados.showMensagem("Erro na digitação do sexo do Autor", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroCep.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if (ProcessamentoDados.digitacaoCampoFormatado(textFieldTelefoneFixo.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do telefone do Autor", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroTelFixo.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if (ProcessamentoDados.digitacaoCampoFormatado(textFieldCelular.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do celular do Autor", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroTelCelular.setVisible(ProcessamentoDados.VERDADEIRO);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		return ProcessamentoDados.FALSO;
	}
	
	private void pegarDadosAutorDaTela() {
		autor = new Autor();
		if (codigo !=0 ) {
			autor.setId(codigo);
		}
		
		autor.setNome(textFieldNome.getText());
		autor.setRua(textFieldEndereco.getText());
		autor.setBairro(textFieldBairro.getText());
		autor.setCidade(textFieldCidade.getText());
		autor.setCep(textFieldCep.getText());
		autor.setDataNascimento(dateChooserAutor.getDate());
		autor.setCpf(textFieldCPF.getText());
		autor.setRg(textFieldRG.getText());
		autor.setSexo(comboBoxAutor.getSelectedItem().toString());
		autor.setTelefoneFixo(textFieldTelefoneFixo.getText());
		autor.setTelefoneCelular(textFieldCelular.getText());
		autor.setEditora(editora);
	
	}
	
	private void incluirAutor() {
	    AutorService autorService = new AutorService();	
		autorService.save(autor);
		tabelaAutorModel.saveAutor(autor);
        tabelaAutores.setModel(tabelaAutorModel);	
        tabelaAutorModel.fireTableDataChanged();
		limparDadosDigitacao();

	}
	
	private void alterarAutor() {
		AutorService autorService = new AutorService();
		autorService.update(autor);
		limparDadosDigitacao();
		tabelaAutorModel.updateAutor(autor, this.linha);
		tabelaAutores.setModel(tabelaAutorModel);
		tabelaAutorModel.fireTableDataChanged();
	}
	
	private void excluirAutor() {
		AutorService autorService = new AutorService();
		autorService.delete(autor);
		limparDadosDigitacao();
		tabelaAutorModel.removeAutor(this.linha);
		tabelaAutores.setModel(tabelaAutorModel);
		tabelaAutorModel.fireTableDataChanged();
	}

    
	private void buscarAutor() {
		autor = new Autor();
		autor = this.tabelaAutorModel.getAutor(this.linha);
		codigo = autor.getId();
		textFieldNome.setText(autor.getNome());
		textFieldEndereco.setText(autor.getRua());
		textFieldBairro.setText(autor.getBairro());
		textFieldCidade.setText(autor.getCidade());
		textFieldCep.setText(autor.getCep());
		dateChooserAutor.setDate(autor.getDataNascimento());
		textFieldCPF.setText(autor.getCpf());
		textFieldRG.setText(autor.getRg());
		textFieldTelefoneFixo.setText(autor.getTelefoneFixo());
		textFieldCelular.setText(autor.getTelefoneCelular());
		comboBoxAutor.setSelectedItem(autor.getSexo());
		textFieldNomeEditora.setText(autor.getEditora().getNome());
	}
	
	private void limparDadosDigitacao() {
		textFieldNome.setText("");
		textFieldEndereco.setText("");
		textFieldBairro.setText("");
		textFieldCidade.setText("");
		textFieldCep.setText("");
		textFieldCPF.setText("");
		textFieldRG.setText("");
		textFieldTelefoneFixo.setText("");
		textFieldCelular.setText("");
		dateChooserAutor.setDate(new Date());
		
	}
	
	

	private void initComponents() {

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AutorFrame.class.getResource(ProcessamentoDados.SHOW_ICON_MENU_AUTOR)));
		setTitle("Cadastro de Autores");
		setBounds(100, 100, 1029, 632);
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setOpaque(false);
		panelPrincipal.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelPrincipal.setBounds(44, 11, 922, 483);
		contentPane.add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(116, 51, 37, 14);
		panelPrincipal.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(153, 48, 371, 20);
		panelPrincipal.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JLabel lblEndereco = new JLabel("Endere\u00E7o:");
		lblEndereco.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEndereco.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEndereco.setBounds(71, 98, 81, 14);
		panelPrincipal.add(lblEndereco);
		
		textFieldEndereco = new JTextField();
		textFieldEndereco.setBounds(153, 95, 661, 20);
		panelPrincipal.add(textFieldEndereco);
		textFieldEndereco.setColumns(10);
		
		lblBairro = new JLabel("Bairro:");
		lblBairro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBairro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBairro.setBounds(107, 142, 46, 14);
		panelPrincipal.add(lblBairro);
		
		textFieldBairro = new JTextField();
		textFieldBairro.setBounds(153, 139, 661, 20);
		panelPrincipal.add(textFieldBairro);
		textFieldBairro.setColumns(10);
		
		lblCidade = new JLabel("Cidade:");
		lblCidade.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCidade.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCidade.setBounds(108, 182, 46, 14);
		panelPrincipal.add(lblCidade);
		
		textFieldCidade = new JTextField();
		textFieldCidade.setBounds(155, 179, 659, 20);
		panelPrincipal.add(textFieldCidade);
		textFieldCidade.setColumns(10);
		
		lblCep = new JLabel("Cep:");
		lblCep.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCep.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCep.setBounds(104, 226, 46, 14);
		panelPrincipal.add(lblCep);
		
		mascara = new MaskFields();
		textFieldCep = new JFormattedTextField(mascara.mascaraCEP(textFieldCep));
		textFieldCep.setBounds(156, 223, 264, 20);
		panelPrincipal.add(textFieldCep);
		textFieldCep.setColumns(10);
		
		JLabel lblCPF = new JLabel("CPF:");
		lblCPF.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCPF.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCPF.setBounds(497, 229, 37, 14);
		panelPrincipal.add(lblCPF);
		
		mascara = new MaskFields();
		textFieldCPF = new JFormattedTextField(mascara.mascaraCPF(textFieldCPF));
		textFieldCPF.setBounds(541, 226, 269, 20);
		panelPrincipal.add(textFieldCPF);
		textFieldCPF.setColumns(10);
		
		JLabel lblRG = new JLabel("RG:");
		lblRG.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRG.setBounds(126, 273, 46, 14);
		panelPrincipal.add(lblRG);
		
		mascara = new MaskFields();
		textFieldRG = new JFormattedTextField(mascara.mascaraRG(textFieldRG));
		textFieldRG.setBounds(154, 270, 266, 20);
		panelPrincipal.add(textFieldRG);
		textFieldRG.setColumns(10);
		
		lblSexo = new JLabel("Sexo:");
		lblSexo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSexo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSexo.setBounds(651, 51, 46, 14);
		panelPrincipal.add(lblSexo);
		
		comboBoxAutor = new JComboBox<String>();
		comboBoxAutor.setModel(new DefaultComboBoxModel<String>(new String[] {"M", "F"}));
		comboBoxAutor.setBounds(711, 48, 99, 20);
		panelPrincipal.add(comboBoxAutor);
		
		lblShowErroNome = new JLabel("");
		lblShowErroNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroNome.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroNome.setBounds(534, 51, 46, 14);
		panelPrincipal.add(lblShowErroNome);
		
		lblShowErroEndereco = new JLabel("");
		lblShowErroEndereco.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroEndereco.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroEndereco.setBounds(824, 98, 46, 14);
		panelPrincipal.add(lblShowErroEndereco);
		
		lblShowErroBairro = new JLabel("");
		lblShowErroBairro.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroBairro.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroBairro.setBounds(824, 142, 46, 14);
		panelPrincipal.add(lblShowErroBairro);
		
		lblShowErroCidade = new JLabel("");
		lblShowErroCidade.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroCidade.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCidade.setBounds(824, 182, 46, 14);
		panelPrincipal.add(lblShowErroCidade);
		
		lblShowErroCep = new JLabel("");
		lblShowErroCep.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroCep.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCep.setBounds(429, 226, 46, 14);
		panelPrincipal.add(lblShowErroCep);
		
		lblShowErroCpf = new JLabel("");
		lblShowErroCpf.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroCpf.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCpf.setBounds(821, 229, 46, 14);
		panelPrincipal.add(lblShowErroCpf);
		
		lblShowErroRg = new JLabel("");
		lblShowErroRg.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroRg.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroRg.setBounds(429, 273, 46, 14);
		panelPrincipal.add(lblShowErroRg);
		
		JLabel lblTelefoneFixo = new JLabel("Telefone Fixo:");
		lblTelefoneFixo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTelefoneFixo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefoneFixo.setBounds(66, 316, 81, 14);
		panelPrincipal.add(lblTelefoneFixo);
		
		mascara = new MaskFields();
		textFieldTelefoneFixo = new JFormattedTextField(mascara.mascaraTelefoneFixo(textFieldTelefoneFixo));
		textFieldTelefoneFixo.setBounds(153, 313, 267, 20);
		panelPrincipal.add(textFieldTelefoneFixo);
		textFieldTelefoneFixo.setColumns(10);
		
		JLabel lblTelefoneMovel = new JLabel("Celular:");
		lblTelefoneMovel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTelefoneMovel.setBounds(494, 313, 46, 14);
		panelPrincipal.add(lblTelefoneMovel);
		
		mascara = new MaskFields();
		textFieldCelular = new JFormattedTextField(mascara.mascaraCelular(textFieldCelular));
		textFieldCelular.setBounds(541, 310, 269, 20);
		panelPrincipal.add(textFieldCelular);
		textFieldCelular.setColumns(10);
		
		lblShowErroTelFixo = new JLabel("");
		lblShowErroTelFixo.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroTelFixo.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroTelFixo.setBounds(429, 316, 46, 14);
		panelPrincipal.add(lblShowErroTelFixo);
		
		lblShowErroTelCelular = new JLabel("");
		lblShowErroTelCelular.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroTelCelular.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroTelCelular.setBounds(822, 313, 46, 14);
		panelPrincipal.add(lblShowErroTelCelular);
		
		lblShowErroSexo = new JLabel("");
		lblShowErroSexo.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroSexo.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroSexo.setBounds(824, 51, 46, 14);
		panelPrincipal.add(lblShowErroSexo);
		
		lblData = new JLabel("Data:");
		lblData.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblData.setHorizontalAlignment(SwingConstants.RIGHT);
		lblData.setDebugGraphicsOptions(DebugGraphics.FLASH_OPTION);
		lblData.setBounds(487, 273, 46, 14);
		panelPrincipal.add(lblData);
		
		dateChooserAutor = new JDateChooser("dd/MM/yyyy","##/##/####",'_');
		dateChooserAutor.setBounds(541, 267, 269, 20);
		panelPrincipal.add(dateChooserAutor);
		
		lblShowErroData = new JLabel("");
		lblShowErroData.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroData.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroData.setBounds(822, 273, 46, 14);
		panelPrincipal.add(lblShowErroData);
		
		JLabel lblEditora = new JLabel("Editora:");
		lblEditora.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEditora.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEditora.setBounds(103, 364, 46, 14);
		panelPrincipal.add(lblEditora);
		
		textFieldNomeEditora = new JTextField();
		textFieldNomeEditora.setBounds(155, 363, 391, 20);
		panelPrincipal.add(textFieldNomeEditora);
		textFieldNomeEditora.setColumns(10);
		
		btnBuscaEditora = new JButton("Editora");
		btnBuscaEditora.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBuscaEditora.setMnemonic(KeyEvent.VK_D);
		btnBuscaEditora.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBuscaEditora.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_SEARCH )));
		btnBuscaEditora.setBounds(608, 360, 89, 23);
		panelPrincipal.add(btnBuscaEditora);
		
		lblShowErroEditora = new JLabel("");
		lblShowErroEditora.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroEditora.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.LBL_ICON_SHOW_ERROR)));
		lblShowErroEditora.setBounds(552, 366, 46, 14);
		panelPrincipal.add(lblShowErroEditora);
		
		listModelEditora = new DefaultListModel<>();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(155, 382, 391, 95);
		panelPrincipal.add(scrollPane);
		listEditora = new JList<String>();
		scrollPane.setViewportView(listEditora);
		listEditora.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		listEditora.setVisible(ProcessamentoDados.FALSO);
		scrollPane.setVisible(ProcessamentoDados.FALSO);
		
		JPanel panelBotoes = new JPanel();
		panelBotoes.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelBotoes.setBounds(44, 514, 922, 55);
		contentPane.add(panelBotoes);
		panelBotoes.setLayout(null);
		
		btnIncluir = new JButton("Incluir");
		btnIncluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnIncluir.setToolTipText("Incluir informa\u00E7\u00F5es do Autor");
		btnIncluir.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_INCLUIR)));
		btnIncluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));

		btnIncluir.setBounds(24, 15, 89, 23);
		panelBotoes.add(btnIncluir);
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAlterar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlterar.setToolTipText("Alterar informa\u00E7\u00E3o do autor");
		btnAlterar.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_ALTERAR)));
		btnAlterar.setBounds(123, 15, 89, 23);
		panelBotoes.add(btnAlterar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		btnExcluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnExcluir.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_EXCLUIR)));
		btnExcluir.setBounds(222, 15, 89, 23);
		panelBotoes.add(btnExcluir);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setToolTipText("Encerrar o programa");
		btnFechar.setIcon(new ImageIcon(AutorFrame.class.getResource(ProcessamentoDados.BUTTON_ICON_FECHAR)));
		
		btnFechar.setBounds(321, 15, 89, 23);
		panelBotoes.add(btnFechar);
		
		lblShowErroBairro.setVisible(ProcessamentoDados.FALSO);
		lblShowErroCep.setVisible(ProcessamentoDados.FALSO);
		lblShowErroCidade.setVisible(ProcessamentoDados.FALSO);
		lblShowErroCpf.setVisible(ProcessamentoDados.FALSO);
		lblShowErroEndereco.setVisible(ProcessamentoDados.FALSO);
		lblShowErroNome.setVisible(ProcessamentoDados.FALSO);
		lblShowErroRg.setVisible(ProcessamentoDados.FALSO);
		lblShowErroSexo.setVisible(ProcessamentoDados.FALSO);
		lblShowErroTelCelular.setVisible(ProcessamentoDados.FALSO);
		lblShowErroTelFixo.setVisible(ProcessamentoDados.FALSO);
		lblShowErroData.setVisible(ProcessamentoDados.FALSO);
		lblShowErroEditora.setVisible(ProcessamentoDados.FALSO);
		
		
	}
}
