package pap.ass08.TemperatureMonitoring;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.observables.ConnectableObservable;

public class ModelReactiveControlSensor extends Thread {

	private double threshold; // valore soglia
	private int refreshSensors, milliseconds;
	private TempSensor sensorAlfa, sensorBravo, sensorCharlie;
	private StopFlag flag;
	private SensorsView view;
	private static double averageTempSensors;
	private TempFlag monitorTemp;

	public ModelReactiveControlSensor(int refreshSensors, int milliSeconds,
			double threshold, StopFlag flag, SensorsView view,
			TempFlag monitorTemp) {
		this.monitorTemp = monitorTemp;
		this.flag = flag;
		this.view = view;
		this.refreshSensors = refreshSensors;
		this.threshold = threshold;
		this.milliseconds = milliSeconds;

		int minAlfa = 50, maxAlfa = 75;
		int minBravo = 50, maxBravo = 75;
		int minCharlie = 50, maxCharlie = 75;

		double percentErrorAlfa = 0.5;
		double percentErrorBravo = 0.07;
		double percentErrorCharlie = 0.02;

		sensorAlfa = new TempSensor(minAlfa, maxAlfa, percentErrorAlfa);
		sensorBravo = new TempSensor(minBravo, maxBravo, percentErrorBravo);
		sensorCharlie = new TempSensor(minCharlie, maxCharlie,
				percentErrorCharlie);

		double averageTempAlfa = (minAlfa + maxAlfa) / 2;
		double averageTempBravo = (minBravo + maxBravo) / 2;
		double averageTempCharlie = (minCharlie + maxCharlie) / 2;

		// calcolo la media dei tre sensori statica
		averageTempSensors = (averageTempAlfa + averageTempBravo + averageTempCharlie) / 3;
	}

	@Override
	public void run() {

		// Creo tre Observable per ogni sensore di test. I nomi dei sensori sono
		// Alfa, Bravo e Charlie.
		Observable<Double> observableAlfa = Observable.create((
				Subscriber<? super Double> subscriber) -> {
			new ObservableTempStream(subscriber, sensorAlfa, refreshSensors,
					flag).start();
		});

		Observable<Double> observableBravo = Observable.create((
				Subscriber<? super Double> subscriber) -> {
			new ObservableTempStream(subscriber, sensorBravo, refreshSensors,
					flag).start();
		});

		Observable<Double> observableCharlie = Observable.create((
				Subscriber<? super Double> subscriber) -> {
			new ObservableTempStream(subscriber, sensorCharlie, refreshSensors,
					flag).start();
		});

		// Mi "allaccio" ai sensori richiamando la funzione getSpikeValue che
		// individua gli spike per ogni sensore.
		ConnectableObservable<Double> connectObsAlfa = getSpikeValue(
				observableAlfa, "Alfa").publish();
		ConnectableObservable<Double> connectObsBravo = getSpikeValue(
				observableBravo, "Bravo").publish();
		ConnectableObservable<Double> connectObsCharlie = getSpikeValue(
				observableCharlie, "Charlie").publish();

		/*
		 * Il Connectable Observable assomiglia ad un normale Observable, salvo
		 * che non inizia emettendo segnali quando è sottoscritto, ma solo
		 * quando il metodo connect() viene chiamato. In questo modo è possibile
		 * attendere che tutti i sottoscrittori siano sottoscritti
		 * all'osservabile prima che Observable inizi ad emettere segnali
		 * http://
		 * reactivex.io/RxJava/javadoc/rx/observables/ConnectableObservable.html
		 */

		// Con la funzione zip faccio la media dei valori ottenuti dai tre
		// sensori
		ConnectableObservable<Double> averageTempStream = Observable.zip(
				observableAlfa, observableBravo, observableCharlie,
				(Double a, Double b, Double c) -> ((a + b + c) / 3)).publish();

		// Eseguo la sottoscrizione all'osservatore media.
		averageTempStream.subscribe((Double v) -> {
			// Se la media è fuori soglia
				if ((v) < (averageTempSensors - threshold)
						| (v) > (averageTempSensors + threshold)) {
					// view.setUpdatedJPaneArea("Value out of range > " + v +
					// "\n",
					// Color.red);
				} else { // se la media non è fuori soglia
					// salvo il valore massimo e minimo riportando sulla view il
					// risultato
					monitorTemp.setMaxMinValue(v);
					view.setUpdatedMinMaxValue(monitorTemp.getMinValue(),
							monitorTemp.getMaxValue());
					// visualizza sulla view il risultato della media dei
					// sensori
					view.setUpdatedJPaneArea("> " + v + "\n", Color.black);
					// System.out.println("Media: " + v + "\n\n");
				}
			}, (Throwable t) -> {
				System.out.println("error  " + t);
			}, () -> {
				// Una volta completato (tutte le volte che spingo il bottone
				// stop sulla view) viene visualizzato il messaggio "Completed"
				view.setUpdatedJPaneArea("Completed\n", Color.gray);
			});

		// x = segnale avg ok
		// - = millisecondo
		// a = alert
		//averageTempStream è uno stream che controlla se il valore media è fuori sogli per più di un certo tempo in millisencondi
		averageTempStream
				.map((Double v) -> (v > averageTempSensors - threshold)
						&& (v < averageTempSensors + threshold))
				.distinctUntilChanged()
				// ignora i segnali continui uguali
				// (es. "x-x-x-x-a-a-x" diventa
				// "x- - - -a- -x")
				// http://reactivex.io/documentation/operators/distinct.html
				.debounce(milliseconds, TimeUnit.MILLISECONDS)
				// L'operatore Debounce filtra i segnali emessi dalla sorgente
				// ed emette un solo segnale da un osservabile se un particolare
				// periodo di tempo (millisenconds) è passato senza che
				// precedentemente è stato emesso un altro segnale.
				// http://reactivex.io/documentation/operators/debounce.html
				.subscribe((Boolean ok) -> {
					if (ok) {
						// System.out.println("Value avg is ok!");
						// view.setUpdatedJPaneArea("Value avg is ok!\n",
						// Color.green);
					} else {
						// System.out.println("Value out of threshold!");
						view.setUpdatedJPaneArea("Value out of threshold!\n",
								Color.red);
					}
				}, (Throwable t) -> {
					System.out.println("error  " + t);
				}, () -> {
					System.out.println("Completed");
				});

		// Mi connetto a tutti gli Observables in modo che ipoteticamente tutti
		// cominciano a lanciare i messaggio nello stesso momento
		connectObsAlfa.connect();
		connectObsBravo.connect();
		connectObsCharlie.connect();

		averageTempStream.connect();

	}

	/**
	 * Funzione che restituisce un Observable<double> stream di dati che filtra
	 * i valori del singolo sonsore intercettando gli spike da esso emessi e
	 * segnalando anche qualche sensore li ha emessi.
	 * 
	 * @param stream
	 * @param sensor
	 * @return
	 */
	Observable<Double> getSpikeValue(Observable<Double> stream, String sensor) {
		return stream.filter(
				(Double v) -> {
					if (v > (averageTempSensors - threshold)
							&& v < (averageTempSensors + threshold)) {
						// view.setUpdatedJPaneArea(sensor + " > " + v+"\n",
						// Color.black);
						// System.out.println(sensor + " > " + v);
						return true;
					} else {
						// view.setUpdatedJPaneArea(sensor + "> " + v +
						// "\n",Color.blue);
						System.out.println("err" + sensor + " > " + v);
						return false;
					}
				}).map((Double x) -> x);
	}
}
