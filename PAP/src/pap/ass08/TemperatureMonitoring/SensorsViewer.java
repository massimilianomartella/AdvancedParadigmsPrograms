package pap.ass08.TemperatureMonitoring;

public class SensorsViewer {

	private static int refreshSensors;
	public static void main(String[] args) {

		refreshSensors = 100; //ms
		TempFlag monitorTemp = new TempFlag();
		// View
		SensorsView view = new SensorsView();
		// Controller
		Controller controller = new Controller(view, refreshSensors, monitorTemp);
 
		view.addListener(controller);
		view.setVisible(true);
	}

}
