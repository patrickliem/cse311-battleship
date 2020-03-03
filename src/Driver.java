
public class Driver {
	
	public static void main(String[] args) {
		View battleshipView = new ViewText();
		Model battleshipModel = new Model();
		
		battleshipModel.addObserver(battleshipView);
		
		Controller battleshipController = new Controller();
		battleshipController.addModel(battleshipModel);
		battleshipController.addViewText(battleshipView);
		battleshipController.initModel();
		
		battleshipView.addController(battleshipController);

	}
}
