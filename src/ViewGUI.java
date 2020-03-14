import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;


public class ViewGUI extends View{
	public JFrame frame;
	private JPanel outer;

	// Top Panel Components
	private JPanel topPanel;
	private JLabel turnLabel;
	private JButton changeView;

	// Battle Phase Components
	private JPanel[][] playerGrid = new JPanel[11][11];
	private JPanel[][] enemyGrid = new JPanel[11][11];
	private JPanel boardsPanel;

	private JPanel bottomBoardsPanel;
	private JLabel battleErrorLabel;

	// Setup Phase Components
	private JPanel setupPanel;
	private JPanel[][] setupGrid = new JPanel[11][11];

	private JPanel bottomSetupPanel;
	private JLabel setupInstructionsLabel;
	private JLabel setupErrorLabel;

	// Transition Phase Components
	private JPanel transitionPanel;
	private JLabel transitionConfLabel;
	private JButton passButton;

	// Model representation for easy access
	private Model data;


	public ViewGUI() {
		frame = new JFrame("Battleship (GUI Version)");

		// Create the outer wrapper panel of the frame
		outer = new JPanel(new BorderLayout());

		// Initialize the top panel of the frame
		initTopPanel();

		// Initialize the boards for the battle phase
		initBoardsPanel();

		// TODO Initialize the bottom panel for the battle phase
		initBottomBoardsPanel();

		// Initialize the setup panel
		initSetupPanel();

		// Initialize the bottom panel for the setup phase
		initBottomSetupPanel();

		// Initialize the panel for the transition turn
		initTransitionPanel();

		// Set up the outer panel and add it to the frame. Maybe replace with drawSetup() later on?
		outer.add(topPanel, BorderLayout.NORTH);
		outer.add(setupPanel, BorderLayout.CENTER);
		outer.add(bottomSetupPanel, BorderLayout.SOUTH);
		frame.add(outer);

		// Set up the frame initialization stuff
		frame.setSize(700,700);
		frame.addWindowListener(new CloseListener());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setVisible(true);
	}
	

	// Get data from model and render the correct panels according to the phase and turn
	@Override
	public void update(Observable o, Object arg) {

		data = (Model)o;

		// Check if there is a winner
		if (data.getWinner() != 0) {
			resetPane();
			drawTransitionPhase();
			
			turnLabel.setText("");
			displayConfirmation("Player " + data.getWinner() + " wins!");
			passButton.setVisible(false);
			return;
		}


		// If the turn is not transitioning, then draw the boards
		if (data.getTurn() != 3) {

			// This is how to draw the board if we are not in the setup phase
			if (!data.isSetup()) {	
				drawBattlePhase();

			} else {
				// This is how to draw the board if we are in the setup phase
				drawSetupPhase();
			}

			// If the turn is transitioning, just draw a mostly blank screen
		} else {
			drawTransitionPhase();
		}

		frame.revalidate();
		frame.repaint();
	}
	
	// TODO Draws the board how it should look during the battle phase
	private void drawBattlePhase() {
		resetPane();
		
		char[][] myBoardArray;
		char[][] enemyBoardArray;
		
		if (data.getTurn() == 1) {
			myBoardArray = data.getPlayer1board();
			enemyBoardArray = data.getPlayer2board();
			turnLabel.setText("Player 1's turn");
			
		} else {
			myBoardArray = data.getPlayer2board();
			enemyBoardArray = data.getPlayer1board();
			turnLabel.setText("Player 2's turn");
		}
		
		// Set up the player board
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (myBoardArray[i][j] == '~') {
					playerGrid[i+1][j+1].setBackground(Color.BLUE);
				} else if (myBoardArray[i][j] == 'a' ||
						   myBoardArray[i][j] == 'b' ||
						   myBoardArray[i][j] == 'c' ||
						   myBoardArray[i][j] == 'd' ||
						   myBoardArray[i][j] == 'e') {
					playerGrid[i+1][j+1].setBackground(Color.GRAY);
				} else if (myBoardArray[i][j] == 'A' ||
						   myBoardArray[i][j] == 'B' ||
						   myBoardArray[i][j] == 'C' ||
						   myBoardArray[i][j] == 'D' ||
						   myBoardArray[i][j] == 'E') {
					playerGrid[i+1][j+1].setBackground(Color.RED);
				} else if (myBoardArray[i][j] == 'x') {
					playerGrid[i+1][j+1].setBackground(Color.BLACK);
				} else if (myBoardArray[i][j] == 'm') {
					playerGrid[i+1][j+1].setBackground(Color.WHITE);
				}
			}
		}
		
		// Set up the enemy board
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (enemyBoardArray[i][j] == 'A' ||
					enemyBoardArray[i][j] == 'B' ||
					enemyBoardArray[i][j] == 'C' ||
					enemyBoardArray[i][j] == 'D' ||
					enemyBoardArray[i][j] == 'E') {
					enemyGrid[i+1][j+1].setBackground(Color.RED);
				} else if (enemyBoardArray[i][j] == 'x') {
					enemyGrid[i+1][j+1].setBackground(Color.BLACK);
				} else if (enemyBoardArray[i][j] == 'm') {
					enemyGrid[i+1][j+1].setBackground(Color.WHITE);
				} else {
					enemyGrid[i+1][j+1].setBackground(Color.BLUE);
				}
			}
		}
		
		outer.add(boardsPanel, BorderLayout.CENTER);
		outer.add(bottomBoardsPanel, BorderLayout.SOUTH);
	}
	

	// Draws the board how it should look during the setup phase
	private void drawSetupPhase() {
		resetPane();

		char[][] myBoardArray;

		int unplacedShip;

		if (data.getTurn() == 1) {
			myBoardArray = data.getPlayer1board();
			turnLabel.setText("Player 1's setup phase");
			unplacedShip = data.getNextUnplaced1();
		} else {
			myBoardArray = data.getPlayer2board();
			turnLabel.setText("Player 2's setup phase");
			unplacedShip = data.getNextUnplaced2();
		}
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (myBoardArray[i][j] == '~') {
					setupGrid[i+1][j+1].setBackground(Color.BLUE);
				} else if (myBoardArray[i][j] == 'a' ||
						   myBoardArray[i][j] == 'b' ||
						   myBoardArray[i][j] == 'c' ||
						   myBoardArray[i][j] == 'd' ||
						   myBoardArray[i][j] == 'e') {
					setupGrid[i+1][j+1].setBackground(Color.GRAY);
				}
			}
		}
		
		setupInstructionsLabel.setText("Click on a tile to place your " + unplacedShip + "-length ship");
		
		outer.add(setupPanel, BorderLayout.CENTER);
		outer.add(bottomSetupPanel, BorderLayout.SOUTH);
	}

	
	// Draws the board how it should look during a transition
	private void drawTransitionPhase() {
		resetPane();
		turnLabel.setText("");

		outer.add(transitionPanel, BorderLayout.CENTER);

	}

	
	// Clears everything except the top JPanel of the frame
	private void resetPane() {
		outer.remove(boardsPanel);
		outer.remove(bottomBoardsPanel);
		outer.remove(setupPanel);
		outer.remove(bottomSetupPanel);
		outer.remove(transitionPanel);
	}


	// This method can be called by Controller to display an error message
	@Override
	public void displayError(String errText) {
		setupErrorLabel.setText("<html><div style='color:red;'>&nbsp;&nbsp;" + errText + "</div></html>");
		battleErrorLabel.setText("<html><div style='color:red;'>&nbsp;&nbsp;" + errText + "</div></html>");

		frame.revalidate();
		frame.repaint();
	}

	
	// This method can be called by Controller to clear any error messages
	@Override
	public void clearError() {
		setupErrorLabel.setText("");
		battleErrorLabel.setText("");

		frame.revalidate();
		frame.repaint();

	}

	
	// This method can be called by Controller to display a confirmation message (you hit/missed/sunk)
	@Override
	public void displayConfirmation(String confText) {
		transitionConfLabel.setText("<html><div style='color:red;'>" + confText + "</div></html>");

		frame.revalidate();
		frame.repaint();

	}

	
	// This method can be called by Controller to clear the confirmation box
	@Override
	public void clearConfirmation() {
		transitionConfLabel.setText("");

		frame.revalidate();
		frame.repaint();

	}

	
	// Add the controller as a MouseListener to all components that need it
	@Override
	public void addController(MouseListener controller) {
		changeView.addMouseListener(controller);

		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				if (i != 0 && j != 0) {
					enemyGrid[i][j].addMouseListener(controller);
					setupGrid[i][j].addMouseListener(controller);
				}
			}
		}

		passButton.addMouseListener(controller);

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

	
	// Initializes the components for the center panel of the battle phase
	private void initBoardsPanel() {
		boardsPanel = new JPanel(new BorderLayout());
		JPanel playerBoardPanelWrapper = new JPanel(new BorderLayout());
		JPanel enemyBoardPanelWrapper = new JPanel(new BorderLayout());

		JLabel boardLabel1 = new JLabel("  Enemy board: ");
		JLabel boardLabel2 = new JLabel("  Your board: ");

		playerBoardPanelWrapper.add(boardLabel2, BorderLayout.NORTH);
		enemyBoardPanelWrapper.add(boardLabel1, BorderLayout.NORTH);

		JPanel playerBoardPanel = new JPanel(new GridLayout(11, 11));
		JPanel enemyBoardPanel = new JPanel(new GridLayout(11, 11));
		playerBoardPanel.setPreferredSize(new Dimension(275, 275));
		enemyBoardPanel.setPreferredSize(new Dimension(275, 275));

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

		boardsPanel.add(enemyBoardPanelWrapper, BorderLayout.NORTH);
		boardsPanel.add(playerBoardPanelWrapper, BorderLayout.SOUTH);
	}


	
	// Initializes the components for the bottom panel for the battle phase
	private void initBottomBoardsPanel() {
		bottomBoardsPanel = new JPanel();
		bottomBoardsPanel.setLayout(new BoxLayout(bottomBoardsPanel, BoxLayout.PAGE_AXIS));

		battleErrorLabel = new JLabel("");
		JLabel battleInstructionsLabel = new JLabel("  Click on the tile you would like to shoot at");

		bottomBoardsPanel.add(battleInstructionsLabel);
		bottomBoardsPanel.add(battleErrorLabel);

	}
	
	
	// Initializes the components for the setup panel
	private void initSetupPanel() {
		setupPanel = new JPanel();
		setupPanel.setLayout(new BoxLayout(setupPanel, BoxLayout.PAGE_AXIS));


		JLabel boardLabel = new JLabel("  Your board: ");		

		JPanel playerBoardPanel = new JPanel(new GridLayout(11, 11));
		playerBoardPanel.setPreferredSize(new Dimension(400, 400));

		for(int i = 0; i < 11; i++) {
			for(int j = 0; j < 11; j++) {
				JPanel playerPanel = new JPanel();

				if (i == 0) {
					playerPanel.add(new JLabel(convertIntToBoardLetter(j)));
				} else if (j == 0 && i != 0) {
					playerPanel.add(new JLabel("" + i));
				} else {
					playerPanel.setBackground(Color.BLUE);

					playerPanel.putClientProperty("entryText", convertIntToBoardLetter(j) + " " + i + " R");
				}

				playerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

				setupGrid[i][j] = playerPanel;

				playerBoardPanel.add(setupGrid[i][j]);
			}
		}

		setupPanel.add(boardLabel, BorderLayout.NORTH);
		setupPanel.add(playerBoardPanel, BorderLayout.SOUTH);
	}

	
	// Initializes the components for the bottom setup panel
	private void initBottomSetupPanel() {
		bottomSetupPanel = new JPanel(new GridLayout(1, 2));
		setupErrorLabel = new JLabel("");
		setupInstructionsLabel = new JLabel("Please click on a tile to place your ship.");

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));

		JLabel orientationLabel = new JLabel("  Please select an orientation: ");

		JRadioButton rightButton = new JRadioButton("Right");
		rightButton.setActionCommand("R");
		rightButton.setSelected(true);
		rightButton.addActionListener(new OrientationListener());
		JRadioButton downButton = new JRadioButton("Down");
		downButton.setActionCommand("D");
		downButton.addActionListener(new OrientationListener());

		ButtonGroup orientationGroup = new ButtonGroup();
		orientationGroup.add(rightButton);
		orientationGroup.add(downButton);

		leftPanel.add(orientationLabel);
		leftPanel.add(rightButton);
		leftPanel.add(downButton);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

		rightPanel.add(setupErrorLabel);
		rightPanel.add(setupInstructionsLabel);

		bottomSetupPanel.add(leftPanel);
		bottomSetupPanel.add(rightPanel);


	}

	
	// Initializes the components for the transition panel
	private void initTransitionPanel() {
		transitionPanel = new JPanel(new GridLayout(2, 1));

		transitionConfLabel = new JLabel("");
		transitionConfLabel.setHorizontalAlignment(JLabel.CENTER);

		JPanel passButtonWrapper = new JPanel();
		passButton = new JButton("Pass to the next player");
		passButton.putClientProperty("entryText", "");
		passButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		passButtonWrapper.add(passButton);

		transitionPanel.add(transitionConfLabel);
		transitionPanel.add(passButtonWrapper);

	}

	// Custom ActionListener for the orientation buttons
	private class OrientationListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < 11; i++) {
				for (int j = 0; j < 11; j++) {
					if (i != 0 && j != 0) {						
						setupGrid[i][j].putClientProperty("entryText", convertIntToBoardLetter(j) + " " + i + " " + e.getActionCommand());
					}
				}
			}
		}

	}

	// Makes sure we exit nicely
	public static class CloseListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			e.getWindow().setVisible(false);
			System.exit(0);
		}
	}

}
