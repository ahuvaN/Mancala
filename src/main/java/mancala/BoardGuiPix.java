package mancala;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel optionsPanel, gamePanel, statsPanel, cupsPanel, cupPanel1, cupPanel2, goalPanel1, goalPanel2;
	private JButton newGameButton;
	private JComponent[] cupComponents;
	private JLabel statsLabel1, statsLabel2, descriptionLabel;

	private Board board;
	private String playerName1, playerName2;
	private int currentPlayer, wins1, wins2, winner;

	private LineBorder border;
	private Font font1, font2;

	public BoardGuiPix(String name1, String name2) {
		setTitle("Mancala");
		setSize(1100, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		optionsPanel = new JPanel();
		gamePanel = new JPanel(new BorderLayout());
		cupsPanel = new JPanel();
		cupPanel1 = new JPanel(new FlowLayout());
		cupPanel2 = new JPanel(new FlowLayout());
		goalPanel1 = new JPanel(new GridBagLayout());
		goalPanel2 = new JPanel(new GridBagLayout());
		statsPanel = new JPanel(new BorderLayout());

		newGameButton = new JButton("New Game");
		playerName1 = name1;
		playerName2 = name2;
		statsLabel1 = new JLabel(getPlayerName(1) + " Wins: " + wins1);
		statsLabel2 = new JLabel(getPlayerName(2) + " Wins: " + wins2);
		descriptionLabel = new JLabel();
		cupComponents = new JComponent[14];

		board = new Board();
		currentPlayer = 1;
		wins1 = 0;
		wins2 = 0;

		this.setIconImage(new ImageIcon(getClass().getResource("/mancala.jpg")).getImage());
		border = new LineBorder(Color.black, 8, true);
		font1 = new Font("Rockwell Extra Bold", Font.PLAIN, 28);
		font2 = new Font("Calibri", Font.PLAIN, 38);

		add();
		format();
		addActionListeners();
		resetBoard();
	}

	public void format() {
		optionsPanel.setBackground(Color.gray);

		newGameButton.setFont(font1);
		newGameButton.setBackground(Color.black);
		newGameButton.setForeground(Color.red);
	
		gamePanel.setBorder(border);

		cupsPanel.setLayout(new BoxLayout(cupsPanel, BoxLayout.Y_AXIS));
		cupsPanel.setBackground(new Color(139, 69, 19));
		cupPanel1.setBackground(new Color(139, 69, 19));
		cupPanel2.setBackground(new Color(139, 69, 19));
		goalPanel1.setBackground(new Color(139, 69, 19));
		goalPanel2.setBackground(new Color(139, 69, 19));

		// statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
		statsPanel.setBackground(Color.gray);

		statsLabel1.setFont(font1);
		statsLabel1.setForeground(Color.red);
		statsLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		statsLabel2.setFont(font1);
		statsLabel2.setForeground(Color.red);
		statsLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		descriptionLabel.setFont(font2);
		descriptionLabel.setForeground(Color.YELLOW);
		descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);

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
		add(optionsPanel, BorderLayout.NORTH);

		cupsPanel.add(cupPanel2);
		cupsPanel.add(cupPanel1);

		gamePanel.add(cupsPanel, BorderLayout.CENTER);
		gamePanel.add(goalPanel1, BorderLayout.EAST);
		gamePanel.add(goalPanel2, BorderLayout.WEST);
		gamePanel.add(cupsPanel, BorderLayout.CENTER);
		add(gamePanel, BorderLayout.CENTER);

		for (int i = 0; i < 6; i++) {
			cupComponents[i] = new CupComponent();
			cupPanel1.add(cupComponents[i]);
			cupComponents[i].setToolTipText(Integer.toString(board.getContent(i)));
		}
		for (int i = 12; i >= 7; i--) {
			cupComponents[i] = new CupComponent();
			cupPanel2.add(cupComponents[i]);
			cupComponents[i].setToolTipText(Integer.toString(board.getContent(i)));
		}

		cupComponents[6] = new GoalComponent();
		cupComponents[6].setToolTipText(Integer.toString(board.getContent(6)));
		cupComponents[13] = new GoalComponent();
		cupComponents[13].setToolTipText(Integer.toString(board.getContent(13)));

		goalPanel1.add(cupComponents[6], new GridBagConstraints());
		goalPanel2.add(cupComponents[13], new GridBagConstraints());

		statsPanel.add(statsLabel1, BorderLayout.EAST);
		// statsPanel.add(Box.createRigidArea(new Dimension(40, 0)));
		statsPanel.add(statsLabel2, BorderLayout.WEST);
		// statsPanel.add(Box.createRigidArea(new Dimension(70, 0)));
		statsPanel.add(descriptionLabel, BorderLayout.NORTH);
		add(statsPanel, BorderLayout.SOUTH);
	}

	// called by action listener
	public void turn(int index) {
		boolean goalTurn = board.distribute(index);
		// returns true if landed in a goal
		resetCups();
		repaint();

		int piecesAdded = board.checkForMoves();
		if (piecesAdded != 0) {
			JOptionPane.showMessageDialog(null,
					"Left over peices added to " + getPlayerName(piecesAdded) + "'s goal!!");
			repaint();
		}
		if (board.checkGame()) {
			winner = board.calculateWinner();
			switch (winner) {
			case 0:
				changeDescription(4);
				break;
			case 1:
				wins1++;
				displayWinner();
				break;
			case 2:
				wins2++;
				displayWinner();
				break;
			}
			repaint();
			return;
		}
		if (!goalTurn) {
			currentPlayer = board.switchPlayer();
			changeDescription(1);
			disableCups();
			repaint();
			return;
		}
		changeDescription(3);
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
		changeDescription(2);
		JOptionPane.showMessageDialog(null, getPlayerName(1) + ": " + board.getContent(6) + " points\n"
				+ getPlayerName(2) + ": " + board.getContent(13) + " points\n\n" + getPlayerName(winner) + " won!!");
		statsLabel1.setText(getPlayerName(1) + " Wins: " + wins1);
		statsLabel2.setText(getPlayerName(2) + " Wins: " + wins2);
	}

	public void changeDescription(int code) {
		switch (code) {
		case 1:
			descriptionLabel.setText(getPlayerName(currentPlayer) + "'s Turn...");
			break;
		case 2:
			descriptionLabel.setText("**** GREAT JOB " + getPlayerName(winner) + "!!! ****");
			break;
		case 3:
			descriptionLabel.setText(getPlayerName(currentPlayer) + " landed in the goal - player goes again");
			break;
		case 4:
			descriptionLabel.setText("Tie Game - no one wins!");
		}
		resetCups();
	}

	public void resetCups() {
		for (int i = 0; i < 14; i++) {
			if (i != 6 && i != 13) {
				((CupComponent) cupComponents[i]).setCount(board.getContent(i));
			} else {
				((GoalComponent) cupComponents[i]).setCount(board.getContent(i));
			}
		}
		for (int i = 0; i < 14; i++) {
			cupComponents[i].setToolTipText(Integer.toString(board.getContent(i)));
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
		changeDescription(1);
		for (int i = 0; i < 6; i++) {
			cupComponents[i].setEnabled(true);
		}
		for (int i = 7; i < 13; i++) {
			cupComponents[i].setEnabled(false);
		}
	}

	public String getPlayerName(int player) {
		if (player == 1) {
			return playerName1;
		} else {
			return playerName2;
		}
	}

	/*public static void main(String[] args) {
		new BoardGuiPix("Leah", "Elise").setVisible(true);
	}*/

}
