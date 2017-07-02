package NSGAII;
import java.io.File;
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

			
			for (Solution s : result){
				//System.out.println(s.getVariable(0));
				objective1[result.indexOf(s)]=s.getObjective(0);
				objective2[result.indexOf(s)]=s.getObjective(1);
				System.out.println(s.getObjective(0)+","+s.getObjective(1)+"\n");
			}
			
			for (int i=0; i<accumulator.size("NFE"); i++) {
				  System.out.println("NSGAII:"+accumulator.get("Elapsed Time", i).toString());
				}
			
			for (Solution s : result1){
				objective1_1[result1.indexOf(s)]=s.getObjective(0);
				objective1_2[result1.indexOf(s)]=s.getObjective(1);
			}
			for (int i=0; i<accumulator1.size("NFE"); i++) {
				  System.out.println("GA:"+accumulator1.get("Elapsed Time", i).toString());
				}		

			Plot p =new Plot();
			//p.add("Radom",result1);
			//p.add("NSGAII", result);
			  p.scatter("NSGAII", objective1, objective2);
//			  p.scatter("random", objective1_1, objective1_2);
			  p.scatter("GA", objective1_1, objective1_2);
			  p.setXLabel("Cost");
			  p.setYLabel("Response time");
			//p.add("NSGAII", result,0,1);
			//p.add("GA",result2);
			//p.add("random",result3);
		
			p.show();
	}
	
	
}
	