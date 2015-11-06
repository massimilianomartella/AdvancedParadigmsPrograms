package pap.lab06.quad1;

public class Worker extends Thread {

	private Bag<Task> taskTodos;
	private Bag<TaskResult> taskResults;

	public Worker(Bag<Task> in, Bag<TaskResult> out) {
		this.taskTodos = in;
		this.taskResults = out;
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
				taskResults.insert(new TaskResult(todo, sum));
				log("Inserted result " + todo.getA() + " " + todo.getB() + " " + sum);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private void log(String msg) {
		System.out.println("[WORKER " + this + " ] " + msg);
	}
}
