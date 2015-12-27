package com.IoT.TemperatureHumidityMonitoring;

import com.IoT.Arduino.sensors.DataDht11;
import com.IoT.Arduino.sensors.Sensors;

import rx.Subscriber;

/**
 * Thread che fa partire la rilevazione al sensore desiderato
 * @author Martella Massimiliano
 *
 */
public class ObservableTemperatureStream extends Thread {

	private DataDht11 sensor;
	private Subscriber<? super Double> subscriber;
	private StopFlag flag;
	private int frequencyTemperature;

	public ObservableTemperatureStream(Subscriber<? super Double> subscriber,
			DataDht11 sensor, StopFlag flag, int frequencyTemperature) {
		this.sensor = sensor;
		this.subscriber = subscriber;
		this.flag = flag;
		this.frequencyTemperature = frequencyTemperature;
	}

	@Override
	public void run() {
		// In realtà una volta "acceso" fisicamente un sensore, esso comincerà
		// ad inviare sempre messaggi. Per questo motivo il ciclo è un
		// while(true)
		while (true) {
			try {
				subscriber.onNext(sensor.getTemperature());
				Thread.sleep(frequencyTemperature);
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
