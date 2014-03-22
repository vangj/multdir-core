package net.multdir.score;

import static net.multdir.util.Util.sum;

import java.util.ArrayList;
import java.util.List;

import net.multdir.log.LogGamma;
import net.multdir.log.LogGammaRatio;

/**
 * Kutato2 (K2) Bayesian scoring function.
 *
 */
public class Kutato {

	/**
	 * {@link Kutato} builder.
	 *
	 */
	public static class KutatoBuilder {
		private List<KutatoBeta> betas = new ArrayList<KutatoBeta>();
		
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
	
	/**
	 * Beta function for {@link Kutato}.
	 *
	 */
	public static class KutatoBeta {
		private int r_i;
		private int[] N_ijk;
		private int N_ij;
		
		/**
		 * Constructor.
		 * @param N_ijk Count of the i-th variable in the
		 * k-th state and its parents in the j-th state.
		 */
		public KutatoBeta(int... N_ijk) {
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
	
	private List<KutatoBeta> betas;
	
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
		for(KutatoBeta beta : betas) {
			sum += beta.get();
		}
		return sum;
	}
}
