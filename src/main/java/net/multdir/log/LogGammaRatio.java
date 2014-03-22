package net.multdir.log;

/**
 * Class to compute the log of a gamma ratio, where
 * log(T(n)) is defined as
 * <ul>
 *  <li>n is a positive integer</li>
 *  <li>T is the gamma function, T(n) = (n-1)!</li>
 *  <li>log(x) is the natural log of x</li>
 * </ul>
 * A gamma ratio takes on the form
 * <ul>
 *  <li>T(x) / T(y) = (x-1)! / (y-1)!</li>
 * </ul>
 * Since it is prohibitive to actually compute
 * factorials, we work in log space.
 * <table>
 *  <tr>
 *   <td>log( T(x) / T(y) )</td>
 *   <td>=</td>
 *   <td>log( T(x) ) - log ( T(y) )</td>
 *  </tr>
 *  <tr>
 *   <td>&nbsp;</td>
 *   <td>=</td>
 *   <td>log( (x-1)! ) - log ( (y-1)! )</td>
 *  </tr>
 *  <tr>
 *   <td>&nbsp;</td>
 *   <td>=</td>
 *   <td>log( (x-1)*(x-2)*...*2*1 ) - log( (y-1)*(y-2)*...*2*1 )</td>
 *  </tr>
 *  <tr>
 *   <td>&nbsp;</td>
 *   <td>=</td>
 *   <td>( log(x-1) + log (x-2) + ... + log(2) + log(1) ) - ( log(y-1) + log (y-2) + ... + log(2) + log(1) )</td>
 *  </tr>
 *  <tr>
 *   <td>&nbsp;</td>
 *   <td>=</td>
 *   <td>log(x-1) + log (x-2) + ... + log(2) + log(1) - log(y-1) - log (y-2) - ... - log(2) - log(1)</td>
 *  </tr>
 * </table>
 */
public class LogGammaRatio {

	private LogGamma x, y;
	
	/**
	 * Constructor.
	 * @param x Positive integer.
	 * @param y Positive integer.
	 */
	public LogGammaRatio(int x, int y) {
		this.x = new LogGamma(x);
		this.y = new LogGamma(y);
	}
	
	/**
	 * Gets the log of the gamma ratio.
	 * @return Value.
	 */
	public double get() {
		double logx = x.get();
		double logy = y.get();
		double result = logx - logy;
		return result;
	}
}
