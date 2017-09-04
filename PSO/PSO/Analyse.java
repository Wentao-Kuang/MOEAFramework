package PSO;

import org.moeaframework.Analyzer;
import org.moeaframework.Executor;

public class Analyse {
	public static void main(String[] args) {
		
	
	String[] algorithms = { "NSGAII", "GA" };
	         
	//setup the experiment
	Executor executor = new Executor()
	    .withProblemClass(NSGAIIproblem.class)
	    .withMaxEvaluations(1000);
	         
	Analyzer analyzer = new Analyzer()
	    .withProblemClass(NSGAIIproblem.class)
	    .includeHypervolume()
	    .showStatisticalSignificance();
	 
	//run each algorithm for 50 seeds
	for (String algorithm : algorithms) {
	    analyzer.addAll(algorithm, 
	        executor.withAlgorithm(algorithm).runSeeds(200));
	}
	 
	//print the results
	analyzer.printAnalysis();
	}
}
