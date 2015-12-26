package pap.ass08.TemperatureMonitoring;

/**
 * TempFlag è la classe adibita a ricavare, a seconda del parametro passatogli
 * v, il valore massimo e minimo.
 * 
 * @author Martella Massimiliano
 *
 */
public class TempFlag {

	private double minValue, maxValue;

	public TempFlag() {
		minValue = 10000000;
		maxValue = 0;
	}

	public void setMaxMinValue(double v) {
		if (v > maxValue)
			maxValue = v;
		if (v < minValue)
			minValue = v;
	}

	public double getMinValue() {
		return minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

}