package pap.ass05.MinDistance;

import java.util.Random;

public class Utils {
	public static final int MIN_RAND = 50;
	public static final int MAX_RAND = 10000;
	public static final int NUM_POINTS = 99999;

	/***
	 * Return random number between range max-min
	 * 
	 * @param min
	 *            Minimum value
	 * @param max
	 *            Maximum value. Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 */
	public static int randInt(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
		
	}

}
