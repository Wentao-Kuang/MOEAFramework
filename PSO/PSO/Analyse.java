package PSO;

import org.moeaframework.Analyzer;
import org.moeaframework.Executor;

public class Analyse {
	public static void main(String[] args) {
		
	
	String[] algorithms = { "NSGAII", "SMPSO","OMOPSO" };
	         
	//setup the experiment
	Executor executor = new Executor()
	    .withProblemClass(PSOproblem.class)
	    .withMaxEvaluations(50000);
	         
	Analyzer analyzer = new Analyzer()
	    .withProblemClass(PSOproblem.class)
	    .includeHypervolume()
	    .showStatisticalSignificance();
	 
	//run each algorithm for 50 seeds
	for (String algorithm : algorithms) {
	    analyzer.addAll(algorithm, 
	        executor.withAlgorithm(algorithm).runSeeds(30));
	}
	 
	//print the results
	analyzer.printAnalysis();
	}
}
