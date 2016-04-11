package mancala;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardScreen extends JFrame {

	private Container container;
	
	public BoardScreen() {
		setTitle("Mancala");
		setSize(1100, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		//setLayout(new BorderLayout());
		
		setIconImage(new ImageIcon(getClass().getResource("/mancala.jpg"))
		.getImage());

		setContentPane(new JLabel(new ImageIcon(getClass().getResource(
				"/mancala board.jpg"))));
		container = getContentPane();
		container.setLayout(new BorderLayout());
		
		addPanels();
	}

	private void addPanels() {
		JPanel top, bottom, center;
		top = new JPanel(new BorderLayout());
		bottom = new JPanel(new BorderLayout());
		center = new JPanel(new BorderLayout());
		//top.setOpaque(false);
		//bottom.setOpaque(false);
		center.setOpaque(false);
		add(top, BorderLayout.PAGE_START);
		add(bottom, BorderLayout.PAGE_END);
		add(center, BorderLayout.CENTER);
		addTopPanel(top);
		addBottomPanel(bottom);
		addCenterPanel(center);
	}

	private void addCenterPanel(JPanel center) {
		
	}

	private void addBottomPanel(JPanel bottom) {
		JLabel black = new JLabel();
		black.setBackground(Color.GREEN);
		bottom.add(black,  BorderLayout.PAGE_END);
	}

	private void addTopPanel(JPanel top) {
		JLabel black = new JLabel();
		black.setBackground(Color.ORANGE);
		top.add(black,  BorderLayout.PAGE_START);
	}
}
