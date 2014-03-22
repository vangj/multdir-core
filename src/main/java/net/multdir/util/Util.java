package net.multdir.util;

/**
 * Utility class.
 *
 */
public class Util {

	private Util() { }
	
	/**
	 * Sums up the values.
	 * @param values Values.
	 * @return Sum.
	 */
	public static double sum(double... values) {
		double sum = 0.0d;
		for(double val : values)
			sum += val;
		return sum;
	}
	
	/**
	 * Sums up the values.
	 * @param values Values.
	 * @return Sum.
	 */
	public static int sum(int... values) {
		int sum = 0;
		for(int value : values)
			sum += value;
		return sum;
	}
}
