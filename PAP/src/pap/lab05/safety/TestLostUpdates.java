package pap.lab05.safety;

public class TestLostUpdates {

	static class UnsafeCounter {

		private int cont;
		
		public UnsafeCounter(int base){
			this.cont = base;
		}
		
		public void inc(){
			cont++;
		}
		
		public int getValue(){
			return cont;
		}
	}

	static class Worker extends Thread{
		
		private UnsafeCounter counter;
		private int ntimes;
		
		public Worker(UnsafeCounter c, int ntimes){
			counter = c;
			this.ntimes = ntimes;
		}
		
		public void run(){
			for (int i = 0; i < ntimes; i++){
				counter.inc();
			}
		}
	}	
	
	public static void main(String[] args) throws Exception {
		int ntimes = 1000000;
		UnsafeCounter c = new UnsafeCounter(0);
		Worker w1 = new Worker(c,ntimes);
		Worker w2 = new Worker(c,ntimes);
		w1.start();
		w2.start();
		w1.join();
		w2.join();
		System.out.println("Counter final value: "+c.getValue());
	}
}
