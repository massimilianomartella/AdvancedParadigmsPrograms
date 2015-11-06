package pap.ass05.MinDistance;

import java.util.List;
import java.util.concurrent.Semaphore;

import javax.vecmath.Point3d;

public class ThreadMinDistance extends Thread {

	// private int startPoints, stopPoints;
	private List<Point3d> points;
	private Point3d c;
	private Semaphore s;
	private Point3d pMin;
	private Buffer<Point3d> sharedListPointMin;
	private int numProc;

	public ThreadMinDistance(
			// int rangeStartPoints, int rangeStopPoints,
			List<Point3d> pPoints, Point3d cPoint, Semaphore semaphore,
			Buffer<Point3d> listPointMin, int i) {
		// startPoints = rangeStartPoints;
		// stopPoints = rangeStopPoints;
		points = pPoints;
		c = cPoint;
		s = semaphore;
		sharedListPointMin = listPointMin;
		numProc = i;
	}

	@Override
	public void run() {
		System.out.println("Processore numero " + numProc + " partito!");
		pMin = new Point3d(Utils.MAX_RAND, Utils.MAX_RAND, Utils.MAX_RAND);
		// for (int i = startPoints; i < stopPoints; i++) {
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).distance(c) <= c.distance(pMin))
				pMin = points.get(i);
		}

		System.out
				.println("Proc"
						+ numProc
						+ ": Punto min trovato! --> "
						+ pMin
						+ "\nImposto i permessi ed inserisco il punto nel buffer condiviso.");
		// try {
		s.release(1);
		sharedListPointMin.put(pMin);
		// } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

}
