package com.IoT.TemperatureHumidityMonitoring;

public interface InputListener {
	
	void started(double temperatureRange, int frequencyTemperature, double humidityRange, int frequencyHumidity);
	
	void stopped();
	
}
