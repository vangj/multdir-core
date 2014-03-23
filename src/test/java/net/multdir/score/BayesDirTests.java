package net.multdir.score;

import org.junit.Test;

/**
 * {@link BayesDir} tests.
 * <ul>
 *  <li>[Cooper92] G.F. Cooper and E. Herskovits. A 
 *  Bayesian method for the induction of probabilistic networks
 *  from data. Machine Learning, 9, 309--347 (1992).
 *  </li>
 * </ul>
 */
public class BayesDirTests {

	/**
	 * Tests computing the 3 network structures
	 * from [Cooper92] with default hyperparameter.
	 */
	@Test
	public void test() {
		double bs1 = (new BayesianDirchletBuilder())
				.add(5, 5)
				.add(1, 4)
				.add(4, 1)
				.add(0, 5)
				.add(4, 1)
				.build()
				.get();
		double bs2 = (new BayesianDirchletBuilder())
				.add(5, 5)
				.add(1, 4)
				.add(4, 1)
				.add(2, 3)
				.add(4, 1)
				.build()
				.get();
		double bs3 = (new BayesianDirchletBuilder())
				.add(1, 4)
				.add(4, 1)
				.add(0, 4)
				.add(5, 1)
				.add(6, 4)
				.build()
				.get();
		double bs4 = (new BayesianDirchletBuilder())
				.add(1, 3)
				.add(0, 1)
				.add(0, 0)
				.add(4, 1)
				.add(5, 5)
				.add(6, 4)
				.build()
				.get();
		
//		assertTrue(bs4 > bs3 && bs3 > bs2 && bs1 > bs2);
		System.out.println("! " + bs1 + ", " + bs2 + ", " + bs3 + ", " + bs4);
	}
	
	/**
	 * Tests computing the 3 network structures
	 * from [Cooper92] with hyperparameter=1.
	 */
	@Test
	public void testAddWithFixedHyperParam() {
		double bs1 = (new BayesianDirchletBuilder())
				.addWithFixedHyperParam(1, 5, 5)
				.addWithFixedHyperParam(1, 1, 4)
				.addWithFixedHyperParam(1, 4, 1)
				.addWithFixedHyperParam(1, 0, 5)
				.addWithFixedHyperParam(1, 4, 1)
				.build()
				.get();
		double bs2 = (new BayesianDirchletBuilder())
				.addWithFixedHyperParam(1, 5, 5)
				.addWithFixedHyperParam(1, 1, 4)
				.addWithFixedHyperParam(1, 4, 1)
				.addWithFixedHyperParam(1, 2, 3)
				.addWithFixedHyperParam(1, 4, 1)
				.build()
				.get();
		double bs3 = (new BayesianDirchletBuilder())
				.addWithFixedHyperParam(1, 1, 4)
				.addWithFixedHyperParam(1, 4, 1)
				.addWithFixedHyperParam(1, 0, 4)
				.addWithFixedHyperParam(1, 5, 1)
				.addWithFixedHyperParam(1, 6, 4)
				.build()
				.get();
		double bs4 = (new BayesianDirchletBuilder())
				.addWithFixedHyperParam(1, 1, 3)
				.addWithFixedHyperParam(1, 0, 1)
				.addWithFixedHyperParam(1, 0, 0)
				.addWithFixedHyperParam(1, 4, 1)
				.addWithFixedHyperParam(1, 5, 5)
				.addWithFixedHyperParam(1, 6, 5)
				.build()
				.get();
		
//		assertTrue(bs4 > bs3 && bs3 > bs2 && bs1 > bs2);
		System.out.println("~ " + bs1 + ", " + bs2 + ", " + bs3 + ", " + bs4);
	}
	
	/**
	 * Tests computing the 3 network structures
	 * from [Cooper92] with different hyperparameters.
	 */
	@Test
	public void testAddWithFixedHyperParamDiffH() {
		testAddWithFixedHyperParam(2);
		testAddWithFixedHyperParam(3);
		testAddWithFixedHyperParam(4);
		testAddWithFixedHyperParam(5);
		testAddWithFixedHyperParam(100000);
	}
	
	public void testAddWithFixedHyperParam(int H) {
		double bs1 = (new BayesianDirchletBuilder())
				.addWithFixedHyperParam(H, 5, 5)
				.addWithFixedHyperParam(H, 1, 4)
				.addWithFixedHyperParam(H, 4, 1)
				.addWithFixedHyperParam(H, 0, 5)
				.addWithFixedHyperParam(H, 4, 1)
				.build()
				.get();
		double bs2 = (new BayesianDirchletBuilder())
				.addWithFixedHyperParam(H, 5, 5)
				.addWithFixedHyperParam(H, 1, 4)
				.addWithFixedHyperParam(H, 4, 1)
				.addWithFixedHyperParam(H, 2, 3)
				.addWithFixedHyperParam(H, 4, 1)
				.build()
				.get();
		double bs3 = (new BayesianDirchletBuilder())
				.addWithFixedHyperParam(H, 1, 4)
				.addWithFixedHyperParam(H, 4, 1)
				.addWithFixedHyperParam(H, 0, 4)
				.addWithFixedHyperParam(H, 5, 1)
				.addWithFixedHyperParam(H, 6, 4)
				.build()
				.get();
		double bs4 = (new BayesianDirchletBuilder())
				.addWithFixedHyperParam(H, 1, 3)
				.addWithFixedHyperParam(H, 0, 1)
				.addWithFixedHyperParam(H, 0, 0)
				.addWithFixedHyperParam(H, 4, 1)
				.addWithFixedHyperParam(H, 5, 5)
				.addWithFixedHyperParam(H, 6, 4)
				.build()
				.get();
		
		System.out.println("* " + bs1 + ", " + bs2 + ", " + bs3 + ", " + bs4);
	}
}
