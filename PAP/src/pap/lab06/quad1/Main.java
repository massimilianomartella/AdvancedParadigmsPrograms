package pap.lab06.quad1;

import java.util.concurrent.*;

public class Main {

	public static void main(String args[]) throws Exception {	

		Bag<Task> taskTodos = new Bag<Task>();
		Bag<TaskResult> taskResults = new Bag<TaskResult>();

		int nWorkers = Runtime.getRuntime().availableProcessors()+1;		
		for (int i = 0; i < nWorkers; i++){
			new Worker(taskTodos,taskResults).start();
		}
		
		new Master(new MyFunction(), 0, Math.PI/2, 50, taskTodos, taskResults).start();
		
	}
	
}
