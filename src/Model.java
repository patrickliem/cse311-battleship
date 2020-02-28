
public class Model extends java.util.Observable {
	// ~ is water, s is ship, h is a hit ship
	private char[][] player1board;
	private char[][] player2board;
	// 0 is no winner, 1 is player 1, 2 is player 2
	private int winner;
	// whose turn is it? 1 or 2?
	private int turn;
	
	public Model() {
		player1board = new char[10][10];
		player2board = new char[10][10];
		winner = 0;
		turn = 1;
		
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
	
	
}
