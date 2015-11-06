package pap.lab06.quad2;

public class Worker extends Thread {

	private Bag<Task> taskTodos;
	private QuadratureResult result;
	
	public Worker(Bag<Task> in, QuadratureResult result) {
		this.taskTodos = in;
		this.result = result;
	}

	public void run() {
		while (true){
			try {
				log("Waiting for tasks.");
				Task todo = taskTodos.remove();
				log("Retrieved task todo " + todo.getA() + " " + todo.getB());
				double sum = 0;
				double step = (todo.getB() - todo.getA()) / 1000;
				double x = todo.getA();
				for (int i = 0; i < 1000; i++) {
					sum += step * todo.getFunction().eval(x);
					x += step;
				}
				result.add(sum);
				log("Added result " + todo.getA() + " " + todo.getB() + " " + sum);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private void log(String msg) {
		System.out.println("[WORKER " + this + " ] " + msg);
	}
}
