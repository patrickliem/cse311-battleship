
public class Model extends java.util.Observable {
	// ~ is water, a is the 5 length, b is the 4 length, c is the 3 length
	// d is the other 3 length, e is the 2 length, h is a hit ship, m is a missed shot, x is a sunken ship
	private char[][] player1board;
	private char[][] player2board;
	// 0 is no winner, 1 is player 1, 2 is player 2
	private int winner;
	// whose turn is it? 1, 2, or transitioning (3)?
	private int turn;
	// what is the next turn (if we are currently in a transition turn) 1/2?
	private int nextTurn;
	
	// are we in the setup phase?
	private boolean setup;
	// Store the unplaced ships for each player
	private int player1Unplaced = 5;
	private int player2Unplaced = 5;

	public Model() {
		player1board = new char[10][10];
		player2board = new char[10][10];

		winner = 0;
		turn = 1;
		nextTurn = 2;
		// change this after done testing
		setup = true;

		// Initialize both boards to water
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				setBoardValue(1, i, j, '~');
				setBoardValue(2, i, j, '~');
			}
		}
	}

	public void setBoardValue(int player, int row, int col, char tile) {
		if (player == 1) {
			player1board[row][col] = tile;
		} else if (player == 2) {
			player2board[row][col] = tile;
		}

		setChanged();
		notifyObservers();

		//clearChanged() ????
	}
	
	public char getBoardValue(int player, int row, int col) {
		if (player == 1) {
			return player1board[row][col];
		} else {
			return player2board[row][col];
		}
	}

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

	
	public int getNextTurn() {
		return nextTurn;
	}

	public void setNextTurn(int nextTurn) {
		this.nextTurn = nextTurn;
	}
	
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

}
