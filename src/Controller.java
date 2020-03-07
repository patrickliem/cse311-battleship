import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public class Controller implements java.awt.event.MouseListener {

	// Our representations of the Model and View
	private Model model;
	private View currentView;
	
	// This is called when the View sends a message to the Controller
	@Override
	public void mouseClicked(MouseEvent e) {
		currentView.clearError();

		// If we are not in a transition phase
		if (model.getTurn() != 3) {

			// This gets the data from View as a String
			// The String should be formatted as such:
			// If we are in setup phase, it should be <col> <row> <orientation>, e.g. A 1 R
			// Valid orientations are R and D (right and down)
			// If we are not in setup phase, it should be <col> <row>, e.g. A 1
			String entryText = ((JComponent)e.getSource()).getClientProperty("entryText").toString();
			((JComponent)e.getSource()).putClientProperty("entryText", "");

			String[] entryTextElements = entryText.split(" ");
			
			if (!validateEntryStringLength(entryTextElements)) {
				return;
			}

			int currentTurn = model.getTurn();
			int col = convertLetterToInt(entryTextElements[0].toUpperCase());
			int row = -1;
			
			if (!validateColumn(col)) {
				return;
			}

			if (!validateRow(entryTextElements[1])) {
				return;
			}
			row = Integer.parseInt(entryTextElements[1]);

			// If we are in the setup phase, place the ship
			if (model.isSetup()) {

				String orientation = entryTextElements[2].toUpperCase();
				
				if (!validateOrientation(orientation)) {
					return;
				}
				
				placeShip(currentTurn, row, col, orientation);

			// If we are not in the setup phase, check for hits and setup the turn transition
			} else {
				if (!checkForHits(currentTurn, row, col)) {
					return;
				}
				setNextTurnIfGoingIntoTransition(currentTurn);
			}

		} else {
			model.setTurn(model.getNextTurn());
		}
		
	}
	
	// Takes a string and checks whether it is a valid orientation or not
	private boolean validateOrientation(String orientation) {
		if (!(orientation.equals("R") || orientation.equals("D"))) {
			currentView.displayError("Please enter a valid orientation.");
			return false;
		}
		return true;
	}
	
	// Takes a string and checks if it is a valid row (i.e. if it is a number)
	private boolean validateRow(String row) {
		try {
			Integer.parseInt(row);	
		} catch(NumberFormatException excep) {
			currentView.displayError("Please enter a valid row.");
			return false;
		}
		return true;
	}
	
	// Takes an integer and checks if it is a valid column
	private boolean validateColumn(int col) {
		if (col == -1) {
			currentView.displayError("Please enter a valid column.");
			return false;
		}
		return true;
	}
	
	// Takes an entry string from the View, and checks whether it has the correct length
	private boolean validateEntryStringLength(String[] entryTextElements) {
		if (entryTextElements.length != 3 && model.isSetup()) {
			currentView.displayError("Please enter a valid string");
			return false;
		}
		
		if (entryTextElements.length != 2 && !model.isSetup()) {
			currentView.displayError("Please enter a valid string");
			return false;
		}
		
		return true;
	}
	
	// Takes the currentTurn, a row, a column, and an orientation, and places a ship at that place
	private void placeShip(int currentTurn, int row, int col, String orientation) {
		int currentShip;

		if (currentTurn == 1) {
			currentShip = model.getNextUnplaced1();
			model.decrementUnplaced1();
		} else {
			currentShip = model.getNextUnplaced2();
			model.decrementUnplaced2();
		}

		if (!validatePlacement(currentTurn, row-1, col-1, currentShip, orientation)) {

			if (currentTurn == 1) model.incrementUnplaced1();
			else model.incrementUnplaced2();

			currentView.displayError("Please choose a valid place for your ship");

			return;

		}	
		
		for (int i = 0; i < currentShip; i++) {
			
			char shipChar;
			int unplacedNumber;
			
			if (currentTurn == 1) unplacedNumber = model.getUnplacedNumber1();
			else unplacedNumber = model.getUnplacedNumber2();
			unplacedNumber++;
			
			shipChar = convertNumToShipChar(unplacedNumber);
			
			model.setBoardValue(currentTurn, row-1, col-1, shipChar);
			if (orientation.equals("R")) col++;
			else row++;
		}

		if (currentTurn == 1 && model.getUnplacedNumber1() < 1) {
			model.setNextTurn(2);
			model.setTurn(3);
		}

		if (currentTurn == 2 && model.getUnplacedNumber2() < 1) {
			model.setNextTurn(1);
			model.setTurn(3);
			model.setSetup(false);
		}
	}
	
	// Takes the current turn, a row, and a column and checks if any ships are hit. Adjusts model and view accordingly
	// Returns true if the turn order should continue, and false otherwise
	private boolean checkForHits(int currentTurn, int row, int col) {
		int enemy;
		
		if (currentTurn == 1) enemy = 2;
		else enemy = 1;
		
		// If we hit, change that board value to 'h'
		if (model.getBoardValue(enemy, row-1, col-1) == 'a' ||
			model.getBoardValue(enemy, row-1, col-1) == 'b' ||
			model.getBoardValue(enemy, row-1, col-1) == 'c' ||
			model.getBoardValue(enemy, row-1, col-1) == 'd' ||
			model.getBoardValue(enemy, row-1, col-1) == 'e') {
			
			char ship = model.getBoardValue(enemy, row-1, col-1);
			
			model.setBoardValue(enemy, row-1, col-1, Character.toUpperCase(model.getBoardValue(enemy, row-1, col-1)));
			
			String confirmation = "You hit a ship!";
			
			// If we sunk a ship, mark it as so
			if (!checkForShip(enemy, ship)) {
				sinkShip(enemy, ship);
				confirmation = "You sunk a ship!";
			}			
			currentView.displayConfirmation(confirmation);
			
			// If there is a winner, do something
			if (!checkForAnyShips(enemy)) {
				model.setWinner(currentTurn);
				return false;
			}
			
			return true;
			
			
		} else if(model.getBoardValue(enemy, row-1, col-1) != '~') {
			currentView.displayError("Please enter a tile you have not shot at yet!");
			return false;
		} else {
			model.setBoardValue(enemy, row-1, col-1, 'm');
			
			currentView.displayConfirmation("You missed!");
			
			return true;
		}
	}
	
	// Sets up the turn order so that the transition turn is handled correctly
	private void setNextTurnIfGoingIntoTransition(int currentTurn) {		
		if (currentTurn == 1) {
			model.setNextTurn(2);
			model.setTurn(3);
		} else {
			model.setNextTurn(1);
			model.setTurn(3);
		}
	}
	
	// Converts a number to its ship character representation
	private char convertNumToShipChar(int num) {
		switch(num) {
		case(5):
			return 'a';
		case(4):
			return 'b';
		case(3):
			return 'c';
		case(2):
			return 'd';
		case(1):
			return 'e';
		default:
			return '?';
		}
	}

	// Takes a board number, and checks if there are any non-sunken ships left on that board
	private boolean checkForAnyShips(int boardNum) {
		char[][] board;
		if (boardNum == 1) board = model.getPlayer1board();
		else board = model.getPlayer2board();
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 'a' ||
					board[i][j] == 'b' ||
					board[i][j] == 'c' ||
					board[i][j] == 'd' ||
					board[i][j] == 'e') 
					return true;
			}
		}
		
		return false;
	}
	
	// Takes a board number and a type of ship, and replaces all hit instances of that ship
	// with x, the sunken ship indicator
	private void sinkShip(int boardNum, char ship) {
		char sunkenShip = Character.toUpperCase(ship);
		char[][] board;
		if (boardNum == 1) board = model.getPlayer1board();
		else board = model.getPlayer2board();
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == sunkenShip) board[i][j] = 'x';
			}
		}
		
	}
	
	// Takes a board number and a type of ship, and checks whether any of that type of ship remain
	private boolean checkForShip(int boardNum, char ship) {
		char[][] board;
		if (boardNum == 1) board = model.getPlayer1board();
		else board = model.getPlayer2board();
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == ship) return true;
			}
		}
		
		return false;
	}

	// Takes a board number, row, column, ship size, and orientation, and returns true if it is a valid
	// place to put a ship, and false otherwise
	private boolean validatePlacement(int board, int row, int col, int shipSize, String orientation) {

		for (int i = 0; i < shipSize; i++) {
			if (row < 0 || row > 9 || col < 0 || col > 9) return false;

			if (model.getBoardValue(board, row, col) == 'a' ||
				model.getBoardValue(board, row, col) == 'b' ||
				model.getBoardValue(board, row, col) == 'c' ||
				model.getBoardValue(board, row, col) == 'd' ||
				model.getBoardValue(board, row, col) == 'e') {
				return false;
			}

			if (orientation.equals("R")) col++;
			else row++;
		}

		return true;
	}

	// Converts the letter of a column to its integer representation (e.g. A to 1, B to 2, etc.)
	private int convertLetterToInt(String letter) {
		switch(letter.toUpperCase()) {
		case "A":
			return 1;
		case "B":
			return 2;
		case "C":
			return 3;
		case "D":
			return 4;
		case "E":
			return 5;
		case "F":
			return 6;
		case "G":
			return 7;
		case "H":
			return 8;
		case "I":
			return 9;
		case "J":
			return 10;
		default:
			return -1;
		}
	}


	// Methods for setting up the MVC architecture
	public void addModel(Model m){
		this.model = m;
	}

	public void addView(View v){
		currentView = v;
	}

	// Updates the model trivially to make it send a message to observers
	public void initModel(){
		model.setBoardValue(1, 1, 1, '~');
	}
	
	// Required empty methods for the MouseListener interface
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
