package pap.ass05.sol.game;

public class Flag {

	private boolean isHigh;
	
	public Flag(){
		isHigh = false;
	}
	
	public synchronized void setHigh(){
		isHigh = true;
	}
	
	public synchronized void setLow(){
		isHigh = false;
	}
	
	public synchronized boolean capture(){
		return isHigh;
	}
}
