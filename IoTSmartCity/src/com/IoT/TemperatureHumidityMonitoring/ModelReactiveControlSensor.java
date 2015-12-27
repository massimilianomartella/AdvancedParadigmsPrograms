package com.IoT.TemperatureHumidityMonitoring;

import java.awt.Color;

import rx.Observable;
import rx.Subscriber;
import rx.observables.ConnectableObservable;

import com.IoT.Arduino.sensors.DataDht11;

public class ModelReactiveControlSensor extends Thread {
	private DataDht11 dht11;
	private StopFlag flag;
	private SensorsView view;
	private MonitorFlag monitorTemperature, monitorHumidity;
	private double thresholdTemperatureError, thresholdHumidityError;
	private int frequencyTemperature, frequencyHumidity;
	private double temperatureRange, humidityRange;

	public ModelReactiveControlSensor(double temperatureRange,
			int frequencyTemperature, double humidityRange,
			int frequencyHumidity, StopFlag flag, SensorsView view,
			MonitorFlag monitorTemperature, MonitorFlag monitorHumidity, DataDht11 dht11) {
		this.monitorTemperature = monitorTemperature;
		this.monitorHumidity = monitorHumidity;
		this.flag = flag;
		this.view = view;
		this.dht11 = dht11;

		thresholdTemperatureError = 30; // if the sensor is out of range of 30
										// degrees
		thresholdHumidityError = 20; // if the sensor is out of range of 20

		this.frequencyHumidity = frequencyHumidity;
		this.frequencyTemperature = frequencyTemperature;
		this.temperatureRange = temperatureRange;
		this.humidityRange = humidityRange;
	}

	@Override
	public void run() {
		// Create an observable
		Observable<Double> observableHumidity = Observable.create((
				Subscriber<? super Double> subscriber) -> {
			new ObservableHumidityStream(subscriber, dht11, flag,
					frequencyHumidity).start();
		});

		Observable<Double> observableTemperature = Observable.create((
				Subscriber<? super Double> subscriber) -> {
			new ObservableTemperatureStream(subscriber, dht11, flag,
					frequencyTemperature).start();
		});

		// Sensor connection by calling the function that identifies
		// getSpikeValue spikes for each sensor.
		ConnectableObservable<Double> connectObsHumidity = getSpikeValue(
				observableHumidity, "Humidity", humidityRange, thresholdHumidityError).publish();
		ConnectableObservable<Double> connectObsTemperature = getSpikeValue(
				observableTemperature, "Temperature", temperatureRange, thresholdTemperatureError).publish();

		connectObsTemperature
				.subscribe((Double v) -> {
					// If the value is out of range
						if ((v) < (temperatureRange - thresholdTemperatureError)
								| (v) > (temperatureRange + thresholdTemperatureError)) {
							// view.setUpdatedJPaneArea("Value out of range > "
							// + v +
							// "\n",
							// Color.red);
						} else { // if the value is fine (between the range)
							// Visualise the result through the function
							// setMaxMinvalue and setUpdateMinMaxValue
							monitorTemperature.setMaxMinValue(v);
							view.setUpdatedMinMaxValueTemperature(
									monitorTemperature.getMinValue(),
									monitorTemperature.getMaxValue());
							view.setUpdatedJPaneAreaTemperature("Temperature > " + v
									+ "\n", Color.black);
							// System.out.println("Media: " + v + "\n\n");
						}
					}, (Throwable t) -> {
						System.out.println("error  " + t);
					}, () -> {
						// When finish, write "Completed" on the JPanelArea
						view.setUpdatedJPaneAreaTemperature("Completed\n", Color.gray);
					});
		
		connectObsHumidity
		.subscribe((Double v) -> {
			// If the value is out of range
				if ((v) < (humidityRange - thresholdHumidityError)
						| (v) > (humidityRange + thresholdHumidityError)) {
					// view.setUpdatedJPaneArea("Value out of range > "
					// + v +
					// "\n",
					// Color.red);
				} else { // if the value is fine (between the range)
					// Show the result through the function
					// setMaxMinvalue and setUpdateMinMaxValue
					monitorHumidity.setMaxMinValue(v);
					view.setUpdatedMinMaxValueHumidity(
							monitorHumidity.getMinValue(),
							monitorHumidity.getMaxValue());
					view.setUpdatedJPaneAreaHumidity("Humidity > " + v
							+ "\n", Color.black);
					// System.out.println("Media: " + v + "\n\n");
				}
			}, (Throwable t) -> {
				System.out.println("error  " + t);
			}, () -> {
				// When finish, write "Completed" on the JPanelArea
				view.setUpdatedJPaneAreaHumidity("Completed\n", Color.gray);
			});

		// Connect to the Observable
		connectObsHumidity.connect();
		connectObsTemperature.connect();

	}

	Observable<Double> getSpikeValue(Observable<Double> stream,
			String sensor, double temperatureRange, double thresholdTemperatureError) {
		return stream
				.filter((Double v) -> {
					if (v > (temperatureRange - thresholdTemperatureError)
							&& v < (temperatureRange + thresholdTemperatureError)) {
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
