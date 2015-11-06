package pap.ass05.CaptureTheFlag;

public class SynchTurn implements iSync {

	private int whoseTurn;
	private int numTotPlayers;
	
	/**
	 * Monitor che gestisce il turno da rispettare dei giocatori
	 * @param primoTurno : indica da quale giocatore si inizia a far "girare" il token
	 * @param numPlayers : indica il numero totale di giocatori
	 */
	public SynchTurn(int primoTurno, int numPlayers) {
		whoseTurn = primoTurno;
		numTotPlayers = numPlayers;
	}

	@Override
	public synchronized void waitForTurn(int turn) {
		// Se il turno non è quello giusto
		if (turn != whoseTurn)
			try {
				// aspetto
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public synchronized void next() {
		// Il turno va da 1 a numTotPlayers, ciclicamente
		if (whoseTurn == numTotPlayers)
			whoseTurn = 1;
		else
			whoseTurn++;

		// Tutte le volte che il turno viene incrementato, sveglio il Thread in
		// attesa che stava aspettando in waitForTurn
		System.out.println("Prossimo turno: " + whoseTurn);
		notify();
	}
}
