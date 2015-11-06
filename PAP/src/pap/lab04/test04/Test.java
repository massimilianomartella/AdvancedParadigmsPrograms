package pap.lab04.test04;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Test {

	public static void main(String[] args) {
		int howMany = Runtime.getRuntime().availableProcessors();
		
		long t0 = System.currentTimeMillis();
		
		List<Thread> tlist = new ArrayList<Thread>();
		IntStream.rangeClosed(0, howMany-1).forEach(i -> {
			Thread t = new Thread(() -> {
				System.out.println("Hello fron core "+i);
				double waste = 0;
				for (int j = 0; j < 100000; j++){
					for (int k = 0; k < 1000; k++){
						waste = waste + k*Math.sin(j);
					}
				}
			});
			t.start();
			tlist.add(t);
		});
		
		tlist.stream().forEach(t -> {
			try {
				t.join();
			} catch (Exception ex){
			}
		});
		
		long t1 = System.currentTimeMillis();
		System.out.println("Time elapsed: "+ (t1-t0));
	}

}
