package com.IoT.TemperatureHumidityMonitoring;

/**
 * Monitor adibito allo start e stop del programma
 * 
 * @author Martella Massimiliano
 */
public class StopFlag {
	
	private boolean done = false;
	
	public StopFlag() {
		done = false;
	}
	
	public synchronized void setDone() {
		done = true;
	}
	
	public synchronized boolean isDone() {
		return done;
	}
}