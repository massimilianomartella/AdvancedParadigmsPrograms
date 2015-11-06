package pap.ass04;

import java.util.Random;

public class TextLibFactory implements TextLib {
	
	private static TextLibFactory instance;
	
	/**
	 * Get the instance of the factory
	 *   
	 * @return
	 */
	public synchronized static TextLib getInstance(){
		if (instance == null){
			instance = new TextLibFactory();
			return instance;
		} else {
			return instance;
		}
	}
	
	public synchronized void cls(){
		System.out.print("\u001b[2J");
		System.out.flush();
		
	}
	public synchronized void writeAt(int x, int y, String st){
		System.out.print("\u001b["+y+";"+x+"H");
		System.out.print(st);
		System.out.flush();
	}

	public synchronized void writeAt(int x, int y, String st, int color){
		System.out.print("\u001b[3"+color+"m");
		System.out.print("\u001b["+y+";"+x+"H");
		System.out.print(st);
		System.out.flush();
	}
	
	public int randInt(int min, int max) {
	    Random rand = new Random();
	    return rand.nextInt((max - min) + 1) + min;
	}
	 
	private TextLibFactory(){}
	
	
	

}
