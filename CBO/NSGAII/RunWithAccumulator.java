package NSGAII;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.moeaframework.Executor;
import org.moeaframework.Instrumenter;
import org.moeaframework.analysis.collector.Accumulator;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.PopulationIO;
import org.moeaframework.util.ReferenceSetMerger;

public class RunWithAccumulator {
	
	
	public static void writeRefSet() throws IOException{
		// evaluate the problem with many seeds
				Executor executor = new Executor()
				        .withProblemClass(CBOproblem.class)
				        .withAlgorithm("NSGAII")
				        .withMaxEvaluations(10000);

				List<NondominatedPopulation> results = executor.runSeeds(100);

				// combine the results into a single reference set
				ReferenceSetMerger referenceSet = new ReferenceSetMerger();

				for (int i = 0; i < results.size(); i++) {
				    referenceSet.add("Seed" + i, results.get(i));
				}

				// save reference set to a file
				PopulationIO.writeObjectives(
				        new File("CBO/MyRefSet.pf"),
				        referenceSet.getCombinedPopulation());

	}
	
	public static void runAlgorithm(){
		// setup the instrumenter to use your reference set)
		Instrumenter instrumenter = new Instrumenter()
		.withProblemClass(CBOproblem.class)
		.withFrequency(100)
		.attachElapsedTimeCollector()
		.attachGenerationalDistanceCollector()
		.attachHypervolumeCollector()
		.withReferenceSet(new File("CBO/MyRefSet.pf"));
		
		NondominatedPopulation result = new Executor()
		.withAlgorithm("NSGAII")
		.withProblemClass(CBOproblem.class)
		.withProperty("populationSize", 100)
		.distributeOnAllCores()
		.withMaxEvaluations(10000)
		.withInstrumenter(instrumenter)
		.run();	
		
				
		System.out.format("  NFE    Time      Generational Distance     Hypervolume %n");
		Accumulator accumulator = instrumenter.getLastAccumulator();
		
		//print result
		final Object[][] table=new String[accumulator.size("NFE")][4];
		for (int i=0; i<accumulator.size("NFE"); i++) {
			  System.out.println(accumulator.get("NFE", i) + "\t" + 
			      accumulator.get("Elapsed Time", i).toString().substring(0,10) + "\t" +
			      accumulator.get("GenerationalDistance", i).toString() + "\t" +
			      accumulator.get("Hypervolume", i).toString());
			}
		
		
	}
	
	public static void main(String[] args) throws IOException  {
		
		//writeRefSet();
		runAlgorithm();
		
		

	}
}
