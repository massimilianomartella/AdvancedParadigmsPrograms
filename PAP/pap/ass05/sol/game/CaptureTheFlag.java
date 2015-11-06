package pap.ass05.sol.game;

public class CaptureTheFlag {
	public static void main(String[] args) {
		int nPlayers = Integer.parseInt(args[0]);
		Flag flag = new Flag();
		WinnerFlag winnerFlag = new WinnerFlag();
		Synch synch = new Synch(nPlayers);
		new Arbiter(flag,winnerFlag).start();
		for (int i = 0; i < nPlayers; i++){
			new Player(i,synch,flag,winnerFlag).start();
		}
	}

}
