package pap.lab06.mandel03;

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
	private Flag stopFlag;
	
	public Master(Complex c0, double diam, MandelbrotSet set, MandelbrotView view, Flag stopFlag){
		this.set = set;
		this.view = view;
		this.c0 = c0;
		this.diam = diam;
		this.stopFlag = stopFlag;
	}
	
	public void run(){
		try {
			view.changeState("Processing...");
			long t0 = System.currentTimeMillis();
			int nTasks = Runtime.getRuntime().availableProcessors()+1;
			ExecutorService executor = Executors.newFixedThreadPool(nTasks);
			
			int w = set.getSizeX();
	       	int x0 = 0;
	    	int dx = w / nTasks;       
	    	
	    	for (int i = 0; i < nTasks; i++){
				executor.execute(new ComputeStripeTask(x0, x0+dx, c0, diam, set, stopFlag));
	    		x0 += dx;
	    	}
	    	executor.shutdown();
	    	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
	    	view.setUpdated(set);
	    	//Thread.sleep(1000);
	    	
	    	if (!stopFlag.isSet()){
	    		long t1 = System.currentTimeMillis();
	    		view.changeState("completed - time elapsed: "+(t1-t0));
	    	} else {
	    		view.changeState("interrupted");
	    	}
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
