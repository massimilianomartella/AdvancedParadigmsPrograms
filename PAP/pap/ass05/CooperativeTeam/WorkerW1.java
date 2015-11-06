package pap.ass05.CooperativeTeam;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class WorkerW1 extends Thread {

	private UnsafeCounter c1;
	private Semaphore binarySemaphoreW1, binarySemaphoreW5;
	private MyMonitor tempo;

	public WorkerW1(UnsafeCounter counter1, Semaphore s1, Semaphore s5,
			MyMonitor myTempo) {
		c1 = counter1;
		binarySemaphoreW1 = s1;
		binarySemaphoreW5 = s5;
		tempo = myTempo;
	}

	public void run() {
		Random random = new Random();
		while (tempo.isOn()) {
			try {
				Thread.sleep(1 + (int) (random.nextDouble() * 500));
				// W1 e W2 hanno il compito di incrementare rispettivamente c1 e
				// c2, concorrentemente e ripetutamente.
				if (binarySemaphoreW1.availablePermits() != 0) {
					c1.inc(); // faccio l'incremento
					// System.out.println("W1 inc(): c1=" + c1.getValue());
					binarySemaphoreW1.acquire();

					// W1 e W2 possono procedere ad un nuovo incremento solo
					// dopo che W5 ha stampato il valore di c3.
					binarySemaphoreW5.acquire();
				}

			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} finally {

			}
		}
	}
}
