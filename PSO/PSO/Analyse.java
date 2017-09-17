package PSO;

import java.io.File;

import org.moeaframework.Analyzer;
import org.moeaframework.Executor;

public class Analyse {
	public static void main(String[] args) {
		
	
	String[] algorithms = { "NSGAII", "SPEA2","MoNSGAII" };
	         
	//setup the experiment
	Executor executor = new Executor()
	    .withProblemClass(NSGAIIproblem.class)
	    .withProperty("populationSize",50)
	    .withProperty("withReplacement", true)
	    .withProperty("hux.rate", 0.95)
		.withProperty("bf.rate", 0.01)
	    .withMaxEvaluations(5000);
	         
	Analyzer analyzer = new Analyzer()
	    .withProblemClass(NSGAIIproblem.class)
	    .includeHypervolume()
	    .showStatisticalSignificance();
	 
	//run each algorithm for 50 seeds
	for (String algorithm : algorithms) {
	    analyzer.addAll(algorithm, 
	        executor.withAlgorithm(algorithm).runSeeds(50));
	}
	 
	//print the results
	analyzer.printAnalysis();
	}
}
