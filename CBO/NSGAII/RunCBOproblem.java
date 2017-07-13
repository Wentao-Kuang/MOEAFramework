package NSGAII;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import CBO.NSGA.CBOproblem1;

import org.moeaframework.core.Problem;
import org.moeaframework.util.Vector;
import org.moeaframework.util.io.CommentedLineReader;
import org.moeaframework.Executor;
import org.moeaframework.Instrumenter;
import org.moeaframework.core.EpsilonBoxDominanceArchive;
import org.moeaframework.core.NondominatedPopulation; 
import org.moeaframework.core.Population;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.analysis.collector.Accumulator;
import org.moeaframework.analysis.plot.Plot;

public class RunCBOproblem {
	public static void main(String[] args) {
		//simulation times
		for(int i=0;i<5;i++){
			System.out.println("run: "+i);
			run();
		}
	}
	
	static void writeResults(String filename,String line){
		try {
			Writer output;
			output = new BufferedWriter(new FileWriter(filename, true));  //clears file every time
			output.append(line);
			output.close();
		} catch (IOException e) {
			System.err.println("Writing data error");
			// why does the catch need its own curly?
		}
		
	}
	
	static void run(){
		/**
		 * NSGAII
		 */
			Instrumenter instrumenter = new Instrumenter()
			.withProblemClass(CBOproblem.class)
			.withFrequency(10000)
			.attachElapsedTimeCollector();
			
			NondominatedPopulation result = new Executor()
			.withAlgorithm("NSGAII")
			.withProblemClass(CBOproblem.class)
			.withProperty("populationSize", 100)
			.withProperty("withReplacement", true)
			.withProperty("sbx.rate", 0.95)
			.withProperty("sbx.distributionIndex", 15.0)
			.withProperty("pm.rate", 0.05)
			.withProperty("pm.distributionIndex", 20.0)
			.distributeOnAllCores()
			.withInstrumenter(instrumenter)
			.withMaxEvaluations(10000)
			.run();		

			
			
			/**
			 * GA
			 */
			
			Instrumenter instrumenter1 = new Instrumenter()
			.withProblemClass(CBOproblem.class)
			.withFrequency(10000)
			.attachElapsedTimeCollector();
			
			
			NondominatedPopulation result1 = new Executor()
			.withAlgorithm("GA")
			.withProblemClass(CBOproblem.class)
			.withProperty("populationSize", 100)
			.withProperty("sbx.rate", 0.95)
			.withProperty("sbx.distributionIndex", 15.0)
			.withProperty("pm.rate", 0.05)
			.withProperty("pm.distributionIndex", 20.0)
			.distributeOnAllCores()
			.withMaxEvaluations(10000)
			.distributeOnAllCores()
			.withInstrumenter(instrumenter1)
			.run();
			
			
			Accumulator accumulator = instrumenter.getLastAccumulator();
			
			Accumulator accumulator1 = instrumenter1.getLastAccumulator();
			
			double[] objective1=new double[result.size()];
			double[] objective2=new double[result.size()];
			double[] objective1_1=new double[result1.size()];
			double[] objective1_2=new double[result1.size()];

			//Objectives results
			System.out.println("NSGAII:");
			for (Solution s : result){
				//System.out.println(s.getVariable(0));
				objective1[result.indexOf(s)]=s.getObjective(0);
				objective2[result.indexOf(s)]=s.getObjective(1);
				System.out.println(s.getObjective(0)+","+s.getObjective(1)+"\n");
				writeResults("CBO/results/NSGA4",s.getObjective(0)+","+s.getObjective(1)+"\n");
			}
			System.out.println("GA:");
			for (Solution s : result1){
				objective1_1[result1.indexOf(s)]=s.getObjective(0);
				objective1_2[result1.indexOf(s)]=s.getObjective(1);
				System.out.println(s.getObjective(0)+","+s.getObjective(1)+"\n");
				writeResults("CBO/results/GA4",s.getObjective(0)+","+s.getObjective(1)+"\n");
			}
			
			//Algorithms Execution time
			for (int i=0; i<accumulator.size("NFE"); i++) {
				  System.out.println("NSGAII:"+accumulator.get("Elapsed Time", i).toString());
				  writeResults("CBO/results/NSGA4time",accumulator.get("Elapsed Time", i).toString()+"\n");
				}
			for (int i=0; i<accumulator1.size("NFE"); i++) {
				  System.out.println("GA:"+accumulator1.get("Elapsed Time", i).toString());
				  writeResults("CBO/results/GA4time",accumulator1.get("Elapsed Time", i).toString()+"\n");
				}		

//			Plot p =new Plot();
//			p.scatter("NSGAII", objective1, objective2);
//			//p.scatter("GA", objective1_1, objective1_2);
//			p.setXLabel("Cost");
//			p.setYLabel("Response time");
//			p.show();
	}
	
}
	