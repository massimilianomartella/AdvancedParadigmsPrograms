package pap.lab08;

import java.util.Random;
import rx.Observable; 
import rx.Subscriber;

public class ObservableCreationAsyncWithCompletion {

	public static void main(String[] args){
		
		StopFlag flag = new StopFlag();
		
		// creating an asynchronous observable
	
		Observable<Integer> stream = Observable.create((Subscriber<? super Integer> subscriber) -> {
			new Thread(() -> {
				Random gen = new Random(System.currentTimeMillis());
				
				long t0 = System.currentTimeMillis();
				while (true) {
					try {
						subscriber.onNext(gen.nextInt());
						Thread.sleep(1000 + gen.nextInt() % 1000);
						/**
						 * Il generatore generara valori per 5 secondi, poi esce.
						 */
						if (System.currentTimeMillis() - t0 > 5000){
							break;
						}
					} catch (Exception ex){
					}
				};				
				// notify completion
				subscriber.onCompleted();
			}).start();
		});	
		
		stream.subscribe(
			(Integer v) -> {
				System.out.println("value: "+v);
			},
			(Throwable t) -> {
				
			},
			() -> {
				System.out.println("done.");
				flag.setDone();
			});
		
		// doing some job 
		
		while (!flag.isDone()) {
			System.out.print(".");
			try {
				Thread.sleep(100);
			} catch (Exception ex){}			
		}
		
	}
}
