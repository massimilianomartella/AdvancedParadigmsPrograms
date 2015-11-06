package pap.ass06.bigGrid;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

public class QuadratureService extends Thread {

	// private int numTasks;
	private ExecutorService executor;
	boolean[][] gameBoard;
	boolean[][] pointBoardT0;
	GameOfLifeSet set;
	Flag stopFlag;

	public QuadratureService(GameOfLifeSet set, int poolSize, Flag stopFlag) {
		this.executor = Executors.newFixedThreadPool(poolSize);
		this.set = set;
		this.stopFlag = stopFlag;
	}

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

					int check = 0;
					for (boolean state[] : pointBoardT0) {
						for (boolean b : state) {
							if (b)
								check++;
						}
					}

					if (check != 0) {
						Future<Point> res = executor.submit(new ComputeTask(
								set, gameBoard[i][j], pointBoardT0, i, j,
								stopFlag));
						pointT1.add(res);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		ArrayList<Point> point = new ArrayList<Point>();
		
		pointT1.stream().forEach(f -> {
			try {
				if (f.get() != null)
					point.add(f.get());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
//		for (Future<Point> future : pointT1) {
//			try {
//				if (future.get() != null)
//					point.add(future.get());
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//		}

		executor.shutdown();
		return point;
	}
}
