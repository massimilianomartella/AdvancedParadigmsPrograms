package pap.ass06.bigGrid;

public class Flag {
	
	private boolean isSet;
	
	public Flag(){
		isSet = false;
	}
	
	public synchronized void set(){
		isSet = true;
	}
	
	public synchronized boolean isSet(){
		return isSet;
	}
	
	public synchronized void reset(){
		isSet = false;
	}

}

