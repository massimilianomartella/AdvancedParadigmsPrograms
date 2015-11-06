package pap.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
 
public class a {
	public static void main(String[ ] args) {
		//Creo un nuovo thread pool.
		ExecutorService threadpool = Executors.newCachedThreadPool();
 
		//Metto in coda il task
		threadpool.execute(
	            //Questo oggetto rappresenta il task.
                    new Runnable() {
			public void run() {
				System.out.println("Vengo eseguito dal thread pool.");
			}
		});
 
                System.out.println("Il thread principale prosegue e poi si mette in attesa.");
		threadpool.shutdown();
                System.out.println("Il thread principale esce.");
	}
}