package pap.lab06.executors.quad03;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

public class QuadratureService extends Thread {

	private int numTasks;
	private ExecutorService executor;
	
	public QuadratureService (int numTasks, int poolSize){		
		this.numTasks = numTasks;
		executor = Executors.newFixedThreadPool(poolSize);
	}
	
	public double compute(IFunction mf, double a, double b) throws InterruptedException { 

		double x0 = a;
		double step = (b-a)/numTasks;		
	    Set<Future<Double>> resultSet = new HashSet<Future<Double>>();
	    //ArrayList<Future<Boolean>> test = new ArrayList<Future<Boolean>>();
		for (int i=0; i<numTasks; i++) {
			try {
				Future<Double> res = executor.submit(new ComputeAreaTask(x0, x0 + step, mf));
				resultSet.add(res);
				log("submitted task "+x0+" "+(x0+step));
				x0 += step;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}				

	    double sum = 0;
	    for (Future<Double> future : resultSet) {
	    	try {
	    		sum += future.get();
	    	} catch (Exception ex){
	    		ex.printStackTrace();
	    	}
	    }
	    System.out.printf("The result is %s\n", sum);
	    executor.shutdown();
		return sum;
	}
	
	
	private void log(String msg){
		System.out.println("[SERVICE] "+msg);
	}
}
