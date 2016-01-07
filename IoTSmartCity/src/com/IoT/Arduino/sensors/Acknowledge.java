package com.IoT.Arduino.sensors;

public class Acknowledge {
	private boolean isOn;
	private double temperature;
	
	public Acknowledge() {
		this.isOn = false;
		this.temperature = 0.0;
	}
	
	public synchronized void setTemperature(double temperature) {
		this.temperature = temperature;
		System.out.println("set temperature => " + this.temperature);
		notifyAll();
	}
	
	public double getTemperature() {
		System.out.println("get temperature => " + this.temperature);
		return this.temperature;
	}
	
	public synchronized void setOn(boolean isOn) {
		this.isOn = isOn;
		System.out.println("set isOn => " + this.isOn);
		notifyAll();
	}
	
	public boolean getIsOn() {
		System.out.println("get isOn => " + this.isOn);
		return this.isOn;
	}
	
	public synchronized void writeData(Sensors serial, String stringValue) {
		try {
			serial.writeData(stringValue);
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
