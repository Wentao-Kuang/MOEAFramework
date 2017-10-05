package PSO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.moeaframework.Executor;
import org.moeaframework.Instrumenter;
import org.moeaframework.analysis.collector.Accumulator;
import org.moeaframework.analysis.plot.Plot;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

public class RunExp1 {

	public static void main(String[] args) {
		//simulation times
		for(int i=0;i<1;i++){
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
			.withProblemClass(NSGAIIproblem.class)
			.withFrequency(2000)
			//.withReferenceSet(new File("PSO/result.text"))
			//.attachAllMetricCollectors()
			.attachElapsedTimeCollector()
			.attach(new CustomCollector(null))
			.attachElapsedTimeCollector();
			
			NondominatedPopulation result = new Executor()
			.withAlgorithm("NSGAII")
			.withProblemClass(NSGAIIproblem.class)
			.withProperty("populationSize", 50)
			.withProperty("withReplacement", true)
			.withProperty("hux.rate", 0.95)
			.withProperty("bf.rate", 0.01)
			.distributeOnAllCores()
			.withInstrumenter(instrumenter)
			.withMaxEvaluations(5000)
			.run();


			
			

			double[] objective1=new double[result.size()];
			double[] objective2=new double[result.size()];

			//Objectives results
			System.out.println("NSGAII:");
			for (Solution s : result){
				System.out.println(s.getVariable(0));
				objective1[result.indexOf(s)]=s.getObjective(0);
				objective2[result.indexOf(s)]=s.getObjective(1);
				System.out.println(s.getObjective(0)+","+s.getObjective(1)+"\n");
				writeResults("PSO/results/NSGA1",s.getObjective(0)+","+s.getObjective(1)+"\n");

			}

			//Algorithms Execution time
			

//			Plot p =new Plot();
//			p.scatter("NSGAII", objective1, objective2);
//			p.setXLabel("Cost");
//			p.setYLabel("Response time");
//			p.show();
	}
}
