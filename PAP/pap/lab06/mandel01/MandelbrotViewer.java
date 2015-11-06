package pap.lab06.mandel01;

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
		int nIter = 10000;
		
		MandelbrotSet set = new MandelbrotSet(w, h, nIter);
		MandelbrotView view = new MandelbrotView(w, h);
		//set.addListener(view);
		Controller controller = new Controller(set, view);
		view.addListener(controller);
		view.setVisible(true);
	}

}
