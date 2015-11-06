package pap.ass06.smallGrid;

import java.awt.Point;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * La classe implementa Callable<Point>: in pratica viene calcolato ogni punto
 * dell'istante T1 a seconda dell'istante T0 (gameBoardT0)
 * 
 * @author Martella Massimiliano
 *
 */
public class ComputeTask implements Callable<Point> {

	private GameOfLifeSet result;
	boolean pointT0;
	boolean[][] pointBoard;
	int i, j;
	private Flag stopFlag;

	/**
	 * Costruttore
	 * @param set : modello dell'algoritmo
	 * @param gameBoardT0 : punto all'istante T0
	 * @param pointBoard : vicini del punto all'istante T0
	 * @param i : coordinata x del nuovo punto
	 * @param j : coodinata y del nuovo punto
	 * @param stopFlag : monitor di start e stop
	 */
	public ComputeTask(GameOfLifeSet set, boolean gameBoardT0,
			boolean[][] pointBoard, int i, int j, Flag stopFlag) {
		this.pointBoard = pointBoard;
		this.pointT0 = gameBoardT0;
		this.i = i;
		this.j = j;
		this.result = set;
		this.stopFlag = stopFlag;
	}

	private void log(String msg) {
		synchronized (System.out) {
			System.out.println(msg);
		}
	}

	/*
	 * Restituisce lo stato del punto all'istante T1
	 * */
	@Override
	public Point call() {
		Point pointT1 = null;
		try {
			pointT1 = result.computePoint(pointT0, pointBoard, i, j);
			if (stopFlag.isSet()) {
				// log("task interrupted");
			} else {
				// log("task completed");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return pointT1;
	}

}
