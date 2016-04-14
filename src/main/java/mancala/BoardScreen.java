package mancala;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BoardScreen extends JFrame {

	private Container container;
	private TopPanel player2;
	private BottomPanel player1; 
	private GamePanel board;
	private int player;
	//private boolean computer;

	
	public BoardScreen(/*boolean cpu*/) {
		setTitle("Mancala");
		setSize(1100, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		setContentPane(new JLabel(new ImageIcon(getClass().getResource(
				"/mancala board.jpg"))));
		container = getContentPane();
		container.setLayout(new BorderLayout());
		
		setIconImage(new ImageIcon(getClass().getResource("/mancala.jpg"))
		.getImage());

		player = 1;
		addPanels();
	}

	private void addPanels() {
		player2 = new TopPanel();
		player1 = new BottomPanel();
		board = new GamePanel(player2, player1, player);
		container.add(player2, BorderLayout.PAGE_START);
		container.add(player1, BorderLayout.PAGE_END);
		container.add(board, BorderLayout.CENTER);
	}

}
