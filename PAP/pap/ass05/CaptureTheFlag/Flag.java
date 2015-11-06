package pap.ass05.CaptureTheFlag;

public class Flag implements iFlag {

	boolean flag;
	
	/**
	 * Una volta che la bandierina viene creata, il suo stato è "abbassato"
	 */
	public Flag() {
		flag = false;
	}

	@Override
	public synchronized void setHigh() {
		flag = true;
	}

	@Override
	public synchronized void setLow() {
		flag = false;
	}

	@Override
	public synchronized boolean capture() {
		return flag;
	}

}
