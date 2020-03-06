
public class Model extends java.util.Observable {
	
	// These represent the boards of each player
	// ~ is water, a is the 5 length, b is the 4 length, c is the 3 length
	// d is the other 3 length, e is the 2 length, capital letter is a hit ship, m is a missed shot, x is a sunken ship
	private char[][] player1board;
	private char[][] player2board;
	
	// An integer that stores whether there is a winner
	// 0 is no winner, 1 is player 1, 2 is player 2
	private int winner;
	
	// An integer that stores whose turn it is
	// 1 is player 1, 2 is player 2, 3 is neither (transitioning)
	private int turn;
	
	// An integer that keeps track of what the next turn is (to ensure transition turns work)
	// 1 is player 1, 2 is player 2
	private int nextTurn;
	
	// A boolean that keeps track of whether we are in the setup phase
	// True if we are in setup, false otherwise
	private boolean setup;
	
	// An integer that represents how many unplaced ships there are for each player
	// Store the unplaced ships for each player
	private int player1Unplaced = 5;
	private int player2Unplaced = 5;

	// Constructor that sets up the Model
	public Model() {
		player1board = new char[10][10];
		player2board = new char[10][10];

		winner = 0;
		turn = 1;
		nextTurn = 2;
		setup = true;

		// Initialize both boards to water
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				setBoardValue(1, i, j, '~');
				setBoardValue(2, i, j, '~');
			}
		}
	}

	// Takes a player, row, column, and tile, and changes the tile at [row][col] on the given players board to tile
	public void setBoardValue(int player, int row, int col, char tile) {
		if (player == 1) {
			player1board[row][col] = tile;
		} else if (player == 2) {
			player2board[row][col] = tile;
		}

		setChanged();
		notifyObservers();
	}
	
	// Takes a player, row, and column, and returns the tile at that position
	public char getBoardValue(int player, int row, int col) {
		if (player == 1) {
			return player1board[row][col];
		} else {
			return player2board[row][col];
		}
	}

	// Returns the length of the next unplaced ship for player 1, based on the current value of player1Unplaced
	public int getNextUnplaced1() {

		int nextUnplaced;

		switch(player1Unplaced) {

		case(5):
			nextUnplaced = 5;
			break;
		case(4):
			nextUnplaced = 4;
			break;
		case(3):
			nextUnplaced = 3;
			break;
		case(2):
			nextUnplaced = 3;
			break;
		case(1):
			nextUnplaced = 2;
			break;
		default:
			nextUnplaced = -1;
			break;
		}

		return nextUnplaced;
	}

	// Returns the length of the next unplaced ship for player 2, based on the current value of player2Unplaced
	public int getNextUnplaced2() {

		int nextUnplaced;

		switch(player2Unplaced) {
		case(5):
			nextUnplaced = 5;
			break;
		case(4):
			nextUnplaced = 4;
			break;
		case(3):
			nextUnplaced = 3;
			break;
		case(2):
			nextUnplaced = 3;
			break;
		case(1):
			nextUnplaced = 2;
			break;
		default:
			nextUnplaced = -1;
			break;
		}

		return nextUnplaced;
	}
	
	/* Methods for manipulating player1Unplaced and player2Unplaced */
	
	public void decrementUnplaced1() {
		player1Unplaced--;
	}
	
	public void decrementUnplaced2() {
		player2Unplaced--;
	}
	
	public void incrementUnplaced1() {
		player1Unplaced++;
	}
	
	public void incrementUnplaced2() {
		player2Unplaced++;
	}
	
	/* End methods for manipulating player1Unplaced and player2Unplaced */
	
	/* Begin getters and setters */
	public void setWinner(int winner) {
		this.winner = winner;
		setChanged();
		notifyObservers();
	}

	public void setTurn(int turn) {
		this.turn = turn;
		setChanged();
		notifyObservers();
	}

	public void setSetup(boolean setup) {
		this.setup = setup;
		setChanged();
		notifyObservers();
	}

	public char[][] getPlayer1board() {
		return player1board;
	}

	public char[][] getPlayer2board() {
		return player2board;
	}

	public int getWinner() {
		return winner;
	}

	public int getTurn() {
		return turn;
	}

	public boolean isSetup() {
		return setup;
	}
	
	public int getUnplacedNumber1() {
		return player1Unplaced;
	}
	
	public int getUnplacedNumber2() {
		return player2Unplaced;
	}
	
	public int getNextTurn() {
		return nextTurn;
	}

	public void setNextTurn(int nextTurn) {
		this.nextTurn = nextTurn;
	}
	
	/* End getters and setters */
}
