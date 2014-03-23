package net.multdir.score;

import java.util.List;

/**
 * Bayesian dirichlet.
 *
 */
public class BayesianDirchlet {

	protected List<BetaFunc> betas;
	
	/**
	 * Constructor.
	 * @param builder {@link BayesianDirchletBuilder}.
	 */
	protected BayesianDirchlet(BayesianDirchletBuilder builder) {
		this.betas = builder.getBetas();
	}
	
	/**
	 * Gets the score.
	 * @return Score.
	 */
	public double get() {
		if(null == betas || betas.size() < 1)
			return Double.NEGATIVE_INFINITY;
		
		double sum = 0.0d;
		for(BetaFunc beta : betas) {
			sum += beta.get();
		}
		return sum;
	}

}
