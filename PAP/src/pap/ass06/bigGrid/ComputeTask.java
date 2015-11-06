package pap.ass06.bigGrid;

import java.awt.Point;
import java.util.List;
import java.util.concurrent.Callable;

public class ComputeTask implements Callable<Point> {

	private GameOfLifeSet result;
	boolean pointT0;
	boolean[][] pointBoard;
	int i, j;
	private Flag stopFlag;

	public ComputeTask(GameOfLifeSet set, boolean gameBoardT0, boolean[][] pointBoard, int i, int j, Flag stopFlag) {
		this.pointBoard = pointBoard;
		this.pointT0 = gameBoardT0;
		this.i = i;
		this.j = j;
		this.result = set;
		this.stopFlag = stopFlag;
	}
	
	private void log(String msg){
		synchronized(System.out){
			System.out.println(msg);
		}
	}

	@Override
	public Point call() throws Exception {
		Point pointT1 = null;
		try {			
			pointT1 = result.computePoint(pointT0, pointBoard, i, j);
			if (stopFlag.isSet()){
				//log("task interrupted");
			} else {
				//log("task completed");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return pointT1;
	}

}
