package com.IoT.TemperatureHumidityMonitoring;

import com.IoT.Arduino.sensors.DataDht11;
import com.IoT.Arduino.sensors.Sensors;

public class Controller implements InputListener {

	private SensorsView view;
	private StopFlag flag;
	private MonitorFlag monitorTemperature, monitorHumidity;
	private Sensors tempSensor;
	private DataDht11 dht11;

	public Controller(SensorsView view, MonitorFlag monitorTemperature,
			MonitorFlag monitorHumidity) {
		this.view = view;
		this.monitorTemperature = monitorTemperature;
		this.monitorHumidity = monitorHumidity;

		dht11 = new DataDht11();

		this.tempSensor = new Sensors(dht11);
		tempSensor.initialize();// start monitoring

	}

	@Override
	public void started(double temperatureRange, int frequencyTemperature,
			double humidityRange, int frequencyHumidity) {
		// Model
		flag = new StopFlag();
		new ModelReactiveControlSensor(temperatureRange, frequencyTemperature,
				humidityRange, frequencyHumidity, flag, view,
				monitorTemperature, monitorHumidity, dht11).start();

	}

	@Override
	public void stopped() {
		flag.setDone();
	}

	@Override
	public void clientThingSpeak() {
		// TODO
	}
}