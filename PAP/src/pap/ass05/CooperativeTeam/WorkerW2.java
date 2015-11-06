package pap.ass05.CooperativeTeam;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class WorkerW2 extends Thread {

	private UnsafeCounter c2;
	private Semaphore binarySemaphoreW2, binarySemaphoreW6;
	private MyMonitor tempo;

	public WorkerW2(UnsafeCounter counter2, Semaphore s2, Semaphore s6,
			MyMonitor myTempo) {
		c2 = counter2;
		binarySemaphoreW2 = s2;
		binarySemaphoreW6 = s6;
		tempo = myTempo;
	}

	public void run() {
		Random random = new Random();
		while (tempo.isOn()) {
			try {
				Thread.sleep(1 + (int) (random.nextDouble() * 500));
				// W1 e W2 hanno il compito di incrementare rispettivamente c1 e
				// c2, concorrentemente e ripetutamente.
				if (binarySemaphoreW2.availablePermits() != 0) {
					c2.inc(); // faccio l'incremento
					// System.out.println("W2 inc(): c2=" + c2.getValue());
					binarySemaphoreW2.acquire();

					// W1 e W2 possono procedere ad un nuovo incremento solo
					// dopo che W5 ha stampato il valore di c3.
					binarySemaphoreW6.acquire();
				}

			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} finally {

			}
		}
	}
}
