import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class Controller implements java.awt.event.ActionListener {
	
	private Model model;
	private View currentView;
	

	
	Controller() {
	}
	
	// Note: probably do some validation of inputs here, and if it's bad tell view to do something
	@Override
	public void actionPerformed(ActionEvent e) {
		// this gets the data from the textfield in ViewText
		
		String entryText = ((JButton)e.getSource()).getClientProperty("entryText").toString();
		
		String[] entryTextElements = entryText.split(" ");
		
		
		// Different behaviors depending on whether we are in the setup phase or not
		if (model.isSetup()) {
			int col = convertLetterToInt(entryTextElements[0].toUpperCase());
			int row = -1;
			try {
				row = Integer.parseInt(entryTextElements[1]);
			} catch(NumberFormatException excep) {
				
			}
			
			
		} else {
			
		}
		
		
		
		
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

	public void addViewText(View v){
		currentView = v;
	}


	// Updates the model trivially to make it send a message to observers
	public void initModel(){
		model.setBoardValue(1, 1, 1, '~');
	}
}
