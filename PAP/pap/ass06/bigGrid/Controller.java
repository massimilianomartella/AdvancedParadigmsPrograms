package pap.ass06.bigGrid;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Controller part of the application - passive part.
 * 
 * @author mmartella
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
		new Master(set,view,stopFlag, matrix).start();
		
	}

	@Override
	public void stopped() {
		stopFlag.set();
	}
	
	

}

