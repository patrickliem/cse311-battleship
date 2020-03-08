import static org.junit.Assert.*;

import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

import org.junit.Test;
import org.junit.Before;

public class BattleshipTest {

	private static Model model = new Model();
	private static Controller controller = new Controller();
	private static ViewText viewText = new ViewText();
	private static ViewGUI viewGUI = new ViewGUI();
	
	/* Begin Model tests */

	@Before
	public void setupSystem() {
		model = new Model();
		controller = new Controller();
		viewText = new ViewText();
		viewGUI = new ViewGUI();
	}
	
	@Test
	public void testModelConstructor() {
		assertEquals(1, model.getTurn());
		assertEquals(0, model.getWinner());
		assertEquals(2, model.getNextTurn());
		assertEquals(true, model.isSetup());
	}
	
	@Test
	public void testModelConstructorBoardLayout() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				assertEquals('~', model.getBoardValue(1, i, j));
				assertEquals('~', model.getBoardValue(2, i, j));
			}
		}
	}
	
	@Test
	public void testSetBoardValue1() {
		model.setBoardValue(1, 1, 1, 'h');
		
		assertEquals('h', model.getBoardValue(1, 1, 1));
		model.setBoardValue(1, 1, 1, '~');
	}

	@Test
	public void testSetBoardValue2() {
		model.setBoardValue(2, 4, 5, 'x');
		
		assertEquals('x', model.getBoardValue(2, 4, 5));
		model.setBoardValue(2, 4, 5, '~');
	}

	@Test
	public void testGetNextUnplaced1() {
		int length = model.getNextUnplaced1();
		
		assertEquals(5, length);
	}
	
	@Test
	public void testGetNextUnplaced2() {
		int length = model.getNextUnplaced2();
		
		assertEquals(5, length);
	}

	/* End Model tests */
	
	/* Begin Controller tests */
	
	@Test
	public void testSetupPlacement() {
		View testView = new ViewText();
		controller.addModel(model);
		controller.addView(testView);
		JComponent testComponent = new JButton();
		testComponent.putClientProperty("entryText", "A 1 R");
		testComponent.addMouseListener(controller);
		MouseEvent me = new MouseEvent(testComponent, 0, 0, 0, 100, 100, 1, false);
		controller.mouseClicked(me);
		
		System.out.println(model.getBoardValue(1, 1, 1));
		
		assertEquals('a', model.getBoardValue(1, 0, 0));
	}
	
	@Test
	public void testNonSetupFiring() {
		View testView = new ViewText();
		controller.addModel(model);
		controller.addView(testView);
		model.setSetup(false);
		JComponent testComponent = new JButton();
		testComponent.putClientProperty("entryText", "A 1");
		testComponent.addMouseListener(controller);
		MouseEvent me = new MouseEvent(testComponent, 0, 0, 0, 100, 100, 1, false);
		controller.mouseClicked(me);
				
		assertEquals('m', model.getBoardValue(2, 0, 0));
	}
	
	/* End Controller tests */
	
	/* Begin ViewText tests */
	
	@Test
	public void testTextDisplayError() {
		viewText.displayError("error");
		
		assertEquals("<html><div style='color:red;'>error</div></html>", viewText.errorLabel.getText());
	}
	
	@Test
	public void testTextClearError() {
		viewText.clearError();
		
		assertEquals("", viewText.errorLabel.getText());
	}
	
	@Test
	public void testTextDisplayConfirmation() {
		viewText.displayConfirmation("you hit a ship");
		
		assertEquals("<html><div style='color:red;'>you hit a ship</div></html>", viewText.errorLabel.getText());
	}
	
	@Test
	public void testTextClearConfirmation() {
		viewText.clearConfirmation();
		
		assertEquals("", viewText.errorLabel.getText());
	}
	
	@Test
	public void testTextAddController() {
		viewText.addController(controller);
		
		assertEquals(controller, viewText.button.getMouseListeners()[viewText.button.getMouseListeners().length - 1]);
	}

	@Test
	public void testTextUpdate() {
		model.addObserver(viewText);
		model.setBoardValue(1, 1, 1, 'a');
		assertEquals("<html>My board:<br>&nbsp;&nbsp;&nbsp;A B C D E F G H I J<br>1&nbsp;&nbsp;~ ~ ~ ~ ~ ~ ~ ~ ~ ~ <br>2&nbsp;&nbsp;~ a ~ ~ ~ ~ ~ ~ ~ ~ <br>3&nbsp;&nbsp;~ ~ ~ ~ ~ ~ ~ ~ ~ ~ <br>4&nbsp;&nbsp;~ ~ ~ ~ ~ ~ ~ ~ ~ ~ <br>5&nbsp;&nbsp;~ ~ ~ ~ ~ ~ ~ ~ ~ ~ <br>6&nbsp;&nbsp;~ ~ ~ ~ ~ ~ ~ ~ ~ ~ <br>7&nbsp;&nbsp;~ ~ ~ ~ ~ ~ ~ ~ ~ ~ <br>8&nbsp;&nbsp;~ ~ ~ ~ ~ ~ ~ ~ ~ ~ <br>9&nbsp;&nbsp;~ ~ ~ ~ ~ ~ ~ ~ ~ ~ <br>10&nbsp;~ ~ ~ ~ ~ ~ ~ ~ ~ ~ <br></html>", viewText.playerBoard.getText());
	}
	
	/* End ViewText tests */
	
	/* Begin ViewGUI tests */
	
	/* End ViewGUI tests */

}

