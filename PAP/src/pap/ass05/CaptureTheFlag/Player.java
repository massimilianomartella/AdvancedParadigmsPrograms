package pap.ass05.CaptureTheFlag;

public class Player extends Thread {
	private Flag flagMyTurn;
	private SynchTurn mTurn;
	private int turn;
	private boolean statusGame;
	private Table myTable;

	/**
	 * La funzione crea il Thread giocatore che prova a catturare la bandierina
	 * ogni 800 ms
	 * 
	 * @param name
	 *            : nome del giocatore
	 * @param turn
	 *            : numero del giocatore
	 * @param flag
	 *            : monitor bandierina -> si alza e si abbassa in un tempo
	 *            casuale, funzione gestita dall'arbrito
	 * @param mTurn
	 *            : turno del giocatore
	 * @param table
	 *            : tavolo -> il tavolo chiude dopo N giocate, funzione gestita
	 *            dall'arbitro
	 */
	public Player(String name, int turn, Flag flag, SynchTurn mTurn, Table table) {
		this.setName(name + turn);
		this.flagMyTurn = flag;
		this.mTurn = mTurn;
		this.turn = turn;
		this.setStatusGame(false);
		this.myTable = table;
	}

	@Override
	public void run() {
		while (myTable.isOn()) {
			try {
				mTurn.waitForTurn(turn); // Aspetto il mio turno
				Thread.sleep(800); // Pronti... via!
				if (flagMyTurn.capture()) { // Se catturo la bandierina
					// Imposto lo stato StatusGame a WIN (true)
					// chiedo all'arbrito chi è il vincitore e resetto il mio
					// stato
					this.setStatusGame(true);
					Arbiter.whoWons();
					this.setStatusGame(false);

					// passo il turno
					mTurn.next();
				} else {
					// altrimenti passo il turno
					mTurn.next();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Imposta lo stato del Giocatore: true si il giocatore è il vincitore, false altrimenti
	 * @param statusGame : true se giocatore vincente, false altrimenti
	 */
	public void setStatusGame(boolean statusGame) {
		this.statusGame = statusGame;
	}

	/**
	 * La funziona restituisce lo stato del giocatore
	 * @return True se vincente, False se perdente
	 */
	public boolean getStatusGame() {
		return statusGame;
	}
}
