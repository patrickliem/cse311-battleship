
public class Driver {
	
	public static void main(String[] args) {
		// Change this to ViewText()/ViewGUI() depending on what to test
		View battleshipView = new ViewText();
		Model battleshipModel = new Model();
		
		battleshipModel.addObserver(battleshipView);
		
		Controller battleshipController = new Controller();
		battleshipController.addModel(battleshipModel);
		battleshipController.addView(battleshipView);
		battleshipController.initModel();
		
		battleshipView.addController(battleshipController);

	}
}
