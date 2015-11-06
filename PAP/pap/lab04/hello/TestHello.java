package pap.lab04.hello;

public class TestHello {

	public static void main(String[] args){
		
		MyThread myThread = new MyThread("Pippo");
		/*
		 * Qual'è la differenza fra run e start?
		 * Il main thread va ad eseguire run*/
		myThread.start();
//		myThread.run();
		
		/**
		 * Possiamo lanciare il thread in due modi diversi
		 * */
//		Thread myThread2 = new Thread(new MyThread2("Pluto"));
		Thread myThread2 = new Thread(() -> {
			System.out.println("Sono paperino");
		});
		myThread2.start();
		
		String myName = Thread.currentThread().getName();		
		System.out.println("Thread spawned - I'm "+myName);
		
	}
}
