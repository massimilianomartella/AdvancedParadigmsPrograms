package pap.ass05.CooperativeTeam;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class WorkerW3 extends Thread {

	private UnsafeCounter c1, c3;
	private Semaphore binarySemaphoreW1, semaphoreS3;
	private MyMonitor tempo;

	public WorkerW3(UnsafeCounter counter1, UnsafeCounter counter3,
			Semaphore s1, Semaphore s3, MyMonitor myTempo) {
		c1 = counter1;
		c3 = counter3;
		binarySemaphoreW1 = s1;
		semaphoreS3 = s3;
		tempo = myTempo;
	}

	public void run() {
		Random random = new Random();
		while (tempo.isOn()) {
			try {
				Thread.sleep(1 + (int) (random.nextDouble() * 500));
				if (binarySemaphoreW1.availablePermits() == 0) {
					System.out.println("W3 (print): c1=" + c1.getValue());
					// Infine W5 ha il compito di stampare il valore di c3 solo
					// dopo che è stato incrementato sia da W3 che W4.
					semaphoreS3.acquire();
					c3.inc();

					// W3 ha il compito di stampare il valore di c1 ogni volta
					// che viene aggiornato da W1 e quindi di incrementare c3.
					binarySemaphoreW1.release();
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}
