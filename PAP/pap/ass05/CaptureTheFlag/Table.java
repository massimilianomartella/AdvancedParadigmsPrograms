package pap.ass05.CaptureTheFlag;

public class Table {
	private boolean on;

	/**
	 * Imposto il tavolo da gioco impostando la condition variable a true
	 * (quando il campo si crea, è subito disponibile per giocarci).
	 */
	public Table() {
		this.setOn(true);
	}

	/**
	 * Restituisce lo stato della condition variable
	 * 
	 * @return on : true se il tavolo è disponibile, false altrimenti
	 */
	public synchronized boolean isOn() {
		return on;
	}

	/**
	 * La funzione imposta lo stato della contion variable stampando a video lo
	 * stato del tavolo da gioco: start game se il tavolo è pronto per ospitare
	 * la partita, stop game se il tavolo non è più disponibile
	 * 
	 * @param on
	 */
	public synchronized void setOn(boolean on) {
		this.on = on;
		if (on)
			System.out.println("***Start game!***");
		else
			System.out.println("***Stop game!***");
	}

}
