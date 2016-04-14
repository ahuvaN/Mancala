package mancala;

//logic of a computer mancala game 

public class Board {

	private Cup[] board;
	private int currentPlayer;
	private int start;
	private int piecesWon;// by both combined

	public Board() {
		board = new Cup[14];
		currentPlayer = 1;
		piecesWon = 0;

		for (int i = 0; i < board.length; i++) {
			if (i == 6 || i == 13) {
				board[i] = new Goal();
			} else {
				board[i] = new Cup();
			}
		}
	}

	public void resetBoard() {
		for (int i = 0; i < board.length; i++) {
			board[i].reset();
		}
		currentPlayer = 1;
		piecesWon = 0;
	}

	public int switchPlayer() {
		return currentPlayer == 1 ? 2 : 1;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean checkGame() {
		 return piecesWon == 48;
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

	public boolean distribute(int startPosit) {
		start = startPosit;
		int amount = board[start].removePieces();

		for (int i = 0, position = start + 1; i < amount; i++, position++) {
			if (position == 6) {
				if (currentPlayer == 1) {
					piecesWon++;
					board[position].addPiece();
				} else {
					i--;
				}
			} else if (position == 13) {
				if (currentPlayer == 2) {
					piecesWon++;
					board[position].addPiece();
				} else {
					i--;
				}
			} else {
				board[position].addPiece();
			}

			start = position;
			if (position == 13) {
				position = -1;// 0 after increment
			}
		} // pieces done being distributed
		return checkTurn();
	}

	// checks to see if landed in a goal our landed in an empty cup
	private boolean checkTurn() {
		int amount;
		if (board[start].getCount() == 1) {
			if (start > -1 && start < 6 && currentPlayer == 1) {
				amount = board[start].removePieces();
				amount = amount + board[Math.abs(start - 12)].removePieces();
				System.out.println("amount is " + amount);
				piecesWon = piecesWon + amount;
				((Goal) board[6]).addToGoal(amount);
			} else if (start > 6 && start < 13 && currentPlayer == 2) {
				amount = board[start].removePieces();
				amount = amount + board[12 - start].removePieces();
				System.out.println("amount is " + amount);
				piecesWon = piecesWon + amount;
				((Goal) board[13]).addToGoal(amount);
			}
		}
		// if ended by a goal returns true;
		if (start == 6) {
			if (currentPlayer == 1) {
				return true;
			}
		}
		if (start == 13) {
			if (currentPlayer == 2) {
				return true;
			}
		}

		return false;

	}

	// add to piecesWon and make it return the player pieces added to
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
			((Goal) board[13]).addToGoal(amount);
			piecesWon += amount;
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
			((Goal) board[6]).addToGoal(amount);
			piecesWon += amount;
			return 1;
		}
		return 0;
	}
}
