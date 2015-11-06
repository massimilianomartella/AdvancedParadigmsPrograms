package pap.ass06.smallGrid;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Il problema viene suddiviso in task, tanti quanti sono i punti sulla griglia.
 * Ciascun punto si calcola, a seconda dei suoi vicini, se deve vivere o morire
 * 
 * @author Martella Massimiliano
 *
 */
public class Master extends Thread {

	private ExecutorService executor;
	boolean[][] gameBoard;
	boolean[][] pointBoardT0;
	GameOfLifeSet set;
	Flag stopFlag;

	/**
	 * 
	 * @param set	: modello dell'algoritmo
	 * @param poolSize : numero di Thread
	 * @param stopFlag : monitor di start e stop
	 */
	public Master(GameOfLifeSet set, int poolSize, Flag stopFlag) {
		this.executor = Executors.newFixedThreadPool(poolSize);
		//this.executor = Executors.newCachedThreadPool();
		this.set = set;
		this.stopFlag = stopFlag;
	}

	/**
	 * La funzione restituisce la lista dei punti vivi del gioco
	 * @return
	 * @throws InterruptedException
	 */
	public ArrayList<Point> compute() throws InterruptedException {

		ArrayList<Future<Point>> pointT1 = new ArrayList<Future<Point>>();
		gameBoard = new boolean[set.getSizeX() + 1][set.getSizeY() + 1];
		for (Point current : set.getMatrix()) {
			if (current != null)
				gameBoard[current.x + 1][current.y + 1] = true;
		}
		for (int i = 1; i < gameBoard.length - 1; i++) {
			for (int j = 1; j < gameBoard[0].length - 1; j++) {
				try {
					pointBoardT0 = new boolean[3][3];
					// carico i punti vicini all'istante T0
					pointBoardT0[0][0] = gameBoard[i - 1][j - 1];
					pointBoardT0[0][1] = gameBoard[i - 1][j];
					pointBoardT0[0][2] = gameBoard[i - 1][j + 1];
					pointBoardT0[1][0] = gameBoard[i][j - 1];
					// pointBoard[1][1] io
					pointBoardT0[1][2] = gameBoard[i][j + 1];
					pointBoardT0[2][0] = gameBoard[i + 1][j - 1];
					pointBoardT0[2][1] = gameBoard[i + 1][j];
					pointBoardT0[2][2] = gameBoard[i + 1][j + 1];

					/*
					 * Se il punto che sto valutando e tutto "false" (griglia
					 * bianca) allora è inutile che creo l'oggetto res di tipo
					 * Future<Point> in quanto sarà sempre "false" e la griglia
					 * non subisce variazioni.
					 */
					int check = 0;
					for (boolean state[] : pointBoardT0) {
						for (boolean b : state) {
							if (b)
								check++;
						}
					}
					if (check != 0) {
						/*
						 * Creo l'oggetto res di tipo Future che è una
						 * "promessa" di Point ("live" op. "dead")
						 */
						Future<Point> res = executor.submit(new ComputeTask(
								set, gameBoard[i][j], pointBoardT0, i, j,
								stopFlag));
						// Il risultato lo inserisco nella lista pointT1 (al
						// tempo T1)
						pointT1.add(res);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		ArrayList<Point> point = new ArrayList<Point>();
		for (Future<Point> future : pointT1) {
			try {
				if (future.get() != null)
					point.add(future.get());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		/*
		 * Una volta ottentuti tutti i punti dovuti, eseguo lo shutdown degli
		 * executor e restituisco la lista di punti ufficiale
		 */
		executor.shutdown();
		return point;
	}
}
