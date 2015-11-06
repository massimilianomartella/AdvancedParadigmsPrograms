package pap.ass06.bigGrid;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Master extends Thread {

	private GameOfLifeView view;
	private GameOfLifeSet set;
	private Flag stopFlag;
	// boolean[][] gameBoardT0;
	boolean[][] pointBoardT0;

	public Master(GameOfLifeSet set, GameOfLifeView view, Flag stopFlag,
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
				Thread.sleep(25);
				int poolSize = Runtime.getRuntime().availableProcessors() + 1;

				QuadratureService service = new QuadratureService(set, poolSize, stopFlag);
				ArrayList<Point> result = service.compute();
				set.setupMatrix(result);
				view.setUpdated(result);
				
				if (stopFlag.isSet()){
		    		view.changeState("Interrupted. Cell live: " + result.size());
		    	}
				else
					view.changeState(String.valueOf(result.size()));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
