package pap.ass05.CooperativeTeam;

public class MyMonitor {

	private boolean on;

	public MyMonitor() {
		this.setOn(true);
	}

	public synchronized boolean isOn() {
		return on;
	}

	public synchronized void setOn(boolean on) {
		this.on = on;
		if (on)
			System.out.println("***Start worker!***");
		else
			System.out.println("***Stop worker!***");
	}
}
