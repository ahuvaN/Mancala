package mancala;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CenterPanel extends JPanel {

	private JPanel boardGUI;
	private Bowl[] cupsTop;
	private Bowl[] cupsBot;
	private Piece[] pieces;
	private TopPanel topp;
	private BottomPanel botp;
	private MouseListener listenerTop, listenerBot;
	private int currentPlayer;
	private Board board;

	public CenterPanel(TopPanel topP, BottomPanel botP, int p) {
		super(new BorderLayout());
		setBackground(this);

		topp = topP;
		botp = botP;
		currentPlayer = p;
		board = new Board();
		pieces = new Piece[4];
		
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

		JPanel top = new Panel(new FlowLayout(FlowLayout.LEFT, 17, 30), 300, 210);
		JPanel bot = new Panel(new FlowLayout(FlowLayout.LEFT, 17, 0), 300, 200);

		cupsTop = new Bowl[6];
		cupsBot = new Bowl[6];

		for (int i = 0; i < 6; i++) {
			cupsTop[i] = new Bowl(i, listenerTop, pieces);
			top.add(cupsTop[i]);
			cupsBot[i] = new Bowl(i, listenerBot, pieces);
			bot.add(cupsBot[i]);
		}
		add(boardGUI, BorderLayout.CENTER);
		boardGUI.add(top);
		boardGUI.add(bot);
	}

	private void addSidePanel() {
		BowlGoal west = new BowlGoal();
		add(west, BorderLayout.WEST);
		BowlGoal east = new BowlGoal();
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
				//if (player == 2) {
					Bowl bowl = (Bowl) e.getSource();
					int pos = Integer.parseInt(bowl.getName()) + 1;
					int pieces = topp.reset(pos--);
					turn(pos);
					/*while (pieces > 0 && pos >= 0) {
						topp.addValue(pos--);
						pieces--;
					}*/
					//board.checkForExtraMoves();
				//	player = 1;
				//}
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
				//if (player == 1) {
					Bowl bowl = (Bowl) e.getSource();
					int pos = Integer.parseInt(bowl.getName());
					int pieces = botp.reset(pos++);
					while (pieces > 0 && pos < 7) {
						botp.addValue(pos++);
						pieces--;
					}
					//turn();
				//	player = 2;
				//}
			}

		};
	}

	// called by action listener
	public void turn(int index) {
		int winner;
		boolean goalTurn = board.distribute(index);
		// returns true if landed in a goal
		repaint();

		int piecesAdded = board.checkForMoves();
		if (piecesAdded != 0) {
			JOptionPane.showMessageDialog(null, 
					"Left over pieces added to other player's goal!!");
			repaint();
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
			repaint();
			return;
		}
		if (!goalTurn) {
			currentPlayer = board.switchPlayer();
			//disableCups();
			repaint();
			return;
		}
	}

	public static void main(String[] args) {
		new BoardScreen().setVisible(true);
	}
}
