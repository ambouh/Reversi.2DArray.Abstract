/*NAME: Andres Mbouh
 *COURSE: CMSC132 - 0203
 *
 *HONOR PLEDGE: I pledge on my honor that I have not given or received any 
 *unauthorized assistance on this assignment/examination. 
 *
 *PURPOSE: Tests all possibilities for cases not encountered in publicTest  
 **/
package tests;

import org.junit.*;

import reversi.Piece;
import reversi.Reversi;
import static org.junit.Assert.*;

public class StudentTests {

	// write your student tests in this class

	/* Tests whether the toString method */
	@Test
	public void testPublic1() {
		// prints out the first row, last column properly
		Reversi reversi = new Reversi();

		// System.out.print(reversi.toString());

		assertEquals(reversi.getSquare(0, 0), Piece.NONE);

	}

	/* Tests if validMove is working vertically */
	@Test
	public void testPublic3() {

		Reversi reversi = new Reversi();

		reversi.setSquare(4, 3, Piece.WHITE);
		reversi.setSquare(5, 3, Piece.BLACK);

		// System.out.print(reversi.toString());

		boolean isValid = reversi.validMove(3, 3, Piece.BLACK);
		assertTrue(isValid);
	}

	/* Test if move works vertically - up */
	@Test
	public void testPublic4() {
		Reversi reversi = new Reversi();

		reversi.setSquare(4, 3, Piece.WHITE);
		reversi.setSquare(5, 3, Piece.WHITE);
		reversi.setSquare(6, 3, Piece.BLACK);

		// System.out.print(reversi.toString());

		boolean isValid = reversi.validMove(3, 3, Piece.BLACK);
		assertTrue(isValid);
		Piece color1 = reversi.getTurn();
		reversi.move(3, 3);

		Piece isFlipped = reversi.getSquare(5, 3);

		// System.out.print(reversi.toString());

		assertEquals(color1, isFlipped);

	}

	/* Test if move works vertically - down */
	@Test
	public void testPublic5() {
		Reversi reversi = new Reversi();

		reversi.setSquare(3, 3, Piece.WHITE);
		reversi.setSquare(2, 3, Piece.WHITE);
		reversi.setSquare(1, 3, Piece.BLACK);

		// System.out.print(reversi.toString());

		boolean isValid = reversi.validMove(4, 3, Piece.BLACK);
		assertTrue(isValid);
		Piece color1 = reversi.getTurn();
		reversi.move(4, 3);

		Piece isFlipped = reversi.getSquare(3, 3);

		// System.out.print(reversi.toString());

		assertEquals(color1, isFlipped);

	}

	/* Test if move works Horizontally - left */
	@Test
	public void testPublic6() {
		Reversi reversi = new Reversi();

		reversi.setSquare(3, 2, Piece.WHITE);
		reversi.setSquare(3, 1, Piece.WHITE);
		reversi.setSquare(3, 0, Piece.BLACK);

		// System.out.print(reversi.toString());
		boolean isValid = reversi.validMove(3, 3, Piece.BLACK);
		assertTrue(isValid);

		// show invalid move to make sure all cases works
		reversi.setSquare(3, 0, Piece.NONE);
		isValid = reversi.validMove(3, 3, Piece.BLACK);
		assertFalse(isValid);
	}

	/* Test if move works diagonally left */
	@Test
	public void testPublic7() {
		Reversi reversi = new Reversi();

		reversi.setSquare(6, 0, Piece.BLACK);
		reversi.setSquare(5, 1, Piece.WHITE);
		reversi.setSquare(4, 2, Piece.WHITE);

		// System.out.print(reversi.toString());
		boolean isValid = reversi.validMove(3, 3, Piece.BLACK);
		assertTrue(isValid);
	}

	/*
	 * Replicate figure 3 from project instructions to evaluate the same result,
	 * it test capturing pieces in the game in all possible directions
	 */
	@Test
	public void testPublic8() {
		Reversi reversi = new Reversi();

		reversi.setSquare(7, 3, Piece.BLACK);
		reversi.setSquare(6, 3, Piece.WHITE);
		reversi.setSquare(5, 3, Piece.BLACK);

		// row 4
		reversi.setSquare(4, 2, Piece.BLACK);
		reversi.setSquare(4, 3, Piece.WHITE);
		reversi.setSquare(4, 4, Piece.BLACK);
		reversi.setSquare(4, 5, Piece.BLACK);

		// row 3
		reversi.setSquare(3, 2, Piece.WHITE);
		reversi.setSquare(3, 3, Piece.WHITE);
		reversi.setSquare(3, 4, Piece.WHITE);
		reversi.setSquare(3, 5, Piece.BLACK);

		// row 2
		reversi.setSquare(2, 2, Piece.BLACK);
		reversi.setSquare(2, 4, Piece.WHITE);
		reversi.setSquare(2, 5, Piece.WHITE);
		reversi.setSquare(2, 6, Piece.BLACK);

		// row 1
		reversi.setSquare(1, 3, Piece.WHITE);
		reversi.setSquare(1, 4, Piece.WHITE);

		// row 0
		reversi.setSquare(0, 3, Piece.WHITE);

		// System.out.println(reversi.toString());

		// insert piece in (2,3)
		reversi.move(2, 3, Piece.BLACK);

		assertEquals("7 - - - b - - - -\n" + "6 - - - w - - - -\n"
				+ "5 - - - b - - - -\n" + "4 - - b b b b - -\n"
				+ "3 - - w b b b - -\n" + "2 - - b b b b b -\n"
				+ "1 - - - w w - - -\n" + "0 - - - w - - - -\n"
				+ "  0 1 2 3 4 5 6 7\n", reversi.toString());
		assertEquals(Piece.WHITE, reversi.getTurn());

	}

	/* Tests the end of the game - when no moves are possible */
	@Test
	public void testPublic9() {
		Reversi reversi = new Reversi();

		// populates the entire board
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				reversi.setSquare(row, col, Piece.BLACK);
			}
		}

		assertFalse(reversi.validMove(2, 3, Piece.WHITE));

		int count = reversi.count(Piece.BLACK);

		// asserts that no possible moves could be made by white piece
		// also, the count remains 64 since black piece filled the board
		assertEquals(count, 64);

	}

	/* Tests if distant trapped pieces can be flipped (with "---" in between) */
	@Test
	public void testPublic10() {
		Reversi reversi = new Reversi();

		reversi.reset(Piece.BLACK);

		// System.out.println(reversi.toString());

		// far left to white trapped right
		reversi.move(4, 0, Piece.BLACK);
		assertFalse(reversi.getSquare(4, 0).equals(Piece.BLACK));

		// far right to black trapped left
		reversi.move(4, 7, Piece.WHITE);
		assertFalse(reversi.getSquare(4, 7).equals(Piece.WHITE));

		// far up to white trapped down
		reversi.move(7, 3, Piece.BLACK);
		assertFalse(reversi.getSquare(7, 3).equals(Piece.BLACK));

		// far down to white trapped up
		reversi.move(0, 4, Piece.BLACK);
		assertFalse(reversi.getSquare(0, 4).equals(Piece.BLACK));

		// Diagonal down left to white diagonal TRAPPED up right
		reversi.setSquare(3, 3, Piece.WHITE);
		reversi.move(1, 1, Piece.BLACK);
		assertFalse(reversi.getSquare(1, 1).equals(Piece.BLACK));

	}
}
