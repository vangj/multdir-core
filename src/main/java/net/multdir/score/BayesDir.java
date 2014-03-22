package net.multdir.score;

import static com.google.common.base.Preconditions.checkArgument;
import static net.multdir.util.Util.sum;

import java.util.ArrayList;
import java.util.List;

import net.multdir.log.LogGammaRatio;

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
		private List<Beta> betas = new ArrayList<Beta>();
		
		/**
		 * Adds a {@link Beta} to compute.
		 * @param beta {@link Beta}.
		 * @return {@link BayesDirBuilder}.
		 */
		public BayesDirBuilder add(Beta beta) {
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
			betas.add(new Beta(N_ijk, H_ijk));
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
			betas.add(new Beta(N_ijk));
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
			betas.add(new Beta(N_ijk, H));
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

	/**
	 * Beta for {@link BayesDir}.
	 *
	 */
	public static class Beta {
		private int[] N_ijk;
		private int[] H_ijk;
		private int H = -1;
		
		/**
		 * Constructor.
		 * @param N_ijk Counts for the i-th variable in the k-th state
		 * with the parents in the j-th state.
		 * @param H_ijk Hyperparameters for the i-th variable in the k-th state
		 * with the parents in the j-th state.
		 */
		public Beta(int[] N_ijk, int[] H_ijk) {
			checkArgument(N_ijk.length == H_ijk.length, "Lengths of counts and hyperparameters must be the same.");
			this.N_ijk = N_ijk;
			this.H_ijk = H_ijk;
		}
		
		/**
		 * Constructor.
		 * @param N_ijk Counts for the i-th variable in the k-th state
		 * with the parents in the j-th state.
		 */
		public Beta(int... N_ijk) {
			this.N_ijk = N_ijk;
		}
		
		/**
		 * Constructor.
		 * @param N_ijk Counts for the i-th variable in the k-th state
		 * with the parents in the j-th state.
		 * @param H A single hyperparameter to be used
		 * for all counts.
		 */
		public Beta(int[] N_ijk, int H) {
			this.N_ijk = N_ijk;
			this.H = H;
		}
		
		/**
		 * Computes the value.
		 * @return Value.
		 */
		public double get() {
			int N_ij = sum(N_ijk);
			
			int H_ij = N_ijk.length;
			boolean useOne = true;
			
			if(H > 0) {
				H_ij = H * N_ijk.length;
				useOne = false;
			} else {
				if(null != H_ijk && H_ijk.length > 0) {
					H_ij = sum(H_ijk);
					useOne = false;
				}
			}
			
			double score = (new LogGammaRatio(H_ij, N_ij)).get();
			for(int i=0; i < N_ijk.length; i++) {
				if(useOne) {
					score += (new LogGammaRatio(1 + N_ijk[i], 1)).get();
				} else {
					if(H > 0) {
						score += (new LogGammaRatio(H + N_ijk[i], H)).get();
					} else {
						score += (new LogGammaRatio(H_ijk[i] + N_ijk[i], H_ijk[i])).get();
					}
				}
			}
			return score;
		}
	}
	
	private List<Beta> betas;
	
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
		for(Beta beta : betas) {
			sum += beta.get();
		}
		return sum;
	}
}
