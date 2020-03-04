import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class Controller implements java.awt.event.ActionListener {

	private Model model;
	private View currentView;



	Controller() {
	}

	// Take the input from the view and process it
	@Override
	public void actionPerformed(ActionEvent e) {
		
		currentView.clearError();

		if (model.getTurn() != 3) {
			
			// this gets the data from the textfield in ViewText
			// consider changing this to JComponent and focuslistener instead of action listener
			String entryText = ((JButton)e.getSource()).getClientProperty("entryText").toString();

			String[] entryTextElements = entryText.split(" ");
			if (entryTextElements.length != 3) {
				currentView.displayError("Please enter a valid string");
				return;
			}

			int currentTurn = model.getTurn();


			// Different behaviors depending on whether we are in the setup phase or not
			if (model.isSetup()) {
				int col = convertLetterToInt(entryTextElements[0].toUpperCase());
				int row = -1;
				String orientation = entryTextElements[2];

				try {
					row = Integer.parseInt(entryTextElements[1]);	
				} catch(NumberFormatException excep) {
					currentView.displayError("Please enter a valid row.");
					return;
				}

				if (col != -1) {
					if (!(orientation.equals("R") || orientation.equals("D"))) {
						currentView.displayError("Please enter a valid orientation.");
						return;
					}

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
						model.setBoardValue(currentTurn, row-1, col-1, 's');
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

					
				} else {
					currentView.displayError("Please enter a valid column.");
					return;
				}

			} else {
				// TODO
				
			}


		} else {
			model.setTurn(model.getNextTurn());
		}

	}
	
	private boolean validatePlacement(int turn, int row, int col, int shipSize, String orientation) {
		
		for (int i = 0; i < shipSize; i++) {
			if (row < 0 || row > 9 || col < 0 || col > 9) return false;
			
			if (model.getBoardValue(turn, row, col) == 's') {
				return false;
			}
			
			if (row-1 >= 0) {
				if (model.getBoardValue(turn, row-1, col) == 's') return false;
			}
			if (row+1 <= 9) {
				if (model.getBoardValue(turn, row+1, col) == 's') return false;
			}
			if (col-1 >= 0) {
				if (model.getBoardValue(turn, row, col-1) == 's') return false;
			}
			if (col+1 <= 9) {
				if (model.getBoardValue(turn, row, col+1) == 's') return false;
			}
			
			if (orientation.equals("R")) col++;
			else row++;
		}
		
		return true;
	}

	private int convertLetterToInt(String letter) {
		switch(letter) {
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
}
