package mancala;

//logic of a computer mancala game 

public class BoardLogic {

	private CupLogic[] board;
	private TopPanel top;
	private BottomPanel bot;
	private int currentPlayer;
	private int startPos;
	private int piecesInGoal;// by both combined

	public BoardLogic(TopPanel topP, BottomPanel botP) {
		board = new CupLogic[14];
		currentPlayer = 1;
		piecesInGoal = 0;
		top = topP;
		bot = botP;

		for (int i = 0; i < board.length; i++) {
			if (i == 6 || i == 13) {
				board[i] = new GoalLogic();
			} else {
				board[i] = new CupLogic();
			}
		}
	}

	public void resetBoard() {
		for (int i = 0; i < board.length; i++) {
			board[i].reset();
		}
		currentPlayer = 1;
		piecesInGoal = 0;
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
		return 0;// no winner
	}

	public int getContent(int i) {
		return board[i].getCount();
	}

	public String getCount(int i) {
		return "" + board[i].getCount();
	}

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

	private int convertLogicToGui(int gui) {
		switch (gui) {
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

	public boolean distribute(int pos, boolean player2) {
		boolean goalLand = false;

		if (player2 && currentPlayer == 2) {
			startPos = pos;
			pos = convertGuiToLogic(pos);
			int amount = board[pos].removePieces();
			top.labels[convertLogicToGui(pos) + 1].setText("0");
			if (amount == 0) {
				return false;
			}
			pos++;
			while (amount > 0) {
				if (pos == 13) {
					board[pos].addPiece();
					piecesInGoal++;
					top.labels[0].setText(getCount(pos));
					goalLand = true;
					pos = -1;
				} else {
					if (pos >= 0 && pos <= 5) {
						board[pos].addPiece();
						bot.labels[pos].setText(getCount(pos));
					} else if (pos == 6) {
						pos++;
						continue;
					} else if (pos >= 7 && pos <= 13) {
						board[pos].addPiece();
						top.labels[convertLogicToGui(pos) + 1]
								.setText(getCount(pos));
					}
					goalLand = false;
				}
				amount--;
				if (amount == 0) {
					// check if goalLand
					// true- go again
					// else- return
					// check if landed in empty spot
					// check if other side is empty
					// check if winner/draw
					// switch player;
				}
				pos++;
			}
		} else if (!player2 && currentPlayer == 1){
			startPos = pos;
			int amount = board[pos].removePieces();
			bot.labels[pos].setText("0");
			if (amount == 0) {
				return false;
			}
			pos++;
			while (amount > 0) {
				if (pos == 6) {
					board[pos].addPiece();
					piecesInGoal++;
					bot.labels[6].setText(getCount(pos));
					goalLand = true;
				} else {
					if (pos >= 0 && pos <= 5) {
						board[pos].addPiece();
						bot.labels[pos].setText(getCount(pos));
					} else if (pos == 13) {
						pos = 1;
						continue;
					} else if (pos >= 7 && pos <= 13) {
						board[pos].addPiece();
						top.labels[convertLogicToGui(pos) + 1]
								.setText(getCount(pos));
					}
					goalLand = false;
				}
				amount--;
				if (amount == 0) {
					// check if goalLand
					// true- go again
					// else- return
					// check if landed in empty spot
					// check if other side is empty
					// check if winner/draw
					// switch player;
				}
				pos++;
			}

		}
		top.repaint();
		bot.repaint();
	
		return checkTurn();
	}

	// checks to see if landed in a goal our landed in an empty cup
	private boolean checkTurn() {
		int amount;
		if (board[startPos].getCount() == 1) {
			if (startPos > -1 && startPos < 6 && currentPlayer == 1) {
				amount = board[startPos].removePieces();
				amount = amount + board[Math.abs(startPos - 12)].removePieces();
				System.out.println("amount is " + amount);
				piecesInGoal += amount;
				((GoalLogic) board[6]).addToGoal(amount);
				bot.labels[6].setText("" + amount);
			} else if (startPos > 6 && startPos < 13 && currentPlayer == 2) {
				amount = board[startPos].removePieces();
				amount = amount + board[12 - startPos].removePieces();
				System.out.println("amount is " + amount);
				piecesInGoal += amount;
				((GoalLogic) board[13]).addToGoal(amount);
				top.labels[13].setText("" + amount);
			}
		}
		// if ended by a goal returns true;
		if (startPos == 6) {
			if (currentPlayer == 1) {
				return true;
			}
		}
		if (startPos == 13) {
			if (currentPlayer == 2) {
				return true;
			}
		}

		return false;

	}

	// add to piecesInGoal and make it return the player pieces added to
	public int checkForMoves() {
		boolean found = false;
		int amount = 0;
		for (int i = 0; i < 6; i++) {
			if (board[i].getCount() != 0) {
				found = true;
				break;
			}
		}
		if (!found) {
			for (int i = 7; i < 13; i++) {
				amount += board[i].removePieces();
			}
			((GoalLogic) board[13]).addToGoal(amount);
			piecesInGoal += amount;
			return 2;
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
			}
			((GoalLogic) board[6]).addToGoal(amount);
			piecesInGoal += amount;
			return 1;
		}
		return 0;
	}
}
