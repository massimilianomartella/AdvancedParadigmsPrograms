package com.IoT.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.IoT.TemperatureHumidityMonitoring.MonitorFlag;

public class ThreadPooledServer implements Runnable {

	protected int serverPort;
	protected ServerSocket serverSocket = null;
	protected boolean isStopped = false;
	protected Thread runningThread = null;
	protected final int NTHREADS = Runtime.getRuntime().availableProcessors() + 1;
	protected final ExecutorService exec = Executors
			.newFixedThreadPool(NTHREADS);

	public ThreadPooledServer(int port) {
		this.serverPort = port;
	}

	public void run() {
		synchronized (this) {
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		while (!isStopped()) {
			Socket clientSocket = null;
			try {
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {
				if (isStopped()) {
					System.out.println("Server Stopped.");
					break;
				}
				throw new RuntimeException("Error accepting client connection",
						e);
			}
			this.exec.execute(new WorkerRunnable(clientSocket,
					"Thread Pooled Server"));
		}
		this.exec.shutdown();
		System.out.println("Server Stopped.");
	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	public synchronized void stop() {
		this.isStopped = true;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
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

		ThreadPooledServer server = new ThreadPooledServer(1111);

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