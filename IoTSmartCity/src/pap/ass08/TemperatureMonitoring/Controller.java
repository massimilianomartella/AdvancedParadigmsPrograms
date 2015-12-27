package pap.ass08.TemperatureMonitoring;

import com.Arduino.sensors.DataDht11;
import com.Arduino.sensors.Sensors;


public class Controller implements InputListener {

	private SensorsView view;
	private StopFlag flag;
	private int refreshSensors;
	private TempFlag monitorTemp;
	private Sensors tempSensor;
	private DataDht11 dht11;
	
	public Controller(SensorsView view, int refreshSensors, TempFlag monitorTemp) {
		this.view = view;
		this.refreshSensors = refreshSensors;
		this.monitorTemp = monitorTemp;
		
		dht11 = new DataDht11();
		
		
		this.tempSensor = new Sensors(dht11);
		tempSensor.initialize();//faccio partire il monitoraggio
		
	}
	
	@Override
	public void started(double avarage, double threshold) {
		//Model
		flag = new StopFlag();				
				
		new ModelReactiveControlSensor(flag, view, monitorTemp, avarage, threshold, dht11).start();
	}

	@Override
	public void stopped() {
		flag.setDone();
	}
}