package pap.lab08;

import java.util.Random;

import rx.Observable;
import rx.Subscriber;

public class FirstObservableManipulation {

	public static void main(String[] args) {

		// simple composition
		Observable<Integer> stream = Observable.create((Subscriber<? super Integer> subscriber) -> {
			new AsyncGenerator(subscriber,0,Integer.MAX_VALUE).start();
		});	
		
		Observable<String> newStream =
			stream
			.skip(10) //crea un nuovo stream saltandone 10
			.take(5) //prendiamo i successivi 5
			.map(v -> "value: "+v);	//dato uno stream, crea un nuovo stream mappando il valore v (di tipo intero)

		newStream.subscribe((String s) -> {
			System.out.println(s);
		});
	}

}
