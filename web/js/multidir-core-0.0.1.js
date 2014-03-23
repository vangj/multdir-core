multidir = {};

multidir.sum = function(values) {
	var sum = 0;
	for(var i=0; i < values.length; i++) {
		sum += values[i];
	}
	return sum;
};

function KutatoBuilder() {
	this.betas = new Array();
};
KutatoBuilder.prototype.add = function(N_ijk) {
	this.betas.push(new KutatoBeta(N_ijk));
	return this;
};
KutatoBuilder.prototype.build = function() {
	var kutato = new Kutato(this.betas);
	return kutato;
};

function BayesDirBuilder() {
	this.betas = new Array();
};
BayesDirBuilder.prototype.addCounts = function(N_ijk) {
	this.betas.push(new Beta(N_ijk, undefined, undefined));
	return this;
};
BayesDirBuilder.prototype.addCountWithHyperparams = function(N_ijk, H_ijk) {
	this.betas.push(new Beta(N_ijk, H_ijk, undefined));
	return this;
};
BayesDirBuilder.prototype.addCountsWithSingleHyperparam = function(N_ijk, H) {
	this.betas.push(new Beta(N_ijk, undefined, H));
	return this;
};
BayesDirBuilder.prototype.build = function() {
	var bayesDir = new BayesDir(this.betas);
	return bayesDir;
};

function KutatoBeta(N_ijk) {
	this.N_ijk = N_ijk;
	this.r_i = N_ijk.length;
	this.N_ij = multidir.sum(this.N_ijk);
};
KutatoBeta.prototype.get = function() {
	var score = (new LogGammaRatio(this.r_i, this.N_ij + this.r_i)).get();
	for(var i=0; i < this.N_ijk.length; i++) {
		score += (new LogGamma(this.N_ijk[i] + 1)).get();
	}
	return score;
};

function Beta(N_ijk, H_ijk, H) {
	this.N_ijk = N_ijk;
	this.H_ijk = H_ijk;
	this.H = H;
};
Beta.prototype.get = function() {
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

function Kutato(betas) {
	this.betas = betas;
};
Kutato.prototype.get = function() {
	if(!this.betas || this.betas.length < 1)
		return Number.NaN;
	var sum = 0;
	for(var i = 0; i < this.betas.length; i++) {
		sum += this.betas[i].get();
	}
	return sum;
};

function BayesDir(betas) {
	this.betas = betas;
};
BayesDir.prototype.get = function() {
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