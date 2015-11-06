package pap.ass05.CaptureTheFlag;

public interface iFlag {
	/**
	 * Imposto lo stato della bandierina ad "alzato"
	 */
	void setHigh();
	
	/**
	 * Imposto lo stato della bandierina ad "abbassato"
	 */
	void setLow();
	
	/**
	 * Restituisco lo stato della bandierina
	 * @return : True se la bandierina è alza, False altrimenti
	 */
	boolean capture();
}
