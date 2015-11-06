package pap.ass06.smallGrid;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Il Thread master lancia il thread QuadratureService che restituisce lo stato
 * di tutte le celle "live" o "dead" della grigli. Successivamente, aggiorna la
 * griglia sul model (GameOfLifeSet) e sulla view (GameOfLifeView)
 * 
 * @author Martella Massimiliano
 *
 */
public class QuadratureService extends Thread {

	private GameOfLifeView view;
	private GameOfLifeSet set;
	private Flag stopFlag;
	boolean[][] pointBoardT0;

	/**
	 * Thread principale per controllare lo stato dell'algoritmo Game of life
	 * 
	 * @param set
	 * @param view
	 * @param stopFlag
	 * @param matrix
	 */
	public QuadratureService(GameOfLifeSet set, GameOfLifeView view, Flag stopFlag,
			ArrayList<Point> matrix) {
		this.view = view;
		this.set = set;
		this.stopFlag = stopFlag;
		pointBoardT0 = new boolean[3][3];
	}

	@Override
	public void run() {
		try {

			if (set.getMatrix().isEmpty()) {
				stopFlag.set();
				view.changeState("No point in Matrix!");
			}
			while (!stopFlag.isSet()) {
				Thread.sleep(50);
				int poolSize = Runtime.getRuntime().availableProcessors() + 1;

				/*
				 * Creo service il quale calcola lo stato delle celle. Il
				 * risultato di tutte le celle computate viene salvato nella
				 * variabile result (ArrayList<Point>) che viene passato sia al
				 * model che alla view per aggiornare lo stato della struttura
				 * dati e la visualizzazione della griglia a video
				 */
				Master service = new Master(set,
						poolSize, stopFlag);
				ArrayList<Point> result = service.compute();
				set.setupMatrix(result);
				view.setUpdated(result);

				if (stopFlag.isSet()) {
					view.changeState("Interrupted. Cell live: " + result.size());
				} else
					view.changeState(String.valueOf(result.size()));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
