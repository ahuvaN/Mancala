/*package mancala;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class BoardGuiPix extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel optionsPanel, gamePanel, cupsPanel, cupPanel1, cupPanel2,
			goalPanel1, goalPanel2;
	private JButton newGameButton;
	private JComponent[] cupComponents;
	private Container container;
	private Board board;
	private int currentPlayer, winner;

	private Font font;

	public BoardGuiPix() {
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
		
		optionsPanel = new JPanel();
		gamePanel = new JPanel(new BorderLayout());
		cupsPanel = new JPanel();
		cupPanel1 = new JPanel(new FlowLayout());
		cupPanel2 = new JPanel(new FlowLayout());
		goalPanel1 = new JPanel(new GridBagLayout());
		goalPanel2 = new JPanel(new GridBagLayout());

		newGameButton = new JButton("New Game");
		currentPlayer = 1;
		
		cupComponents = new JComponent[14];

		board = new Board();
		
		font = new Font("Calibri", Font.PLAIN, 38);

		add();
		format();
		addActionListeners();
		resetBoard();
	}

	public void format() {
		optionsPanel.setBackground(Color.gray);

		newGameButton.setFont(font);
		newGameButton.setBackground(Color.black);

		cupsPanel.setLayout(new BoxLayout(cupsPanel, BoxLayout.Y_AXIS));
		gamePanel.setBackground(new Color(0,0,0,0));
		
		for (int i = 0; i < cupComponents.length; i++) {
			if (i != 6 && i != 13) {
				cupComponents[i].putClientProperty("index", i);
				cupComponents[i].addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent event) {
						CupComponent cup = (CupComponent) event.getSource();
						int index = (Integer) cup.getClientProperty("index");
						if (!cup.isEnabled() || board.getContent(index) == 0) {
							return;
						}
						turn(index);
					}
				});
			}
		}
	}

	public void add() {
		optionsPanel.add(newGameButton);
		container.add(optionsPanel, BorderLayout.NORTH);

		cupsPanel.add(cupPanel2);
		cupsPanel.add(cupPanel1);
		cupsPanel.setOpaque(false);
		gamePanel.setOpaque(false);
		gamePanel.add(cupsPanel, BorderLayout.CENTER);
		gamePanel.add(goalPanel1, BorderLayout.EAST);
		gamePanel.add(goalPanel2, BorderLayout.WEST);
		gamePanel.add(cupsPanel, BorderLayout.CENTER);
		container.add(gamePanel, BorderLayout.CENTER);

		for (int i = 0; i < 6; i++) {
			cupComponents[i] = new CupComponent();
			cupPanel1.add(cupComponents[i]);
			cupComponents[i].setToolTipText(Integer.toString(board
					.getContent(i)));
		}
		for (int i = 12; i >= 7; i--) {
			cupComponents[i] = new CupComponent();
			cupPanel2.add(cupComponents[i]);
			cupComponents[i].setToolTipText(Integer.toString(board
					.getContent(i)));
		}

		cupComponents[6] = new GoalComponent();
		cupComponents[6].setToolTipText(Integer.toString(board.getContent(6)));
		cupComponents[13] = new GoalComponent();
		cupComponents[13]
				.setToolTipText(Integer.toString(board.getContent(13)));

		goalPanel1.add(cupComponents[6], new GridBagConstraints());
		goalPanel2.add(cupComponents[13], new GridBagConstraints());

	}

	// called by action listener
	public void turn(int index) {
		boolean goalTurn = board.distribute(index);
		// returns true if landed in a goal
		resetCups();
		repaint();

		int piecesAdded = board.checkForMoves();
		if (piecesAdded != 0) {
			JOptionPane.showMessageDialog(null, "Left over pieces added to "
					+ getPlayerName(piecesAdded) + "'s goal!!");
			repaint();
		}
		if (board.checkGame()) {
			winner = board.calculateWinner();
			switch (winner) {
			case 0:
				break;
			case 1:
				displayWinner();
				break;
			case 2:
				displayWinner();
				break;
			}
			repaint();
			return;
		}
		if (!goalTurn) {
			currentPlayer = board.switchPlayer();
			disableCups();
			repaint();
			return;
		}
	}

	private void disableCups() {
		for (int i = 0; i < 13; i++) {
			if (i != 6 && i != 13) {
				if (cupComponents[i].isEnabled()) {
					cupComponents[i].setEnabled(false);
				} else {
					cupComponents[i].setEnabled(true);
				}
			}
		}

	}

	public void displayWinner() {
		JOptionPane.showMessageDialog(null,
				getPlayerName(1) + ": " + board.getContent(6) + " points\n"
						+ getPlayerName(2) + ": " + board.getContent(13)
						+ " points\n\n" + getPlayerName(winner) + " won!!");
		resetCups();
	}

	public void resetCups() {
		for (int i = 0; i < 14; i++) {
			if (i != 6 && i != 13) {
				((CupComponent) cupComponents[i]).setCount(board.getContent(i));
			} else {
				((GoalComponent) cupComponents[i])
						.setCount(board.getContent(i));
			}
		}
		for (int i = 0; i < 14; i++) {
			cupComponents[i].setToolTipText(Integer.toString(board
					.getContent(i)));
		}
		repaint();
	}

	public void addActionListeners() {
		newGameButton.addActionListener(new ActionListener() {
			// @Override
			public void actionPerformed(ActionEvent e) {
				resetBoard();
				resetCups();
			}
		});
	}

	public void resetBoard() {
		board.resetBoard();
		repaint();
		currentPlayer = 1;
		resetCups();
		for (int i = 0; i < 6; i++) {
			cupComponents[i].setEnabled(true);
		}
		for (int i = 7; i < 13; i++) {
			cupComponents[i].setEnabled(false);
		}
	}

	public String getPlayerName(int player) {
		if (player == 1) {
			return "Player 1";
		} else {
			return "Player 2";
		}
	}

}*/
