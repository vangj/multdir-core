package net.multdir.score;

import java.util.ArrayList;
import java.util.List;

import com.google.common.annotations.Beta;

/**
 * Bayesian Dirichlet (BD) scoring function.
 *
 */
public class BayesDir implements ScoringFunc {
	
	/**
	 * Builder for {@link BayesDir}.
	 *
	 */
	public static class BayesDirBuilder {
		private List<BayesDirBeta> betas = new ArrayList<BayesDirBeta>();
		
		/**
		 * Adds a {@link Beta} to compute.
		 * @param beta {@link Beta}.
		 * @return {@link BayesDirBuilder}.
		 */
		public BayesDirBuilder add(BayesDirBeta beta) {
			betas.add(beta);
			return this;
		}
		
		/**
		 * Adds {@link Beta} to compute.
		 * @param N_ijk Counts for the i-th variable in the k-th state
		 * with the parents in the j-th state.
		 * @param H_ijk Hyperparameters for the i-th variable in the
		 * k-th state with the parents in the j-th state.
		 * @return {@link BayesDirBuilder}.
		 */
		public BayesDirBuilder add(int[] N_ijk, int[] H_ijk) {
			betas.add(new BayesDirBeta(N_ijk, H_ijk));
			return this;
		}
		
		/**
		 * Adds {@link Beta} to compute. Since no hyperparameters 
		 * are specified, they will all be set to 1.
		 * @param N_ijk Counts for the i-th variable in the k-th state
		 * with the parents in the j-th state.
		 * @return {@link BayesDirBuilder}.
		 */
		public BayesDirBuilder add(int... N_ijk) {
			betas.add(new BayesDirBeta(N_ijk));
			return this;
		}
		
		/**
		 * Adds {@link Beta} to compute. Gives the user an
		 * option to set the hyperparameter to be used
		 * for all counts.
		 * @param H Hyperparameter to be used for all counts.
		 * @param N_ijk Counts for the i-th variable in the k-th state
		 * with the parents in the j-th state.
		 * @return {@link BayesDirBuilder}.
		 */
		public BayesDirBuilder addWithFixedHyperParam(int H, int... N_ijk) {
			betas.add(new BayesDirBeta(N_ijk, H));
			return this;
		}
		
		/**
		 * Builds {@link BayesDir}.
		 * @return {@link BayesDir}.
		 */
		public BayesDir build() {
			return new BayesDir(this);
		}
	}
	
	private List<BayesDirBeta> betas;
	
	private BayesDir() { }
	
	private BayesDir(BayesDirBuilder builder) {
		this.betas = builder.betas;
	}
	
	/**
	 * Gets the score.
	 * @return Score.
	 */
	public double get() {
		if(null == betas || betas.size() < 1)
			return Double.NEGATIVE_INFINITY;
		
		double sum = 0.0d;
		for(BayesDirBeta beta : betas) {
			sum += beta.get();
		}
		return sum;
	}
}
