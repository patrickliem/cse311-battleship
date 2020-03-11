import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

public abstract class View implements Observer {
	public abstract void displayError(String errText);
	
	public abstract void clearError();
	
	public abstract void displayConfirmation(String confText);
	
	public abstract void clearConfirmation();
	
	public abstract void addController(MouseListener controller);
	
	public abstract void update(Observable o, Object arg);
	
}
