package pap.ass08.GameOfLife.Actors;

/**
 * Monitor per settare lo start e lo stop dell'algoritmo
 * @author Martella Massimiliano
 *
 */
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

