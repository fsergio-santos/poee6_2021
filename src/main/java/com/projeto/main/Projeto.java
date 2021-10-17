package com.projeto.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.projeto.menu.MenuPrincipal;
import com.projeto.model.service.AutorService;

public class Projeto extends JFrame {

	private static final long serialVersionUID = -305565105572791828L;

	private JPanel contentPane;
	private JTextField nomeTextField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Projeto frame = new Projeto();
	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Projeto() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 838, 563);
		//contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPane);
		//contentPane.setLayout(null);
		initComponents();
		//createEvents();
		
	}

	

	private void initComponents() {
		//AutorFrame autorFrame = new AutorFrame();
		//autorFrame.setVisible(true);
		
		MenuPrincipal menu = new MenuPrincipal();
		menu.setLocationRelativeTo(null);
		menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
		menu.setVisible(true);
		
		
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}

	public JTextField getNomeTextField() {
		return nomeTextField;
	}

	public void setNomeTextField(JTextField nomeTextField) {
		this.nomeTextField = nomeTextField;
	}


}












