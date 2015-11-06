package pap.ass05.CooperativeTeam;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class WorkerW5 extends Thread {

	private UnsafeCounter c3;
	private Semaphore semaphoreS3, semaphoreS4, semaphoreS5, semaphoreS6;
	private int iterator; // numero di iterazioni totali
	private MyMonitor tempo;

	public WorkerW5(UnsafeCounter counter3, Semaphore s3, Semaphore s4,
			Semaphore s5, Semaphore s6, int numIteratorTot, MyMonitor myTempo) {
		c3 = counter3;
		semaphoreS3 = s3;
		semaphoreS4 = s4;
		semaphoreS5 = s5;
		semaphoreS6 = s6;
		iterator = numIteratorTot;
		tempo = myTempo;
	}

	public void run() {
		Random random = new Random();

		while (true) {
			try {
				Thread.sleep(1 + (int) (random.nextDouble() * 500));
				if (semaphoreS3.availablePermits() == 0
						&& semaphoreS4.availablePermits() == 0) {
					System.out.println("W5 (print): c3=" + c3.getValue());
					

					// Infine W5 ha il compito di stampare il valore di c3 solo
					// dopo che è stato incrementato sia da W3 che W4.
					semaphoreS3.release();
					semaphoreS4.release();
					
					// W1 e W2 possono procedere ad un nuovo incremento solo
					// dopo che W5 ha stampato il valore di c3.
					semaphoreS6.release();
					semaphoreS5.release();
				}

			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} finally {
				iterator--;
				if (iterator == 0)
					tempo.setOn(false);
			}
		}
	}
}
