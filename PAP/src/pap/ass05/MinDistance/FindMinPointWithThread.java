package pap.ass05.MinDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.vecmath.Point3d;

public class FindMinPointWithThread {
	private List<Point3d> p;
	private Point3d c;

	/**
	 * 
	 * @param points
	 * @param toFind
	 */
	public FindMinPointWithThread(List<Point3d> points, Point3d toFind) {
		p = points;
		c = toFind;
	}

	/**
	 * 
	 */
	public void findDistanceMin() {
		int n = Utils.NUM_POINTS;

		// Calcola il numero di processori presenti sulla macchina
		int nProc = Runtime.getRuntime().availableProcessors();

		// Divido tutti i punti e li assegno ai processori
		int range = n / nProc;
		int from = 0;
		int to = range;

		// Assegno ad ogni processore il range da cercare
		List<Thread> tlist = new ArrayList<Thread>();
		Semaphore s = new Semaphore(0);
		Buffer<Point3d> listPointMin = new Buffer<Point3d>(nProc);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < nProc; i++) {

			// Ogni thread lanciato dal processore trova il minimo

			List<Point3d> t = new ArrayList<Point3d>(p.subList(from, to));
			ThreadMinDistance pointsMin = new ThreadMinDistance(t, c, s,
					listPointMin, i);

			from += range;
			to += range;

			pointsMin.start();
			tlist.add(pointsMin);
		}

		/**
		 * Attenzione! In questa fase devo utilizzare un semaforo contenente
		 * tanti flag quanti sono i processori in modo da andare avanti se e
		 * solo se tutti i thread lanciati hanno finito di cercare il loro punto
		 * minimo.
		 * 
		 * In pratica dovrei utilizzare una variabile condivisa che mi permetta
		 * di andare avanti a cercare il punto minimo dei nProc (4, nel mio
		 * caso) punti minimi trovati.
		 */

		try {
			// Aspetto che tutti i thread finiscano
			System.out.println("Waiting All Threads to Finish");
			s.acquire(nProc);
			System.out.println("All Threads are Finished");

			// Una volta trovato i minimi, per ogni processore, trovo il minimo
			// totale
			Point3d pMin = new Point3d(Utils.MAX_RAND, Utils.MAX_RAND,
					Utils.MAX_RAND);
			for (int i = 0; i < listPointMin.getSize(); i++) {
				if (listPointMin.take(i).distance(c) <= c.distance(pMin))
					pMin = listPointMin.take(i);
			}
			System.out.println("Point min with Thread: " + pMin);
			long stopTime = System.currentTimeMillis() - startTime;
			System.out.println("Total time with Thread: " + stopTime + " ms");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Ammazzo tutti i thread
			tlist.stream().forEach(t -> {
				try {
					t.join();
				} catch (Exception ex) {
				}
			});
		}
	}
}
