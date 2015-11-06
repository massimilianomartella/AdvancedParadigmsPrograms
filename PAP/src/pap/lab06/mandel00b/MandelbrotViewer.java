package pap.lab06.mandel00b;

/**
 * Mandelbrot Viewer - structured sequential program.
 * 
 * @author aricci
 *
 */
public class MandelbrotViewer {
	public static void main(String[] args) {
		
		int w = 800;
		int h = 600;
		int nIter = 5000;
		
		MandelbrotSet set = new MandelbrotSet(w, h, nIter);
		MandelbrotView view = new MandelbrotView(w, h);
		Controller controller = new Controller(set, view);
		view.addListener(controller);
		view.setVisible(true);
	}

}
