import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class ViewText implements java.util.Observer {

	private JFrame frame;
	private JLabel turnIndicator;
	private JLabel enemyBoard;
	private JLabel playerBoard;
	private JButton button;
	
	public ViewText() {
		frame = new JFrame("Battleship");
		JPanel outer = new JPanel(new BorderLayout());
		
		outer.add(new JLabel("Battleship (Text)"), BorderLayout.NORTH);
		
		JPanel boardDisplay = new JPanel(new BorderLayout());

		turnIndicator = new JLabel("Player 1's turn");
		boardDisplay.add(turnIndicator, BorderLayout.NORTH);
		
		enemyBoard = new JLabel("test");
		boardDisplay.add(enemyBoard, BorderLayout.CENTER);
		
		playerBoard = new JLabel("test");
		boardDisplay.add(playerBoard, BorderLayout.SOUTH);
		
		outer.add(boardDisplay, BorderLayout.CENTER);
		
		button = new JButton("PressMe");
		outer.add(button, BorderLayout.SOUTH);
		
		frame.add(outer);


		frame.addWindowListener(new CloseListener());	
		frame.setSize(700,700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setVisible(true);
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		Model data = (Model)o;
		
		char[][] player1Board = data.getPlayer1board();
		char[][] player2Board = data.getPlayer2board();
		
		// see https://stackoverflow.com/questions/11037622/pass-variables-to-actionlistener-in-java
		// for passing variables to the actionlistener
		
		
		//testing only
		String myBoard = "<html>My board:<br>";
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				myBoard += player1Board[i][j] + " ";
			}
			myBoard += "<br>";
		}
		myBoard += "</html>";
		playerBoard.setText(myBoard);
		
		String enemyBoard = "<html>Enemy board:<br>";
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				enemyBoard += player2Board[i][j] + " ";
			}
			enemyBoard += "<br>";
		}
		enemyBoard += "</html>";
		this.enemyBoard.setText(enemyBoard);
		// end testing
		
		
		frame.revalidate();
		frame.repaint();
		System.out.println("updating");
		
	}
	
	public void addController(ActionListener controller){
		button.addActionListener(controller);	//need instance of controller before can add it as a listener 
	}
	
	public static class CloseListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			e.getWindow().setVisible(false);
			System.exit(0);
		} //windowClosing()
	} //CloseListener

}
