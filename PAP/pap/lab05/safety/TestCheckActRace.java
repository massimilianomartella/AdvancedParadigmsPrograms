package pap.lab05.safety;

//import gov.nasa.jpf.jvm.Verify;

/**
 * Check race condition: check-and-act concurrency hazard
 */
public class TestCheckActRace {

	static class Counter {
		private int count;
		public Counter(){
			count = 0;
		}
		public synchronized void inc(){
			count++;
		}
		public synchronized int getValue(){
			return count;
		}
	}

	static class MyThread extends Thread {
		private Counter c;
		public MyThread(Counter c){
			this.c = c;
		}
		public void run(){
			if (c.getValue() == 0){
				try {
					Thread.sleep(100);
				} catch (Exception ex){}
				c.inc();
			}
		}		
	}	
	
	public static void main(String[] args) throws Exception {
		Counter c = new Counter();
		Thread th0 = new MyThread(c);
		Thread th1 = new MyThread(c);
		th0.start();
		th1.start();
		th0.join();
		th1.join();
		System.out.println("Counter final value: "+c.getValue());
		
	}
	
}
