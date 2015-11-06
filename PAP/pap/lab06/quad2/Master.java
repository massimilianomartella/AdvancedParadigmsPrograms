package pap.lab06.quad2;

public class Master extends Thread {

	private IFunction mf;
	private double a, b;
	private int numTasks;
	private Bag<Task> taskTodos;
	private QuadratureResult result;
	
	public Master (IFunction mf, double a, double b,  int numTasks, Bag<Task> taskTodos, QuadratureResult result){		
		this.mf = mf;
		this.a = a;
		this.b = b;
		this.numTasks = numTasks;
		this.taskTodos = taskTodos;
		this.result = result;
	}
	
	public void run() {
		
		double x0 = a;
		double step = (b-a)/numTasks;		
		for (int i=0; i<numTasks; i++) {
			try {
				taskTodos.insert(new Task(x0, x0 + step, mf));
				log("assigned task "+x0+" "+(x0+step));
				x0 += step;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		try {
			double res = result.getResult();
			System.out.println("Result: "+res);	
			System.exit(0);
		} catch(Exception ex){
		}
	}
	
	
	private void log(String msg){
		System.out.println("[MASTER] "+msg);
	}
}
