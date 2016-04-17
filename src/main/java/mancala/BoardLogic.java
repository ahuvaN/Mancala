package mancala;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JOptionPane;

//logic of a computer mancala game 

public class BoardLogic {

	private CupLogic[] board;
	private TopPanel top;
	private BottomPanel bot;
	private GamePanel game;
	private int currentPlayer;
	private int piecesInGoal;// by both combined

	public BoardLogic(TopPanel topP, BottomPanel botP, GamePanel gameP) {
		board = new CupLogic[14];
		currentPlayer = 1;
		piecesInGoal = 0;
		top = topP;
		bot = botP;
		game = gameP;

		for (int i = 0; i < board.length; i++) {
			if (i == 6 || i == 13) {
				board[i] = new GoalLogic();
			} else {
				board[i] = new CupLogic();
			}
		}
	}

	public int switchPlayer() {
		return currentPlayer == 1 ? 2 : 1;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean checkGame() {
		return piecesInGoal == 48;
	}

	public int calculateWinner() {
		if (board[6].getCount() > board[13].getCount()) {
			return 1;
		} else if (board[6].getCount() < board[13].getCount()) {
			return 2;
		}
		return 0;// no winner/draw
	}

	public int getContent(int i) {
		return board[i].getCount();
	}

	public String getCount(int i) {
		return "" + board[i].getCount();
	}

	// method for top panel to convert gui index to logic index
	private int convertGuiToLogic(int gui) {
		switch (gui) {
		case 0:
			return 12;
		case 1:
			return 11;
		case 2:
			return 10;
		case 3:
			return 9;
		case 4:
			return 8;
		case 5:
			return 7;
		}
		return 0;
	}

	// method to convert back logic index to gui index
	private int convertLogicToGui(int logic) {
		switch (logic) {
		case 12:
			return 0;
		case 11:
			return 1;
		case 10:
			return 2;
		case 9:
			return 3;
		case 8:
			return 4;
		case 7:
			return 5;
		}
		return 0;
	}

	public boolean distribute(Cup cup, boolean player2, BoardScreen screen) {
		int pos = Integer.parseInt(cup.getName());
		currentPlayer = player2 ? 2 : 1;
		ArrayList<Image> images = cup.removePieces();

		if (player2) {
			pos = convertGuiToLogic(pos);
			int amount = board[pos].removePieces();
			top.labels[convertLogicToGui(pos) + 1].setText("0");
			if (amount == 0) {
				screen.repaint();
				return true;
			}
			pos++;
			while (amount > 0) {
				if (pos == 13) {
					board[pos].addPiece();
					piecesInGoal++;
					top.labels[0].setText(getCount(13));
					game.west.addPiece(images.remove(0));
					if (amount == 1) {
						screen.repaint();
						return true;
					} else
						pos = -1;
				} else {
					if (pos >= 0 && pos <= 5) {
						board[pos].addPiece();
						game.cupsBot[pos].addPiece(images.remove(0));
						bot.labels[pos].setText(getCount(pos));
					} else if (pos == 6) {
						pos++;
						continue;
					} else if (pos >= 7 && pos < 13) {
						int guiPos = convertLogicToGui(pos);
						board[pos].addPiece();
						game.cupsTop[guiPos].addPiece(images.remove(0));
						top.labels[guiPos + 1].setText(getCount(pos));
					}
				}
				amount--;
				if (amount == 0) {
					// check if landed in empty spot
					if (pos >= 7 && pos <= 12 && board[pos].count == 1) {
						// capture opponents pieces
						int posOfBotLogic = convertLogicToGui(pos);
						if (game.cupsBot[posOfBotLogic].count() > 0) {
							images = game.cupsBot[posOfBotLogic].removePieces();
							images.add(game.cupsTop[posOfBotLogic].removePieces().get(0));
							amount = board[posOfBotLogic].removePieces()
									+ board[pos].removePieces();
							for (Image img : images) {
								game.west.addPiece(img);
							}
							((GoalLogic) board[13]).addToGoal(amount);
							bot.labels[posOfBotLogic].setText("0");
							top.labels[posOfBotLogic + 1].setText("0");
							top.labels[0].setText("" + board[13].count);
							piecesInGoal += amount;
							screen.repaint();
							JOptionPane.showMessageDialog(null, "Captured");
							return false;
						}
					}
				}
				pos++;
			}
		} else {
			int amount = board[pos].removePieces();
			bot.labels[pos].setText("0");
			if (amount == 0) {
				return true;
			}
			pos++;
			while (amount > 0) {
				if (pos == 6) {
					board[pos].addPiece();
					piecesInGoal++;
					game.east.addPiece(images.remove(0));
					bot.labels[6].setText(getCount(pos));
					if (amount == 1) {
						screen.repaint();
						return true;
					}
				} else {
					if (pos >= 0 && pos <= 5) {
						board[pos].addPiece();
						game.cupsBot[pos].addPiece(images.remove(0));
						bot.labels[pos].setText(getCount(pos));
					} else if (pos == 13) {
						pos = 0;
						continue;
					} else if (pos >= 7 && pos <= 12) {
						int posOfTop = convertLogicToGui(pos);
						board[pos].addPiece();
						game.cupsTop[posOfTop].addPiece(images.remove(0));
						top.labels[posOfTop + 1].setText(getCount(pos));
					}
				}
				amount--;
				if (amount == 0) {
					// check if landed in empty spot
					if (pos >= 0 && pos <= 5 && board[pos].count == 1) {
						// capture opponents pieces
						if (game.cupsTop[pos].count() > 0) {
							int posOfTopLogic = convertGuiToLogic(pos);
							images = game.cupsTop[pos].removePieces();
							images.add(game.cupsBot[pos].removePieces().get(0));
							amount = board[posOfTopLogic].removePieces()
									+ board[pos].removePieces();
							for (Image img : images) {
								game.east.addPiece(img);
							}
							((GoalLogic) board[6]).addToGoal(amount);
							top.labels[pos + 1].setText("0");
							bot.labels[pos].setText("0");
							bot.labels[6].setText("" + board[6].count);
							piecesInGoal += amount;
							screen.repaint();
							JOptionPane.showMessageDialog(null, "Captured");
							return false;
						}
					}
				}
				pos++;
			}

		}
		screen.repaint();
		top.repaint();
		bot.repaint();

		return false;
	}

	// add to piecesInGoal and make it return the player pieces added to
	public String checkForMoves() {
		boolean found = false;
		int amount = 0;
		for (int i = 0; i < 6; i++) {
			if (board[i].getCount() != 0) {
				found = true; // found pieces in the cup
				break;
			}
		}
		if (!found) {
			int x = 1;
			for (int i = 12; i >= 7 ; i--) {
				amount += board[i].removePieces();
				int guiPos = convertLogicToGui(i);
				ArrayList<Image> images = game.cupsTop[guiPos].removePieces();
				top.labels[x++].setText("0");
				for (Image img : images) {
					game.west.addPiece(img);
				}
			}
			((GoalLogic) board[13]).addToGoal(amount);
			top.labels[0].setText("" + getCount(13));
			piecesInGoal += amount;
			return "Player 2";
		} // currentPlayer is player2
		found = false;
		amount = 0;
		for (int i = 7; i < 13; i++) {
			if (board[i].getCount() != 0) {
				found = true;
				break;
			}
		}
		if (!found) {
			for (int i = 0; i < 6; i++) {
				amount += board[i].removePieces();
				ArrayList<Image> images = game.cupsBot[i].removePieces();
				bot.labels[i].setText("0");
				for (Image img : images) {
					game.east.addPiece(img);
				}
			}
			((GoalLogic) board[6]).addToGoal(amount);
			bot.labels[6].setText("" + getCount(6));
			piecesInGoal += amount;
			return "Player 1";
		}
		return null;
	}
}
