package net.multdir.score;

import static net.multdir.util.Util.sum;
import net.multdir.log.LogGamma;
import net.multdir.log.LogGammaRatio;

/**
 * Beta function K2.
 *
 */
public class K2Beta implements BetaFunc {

	private int r_i;
	private int[] N_ijk;
	private int N_ij;
	
	/**
	 * Constructor.
	 * @param N_ijk Count of the i-th variable in the
	 * k-th state and its parents in the j-th state.
	 */
	public K2Beta(int... N_ijk) {
		this.N_ijk = N_ijk;
		this.r_i = N_ijk.length;
		this.N_ij = sum(N_ijk);
	}
	
	/**
	 * Gets the value.
	 * @return Value.
	 */
	public double get() {
		double score = (new LogGammaRatio(r_i, N_ij + r_i)).get();
		for(int n_ijk : N_ijk) {
			score += (new LogGamma(n_ijk+1)).get();
		}
		return score;
	}

}
