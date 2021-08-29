package com.projeto.view.autor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

import com.projeto.model.model.Autor;
import com.projeto.model.service.AutorService;
import com.projeto.util.ProcessamentoDados;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class AutorFrame extends JFrame {


	private static final long serialVersionUID = 6717485292269251800L;
	
	private JPanel contentPane;
	private JTextField textFieldCodigo;
	private JTextField textFieldNome;
	private JButton btnFechar;
	private JButton btnNovo;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JTextField textFieldEndereco;
	private JComboBox comboBoxAutor;

	private Autor autor;
	private AutorService autorService; 
	private JLabel lblBairro;
	private JTextField textFieldBairro;
	private JLabel lblCidade;
	private JTextField textFieldCidade;
	private JLabel lblCep;
	private JTextField textFieldCep;
	private JTextField textFieldTelefoneFixo;
	private JTextField textFieldCelular;
	private JTextField textFieldCPF;
	private JTextField textFieldRG;
	private JLabel lblSexo;
	
	
	public AutorFrame() {
		
		autorService = new AutorService();
		autor = new Autor();
		
	    initComponents();
	    createEvents();
	}



	private void createEvents() {
		
		btnNovo.addActionListener(new ActionListener() {
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
	
	
	private boolean verificarDigitacao() {
		
		if (ProcessamentoDados.digitacaoCampo(textFieldNome.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do nome do autor ", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if ( textFieldNome.getText().length() > 80  ) {
			ProcessamentoDados.showMensagem("Erro na digitação do nome do Autor", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if (ProcessamentoDados.digitacaoCampo(textFieldCep.getText())) {
			ProcessamentoDados.showMensagem("Erro na digitação do Cep", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		if ( textFieldCep.getText().length() > 20  ) {
			ProcessamentoDados.showMensagem("Erro na digitação no CEP", "Erro de digitação", JOptionPane.ERROR_MESSAGE);
            return ProcessamentoDados.VERDADEIRO;
		}
		
		
		return ProcessamentoDados.FALSO;
	}
	
	private void pegarDadosAutorDaTela() {
		
		autor.setNome(textFieldNome.getText());
		autor.setRua(textFieldEndereco.getText());
		autor.setBairro(textFieldBairro.getText());
		autor.setCidade(textFieldCidade.getText());
		autor.setCep(textFieldCep.getText());
		autor.setCpf(textFieldCPF.getText());
		autor.setRg(textFieldRG.getText());
		autor.setTelefoneFixo(textFieldTelefoneFixo.getText());
		autor.setTelefoneCelular(textFieldCelular.getText());
		autor.setSexo(comboBoxAutor.getSelectedItem().toString());
		
	}
	
	private void incluirAutor() {
		autorService.save(autor);
		limparDadosDigitacao();
	}
	
	private void alterarAutor() {
		autorService.update(autor);
		limparDadosDigitacao();
	}
	
	private void excluirAutor() {
		autorService.delete(autor);
		limparDadosDigitacao();
	}

    
	private void buscarAutor() {
		Integer id = 0;
		if(!textFieldCodigo.getText().equals("")) {
			id = ProcessamentoDados.converterParaInteiro(textFieldCodigo.getText());
			
			
			autor = autorService.findAutorById(id);
			
			
			textFieldNome.setText(autor.getNome());
			textFieldEndereco.setText(autor.getRua());
			textFieldBairro.setText(autor.getBairro());
			textFieldCidade.setText(autor.getCidade());
			textFieldCep.setText(autor.getCep());
			textFieldCPF.setText(autor.getCpf());
			textFieldRG.setText(autor.getRg());
			textFieldTelefoneFixo.setText(autor.getTelefoneFixo());
			textFieldCelular.setText(autor.getTelefoneCelular());
		}
		
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
	}
	
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1029, 632);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(44, 11, 922, 462);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblCodigo = new JLabel("C\u00F3digo:");
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo.setBounds(107, 76, 46, 14);
		panel.add(lblCodigo);
		
		textFieldCodigo = new JTextField();
		textFieldCodigo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				buscarAutor();
			}
		});
		textFieldCodigo.setBounds(153, 73, 81, 20);
		panel.add(textFieldCodigo);
		textFieldCodigo.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(116, 117, 37, 14);
		panel.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(153, 114, 371, 20);
		panel.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JLabel lblEndereco = new JLabel("Endere\u00E7o:");
		lblEndereco.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEndereco.setBounds(71, 164, 81, 14);
		panel.add(lblEndereco);
		
		textFieldEndereco = new JTextField();
		textFieldEndereco.setBounds(153, 161, 661, 20);
		panel.add(textFieldEndereco);
		textFieldEndereco.setColumns(10);
		
		lblBairro = new JLabel("Bairro:");
		lblBairro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBairro.setBounds(107, 208, 46, 14);
		panel.add(lblBairro);
		
		textFieldBairro = new JTextField();
		textFieldBairro.setBounds(153, 205, 661, 20);
		panel.add(textFieldBairro);
		textFieldBairro.setColumns(10);
		
		lblCidade = new JLabel("Cidade:");
		lblCidade.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCidade.setBounds(108, 248, 46, 14);
		panel.add(lblCidade);
		
		textFieldCidade = new JTextField();
		textFieldCidade.setBounds(155, 245, 659, 20);
		panel.add(textFieldCidade);
		textFieldCidade.setColumns(10);
		
		lblCep = new JLabel("Cep:");
		lblCep.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCep.setBounds(104, 292, 46, 14);
		panel.add(lblCep);
		
		textFieldCep = new JTextField();
		textFieldCep.setBounds(156, 289, 198, 20);
		panel.add(textFieldCep);
		textFieldCep.setColumns(10);
		
		JPanel panelContato = new JPanel();
		panelContato.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelContato.setBounds(128, 337, 686, 60);
		panel.add(panelContato);
		panelContato.setLayout(null);
		
		JLabel lblTelefoneFixo = new JLabel("Telefone Fixo:");
		lblTelefoneFixo.setBounds(10, 21, 81, 14);
		panelContato.add(lblTelefoneFixo);
		
		textFieldTelefoneFixo = new JTextField();
		textFieldTelefoneFixo.setBounds(101, 18, 218, 20);
		panelContato.add(textFieldTelefoneFixo);
		textFieldTelefoneFixo.setColumns(10);
		
		JLabel lblTelefoneMovel = new JLabel("Celular:");
		lblTelefoneMovel.setBounds(348, 21, 46, 14);
		panelContato.add(lblTelefoneMovel);
		
		textFieldCelular = new JTextField();
		textFieldCelular.setBounds(395, 18, 262, 20);
		panelContato.add(textFieldCelular);
		textFieldCelular.setColumns(10);
		
		JLabel lblCPF = new JLabel("CPF:");
		lblCPF.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCPF.setBounds(376, 292, 37, 14);
		panel.add(lblCPF);
		
		textFieldCPF = new JTextField();
		textFieldCPF.setBounds(419, 289, 172, 20);
		panel.add(textFieldCPF);
		textFieldCPF.setColumns(10);
		
		JLabel lblRG = new JLabel("RG:");
		lblRG.setBounds(619, 292, 46, 14);
		panel.add(lblRG);
		
		textFieldRG = new JTextField();
		textFieldRG.setBounds(647, 289, 167, 20);
		panel.add(textFieldRG);
		textFieldRG.setColumns(10);
		
		lblSexo = new JLabel("Sexo:");
		lblSexo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSexo.setBounds(559, 117, 46, 14);
		panel.add(lblSexo);
		
		comboBoxAutor = new JComboBox();
		comboBoxAutor.setModel(new DefaultComboBoxModel(new String[] {"M", "F"}));
		comboBoxAutor.setBounds(619, 114, 99, 20);
		panel.add(comboBoxAutor);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(44, 514, 922, 55);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnNovo = new JButton("Incluir");
		btnNovo.setToolTipText("Incluir informa\u00E7\u00F5es do Autor");
		btnNovo.setIcon(new ImageIcon(AutorFrame.class.getResource("/imagens/book_add.png")));
		btnNovo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));

		btnNovo.setBounds(24, 15, 89, 23);
		panel_1.add(btnNovo);
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlterar.setToolTipText("Alterar informa\u00E7\u00E3o do autor");
		btnAlterar.setIcon(new ImageIcon(AutorFrame.class.getResource("/imagens/book_edit.png")));
		btnAlterar.setBounds(123, 15, 89, 23);
		panel_1.add(btnAlterar);
		
		btnExcluir = new JButton("Excluir");
		
		btnExcluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnExcluir.setIcon(new ImageIcon(AutorFrame.class.getResource("/imagens/book_delete.png")));
		btnExcluir.setBounds(222, 15, 89, 23);
		panel_1.add(btnExcluir);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setToolTipText("Encerrar o programa");
		btnFechar.setIcon(new ImageIcon(AutorFrame.class.getResource("/imagens/saida.png")));
		
		btnFechar.setBounds(321, 15, 89, 23);
		panel_1.add(btnFechar);
		
	}
}
