package pap.lab08;

import java.util.stream.IntStream;
import rx.Observable; 
import rx.Subscriber;

public class ObservableCreationSynch {

	public static void main(String[] args){
		
		/**
		 * Subscriber è la funzione da passare all'Observable
		 */
		Observable<Integer> stream = Observable.create((Subscriber<? super Integer> observer) -> {
			IntStream.range(0, 10).forEach(value -> {
				observer.onNext(value);
			});
		});	
		
		/**
		 * Osservatori
		 */
		stream.subscribe((Integer v) -> {
			System.out.println("value: "+v);
		});
		
	}
}
