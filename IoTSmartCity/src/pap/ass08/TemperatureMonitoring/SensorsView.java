package pap.ass08.TemperatureMonitoring;

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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class SensorsView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private ArrayList<InputListener> listeners;
	private JButton startButton;
	private JButton stopButton;
	private JTextField maxValue, minValue;
	private JTextField avarage, threshold;
	
	
	private JTextPane tPane;

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
		
		stopButton.setEnabled(false);
		
		avarage = new JTextField(5);
		avarage.setText("20");	//in media ci sono 20 gradi in casa
		avarage.setEditable(true);
		threshold = new JTextField(5);
		threshold.setText("15");
		threshold.setEditable(true);

		JPanel controlPanel = new JPanel();
		controlPanel.add(startButton);
		controlPanel.add(stopButton);
		controlPanel.add(new JLabel("Avarage"));
		controlPanel.add(avarage);
		controlPanel.add(new JLabel("Threshold"));
		controlPanel.add(threshold);

		EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));
		tPane = new JTextPane();                
        tPane.setBorder(eb);
        //tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        tPane.setMargin(new Insets(5, 5, 5, 5));
		JScrollPane scroll = new JScrollPane(tPane);
		scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(250, 250));
		scroll.setMaximumSize(new Dimension(250, 250));
		
		JPanel infoPanel = new JPanel();
		maxValue = new JTextField(20);
		maxValue.setText("Ready...");
		maxValue.setEditable(false);
		minValue = new JTextField(20);
		minValue.setText("Ready...");
		minValue.setEditable(false);
		infoPanel.add(new JLabel("Max Value AVG"));
		infoPanel.add(maxValue);
		infoPanel.add(new JLabel("Min Value AVG"));
		infoPanel.add(minValue);
		JPanel cp = new JPanel();
		LayoutManager layout = new BorderLayout();
		cp.setLayout(layout);
		cp.add(BorderLayout.NORTH, controlPanel);
		
		cp.add(BorderLayout.CENTER, scroll);
		cp.add(BorderLayout.SOUTH, infoPanel);
		setContentPane(cp);

		startButton.addActionListener(this);
		stopButton.addActionListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		pack();
	}

	public void addListener(InputListener l) {
		listeners.add(l);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("start")) {
			notifyStarted(Integer.parseInt(avarage.getText()), Double.parseDouble(threshold.getText()));
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			threshold.setEnabled(false);
			avarage.setEnabled(false);
		} else if (cmd.equals("stop")) {
			notifyStopped();
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
			threshold.setEnabled(true);
			avarage.setEnabled(true);
		}
	}

	private void notifyStarted(int milliSeconds, double threshold) {
		for (InputListener l : listeners) {
			l.started(milliSeconds, threshold);
		}
	}

	private void notifyStopped() {
		for (InputListener l : listeners) {
			l.stopped();
		}
	}
	
	public void setUpdatedJPaneArea(final String msg, Color color) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					//JTextPane è in grado di stampare a video messaggi con colorazioni diverse
					StyleContext sc = StyleContext.getDefaultStyleContext();
			        AttributeSet aset =  sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
			        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
			        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
			        tPane.setCharacterAttributes(aset, false);
			        tPane.replaceSelection(msg);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setUpdatedMinMaxValue(final Double valMin, final Double valMax) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					minValue.setText(Double.toString(valMin));
					maxValue.setText(Double.toString(valMax));
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
