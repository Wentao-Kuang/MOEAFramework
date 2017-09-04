package PSO;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

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

import NSGAII.CBOproblem;

public class RunPSOproblem {
	public static void main(String[] args) {
		//simulation times
		for(int i=0;i<30;i++){

			run();
			System.out.println("run: "+(i+1));
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
			.withProblemClass(NSGAIIproblem.class)
			.withFrequency(5000)
			.attachElapsedTimeCollector();

			NondominatedPopulation result = new Executor()
			.withAlgorithm("NSGAII")
			.withProblemClass(NSGAIIproblem.class)
			.withProperty("populationSize", 50)
			.withProperty("withReplacement", true)
			.withProperty("sbx.rate", 0.9)
			.withProperty("sbx.distributionIndex", 15.0)
			.withProperty("pm.rate", 0.5)
			.withProperty("pm.distributionIndex", 20.0)
			.distributeOnAllCores()
			.withInstrumenter(instrumenter)
			.withMaxEvaluations(5000)
			.run();

			/**
			 * GA
			 */

			Instrumenter instrumenter1 = new Instrumenter()
			.withProblemClass(NSGAIIproblem.class)
			.withFrequency(5000)
			.attachElapsedTimeCollector();


			NondominatedPopulation result1 = new Executor()
			.withAlgorithm("SPEA2")
			.withProblemClass(NSGAIIproblem.class)
			.withProperty("weights", new double[] {0.5,0.5})
			.withProperty("populationSize", 50)
			.withProperty("sbx.rate", 0.9)
			.withProperty("sbx.distributionIndex", 15.0)
			.withProperty("pm.rate", 0.5)
			.withProperty("pm.distributionIndex", 20.0)
			.distributeOnAllCores()
			.withMaxEvaluations(5000)
			.distributeOnAllCores()
			.withInstrumenter(instrumenter1)
			.run();


			/**
			 * Random
			 */

//			Instrumenter instrumenter2 = new Instrumenter()
//			.withProblemClass(NSGAIIproblem.class)
//			.withFrequency(5000)
//			.attachElapsedTimeCollector();
//
//
//			NondominatedPopulation result2 = new Executor()
//			.withAlgorithm("Random")
//			.withProblemClass(NSGAIIproblem.class)
//			//.withProperty("weights", new double[] {0.5,0.5})
//			.withProperty("populationSize", 100)
//			.distributeOnAllCores()
//			.withMaxEvaluations(5000)
//			.distributeOnAllCores()
//			.withInstrumenter(instrumenter2)
//			.run();

			Accumulator accumulator = instrumenter.getLastAccumulator();

			Accumulator accumulator1 = instrumenter1.getLastAccumulator();

			//Accumulator accumulator2 = instrumenter1.getLastAccumulator();

			double[] objective1=new double[result.size()];
			double[] objective2=new double[result.size()];
			double[] objective1_1=new double[result1.size()];
			double[] objective1_2=new double[result1.size()];
//			double[] objective2_1=new double[result2.size()];
//			double[] objective2_2=new double[result2.size()];

			//Objectives results
			System.out.println("NSGAII:");
			for (Solution s : result){
				//System.out.println(s.getVariable(0));
				objective1[result.indexOf(s)]=s.getObjective(0);
				objective2[result.indexOf(s)]=s.getObjective(1);
				System.out.println(s.getObjective(0)+","+s.getObjective(1)+"\n");
				writeResults("PSO/results/NSGA3",s.getObjective(0)+","+s.getObjective(1)+"\n");

			}
			System.out.println("SPEAII");
			for (Solution s : result1){
				objective1_1[result1.indexOf(s)]=s.getObjective(0);
				objective1_2[result1.indexOf(s)]=s.getObjective(1);
				System.out.println(s.getObjective(0)+","+s.getObjective(1)+"\n");

				writeResults("PSO/results/SPEA3",s.getObjective(0)+","+s.getObjective(1)+"\n");

			}

//			System.out.println("Random");
//			for (Solution s : result2){
//				objective2_1[result2.indexOf(s)]=s.getObjective(0);
//				objective2_2[result2.indexOf(s)]=s.getObjective(1);
//				System.out.println(s.getObjective(0)+","+s.getObjective(1)+"\n");
//
//				writeResults("PSO/results/Random1",s.getObjective(0)+","+s.getObjective(1)+"\n");
//
//			}

			//Algorithms Execution time
			for (int i=0; i<accumulator.size("NFE"); i++) {
				  System.out.println("NSGAII:"+accumulator.get("Elapsed Time", i).toString());
				  writeResults("PSO/results/NSGAtime3",accumulator.get("Elapsed Time", i).toString()+"\n");

				}
			for (int i=0; i<accumulator1.size("NFE"); i++) {
				  System.out.println("GA:"+accumulator1.get("Elapsed Time", i).toString());

				  writeResults("PSO/results/SPEAtime3",accumulator1.get("Elapsed Time", i).toString()+"\n");

				}

//			for (int i=0; i<accumulator2.size("NFE"); i++) {
//				  System.out.println("Random:"+accumulator2.get("Elapsed Time", i).toString());
//
//				  writeResults("PSO/results/Randomtime1",accumulator1.get("Elapsed Time", i).toString()+"\n");
//
//				}

			Plot p =new Plot();
			p.scatter("NSGAII", objective1, objective2);
			p.scatter("GA", objective1_1, objective1_2);
			//p.scatter("Random", objective2_1, objective2_2);
			p.setXLabel("Cost");
			p.setYLabel("Response time");
			p.show();
	}

}
