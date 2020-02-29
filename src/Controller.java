import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class Controller implements java.awt.event.ActionListener {
	
	private Model model;
	private ViewText viewText;
	private ViewGUI viewGUI;
	
	Controller() {
		
	}
	
	// Note: probably do some validation of inputs here, and if it's bad tell view to do something
	@Override
	public void actionPerformed(ActionEvent e) {
		// this gets the data from the textfield in ViewText
		System.out.println(((JButton)e.getSource()).getClientProperty("entryText"));
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
