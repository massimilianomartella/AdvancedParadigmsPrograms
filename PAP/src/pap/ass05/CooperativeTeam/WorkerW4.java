package pap.ass05.CooperativeTeam;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class WorkerW4 extends Thread {

	private UnsafeCounter c2, c3;
	private Semaphore binarySemaphoreW2, semaphoreS4;
	private MyMonitor tempo;

	public WorkerW4(UnsafeCounter counter2, UnsafeCounter counter3,
			Semaphore s2, Semaphore s4, MyMonitor myTempo) {
		c2 = counter2;
		c3 = counter3;
		binarySemaphoreW2 = s2;
		semaphoreS4 = s4;
		tempo = myTempo;
	}

	public void run() {
		Random random = new Random();
		while (tempo.isOn()) {
			try {
				Thread.sleep(1 + (int) (random.nextDouble() * 500));
				if (binarySemaphoreW2.availablePermits() == 0) {
					System.out.println("W4 (print): c2=" + c2.getValue());

					// Infine W5 ha il compito di stampare il valore di c3 solo
					// dopo che è stato incrementato sia da W3 che W4.
					semaphoreS4.acquire();
					c3.inc();

					// Analogamente W4 ha il compito di stampare il valore di c2
					// ogni volta che viene aggiornato da W2 e quindi di
					// incrementare c3.
					binarySemaphoreW2.release();
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}
