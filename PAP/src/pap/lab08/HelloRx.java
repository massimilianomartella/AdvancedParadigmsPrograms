package pap.lab08;

import rx.Observable;
import rx.Subscriber;

/**
 * Sono stream Sincroni
 * 
 * @author aricci
 *
 */

public class HelloRx {

	public static void main(String[] args) {

		// just a single element
		/*
		 * Creo un oggetto osservabile che dice Ciao Mondo, sincrono subscribe è
		 * il metodo usato per registrare coloro che vogliono usare questi
		 * stream. Per ogni elemento osservato s faccio la print
		 */
		Observable.just("Hello, world!").subscribe(s -> System.out.println(s));

		// synch data source

		String[] names = { "a", "b", "c", "d" };

		// simple subscription

		Observable.from(names).subscribe((String s) -> {
			System.out.println("> " + s);
		});

		// full subscription: onNext(), onError(), onCompleted()

		System.out.println("Full subscription...");

		// gli stream devono completarsi sempre
		Observable.from(names).subscribe((String s) -> {
			System.out.println("> " + s);
		}, (Throwable t) -> {
			System.out.println("error  " + t);
		}, () -> {
			System.out.println("completed");
		});

//		Observable.from(names).subscribe(new Subscriber<String>() {
//			public void onNext(String s) {
//				System.out.println("> " + s);
//			}
//
//			public void onError(Throwable t) {
//				System.out.println("error  " + t);
//			}
//
//			public void onCompleted() {
//				System.out.println("completed");
//			}
//		});

	}
}
