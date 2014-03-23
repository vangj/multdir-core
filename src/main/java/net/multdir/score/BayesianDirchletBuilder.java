package net.multdir.score;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for {@link BayesDir}.
 *
 */
public class BayesianDirchletBuilder {
	private List<BetaFunc> betas = new ArrayList<BetaFunc>();
	
	/**
	 * Adds a {@link BdBeta} to compute.
	 * @param beta {@link BdBeta}.
	 * @return {@link BayesDirBuilder}.
	 */
	public BayesianDirchletBuilder add(BdBeta beta) {
		betas.add(beta);
		return this;
	}
	
	/**
	 * Adds beta to compute.
	 * @param beta {@link K2Beta}.
	 * @return {@link KutatoBuilder}.
	 */
	public BayesianDirchletBuilder add(K2Beta beta) { 
		betas.add(beta); 
		return this; 
	}
	
	/**
	 * Adds {@link BdBeta} to compute.
	 * @param N_ijk Counts for the i-th variable in the k-th state
	 * with the parents in the j-th state.
	 * @param H_ijk Hyperparameters for the i-th variable in the
	 * k-th state with the parents in the j-th state.
	 * @return {@link BayesDirBuilder}.
	 */
	public BayesianDirchletBuilder add(int[] N_ijk, int[] H_ijk) {
		betas.add(new BdBeta(N_ijk, H_ijk));
		return this;
	}
	
	/**
	 * are specified, they will all be set to 1.
	 * @param N_ijk Counts for the i-th variable in the k-th state
	 * with the parents in the j-th state.
	 * @return {@link BayesDirBuilder}.
	 */
	public BayesianDirchletBuilder add(int... N_ijk) {
		betas.add(new BdBeta(N_ijk));
		return this;
	}
	
	/**
	 * Adds beta to compute.
	 * @param N_ijk The count of the i-th variable in the k-th state
	 * with the parents in the j-th state.
	 * @return {@link KutatoBuilder}.
	 */
	public BayesianDirchletBuilder addKutato(int... N_ijk) {
		betas.add(new K2Beta(N_ijk));
		return this;
	}
	
	/**
	 * Adds {@link BdBeta} to compute. Gives the user an
	 * option to set the hyperparameter to be used
	 * for all counts.
	 * @param H Hyperparameter to be used for all counts.
	 * @param N_ijk Counts for the i-th variable in the k-th state
	 * with the parents in the j-th state.
	 * @return {@link BayesDirBuilder}.
	 */
	public BayesianDirchletBuilder addWithFixedHyperParam(int H, int... N_ijk) {
		betas.add(new BdBeta(N_ijk, H));
		return this;
	}
	
	/**
	 * Builds {@link BayesDir}.
	 * @return {@link BayesDir}.
	 */
	public BayesianDirchlet build() {
		return new BayesianDirchlet(this);
	}

	/**
	 * Gets the List of {@link BetaFunc}.
	 * @return List of {@link BetaFunc}.
	 */
	public List<BetaFunc> getBetas() {
		return betas;
	}
}
