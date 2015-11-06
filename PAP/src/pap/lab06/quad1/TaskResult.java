package pap.lab06.quad1;

public class TaskResult {
	
	private double result;
	private Task todo;
	
	public TaskResult(Task t, double result) {
		this.todo = t;
		this.result = result;
	}
	
	public double getResult() {
		return result;
	}
	
	public Task getTodo() {
		return todo;
	}
}
