package net.multdir.log;

import static net.multdir.util.Util.sum;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LogGammaRatioTests {

	@Test
	public void test() {
		double num = Math.log(3 * 2 * 1); //T(4) = 3!
		double den = Math.log(9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1); //T(10) = 9!
		double expected = num - den; //T(4) / T(10) = 3! / 9!
		double actual = (new LogGammaRatio(4, 10)).get();
		assertEquals(expected, actual, 0.00001d);
		
		expected = den - num;
		actual = (new LogGammaRatio(10, 4)).get();
		assertEquals(expected, actual, 0.00001d);
	}
	
	@Test
	public void testCooper() {
		double s1 = getScore1();
		double s2 = getScore2();
		double s3 = getScore3();
		double s4 = getScore4();
		
		System.out.println(s1 + ", " + s2 + ", " + s3 + ", " + s4);
		assertTrue(s4 > s3 && s3 > s2 && s1 > s2);
	}
	
	public double getScore1() {
		// X1 -> X2 -> X3 figure (1) or B_S1
		double s1 = compute(2, 5, 5);
		double s2 = compute(2, 1, 4);
		double s3 = compute(2, 4, 1);
		double s4 = compute(2, 0, 5);
		double s5 = compute(2, 4, 1);
		double score = sum(s1, s2, s3, s4, s5);
		return score;
	}
	
	public double getScore2() {
		// X2 <- X1 -> X3 figure (2) or B_S2
		double s1 = compute(2, 5, 5); //x1
		double s2 = compute(2, 1, 4); //x2
		double s3 = compute(2, 4, 1);
		double s4 = compute(2, 2, 3); //x3
		double s5 = compute(2, 4, 1);
		double score = sum(s1, s2, s3, s4, s5);
		return score;
	}
	
	public double getScore3() {
		// X1 <- X2 <- X3  B_S2
		double s1 = compute(2, 1, 4); //x1
		double s2 = compute(2, 4, 1);
		double s3 = compute(2, 0, 4); //x2
		double s4 = compute(2, 5, 1);
		double s5 = compute(2, 6, 4);//x3
		double score = sum(s1, s2, s3, s4, s5);
		return score;
	}
	
	public double getScore4() {
		// X2 -> X1 <- X3
		double s1 = compute(2, 2, 3); //x2
		double s2 = compute(2, 4, 1); //x3
		double s3 = compute(2, 0, 1); //x1
		double s4 = compute(2, 4, 1);
		double s5 = compute(2, 0, 0);
		double s6 = compute(2, 1, 1);
		double score = sum(s1, s2, s3, s4, s5, s6);
		return score;
	}
	
	public double compute(int r_i, int... N_ijk) {
		int N_ij = sum(N_ijk);
		double score = (new LogGammaRatio(r_i, N_ij + r_i)).get();
		for(int n_ijk : N_ijk) {
			score += (new LogGamma(n_ijk+1)).get();
		}
		return score;
	}
}
