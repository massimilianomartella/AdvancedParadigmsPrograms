package pap.lab04.test03;

public class MyWorkerA extends Worker {
	
	public MyWorkerA(String name){
		super(name);
	}
	
	public void run(){
		println("a1");
		wasteTime(100);
		println("a2");
	}
	
}

