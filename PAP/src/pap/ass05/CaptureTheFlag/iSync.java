package pap.ass05.CaptureTheFlag;

public interface iSync {

	/**
	 * Chiamata dal player i-esimo, sospende il player fin quando non è il suo turno
	 * @param turn
	 */
	void waitForTurn(int turn);

	/**
	 * Cede il turno al player successivo
	 */
	void next();
}
