package pap.ass06.smallGrid;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.spi.SyncResolver;

import com.sun.swing.internal.plaf.synth.resources.synth;

/**
 * Modello del sistema. Viene implementato l'algoritmo per calcolare se il punto
 * deve vivere o morire
 * 
 * @author Martella Massimiliano
 *
 */
public class GameOfLifeSet {

	private ArrayList<Point> point = new ArrayList<Point>(0);
	private Dimension d_gameBoardSize;

	/**
	 * La classe implementa l'algoritmo per calcolare se, dato un punto della
	 * griglia, esso deve vivere o morire a seconda dei suoi punti vicini
	 * 
	 * @param matrixSize
	 */
	public GameOfLifeSet(Dimension matrixSize) {
		this.d_gameBoardSize = matrixSize;
	}

	public int getSizeX() {
		return d_gameBoardSize.width;
	}

	public int getSizeY() {
		return d_gameBoardSize.height;
	}

	public ArrayList<Point> getMatrix() {
		return point;
	}

	public void setupMatrix(ArrayList<Point> matrix) {
		point = matrix;
	}

	/**
	 * A seconda dei punti vicini, il punto pointT0, all'istante T1, diventa
	 * "live" o "dead".
	 * 
	 * @param pointT0
	 *            : punto da valutare
	 * @param pointBoard
	 *            : punti vicini
	 * @param i
	 *            : coordinata x del punto che deve vivere
	 * @param j
	 *            : coordinata y del punto che deve vivere
	 * @return
	 */
	public Point computePoint(boolean pointT0, boolean[][] pointBoard, int i,
			int j) {
		int surrounding = 0;
		if (pointBoard[0][0])
			surrounding++;
		if (pointBoard[0][1])
			surrounding++;
		if (pointBoard[0][2])
			surrounding++;
		if (pointBoard[1][0])
			surrounding++;
		// pointBoard[1][1]
		if (pointBoard[1][2])
			surrounding++;
		if (pointBoard[2][0])
			surrounding++;
		if (pointBoard[2][1])
			surrounding++;
		if (pointBoard[2][2])
			surrounding++;

		if (pointT0) {
			// una cella m[i,j] che nello stato s(t) è live e ha
			// due o tre celle vicine live,
			// nello stato s(t+1) rimane live (“sopravvive”)
			if ((surrounding == 2) || (surrounding == 3))
				return new Point(i - 1, j - 1);
		} else {
			// una cella m[i,j] che nello stato s(t) è dead e ha
			// tre celle vicine live,
			// nello stato s(t+1) diventa live
			if (surrounding == 3)
				return new Point(i - 1, j - 1);
		}
		/**
		 * 1) una cella m[i,j] che nello stato s(t) è live e ha zero o al più
		 * una cella vicina live (e le altre dead), nello stato s(t+1) diventa
		 * dead (“muore di solitudine”)
		 *
		 * 2) una cella m[i,j] che nello stato s(t) è live e ha quattro o più
		 * celle vicine live, nello stato s(t+1) diventa dead (“muore di
		 * sovrappopolamento”)
		 */
		return null;
	}
}
