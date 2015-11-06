package pap.ass06.smallGrid;

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
	
	public Controller(GameOfLifeSet set, GameOfLifeView view){
		this.set = set;
		this.view = view;
	}

	@Override
	public void started(ArrayList<Point> matrix){
		set.setupMatrix(matrix);
		stopFlag = new Flag();
		new QuadratureService(set,view,stopFlag, matrix).start();
		
	}

	@Override
	public void stopped() {
		stopFlag.set();
	}
	
	

}

