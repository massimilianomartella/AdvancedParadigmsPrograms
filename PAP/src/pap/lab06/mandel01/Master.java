package pap.lab06.mandel01;

import java.util.concurrent.*;

/**
 * Master controller agent 
 * 
 * @author aricci
 *
 */
public class Master extends Thread {

	private MandelbrotView view;
	private MandelbrotSet set;
	private Complex c0;
	private double diam;
	
	public Master(Complex c0, double diam, MandelbrotSet set, MandelbrotView view){
		this.set = set;
		this.view = view;
		this.c0 = c0;
		this.diam = diam;
	}
	
	public void run(){
		try {
			view.changeState("Processing...");
			long t0 = System.currentTimeMillis();

			int nWorkers = Runtime.getRuntime().availableProcessors()+1;
			CountDownLatch allTasksDone = new CountDownLatch(nWorkers);
			
			int w = set.getSizeX();
	       	int x0 = 0;
	    	int dx = w / nWorkers;       
	    	
	    	for (int i = 0; i < nWorkers; i++){
				new Worker(x0, x0+dx, c0, diam, set, allTasksDone).start();
	    		x0 += dx;
	    	}
	    	allTasksDone.await();
    		long t1 = System.currentTimeMillis();
    		view.changeState("completed - time elapsed: "+(t1-t0));
    		view.setUpdated(set);
	    	
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void log(String msg){
		synchronized(System.out){
			System.out.println(msg);
		}
	}
	
}
