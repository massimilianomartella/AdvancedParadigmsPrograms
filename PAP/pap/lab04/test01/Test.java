package pap.lab04.test01;

/*
 * Non terminating behaviours.
 * 
 */
public class Test {

	/*
	 * launch program from terminal:
	 * java -cp ./Documents/workspace/PAP/bin/ pap.lab04.test01.Test
	 * 
	 * */
	public static void main(String[] args) {
		new MyWorkerB("worker-A").start();
		new MyWorkerA("worker-B").start();		
	}

}
