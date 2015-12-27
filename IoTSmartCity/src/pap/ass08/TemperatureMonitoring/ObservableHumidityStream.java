package pap.ass08.TemperatureMonitoring;

import com.Arduino.sensors.DataDht11;
import com.Arduino.sensors.Sensors;

import rx.Subscriber;

/**
 * Thread che fa partire la rilevazione al sensore desiderato
 * @author Martella Massimiliano
 *
 */
public class ObservableHumidityStream extends Thread {

	private DataDht11 sensor;
	private Subscriber<? super Double> subscriber;
	private StopFlag flag;

	public ObservableHumidityStream(Subscriber<? super Double> subscriber,
			DataDht11 sensor, StopFlag flag) {
		this.sensor = sensor;
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
				subscriber.onNext(sensor.getHumidity());
				//Thread.sleep(intervallo);
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
