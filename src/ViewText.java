import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class ViewText extends View {

	public JFrame frame;
	private JLabel turnIndicator;
	private JLabel enemyBoard;
	private JLabel playerBoard;
	private JLabel actionLabel;
	private JTextField entryField;
	private JButton button;
	
	// Constructor that sets up the JFrame we will be using for the View
	public ViewText() {
		frame = new JFrame("Battleship (Text Version)");
		JPanel outer = new JPanel(new BorderLayout());
		
		
		turnIndicator = new JLabel("Player 1's turn", SwingConstants.CENTER);
		outer.add(turnIndicator, BorderLayout.NORTH);
		
		JPanel boardDisplay = new JPanel(new GridLayout(3, 1));
		
		enemyBoard = new JLabel("", SwingConstants.CENTER);
		enemyBoard.setFont(new Font("Courier New", Font.PLAIN, 12));
		boardDisplay.add(enemyBoard);
		
		playerBoard = new JLabel("", SwingConstants.CENTER);
		playerBoard.setFont(new Font("Courier New", Font.PLAIN, 12));
		boardDisplay.add(playerBoard);
		
		outer.add(boardDisplay, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
		actionLabel = new JLabel("Please enter the letter and number of the tile you wish to shoot (e.g. A 1):");
		bottomPanel.add(actionLabel);
		JPanel textfieldAndButtonPanel = new JPanel(new GridLayout(1, 2));
		entryField = new JTextField(40);
		entryField.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {
				}

				@Override
				public void focusLost(FocusEvent e) {
					button.putClientProperty("entryText", entryField.getText());
					entryField.setText("");
				}
				
		});
		textfieldAndButtonPanel.add(entryField);
		
		button = new JButton("Submit");
		textfieldAndButtonPanel.add(button);
		
		bottomPanel.add(textfieldAndButtonPanel);
		
		outer.add(bottomPanel, BorderLayout.SOUTH);
		
		frame.add(outer);


		frame.addWindowListener(new CloseListener());	
		frame.setSize(700,700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setVisible(true);
	}
	
	// This is called whenever the Model changes, and it repaints the frame after receiving the new information
	@Override
	public void update(Observable o, Object arg) {
		Model data = (Model)o;
		
		char[][] player1Board = data.getPlayer1board();
		char[][] player2Board = data.getPlayer2board();
		
		String rowStr = "&nbsp;&nbsp;&nbsp;A B C D E F G H I J";
		
		// see https://stackoverflow.com/questions/11037622/pass-variables-to-actionlistener-in-java
		// for passing variables to the actionlistener
		

		// If the turn is not transitioning, then draw the boards
		if (data.getTurn() != 3) {
			entryField.setEditable(true);
			
			
			// This is how to draw the board if we are not in the setup phase
			if (!data.isSetup()) {
				char[][] myBoardArray;
				char[][] enemyBoardArray;
				
				if (data.getTurn() == 1) {
					myBoardArray = player1Board;
					enemyBoardArray = player2Board;
					turnIndicator.setText("Player 1's turn");
					
				} else {
					myBoardArray = player2Board;
					enemyBoardArray = player1Board;
					turnIndicator.setText("Player 2's turn");
				}
					
				String myBoard = "<html>My board:<br>" + rowStr + "<br>";
				for (int i = 0; i < 10; i++) {
					myBoard += (i+1) + "&nbsp;";
					if (i != 9) myBoard += "&nbsp;";
					
					for (int j = 0; j < 10; j++) {
						
						myBoard += myBoardArray[i][j] + " ";
					}
					myBoard += "<br>";
				}
				myBoard += "</html>";
				playerBoard.setText(myBoard);
				
				String enemyBoard = "<html>Enemy board:<br>" + rowStr + "<br>";
				for (int i = 0; i < 10; i++) {
					
					enemyBoard += (i+1) + "&nbsp;";
					if (i != 9) enemyBoard += "&nbsp;";
					
					for (int j = 0; j < 10; j++) {
						enemyBoard += enemyBoardArray[i][j] + " ";
					}
					enemyBoard += "<br>";
				}
				enemyBoard += "</html>";
				this.enemyBoard.setText(enemyBoard);
			} else {
				// This is how to draw the board if we are in the setup phase
				char[][] myBoardArray;
				
				int unplacedShip;
				
				if (data.getTurn() == 1) {
					myBoardArray = player1Board;
					turnIndicator.setText("Player 1's setup phase");
					unplacedShip = data.getNextUnplaced1();
				} else {
					myBoardArray = player2Board;
					turnIndicator.setText("Player 2's setup phase");
					unplacedShip = data.getNextUnplaced2();
				}
				
				String myBoard = "<html>My board:<br>" + rowStr + "<br>";
				for (int i = 0; i < 10; i++) {
					myBoard += (i+1) + "&nbsp;";
					if (i != 9) myBoard += "&nbsp;";
					
					for (int j = 0; j < 10; j++) {
						
						myBoard += myBoardArray[i][j] + " ";
					}
					myBoard += "<br>";
				}
				myBoard += "</html>";
				playerBoard.setText(myBoard);
				
				actionLabel.setText("<html>Enter where and how to place your " + unplacedShip + "-length ship. <br>"
									+ "For example, A 1 R to placed your ship at A 1 facing right, or A 1 D to place your ship at A 1 facing down.</html>");
				
				enemyBoard.setText("");
				
				
			}
		
		// If the turn is transitioning, just draw a mostly blank screen
		} else {
			turnIndicator.setText("");
			playerBoard.setText("Please pass to the next player");
			enemyBoard.setText("");
			
			actionLabel.setText("Press submit to continue the game.");
			entryField.setEditable(false);
		}
		
		frame.revalidate();
		frame.repaint();
		
	}
	
	// This method can be called by Controller to display an error message
	public void displayError(String errText) {
		actionLabel.setText(actionLabel.getText().substring(0, actionLabel.getText().length() - 7) + "<br><div style='color:red;'>" + errText + "</div></html>");
		frame.revalidate();
		frame.repaint();
	}
	
	// Used for setting up the MVC architecture
	public void addController(ActionListener controller){
		button.addActionListener(controller);	//need instance of controller before can add it as a listener 
	}
	
	// Makes sure we exit nicely
	public static class CloseListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			e.getWindow().setVisible(false);
			System.exit(0);
		}
	}

}
