package pap.ass05.sol.game;

import java.util.Random;

public class Arbiter extends Thread {
	
	private Flag flag;
	private Random gen;
	private WinnerFlag winner;
	
	public Arbiter(Flag flag, WinnerFlag winner){
		this.flag = flag;
		this.winner = winner;
		gen = new Random(System.currentTimeMillis());
	}
	
	public void run(){
		while (!winner.isSet()){
			sleepAbit(500,1000);
			flag.setHigh();
			sleepAbit(100,200);
			flag.setLow();
		}
	}
	
	private void sleepAbit(int from, int to)  {
		try {
			int dt = gen.nextInt(to-from)+from;
			sleep(dt);
		} catch (Exception ex){}
	}

}
