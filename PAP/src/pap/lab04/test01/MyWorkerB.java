package pap.lab04.test01;

public class MyWorkerB extends Worker {
	
	public MyWorkerB(String name){
		super(name);
	}

	public void run(){
		while (true){
		  action1();	
		  action2();
		}
	}
	
	protected void action1(){
		println("c2");
		wasteRandomTime(0,10);	
	}
	
	protected void action2(){
		println("d2");
		wasteRandomTime(100,200);	
	}
}
