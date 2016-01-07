package com.IoT.TemperatureHumidityMonitoring;

import com.IoT.Arduino.sensors.Acknowledge;
import com.IoT.Arduino.sensors.DataDht11;
import com.IoT.Arduino.sensors.Sensors;
import com.IoT.Server.ThreadPooledServer;

public class Controller implements InputListener {
	
	private SensorsView view;
	private StopFlag flag;
	private MonitorFlag monitorTemperature, monitorHumidity;
	private Sensors tempSensor;
	private DataDht11 dht11;
	private ThreadPooledServer server;
	private Acknowledge ack;
	
	public Controller(SensorsView view, MonitorFlag monitorTemperature, MonitorFlag monitorHumidity) {
		this.view = view;
		this.monitorTemperature = monitorTemperature;
		this.monitorHumidity = monitorHumidity;
		
		ack = new Acknowledge();
		dht11 = new DataDht11();
		this.tempSensor = new Sensors(dht11, ack);
		tempSensor.initialize();// start monitoring
	}
	
	@Override
	public void started(double temperatureRange, int frequencyTemperature, double humidityRange, int frequencyHumidity) {
		// Model
		flag = new StopFlag();
		server = new ThreadPooledServer(1111, tempSensor, flag, dht11, ack);
		server.setPriority(Thread.MAX_PRIORITY);
		server.start();
		ModelReactiveControlSensor model = new ModelReactiveControlSensor(temperatureRange, frequencyTemperature,
				humidityRange, frequencyHumidity, flag, view, monitorTemperature, monitorHumidity, dht11);
		model.setPriority(Thread.MIN_PRIORITY);
		model.start();
	}
	
	@Override
	public void stopped() {
		flag.setDone();
	}
}