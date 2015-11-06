package pap.ass05.sol.team;

import java.util.concurrent.Semaphore;

class WorkerA extends Thread {

	private Counter c;
	private Semaphore doneInc, ready;
	
	public WorkerA(Counter c, Semaphore doneInc, Semaphore ready){
		this.c = c;
		this.doneInc = doneInc;
		this.ready = ready;
	}
	
	public void run(){
		while (true){
			c.inc();
			doneInc.release();
			try {
				ready.acquire();
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
	}	
}

class WorkerB extends Thread {

	private Counter cA, cB;
	private Semaphore doneIncA, doneIncB, mutex;
	
	public WorkerB(Counter cA, Counter cB, Semaphore doneIncA, Semaphore mutex, Semaphore doneIncB){
		this.cA = cA;
		this.cB = cB;
		this.doneIncA = doneIncA;
		this.doneIncB = doneIncB;
		this.mutex = mutex;
	}
	
	public void run(){
		while (true){
			try {
				doneIncA.acquire();
				System.out.println("c1/2: "+cA.getValue()+" ("+cA+") by "+this);
				mutex.acquire();
				cB.inc();
				mutex.release();
				doneIncB.release();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
		}
	}	
}

class WorkerC extends Thread {

	private Counter c;
	private Semaphore doneIncA, doneIncB, nextTurnA, nextTurnB;
	
	public WorkerC(Counter c, Semaphore doneIncA, Semaphore doneIncB, Semaphore nextTurnA, Semaphore nextTurnB){
		this.c = c;
		this.doneIncA = doneIncA;
		this.doneIncB = doneIncB;
		this.nextTurnA = nextTurnA;
		this.nextTurnB = nextTurnB;
	}
	
	public void run(){
		while (true){
			try {
				doneIncA.acquire();
				doneIncB.acquire();
				System.out.println("c3: "+c.getValue());
				Thread.sleep(1000);
				nextTurnA.release();
				nextTurnB.release();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
		}
	}	
}


public class TestTeam {

	public static void main(String[] args) {

		Counter c1 = new Counter();
		Counter c2 = new Counter();
		Counter c3 = new Counter();
		
		Semaphore mutex = new Semaphore(1);
		Semaphore doneIncC1 = new Semaphore(0);
		Semaphore doneIncC2 = new Semaphore(0);
		Semaphore doneIncC3a = new Semaphore(0);
		Semaphore doneIncC3b = new Semaphore(0);
		Semaphore nextTurnA = new Semaphore(0);
		Semaphore nextTurnB = new Semaphore(0);
		
		WorkerA w1 = new WorkerA(c1,doneIncC1,nextTurnA);
		WorkerA w2 = new WorkerA(c2,doneIncC2,nextTurnB);
		WorkerB w3 = new WorkerB(c1,c3,doneIncC1,mutex,doneIncC3a);
		WorkerB w4 = new WorkerB(c2,c3,doneIncC2,mutex,doneIncC3b);
		WorkerC w5 = new WorkerC(c3,doneIncC3a,doneIncC3b,nextTurnA,nextTurnB);
		
		w1.start();
		w2.start();
		w3.start();
		w4.start();
		w5.start();
	
	}

}
