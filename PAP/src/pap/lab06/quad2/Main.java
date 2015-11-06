package pap.lab06.quad2;

import java.util.concurrent.*;

public class Main {

	public static void main(String args[]) {	

		IFunction mf = new MyFunction();
		double a = 0;
		double b = Math.PI;
		int numTasks = 100;
		
		QuadratureResult result = new QuadratureResult(numTasks);
		Bag<Task> taskTodos = new Bag<Task>();		

		int nWorkers = Runtime.getRuntime().availableProcessors()+1;		
		for (int i = 0; i < nWorkers; i++){
			new Worker(taskTodos, result).start();
		}
		
		new Master(mf, a, b, numTasks, taskTodos, result).start();
		
	}
	
}
