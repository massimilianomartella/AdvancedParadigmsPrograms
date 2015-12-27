package pap.ass08.TemperatureMonitoring;

public interface InputListener {

	/**
	 * Interfaccia per lo start
	 * 
	 * @param avarage
	 *            se il valore medio è fuori range per il paramentro in oggetto
	 *            allora si deve lanciare un messaggio di errore
	 * @param threshold
	 *            rappresente la soglia di errore massimo che la media dei
	 *            sensori può avere
	 */
	void started(double avarage, double threshold);

	void stopped();

}
