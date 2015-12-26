package pap.ass08.TemperatureMonitoring;

import rx.Subscriber;

/**
 * Thread che fa partire la rilevazione al sensore desiderato
 * @author Martella Massimiliano
 *
 */
public class ObservableTempStream extends Thread {

	private TempSensor sensor;
	private int intervallo;
	private Subscriber<? super Double> subscriber;
	private StopFlag flag;

	public ObservableTempStream(Subscriber<? super Double> subscriber,
			TempSensor sensor, int intervallo, StopFlag flag) {
		this.sensor = sensor;
		this.intervallo = intervallo;
		this.subscriber = subscriber;
		this.flag = flag;
	}

	@Override
	public void run() {
		// In realtà una volta "acceso" fisicamente un sensore, esso comincerà
		// ad inviare sempre messaggi. Per questo motivo il ciclo è un
		// while(true)
		while (true) {
			try {
				subscriber.onNext(sensor.getCurrentValue());
				Thread.sleep(intervallo);
			} catch (Exception e) {
				subscriber.onError(e);
			} finally {
				if (flag.isDone())
					break;
			}
		}
		// Aggiunto per segnalare il completamento
		subscriber.onCompleted();
	}
}
