
public class Driver {
	
	public static void main(String[] args) {
		ViewText battleshipTextView = new ViewText();
		//ViewGUI battleshipGUIView = new ViewGUI();
		Model battleshipModel = new Model();
		
		battleshipModel.addObserver(battleshipTextView);
		
		Controller battleshipController = new Controller();
		battleshipController.addModel(battleshipModel);
		battleshipController.addViewText(battleshipTextView);
		//battleshipController.addViewGUI(battleshipGUIView);
		battleshipController.initModel();
		
		battleshipTextView.addController(battleshipController);
		//battleshipGUIView.addController(battleshipController);
	}
}
