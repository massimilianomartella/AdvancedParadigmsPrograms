package pap.ass08.TemperatureMonitoring;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import com.Arduino.sensors.DataDht11;
import com.Arduino.sensors.Sensors;

import rx.Observable;
import rx.Subscriber;
import rx.observables.ConnectableObservable;

public class ModelReactiveControlSensor extends Thread {

	//private double threshold; // valore soglia
	//private int refreshSensors, milliseconds;
	//private TempSensor sensorAlfa, sensorBravo, sensorCharlie;
	private DataDht11 dht11;
	private StopFlag flag;
	private SensorsView view;
	private TempFlag monitorTemp;
	private static double averageTempSensors;
	private double threshold;

	public ModelReactiveControlSensor(
			StopFlag flag, SensorsView view,
			TempFlag monitorTemp, double avarage, double threshold, DataDht11 dht11) {
		this.monitorTemp = monitorTemp;
		this.flag = flag;
		this.view = view;
		this.dht11 = dht11;
		
		averageTempSensors = avarage; //in media ci sono 20 gradi
		this.threshold = threshold;
	}

	@Override
	public void run() {

		// Creo un Observable.
		Observable<Double> observableAlfa = Observable.create((
				Subscriber<? super Double> subscriber) -> {
			new ObservableHumidityStream(subscriber, dht11,
					flag).start();
		});

		

		// Mi "allaccio" ai sensori richiamando la funzione getSpikeValue che
		// individua gli spike per ogni sensore.
		ConnectableObservable<Double> connectObsAlfa = getSpikeValue(
				observableAlfa, "Alfa").publish();
		

		// Mi connetto a tutti gli Observables in modo che ipoteticamente tutti
		// cominciano a lanciare i messaggio nello stesso momento
		connectObsAlfa.connect();

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
