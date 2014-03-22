package net.multdir.score;

import net.multdir.score.Kutato;

import org.junit.Test;

/**
 * {@link Kutato} tests.
 * <ul>
 *  <li>[Cooper92] G.F. Cooper and E. Herskovits. A 
 *  Bayesian method for the induction of probabilistic networks
 *  from data. Machine Learning, 9, 309--347 (1992).
 *  </li>
 * </ul>
 *
 */
public class KutatoTests {

	/**
	 * Tests computing the 3 network structures
	 * from [Cooper92].
	 */
	@Test
	public void test() {
		double bs1 = (new Kutato.KutatoBuilder())
				.add(5, 5)
				.add(1, 4)
				.add(4, 1)
				.add(0, 5)
				.add(4, 1)
				.build()
				.get();
		double bs2 = (new Kutato.KutatoBuilder())
				.add(5, 5)
				.add(1, 4)
				.add(4, 1)
				.add(2, 3)
				.add(4, 1)
				.build()
				.get();
		double bs3 = (new Kutato.KutatoBuilder())
				.add(1, 4)
				.add(4, 1)
				.add(0, 4)
				.add(5, 1)
				.add(6, 4)
				.build()
				.get();
		double bs4 = (new Kutato.KutatoBuilder())
				.add(1, 3)
				.add(0, 1)
				.add(0, 0)
				.add(4, 1)
				.add(5, 5)
				.add(6, 4)
				.build()
				.get();
		System.out.println(bs1 + ", " + bs2 + ", " + bs3 + ", " + bs4);
	}
}
