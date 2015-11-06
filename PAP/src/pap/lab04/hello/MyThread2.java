package pap.lab04.hello;

public class MyThread2 implements Runnable {

	private String name;
	
	public MyThread2(String myName){
		name = myName;
	}
	
	public String getName() {
		return name;
	}
	
	public void run(){
		System.out.println("Hello concurrent world! I'm thread2");
	}
}
