package pap.lab08;

import java.util.Random;
import rx.Observable;
import rx.Subscriber;

public class ObservableCreationAsync {

	public static void main(String[] args) {

		// creating an asynchronous observable

		/*
		 * Subscriber<? super Integer> subscriber --> colui a cui dobbiamo
		 * notificare Non termina mai
		 */

		Observable<Integer> stream = Observable.create((
				Subscriber<? super Integer> subscriber) -> {
			new Thread(() -> {
				Random gen = new Random(System.currentTimeMillis());
				while (true) {
					try {
						subscriber.onNext(gen.nextInt());
						// da uno a due secondi
					Thread.sleep(1000 + gen.nextInt() % 1000);
				} catch (Exception ex) {
				}
			}
		}	).start();
		});

		stream.subscribe((Integer v) -> {
			System.out.println("value: " + v);
		});

		// doing some job

		while (true) {
			System.out.print(".");
			try {
				Thread.sleep(100);
			} catch (Exception ex) {
			}
		}
	}
}
