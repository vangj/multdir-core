package net.multdir.log;

import static org.junit.Assert.assertEquals;
import net.multdir.log.LogGamma;

import org.junit.Test;

public class LogGammaTests {

	@Test
	public void test() {
		double expected = Math.log(3 * 2 * 1); //T(4) = 3!
		double actual = (new LogGamma(4)).get();
		assertEquals(expected, actual, 0.00001d);
		
		expected = Math.log(9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1); //T(10) = 9!
		actual = (new LogGamma(10)).get();
		assertEquals(expected, actual, 0.00001d);
	}
}
