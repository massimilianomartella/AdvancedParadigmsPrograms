package pap.ass05.CooperativeTeam;

import java.util.concurrent.Semaphore;

public class TestCounterWorkers {
	

	// protected static final Random random = new Random();

	static UnsafeCounter c1 = new UnsafeCounter(0);
	static UnsafeCounter c2 = new UnsafeCounter(0);
	static UnsafeCounter c3 = new UnsafeCounter(0);

	// verde 1 rosso 0
	static Semaphore sW1 = new Semaphore(1);
	static Semaphore sW2 = new Semaphore(1);

	static Semaphore sW3 = new Semaphore(1);
	static Semaphore sW4 = new Semaphore(1);
	
	static Semaphore sW5 = new Semaphore(1);
	static Semaphore sW6 = new Semaphore(1);

	public static void main(String args[]) throws InterruptedException {
		int numIterator = 20;
		MyMonitor tempo = new MyMonitor();
		tempo.isOn();
		
		//W1 e W2 hanno il compito di incrementare rispettivamente c1 e c2, concorrentemente e ripetutamente.
		WorkerW1 W1 = new WorkerW1(c1, sW1, sW5, tempo);
		WorkerW2 W2 = new WorkerW2(c2, sW2, sW6, tempo);
		
		//W3 ha il compito di stampare il valore di c1 ogni volta che viene aggiornato da W1 e quindi di incrementare c3.
		WorkerW3 W3 = new WorkerW3(c1, c3, sW1, sW3, tempo);
		
		//Analogamente W4 ha il compito di stampare il valore di c2 ogni volta che viene aggiornato da W2 e quindi di incrementare c3.
		WorkerW4 W4 = new WorkerW4(c2, c3, sW2, sW4, tempo);
		
		//Infine W5 ha il compito di stampare il valore di c3 solo dopo che è stato incrementato sia da W3 che W4.
		WorkerW5 W5 = new WorkerW5(c3, sW3, sW4, sW5, sW6, numIterator, tempo);
		
		

		W1.start();
		W2.start();
		W3.start();
		W4.start();
		W5.start();
		
		W1.join();
		W2.join();
		W3.join();
		W4.join();
		W5.join();
		
		System.exit(0);
	}
}