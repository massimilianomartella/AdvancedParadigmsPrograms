package pap.lab06.mandel02;

import java.util.concurrent.CountDownLatch;

/**
 * Worker agents doing the computational work.
 * 
 * @author aricci
 *
 */
public class Worker extends Thread {

	private MandelbrotSet result;
	private CountDownLatch latch;
	private int x0;
	private int x1;
	private Complex c0;
	private double diam;
	private Flag stopFlag;

	public Worker(int x0, int x1, Complex c0, double diam, MandelbrotSet result, CountDownLatch latch, Flag stopFlag) {
		this.result = result;
		this.latch = latch;
		this.x0 = x0;
		this.x1 = x1;
		this.diam = diam;
		this.c0 = c0;
		this.stopFlag = stopFlag;
	}

	public void run() {
		try {
			log("start working ");
			int nSteps = 4;
			int dx = (x1 - x0) / nSteps;       
			//boolean stopped = false;
			for (int i = 0; i < nSteps; i++){
				if (stopFlag.isSet()){
					break;
				}
				result.computeSlice(x0, x0 + dx, c0, diam);
				x0 += dx;
			}
			if (stopFlag.isSet()){
				log("task interrupted");
			} else {
				log("task completed");
			}
			latch.countDown();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void log(String msg){
		synchronized(System.out){
			System.out.println(msg);
		}
	}

}
