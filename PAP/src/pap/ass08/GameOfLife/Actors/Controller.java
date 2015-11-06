package pap.ass08.GameOfLife.Actors;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Controllo dell'applicazione
 * 
 * @author Martella Massimiliano
 *
 */
public class Controller implements InputListener {

	private GameOfLifeView view;
	private GameOfLifeSet set;
	private Flag stopFlag;

	public Controller(GameOfLifeSet set, GameOfLifeView view) {
		this.set = set;
		this.view = view;
	}

	@Override
	public void started(ArrayList<Point> matrix) {
		set.setupMatrix(matrix);
		stopFlag = new Flag();
		// Il Thread Master mi assicura che la creazione degli N actorsPoint non
		// venga eseguita dall'Event Dispatcher Thread
		new Master(set, view, stopFlag, matrix).start();

	}

	@Override
	public void stopped() {
		stopFlag.set();
	}

}
