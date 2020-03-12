import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;


public class ViewGUI extends View{
	public JFrame frame;
	public JPanel pane;
	
	//messing around with graphics we shall see what happens here
	private void paintBoards(Graphics g) {
		
		//opponent grid 10 x 10 (no labels) filled blue for water
//		 GridLayout oppGrid = new GridLayout(10, 10, 0, 0);
//		 frame.setLayout(oppGrid);
//		 for (int col = 0; col < 10; col++) {
//             for (int row = 0; row < 10; row++) {
//            	 g.setColor(Color.blue);
//                 g.fillRect(col, row, 50, 50);
//                 pane.setBorder(BorderFactory.createLineBorder(Color.black));
//            }
//         }
//		//player grid 10 x 10 (no labels) filled blue for water
//		 GridLayout playerGrid = new GridLayout(10, 10, 0, 0);
//		 frame.setLayout(playerGrid);
//		 for (int col = 0; col < 10; col++) {
//             for (int row = 0; row < 10; row++) {
//            	 g.setColor(Color.blue);
//                 g.fillRect(col, row, 50, 50);
//            }
//         }
	}
	
	
	public ViewGUI() {
		frame = new JFrame("Battleship (GUI Version)");
		
		//size same as ViewText for now
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearConfirmation() {
		// TODO Auto-generated method stub
		
	}
	
	// Makes sure we exit nicely
	public static class CloseListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			e.getWindow().setVisible(false);
			System.exit(0);
		}
	}

}
