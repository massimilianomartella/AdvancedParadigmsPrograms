package pap.ass05.CaptureTheFlag;

import java.util.ArrayList;
import java.util.List;

public class TestCaptureTheFlag {

	/**
	 * La funzione main() orchestra il gioco creando: - monitor Flag - monitor
	 * Table - Thread Arbrito - Thread Giocatori
	 * 
	 * @param args
	 * @throws InterruptedException
	 *             : si utilizza la funzione Thread.sleep(N ms)
	 */
	public static void main(String[] args) throws InterruptedException {
		int numeroDiGiocate = 2000;
		int numeroGiocatori = 5;
		Flag flag = new Flag(); // Monitor bandierina
		SynchTurn turn = new SynchTurn(1, numeroGiocatori); // Monitor turno giocatori
		Table myTable = new Table(); // Monitor tavolo
		
		Arbiter myArbiter = new Arbiter(flag, myTable, numeroDiGiocate);
		// L'arbitro parte per alzare e abbassare la bandierina in un tempo
		// casuale
		myArbiter.start();

		// Dopo un secondo faccio partire i giocatori
		Thread.sleep(1000);

		// Creo i giocatori
		List<Player> tlist = new ArrayList<Player>();
		for (int i = 1; i <= numeroGiocatori; i++) {
			Player p = new Player("PlayerN", i, flag, turn, myTable);
			tlist.add(p);
			//passo all'arbitro la "lista" dei giocatori che partecipano alla partita
			myArbiter.addNamePlayers(p, numeroGiocatori);
			p.start();
		}

		/*
		 * Dopo il numero di giocate previste (int numeroDiGiocate) l'arbitro
		 * imposta il semaforo Table.isOn() = False in modo da terminare il
		 * gioco permettendo al main di uccidere i Thread
		 * 
		 */

		myArbiter.join();
		tlist.stream().forEach(t -> {
			try {
				t.join();
			} catch (Exception ex) {
			}
		});

	}

}
