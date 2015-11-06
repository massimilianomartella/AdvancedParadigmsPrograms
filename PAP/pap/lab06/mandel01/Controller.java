package pap.lab06.mandel01;

/**
 * Controller part of the application - passive part.
 * 
 * @author aricci
 *
 */
public class Controller implements InputListener {

	private MandelbrotView view;
	private MandelbrotSet set;
	
	public Controller(MandelbrotSet set, MandelbrotView view){
		this.set = set;
		this.view = view;
	}
	
	public void started(Complex c0, double diam){
		new Master(c0,diam,set,view).start();
	}

	public void stopped() {
		// what to do??
	}

}
