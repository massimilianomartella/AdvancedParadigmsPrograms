package pap.lab08;

import java.util.Random;

import rx.Observable;
import rx.Subscriber;

public class FirstObservableCombination {

	public static void main(String[] args) {

		// simple composition
		Observable<Integer> s1 = Observable.create((Subscriber<? super Integer> subscriber) -> {
			new AsyncGenerator(subscriber,0,5000).start();
		});	

		Observable<Integer> s2 = Observable.create((Subscriber<? super Integer> subscriber) -> {
			new AsyncGenerator(subscriber,100,6000).start();
		});	
		
		Observable<String> newStream =
			Observable.merge(s1,s2)
				.map((Integer x) -> "combined value: "+x);
		
		Observable<Integer> newStream2 =
				Observable.merge(s1,s2)
					.reduce((a,b) -> a + b);

		newStream.subscribe((String s) -> {
			System.out.println(s);
		});

		newStream2.subscribe((Integer v) -> {
			System.out.println("Final sum: "+v);
		});


	}

}
