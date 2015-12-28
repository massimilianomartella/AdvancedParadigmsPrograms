package com.IoT.TemperatureHumidityMonitoring;

/**
 * TempFlag è la classe adibita a ricavare, a seconda del parametro passatogli
 * v, il valore massimo e minimo.
 * 
 * @author Martella Massimiliano
 *
 */
public class MonitorFlag {

	private double minValue, maxValue;
	private double value;

	public MonitorFlag() {
		minValue = 10000000;
		maxValue = 0;
	}

	public void setMaxMinValue(double v) {
		if (v > maxValue)
			maxValue = v;
		if (v < minValue)
			minValue = v;
	}

	public double getMinValue() {
		return minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public synchronized void setValue(Double v) {
		this.value = v;
	}
	
	public double getValue() {
		return value;
	}

}