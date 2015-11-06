package pap.ass08.TemperatureMonitoring;


public class Controller implements InputListener {

	private SensorsView view;
	private StopFlag flag;
	private int refreshSensors;
	private TempFlag monitorTemp;
	
	public Controller(SensorsView view, int refreshSensors, TempFlag monitorTemp) {
		this.view = view;
		this.refreshSensors = refreshSensors;
		this.monitorTemp = monitorTemp;
	}
	
	@Override
	public void started(int milliSeconds, double threshold) {
		//Model
		flag = new StopFlag();
		new ModelReactiveControlSensor(refreshSensors, milliSeconds, threshold, flag, view, monitorTemp).start();
	}

	@Override
	public void stopped() {
		flag.setDone();
	}
}