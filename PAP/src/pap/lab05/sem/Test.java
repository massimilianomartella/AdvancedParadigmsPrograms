package pap.lab05.sem;

//import gov.nasa.jpf.jvm.Verify;

import java.util.concurrent.Semaphore;


public class Test {
	
	private static final int BUFFER_SIZE = 5;
	private static final int N_PRODUCERS = 2;
	private static final int N_CONSUMERS = 3;

	public static void main(String[] args) {
		// Verify.beginAtomic();
		BoundedBuffer<Item> buffer = new BoundedBuffer<Item>(BUFFER_SIZE);
		for(int i=0; i<N_PRODUCERS; i++){
			new Producer(buffer).start();
		}
		for(int i=0; i<N_CONSUMERS; i++){
			new Consumer(buffer).start();
		}
		//Verify.endAtomic();
	}

}
