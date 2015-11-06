package pap.ass05.sol.game;

public class WinnerFlag {

	private boolean isHigh;
	
	public WinnerFlag(){
		isHigh = false;
	}
	
	public synchronized void set(){
		isHigh = true;
	}
	
	public synchronized boolean isSet(){
		return isHigh;
	}
}
