package pap.lab06.mandel03;

/**
 * Mandelbrot Viewer - structured sequential program.
 * 
 * @author aricci
 *
 */
public class MandelbrotViewer {
	public static void main(String[] args) {
		
		int w = 2000;
		int h = 2000;
		int nIter = 5000;
		
		MandelbrotSet set = new MandelbrotSet(w, h, nIter);
		MandelbrotView view = new MandelbrotView(w, h);
		Controller controller = new Controller(set, view);
		view.addListener(controller);
		view.setVisible(true);
	}

}
