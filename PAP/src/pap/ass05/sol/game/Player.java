package pap.ass05.sol.game;

public class Player extends Thread {

	private Synch synch;
	private Flag flag;
	private int turn;
	private WinnerFlag winner;
	
	public Player(int turn, Synch synch, Flag flag, WinnerFlag winner){
		this.synch = synch;
		this.flag = flag;
		this.turn = turn;
		this.winner = winner;
	}
	
	public void run(){
		boolean won = false;
		try {
			while (true){
				synch.waitForTurn(turn);
				if (flag.capture()){
					won = true;
					synch.close();
					winner.set();
					break;
				} else {
					synch.next();
				}
			}
		} catch (InterruptedException ex){
		}
		if (won){
			println("Player "+turn+" WON!");
		} else {
			println("Player "+turn+" SOB");
		}
	}
	
	private void println(String msg){
		synchronized (System.out){
			System.out.println(msg);
		}
	}
}
