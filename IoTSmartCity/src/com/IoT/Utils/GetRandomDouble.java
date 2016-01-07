package com.IoT.Utils;

import java.util.Random;

public class GetRandomDouble {
	
	/***
	 * Return random number between range max-min
	 * 
	 * @param min
	 *            Minimum value
	 * @param max
	 *            Maximum value. Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 */
	public static double getNum(int min, int max) {
		double rand = new Random().nextDouble();
		return min + (rand * (max - min));
	}
	
}
