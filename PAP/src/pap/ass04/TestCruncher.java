package pap.ass04;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class TestCruncher {

	/*
	 * launch program from terminal: java -cp ./Documents/workspace/PAP/bin/
	 * pap.ass04.TestCruncher 434767576
	 */

	public static void main(String args[]) {

		long max;// = Integer.parseInt(args[0]);
		max = 434767576;
		Secret blackNum = new Secret(max);

		int howMany = Runtime.getRuntime().availableProcessors();
		long t0 = System.currentTimeMillis();
		System.out.println("Number of processor: " + howMany);

		List<Thread> tlist = new ArrayList<Thread>();

		// Suddivido il problema splittando la ricerca per il numero dei
		// processori
		long range = (max / howMany);
		for (int i = 0; i < howMany; i++) {
			long rangeStartNum = i * range;
			long rangeStopNum = (i + 1) * range - 1;
			// Assegno ad ogni processore il range da cercare
			Cerca c = new Cerca(rangeStartNum, rangeStopNum, blackNum, t0);
			c.start();
			tlist.add(c);
			
		}

		// Se non trovo il numero (impossibile)
		tlist.stream().forEach(t -> {
			try {
				t.join();
			} catch (Exception ex) {
			}
		});

	}
}

class Cerca extends Thread {

	long rangeStartNumbers, rangeStopNumbers, t0;
	Secret blackNum;

	/**
	 * Viene cercato il numero nascosto in un range definito a priori
	 * @param rangeStartNumbers: limite range minimo
	 * @param rangeStopNumbers: limite range massimo
	 * @param blackNum: numero nascosto da indovinare
	 * @param t0: tempo iniziale della ricerca
	 */
	public Cerca(long rangeStartNumbers, long rangeStopNumbers,
			Secret blackNum, long t0) {
		this.rangeStartNumbers = rangeStartNumbers;
		this.rangeStopNumbers = rangeStopNumbers;
		this.blackNum = blackNum;
		this.t0 = t0;
	}

	@Override
	public void run() {
		LongStream.rangeClosed(rangeStartNumbers, rangeStopNumbers).forEach(
				i -> {
					if (blackNum.guess(i)) {
						long t1 = System.currentTimeMillis();
						System.out.println(getName() + ": number guessed -> " + i);
						System.out.println("Time elapsed: " + (t1 - t0));
						System.exit(0);
					}
				});
	}
}
