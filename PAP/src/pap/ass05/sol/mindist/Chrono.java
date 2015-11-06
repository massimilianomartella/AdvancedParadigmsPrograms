package pap.ass05.sol.mindist;

public class Chrono {
	private long t;
	
	public void reset(){
		t = System.currentTimeMillis();
	}

	public long getTimeElapsed(){
		return System.currentTimeMillis() - t;
	}
}
