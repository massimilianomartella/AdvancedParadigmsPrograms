package pap.ass04;

public interface TextLib {

	/**
	 * Clear the console screen
	 */
	void cls();
	
	/**
	 * Write a text message on the console screen at the specified position
	 */
	void writeAt(int x, int y, String msg);
	
	/**
	 * Write a text message on the console screen at the specified position and color.
	 * 
	 * Code colors are specified here: http://en.wikipedia.org/wiki/ANSI_escape_code#Colors
	 */
	void writeAt(int x, int y, String st, int color);
	
	
	/***
	 * Return random number between range max-min
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 */
	int randInt(int min, int max);

}
