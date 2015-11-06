package pap.ass06.smallGrid;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Interfaccia InputListener
 * @author Martella Massimiliano
 *
 */
public interface InputListener {
	/**
	 * Start dell'algoritmo Game of Life
	 * @param matrix : matrice di celle vive
	 */
	void started(ArrayList<Point> matrix);
	
	/**
	 * Stop dell'algoritmo
	 */
	void stopped();
}
