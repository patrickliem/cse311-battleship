import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;


public class ViewGUI extends View{
	public JFrame frame;

	private JPanel topPanel;
	private JLabel turnLabel;
	private JButton changeView;

	private JPanel[][] playerGrid = new JPanel[11][11];
	private JPanel[][] enemyGrid = new JPanel[11][11];
	private JPanel boardsPanel;
	private JLabel boardLabel1, boardLabel2;


	public ViewGUI() {
		frame = new JFrame("Battleship (GUI Version)");

		// Create the outer wrapper panel of the frame
		JPanel outer = new JPanel(new BorderLayout());

		// Initialize the top panel of the frame
		initTopPanel();
		
		// Initialize the boards
		initBoardsPanel();

		// TODO initialize the bottom panel

		// Set up the outer panel and add it to the frame. Maybe replace with drawSetup() later on?
		outer.add(topPanel, BorderLayout.NORTH);
		outer.add(boardsPanel, (BorderLayout.CENTER));
		frame.add(outer);

		// Set up the frame initialization stuff
		frame.setSize(700,700);
		frame.addWindowListener(new CloseListener());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setVisible(true);

	}

	@Override
	public void displayError(String errText) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearError() {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayConfirmation(String confText) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addController(MouseListener controller) {
		changeView.addMouseListener(controller);
		
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				if (i != 0 && j != 0) {
					playerGrid[i][j].addMouseListener(controller);
					enemyGrid[i][j].addMouseListener(controller);
				}
			}
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		//get data from model -- set up phase, turn, etc and render 1 board or 2 depending on turn
	}

	@Override
	public void clearConfirmation() {
		// TODO Auto-generated method stub

	}
	
	// Initializes the components for the top panel
	private void initTopPanel() {
		topPanel = new JPanel(new GridLayout(1, 2));

		turnLabel = new JLabel("  Player 1's turn:");

		changeView = new JButton("Change View");
		changeView.putClientProperty("entryText", "changeView");
		changeView.setAlignmentX(Component.RIGHT_ALIGNMENT);
		JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		topRightPanel.add(changeView);

		topPanel.add(turnLabel);
		topPanel.add(topRightPanel);
	}
	
	// Helper method to turn numbers into their column representation on the board
	private String convertIntToBoardLetter(int n) {
		switch(n) {
			case 0:
				return "";
			case 1:
				return "A";
			case 2:
				return "B";
			case 3:
				return "C";
			case 4:
				return "D";
			case 5:
				return "E";
			case 6:
				return "F";
			case 7:
				return "G";
			case 8:
				return "H";
			case 9:
				return "I";
			case 10:
				return "J";
			default:
				return "?";
				
		}
	}

	// Initializes the components for the boards panel
	private void initBoardsPanel() {
		boardsPanel = new JPanel(new BorderLayout());
		JPanel playerBoardPanelWrapper = new JPanel(new BorderLayout());
		JPanel enemyBoardPanelWrapper = new JPanel(new BorderLayout());
		
		boardLabel1 = new JLabel("Enemy board: ");
		boardLabel2 = new JLabel("Your board: ");
		
		playerBoardPanelWrapper.add(boardLabel1, BorderLayout.NORTH);
		enemyBoardPanelWrapper.add(boardLabel2, BorderLayout.NORTH);
		
		JPanel playerBoardPanel = new JPanel(new GridLayout(11, 11));
		JPanel enemyBoardPanel = new JPanel(new GridLayout(11, 11));
		playerBoardPanel.setPreferredSize(new Dimension(300, 300));
		enemyBoardPanel.setPreferredSize(new Dimension(300, 300));
		
		for(int i = 0; i < 11; i++) {
			for(int j = 0; j < 11; j++) {
				JPanel playerPanel = new JPanel();
				JPanel enemyPanel = new JPanel();
				
				if (i == 0) {
					playerPanel.add(new JLabel(convertIntToBoardLetter(j)));
					enemyPanel.add(new JLabel(convertIntToBoardLetter(j)));
				} else if (j == 0 && i != 0) {
					playerPanel.add(new JLabel("" + i));
					enemyPanel.add(new JLabel("" + i));
				} else {
					playerPanel.setBackground(Color.BLUE);
					enemyPanel.setBackground(Color.BLUE);
					
					playerPanel.putClientProperty("entryText", convertIntToBoardLetter(j) + " " + i);
					enemyPanel.putClientProperty("entryText", convertIntToBoardLetter(j) + " " + i);
				}
				
				playerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				enemyPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				playerGrid[i][j] = playerPanel;
				enemyGrid[i][j] = enemyPanel;
				
				playerBoardPanel.add(playerGrid[i][j]);
				enemyBoardPanel.add(enemyGrid[i][j]);
			}
		}
		
		playerBoardPanelWrapper.add(playerBoardPanel, BorderLayout.SOUTH);
		enemyBoardPanelWrapper.add(enemyBoardPanel, BorderLayout.SOUTH);
		
		boardsPanel.add(playerBoardPanelWrapper, BorderLayout.NORTH);
		boardsPanel.add(enemyBoardPanelWrapper, BorderLayout.SOUTH);
	}
	
	// Makes sure we exit nicely
	public static class CloseListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			e.getWindow().setVisible(false);
			System.exit(0);
		}
	}

}
