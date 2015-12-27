package com.IoT.Arduino.sensors;

public class DataDht11 {
	private double humidity;
	private double temperature;

	public DataDht11() {
		this.humidity = 0.0;
		this.temperature = 0.0;
	}

	synchronized void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	synchronized void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getHumidity() {
		return this.humidity;
	}

	public double getTemperature() {
		return this.temperature;
	}
}
