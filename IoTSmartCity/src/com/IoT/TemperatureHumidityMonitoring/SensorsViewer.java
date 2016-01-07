package com.IoT.TemperatureHumidityMonitoring;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SensorsViewer {
	static final int MY_MINIMUM = 0;
	static final int MY_MAXIMUM = 100;
	
	static private void loading() {
		final SwingProgressBar it = new SwingProgressBar();
		JFrame frame = new JFrame("Progress Bar Example");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(it);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		// run a loop to demonstrate raising
		for (int i = MY_MINIMUM; i <= MY_MAXIMUM; i++) {
			final int percent = i;
			try {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						it.updateBar(percent);
					}
				});
				Thread.sleep(30);
			} catch (InterruptedException e) {
				;
			}
		}
		frame.setVisible(false);
	}
	
	public static void main(String[] args) {
		
		MonitorFlag monitorTemp = new MonitorFlag();
		MonitorFlag monitorHumidity = new MonitorFlag();
		// View
		SensorsView view = new SensorsView();
		// Controller
		Controller controller = new Controller(view, monitorTemp, monitorHumidity);
		
		view.addListener(controller);
		
		// loading();
		view.setVisible(true);
	}
	
}
