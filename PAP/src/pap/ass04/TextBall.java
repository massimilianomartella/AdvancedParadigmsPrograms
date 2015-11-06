package pap.ass04;

class writeBall extends Thread {
	private int PosX, PosY, ColorShell;
	private int randDirection, precRandDirection;
	private int max_x, max_y, min_x, min_y;

	/**
	 * La classe "writeBall" stampa un asterisco per ogni Thread creato.
	 * L'asterisco si muove in modalità casuale (sopra, sotto, destra e
	 * sinistra)
	 * 
	 * @param myPosX
	 *            : posizione X iniziale
	 * @param myPosY
	 *            : posizione Y iniziale
	 * @param myColorShell
	 *            : colore assegnato all'asterisco
	 */
	writeBall(int myPosX, int myPosY, int myColorShell, int min_x, int max_x, int min_y, int max_y) {
		this.PosX = myPosX;
		this.PosY = myPosY;
		this.ColorShell = myColorShell;
		randDirection = 1;
		
		this.max_x = max_x;
		this.min_x = min_x;
		this.max_y = max_y;
		this.min_y = min_y;
		
		
		
	}

	@Override
	public void run() {
		TextLib lib = TextLibFactory.getInstance();
		int precPosX = 0, precPosY = 0;
		try {

			// Distanza di spostamento (dalla vecchia posizione alla nuova)
			int x = 1;
			int y = 1;
			
			
			while (true) {

				// Scrivi il punto sullo schermo
				lib.writeAt(PosX, PosY, "*", ColorShell);
				// lancio il dado e prendo una direzione casuale
				lanciaIlDado();

				switch (randDirection) { // 3=sopra, 4=sotto, 1=destra,
											// 2=sinistra
				case 1: // destra
					if (PosX >= min_x && PosX <= max_x) {
						precPosX = PosX; // Salvo la posizione
						PosX += x; // Nuova posizione

						// Cancello l'asterisco dalla posizione precedente
						lib.writeAt(precPosX, PosY, " ");
						// Stampo l'asterisco nella nuova posizione
						lib.writeAt(PosX, PosY, "*", ColorShell);
					}
					// Se la posizione è arrivata al limite faccio
					// tornare indietro l'asterisco facendolo "rimbalzare"
					// sul bordo
					precRandDirection = rimbalza();
					break;

				case 2: // sinistra
					if (PosX >= min_x && PosX <= max_x) {
						precPosX = PosX;
						PosX -= x;

						lib.writeAt(precPosX, PosY, " ");
						lib.writeAt(PosX, PosY, "*", ColorShell);

					}
					precRandDirection = rimbalza();
					break;

				case 3: // sopra
					if (PosY >= min_y && PosY <= max_y) {
						precPosY = PosY;
						PosY -= y;

						lib.writeAt(PosX, precPosY, " ");
						lib.writeAt(PosX, PosY, "*", ColorShell);

					}
					precRandDirection = rimbalza();
					break;

				case 4: // sotto
					if (PosY >= min_y && PosY <= max_y) {
						precPosY = PosY;
						PosY += y;

						lib.writeAt(PosX, precPosY, " ");
						lib.writeAt(PosX, PosY, "*", ColorShell);
					}
					precRandDirection = rimbalza();
					break;

				default:
					break;
				}
				Thread.sleep(80);
			}
		} catch (InterruptedException e) {
			System.out.println("Thread interrupted.");
		}
	}

	/**
	 * Simulazione del lancio di un dado a 7 facce: se il dado fa uscire il
	 * numero 5, allora l'asterisco prende una direzione casuale tra sopra,
	 * sotto, destra e sinistra. Se il dado non fa uscire il numero 5 allora la
	 * direzione rimane la stessa e non cambia
	 */
	private void lanciaIlDado() {
		if (TextLibFactory.getInstance().randInt(0, 7) == 5) // lancio un dado a
																// 7 facce
		{
			precRandDirection = randDirection; // salvo la direzione
			randDirection = TextLibFactory.getInstance().randInt(1, 4); // nuova
																		// direzione
																		// in 4
			// direzioni possibili
		} else
			randDirection = precRandDirection; // la direzione rimane la
												// stessa

	}

	/**
	 * La funzione mantiene la direzione dell'asterisco cercando di farlo
	 * convergere verso l'interno dello schermo, senza farlo convergere verso il
	 * bordo.
	 * 
	 * @return precRandDirection: se l'asterisco è vicino al bordo, lo faccio
	 *         convergere verso il centro, altrimenti lascio la direzione
	 *         inalterata
	 */
	private int rimbalza() {
		if (PosX >= max_x) // troppo a destra
			return precRandDirection = 2; // vai a sinistra
		if (PosX <= 0) // troppo a sinistra
			return precRandDirection = 1; // vai a destra
		if (PosY >= max_y) // troppo in basso
			return precRandDirection = 3; // vai in alto
		if (PosY <= 0) // troppo in alto
			return precRandDirection = 4; // vai in basso
		else
			return precRandDirection;
	}

}

public class TextBall {

	/*
	 * launch program from terminal: java -cp ./Documents/workspace/PAP/bin/
	 * pap.ass04.TextBall XX
	 */
	
	

	public static void main(String args[]) {

		TextLibFactory.getInstance().cls();
		int max_x = 158, max_y = 46, min_x = 0, min_y = 0;
		
		for (int i = 0; i < Integer.parseInt(args[0]); i++) {
			int randColor = TextLibFactory.getInstance().randInt(0, 7);
			int randX = TextLibFactory.getInstance().randInt(min_x, max_x);
			int randY = TextLibFactory.getInstance().randInt(min_y, max_y);

			// A seconda del numero di Thread passato come parametro (Args[0])
			// stampo un asterisco per ogni Thread in una posizione casuale
			// (randX, randY) e con un colore casuale (randColor)
			new writeBall(randX, randY, randColor, min_x, max_x, min_y, max_y).start();
		}
	}

}
