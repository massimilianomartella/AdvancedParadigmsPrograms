package pap.ass05.CaptureTheFlag;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arbiter extends Thread {

	private Flag myFlag;
	private Table myTable;
	private int i;
	private static List<Player> players;

	/**
	 * Il Thread Arbitro cambia lo stato del monitor Bandierina da "alzata" ad
	 * "abbassata" in modo causale Riceve la distinta dei giocatori e organizza
	 * il tavolo da gioco in modo da poter terminare la partita
	 * 
	 * @param flag
	 *            : bandierina
	 * @param table
	 *            : tavolo da gioco
	 * @param nGiocate
	 *            : numero di giocate
	 */
	public Arbiter(Flag flag, Table table, int nGiocate) {
		myFlag = flag;
		myTable = table;
		i = nGiocate;
	}

	@Override
	public void run() {

		Random random = new Random();

		// Se il tavolo è ancora disponibile
		while (myTable.isOn()) {
			try {
				// Cambio, in un tempo causale, lo stato della bandierina
				Thread.sleep(1 + (int) (random.nextDouble() * 700));
				changeStateFlag();

			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} finally {
				// Decremento il numero di giocate
				i--;
				// Se il numero di giocate è zero, vuol dire che il tempo a
				// disposizione del tavolo è scaduto.
				// Imposto la condition variable del tavolo a false per
				// terminare il gioco.
				if (i == 0)
					myTable.setOn(false);
			}
		}
	}

	/**
	 * La funzione cambia lo stato della bandierina, da alza ad abbassata e
	 * viceversa stampandone lo stato a video.
	 */
	private void changeStateFlag() {
		if (myFlag.capture()) {
			myFlag.setLow();
			System.out.println("Flag Low!");
		} else {
			myFlag.setHigh();
			System.out.println("Flag High!");
		}
	}

	/**
	 * La funzione stampa il vincitore e i vinti
	 */
	public static void whoWons() {
		players.forEach(p -> {
			if (p.getStatusGame())
				System.out.println(p.getName() + ": WON");
			else
				System.out.println(p.getName() + ": SOB");
		});
	}

	/**
	 * Imposta la distinta dei giocatori: la funzione serve per sapere chi è il
	 * vincitore e i vinti.
	 * 
	 * @param p
	 *            : giocatore partecipante
	 * @param numGiocatoriTotali
	 *            : numero di giocatori totali
	 */
	public void addNamePlayers(Player p, int numGiocatoriTotali) {
		if (players == null) {
			players = new ArrayList<Player>(numGiocatoriTotali);
			players.add(p);
		} else
			players.add(p);
	}

}
