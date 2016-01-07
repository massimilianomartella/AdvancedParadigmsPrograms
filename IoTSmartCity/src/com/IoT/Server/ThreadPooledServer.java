package com.IoT.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.IoT.Arduino.sensors.Acknowledge;
import com.IoT.Arduino.sensors.DataDht11;
import com.IoT.Arduino.sensors.Sensors;
import com.IoT.TemperatureHumidityMonitoring.StopFlag;

public class ThreadPooledServer extends Thread {
	
	protected int serverPort;
	private Sensors dht11;
	protected ServerSocket serverSocket = null;
	protected boolean isStopped = false;
	protected Thread runningThread = null;
	protected final int NTHREADS = Runtime.getRuntime().availableProcessors() + 1;
	protected final ExecutorService exec = Executors.newFixedThreadPool(NTHREADS);
	private StopFlag flag;
	private DataDht11 data;
	private Acknowledge ack;
	
	public ThreadPooledServer(int port, Sensors sensor, StopFlag flag, DataDht11 data, Acknowledge ack) {
		this.serverPort = port;
		this.dht11 = sensor;
		this.flag = flag;
		this.data = data;
		this.ack = ack;
	}
	
	public void run() {
		openServerSocket();
		while (!flag.isDone()) {
			Socket clientSocket = null;
			try {
				clientSocket = this.serverSocket.accept();
				System.out.println("Accept connection!");
			} catch (IOException e) {
				throw new RuntimeException("Error accepting client connection", e);
			}
			this.exec.execute(new WorkerRunnable(clientSocket, dht11, "Thread Pooled Server", data, ack));
		}
		// Initiates an orderly shutdown in which previously submitted tasks are
		// executed, but no new tasks will be accepted.
		this.exec.shutdown();
		try {
			serverSocket.close();
			System.out.println("Server Stopped.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
			System.out.println("Socket opened, port " + this.serverPort);
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port " + this.serverPort, e);
		}
	}
	
	public static void main(String[] args) {
		
		ThreadPooledServer server = new ThreadPooledServer(1111, new Sensors(new DataDht11(), new Acknowledge()),
				new StopFlag(), new DataDht11(), new Acknowledge());
		
		// ThreadPooledServer server = new ThreadPooledServer(1111, MonitorFlag
		// temperature, MonitorFlag humidity);
		new Thread(server).start();
		
		// try {
		// Thread.sleep(20 * 1000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// System.out.println("Stopping Server");
		// server.stop();
	}
}