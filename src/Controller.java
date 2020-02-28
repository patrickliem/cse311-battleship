import java.awt.event.ActionEvent;

public class Controller implements java.awt.event.ActionListener {
	
	private Model model;
	private ViewText viewText;
	private ViewGUI viewGUI;
	
	Controller() {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("button pressed");
		// change this obviously
		model.setBoardValue(1, 1, 1, 's');;
		
	}
	
	public void addModel(Model m){
		this.model = m;
	}

	public void addViewText(ViewText v){
		this.viewText = v;
	}

	public void addViewGUI(ViewGUI v){
		System.out.println("Controller: adding GUI view");
		this.viewGUI = v;
	}

	// Updates the model trivially to make it send a message to observers
	public void initModel(){
		model.setBoardValue(1, 1, 1, '~');
	}
}
