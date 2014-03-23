package net.multdir.score;

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
		double bs1 = (new BayesianDirchletBuilder())
				.addKutato(5, 5)
				.addKutato(1, 4)
				.addKutato(4, 1)
				.addKutato(0, 5)
				.addKutato(4, 1)
				.build()
				.get();
		double bs2 = (new BayesianDirchletBuilder())
				.addKutato(5, 5)
				.addKutato(1, 4)
				.addKutato(4, 1)
				.addKutato(2, 3)
				.addKutato(4, 1)
				.build()
				.get();
		double bs3 = (new BayesianDirchletBuilder())
				.addKutato(1, 4)
				.addKutato(4, 1)
				.addKutato(0, 4)
				.addKutato(5, 1)
				.addKutato(6, 4)
				.build()
				.get();
		double bs4 = (new BayesianDirchletBuilder())
				.addKutato(1, 3)
				.addKutato(0, 1)
				.addKutato(0, 0)
				.addKutato(4, 1)
				.addKutato(5, 5)
				.addKutato(6, 4)
				.build()
				.get();
		System.out.println(bs1 + ", " + bs2 + ", " + bs3 + ", " + bs4);
	}
}
