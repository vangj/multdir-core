multidir = {};

multidir.sum = function(values) {
	var sum = 0;
	for(var i=0; i < values.length; i++) {
		sum += values[i];
	}
	return sum;
};

function BayesianDirichletBuilder() {
	this.betas = new Array();
};
BayesianDirichletBuilder.prototype.addCounts = function(N_ijk) {
	this.betas.push(new BdBeta(N_ijk, undefined, undefined));
	return this;
};
BayesianDirichletBuilder.prototype.addCountWithHyperparams = function(N_ijk, H_ijk) {
	this.betas.push(new BdBeta(N_ijk, H_ijk, undefined));
	return this;
};
BayesianDirichletBuilder.prototype.addCountsWithSingleHyperparam = function(N_ijk, H) {
	this.betas.push(new BdBeta(N_ijk, undefined, H));
	return this;
};
BayesianDirichletBuilder.prototype.addKutato = function(N_ijk) {
	this.betas.push(new K2Beta(N_ijk));
	return this;
};
BayesianDirichletBuilder.prototype.build = function() {
	return new BayesianDirichlet(this.betas);
};

function K2Beta(N_ijk) {
	this.N_ijk = N_ijk;
	this.r_i = N_ijk.length;
	this.N_ij = multidir.sum(this.N_ijk);
};
K2Beta.prototype.get = function() {
	var score = (new LogGammaRatio(this.r_i, this.N_ij + this.r_i)).get();
	for(var i=0; i < this.N_ijk.length; i++) {
		score += (new LogGamma(this.N_ijk[i] + 1)).get();
	}
	return score;
};

function BdBeta(N_ijk, H_ijk, H) {
	this.N_ijk = N_ijk;
	this.H_ijk = H_ijk;
	this.H = H;
};
BdBeta.prototype.get = function() {
	var N_ij = multidir.sum(this.N_ijk);
	
	var H_ij = this.N_ijk.length;
	var useOne = true;
	
	if(this.H && this.H > 0) {
		H_ij = this.H * this.N_ijk.length;
		useOne = false;
	} else if(this.H_ijk && this.H_ijk.length > 0) {
		H_ij = multidir.sum(this.H_ijk);
		useOne = false;
	}
	
	var score = (new LogGammaRatio(H_ij, N_ij)).get();
	for(var i=0; i < this.N_ijk.length; i++) {
		if(useOne) {
			score += (new LogGammaRatio(1 + this.N_ijk[i], 1)).get();
		} else {
			if(this.H && this.H > 0) {
				score += (new LogGammaRatio(this.H + this.N_ijk[i], this.H)).get();
			} else {
				score += (new LogGammaRatio(this.H_ijk[i] + this.N_ijk[i], this.H_ijk[i])).get();
			}
		}
		
	}
	return score;
}

function BayesianDirichlet(betas) {
	this.betas = betas;
};
BayesianDirichlet.prototype.get = function() {
	if(!this.betas || this.betas.length < 1)
		return Number.NaN;
	var sum = 0;
	for(var i = 0; i < this.betas.length; i++) {
		sum += this.betas[i].get();
	}
	return sum;
};

function LogGamma(x) {
	if(x > 0)
		this.x = x -1;
	else
		this.x = 1;
};
LogGamma.prototype.getX = function() { return this.x + 1; };
LogGamma.prototype.get = function() {
	var sum = 0;
	for(var n = this.x; n >= 2; n--) {
		var v = Math.log(n);
		sum += v;
	}
	return sum;
};

function LogGammaRatio(x, y) {
	this.x = new LogGamma(x);
	this.y = new LogGamma(y);
};
LogGammaRatio.prototype.get = function() {
	var logx = this.x.get();
	var logy = this.y.get();
	var result = logx - logy;
	return result;
};