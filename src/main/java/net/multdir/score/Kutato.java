package net.multdir.score;

import java.util.ArrayList;
import java.util.List;

/**
 * Kutato2 (K2) Bayesian scoring function.
 *
 */
public class Kutato implements ScoringFunc {

	/**
	 * {@link Kutato} builder.
	 *
	 */
	public static class KutatoBuilder {
		private List<BetaFunc> betas = new ArrayList<BetaFunc>();
		
		/**
		 * Adds beta to compute.
		 * @param beta {@link KutatoBeta}.
		 * @return {@link KutatoBuilder}.
		 */
		public KutatoBuilder add(KutatoBeta beta) { 
			betas.add(beta); 
			return this; 
		}
		
		/**
		 * Adds beta to compute.
		 * @param N_ijk The count of the i-th variable in the k-th state
		 * with the parents in the j-th state.
		 * @return {@link KutatoBuilder}.
		 */
		public KutatoBuilder add(int... N_ijk) {
			betas.add(new KutatoBeta(N_ijk));
			return this;
		}
		
		/**
		 * Builds {@link Kutato}.
		 * @return {@link Kutato}.
		 */
		public Kutato build() {
			return new Kutato(this);
		}
	}
	
	private List<BetaFunc> betas;
	
	private Kutato() { }
	
	private Kutato(KutatoBuilder builder) {
		this.betas = builder.betas;
	}
	
	/**
	 * Gets the score.
	 * @return K2 score.
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
