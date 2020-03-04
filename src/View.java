import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public abstract class View implements Observer {
	public abstract void displayError(String errText);
	
	public abstract void clearError();
	
	public abstract void displayConfirmation(String confText);
	
	public abstract void addController(ActionListener controller);
	
	public abstract void update(Observable o, Object arg);
	
}
