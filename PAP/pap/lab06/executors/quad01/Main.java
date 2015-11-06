package pap.lab06.executors.quad01;

public class Main {

	public static void main(String args[]) throws Exception {	

		IFunction mf = new MyFunction();
		double a = 0;
		double b = 3;		
		int nTasks = 10;
		int poolSize = Runtime.getRuntime().availableProcessors()+1;
		
		QuadratureService service = new QuadratureService(nTasks, poolSize);
		double result = service.compute(mf, a, b);
		System.out.println("Result: "+result);
		
		System.exit(0);
	}
	
}
