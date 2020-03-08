import static org.junit.Assert.*;

import org.junit.Test;

public class BattleshipTest {
	
	/* Begin Model tests */
	private static Model model = new Model();

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
	private static Controller controller = new Controller();
	
	public void testMouseClicked() {
		
	}
	
	/* End Controller tests */
	
	/* Begin ViewText tests */
	private static ViewText viewText = new ViewText();
	
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
	
	
	/* End ViewText tests */
	
	/* Begin ViewGUI tests */
	
	/* End ViewGUI tests */

}

