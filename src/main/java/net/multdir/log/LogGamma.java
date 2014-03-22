package net.multdir.log;

import static com.google.common.base.Preconditions.checkArgument;


/**
 * Class to compute log(T(n)), where
 * <ul>
 *  <li>n is a positive integer</li>
 *  <li>T is the gamma function, T(n) = (n-1)! = (n-1)*(n-2)*...*2*1</li>
 *  <li>log(x) is the natural log of x</li>
 * </ul>
 *
 */
public class LogGamma {

	private int x;
	
	/**
	 * Constructor.
	 * @param x Positive integer.
	 */
	public LogGamma(int x) {
		checkArgument(x > 0, "Cannot compute a negative value.");
		this.x = x - 1;
	}
	
	/**
	 * The value, x, for which we are 
	 * computing log(T(x)).
	 * @return X.
	 */
	public int getX() { return x + 1; }
	
	/**
	 * Computes log(T(x)).
	 * @return log(T(x)).
	 */
	public double get() {
		double sum = 0.0d;
		for(int n = x; n >= 2; n--) {
			double v = Math.log(n);
			sum += v;
		}
		return sum;
	}
}
