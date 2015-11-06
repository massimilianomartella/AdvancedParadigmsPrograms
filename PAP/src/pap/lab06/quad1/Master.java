package pap.lab06.quad1;

public class Master extends Thread {

	private IFunction mf;
	private double a, b;
	private int numTask;
	private Bag<Task> taskTodos;
	private Bag<TaskResult> taskResults;
	
	public Master (IFunction mf, double a, double b, int numTask, Bag<Task> in, Bag<TaskResult> out){		
		this.mf = mf;
		this.a = a;
		this.b = b;
		this.numTask=numTask;
		taskTodos = in;
		taskResults = out;
	}
	
	public void run() {
		double x0 = a;
		double step = (b-a)/numTask;		
		for (int i=0; i<numTask; i++) {
			try {
				taskTodos.insert(new Task(x0, x0 + step, mf));
				log("assigned task "+x0+" "+(x0+step));
				x0 += step;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		
		double sum = 0;
		for (int i=0; i<numTask; i++) {
			try {
				TaskResult tr=taskResults.remove();
				log("collected result "+tr.getResult());
				sum += tr.getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Result: "+sum);
		System.exit(0);
	}
	
	
	private void log(String msg){
		System.out.println("[MASTER] "+msg);
	}
}
