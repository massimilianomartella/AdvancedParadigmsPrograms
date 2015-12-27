package com.IoT.Arduino.sensors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Sensors implements SerialPortEventListener {
	DataDht11 sensor = new DataDht11();
	SerialPort serialPort;

	public Sensors(DataDht11 data) {
		sensor = data;
	}

	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { "/dev/tty.usbmodem1411", // Mac
																			// OS
																			// X
			"/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM3", // Windows
	};
	/**
	 * A BufferedReader which will be fed by a InputStreamReader converting the
	 * bytes into characters making the displayed results codepage independent
	 */
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	public void initialize() {
		// the next line is for Raspberry Pi and
		// gets us into the while loop and was suggested here was suggested
		// http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
		System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/tty.usbmodem1411");
		
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(
					serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port. This will prevent
	 * port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine = input.readLine();
				System.out.println("InputLine: "+inputLine);
				
				getCurrentValue(inputLine);
				
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other
		// ones.
	}

	public void getCurrentValue(String inputLine) {
		JSONParser parser = new JSONParser();
		ContainerFactory containerFactory = new ContainerFactory(){
		    public List creatArrayContainer() {
		      return new LinkedList();
		    }

		    public Map createObjectContainer() {
		      return new LinkedHashMap();
		    }
		                        
		  };
		  try{
			    Map json = (Map)parser.parse(inputLine, containerFactory);
			    Iterator iter = json.entrySet().iterator();
			    //System.out.println("==iterate result==");
			    while(iter.hasNext()){
			      Map.Entry entry = (Map.Entry)iter.next();
//			      System.out.println(entry.getKey() + "=>" + entry.getValue());
			      if(entry.getKey().toString().equals("value") && entry.getValue().toString().equals("OK")) {
			    	  Map.Entry nextEntry = (Map.Entry)iter.next();
			    	  //System.out.println(nextEntry.getValue());
			    	  String[] c = nextEntry.getValue().toString().replaceAll(" ", "").split(",");
			    	  String humidity = c[0].replaceAll("\\D+","");	//regular expression for extract the number
			    	  String temperature = c[1].replaceAll("\\D+","");	//regular expression for extract the number
			    	  
			    	  sensor.setHumidity(Double.parseDouble(humidity));
			    	  sensor.setTemperature(Double.parseDouble(temperature));			    	  
			      }
			    }
			                        
//			    System.out.println("==toJSONString()==");
//			    System.out.println(JSONValue.toJSONString(json));
			  }
			  catch(ParseException pe){
			    System.out.println(pe);
			  }
	}

	public static void main(String[] args) throws Exception {
		DataDht11 dht11 = new DataDht11();
		Sensors main = new Sensors(dht11);
		main.initialize();

//		Thread t = new Thread() {
//			public void run() {
//				// the following line will keep this app alive for 1000 seconds,
//				// waiting for events to occur and responding to them (printing
//				// incoming messages to console).
//				try {
//					Thread.sleep(10000);
//				} catch (InterruptedException ie) {
//				}
//			}
//		};
//		t.start();
		System.out.println("Started");
		
		
		
	}
}