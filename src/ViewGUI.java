import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.util.Observable;


public class ViewGUI extends View{
	public JFrame frame;
	public JPanel pane;
	
	
	public ViewGUI() {
		//Setting the Window
		frame = new JFrame("Battleship (GUI Version)");
		frame.setSize(700,700);
		frame.addWindowListener(new CloseListener());
		
		
		frame.add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;

                g2.setColor(Color.BLUE);
                //ocean aka big blue rectangle
                g2.draw(new Rectangle2D.Double(220, 40, 300, 300));
                g2.fillRect(220, 40, 300, 300);
                
                // makes a grid for the board
                for (int row = 220; row < 520; row += 30) {
                	for (int col = 40; col < 340; col += 30) {
                		g2.setColor(Color.BLACK);
                		g2.draw(new Rectangle2D.Double(row, col, 30, 30));
                	}         
                }
                
                
            }
        }, BorderLayout.CENTER);
		
		//Window
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
