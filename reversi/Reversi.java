/*NAME: Andres Mbouh
 *COURSE: CMSC132 - 0203
 *DUE: 2/25/2015
 *
 *HONOR PLEDGE: I pledge on my honor that I have not given or received any 
 *unauthorized assistance on this assignment/examination. 
 *
 *PURPOSE: This class simulates the Reversi board game. It involves two players;
 *white and black pieces. The goal is to have a program that sets, moves and/or
 *flip pieces. The board game is represented in an array that collects objects
 *of Piece class.  
 **/
package reversi;

import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;

public class Reversi {

	// FIELDS
	private Piece[][] board;
	private Piece turnKeeper;
	private String[] directions = { "up", "down", "left", "right", "upLeft",
			"upRight", "downLeft", "downRight" };

	// CONSTRUCTOR
	/*
	 * Reversi() initializes board array to (8x8) and populates it with
	 * Piece.NONE to represent an empty board. Also, sets turnKeeper to
	 * Piece.BLACK because the black piece holds the first turn
	 */
	public Reversi() {
		board = new Piece[8][8];

		// populates the board
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				board[row][col] = Piece.NONE;
			}
		}
		// sets the first turn
		turnKeeper = Piece.BLACK;
	}

	// COPY-CONSTRUCTOR
	/*
	 * Reversi(Reversi otherGame) makes a board that is exactly as the one that
	 * ispassed in, with Piece objects that are totally independent from each
	 * other
	 */
	public Reversi(Reversi otherGame) {
		board = new Piece[8][8];

		// populates current board with otherGame piece (shallow copy)
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				board[row][col] = otherGame.board[row][col];
			}
		}
		turnKeeper = otherGame.turnKeeper;
	}

	/*
	 * setTurn() sets the next turn to type and throws an exception if type is
	 * Piece.NONE
	 */
	public void setTurn(Piece type) throws IllegalArgumentException {

		if (type.isNone())
			throw new IllegalArgumentException();

		turnKeeper = type;
	}

	/* getTurn() returns the player who has got the current turn */
	public Piece getTurn() {
		return turnKeeper;
	}

	/*
	 * setSquare() moves piece of type into desired row and column. Throws a
	 * NoSuchElementException() if row and col is invalid
	 */
	public void setSquare(int row, int col, Piece type)
			throws NoSuchElementException {
		// checks if row and col is invalid
		if ((row < 0 || row > 7) || (col < 0 || col > 7))
			throw new NoSuchElementException();

		board[row][col] = type;

	}

	/*
	 * getSquare() returns the Piece positioned in row and col. Throws a
	 * NoSuchElementException() if row and col is invalid
	 */
	public Piece getSquare(int row, int col) throws NoSuchElementException {

		// checks if row and col is invalid
		if ((row < 0 || row > 7) || (col < 0 || col > 7))
			throw new NoSuchElementException();

		return board[row][col];
	}

	/* count() returns the number of pieces of type in the game */
	public int count(Piece type) {
		int count = 0;

		// iterates through array to count all pieces of type
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (getSquare(row, col).equals(type))
					count++;
			}
		}

		return count;
	}

	/*
	 * reset() resets game back to the beginning with two black pieces on () and
	 * () and with two white pieces on () and (). sets the next turn to type.
	 * Throws illegalArgumentexcep if peice.NONE is passed
	 */
	public void reset(Piece type) throws IllegalArgumentException {
		if (type.isNone())
			throw new IllegalArgumentException();

		setTurn(type);
		Reversi newGame = new Reversi();
		board = newGame.board;

		// reset two black pieces
		setSquare(3, 3, Piece.BLACK);
		setSquare(4, 4, Piece.BLACK);

		// reset two white pieces
		setSquare(4, 3, Piece.WHITE);
		setSquare(3, 4, Piece.WHITE);
	}

	/*
	 * This method returns the string representation of the game at the
	 * particular time it's called. The String will have 162 characters and 9
	 * lines.
	 */
	public String toString() {
		String str = "";
		// prints starting from last row (7) to first
		for (int row = 7; row >= 0; row--) {
			str += row + " ";
			for (int col = 0; col <= 7; col++) {
				str += getSquare(row, col);
				// separates with either blank space or new-line
				if (col < 7)
					str += " ";
				else
					str += "\n";
			}
		}
		// the last 18 character should look like this
		str += "  0 1 2 3 4 5 6 7\n";

		return str;
	}

	/*
	 * validMove() determines if type can move into the desired row and col.
	 * returns false: - if (row, col) is invalid, type is Piece.NONE or move
	 * doesn't trap an opposed Piece
	 */
	public boolean validMove(int row, int col, Piece type) {

		int[] results = { -1, -1, -1 };

		int size = directions.length - 1;
		for (int i = 0; (results[2] == -1) && (i < size); i++) {
			results = moveHelper(directions[i], row, col, type);

			if (results[2] == 1)
				return true;
		}

		return false;

	}

	/* move() calls move() overload method to place a piece type on the board */
	public void move(int row, int col) {

		move(row, col, turnKeeper);

	}

	/*
	 * move() moves current type to desired row and col, and flips every
	 * opponent. Calls moveHelper which determines if move is valid and if so,
	 * the method stores the row, col, and validity in array and returns it
	 */
	public void move(int row, int col, Piece type) {
		int[] results = { -1, -1, -1 };

		for (int dir = 0; dir < directions.length; dir++) {
			results = moveHelper(directions[dir], row, col, type);

			if (results[2] == 1) {
				// moves current type to desired row and col
				board[row][col] = type;

				/* flips every opponent piece */
				// up
				if (directions[dir].equals("up")) {
					for (int flipUp = row + 1; flipUp <= results[0]; flipUp++) {
						board[flipUp][col] = type;
					}
				}
				// down
				if (directions[dir].equals("down")) {
					for (int flipDown = row - 1; flipDown >= results[0]; flipDown--) {
						board[flipDown][col] = type;
					}
				}
				// left
				if (directions[dir].equals("left")) {
					for (int flipLeft = col - 1; flipLeft >= results[1]; flipLeft--) {
						board[row][flipLeft] = type;
					}
				}
				// right
				if (directions[dir].equals("right")) {
					for (int flipRight = col + 1; flipRight <= results[1]; 
							flipRight++) {
						board[row][flipRight] = type;
					}
				}
				// up-left
				if (directions[dir].equals("upLeft")) {
					for (int flipUp = row + 1, flipLeft = col - 1; 
							flipUp <= results[0]
							&& flipLeft >= results[1]; 
							flipUp++, flipLeft--) {
						board[flipUp][flipLeft] = type;
					}
				}
				// up-right
				if (directions[dir].equals("upRight")) {
					for (int flipUp = row + 1, flipRight = col + 1;
							flipUp <= results[0] && flipRight <= results[1]; 
							flipUp++, flipRight++) {
						board[flipUp][flipRight] = type;
					}
				}
				// down-left
				if (directions[dir].equals("downLeft")) {
					for (int flipDown = row - 1, flipLeft = col - 1; 
							flipDown >= results[0] && flipLeft >= results[1]; 
							flipDown--, flipLeft--) {
						board[flipDown][flipLeft] = type;
					}
				}
				// down-right
				if (directions[dir].equals("downRight")) {
					for (int flipDown = row - 1, flipRight = col + 1; 
							flipDown >= results[0]&& flipRight <= results[1]; 
							flipDown--, flipRight++) {
						board[flipDown][flipRight] = type;
					}
				}

				// Changes up current player
				if (type.isBlack())
					turnKeeper = Piece.WHITE;
				if (type.isWhite())
					turnKeeper = Piece.BLACK;

			}
		}
	}

	/*---------HELPER METHOD-----------*/
	/*
	 * moveHelper() determines if a piece type can move into desired row and
	 * col, at a particular direction. To move, a piece MUST have an opposed
	 * piece, and a team piece that traps the opposed piece When true,
	 * 
	 * this method returns an array of 3 elements: - 1st element -
	 * rowColValid[0]: stores row of team piece, - 2nd element - rowColValid[1]:
	 * stores col of team piece, - 3rd element - rowColValid[2]: stores valid
	 * move 1 or -1 (True or false)
	 */
	private int[] moveHelper(String direction, int row, int col, Piece type) {

		// set the array's element to -1 for invalid coordinates and move
		int[] rowColValid = { -1, -1, -1 };

		// checks for out-of-bound coordinates and type of Piece.NONE
		if ((row < 0 || row > 7) || (col < 0 || col > 7) || type.isNone())
			return rowColValid;

		boolean isOpponentFound = false;

		if (direction.equals("up")) {
			for (int goingUp = row + 1; goingUp < 8; goingUp++) {
				if (board[goingUp][col].isNone())
					return rowColValid;

				if (!board[goingUp][col].equals(type)
						&& !board[goingUp][col].isNone()) {
					isOpponentFound = true;
				} else if (board[goingUp][col].equals(type) && isOpponentFound) {
					rowColValid[0] = goingUp;
					rowColValid[1] = col;
					rowColValid[2] = 1;

					return rowColValid;
				}
			}
		}

		if (direction.equals("down")) {
			for (int goingDown = row - 1; goingDown > -1; goingDown--) {
				if (board[goingDown][col].isNone())
					return rowColValid;

				if (!board[goingDown][col].equals(type)
						&& !board[goingDown][col].isNone()) {
					isOpponentFound = true;
				} else if (board[goingDown][col].equals(type)
						&& isOpponentFound) {
					rowColValid[0] = goingDown;
					rowColValid[1] = col;
					rowColValid[2] = 1;

					return rowColValid;

				}
			}

		}

		if (direction.equals("left")) {
			for (int goingLeft = col - 1; goingLeft > -1; goingLeft--) {
				if (board[row][goingLeft].isNone())
					return rowColValid;

				if (!board[row][goingLeft].equals(type)
						&& !board[row][goingLeft].isNone()) {
					isOpponentFound = true;
				} else if (board[row][goingLeft].equals(type)
						&& isOpponentFound) {
					rowColValid[0] = row;
					rowColValid[1] = goingLeft;
					rowColValid[2] = 1;

					return rowColValid;

				}
			}

		}

		if (direction.equals("right")) {
			for (int goingRight = col + 1; goingRight < 8; goingRight++) {
				if (board[row][goingRight].isNone())
					return rowColValid;

				if (!board[row][goingRight].equals(type)
						&& !board[row][goingRight].isNone()) {
					isOpponentFound = true;
				} else if (board[row][goingRight].equals(type)
						&& isOpponentFound) {
					rowColValid[0] = row;
					rowColValid[1] = goingRight;
					rowColValid[2] = 1;

					return rowColValid;

				}
			}

		}

		if (direction.equals("upLeft")) {
			for (int goingUp = row + 1, goingLeft = col - 1; goingUp < 8
					&& goingLeft > -1; goingUp++, goingLeft--) {
				if (board[goingUp][goingLeft].isNone())
					return rowColValid;

				if (!board[goingUp][goingLeft].equals(type)
						&& !board[goingUp][goingLeft].isNone()) {
					isOpponentFound = true;

				} else if (board[goingUp][goingLeft].equals(type)
						&& isOpponentFound) {
					rowColValid[0] = goingUp;
					rowColValid[1] = goingLeft;
					rowColValid[2] = 1;

					return rowColValid;
				}
			}
		}

		if (direction.equals("upRight")) {
			for (int goingUp = row + 1, goingRight = col + 1; goingUp < 8
					&& goingRight < 8; goingUp++, goingRight++) {
				if (board[goingUp][goingRight].isNone())
					return rowColValid;

				if (!board[goingUp][goingRight].equals(type)
						&& !board[goingUp][goingRight].isNone()) {
					isOpponentFound = true;

				} else if (board[goingUp][goingRight].equals(type)
						&& isOpponentFound) {
					rowColValid[0] = goingUp;
					rowColValid[1] = goingRight;
					rowColValid[2] = 1;

					return rowColValid;
				}
			}

		}

		if (direction.equals("downLeft")) {
			for (int goingDown = row - 1, goingLeft = col - 1; goingDown > -1
					&& goingLeft > -1; goingDown--, goingLeft--) {
				if (board[goingDown][goingLeft].isNone())
					return rowColValid;

				if (!board[goingDown][goingLeft].equals(type)
						&& !board[goingDown][goingLeft].isNone()) {
					isOpponentFound = true;
				} else if (board[goingDown][goingLeft].equals(type)
						&& isOpponentFound) {
					rowColValid[0] = goingDown;
					rowColValid[1] = goingLeft;
					rowColValid[2] = 1;

					return rowColValid;

				}
			}
		}

		if (direction.equals("downRight")) {
			for (int goingDown = row - 1, goingRight = col + 1; goingDown > -1
					&& goingRight < 8; goingDown--, goingRight++) {
				if (board[goingDown][goingRight].isNone())
					return rowColValid;

				if (!board[goingDown][goingRight].equals(type)
						&& !board[goingDown][goingRight].isNone()) {
					isOpponentFound = true;
				} else if (board[goingDown][goingRight].equals(type)
						&& isOpponentFound) {
					rowColValid[0] = goingDown;
					rowColValid[1] = goingRight;
					rowColValid[2] = 1;

					return rowColValid;

				}
			}
		}

		return rowColValid;
	}
}
