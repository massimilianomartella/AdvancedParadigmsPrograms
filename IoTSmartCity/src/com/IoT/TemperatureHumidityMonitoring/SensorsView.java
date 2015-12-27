package com.IoT.TemperatureHumidityMonitoring;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class SensorsView extends JFrame implements ActionListener {

	static final int MY_MINIMUM = 0;

	static final int MY_MAXIMUM = 100;

	private static final long serialVersionUID = 1L;
	private ArrayList<InputListener> listeners;
	private JButton startButton;
	private JButton stopButton;
	private JButton thingSpeak;
	private JTextField maxValueTemperature, minValueTemperature,
			maxValueHumidity, minValueHumidity;
	private JTextField temperatureRange, humidityRange, frequencyTemperature,
			frequencyHumidity;

	private JTextPane temperaturePane, humidityPane;

	public SensorsView() {
		super("Pannello di Controllo");
		setSize(new Dimension(480, 320));

		// Centrare la finestra al centro dello schermo
		setLocation(
				(Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2);

		listeners = new ArrayList<InputListener>();
		startButton = new JButton("start");
		stopButton = new JButton("stop");
		thingSpeak = new JButton("ThingSpeak");

		stopButton.setEnabled(false);

		temperatureRange = new JTextField(5);
		temperatureRange.setText("20"); // in media ci sono 20 gradi in casa
		temperatureRange.setEditable(true);

		frequencyTemperature = new JTextField(5);
		frequencyTemperature.setText("2000"); // ogni 2 secondi
		frequencyTemperature.setEditable(true);

		humidityRange = new JTextField(5);
		humidityRange.setText("20"); // in media ci sono 20 gradi in casa
		humidityRange.setEditable(true);

		frequencyHumidity = new JTextField(5);
		frequencyHumidity.setText("2000");
		frequencyHumidity.setEditable(true);

		JPanel controlPanel = new JPanel();
		controlPanel.add(startButton);
		controlPanel.add(stopButton);
		controlPanel.add(thingSpeak);
		controlPanel.add(new JLabel("Temperature Range"));
		controlPanel.add(temperatureRange);
		controlPanel.add(new JLabel("Frequency Temperature"));
		controlPanel.add(frequencyTemperature);
		controlPanel.add(new JLabel("Humidity Range"));
		controlPanel.add(humidityRange);
		controlPanel.add(new JLabel("Frequency Humidity"));
		controlPanel.add(frequencyHumidity);

		EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));
		temperaturePane = new JTextPane();
		temperaturePane.setBorder(eb);
		// tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		temperaturePane.setMargin(new Insets(5, 5, 5, 5));
		JScrollPane scrollTemperature = new JScrollPane(temperaturePane);
		scrollTemperature
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollTemperature.setPreferredSize(new Dimension(500, 250));
		scrollTemperature.setMaximumSize(new Dimension(500, 500));

		humidityPane = new JTextPane();
		humidityPane.setBorder(eb);
		// tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		humidityPane.setMargin(new Insets(5, 5, 5, 5));
		JScrollPane scrollHumidity = new JScrollPane(humidityPane);
		scrollHumidity
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollHumidity.setPreferredSize(new Dimension(500, 250));
		scrollHumidity.setMaximumSize(new Dimension(500, 500));

		JScrollPane scrollPanelLeft = new JScrollPane(scrollTemperature);
		JScrollPane scrollPanelRight = new JScrollPane(scrollHumidity);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				scrollPanelLeft, scrollPanelRight);
		splitPane.setDividerLocation(0.5);
		splitPane.setEnabled(false);
		// splitPane.setOneTouchExpandable(true);

		JPanel infoPanel = new JPanel();
		maxValueTemperature = new JTextField(5);
		maxValueTemperature.setText("Ready...");
		maxValueTemperature.setEditable(false);
		minValueTemperature = new JTextField(5);
		minValueTemperature.setText("Ready...");
		minValueTemperature.setEditable(false);

		maxValueHumidity = new JTextField(5);
		maxValueHumidity.setText("Ready...");
		maxValueHumidity.setEditable(false);
		minValueHumidity = new JTextField(5);
		minValueHumidity.setText("Ready...");
		minValueHumidity.setEditable(false);

		infoPanel.add(new JLabel("Max Temperature"));
		infoPanel.add(maxValueTemperature);
		infoPanel.add(new JLabel("Min Temperature"));
		infoPanel.add(minValueTemperature);

		infoPanel.add(new JLabel("Max Humidity"));
		infoPanel.add(maxValueHumidity);
		infoPanel.add(new JLabel("Min Humidity"));
		infoPanel.add(minValueHumidity);

		JPanel cp = new JPanel();
		LayoutManager layout = new BorderLayout();
		cp.setLayout(layout);
		cp.add(BorderLayout.NORTH, controlPanel);

		cp.add(BorderLayout.CENTER, splitPane);
		cp.add(BorderLayout.SOUTH, infoPanel);
		setContentPane(cp);

		startButton.addActionListener(this);
		stopButton.addActionListener(this);
		thingSpeak.addActionListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		pack();
	}

	public void addListener(InputListener l) {
		listeners.add(l);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("start")) {

			notifyStarted(Double.parseDouble(temperatureRange.getText()),
					Integer.parseInt(frequencyTemperature.getText()),
					Double.parseDouble(humidityRange.getText()),
					Integer.parseInt(frequencyHumidity.getText()));
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			frequencyTemperature.setEnabled(false);
			temperatureRange.setEnabled(false);
			frequencyHumidity.setEnabled(false);
			humidityRange.setEnabled(false);
		} else if (cmd.equals("stop")) {
			notifyStopped();
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
			frequencyTemperature.setEnabled(true);
			temperatureRange.setEnabled(true);
			frequencyHumidity.setEnabled(true);
			humidityRange.setEnabled(true);
		} else if (cmd.equals("thinkSpeak")) {

		}
	}

	private void notifyStarted(double temperatureRange,
			int frequencyTemperature, double humidityRange,
			int frequencyHumidity) {
		for (InputListener l : listeners) {
			l.started(temperatureRange, frequencyTemperature, humidityRange,
					frequencyHumidity);
		}
	}

	private void notifyStopped() {
		for (InputListener l : listeners) {
			l.stopped();
		}
	}

	private void notifyClientThingSpeak() {
		for (InputListener l : listeners) {
			l.clientThingSpeak();
		}
	}

	public void setUpdatedJPaneAreaTemperature(final String msg, Color color) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					// JTextPane è in grado di stampare a video messaggi con
					// colorazioni diverse
					StyleContext sc = StyleContext.getDefaultStyleContext();
					AttributeSet aset = sc.addAttribute(
							SimpleAttributeSet.EMPTY,
							StyleConstants.Foreground, color);
					aset = sc.addAttribute(aset, StyleConstants.FontFamily,
							"Lucida Console");
					aset = sc.addAttribute(aset, StyleConstants.Alignment,
							StyleConstants.ALIGN_JUSTIFIED);
					temperaturePane.setCharacterAttributes(aset, false);
					temperaturePane.replaceSelection(msg);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setUpdatedJPaneAreaHumidity(final String msg, Color color) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					// JTextPane è in grado di stampare a video messaggi con
					// colorazioni diverse
					StyleContext sc = StyleContext.getDefaultStyleContext();
					AttributeSet aset = sc.addAttribute(
							SimpleAttributeSet.EMPTY,
							StyleConstants.Foreground, color);
					aset = sc.addAttribute(aset, StyleConstants.FontFamily,
							"Lucida Console");
					aset = sc.addAttribute(aset, StyleConstants.Alignment,
							StyleConstants.ALIGN_JUSTIFIED);
					humidityPane.setCharacterAttributes(aset, false);
					humidityPane.replaceSelection(msg);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setUpdatedMinMaxValueTemperature(final Double valMin,
			final Double valMax) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					minValueTemperature.setText(Double.toString(valMin));
					maxValueTemperature.setText(Double.toString(valMax));
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setUpdatedMinMaxValueHumidity(final Double valMin,
			final Double valMax) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					minValueHumidity.setText(Double.toString(valMin));
					maxValueHumidity.setText(Double.toString(valMax));
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
