$(document).ready(function() {
	pageModel = new PageModel(data);
	ko.applyBindings(pageModel);
	pageModel.updateChart();
	MathJax.Hub.Config({ tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]} });
});

var pageModel;
var data = [
            { x1 : "p", x2: "a", x3: "a" },
            { x1 : "p", x2: "p", x3: "p" },
            { x1 : "a", x2: "a", x3: "p" },
            { x1 : "p", x2: "p", x3: "p" },
            { x1 : "a", x2: "a", x3: "a" },
            { x1 : "a", x2: "p", x3: "p" },
            { x1 : "p", x2: "p", x3: "p" },
            { x1 : "a", x2: "a", x3: "a" },
            { x1 : "p", x2: "p", x3: "p" },
            { x1 : "a", x2: "a", x3: "a" }
            ];

var Record = function(data) {
	this.x1 = ko.observable(data.x1);
	this.x2 = ko.observable(data.x2);
	this.x3 = ko.observable(data.x3);
};

var PageModel = function(data) {
	var self = this;
	self.valueOptions = ko.observableArray(["a","p"]);
	self.records = ko.observableArray(ko.utils.arrayMap(data, function(record){ 
		return new Record(record);
	}));
	
	self.count = function(lambda) {
		var total = self.records().where(lambda).count();
		return total;
	}
	
	self.score1 = ko.computed(function() { 
		var score = (new KutatoBuilder())
			.add(2, [self.count("x=>x.x1()=='p'"), self.count("x=>x.x1()=='a'")])
			.add(2, [self.count("x=>x.x2()=='p' && x.x1()=='a'"), self.count("x=>x.x2()=='a' && x.x1()=='a'")])
			.add(2, [self.count("x=>x.x2()=='p' && x.x1()=='p'"), self.count("x=>x.x2()=='a' && x.x1()=='p'")])
			.add(2, [self.count("x=>x.x3()=='p' && x.x2()=='a'"), self.count("x=>x.x3()=='a' && x.x2()=='a'")])
			.add(2, [self.count("x=>x.x3()=='p' && x.x2()=='p'"), self.count("x=>x.x3()=='a' && x.x2()=='p'")])
			.build()
			.get();
		return score;
	});
	
	self.score2 = ko.computed(function() { 
		var score = (new KutatoBuilder())
			.add(2, [self.count("x=>x.x1()=='p'"), self.count("x=>x.x1()=='a'")])
			.add(2, [self.count("x=>x.x2()=='p' && x.x1()=='a'"), self.count("x=>x.x2()=='a' && x.x1()=='a'")])
			.add(2, [self.count("x=>x.x2()=='p' && x.x1()=='p'"), self.count("x=>x.x2()=='a' && x.x1()=='p'")])
			.add(2, [self.count("x=>x.x3()=='p' && x.x1()=='a'"), self.count("x=>x.x3()=='a' && x.x1()=='a'")])
			.add(2, [self.count("x=>x.x3()=='p' && x.x1()=='p'"), self.count("x=>x.x3()=='a' && x.x1()=='p'")])
			.build()
			.get();
		return score;
	});
	
	self.score3 = ko.computed(function() { 
		var score = (new KutatoBuilder())
			.add(2, [self.count("x=>x.x1()=='p' && x.x2()=='a'"), self.count("x=>x.x1()=='a' && x.x2()=='a'")])
			.add(2, [self.count("x=>x.x1()=='p' && x.x2()=='p'"), self.count("x=>x.x1()=='a' && x.x2()=='p'")])
			.add(2, [self.count("x=>x.x2()=='p' && x.x3()=='a'"), self.count("x=>x.x2()=='a' && x.x3()=='a'")])
			.add(2, [self.count("x=>x.x2()=='p' && x.x3()=='p'"), self.count("x=>x.x2()=='a' && x.x3()=='p'")])
			.add(2, [self.count("x=>x.x3()=='p'"), self.count("x=>x.x3()=='a'")])
			.build()
			.get();
		return score;
	});
	
	self.score4 = ko.computed(function() { 
		var score = (new KutatoBuilder())
			.add(2, [self.count("x=>x.x1()=='p' && x.x2()=='a' && x.x3()=='a'"), self.count("x=>x.x1()=='a' && x.x2()=='a' && x.x3()=='a'")])
			.add(2, [self.count("x=>x.x1()=='p' && x.x2()=='a' && x.x3()=='p'"), self.count("x=>x.x1()=='a' && x.x2()=='a' && x.x3()=='p'")])
			.add(2, [self.count("x=>x.x1()=='p' && x.x2()=='p' && x.x3()=='a'"), self.count("x=>x.x1()=='a' && x.x2()=='p' && x.x3()=='a'")])
			.add(2, [self.count("x=>x.x1()=='p' && x.x2()=='p' && x.x3()=='p'"), self.count("x=>x.x1()=='a' && x.x2()=='p' && x.x3()=='p'")])
			.add(2, [self.count("x=>x.x2()=='p'"), self.count("x=>x.x2()=='a'")])
			.add(2, [self.count("x=>x.x3()=='p'"), self.count("x=>x.x3()=='a'")])
			.build()
			.get();
		return score;
	});
	
	self.dataModified = function() {
		self.updateChart();
	};
	
	self.updateChart = function() {
		var chartData = self.getChartData();
		//console.log(chartData);
		
		nv.addGraph(function() {
			  var chart = nv.models.discreteBarChart()
			      .x(function(d) { return d.label })    //Specify the data accessors.
			      .y(function(d) { return d.value })
			      .staggerLabels(true)    //Too many bars and not enough room? Try staggering labels.
			      .tooltips(false)        //Don't show tooltips
			      .showValues(true)       //...instead, show the bar value right on top of each bar.
			      .transitionDuration(350)
			      ;

			  d3.select("#chart svg")
			      .datum(chartData)
			      .call(chart);

			  nv.utils.windowResize(chart.update);

			  return chart;
			});
	};
	
	self.getChartData = function() {
		return  [ 
		         {
		           key: "Cumulative Return",
		           values: [
		             { 
		               "label" : "BS1" ,
		               "value" : self.score1()
		             } , 
		             { 
		               "label" : "BS2" , 
		               "value" : self.score2()
		             } , 
		             { 
		               "label" : "BS3" , 
		               "value" : self.score3()
		             } , 
		             { 
		               "label" : "BS4" , 
		               "value" : self.score4()
		             }
		           ]
		         }
		       ]
	};
	
	self.nextValue = function() {
		var prob = Math.random();
		if(prob < 0.5)
			return "a";
		return "p";
	}
	
	self.addRecord = function() {
		var data = { x1: self.nextValue(), x2: self.nextValue(), x3: self.nextValue()};
		//console.log(data);
		var record = new Record(data);
		self.records.unshift(record);
		self.updateChart();
	};
};
