package mancala;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private JPanel boardGUI;
	protected Cup[] cupsTop;
	protected Cup[] cupsBot;
	protected CupGoal west, east;
	private Piece[] pieces;
	private TopPanel topp;
	private BottomPanel botp;
	private MouseListener listenerTop, listenerBot;
	private int currentPlayer;
	private BoardLogic board;
	private BoardScreen screen;
	private boolean computer;

	public GamePanel(TopPanel topP, BottomPanel botP, int p,
			BoardScreen boardScreen, boolean cpu) {
		super(new BorderLayout());
		setBackground(this);

		topp = topP;
		botp = botP;
		currentPlayer = p;
		screen = boardScreen;
		board = new BoardLogic(topp, botp, this);
		pieces = new Piece[4];
		computer = cpu;

		initializePieces();
		listeners();
		setUpLayout();

	}

	private void initializePieces() {
		pieces[0] = new Piece("blue");
		pieces[1] = new Piece("green");
		pieces[2] = new Piece("red");
		pieces[3] = new Piece("white");
	}

	private void setBackground(JPanel panel) {
		panel.setBackground(new Color(0, 0, 0, 0));
	}

	private void setUpLayout() {
		addSidePanel();
		boardGUI = new JPanel();
		boardGUI.setLayout(new BoxLayout(boardGUI, BoxLayout.Y_AXIS));
		setBackground(boardGUI);

		JPanel top = new Panel(new FlowLayout(FlowLayout.LEFT, 17, 30), 300,
				210);
		JPanel bot = new Panel(new FlowLayout(FlowLayout.LEFT, 17, 0), 300, 200);

		cupsTop = new Cup[6];
		cupsBot = new Cup[6];

		for (int i = 0; i < 6; i++) {
			cupsTop[i] = new Cup(i, listenerTop, pieces);
			top.add(cupsTop[i]);
			cupsBot[i] = new Cup(i, listenerBot, pieces);
			bot.add(cupsBot[i]);
		}
		// if(computer)
		// top.setEnabled(false);
		add(boardGUI, BorderLayout.CENTER);
		boardGUI.add(top);
		boardGUI.add(bot);
	}

	private void addSidePanel() {
		west = new CupGoal();
		add(west, BorderLayout.WEST);
		east = new CupGoal();
		add(east, BorderLayout.EAST);
	}

	private void listeners() {
		listenerTop = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (currentPlayer == 2 && !computer) {
					Cup cup = (Cup) e.getSource();
					int pos = Integer.parseInt(cup.getName());
					turn(pos, cup, true);
				}
			}
		};

		listenerBot = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (currentPlayer == 1) {
					Cup cup = (Cup) e.getSource();
					int pos = Integer.parseInt(cup.getName());
					turn(pos, cup, false);
				}
			}

		};
	}

	// called by action listener
	public void turn(int index, Cup cup, boolean top) {
		int winner;
		boolean goalTurn = board.distribute(index, cup, top, screen);

		int piecesAdded = board.checkForMoves();
		if (piecesAdded != 0) {
			JOptionPane.showMessageDialog(null,
					"Left over pieces added to other player's goal!!");
		}
		if (board.checkGame()) {
			winner = board.calculateWinner();
			switch (winner) {
			case 0:
				break;
			case 1:
				JOptionPane.showMessageDialog(null, "Player 1 won!!");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Player 1 won!!");
				break;
			}
			return;
		}
		if (!goalTurn) {
			currentPlayer = board.switchPlayer();
			//return;
		}
	}
}
