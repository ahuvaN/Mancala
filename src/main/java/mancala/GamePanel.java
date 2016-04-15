package mancala;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.BoxLayout;
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
	private boolean computer;
	private BoardLogic board;
	private BoardScreen screen;

	public GamePanel(TopPanel topP, BottomPanel botP, BoardScreen boardScreen,
			boolean cpu) {
		super(new BorderLayout());
		setBackground(this);

		topp = topP;
		botp = botP;
		currentPlayer = 1;
		screen = boardScreen;
		board = new BoardLogic(topp, botp, this);
		pieces = new Piece[4];
		computer = cpu;

		initializePieces();
		listeners(computer);
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
			if (!computer)
				cupsTop[i] = new Cup(i, listenerTop, pieces);
			else
				cupsTop[i] = new Cup(i, null, pieces);
			top.add(cupsTop[i]);
			cupsBot[i] = new Cup(i, listenerBot, pieces);
			bot.add(cupsBot[i]);
		}
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

	private void listeners(boolean cpu) {
		if (!cpu) {
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
					if (!computer || currentPlayer == 2) {
						Cup cup = (Cup) e.getSource();
						turn(cup, true);
					}
				}
			};
		}
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
					turn(cup, false);
				}
			}
		};
	}

	// called by action listener
	public void turn(Cup cup, boolean top) {
		int winner;
		int computerTurn;
		Random rand = new Random();

		boolean goAgain = board.distribute(cup, top, screen);
		// returns true if landed in a goal

		int piecesAdded = board.checkForMoves();
		screen.repaint();
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
				JOptionPane.showMessageDialog(null, "Player 1 wins!!");
				break;
			case 2:
				if (computer) {
					JOptionPane.showMessageDialog(null, "Computer wins!!");
				} else {
					JOptionPane.showMessageDialog(null, "Player 2 wins!!");
				}
				break;
			}
			return;
		}
		if (!goAgain) {
			currentPlayer = switchPlayer();
			if (computer && currentPlayer == 2) {
				computerTurn = rand.nextInt(6 - 0) + 0;
				while (board.getContent(computerTurn) == 0) {
					computerTurn = rand.nextInt(6 - 0) + 0;
				}
				turn(cupsTop[computerTurn], true);
			}
			return;
		} else { // goAgain

			if (computer && currentPlayer == 2) {
				computerTurn = rand.nextInt(6 - 0) + 0;
				while (board.getContent(computerTurn) == 0) {
					computerTurn = rand.nextInt(6 - 0) + 0;
				}
				turn(cupsTop[computerTurn], true);
			} else {
				return;
			}
		}
		currentPlayer = switchPlayer();
		return;
	}

	public int switchPlayer() {
		currentPlayer = board.switchPlayer();
		if (currentPlayer == 1) {
			botp.highlight();
			topp.unHighlight();
		} else if (currentPlayer == 2) {
			topp.highlight();
			botp.unHighlight();
		}
		return currentPlayer;
	}
}
